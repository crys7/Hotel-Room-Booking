package com.example.hotelroom_booking;

import android.Manifest;
import android.app.DatePickerDialog;
import android.content.ContentValues;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import java.util.Calendar;


public class ProfileActivity extends AppCompatActivity {
    private DatePickerDialog.OnDateSetListener mDateSetListener;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        final Spinner no_of_days=findViewById(R.id.spinner2);
        String[] items=new String[]{"1","2","3","4","5","6","7","8","9","10"};
        ArrayAdapter<String> adapter1=new ArrayAdapter<>(this,android.R.layout.simple_spinner_dropdown_item,items);
        no_of_days.setAdapter(adapter1);

        Bundle bundle=getIntent().getExtras();
        final TextView v1=findViewById(R.id.textView7);
        final String usn=bundle.getString("Username");
        Button btn=findViewById(R.id.button9);
        MyDb myDb=new MyDb(this);
        final SQLiteDatabase db=myDb.getWritableDatabase();
        final SQLiteDatabase dbr=myDb.getReadableDatabase();
        final TextView startdate=findViewById(R.id.textView17);
        startdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar cal=Calendar.getInstance();
                final int year=cal.get(Calendar.YEAR);
                final int month=cal.get(Calendar.MONTH);
                final int day=cal.get(Calendar.DAY_OF_MONTH);
                DatePickerDialog dialog=new DatePickerDialog(
                        ProfileActivity.this,android.R.style.Theme_Holo_Light_Dialog_MinWidth,mDateSetListener,
                        year,month,day);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.show();

            }
        });
        mDateSetListener=new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                month=month+1;
                String date=dayOfMonth+"/"+month+"/"+year;
                startdate.setText(date);
            }
        };
        final int REQUEST_PHONE_CALL=1;

        final Button callbutton=findViewById(R.id.button10);
        callbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    long num=7689546743l;
                    Intent callIntent = new Intent(Intent.ACTION_DIAL);
                    callIntent.setData(Uri.parse("tel:"+num));
                    startActivity(callIntent);
                }
                catch (Exception e)
                {

                }
            }
        });
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                String days=no_of_days.getSelectedItem().toString();
                //int in=Integer.parseInt(days);
                ContentValues values=new ContentValues();
                RadioGroup help=findViewById(R.id.radio);
                int recheck=help.getCheckedRadioButtonId();
                if(recheck==-1)
                {
                    Toast.makeText(getApplicationContext(), "This room is already booked", Toast.LENGTH_SHORT).show();
                }
                else {
                    RadioButton selectedroom = findViewById(recheck);
                    RadioButton one = findViewById(R.id.radioButton3);
                    RadioButton two = findViewById(R.id.radioButton4);
                    RadioButton three = findViewById(R.id.radioButton5);
                    RadioButton four = findViewById(R.id.radioButton6);
                    RadioButton five = findViewById(R.id.radioButton7);
                    String s = selectedroom.getText().toString().trim();
                    String startDate = startdate.getText().toString();
                    //v1.setText(s);
                    values.put("name", usn);
                    values.put("room_no", s);
                    values.put("startDate", startDate);
                    values.put("no_of_days", days);
                    if (startDate.matches("Datepick")) {
                        Toast.makeText(getApplicationContext(), "Select the date", Toast.LENGTH_SHORT).show();
                    } else {

                        Cursor c = dbr.rawQuery("SELECT * FROM rooms WHERE room_no = ?", new String[]{s});
                        if (c.moveToFirst()) {

                            Toast.makeText(getApplicationContext(), "This room is already booked", Toast.LENGTH_SHORT).show();
                        } else {
                            db.insert("rooms", null, values);
                            //Toast.makeText(getApplicationContext(),"Room booked succesfully",Toast.LENGTH_LONG).show();
                            Intent i = new Intent(getApplicationContext(), AfterBooking.class);
                            i.putExtra("Username", usn);
                            i.putExtra("Room_no", s);
                            i.putExtra("startDate",startDate);
                            i.putExtra("Number_of_days", days);
                            startActivity(i);

                        }

                    }
                }

            }
        });
    }

}