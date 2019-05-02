package com.example.perfectreminderapp;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;

public class Appointment extends AppCompatActivity {

    SQLiteDatabase db;
    DbHelper mDbHelper;
    ListView list;
    FloatingActionButton floatingActionButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_appointment);
        getSupportActionBar().setTitle("Appointment Reminder");
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.blue)));

        list=(ListView)findViewById(R.id.appointmentlist);
        floatingActionButton= (FloatingActionButton)findViewById(R.id.btn_addappoint);
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent= new Intent(Appointment.this, AddAppointmet.class);
                startActivity(intent);
            }
        });

        mDbHelper = new DbHelper(this);
        db= mDbHelper.getWritableDatabase();

        String[] from = {mDbHelper.A_Type, mDbHelper.A_Date, mDbHelper.A_Time, mDbHelper.A_Docname,mDbHelper.A_Desc};
        final String[] column = { mDbHelper.A_ID, mDbHelper.A_Type, mDbHelper.A_Date, mDbHelper.A_Time, mDbHelper.A_Docname, mDbHelper.A_Hosname, mDbHelper.A_Phone, mDbHelper.A_Email,mDbHelper.A_Desc};
        int [] to = {R.id.Atype, R.id.Adate, R.id.Atime,R.id.Adoctor, R.id.Adesc};

        //Cursor cursor = db.rawQuery("Select * from Appoinements", null);

        final Cursor cursor= db.query(mDbHelper.Appointments,column,null,null,null,null,null);

        final SimpleCursorAdapter adapter = new SimpleCursorAdapter(this, R.layout.list_appointment, cursor, from, to, 0);

        list.setAdapter(adapter);

        list.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            public void onItemClick(AdapterView<?> listView, View view, int position,
                                    long ids){
                Intent intent = new Intent(Appointment.this, viewreminder.class);
                intent.putExtra(getString(R.string.arodId), ids);
                startActivity(intent);
            }

        });

    }

    @Override
    public void onBackPressed()
    {
        Intent openMainScreen = new Intent(Appointment.this, MainActivity.class);
        openMainScreen.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(openMainScreen);
    }


}
