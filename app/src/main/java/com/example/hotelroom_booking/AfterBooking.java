package com.example.hotelroom_booking;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.os.Build;
import android.os.Bundle;


import android.app.Notification;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.app.Activity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.Date;

public class AfterBooking extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        MyDb myDb=new MyDb(this);
        SQLiteDatabase db=myDb.getReadableDatabase();
        setContentView(R.layout.activity_after_booking);
        Cursor resultSet = db.rawQuery("Select * from Customer",null);
        resultSet.moveToFirst();
        String name= resultSet.getString(0);
        Bundle bundle=getIntent().getExtras();
        int j=0;
        final String usn=bundle.getString("Username");
        final String Room_no=bundle.getString("Room_no");
        String start=bundle.getString("startDate");
        //int numberofdays=getIntent().getExtras().getInt("Number_of_days");
        String nom=getIntent().getExtras().getString("Number_of_days");
        String currentDateTimeString = java.text.DateFormat.getDateTimeInstance().format(new Date());
        int nn=Integer.parseInt(nom);
        switch (Room_no)
        {
            case "Room-1":
                j=500;
                break;
            case "Room-2":
                j=1000;
                break;
            case "Room-3":
                j=1100;
                break;
            case "Room-4":
                j=1400;
                break;
            case "Room-5":
                j=2000;
                break;
            default:
                j=0;
        }
        int num=nn*j;
        TextView text=findViewById(R.id.textView18);
        TextView text1=findViewById(R.id.textView20);
        TextView text2=findViewById(R.id.textView23);
        TextView text3=findViewById(R.id.textView25);
        TextView text4=findViewById(R.id.textView32);
        TextView text5=findViewById(R.id.textView34);
        TextView text6=findViewById(R.id.textView33);
        text1.setText(name);
        text2.setText(usn);
        text3.setText(currentDateTimeString);
        text4.setText(Room_no);
        text5.setText(start);
        text6.setText(nom);
        text.setText("Amount Payable:\t\t\t\t\t\t\t\t\t"+String.valueOf(num));
        if(Build.VERSION.SDK_INT >=Build.VERSION_CODES.O)
        {
            NotificationChannel channel=  new NotificationChannel("Notification","MYnotification", NotificationManager.IMPORTANCE_DEFAULT );
            NotificationManager manager=getSystemService(NotificationManager.class);
            manager.createNotificationChannel(channel);
        }
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this,"Notification")
                .setSmallIcon(R.drawable.ic_hotel_book)
                .setContentTitle("ROOM BOOKED")
                .setContentText("We hope that you enjoy your stay with us")
                .setPriority(NotificationCompat.PRIORITY_DEFAULT);
        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(this);
        notificationManager.notify(1, builder.build());
        TextView tt=findViewById(R.id.textView13);
        tt.setText("Thank You!");
        Button butt=findViewById(R.id.button4);
        butt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent j=new Intent(getApplicationContext(),MainActivity.class);
                startActivity(j);
            }
        });
    }

    @Override
    public void onBackPressed(){
        moveTaskToBack(true);
    }
}