package com.example.hotelroom_booking;
import android.app.DatePickerDialog;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import android.text.InputType;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;
import java.util.regex.Pattern;

public class RegistrationActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

    }




    public void register(View view) {

        final EditText name = findViewById(R.id.editText1);
        final EditText username = findViewById(R.id.editText2);
        final EditText passEdit = findViewById(R.id.editText3);
        final RadioGroup radioGroup = findViewById(R.id.radioGroup);
        String name1 = name.getText().toString();
        String user = username.getText().toString();
        String pass = passEdit.getText().toString();
        int rCheckId = radioGroup.getCheckedRadioButtonId();
        if (rCheckId == -1) {
            Toast.makeText(getApplicationContext(), "Select the gender", Toast.LENGTH_SHORT).show();
        } else {
            RadioButton radioButon = findViewById(rCheckId);
            String gender = radioButon.getText().toString();
            MyDb myDb = new MyDb(this);
            SQLiteDatabase dbr = myDb.getReadableDatabase();
            Cursor c = dbr.rawQuery("SELECT * FROM Customer WHERE username = ?", new String[]{user});
            if (c.moveToFirst()) {
                Toast.makeText(getApplicationContext(), "This username already exists", Toast.LENGTH_SHORT).show();
            } else {
                SQLiteDatabase db = myDb.getWritableDatabase();
                ContentValues values = new ContentValues();
                values.put("name", name1);
                values.put("username", user);
                if (!isValidPassword(pass)) {
                    Toast.makeText(this, "Please enter a strong password of 8 characters or more", Toast.LENGTH_LONG).show();
                    return;
                }
                values.put("pass", pass);
                values.put("gender", gender);/*
            values.put("startdate",startDate);
            values.put("enddate",endDate);*/
                if (name1.trim().length() == 0 || user.trim().length() == 0 || pass.trim().length() == 0 || gender.matches("")) {
                    Toast.makeText(getApplicationContext(), "Enter the details", Toast.LENGTH_SHORT).show();
                } else {
                    db.insert("Customer", null, values);
                    Toast.makeText(this, "You have registered successfully!", Toast.LENGTH_LONG).show();
                    Intent i = new Intent(this, MainActivity.class);
                    startActivity(i);
                }
            }
        }
    }

    Pattern lowercase = Pattern.compile("^.*[a-z].*$");
    Pattern uppercase = Pattern.compile("^.*[A-Z].*$");
    Pattern number = Pattern.compile("^.*[0-9].*$");
    Pattern specialCharacter = Pattern.compile("^.*[^a-zA-Z0-9].*$");
    private Boolean isValidPassword(String password) {
        if (password.length() < 8) {
            return false;
        }
        if (!lowercase.matcher(password).matches()) {
            return false;
        }
        if (!uppercase.matcher(password).matches()) {
            return false;
        }


        if (!number.matcher(password).matches()) {
            return false;
        }
        if (!specialCharacter.matcher(password).matches()) {
            return false;
        }
        return true;
    }

}