package com.example.perfectreminderapp;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TimePicker;

import com.allyants.notifyme.NotifyMe;

import java.util.Calendar;

public class Main2Activity extends AppCompatActivity implements DatePickerDialog.OnDateSetListener, TimePickerDialog.OnTimeSetListener {


    Calendar now= Calendar.getInstance();
   com.wdullaer.materialdatetimepicker.time.TimePickerDialog tpd;
    com.wdullaer.materialdatetimepicker.date.DatePickerDialog dpd;
    EditText eTitle, etContent;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        Button btnNotify = findViewById(R.id.showNotification);
        Button cancel = findViewById(R.id.btnCancel);
        eTitle = findViewById(R.id.edittitle);
        etContent= findViewById(R.id.editDesc);

//        dpd = com.wdullaer.materialdatetimepicker.date.DatePickerDialog.newInstance(
//                (com.wdullaer.materialdatetimepicker.date.DatePickerDialog.OnDateSetListener) Main2Activity.this,
//                now.get(Calendar.YEAR),
//                now.get(Calendar.MONTH),
//                now.get(Calendar.DAY_OF_MONTH)
//        );
//
//        tpd = com.wdullaer.materialdatetimepicker.time.TimePickerDialog.newInstance(
//                (com.wdullaer.materialdatetimepicker.time.TimePickerDialog.OnTimeSetListener) Main2Activity.this,
//                now.get(Calendar.HOUR_OF_DAY),
//                now.get(Calendar.MINUTE),
//                now.get(Calendar.SECOND),
//                false
//        );

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NotifyMe.cancel(getApplicationContext(), "test");
            }
        });

        btnNotify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dpd.show(getFragmentManager(), "DatePickerDialog");
            }
        });

    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {

        now.set(Calendar.YEAR,year);
        now.set(Calendar.MONTH,month);
        now.set(Calendar.DAY_OF_MONTH,dayOfMonth);
        dpd.show(getFragmentManager(), "Date Picker");
    }


    @Override
    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
        now.set(Calendar.HOUR_OF_DAY,hourOfDay);
        now.set(Calendar.MINUTE,minute);
        now.set(Calendar.SECOND,0);

        tpd.show(getFragmentManager(), "Time Picker");

        NotifyMe notifyMe = new NotifyMe.Builder(getApplicationContext())
                .title(eTitle.getText().toString())
                .content(etContent.getText().toString())
                .color(255,0,0,255)
                .time(now)
                .addAction(new Intent(), "Snooze", false)
                .key("Test")
                .addAction(new Intent(), "Dismiss", true, false)
                .addAction(new Intent(), "Done")
                .large_icon(R.mipmap.ic_launcher_round)
                .build();
    }
}
