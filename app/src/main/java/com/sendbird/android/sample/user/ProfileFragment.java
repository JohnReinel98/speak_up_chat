package com.sendbird.android.sample.user;


import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.firebase.client.Firebase;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.sendbird.android.sample.R;

import de.hdodenhof.circleimageview.CircleImageView;

public class ProfileFragment extends Fragment {
    private FirebaseAuth firebaseAuth;
    private FirebaseUser user;
    private TextView txtFullname, txtGender, txtStreet, txtCity, txtProvince, txtBirthday, btnUpdate;
    private DatabaseReference firebaseDatabaseGender;
    private CircleImageView imgProfilePic;
    private Firebase mRef;
    public String address, street, city, province;
    private ProgressDialog progressDialog;
    private int showed = 0;
    String id;
    private BottomNavigationView mMainNav;

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

        firebaseAuth = FirebaseAuth.getInstance();
        user = firebaseAuth.getCurrentUser();
        id = firebaseAuth.getCurrentUser().getUid();

        loadUserInfo();

        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getContext(), UserUpdateProfile.class));
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

}
