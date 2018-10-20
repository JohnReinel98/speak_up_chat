package com.sendbird.android.sample.user;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.sendbird.android.sample.R;
import com.sendbird.android.sample.utils.SharedPrefManager;

import org.w3c.dom.Text;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class ContactDoctorActivity extends AppCompatActivity {
    private String selectedGen, doctor;
    private static final int DIALOG_ID = 0;
    private TextInputLayout txtContactName, txtContactPhone, txtContactRelationship, txtFname, txtLname, txtMI, txtemail, txtScrtAns, txtStreet, txtCity, txtProv, txtBirthday;
    private String[] monthStr = {"January","February","March","April","May","June","July","August","September","October","November","December"};
    private int year_x,month_x,day_x;
    private RadioButton rbtnMale, rbtnFemale;
    private TextView btnSend;
    private ProgressDialog progressDialog;
    private FirebaseAuth firebaseAuth;
    private DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact_doctor);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        firebaseAuth = FirebaseAuth.getInstance();
        databaseReference = FirebaseDatabase.getInstance().getReference("Doctors");
        progressDialog = new ProgressDialog(this);

        Intent i = getIntent();
        doctor = i.getStringExtra("doctor");

        txtLname = findViewById(R.id.txtLname);
        txtFname = findViewById(R.id.txtFname);
        txtMI = findViewById(R.id.txtMI);
        txtStreet = findViewById(R.id.txtStreet);
        txtCity = findViewById(R.id.txtCity);
        txtProv = findViewById(R.id.txtProv);
        txtContactName = findViewById(R.id.txtContactName);
        txtContactPhone = findViewById(R.id.txtContactPhone);
        txtContactRelationship = findViewById(R.id.txtContactRelationShip);
        txtBirthday = findViewById(R.id.txtBirthday);
        rbtnMale = findViewById(R.id.rbtnMale);
        rbtnFemale = findViewById(R.id.rbtnFemale);

        btnSend = findViewById(R.id.btnSend);

        final Calendar cal = Calendar.getInstance();
        year_x = cal.get(Calendar.YEAR);
        month_x = cal.get(Calendar.MONTH);
        day_x = cal.get(Calendar.DAY_OF_MONTH);

        if(rbtnFemale.isSelected()){
            selectedGen = "Female";
        }
        if(rbtnMale.isSelected()){
            selectedGen = "Male";
        }

        showDialogOnClick();

        btnSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                validateInfo();
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

    private void validateInfo(){
        final String name = firebaseAuth.getCurrentUser().getDisplayName();
        /*final String fname = txtFname.getEditText().getText().toString().trim();
        final String lname = txtLname.getEditText().getText().toString().trim();
        final String mname = txtMI.getEditText().getText().toString().trim();*/
        final String street = txtStreet.getEditText().getText().toString().trim();
        final String city = txtCity.getEditText().getText().toString().trim();
        final String province = txtProv.getEditText().getText().toString().trim();
        final String birthday = txtBirthday.getEditText().getText().toString().trim();
        final String contact_name = txtContactName.getEditText().getText().toString().trim();
        final String contact_phone = txtContactPhone.getEditText().getText().toString().trim();
        final String contact_relationship = txtContactRelationship.getEditText().getText().toString().trim();

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
        if (selectedGen == null) {
            Toast.makeText(getApplicationContext(), "Please choose a gender.", Toast.LENGTH_LONG).show();
            return;
        }

        progressDialog.setMessage("Sending Information...");
        progressDialog.show();
        progressDialog.setCancelable(false);

        saveUser(name, street, city, province, birthday, selectedGen, contact_name, contact_phone, contact_relationship);

    }

    private void saveUser(final String name,  final String street, final String city, final String province,
                          final String birthday, final String gender, final String contact_name, final String contact_phone, final String contact_relationship) {
        FirebaseUser currentUser = firebaseAuth.getCurrentUser();
        String sender = currentUser.getDisplayName().replace(".", "");

        Date date = new Date();
        String strDateFormat = "MMMM-dd-YYYY hh:mm:ss a";
        DateFormat dateFormat = new SimpleDateFormat(strDateFormat);
        final String formattedDate = dateFormat.format(date);

        /*final DatabaseReference refLname = databaseReference.child("Messages").child(doctor).child(sender).child("lname");
        refLname.keepSynced(true);
        refLname.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                refLname.setValue(lname);
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        final DatabaseReference refFname = databaseReference.child("Messages").child(doctor).child(sender).child("fname");
        refFname.keepSynced(true);
        refFname.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                refFname.setValue(fname);
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });*/

        final DatabaseReference refName = databaseReference.child("Messages").child(doctor).child(sender).child("name");
        refName.keepSynced(true);
        refName.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                refName.setValue(name);
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        final DatabaseReference refBday = databaseReference.child("Messages").child(doctor).child(sender).child("bday");
        refBday.keepSynced(true);
        refBday.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                refBday.setValue(birthday);
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        final DatabaseReference refGender = databaseReference.child("Messages").child(doctor).child(sender).child("gender");
        refGender.keepSynced(true);
        refGender.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                refGender.setValue(gender);
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        final DatabaseReference refStreet = databaseReference.child("Messages").child(doctor).child(sender).child("street");
        refStreet.keepSynced(true);
        refStreet.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                refStreet.setValue(street);
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        final DatabaseReference refCity = databaseReference.child("Messages").child(doctor).child(sender).child("city");
        refCity.keepSynced(true);
        refCity.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                refCity.setValue(city);
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        final DatabaseReference refProv = databaseReference.child("Messages").child(doctor).child(sender).child("province");
        refProv.keepSynced(true);
        refProv.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                refProv.setValue(province);
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        final DatabaseReference refCName = databaseReference.child("Messages").child(doctor).child(sender).child("contact_name");
        refCName.keepSynced(true);
        refCName.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                refCName.setValue(contact_name);
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        final DatabaseReference refCPhone = databaseReference.child("Messages").child(doctor).child(sender).child("contact_phone");
        refCPhone.keepSynced(true);
        refCName.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                refCPhone.setValue(contact_phone);
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        final DatabaseReference refCRelationShip = databaseReference.child("Messages").child(doctor).child(sender).child("contact_relationship");
        refCRelationShip.keepSynced(true);
        refCRelationShip.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                refCRelationShip.setValue(contact_relationship);
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        final DatabaseReference refDateTime = databaseReference.child("Messages").child(doctor).child(sender).child("dateTime");
        refDateTime.keepSynced(true);
        refDateTime.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                refDateTime.setValue(formattedDate);
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        progressDialog.dismiss();
        Toast.makeText(getApplicationContext(), "Information Sent, Please wait for your doctor to message you.", Toast.LENGTH_LONG).show();
        showSuccessDialog();

    }

    private void showSuccessDialog() {
        //show instructions
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(ContactDoctorActivity.this);
        View view = LayoutInflater.from(ContactDoctorActivity.this).inflate(R.layout.question_instructions_layout,null);
        alertDialog
                .setCancelable(false)
                .setPositiveButton("Okay", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        finish();
                        startActivity(new Intent(ContactDoctorActivity.this, UserQuestion.class));
                    }
                });
        alertDialog.setView(view);
        alertDialog.show();
        /*AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);
        alertDialog.setMessage("Information Sent, Please wait for your doctor to message you.")
                .setTitle("Success")
                .setCancelable(false)
                .setPositiveButton("Done", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        finish();
                        startActivity(new Intent(ContactDoctorActivity.this, UserQuestion.class));
                    }
                });
        alertDialog.show();*/
    }
}
