package com.sendbird.android.sample.user;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.sendbird.android.sample.R;

public class UserLogin extends AppCompatActivity implements View.OnClickListener{
    private FirebaseAuth firebaseAuth;
    private TextInputLayout txtemail,txtpassw;
    private Button btnLogin, btnRegister;
    private ProgressDialog progressDialog;
    private TextView txtforgot;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_login);
        firebaseAuth = FirebaseAuth.getInstance();

        progressDialog = new ProgressDialog(this);
        btnLogin = (Button)findViewById(R.id.btnLogin);
        btnRegister = (Button)findViewById(R.id.btnRegister);
        txtemail = findViewById(R.id.txtemail);
        txtpassw = findViewById(R.id.txtpassw);
        txtforgot = findViewById(R.id.txtForgot);

        btnLogin.setOnClickListener(this);

        txtforgot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(UserLogin.this, UserForgotPass.class));
            }
        });
    }

    public void goRegister(View v){
        Intent i = new Intent(this, UserRegister.class);
        startActivity(i);
    }

    @Override
    public void onClick(View v) {
        if(v == btnLogin){
            userLogin();
        }
    }

    private void userLogin(){
        String email = txtemail.getEditText().getText().toString().trim();
        String passw = txtpassw.getEditText().getText().toString().trim();

        if(TextUtils.isEmpty(email)){
            txtemail.setError("Please enter email");
            txtemail.requestFocus();
            return;
        }
        if(TextUtils.isEmpty(passw)){
            txtpassw.setError("Please enter password");
            txtpassw.requestFocus();
            return;
        }

        progressDialog.setMessage("Logging in...");
        progressDialog.show();

        firebaseAuth.signInWithEmailAndPassword(email,passw)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        progressDialog.dismiss();
                        if(task.isSuccessful()){
                            finish();
                            startActivity(new Intent(UserLogin.this, UserHome.class));
                        }
                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(UserLogin.this,"Invalid username or password",Toast.LENGTH_SHORT).show();
            }
        });
    }
}
