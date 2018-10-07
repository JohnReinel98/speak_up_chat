package com.sendbird.android.sample.user;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.sendbird.android.sample.R;

public class ChooseDoctorActivity extends AppCompatActivity {
    private Button btnDoctor1, btnDoctor2, btnDoctor3;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_doctor);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        btnDoctor1 = findViewById(R.id.btnDoctor1);
        btnDoctor2 = findViewById(R.id.btnDoctor2);
        btnDoctor3 = findViewById(R.id.btnDoctor3);

        btnDoctor1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ChooseDoctorActivity.this, ContactDoctorActivity.class);
                intent.putExtra("doctor", "Doctor1");
                startActivity(intent);
            }
        });

        btnDoctor2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ChooseDoctorActivity.this, ContactDoctorActivity.class);
                intent.putExtra("doctor", "Doctor2");
                startActivity(intent);
            }
        });

        btnDoctor3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ChooseDoctorActivity.this, ContactDoctorActivity.class);
                intent.putExtra("doctor", "Doctor3");
                startActivity(intent);
            }
        });
    }


}
