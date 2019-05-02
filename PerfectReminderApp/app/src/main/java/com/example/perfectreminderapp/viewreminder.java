package com.example.perfectreminderapp;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class viewreminder extends AppCompatActivity {

    TextView Title, Notify, Descript, DateValue, TimeValue;
    ImageView DocImage;
    SQLiteDatabase db;
    DbHelper dbHelper;


    private class ViewHolder {
        ImageView imageView;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.activity_viewreminder);

        final long id = getIntent().getExtras().getLong(getString(R.string.row_id));

        dbHelper = new DbHelper(this);
        db = dbHelper.getWritableDatabase();

        Cursor cursor = db.rawQuery("select * from " + dbHelper.Reminders + " where " + dbHelper.R_ID + "=" + id, null);
        Title = (TextView) findViewById(R.id.title);
        Notify = (TextView) findViewById(R.id.notify);
        Descript = (TextView) findViewById(R.id.descript);
        DateValue = (TextView) findViewById(R.id.dateValue);
        TimeValue = (TextView) findViewById(R.id.timeValue);
        DocImage= (ImageView) findViewById(R.id.docImage);
        if (cursor != null) {
            if (cursor.moveToFirst()) {
                Title.setText(cursor.getString(cursor.getColumnIndex(dbHelper.R_Type)));
                Notify.setText(cursor.getString(cursor.getColumnIndex(dbHelper.R_Notify)));
                Descript.setText(cursor.getString(cursor.getColumnIndex(dbHelper.R_Desc)));
                DateValue.setText(cursor.getString(cursor.getColumnIndex(dbHelper.R_Date)));
                TimeValue.setText(cursor.getString(cursor.getColumnIndex(dbHelper.R_Time)));
                //DocImage.setImageBitmap(cursor.getBlob(cursor.getColumnIndex(dbHelper.R_Image)));
                byte [] image= cursor.getBlob(5);
                Bitmap bmp= BitmapFactory.decodeByteArray(image, 0, image.length);
                DocImage.setImageBitmap(bmp);
                Toast.makeText(this,"Done", Toast.LENGTH_SHORT).show();

            }
            cursor.close();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
// Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_viewreminder, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem menu){
        final long id = getIntent().getExtras().getLong(getString(R.string.rowID));
        switch (menu.getItemId()) {
            case R.id.action_discard:
                AlertDialog.Builder builder = new AlertDialog.Builder(viewreminder.this);
                builder
                        .setTitle(getString(R.string.delete_title))
                        .setMessage(getString(R.string.delete_message))
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .setPositiveButton(getString(R.string.yes), new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                Long id = getIntent().getExtras().getLong(getString(R.string.rodId));
                                db.delete(DbHelper.Reminders, DbHelper.R_ID + "=" + id, null);
                                db.close();
                                Intent openMainActivity = new Intent(viewreminder.this, Reminder.class);
                                startActivity(openMainActivity);

                            }
                        })
                        .setNegativeButton(getString(R.string.no), null)                        //Do nothing on no
                        .show();
                return true;

            default:
                return super.onOptionsItemSelected(menu);
        }
    }
}
