package com.sendbird.android.sample.user;


import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

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


/**
 * A simple {@link Fragment} subclass.
 */
public class ChatFragment extends Fragment {
    ImageButton btnDepression, btnHappy, btnHopeful;
    Button btnDoctor, btnPatients;
    private FirebaseAuth firebaseAuth;
    private ProgressDialog progressDialog;
    private TextView txtPatientDetails, txtDoctorDetails;
    private DatabaseReference databaseReference;
    private static final String APP_ID_DEPRESSION = "26C9F889-F713-4847-8C71-40BA098D3D2A";
    private static final String APP_ID_HAPPY = "09BDD7F8-267A-4C4B-B966-77013078AA79";
    private static final String APP_ID_HOPEFUL = "CB8FC80D-78A6-46F3-9941-CEBB93D9CE73";
    String userID, nickName, server, choose, isDoctor;
    private LinearLayout linearLayoutUser, linearLayoutDoctor;

    public ChatFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_chat, container, false);
        firebaseAuth = FirebaseAuth.getInstance();
        progressDialog = new ProgressDialog(getContext());
        btnDepression = rootView.findViewById(R.id.btnDepression);
        btnHappy = rootView.findViewById(R.id.btnHappy);
        btnHopeful = rootView.findViewById(R.id.btnHopeful);
        btnDoctor = rootView.findViewById(R.id.btnDoctor);
        btnPatients = rootView.findViewById(R.id.btnPatients);
        txtPatientDetails = rootView.findViewById(R.id.txtPatientDetails);
        txtDoctorDetails = rootView.findViewById(R.id.txtDoctorDetails);
        linearLayoutUser = rootView.findViewById(R.id.linearLayoutUser);
        linearLayoutDoctor = rootView.findViewById(R.id.linearLayoutDoctor);

        getFirstname();
        getServer();
        getisDoctor();


        btnDepression.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressDialog.setMessage("Loading Server...");
                progressDialog.setCancelable(false);
                progressDialog.show();
                SendBird.init(APP_ID_DEPRESSION, getContext());
                setServer("depression");
                choose = "depression";
                connectToSendBird();
            }
        });

        btnHappy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressDialog.setMessage("Loading Server...");
                progressDialog.setCancelable(false);
                progressDialog.show();
                SendBird.init(APP_ID_HAPPY, getContext());
                setServer("cheerful");
                choose = "cheerful";
                connectToSendBird();
            }
        });

        btnHopeful.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressDialog.setMessage("Loading Server...");
                progressDialog.setCancelable(false);
                progressDialog.show();
                SendBird.init(APP_ID_HOPEFUL, getContext());
                setServer("hopeful");
                choose = "hopeful";
                connectToSendBird();
            }
        });

        btnDoctor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), ContactDoctorActivity.class));
            }
        });

        return rootView;
    }

    @Override
    public void onStart() {
        super.onStart();
        if(PreferenceUtils.getConnected()) {
            connectToSendBird();
        }
    }

    private void connectToSendBird() {
        ConnectionManager.login(userID, new SendBird.ConnectHandler() {
            @Override
            public void onConnected(User user, SendBirdException e) {
                // Callback received; hide the progress bar.

                if (e != null) {
                    // Error!
                    // Show login failure snackbar
                    PreferenceUtils.setConnected(false);
                    return;
                }

                PreferenceUtils.setNickname(nickName);
                PreferenceUtils.setProfileUrl(user.getProfileUrl());
                PreferenceUtils.setConnected(true);

                // Update the user's nickname
                updateCurrentUserInfo(nickName);
                updateCurrentUserPushToken();

                // Proceed to MainActivity
                Intent intent = new Intent(getActivity(), GroupChannelActivity.class);
                intent.putExtra("serverExtra1", choose);
                startActivity(intent);
                getActivity().finish();
                progressDialog.dismiss();
            }
        });
    }

    /**
     * Update the user's push token.
     */
    private void updateCurrentUserPushToken() {
        PushUtils.registerPushTokenForCurrentUser(getActivity(), null);
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

    private void getisDoctor() {
        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        String id = firebaseAuth.getCurrentUser().getUid();
        DatabaseReference refisDoctor = database.getReference(id).child("isDoctor");

        progressDialog.setMessage("Checking user...");
        progressDialog.show();
        refisDoctor.addValueEventListener(new com.google.firebase.database.ValueEventListener() {
            @Override
            public void onDataChange(com.google.firebase.database.DataSnapshot dataSnapshot) {
                String value = dataSnapshot.getValue(String.class);
                isDoctor = value;

                if (value.equalsIgnoreCase("false")) {
                    linearLayoutUser.setVisibility(View.VISIBLE);
                    txtDoctorDetails.setVisibility(View.VISIBLE);
                    btnDoctor.setVisibility(View.VISIBLE);
                } else {
                    linearLayoutDoctor.setVisibility(View.VISIBLE);
                    txtPatientDetails.setVisibility(View.VISIBLE);
                    btnPatients.setVisibility(View.VISIBLE);
                }
                progressDialog.dismiss();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                progressDialog.dismiss();
            }
        });
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

    private void getFirstname(){
        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        String id = firebaseAuth.getCurrentUser().getUid();
        DatabaseReference refProv = database.getReference(id).child("fname");
        refProv.keepSynced(true);
        refProv.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                progressDialog.setMessage("Loading Server...");
                progressDialog.show();
                userID = dataSnapshot.getValue(String.class);
                nickName = dataSnapshot.getValue(String.class);
                progressDialog.dismiss();
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

}
