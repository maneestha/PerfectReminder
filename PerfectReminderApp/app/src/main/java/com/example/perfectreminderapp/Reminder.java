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
import android.widget.TextView;

public class Reminder extends AppCompatActivity {

    SQLiteDatabase db;
    DbHelper mDbHelper;
    ListView list;
    FloatingActionButton floatingActionButton;
    TextView textView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reminder);
        getSupportActionBar().setTitle("Task Reminder");
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.blue)));
        list=(ListView)findViewById(R.id.reminderlist);

        floatingActionButton = (FloatingActionButton)findViewById(R.id.btn_add);
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Reminder.this, AddReminder.class );
                startActivity(intent);
            }
        });

        mDbHelper = new DbHelper(this);
        db= mDbHelper.getWritableDatabase();

        String[] from = {mDbHelper.R_Type, mDbHelper.R_Date, mDbHelper.R_Time, mDbHelper.R_Notify,mDbHelper.R_Desc};
        final String[] column = { mDbHelper.R_ID, mDbHelper.R_Type, mDbHelper.R_Date, mDbHelper.R_Time, mDbHelper.R_Notify, mDbHelper.R_Image, mDbHelper.R_Desc};
        int [] to = {R.id.RType, R.id.Date, R.id.Time,R.id.Notify, R.id.Desc};

         //Cursor cursor = db.rawQuery("Select * from Reminder", null);

         final Cursor cursor1= db.query(mDbHelper.Reminders,column,null,null,null,null,null);

         final SimpleCursorAdapter adapter = new SimpleCursorAdapter(this, R.layout.list_reminder, cursor1, from, to, 0);

        list.setAdapter(adapter);

        list.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            public void onItemClick(AdapterView<?> listView, View view, int position,
                                    long id){
                Intent intent = new Intent(Reminder.this, viewreminder.class);
                intent.putExtra(getString(R.string.rodId), id);
                startActivity(intent);
            }

        });




    }
    @Override
    public void onBackPressed(){

        Intent openMainScreen = new Intent(Reminder.this, MainActivity.class);
        openMainScreen.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(openMainScreen);
    }


}
