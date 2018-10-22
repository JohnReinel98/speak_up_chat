package com.sendbird.android.sample.user;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.sendbird.android.SendBird;
import com.sendbird.android.SendBirdException;
import com.sendbird.android.User;
import com.sendbird.android.sample.R;
import com.sendbird.android.sample.groupchannel.GroupChannelActivity;
import com.sendbird.android.sample.main.ConnectionManager;
import com.sendbird.android.sample.utils.PreferenceUtils;
import com.sendbird.android.sample.utils.PushUtils;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;

public class PatientMessageActivity extends AppCompatActivity {
    private Button btnMessagePatient;
    private String name, gender, city, street, province, date, bday, contact_name, contact_phone, contact_relationship, data, doctor, patient;
    String userID, nickName, server, choose;
    private DatabaseReference databaseReference;
    private FirebaseAuth firebaseAuth;
    private ProgressDialog progressDialog;
    private TextView txtName, txtGender, txtDate, txtBirthday, txtAddress, txtContactName, txtContactPhone, txtContactRelationship;
    private static final String APP_ID_DOCTOR = "C78BA867-A68E-497D-8959-C2EDD9BD42D9";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patient_message);
        //getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        firebaseAuth = FirebaseAuth.getInstance();
        databaseReference = FirebaseDatabase.getInstance().getReference("Doctors");
        progressDialog = new ProgressDialog(this);
        txtName = findViewById(R.id.txtName);
        txtGender = findViewById(R.id.txtGender);
        txtDate = findViewById(R.id.txtDateTime);
        txtBirthday = findViewById(R.id.txtBirthday);
        txtAddress = findViewById(R.id.txtAddress);
        txtContactName = findViewById(R.id.txtContactName);
        txtContactPhone = findViewById(R.id.txtContactPhone);
        txtContactRelationship = findViewById(R.id.txtContactRelationShip);

        btnMessagePatient = findViewById(R.id.btnMessagePatient);

        Intent intent = getIntent();
        data = intent.getStringExtra("patient");
        String[] separated = data.split(":");
        patient = separated[0];
        doctor = separated[1];

        getName();
        getGender();
        getBday();
        getStreet();
        getProv();
        getCity();
        getGender();
        getContactName();
        getContactPhone();
        getContactRelationship();
        getDate();
        getServer();

        btnMessagePatient.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressDialog.setMessage("Loading Server...");
                progressDialog.setCancelable(false);
                progressDialog.show();
                SendBird.init(APP_ID_DOCTOR, getApplicationContext());
                setServer("doctor");
                choose = "doctor";
                connectToSendBirdDoctor();
            }
        });
    }

    @Override
    public void onStart() {
        super.onStart();
        if(PreferenceUtils.getConnected()) {
            connectToSendBirdDoctor();
        }
    }

    private void connectToSendBirdDoctor() {
        final String userName = firebaseAuth.getCurrentUser().getDisplayName().replace(".","");
        ConnectionManager.login(userName, new SendBird.ConnectHandler() {
            @Override
            public void onConnected(User user, SendBirdException e) {
                // Callback received; hide the progress bar.

                if (e != null) {
                    // Error!
                    // Show login failure snackbar
                    PreferenceUtils.setConnected(false);
                    return;
                }

                PreferenceUtils.setNickname(userName);
                PreferenceUtils.setProfileUrl(user.getProfileUrl());
                PreferenceUtils.setConnected(true);

                // Update the user's nickname
                updateCurrentUserInfo(userName);
                updateCurrentUserPushToken();

                // Proceed to MainActivity
                Intent intent = new Intent(PatientMessageActivity.this, GroupChannelActivity.class);
                intent.putExtra("serverExtra1", choose);
                intent.putExtra("patientName", name);
                intent.putExtra("isDoctorMsg", true);
                startActivity(intent);
                finish();
                progressDialog.dismiss();
            }
        });
    }

    private void updateCurrentUserPushToken() {
        PushUtils.registerPushTokenForCurrentUser(PatientMessageActivity.this, null);
    }

    /**
     * Updates the user's nickname.
     * @param userNickname  The new nickname of the user.
     */
    private void updateCurrentUserInfo(final String userNickname) {
        SendBird.updateCurrentUserInfo(userNickname, null, new SendBird.UserInfoUpdateHandler() {
            @Override
            public void onUpdated(SendBirdException e) {
                if (e != null) {
                    // Error!

                    // Show update failed snackbar

                    return;
                }

                PreferenceUtils.setNickname(userNickname);
            }
        });
    }

    private String getServer(){
        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        String id = firebaseAuth.getCurrentUser().getUid();
        DatabaseReference refServer = database.getReference(id).child("server");
        refServer.keepSynced(true);
        refServer.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                server = dataSnapshot.getValue(String.class);
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        return server;
    }

    private void setServer(final String server){
        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        String id = firebaseAuth.getCurrentUser().getUid();
        final DatabaseReference refServer = database.getReference(id).child("server");
        refServer.keepSynced(true);
        refServer.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                refServer.setValue(server);
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    private void getName(){
        DatabaseReference refServer = databaseReference.child("Messages").child(doctor).child(patient).child("name");
        refServer.keepSynced(true);
        refServer.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                name = dataSnapshot.getValue(String.class);
                txtName.setText(name);
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    private void getDate(){
        DatabaseReference refServer = databaseReference.child("Messages").child(doctor).child(patient).child("dateTime");
        refServer.keepSynced(true);
        refServer.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                date = dataSnapshot.getValue(String.class);
                txtDate.setText(date);
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    private void getBday(){
        DatabaseReference refServer = databaseReference.child("Messages").child(doctor).child(patient).child("bday");
        refServer.keepSynced(true);
        refServer.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                bday = dataSnapshot.getValue(String.class);
                txtBirthday.setText(bday);
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    private void getGender(){
        DatabaseReference refServer = databaseReference.child("Messages").child(doctor).child(patient).child("gender");
        refServer.keepSynced(true);
        refServer.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                gender = dataSnapshot.getValue(String.class);
                txtGender.setText(gender);
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    private void getProv(){
        DatabaseReference refServer = databaseReference.child("Messages").child(doctor).child(patient).child("province");
        refServer.keepSynced(true);
        refServer.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                province = dataSnapshot.getValue(String.class);
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    private void getStreet(){
        DatabaseReference refServer = databaseReference.child("Messages").child(doctor).child(patient).child("street");
        refServer.keepSynced(true);
        refServer.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                street = dataSnapshot.getValue(String.class);
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    private void getCity(){
        DatabaseReference refServer = databaseReference.child("Messages").child(doctor).child(patient).child("city");
        refServer.keepSynced(true);
        refServer.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                city = dataSnapshot.getValue(String.class);
                txtAddress.setText(street + " " + city + " " + province);
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    private void getContactName(){
        DatabaseReference refServer = databaseReference.child("Messages").child(doctor).child(patient).child("contact_name");
        refServer.keepSynced(true);
        refServer.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                contact_name = dataSnapshot.getValue(String.class);
                txtContactName.setText(contact_name);
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    private void getContactPhone(){
        DatabaseReference refServer = databaseReference.child("Messages").child(doctor).child(patient).child("contact_phone");
        refServer.keepSynced(true);
        refServer.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                contact_phone = dataSnapshot.getValue(String.class);
                txtContactPhone.setText(contact_phone);
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    private void getContactRelationship(){
        DatabaseReference refServer = databaseReference.child("Messages").child(doctor).child(patient).child("contact_relationship");
        refServer.keepSynced(true);
        refServer.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                contact_relationship = dataSnapshot.getValue(String.class);
                txtContactRelationship.setText(contact_relationship);
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

}
