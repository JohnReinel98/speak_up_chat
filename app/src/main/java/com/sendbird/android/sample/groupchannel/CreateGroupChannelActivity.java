package com.sendbird.android.sample.groupchannel;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.sendbird.android.GroupChannel;
import com.sendbird.android.SendBirdException;
import com.sendbird.android.sample.R;
import com.sendbird.android.sample.utils.PreferenceUtils;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

public class CreateGroupChannelActivity extends AppCompatActivity
        implements SelectUserFragment.UsersSelectedListener, SelectDistinctFragment.DistinctSelectedListener {

    public static final String EXTRA_NEW_CHANNEL_URL = "EXTRA_NEW_CHANNEL_URL";

    static final int STATE_SELECT_USERS = 0;
    static final int STATE_SELECT_DISTINCT = 1;

    private Button mNextButton, mCreateButton;

    private List<String> mSelectedIds;
    private boolean mIsDistinct = true;
    private int mCurrentState;
    private ProgressDialog progressDialog;
    private Toolbar mToolbar;
    private String server,userFname, checkJoined, checkName;
    private FirebaseAuth firebaseAuth;
    private boolean noSearch;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_create_group_channel);
        firebaseAuth = FirebaseAuth.getInstance();
        mSelectedIds = new ArrayList<>();
        progressDialog = new ProgressDialog(getApplicationContext());

        if (noSearch) {
            Toast.makeText(getApplicationContext(), "No Available Users, Please Try Again", Toast.LENGTH_LONG).show();
        }

        getFirstname();
        getServer();

        if (savedInstanceState == null) {
            Fragment fragment = SelectUserFragment.newInstance();
            FragmentManager manager = getSupportFragmentManager();
            manager.beginTransaction()
                    .replace(R.id.container_create_group_channel, fragment)
                    .commit();
        }

        mNextButton = (Button) findViewById(R.id.button_create_group_channel_next);
        mNextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mCurrentState == STATE_SELECT_USERS) {
                    Fragment fragment = SelectDistinctFragment.newInstance();
                    getSupportFragmentManager().beginTransaction()
                            .replace(R.id.container_create_group_channel, fragment)
                            .addToBackStack(null)
                            .commit();
                }
            }
        });
        mNextButton.setEnabled(false);

        mCreateButton = (Button) findViewById(R.id.button_create_group_channel_create);
        mCreateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mCurrentState == STATE_SELECT_USERS) {
                    mIsDistinct = PreferenceUtils.getGroupChannelDistinct();
                    createGroupChannel(mSelectedIds, mIsDistinct);
                }
            }
        });
        mCreateButton.setEnabled(false);

        mToolbar = (Toolbar) findViewById(R.id.toolbar_create_group_channel);
        setSupportActionBar(mToolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_arrow_left_white_24_dp);
        }

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == android.R.id.home) {
            onBackPressed();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    void setState(int state) {
        if (state == STATE_SELECT_USERS) {
            mCurrentState = STATE_SELECT_USERS;
            mCreateButton.setVisibility(View.VISIBLE);
            mNextButton.setVisibility(View.GONE);
        } else if (state == STATE_SELECT_DISTINCT){
            mCurrentState = STATE_SELECT_DISTINCT;
            mCreateButton.setVisibility(View.VISIBLE);
            mNextButton.setVisibility(View.GONE);
        }
    }

    @Override
    public void onUserSelected(boolean selected, String userId) {
        if (selected) {
            mSelectedIds.add(userId);
        } else {
            mSelectedIds.remove(userId);
        }

        if (mSelectedIds.size() > 0) {
            mCreateButton.setEnabled(true);
        } else {
            mCreateButton.setEnabled(false);
        }
    }

    @Override
    public void onDistinctSelected(boolean distinct) {
        mIsDistinct = distinct;
    }

    private void createGroupChannel(List<String> userIds, boolean distinct) {
        GroupChannel.createChannelWithUserIds(userIds, distinct, new GroupChannel.GroupChannelCreateHandler() {
            @Override
            public void onResult(GroupChannel groupChannel, SendBirdException e) {
                if (e != null) {
                    // Error!
                    return;
                }

                Intent intent = new Intent();
                intent.putExtra(EXTRA_NEW_CHANNEL_URL, groupChannel.getUrl());
                setResult(RESULT_OK, intent);
                finish();
            }
        });
    }

    private void setJoined(final String toggle){
        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        String id = firebaseAuth.getCurrentUser().getUid();
        final DatabaseReference refServer = database.getReference(id).child("joined");
        refServer.keepSynced(true);
        refServer.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                refServer.setValue(toggle);
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
        refProv.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                userFname = dataSnapshot.getValue(String.class);
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    private void getServer(){
        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        String id = firebaseAuth.getCurrentUser().getUid();
        DatabaseReference refServer = database.getReference(id).child("server");
        refServer.keepSynced(true);
        refServer.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                server = dataSnapshot.getValue(String.class);
                serverJoiner();
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }

    private void serverJoiner(){
        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference refServer = database.getReference();
        Query searchQuery = refServer.orderByChild("server").equalTo(server);
        searchQuery.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                try{
                    Random random = new Random();
                    int questionCount = (int) dataSnapshot.getChildrenCount();
                    if (questionCount == 2){
                        questionCount -= 1;
                    }
                    int rand = random.nextInt(questionCount);
                    Iterator itr = dataSnapshot.getChildren().iterator();
                    boolean found = false;
                    for(int i = 0; i < rand; i++) {
                        itr.next();
                    }

                    do {

                        DataSnapshot childSnapshot = (DataSnapshot) itr.next();
                        checkName = childSnapshot.child("fname").getValue().toString();
                        checkJoined = childSnapshot.child("joined").getValue().toString();

                        if (checkName.equalsIgnoreCase(userFname) || checkJoined.equalsIgnoreCase("true")){
                            Toast.makeText(getApplicationContext(), "Search Mismatch, Returning...", Toast.LENGTH_LONG).show();
                            noSearch = true;
                        }
                        else {
                            Toast.makeText(getApplicationContext(), "Search Successful", Toast.LENGTH_LONG).show();
                            found = true;
                            if (childSnapshot.child("joined").getValue().toString().equalsIgnoreCase("false") &&
                                    !childSnapshot.child("fname").getValue().toString().equalsIgnoreCase(userFname) &&
                                    childSnapshot.child("server").getValue().toString().equalsIgnoreCase(server)){

                                mSelectedIds.add(checkName);
                                mSelectedIds.add(userFname);
                                setJoined("true");
                                createGroupChannel(mSelectedIds, true);
                                break;
                            }
                        }
                    }
                    while(!found);
                } catch (Exception e){
                    Toast.makeText(getApplicationContext(), "Something went wrong, try again.", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Toast.makeText(getApplicationContext(), databaseError.toString(), Toast.LENGTH_LONG).show();
            }
        });
    }
}
