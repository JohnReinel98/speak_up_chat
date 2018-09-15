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
    RadioButton rbtnFirst1, rbtnFirst2,rbtnFirst3,
                rbtnSecond1,rbtnSecond2,rbtnSecond3,
                rbtnThird1,rbtnThird2,rbtnThird3,
                rbtnFourth1,rbtnFourth2,rbtnFourth3,
                rbtnFifth1,rbtnFifth2,rbtnFifth3;
    TextView btnSubmit;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_filter);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        rbtnFirst1 = findViewById(R.id.rbtnFirst1);
        rbtnFirst2 = findViewById(R.id.rbtnFirst2);
        rbtnFirst3 = findViewById(R.id.rbtnFirst3);

        rbtnSecond1 = findViewById(R.id.rbtnSecond1);
        rbtnSecond2 = findViewById(R.id.rbtnSecond2);
        rbtnSecond3 = findViewById(R.id.rbtnSecond3);

        rbtnThird1 = findViewById(R.id.rbtnThird1);
        rbtnThird2 = findViewById(R.id.rbtnThird2);
        rbtnThird3 = findViewById(R.id.rbtnThird3);

        rbtnFourth1 = findViewById(R.id.rbtnFourh1);
        rbtnFourth2 = findViewById(R.id.rbtnFourh2);
        rbtnFourth3 = findViewById(R.id.rbtnFourh3);

        rbtnFifth1 = findViewById(R.id.rbtnFifth1);
        rbtnFifth2 = findViewById(R.id.rbtnFifth2);
        rbtnFifth3 = findViewById(R.id.rbtnFifth3);

        btnSubmit = findViewById(R.id.btnSubmit);

        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkAnswers();
            }
        });
    }

    private void checkAnswers() {
        if(rbtnFirst2.isChecked() && rbtnSecond3.isChecked() && rbtnThird1.isChecked() && rbtnFourth1.isChecked() && rbtnFifth2.isChecked()){
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
        }
        else if(!rbtnFirst1.isChecked() &&
                !rbtnFirst2.isChecked() &&
                !rbtnFirst3.isChecked() ||
                !rbtnSecond1.isChecked() &&
                !rbtnSecond2.isChecked() &&
                !rbtnSecond3.isChecked() ||
                !rbtnThird1.isChecked() &&
                !rbtnThird2.isChecked() &&
                !rbtnThird3.isChecked() ||
                !rbtnFourth1.isChecked() &&
                !rbtnFourth2.isChecked() &&
                !rbtnFourth3.isChecked() ||
                !rbtnFifth1.isChecked() &&
                !rbtnFifth2.isChecked() &&
                !rbtnFifth3.isChecked()){
            Toast.makeText(this,"Please answer all the questions", Toast.LENGTH_SHORT).show();
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
