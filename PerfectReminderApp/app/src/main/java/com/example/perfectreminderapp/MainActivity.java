package com.example.perfectreminderapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    LinearLayout linearLayout, linearLayout1, linearReminder, linearAppointment;
    DbHelper dbHelper;
    Button reminder, appointment, view;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        linearReminder = (LinearLayout) findViewById(R.id.addReminder);
        linearReminder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, Reminder.class);
                startActivity(intent);

            }
        });

        linearAppointment = (LinearLayout) findViewById(R.id.addAppointment);
        linearAppointment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,Appointment.class);
                startActivity(intent);
            }
        });


        linearLayout=(LinearLayout)findViewById(R.id.dateConverter);
        linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(MainActivity.this, Main2Activity.class);
                startActivity(intent);
                //Toast.makeText(MainActivity.this, "Date converter coming Soon!!", Toast.LENGTH_SHORT).show();
            }
        });

        linearLayout1=(LinearLayout)findViewById(R.id.smsSender);
        linearLayout1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MainActivity.this, "SMS sender Coming soon!!", Toast.LENGTH_SHORT).show();

            }
        });



    }
}
