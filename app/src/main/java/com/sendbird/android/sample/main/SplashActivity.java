package com.sendbird.android.sample.main;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.google.firebase.auth.FirebaseAuth;
import com.sendbird.android.sample.R;
import com.sendbird.android.sample.user.UserHome;
import com.sendbird.android.sample.user.UserLogin;

public class SplashActivity extends AppCompatActivity {
    private FirebaseAuth firebaseAuth;
    private static int SPLASH_TIME_OUT = 4000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        firebaseAuth = FirebaseAuth.getInstance();

        new Handler().postDelayed(new Runnable(){

            @Override
            public void run() {
                if(firebaseAuth.getCurrentUser()!=null && firebaseAuth.getCurrentUser().getDisplayName()!=null && firebaseAuth.getCurrentUser().getPhotoUrl()!=null){
                    startActivity(new Intent(SplashActivity.this, UserHome.class));
                    finish();
                }else{
                    Intent intent = new Intent(SplashActivity.this, UserLogin.class);
                    startActivity(intent);
                    finish();
                }
            }
        }, SPLASH_TIME_OUT);
    }
}
