package com.sendbird.android.sample.user;


import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.sendbird.android.sample.R;
import com.sendbird.android.sample.main.LoginActivity;

import de.hdodenhof.circleimageview.CircleImageView;

public class ProfileFragment extends Fragment {
    private FirebaseAuth firebaseAuth;
    private FirebaseUser user;
    private TextView txtFullname, txtGender, txtStreet, txtCity, txtProvince, txtBirthday, btnUpdate, txtEmail, txtChecker;
    private CircleImageView imgProfilePic;
    private ProgressDialog progressDialog;
    private Button btnVerify, btnRequestChangePassword;
    private int showed = 0, rooms_total;
    private float rating_total;
    String id;

    public ProfileFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_profile, container, false);
        progressDialog = new ProgressDialog(getContext());

        txtFullname = v.findViewById(R.id.txtFullname);
        txtGender = v.findViewById(R.id.txtGender);
        txtStreet = v.findViewById(R.id.txtStreet);
        txtCity = v.findViewById(R.id.txtCity);
        txtProvince = v.findViewById(R.id.txtProv);
        txtBirthday = v.findViewById(R.id.txtBirthday);
        btnUpdate = v.findViewById(R.id.btnUpdate);
        imgProfilePic = v.findViewById(R.id.imgProfilePic);
        btnVerify = v.findViewById(R.id.btnVerify);
        btnRequestChangePassword = v.findViewById(R.id.btnRequestChangePassword);
        txtEmail = v.findViewById(R.id.txtEmail);
        txtChecker = v.findViewById(R.id.txtChecker);

        firebaseAuth = FirebaseAuth.getInstance();
        user = firebaseAuth.getCurrentUser();
        id = firebaseAuth.getCurrentUser().getUid();



        loadUserInfo();
        getRating();
        getRooms();

        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getContext(), UserUpdateProfile.class));
            }
        });

        if(user.isEmailVerified()){
            txtChecker.setText("Email Verified");
            btnVerify.setVisibility(View.GONE);
        } else {
            progressDialog.setTitle("Email Verification");
            progressDialog.setMessage("Sending... \n\n Note: You need to login again after verifying");
            progressDialog.setCancelable(false);
            btnVerify.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    user.sendEmailVerification().addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            Toast.makeText(getContext(), "Email Verification sent", Toast.LENGTH_SHORT).show();
                            final AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                            builder.setTitle("Redirect");
                            builder.setMessage("Email Verification Sent, Check your email \nYou need to Login again");
                            builder.setNeutralButton("Okay", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    progressDialog.dismiss();
                                    dialog.dismiss();
                                    getActivity().finish();
                                    firebaseAuth.signOut();
                                    startActivity(new Intent(getContext(), UserLogin.class));
                                }

                            });

                            builder.show();
                        }
                    });
                }
            });
        }

        btnRequestChangePassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String email = user.getEmail();
                final AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                builder.setTitle("Change Password");
                builder.setMessage("Note: Requesting for Change Password will signout you in this app, and an mail will send on your email address");
                builder.setPositiveButton("Proceed", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        progressDialog.setMessage("Sending email");
                        progressDialog.show();
                        firebaseAuth.sendPasswordResetEmail(email).addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if(task.isSuccessful()){
                                    progressDialog.dismiss();
                                    Toast.makeText(getContext(), "Password reset email sent",Toast.LENGTH_SHORT).show();
                                    getActivity().finish();
                                    startActivity(new Intent(getContext(), UserLogin.class));
                                }
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(getContext(), e.toString() ,Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                }).setNeutralButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });



                builder.show();



            }
        });


        return v;
    }

    public void loadUserInfo(){
        progressDialog.setMessage("Loading user info...");
        progressDialog.show();

        Glide.with(this).load(user.getPhotoUrl().toString()).into(imgProfilePic);
        String name = user.getDisplayName();
        txtFullname.setText(name);

        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference refGender = database.getReference(id).child("gender");
        refGender.keepSynced(true);
        refGender.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String user = dataSnapshot.getValue(String.class);
                txtGender.setText(user.toString());
            }


            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        FirebaseUser user = firebaseAuth.getCurrentUser();
        txtEmail.setText(user.getEmail());

        DatabaseReference refStreet = database.getReference(id).child("street");
        refStreet.keepSynced(true);
        refStreet.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String user = dataSnapshot.getValue(String.class);
                //txtGender.setText(user.getGender());
                System.out.println(user);
                txtStreet.setText(user.toString()+", ");

            }


            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        DatabaseReference refCity = database.getReference(id).child("city");
        refCity.keepSynced(true);
        refCity.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String user = dataSnapshot.getValue(String.class);
                //txtGender.setText(user.getGender());
                System.out.println(user);
                txtCity.setText(user.toString()+", ");
            }


            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        DatabaseReference refProv = database.getReference(id).child("province");
        refProv.keepSynced(true);
        refProv.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String user = dataSnapshot.getValue(String.class);
                //txtGender.setText(user.getGender());
                System.out.println(user);
                txtProvince.setText(user.toString());
            }


            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        DatabaseReference refBday = database.getReference(id).child("birthday");
        refBday.keepSynced(true);
        refBday.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String user = dataSnapshot.getValue(String.class);
                //txtGender.setText(user.getGender());
                System.out.println(user);
                txtBirthday.setText(user.toString());
            }


            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        progressDialog.dismiss();

    }

    private void getRooms(){
        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        String id = firebaseAuth.getCurrentUser().getUid();
        DatabaseReference refProv = database.getReference(id).child("rooms_total");
        refProv.keepSynced(true);
        refProv.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                rooms_total = Integer.parseInt(dataSnapshot.getValue(String.class));
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    private void getRating(){
        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        String id = firebaseAuth.getCurrentUser().getUid();
        DatabaseReference refProv = database.getReference(id).child("rating_total");
        refProv.keepSynced(true);
        refProv.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                rating_total = Float.parseFloat(dataSnapshot.getValue(String.class));
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

}
