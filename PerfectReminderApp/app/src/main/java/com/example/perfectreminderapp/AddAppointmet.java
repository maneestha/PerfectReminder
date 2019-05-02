package com.example.perfectreminderapp;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.FragmentManager;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.view.View;
import android.app.TimePickerDialog;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class AddAppointmet extends AppCompatActivity {

    SQLiteDatabase db;
    DbHelper mDbHelper;
    EditText AppointmentDate, AppointmentTime, DoctorName, HospitalName, AppointmentPhone, AppointmentEmail, AppointmentDetail ;
    String appointmentType;
    protected  static  TextView displayCurrentDate, displayCurrentTime;
    Button addAppointment;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_appointmet);
        mDbHelper = new DbHelper(this);
        Spinner mspinner= findViewById(R.id.appointSpinner);
        AppointmentDate= (EditText) findViewById(R.id.selectdate);
        AppointmentTime= (EditText)findViewById(R.id.selectTime) ;
        DoctorName= (EditText)findViewById(R.id.doctorName);
        HospitalName= (EditText)findViewById(R.id.hospitalName);
        AppointmentPhone= (EditText)findViewById(R.id.appointPhone);
        AppointmentEmail= (EditText)findViewById(R.id.appointEmail);
        AppointmentDetail=(EditText)findViewById(R.id.appointDetail) ;
        addAppointment= (Button)findViewById(R.id.btnAddAppoint);

        //spinner
        //select the reminder type from the drp down list from an array

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.appointmentType, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mspinner.setAdapter(adapter);
        mspinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                appointmentType = parent.getItemAtPosition(position).toString();
                Toast.makeText(parent.getContext(), appointmentType, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        //Date picker
        final Calendar myCalendar = Calendar.getInstance();

        final DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {
                // TODO Auto-generated method stub
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, monthOfYear);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                updateLabel();
            }
            private void updateLabel() {
                String myFormat = "MM/dd/yyyy"; //In which you need put here
                SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);

                AppointmentDate.setText(sdf.format(myCalendar.getTime()));
            }
        };

        AppointmentDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                new DatePickerDialog(AddAppointmet.this, date, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

        //Time picker

        AppointmentTime.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                Calendar mcurrentTime = Calendar.getInstance();
                int hour = mcurrentTime.get(Calendar.HOUR_OF_DAY);
                int minute = mcurrentTime.get(Calendar.MINUTE);

                TimePickerDialog mTimePicker;
                mTimePicker = new TimePickerDialog(AddAppointmet.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(android.widget.TimePicker view, int hourOfDay, int minute) {
                        AppointmentTime.setText( hourOfDay + ":" + minute);
                    }

                }, hour, minute, true);
                mTimePicker.setTitle("Select Time");
                mTimePicker.show();

            }
        });



//
//        displayCurrentTime = (TextView)findViewById(R.id.selected_time);
//        Button displayTimeButton = (Button)findViewById(R.id.select_time);
//        assert displayTimeButton != null;
//        displayTimeButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                TimePicker mTimePicker = new TimePicker();
//                mTimePicker.show(getSupportFragmentManager(),"Select time");
//
//            }
//        });
//        displayCurrentDate = (TextView)findViewById(R.id.selected_date);
//        Button displayDateButton = (Button)findViewById(R.id.select_date);
//        assert displayDateButton != null;
//        displayDateButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                DatePickerFragment mDatePicker = new DatePickerFragment();
//                mDatePicker.show(getSupportFragmentManager(), "Select date");
//
//            }
//
//        });


        addappointmentDatas();
    }

    public void addappointmentDatas(){
        addAppointment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //desc.setText("Your choice "+ radioButton.getText()+documentType+reminddisplayCurrentDate.getText()+reminddisplayCurrentTime.getText());

                if(AppointmentDetail.length() <0 || appointmentType.matches("Select an appointment type]") || AppointmentDate == null || AppointmentTime == null || DoctorName == null || AppointmentPhone == null || AppointmentEmail == null ){
                    Toast.makeText(AddAppointmet.this, "Fill all text fields!", Toast.LENGTH_SHORT).show();
                }
                else {
                    DbHelper dbHelper = new DbHelper(getApplicationContext());
                    dbHelper.addAppointmentData(
                            appointmentType,
                            AppointmentDate.getText(),
                            AppointmentTime.getText(),
                            DoctorName.getText(),
                            HospitalName.getText(),
                            AppointmentPhone.getText(),
                            AppointmentEmail.getText(),
                            AppointmentDetail.getText());

                    Toast.makeText(AddAppointmet.this, "Data Saved", Toast.LENGTH_SHORT).show();
                    Intent openMainScreen = new Intent(AddAppointmet.this, Appointment.class);
                    openMainScreen.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(openMainScreen);
                }


            }
        });
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
            displayCurrentTime.setText(String.valueOf(hourOfDay) + " : " + String.valueOf(minute));
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
            displayCurrentDate.setText(String.valueOf(year) + " - " + String.valueOf(month) + " - " + String.valueOf(day));

        }

    }
    @Override
    public void onBackPressed()
    {
        Intent openMainScreen = new Intent(AddAppointmet.this, Appointment.class);
        openMainScreen.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(openMainScreen);
    }
}


