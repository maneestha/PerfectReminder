package com.example.perfectreminderapp;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.RequiresApi;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.text.format.DateFormat;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.util.Calendar;

public class AddReminder extends AppCompatActivity {

    //variable declarations
    SQLiteDatabase db;
    DbHelper mDbHelper;
    Button btnAddReminder;
    Button btnChoose;
    ImageView imageView;
    RadioGroup radioGroup;
    RadioButton radioButton;
    EditText desc;
    String documentType;
    Spinner mspinner;
    EditText mTitleText;
    EditText mDescriptionText;
    Integer REQUEST_CAMERA=100, SELECT_FILE=0;
    public  static TextView reminddisplayCurrentDate;
    public static TextView  reminddisplayCurrentTime;
    public final int REQUEST_CODE_GALLERY = 999;
    private static  final int PICK_IMAGE=100;




    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_reminder);
        mDbHelper = new DbHelper(this);
        db = mDbHelper.getWritableDatabase();
        imageView=(ImageView)findViewById(R.id.imageToChoose);
        desc =(EditText)findViewById(R.id.description);
        reminddisplayCurrentTime = (TextView)findViewById(R.id.remindselected_time);
        radioGroup=findViewById(R.id.remind_radioGroup);
        reminddisplayCurrentDate = (TextView)findViewById(R.id.remindselected_date);

        Button displayDateButton = (Button)findViewById(R.id.remindselect_date);
        Button displayTimeButton = (Button)findViewById(R.id.remindselect_time);
        btnAddReminder=(Button)findViewById(R.id.btn_addReminder);
        btnChoose = (Button)findViewById(R.id.btn_Choose);


        //select the reminder type from the drp down list from an array
        mspinner= findViewById(R.id.spinner);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.reminderType, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mspinner.setAdapter(adapter);
        mspinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                documentType = parent.getItemAtPosition(position).toString();
                Toast.makeText(parent.getContext(), documentType, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        btnChoose.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.M)
            @Override
            public void onClick(View view) {
                SelectImage();
            }
        });



        assert displayTimeButton != null;
        displayTimeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AddReminder.TimePicker mTimePicker = new AddReminder.TimePicker();
                mTimePicker.show(getSupportFragmentManager(),"Select time");

            }
        });


        assert displayDateButton != null;
        displayDateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AddReminder.DatePickerFragment mDatePicker = new AddReminder.DatePickerFragment();
                mDatePicker.show(getSupportFragmentManager(), "Select date");

            }

        });

        addReminderDatas();
    }

    public void checkButton(View v){
        int radioId= radioGroup.getCheckedRadioButtonId();
        radioButton=findViewById(radioId);
        Toast.makeText(this,"Selected Button "+ radioButton.getText(),Toast.LENGTH_SHORT).show();
    }

    public static byte[] imageViewToByte(ImageView image) {
        Bitmap bitmap = ((BitmapDrawable)image.getDrawable()).getBitmap();
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
        byte[] byteArray = stream.toByteArray();
        return byteArray;
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    private void SelectImage(){

        final CharSequence[] items={"Camera","Gallery", "Cancel"};

        AlertDialog.Builder builder = new AlertDialog.Builder(AddReminder.this);
        builder.setTitle("Add Image");

        builder.setItems(items, new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                if (items[i].equals("Camera")) {

                    Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    startActivityForResult(intent, REQUEST_CAMERA);

                } else if (items[i].equals("Gallery")) {
                    ActivityCompat.requestPermissions(
                            AddReminder.this,
                            new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                            REQUEST_CODE_GALLERY
                    );
                    Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                    intent.setType("image/*");
                    startActivityForResult(intent, SELECT_FILE);


                } else if (items[i].equals("Cancel")) {
                    dialogInterface.dismiss();
                }
            }
        });
        builder.show();

        if (checkSelfPermission(Manifest.permission.CAMERA)
                != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(new String[]{Manifest.permission.CAMERA},
                    REQUEST_CAMERA);
        }
    }

    @Override
    public  void onActivityResult(int requestCode, int resultCode, Intent data){
        super.onActivityResult(requestCode, resultCode,data);

        if(resultCode== Activity.RESULT_OK){

            if(requestCode==REQUEST_CAMERA){

                Bundle bundle = data.getExtras();
                final Bitmap bmp = (Bitmap) bundle.get("data");
                imageView.setImageBitmap(bmp);

            }else if(requestCode==SELECT_FILE){

                Uri selectedImageUri = data.getData();
                imageView.setImageURI(selectedImageUri);
            }

        }
    }

    public static class TimePicker extends DialogFragment implements TimePickerDialog.OnTimeSetListener {
        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            final Calendar c = Calendar.getInstance();
            int hour = c.get(Calendar.HOUR_OF_DAY);
            int minute = c.get(Calendar.MINUTE);
            return new TimePickerDialog(getActivity(), this, hour, minute, DateFormat.is24HourFormat(getActivity()));
        }
        @Override
        public void onTimeSet(android.widget.TimePicker view, int hourOfDay, int minute) {




            reminddisplayCurrentTime.setText(String.valueOf(hourOfDay) + " : " + String.valueOf(minute));

        }

    }

    public static class DatePickerFragment extends DialogFragment implements DatePickerDialog.OnDateSetListener {
        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            final Calendar c = Calendar.getInstance();
            int year = c.get(Calendar.YEAR);
            int month = c.get(Calendar.MONTH);
            int day = c.get(Calendar.DAY_OF_MONTH);
            return new DatePickerDialog(getActivity(), this, year, month, day);
        }
        public void onDateSet(DatePicker view, int year, int month, int day) {

            reminddisplayCurrentDate.setText(String.valueOf(year) + " - " + String.valueOf(month) + " - " + String.valueOf(day));

        }

    }

    public void addReminderDatas(){
        btnAddReminder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //desc.setText("Your choice "+ radioButton.getText()+documentType+reminddisplayCurrentDate.getText()+reminddisplayCurrentTime.getText());

                if(desc.length() <0 || reminddisplayCurrentDate == null || reminddisplayCurrentTime == null || radioButton == null || documentType.matches("Select an option")){
                    Toast.makeText(AddReminder.this, "Fill all text fields!", Toast.LENGTH_SHORT).show();
                }
                else {
                    DbHelper dbHelper = new DbHelper(getApplicationContext());
                    dbHelper.addReminderData(
                            documentType,
                            reminddisplayCurrentDate.getText(),
                            reminddisplayCurrentTime.getText(),
                            radioButton.getText(),
                            imageViewToByte(imageView),
                            desc.getText().toString());

                    Toast.makeText(AddReminder.this, "Data Saved", Toast.LENGTH_SHORT).show();
                    Intent openMainScreen = new Intent(AddReminder.this, Reminder.class);
                    openMainScreen.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(openMainScreen);
                }

            }

        });
    }
    @Override
    public void onBackPressed()
    {
        Intent openMainScreen = new Intent(AddReminder.this, Reminder.class);
        openMainScreen.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(openMainScreen);
    }


}
