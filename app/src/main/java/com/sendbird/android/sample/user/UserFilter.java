package com.sendbird.android.sample.user;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.sendbird.android.sample.R;

public class UserFilter extends AppCompatActivity {
    RadioButton rbtnFirst, rbtnSecond, rbtnThird, rbtnFourth, rbtnFifth;
    TextView btnSubmit;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_filter);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        rbtnFirst = findViewById(R.id.rbtnFirst2);
        rbtnSecond = findViewById(R.id.rbtnSecond3);
        rbtnThird = findViewById(R.id.rbtnThird1);
        rbtnFourth = findViewById(R.id.rbtnFourh1);
        rbtnFifth = findViewById(R.id.rbtnFifth2);
        btnSubmit = findViewById(R.id.btnSubmit);

        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkAnswers();
            }
        });
    }

    private void checkAnswers() {
        if(rbtnFirst.isChecked() && rbtnSecond.isChecked() && rbtnThird.isChecked() && rbtnFourth.isChecked() && rbtnFifth.isChecked()){
            AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);
            alertDialog.setMessage("Congratulations! You may now register on this App!")
                    .setTitle("Success")
                    .setPositiveButton("Okay", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.cancel();
                            finish();
                            startActivity(new Intent(getApplicationContext(), UserRegister.class));
                        }
                    });
            alertDialog.show();
        }else{
            AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);
            alertDialog.setMessage("Sorry but you are not fit to use this App, this app will now close")
                    .setTitle("Failed")
                    .setPositiveButton("Okay", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.cancel();
                            System.exit(0);
                        }
                    });
            alertDialog.show();
        }


    }
}
