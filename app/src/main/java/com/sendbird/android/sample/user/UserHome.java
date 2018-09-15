package com.sendbird.android.sample.user;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.sendbird.android.sample.R;

import java.util.Calendar;
import java.util.Random;

import de.hdodenhof.circleimageview.CircleImageView;

public class UserHome extends AppCompatActivity {
    private BottomNavigationView mMainNav;
    private FrameLayout mMainFrame;
    private FirebaseAuth firebaseAuth;
    private CircleImageView imgProfile;
    private TextView txtFullname;
    private ChatFragment chatFragment;
    private AlarmFragment alarmFragment;
    private DepressionFragment depressionFragment;
    private SharedPreferences sharedPref;
    private SharedPreferences.Editor editor;
    private NavigationView navView;
    private int totalAns, result;
    private DatabaseReference mDatabase;
    private String id;

    private DrawerLayout mDrawerLayout;
    private ActionBarDrawerToggle mToggle;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_home);

        mMainFrame = findViewById(R.id.main_frame);
        mMainNav = findViewById(R.id.main_nav);
        mDrawerLayout = findViewById(R.id.drawerLayout);
        mToggle = new ActionBarDrawerToggle(this, mDrawerLayout, R.string.open, R.string.close);
        navView = findViewById(R.id.navView);
        firebaseAuth = FirebaseAuth.getInstance();
        id = firebaseAuth.getCurrentUser().getUid();

        Intent intent = getIntent();
        boolean openMeter = intent.getBooleanExtra("openMeter", false);

        SharedPreferences sharedPreferences = getSharedPreferences("PREFS", 0);
        int answ1 = sharedPreferences.getInt("ans1",0);
        int answ2 = sharedPreferences.getInt("ans2",0);
        int answ3 = sharedPreferences.getInt("ans3",0);
        int answ4 = sharedPreferences.getInt("ans4",0);
        int answ5 = sharedPreferences.getInt("ans5",0);
        int answ6 = sharedPreferences.getInt("ans6",0);
        int answ7 = sharedPreferences.getInt("ans7",0);
        int answ8 = sharedPreferences.getInt("ans8",0);
        int answ9 = sharedPreferences.getInt("ans9",0);
        int answ10 = sharedPreferences.getInt("ans10",0);

        totalAns = answ1 + answ2 + answ3 + answ4 + answ5 + answ6 + answ7 + answ8 + answ9 + answ10;
        result = totalAns / 10;



        String res = String.valueOf(result);

        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference depResult = database.getReference(id).child("depTest");
        depResult.setValue(res);
        depResult.keepSynced(true);

        mDrawerLayout.addDrawerListener(mToggle);
        mToggle.syncState();

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        chatFragment = new ChatFragment();
        alarmFragment = new AlarmFragment();
        depressionFragment = new DepressionFragment();

        //when device rotated!=load again
        if(savedInstanceState==null){
            setFragment(chatFragment);
        }

        View v = navView.getHeaderView(0);
        imgProfile = v.findViewById(R.id.imgPic);
        txtFullname = v.findViewById(R.id.txtUsername);




        FirebaseUser user = firebaseAuth.getCurrentUser();
        Glide.with(this)
                        .load(user.getPhotoUrl().toString())
                        .into((CircleImageView) v.findViewById(R.id.imgPic));
        String name = user.getDisplayName();
        txtFullname.setText(name);

        if (openMeter) {
            mMainNav.setSelectedItemId(R.id.nav_depBar);
            setFragment(depressionFragment);
        }

        mMainNav.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                switch (item.getItemId()){

                    case R.id.nav_chat:
                        setFragment(chatFragment);
                        return true;

                    case R.id.nav_medSched:
                        setFragment(alarmFragment);
                        return true;

                    case R.id.nav_depBar:
                        setFragment(depressionFragment);
                        return true;

                    default:
                        return false;

                }
            }
        });

        dailyQuoteTest();

        navView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case(R.id.nav_home):
                        mMainNav.setVisibility(View.VISIBLE);
                        chatFragment = new ChatFragment();
                        setFragment(chatFragment);
                        break;
                    case(R.id.nav_profile):
                        mMainNav.setVisibility(View.GONE);
                        ProfileFragment profileFragment = new ProfileFragment();
                        setFragment(profileFragment);
                        break;
                    case(R.id.nav_logout):
                        Logout();
                        break;
                }
                mDrawerLayout.closeDrawer(GravityCompat.START);
                return true;
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        if(firebaseAuth.getCurrentUser()!=null && firebaseAuth.getCurrentUser().getDisplayName()==null&&firebaseAuth.getCurrentUser().getPhotoUrl()==null){
            finish();
            startActivity(new Intent(UserHome.this, UserRegister2.class));
            Toast.makeText(this,"Please finish registraion first", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onBackPressed() {
        if(mDrawerLayout.isDrawerOpen(GravityCompat.START)){
            mDrawerLayout.closeDrawer(GravityCompat.START);
        }else{
            super.onBackPressed();
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(mToggle.onOptionsItemSelected(item)){
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void dailyQuoteTest() {
        Calendar calendar = Calendar.getInstance();
        int currentDay = calendar.get(Calendar.DAY_OF_MONTH);
        SharedPreferences settings = getSharedPreferences("PREFS", 0);
        int lastDay = settings.getInt("day", 1);

        if(lastDay!=currentDay){
            SharedPreferences.Editor editor = settings.edit();
            editor.putInt("day", currentDay);
            editor.commit();
            //code that will be started only once a day
            String[] arrayOfStrings = this.getResources().getStringArray(R.array.dailyQuotes);
            String rndQuotes = arrayOfStrings[new Random().nextInt(arrayOfStrings.length)];
            dailyQuotes(rndQuotes);
        }
    }

    private void setFragment(Fragment fragment) {
        android.support.v4.app.FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.main_frame, fragment);
        fragmentTransaction.commit();

    }

    private void dailyQuotes(String quote){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        View v = LayoutInflater.from(UserHome.this).inflate(R.layout.daily_quotes_layout,null);
        TextView quoteCon = v.findViewById(R.id.dailyQuotes);

        quoteCon.setText(quote);

        if(quote.length()>90)
            quoteCon.setTextSize(15);

        builder.setPositiveButton("Got it!", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });

        builder.setView(v);
        builder.show();
    }

    public void Logout(){
        final AlertDialog.Builder alertBuilder = new AlertDialog.Builder(this);
        alertBuilder.setMessage("Do you want to logout?")
                .setCancelable(false)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        finish();
                        firebaseAuth.signOut();
                        startActivity(new Intent(UserHome.this, UserLogin.class));
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
        AlertDialog alert = alertBuilder.create();
        alert.setTitle("Log out");
        alert.show();
    }
}
