package com.sendbird.android.sample.user;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.sendbird.android.sample.R;

import java.io.IOException;
import java.util.Calendar;
import java.util.Date;

import de.hdodenhof.circleimageview.CircleImageView;

public class UserUpdateProfile extends AppCompatActivity {
    //private static final int PICK_IMAGE_REQUEST = 234;
    private static final int CHOOSE_IMAGE = 101;
    private Button btnLogout, btnBack, btnChoose, btnUpload;
    private TextView useremail, btnUpdate;
    private FirebaseAuth firebaseAuth;
    private TextInputLayout txtusern, txtpassw, txtFname, txtLname, txtMI, txtemail, txtScrtAns, txtStreet, txtCity, txtProv, txtBirthday;
    private Spinner spnScrtQues;
    private RadioButton rbtnMale, rbtnFemale;
    private ProgressDialog progressDialog;
    private String gender, userType;
    private ImageView imgView2;
    private CircleImageView imgView;
    private Uri filepath;
    private StorageReference storageReference;
    String profileImgUrl;
    private CircleImageView imgProfilePic;
    private String id;
    String selectedGen;
    private int year_x,month_x,day_x;
    private String[] monthStr = {"January","February","March","April","May","June","July","August","September","October","November","December"};
    private static final int DIALOG_ID = 0;
    private String sharedUri;
    private Uri uriHolder;
    private int depTestVar, rooms_total, reports;
    private Uri uriprofileImage;
    private String profileImageUrl, joined, server, isDoctor, contact_name, contact_phone, contact_relationship;
    private float rating_total;

    private DatabaseReference databaseReference;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_update_profile);
        firebaseAuth = FirebaseAuth.getInstance();
        databaseReference = FirebaseDatabase.getInstance().getReference();

        final Calendar cal = Calendar.getInstance();
        year_x = cal.get(Calendar.YEAR);
        month_x = cal.get(Calendar.MONTH);
        day_x = cal.get(Calendar.DAY_OF_MONTH);

        showDialogOnClick();
        getJoined();
        getReports();
        getServer();
        getRating();
        getRooms();
        getisDoctor();
        getCName();
        getCPhone();
        getCRelationship();

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        storageReference = FirebaseStorage.getInstance().getReference();
        progressDialog = new ProgressDialog(this);

        btnChoose = findViewById(R.id.btnChoose);
        imgProfilePic = findViewById(R.id.imgView);

        txtLname = findViewById(R.id.txtLname);
        txtFname = findViewById(R.id.txtFname);
        txtMI = findViewById(R.id.txtMI);
        txtStreet = findViewById(R.id.txtStreet);
        txtCity = findViewById(R.id.txtCity);
        txtProv = findViewById(R.id.txtProv);

        btnUpdate = findViewById(R.id.btnUpdate);
        btnChoose = findViewById(R.id.btnChoose);

        txtBirthday = findViewById(R.id.txtBirthday);

        rbtnMale = findViewById(R.id.rbtnMale);
        rbtnFemale = findViewById(R.id.rbtnFemale);

        SharedPreferences mPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        SharedPreferences.Editor mEditor = mPreferences.edit();

        String sharedUri = mPreferences.getString("keyUri","");
        uriHolder = Uri.parse(sharedUri);

        btnChoose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showFileChooser();
            }
        });


        if(rbtnFemale.isSelected()){
            selectedGen = "Female";
        }
        if(rbtnMale.isSelected()){
            selectedGen = "Male";
        }

        loadUserInfo();

        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String lname = txtLname.getEditText().getText().toString().trim();
                final String fname = txtFname.getEditText().getText().toString().trim();
                final String mname = txtMI.getEditText().getText().toString().trim();
                final String bday = txtBirthday.getEditText().getText().toString().trim();
                final String street = txtStreet.getEditText().getText().toString().trim();
                final String city = txtCity.getEditText().getText().toString().trim();
                final String prov = txtProv.getEditText().getText().toString().trim();

                if (TextUtils.isEmpty(lname)) {
                    txtLname.setError("Please enter first name");
                    txtLname.requestFocus();
                    return;
                }
                if (TextUtils.isEmpty(fname)) {
                    txtFname.setError("Please enter last name");
                    txtFname.requestFocus();
                    return;
                }
                if (TextUtils.isEmpty(mname)) {
                    txtMI.setError("Please enter middle name");
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
                if (TextUtils.isEmpty(prov)) {
                    txtProv.setError("Please enter province address");
                    txtProv.requestFocus();
                    return;
                }
                if (TextUtils.isEmpty(bday)) {
                    txtBirthday.setError("Please enter birthday");
                    txtBirthday.requestFocus();
                    return;
                }
                if(uriprofileImage == null){
                    updateUserProfile();
                }else{
                    uploadImage();
                }
            }
        });
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
                    selectedGen = "Male";
                break;
            case R.id.rbtnFemale:
                if (checked)
                    selectedGen = "Female";
                break;
        }
    }

    private void loadUserInfo() {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        System.out.println(user);
        Glide.with(this)
                .load(user.getPhotoUrl().toString())
                .into(imgProfilePic);
        String name = user.getDisplayName();


        firebaseAuth = FirebaseAuth.getInstance();
        id = firebaseAuth.getCurrentUser().getUid();

        final FirebaseDatabase database = FirebaseDatabase.getInstance();

        DatabaseReference refProf = database.getReference(id).child("profielImgUrl");

        refProf.addValueEventListener(new com.google.firebase.database.ValueEventListener() {
            @Override
            public void onDataChange(com.google.firebase.database.DataSnapshot dataSnapshot) {
                String updateUser = dataSnapshot.getValue(String.class);
                profileImgUrl = updateUser;
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        DatabaseReference refFname = database.getReference(id).child("fname");

        refFname.addValueEventListener(new com.google.firebase.database.ValueEventListener() {
            @Override
            public void onDataChange(com.google.firebase.database.DataSnapshot dataSnapshot) {
                String updateUser = dataSnapshot.getValue(String.class);
                txtFname.getEditText().setText(updateUser);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        DatabaseReference refLname = database.getReference(id).child("lname");

        refLname.addValueEventListener(new com.google.firebase.database.ValueEventListener() {
            @Override
            public void onDataChange(com.google.firebase.database.DataSnapshot dataSnapshot) {
                String updateUser = dataSnapshot.getValue(String.class);
                txtLname.getEditText().setText(updateUser);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        DatabaseReference refMname = database.getReference(id).child("mname");

        refMname.addValueEventListener(new com.google.firebase.database.ValueEventListener() {
            @Override
            public void onDataChange(com.google.firebase.database.DataSnapshot dataSnapshot) {
                String updateUser = dataSnapshot.getValue(String.class);
                txtMI.getEditText().setText(updateUser);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        DatabaseReference refBday = database.getReference(id).child("birthday");

        refBday.addValueEventListener(new com.google.firebase.database.ValueEventListener() {
            @Override
            public void onDataChange(com.google.firebase.database.DataSnapshot dataSnapshot) {
                String updateUser = dataSnapshot.getValue(String.class);
                txtBirthday.getEditText().setText(updateUser);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        DatabaseReference refGender = database.getReference(id).child("gender");

        refGender.addValueEventListener(new com.google.firebase.database.ValueEventListener() {
            @Override
            public void onDataChange(com.google.firebase.database.DataSnapshot dataSnapshot) {
                String updateUser = dataSnapshot.getValue(String.class);
                if(updateUser.equals("Male")){
                    selectedGen = "Male";
                    rbtnMale.setChecked(true);
                }else{
                    rbtnFemale.setChecked(true);
                    selectedGen = "Female";
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        DatabaseReference refStreet = database.getReference(id).child("street");

        refStreet.addValueEventListener(new com.google.firebase.database.ValueEventListener() {
            @Override
            public void onDataChange(com.google.firebase.database.DataSnapshot dataSnapshot) {
                String updateUser = dataSnapshot.getValue(String.class);
                txtStreet.getEditText().setText(updateUser);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        DatabaseReference refCity = database.getReference(id).child("city");

        refCity.addValueEventListener(new com.google.firebase.database.ValueEventListener() {
            @Override
            public void onDataChange(com.google.firebase.database.DataSnapshot dataSnapshot) {
                String updateUser = dataSnapshot.getValue(String.class);
                txtCity.getEditText().setText(updateUser);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        DatabaseReference refProv = database.getReference(id).child("province");

        refProv.addValueEventListener(new com.google.firebase.database.ValueEventListener() {
            @Override
            public void onDataChange(com.google.firebase.database.DataSnapshot dataSnapshot) {
                String updateUser = dataSnapshot.getValue(String.class);
                txtProv.getEditText().setText(updateUser);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    private void updateUserProfile(){
        final String lname = txtLname.getEditText().getText().toString().trim();
        final String fname = txtFname.getEditText().getText().toString().trim();
        final String mname = txtMI.getEditText().getText().toString().trim();
        final String bday = txtBirthday.getEditText().getText().toString().trim();
        final String street = txtStreet.getEditText().getText().toString().trim();
        final String city = txtCity.getEditText().getText().toString().trim();
        final String prov = txtProv.getEditText().getText().toString().trim();
        final String rooms = String.valueOf(rooms_total);
        final String rating = String.valueOf(rating_total);
        final String no_reports = String.valueOf(reports);

        progressDialog.setTitle("Updating Information...");
        progressDialog.setMessage("Saving User Data...");
        progressDialog.show();
        progressDialog.setCancelable(false);

        final FirebaseUser currentUser = firebaseAuth.getCurrentUser();
        UserProfileChangeRequest profileChangeRequest = new UserProfileChangeRequest.Builder()
                .setDisplayName(lname + ", " + fname + " " + mname + ".")
                .build();
        currentUser.updateProfile(profileChangeRequest).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                int depTest = 0;
                User user = new User(fname, lname, mname, bday, selectedGen, isDoctor, street, city, prov, String.valueOf(depTest), server, joined, rating, rooms, no_reports, contact_name, contact_phone, contact_relationship);
                databaseReference.child(currentUser.getUid()).setValue(user);
                progressDialog.dismiss();
                finish();
                //startActivity(new Intent(UserUpdateProfile.this, UserProfile.class));
                startActivity(new Intent(UserUpdateProfile.this, UserHome.class));
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                progressDialog.dismiss();
                Toast.makeText(UserUpdateProfile.this,e.getMessage(),Toast.LENGTH_LONG).show();
            }
        });
    }

    private void uploadImage() {
        final String lname = txtLname.getEditText().getText().toString().trim();
        final String fname = txtFname.getEditText().getText().toString().trim();
        final String mname = txtMI.getEditText().getText().toString().trim();
        final String bday = txtBirthday.getEditText().getText().toString().trim();
        final String street = txtStreet.getEditText().getText().toString().trim();
        final String city = txtCity.getEditText().getText().toString().trim();
        final String prov = txtProv.getEditText().getText().toString().trim();
        final String rooms = String.valueOf(rooms_total);
        final String rating = String.valueOf(rating_total);
        final String no_reports = String.valueOf(reports);

        progressDialog.setTitle("Updating Information...");
        progressDialog.show();
        progressDialog.setCancelable(false);
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
                            currentUser.updateProfile(profileChangeRequest).addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {
                                    int depTest = 0;
                                    User user = new User(fname,lname,mname,bday,selectedGen,isDoctor,street,city,prov,String.valueOf(depTest),server,joined,rating,rooms,no_reports, contact_name, contact_phone, contact_relationship);
                                    FirebaseUser currentUser = firebaseAuth.getCurrentUser();
                                    databaseReference.child(currentUser.getUid()).setValue(user);
                                    progressDialog.dismiss();
                                    finish();
                                    startActivity(new Intent(UserUpdateProfile.this, UserHome.class));
                                }
                            }).addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    progressDialog.dismiss();
                                    Toast.makeText(UserUpdateProfile.this,e.getMessage(),Toast.LENGTH_LONG).show();
                                }
                            });
                        }
                    });

                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception exception) {
                    progressDialog.dismiss();
                    Toast.makeText(UserUpdateProfile.this,exception.getMessage(),Toast.LENGTH_LONG).show();
                }
            })
            .addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                    double progress = (100.0 * taskSnapshot.getBytesTransferred())/taskSnapshot.getTotalByteCount();
                    progressDialog.setMessage(((int) progress) + "% Updated");
                }
            });
        }
    }

    private void showFileChooser(){
        Intent i = new Intent();
        //you can only select image
        i.setType("image/*");
        i.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(i, "Select an Image"), CHOOSE_IMAGE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == CHOOSE_IMAGE && resultCode == RESULT_OK && data != null && data.getData() != null){
            uriprofileImage = data.getData();

            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), uriprofileImage);
                imgProfilePic.setImageBitmap(bitmap);

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void getServer(){
        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        String id = firebaseAuth.getCurrentUser().getUid();
        DatabaseReference refServer = database.getReference(id).child("server");
        refServer.keepSynced(true);
        refServer.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                server = dataSnapshot.getValue(String.class);
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    private void getReports(){
        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        String id = firebaseAuth.getCurrentUser().getUid();
        DatabaseReference refProv = database.getReference(id).child("reports");
        refProv.keepSynced(true);
        refProv.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                reports = Integer.parseInt(dataSnapshot.getValue(String.class));
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    private void getRooms(){
        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        String id = firebaseAuth.getCurrentUser().getUid();
        DatabaseReference refProv = database.getReference(id).child("rooms_total");
        refProv.keepSynced(true);
        refProv.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                rooms_total = Integer.parseInt(dataSnapshot.getValue(String.class));
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    private void getRating(){
        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        String id = firebaseAuth.getCurrentUser().getUid();
        DatabaseReference refProv = database.getReference(id).child("rating_total");
        refProv.keepSynced(true);
        refProv.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                rating_total = Float.parseFloat(dataSnapshot.getValue(String.class));
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    private void getJoined(){
        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        String id = firebaseAuth.getCurrentUser().getUid();
        DatabaseReference refServer = database.getReference(id).child("joined");
        refServer.keepSynced(true);
        refServer.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                joined = dataSnapshot.getValue(String.class);
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    private void getisDoctor(){
        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        String id = firebaseAuth.getCurrentUser().getUid();
        DatabaseReference refisDoctor = database.getReference(id).child("isDoctor");
        refisDoctor.keepSynced(true);
        refisDoctor.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                isDoctor = dataSnapshot.getValue(String.class);
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    private void getCName () {
        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        String id = firebaseAuth.getCurrentUser().getUid();
        DatabaseReference refCName = database.getReference(id).child("contact_name");
        refCName.keepSynced(true);
        refCName.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                contact_name = dataSnapshot.getValue(String.class);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    private void getCPhone () {
        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        String id = firebaseAuth.getCurrentUser().getUid();
        DatabaseReference refCPhone = database.getReference(id).child("contact_phone");
        refCPhone.keepSynced(true);
        refCPhone.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                contact_phone = dataSnapshot.getValue(String.class);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    private void getCRelationship () {
        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        String id = firebaseAuth.getCurrentUser().getUid();
        DatabaseReference refCRelationShip = database.getReference(id).child("contact_relationship");
        refCRelationShip.keepSynced(true);
        refCRelationShip.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                contact_relationship = dataSnapshot.getValue(String.class);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
}
