package com.sendbird.android.sample.user;

import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.TextView;

import com.sendbird.android.sample.R;

import org.w3c.dom.Text;

import java.util.Calendar;

public class ContactDoctorActivity extends AppCompatActivity {
    private String selectedGen;
    private TextInputLayout txtContactName, txtContactPhone, txtContactRelationship, txtFname, txtLname, txtMI, txtemail, txtScrtAns, txtStreet, txtCity, txtProv, txtBirthday;
    private String[] monthStr = {"January","February","March","April","May","June","July","August","September","October","November","December"};
    private int year_x,month_x,day_x;
    private RadioButton rbtnMale, rbtnFemale;
    private TextView btnSend;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact_doctor);

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

        btnSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Snackbar.make(v, "Test", Snackbar.LENGTH_LONG);
            }
        });

    }

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
}
