package com.sendbird.android.sample.user;

import android.app.ActionBar;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Patterns;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.sendbird.android.sample.R;

public class UserRegister extends AppCompatActivity implements View.OnClickListener{
    private Button btnRegister;
    private TextInputLayout txtemail,txtpass, txtconfirmpassw;;
    private TextView linkSignin, linkTerms;
    private ProgressDialog progressDialog;
    private FirebaseAuth firebaseAuth;
    private CheckBox cbTerms;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_register);

        progressDialog = new ProgressDialog(this);
        firebaseAuth = FirebaseAuth.getInstance();


        cbTerms = findViewById(R.id.cbTerms);
        btnRegister = (Button)findViewById(R.id.btnReg);
        txtemail = findViewById(R.id.txtemail);
        txtpass = findViewById(R.id.txtpassw);
        txtconfirmpassw = findViewById(R.id.txtconfirmpassw);
        linkSignin = findViewById(R.id.linkSign);
        linkTerms = findViewById(R.id.txtTerms);

        btnRegister.setOnClickListener(this);


        linkTerms.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(UserRegister.this, TermsActivity.class));

            }
        });

        txtpass.getEditText().addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                txtconfirmpassw.setVisibility(View.VISIBLE);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        linkSignin.setOnClickListener(this);
    }





    @Override
    public void onClick(View v) {
        if(v == btnRegister){
            registerUser();
        }
        if(v == linkSignin){
            Login();
        }
    }


    private void registerUser(){
        String email = txtemail.getEditText().getText().toString().trim();
        String passw = txtpass.getEditText().getText().toString().trim();
        String confirmpassw = txtconfirmpassw.getEditText().getText().toString().trim();

        if(TextUtils.isEmpty(email)){
            txtemail.setError("Please enter email");
            txtemail.requestFocus();
            return;
        }
        if(passw.length() < 6){
            txtpass.setError("Password must be 6 letters or above");
            txtpass.requestFocus();
            return;
        }
        if(TextUtils.isEmpty(passw)){
            txtpass.setError("Please enter password");
            txtpass.requestFocus();
            //Stopping the function executing more
            return;
        }
        if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            txtemail.setError("Invalid email");
            txtemail.requestFocus();
            return;
        }
        if(!passw.equals(confirmpassw)){
            txtconfirmpassw.setError("Password does not match");
            txtconfirmpassw.requestFocus();
            return;
        }
        if(TextUtils.isEmpty(confirmpassw)){
            txtconfirmpassw.setError("Please enter confirm password");
            txtconfirmpassw.requestFocus();
            return;
        }
        if (!checkToggle()) {
            Toast.makeText(getApplicationContext(), "Please agree to the terms and conditions.", Toast.LENGTH_LONG).show();
        } else {
            progressDialog.setMessage("Registering user...");
            progressDialog.show();
            progressDialog.setCancelable(false);

            firebaseAuth.createUserWithEmailAndPassword(email,passw)
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if(task.isSuccessful()){
                                progressDialog.dismiss();
                                Intent i = new Intent(UserRegister.this, UserRegister2.class);
                                finish();
                                startActivity(i);
                            }else{
                                progressDialog.dismiss();
                                Toast.makeText(UserRegister.this,"Registration Failed, Please try again",Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
        }

    }

    public void Login(){
        Intent i = new Intent(UserRegister.this, UserLogin.class);
        finish();
        startActivity(i);
    }

    private boolean checkToggle() {
        return cbTerms.isChecked();
    }
}
