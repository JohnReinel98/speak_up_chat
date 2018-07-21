package com.sendbird.android.sample.user;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.sendbird.android.sample.R;

public class UserForgotPass extends AppCompatActivity {
    private Button btnPassReset;
    private TextInputLayout txtEmail;
    private FirebaseAuth firebaseAuth;
    private ProgressDialog progressDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_forgot_pass);
        btnPassReset = findViewById(R.id.btnPassReset);
        txtEmail = findViewById(R.id.txtemail);
        firebaseAuth = FirebaseAuth.getInstance();

        progressDialog = new ProgressDialog(this);

        btnPassReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String userEmail = txtEmail.getEditText().getText().toString().trim();
                if(TextUtils.isEmpty(userEmail)){
                    txtEmail.setError("Please enter email");
                    return;
                }
                if(!Patterns.EMAIL_ADDRESS.matcher(userEmail).matches()){
                    txtEmail.setError("Invalid email");
                    txtEmail.requestFocus();
                    return;
                }
                progressDialog.setMessage("Sending reset email");
                progressDialog.show();
                firebaseAuth.sendPasswordResetEmail(userEmail).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if(task.isSuccessful()){
                            progressDialog.dismiss();
                            Toast.makeText(UserForgotPass.this, "Password reset email sent",Toast.LENGTH_SHORT).show();
                            finish();
                            startActivity(new Intent(UserForgotPass.this, UserLogin.class));
                        }
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(UserForgotPass.this, e.toString() ,Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
    }
}
