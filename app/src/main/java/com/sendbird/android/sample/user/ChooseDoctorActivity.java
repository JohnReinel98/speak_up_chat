package com.sendbird.android.sample.user;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.sendbird.android.sample.R;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ChooseDoctorActivity extends AppCompatActivity {
    private Button btnDoctor1, btnDoctor2, btnDoctor3;
    private ProgressDialog progressDialog;
    private FirebaseAuth firebaseAuth;
    private DatabaseReference databaseReference;
    private String id, name, street, city, province, birthday, gender, contact_name, contact_phone, contact_relationship;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_doctor);
        /*getSupportActionBar().setDisplayHomeAsUpEnabled(true);*/

        firebaseAuth = FirebaseAuth.getInstance();
        id = firebaseAuth.getCurrentUser().getUid();
        name = firebaseAuth.getCurrentUser().getDisplayName();
        databaseReference = FirebaseDatabase.getInstance().getReference("Doctors");
        progressDialog = new ProgressDialog(this);

        fetchUserDetails();

        btnDoctor1 = findViewById(R.id.btnDoctor1);
        btnDoctor2 = findViewById(R.id.btnDoctor2);
        btnDoctor3 = findViewById(R.id.btnDoctor3);

        btnDoctor1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*Intent intent = new Intent(ChooseDoctorActivity.this, ContactDoctorActivity.class);
                intent.putExtra("doctor", "Doctor1");
                startActivity(intent);*/
                //doctor = "Doctor1";
                saveUser("Doctor1");
            }
        });

        btnDoctor2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*Intent intent = new Intent(ChooseDoctorActivity.this, ContactDoctorActivity.class);
                intent.putExtra("doctor", "Doctor2");
                startActivity(intent);*/
                //doctor = "Doctor2";
                saveUser("Doctor2");
            }
        });

        btnDoctor3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*Intent intent = new Intent(ChooseDoctorActivity.this, ContactDoctorActivity.class);
                intent.putExtra("doctor", "Doctor3");
                startActivity(intent);*/
                Toast.makeText(getApplicationContext(), "Doctor not found, please choose other doctor.", Toast.LENGTH_LONG).show();
            }
        });
    }

    private void saveUser(final String doctor) {
        progressDialog.setMessage("Sending details...");
        progressDialog.setCancelable(false);
        progressDialog.show();

        FirebaseUser currentUser = firebaseAuth.getCurrentUser();
        String sender = currentUser.getDisplayName().replace(".", "");

        Date date = new Date();
        String strDateFormat = "MMMM-dd-YYYY hh:mm:ss a";
        DateFormat dateFormat = new SimpleDateFormat(strDateFormat);
        final String formattedDate = dateFormat.format(date);

        final DatabaseReference refName = databaseReference.child("Messages").child(doctor).child(sender).child("name");
        refName.keepSynced(true);
        refName.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                refName.setValue(name);
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        final DatabaseReference refBday = databaseReference.child("Messages").child(doctor).child(sender).child("bday");
        refBday.keepSynced(true);
        refBday.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                refBday.setValue(birthday);
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        final DatabaseReference refGender = databaseReference.child("Messages").child(doctor).child(sender).child("gender");
        refGender.keepSynced(true);
        refGender.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                refGender.setValue(gender);
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        final DatabaseReference refStreet = databaseReference.child("Messages").child(doctor).child(sender).child("street");
        refStreet.keepSynced(true);
        refStreet.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                refStreet.setValue(street);
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        final DatabaseReference refCity = databaseReference.child("Messages").child(doctor).child(sender).child("city");
        refCity.keepSynced(true);
        refCity.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                refCity.setValue(city);
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        final DatabaseReference refProv = databaseReference.child("Messages").child(doctor).child(sender).child("province");
        refProv.keepSynced(true);
        refProv.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                refProv.setValue(province);
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        final DatabaseReference refCName = databaseReference.child("Messages").child(doctor).child(sender).child("contact_name");
        refCName.keepSynced(true);
        refCName.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                refCName.setValue(contact_name);
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        final DatabaseReference refCPhone = databaseReference.child("Messages").child(doctor).child(sender).child("contact_phone");
        refCPhone.keepSynced(true);
        refCName.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                refCPhone.setValue(contact_phone);
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        final DatabaseReference refCRelationShip = databaseReference.child("Messages").child(doctor).child(sender).child("contact_relationship");
        refCRelationShip.keepSynced(true);
        refCRelationShip.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                refCRelationShip.setValue(contact_relationship);
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        final DatabaseReference refDateTime = databaseReference.child("Messages").child(doctor).child(sender).child("dateTime");
        refDateTime.keepSynced(true);
        refDateTime.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                refDateTime.setValue(formattedDate);
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        progressDialog.dismiss();
        Toast.makeText(getApplicationContext(), "Information Sent, Please wait for your doctor to message you.", Toast.LENGTH_LONG).show();
        showSuccessDialog();

    }

    private void showSuccessDialog() {
        //show instructions
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(ChooseDoctorActivity.this);
        View view = LayoutInflater.from(ChooseDoctorActivity.this).inflate(R.layout.question_instructions_layout,null);
        alertDialog
                .setCancelable(false)
                .setPositiveButton("Okay", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        finish();
                        startActivity(new Intent(ChooseDoctorActivity.this, UserQuestion.class));
                    }
                });
        alertDialog.setView(view);
        alertDialog.show();
    }

    private void fetchUserDetails() {
        progressDialog.setMessage("Loading user info...");
        progressDialog.setCancelable(false);
        progressDialog.show();

        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference refGender = database.getReference(id).child("gender");
        refGender.keepSynced(true);
        refGender.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                gender = dataSnapshot.getValue(String.class);
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
                street = dataSnapshot.getValue(String.class);

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
                city = dataSnapshot.getValue(String.class);
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
                province = dataSnapshot.getValue(String.class);
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
                birthday = dataSnapshot.getValue(String.class);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        DatabaseReference refCName = database.getReference(id).child("contact_name");
        refCName.keepSynced(true);
        refCName.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                contact_name = dataSnapshot.getValue(String.class);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        DatabaseReference refCPhone = database.getReference(id).child("contact_phone");
        refCPhone.keepSynced(true);
        refCPhone.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                contact_phone = dataSnapshot.getValue(String.class);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        DatabaseReference refCRelationShip = database.getReference(id).child("contact_relationship");
        refCRelationShip.keepSynced(true);
        refCRelationShip.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                contact_relationship = dataSnapshot.getValue(String.class);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        progressDialog.dismiss();
    }

}
