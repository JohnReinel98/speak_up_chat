package com.sendbird.android.sample.user;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.sendbird.android.sample.R;
import com.sendbird.android.sample.utils.SharedPrefManager;

import java.io.IOException;
import java.util.Calendar;
import java.util.Date;

import de.hdodenhof.circleimageview.CircleImageView;

public class UserRegister2 extends AppCompatActivity implements View.OnClickListener{
    //private static final int PICK_IMAGE_REQUEST = 234;
    private static final int CHOOSE_IMAGE = 101;
    private Button btnLogout, btnChoose;
    private TextView useremail, btnSave;
    private FirebaseAuth firebaseAuth;
    private TextInputLayout txtusern, txtpassw, txtFname, txtLname, txtMI, txtemail, txtScrtAns, txtStreet, txtCity, txtProv, txtBirthday;
    private RadioButton rbtnMale, rbtnFemale, rbtnUser, rbtnDoctor;
    private ProgressDialog progressDialog;
    private String gender, userType;
    private CircleImageView imgView;
    private StorageReference storageReference;
    private int year_x,month_x,day_x;
    private String[] monthStr = {"January","February","March","April","May","June","July","August","September","October","November","December"};
    private static final int DIALOG_ID = 0;
    private Uri uriprofileImage;
    private String profileImageUrl;
    private DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_register2);
        firebaseAuth = FirebaseAuth.getInstance();

        final Calendar cal = Calendar.getInstance();
        year_x = cal.get(Calendar.YEAR);
        month_x = cal.get(Calendar.MONTH);
        day_x = cal.get(Calendar.DAY_OF_MONTH);

        showDialogOnClick();

        progressDialog = new ProgressDialog(this);

        storageReference = FirebaseStorage.getInstance().getReference();

        FirebaseUser user = firebaseAuth.getCurrentUser();
        databaseReference = FirebaseDatabase.getInstance().getReference();

        btnChoose = findViewById(R.id.btnChoose);
        imgView = findViewById(R.id.imgView);


        txtLname = findViewById(R.id.txtLname);
        txtFname = findViewById(R.id.txtFname);
        txtMI = findViewById(R.id.txtMI);
        txtStreet = findViewById(R.id.txtStreet);
        txtCity = findViewById(R.id.txtCity);
        txtProv = findViewById(R.id.txtProv);

        btnSave = findViewById(R.id.btnSave);

        txtBirthday = findViewById(R.id.txtBirthday);

        rbtnMale = findViewById(R.id.rbtnMale);
        rbtnFemale = findViewById(R.id.rbtnFemale);

        /*rbtnUser = findViewById(R.id.rbtnUser);
        rbtnDoctor = findViewById(R.id.rbtnDoctor);*/

        rbtnMale.setChecked(true);
        gender="Male";

        btnSave.setOnClickListener(this);
        btnChoose.setOnClickListener(this);
    }

    private void saveUserInfo(){
        final String fname = txtFname.getEditText().getText().toString().trim();
        final String lname = txtLname.getEditText().getText().toString().trim();
        final String mname = txtMI.getEditText().getText().toString().trim();
        final String street = txtStreet.getEditText().getText().toString().trim();
        final String city = txtCity.getEditText().getText().toString().trim();
        final String province = txtProv.getEditText().getText().toString().trim();
        final String birthday = txtBirthday.getEditText().getText().toString().trim();
        final String server = "", joined = "false";

        if (TextUtils.isEmpty(lname)) {
            txtLname.setError("Please enter last name");
            txtLname.requestFocus();
            return;
        }
        if (TextUtils.isEmpty(fname)) {
            txtFname.setError("Please enter first name");
            txtFname.requestFocus();
            return;
        }
        if (TextUtils.isEmpty(mname)) {
            txtMI.setError("Please enter middle initial");
            txtMI.requestFocus();
            return;
        }
        if (TextUtils.isEmpty(street)) {
            txtStreet.setError("Please enter street address");
            txtStreet.requestFocus();
            return;
        }
        if (TextUtils.isEmpty(city)) {
            txtStreet.setError("Please enter city address");
            txtCity.requestFocus();
            return;
        }
        if (TextUtils.isEmpty(province)) {
            txtProv.setError("Please enter province address");
            txtProv.requestFocus();
            return;
        }
        if (TextUtils.isEmpty(birthday)) {
            txtBirthday.setError("Please enter birthday");
            txtBirthday.requestFocus();
            return;
        }
        if(uriprofileImage==null){
            Toast.makeText(UserRegister2.this,"Please enter an image",Toast.LENGTH_SHORT).show();
            return;
        }
        progressDialog.setTitle("Uploading Information...");
        progressDialog.show();
        progressDialog.setCancelable(false);
        uploadImage();
    }

    public void showDialogOnClick(){
        txtBirthday = findViewById(R.id.txtBirthday);
        txtBirthday.getEditText().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialog(DIALOG_ID);
            }
        });
    }

    @Override
    protected Dialog onCreateDialog(int id){
        if(id == DIALOG_ID){
            DatePickerDialog datePickerDialog = new DatePickerDialog(this, dpickerListener, year_x,month_x,day_x);
            datePickerDialog.getDatePicker().setMaxDate(new Date().getTime());
            return datePickerDialog;
        }else{
            return null;
        }
    }

    private DatePickerDialog.OnDateSetListener dpickerListener = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
            year_x = year;
            month_x = month;
            day_x = dayOfMonth;
            txtBirthday.getEditText().setText(monthStr[month_x] + " " + day_x + ", " + year_x);
        }
    };

    public void genderClick(View view) {
        // Is the button now checked?
        boolean checked = ((RadioButton) view).isChecked();

        // Check which radio button was clicked
        switch(view.getId()) {
            case R.id.rbtnMale:
                if (checked)
                    gender = "Male";
                break;
            case R.id.rbtnFemale:
                if (checked)
                    gender = "Female";
                break;
        }
    }

    /*public void userClick(View view) {
        boolean checked = ((RadioButton) view).isChecked();
        switch (view.getId()) {
            case R.id.rbtnUser:
                if (checked)
                    userType = "false";
                break;
            case R.id.rbtnDoctor:
                if (checked)
                    userType = "true";
                break;
        }
    }*/

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == CHOOSE_IMAGE && resultCode == RESULT_OK && data != null && data.getData() != null){
            uriprofileImage = data.getData();

            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), uriprofileImage);
                imgView.setImageBitmap(bitmap);

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void uploadImage() {
        final String fname = txtFname.getEditText().getText().toString().trim();
        final String lname = txtLname.getEditText().getText().toString().trim();
        final String mname = txtMI.getEditText().getText().toString().trim();
        final String street = txtStreet.getEditText().getText().toString().trim();
        final String city = txtCity.getEditText().getText().toString().trim();
        final String province = txtProv.getEditText().getText().toString().trim();
        final String birthday = txtBirthday.getEditText().getText().toString().trim();
        final String server = "", joined = "false", rating_total = "0.0", rooms_total = "0", reports = "0";

        final StorageReference profileImage = FirebaseStorage.getInstance().getReference("profilePics/"+System.currentTimeMillis()+".jpg");
        if(uriprofileImage != null){
            profileImage.putFile(uriprofileImage).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    profileImage.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                        @Override
                        public void onSuccess(Uri uri) {
                            final Uri downloadUrl = uri;
                            profileImageUrl = downloadUrl.toString();
                            FirebaseUser currentUser = firebaseAuth.getCurrentUser();
                            UserProfileChangeRequest profileChangeRequest = new UserProfileChangeRequest.Builder()
                                    .setDisplayName(lname+", "+fname+" "+mname+".")
                                    .setPhotoUri(downloadUrl)
                                    .build();
                            currentUser.updateProfile(profileChangeRequest)
                                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {
                                    int depTest = 0;
                                    User user = new User(fname,lname,mname,birthday,gender,"false",street,city,province,String.valueOf(depTest),server,joined,rating_total,rooms_total,reports);
                                    FirebaseUser currentUser = firebaseAuth.getCurrentUser();
                                    databaseReference.child(currentUser.getUid()).setValue(user);
                                    SharedPrefManager.getInstance(getApplicationContext()).userId(fname);
                                    SharedPrefManager.getInstance(getApplicationContext()).userNickname(fname);
                                    //Toast.makeText(UserRegister2.this,"Information Saved",Toast.LENGTH_SHORT).show();


                                    //show instructions
                                    AlertDialog.Builder alertDialog = new AlertDialog.Builder(UserRegister2.this);
                                    View view = LayoutInflater.from(UserRegister2.this).inflate(R.layout.question_instructions_layout,null);
                                    alertDialog
                                            .setCancelable(false)
                                            .setPositiveButton("Okay", new DialogInterface.OnClickListener() {
                                                @Override
                                                public void onClick(DialogInterface dialog, int which) {
                                                    dialog.cancel();
                                                    finish();
                                                    startActivity(new Intent(UserRegister2.this, UserQuestion.class));
                                                }
                                            });
                                    alertDialog.setView(view);
                                    alertDialog.show();

                                }
                            }).addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    progressDialog.dismiss();
                                    Toast.makeText(UserRegister2.this,e.getMessage(),Toast.LENGTH_LONG).show();
                                }
                            });
                        }
                    });

                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception exception) {
                    progressDialog.dismiss();
                    Toast.makeText(UserRegister2.this,exception.getMessage(),Toast.LENGTH_LONG).show();
                }
            })
            .addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                    double progress = (100.0 * taskSnapshot.getBytesTransferred())/taskSnapshot.getTotalByteCount();
                    progressDialog.setMessage(((int) progress) + "% Uploaded");
                }
            });
        }
    }

    private void imageChooser (){
        Intent i = new Intent();
        i.setType("image/*");
        i.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(i,"Select an Image"),CHOOSE_IMAGE);
    }

    @Override
    public void onClick(View v) {
        if(v == btnLogout){
            firebaseAuth.signOut();
            finish();
            startActivity(new Intent(this, UserLogin.class));
        }

        if(v == btnSave){
            saveUserInfo();
        }

        if(v == btnChoose){
            imageChooser();
        }
    }
}
