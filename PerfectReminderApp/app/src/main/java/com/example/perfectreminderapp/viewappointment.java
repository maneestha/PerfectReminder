package com.example.perfectreminderapp;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.widget.ImageView;
import android.widget.TextView;

public class viewappointment extends AppCompatActivity {

    TextView Title, Notify, Descript, DateValue, TimeValue;
    ImageView DocImage;
    SQLiteDatabase db;
    DbHelper dbHelper;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.activity_viewappointment);

        final long id = getIntent().getExtras().getLong(getString(R.string.arow_id));

        dbHelper = new DbHelper(this);
        db = dbHelper.getWritableDatabase();

        Cursor cursor = db.rawQuery("select * from " + dbHelper.Appointments + " where " + dbHelper.A_ID + "=" + id, null);
        Title = (TextView) findViewById(R.id.atitle);
        Notify = (TextView) findViewById(R.id.anotify);
        Descript = (TextView) findViewById(R.id.adescript);
        DateValue = (TextView) findViewById(R.id.adateValue);
        TimeValue = (TextView) findViewById(R.id.atimeValue);
        DocImage= (ImageView) findViewById(R.id.docImage);
        if (cursor != null) {
            if (cursor.moveToFirst()) {
                Title.setText(cursor.getString(cursor.getColumnIndex(dbHelper.A_Type)));
                Notify.setText(cursor.getString(cursor.getColumnIndex(dbHelper.A_Docname)));
                Descript.setText(cursor.getString(cursor.getColumnIndex(dbHelper.A_Desc)));
                DateValue.setText(cursor.getString(cursor.getColumnIndex(dbHelper.A_Date)));
                TimeValue.setText(cursor.getString(cursor.getColumnIndex(dbHelper.A_Time)));

            }
            cursor.close();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
// Inflate the menu; this adds items to the action bar if it is present.
        //getMenuInflater().inflate(R.menu.menu_viewreminder, menu);
        return true;
    }
}
