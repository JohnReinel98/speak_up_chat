package com.sendbird.android.sample.user;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.sendbird.android.SendBird;
import com.sendbird.android.sample.R;
import com.sendbird.android.sample.utils.PreferenceUtils;

public class Test extends AppCompatActivity {

    Button btnTests;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);

        btnTests = findViewById(R.id.btnTests);

        btnTests.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Test.this, UserLogin.class));
            }
        });
    }
}
