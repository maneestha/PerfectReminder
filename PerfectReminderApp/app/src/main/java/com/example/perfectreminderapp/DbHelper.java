package com.example.perfectreminderapp;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteStatement;
import android.text.Editable;

/**
 * Created by Dominic on 07/04/2015.
 */
public class DbHelper extends SQLiteOpenHelper {

    // Database Version
    private static final int DATABASE_VERSION = 3;

    // Database Name
    private static final String DATABASE_NAME = "reminderDatabase";


    //Table Names
    public static final String Reminders = "Reminder";
    public static final String Appointments = "Appointment";

    //Reminder Table Columns
    public static final String R_ID= "_id";
    public static final String R_Type= "R_Type";
    public static final String R_Date= "R_Date";
    public static final String R_Time= "R_Time";
    public static final String R_Notify= "R_Notify";
    public static final String R_Image = "R_Image";
    public static final String R_Desc= "R_Desc";


    //Appointment Table Columns
    public static final String A_ID= "_id";
    public static final String A_Type= "A_Type";
    public static final String A_Date= "A_Date";
    public static final String A_Time= "A_Time";
    public static final String A_Docname= "A_Docname";
    public static final String A_Hosname= "A_Hosname";
    public static final String A_Phone= "A_Phone";
    public static final String A_Email= "A_Email";
    public static final String A_Desc= "A_Desc";



    public DbHelper(Context context){
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String Create_Reminder_Table = "CREATE TABLE if not exists " + Reminders + "("
                + R_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," + R_Type + " TEXT , " + R_Date +
                " Text ,  " + R_Time + " Text , " + R_Notify + " Text , " + R_Image + " Blob, " + R_Desc + " Text  )";
        db.execSQL(Create_Reminder_Table);

        //creating appointment table
        String CREATE_Appointment_Table = "create table if not exists " + Appointments + "("
                + A_ID +" INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL ," + A_Type + " TEXT , " +
                A_Date + " Text ,"+ A_Time+ " TEXT, " + A_Docname + " TEXT," + A_Hosname + " TEXT , " + A_Phone +
                " TEXT ," + A_Email + " TEXT ," +A_Desc +" TEXT )";

        db.execSQL(CREATE_Appointment_Table);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
       db.execSQL("Drop table if exists " + Reminders);
       db.execSQL("Drop table if exists " + Appointments);
//
//        onCreate(db);
    }


    public void addReminderData(String type, CharSequence date, CharSequence time, CharSequence notify, byte[] image, String desc){
        /*SQLiteDatabase db = getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(R_Type, type);
        cv.put(R_Date, String.valueOf(date));
        cv.put(R_Time, String.valueOf(time));
        cv.put(R_Notify, (String) notify);
        cv.put(R_Image, String.valueOf(image));
        cv.put(R_Desc, String.valueOf(desc));
        db.insert(Reminders, null, cv);*/
        SQLiteDatabase database = getWritableDatabase();
        String sql = "INSERT INTO Reminder VALUES (NULL, ?, ?, ?, ?, ?, ?)";

        SQLiteStatement statement = database.compileStatement(sql);
        statement.clearBindings();
        statement.bindString(1, type);
        statement.bindString(2, String.valueOf(date));
        statement.bindString(3, String.valueOf(time));
        statement.bindString(4, (String) notify);
        statement.bindBlob(5,image);
        statement.bindString(6,desc);
        statement.executeInsert();
    }


    public void addAppointmentData(String appointmentType, Editable text, Editable text1, Editable text2, Editable text3, Editable text4, Editable text5, Editable text6) {
        SQLiteDatabase database = getWritableDatabase();
        String sql = "INSERT INTO Appointment VALUES (NULL, ?, ?, ?, ?, ?, ?,?, ?)";

        SQLiteStatement statement = database.compileStatement(sql);
        statement.clearBindings();
        statement.bindString(1, appointmentType);
        statement.bindString(2, String.valueOf(text));
        statement.bindString(3, String.valueOf(text1));
        statement.bindString(4, String.valueOf(text2));
        statement.bindString(5, String.valueOf(text3));
        statement.bindString(6, String.valueOf(text5));
        statement.bindString(7, String.valueOf(text6));
        statement.executeInsert();
    }




/*    public void list_all_Reminder(TextView textView, TextView date, TextView time, ImageView image, TextView describe){
        Cursor cursor = this .getReadableDatabase().rawQuery("Select * from Reminders", null);
        //textView.setText("");
        while (cursor.moveToNext()){
            textView.append(cursor.getString(1));
            date.append(cursor.getString(2));
            time.append(cursor.getString(3));
            cursor.getBlob(4);
            describe.append(cursor.getString(6));
        }
    }*/


}
