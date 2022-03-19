package com.example.odev2birthday;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;

import java.util.Calendar;
import java.util.Date;

public class MainActivity extends AppCompatActivity {

    private DatePickerDialog datePickerDialog;
    private Button dateButton;
    private TextView finalMessage;

    private static int monthDays[];

    static {
        monthDays = new int[]{31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31};
    }

    static class Date {
        int d, m, y;

        public Date(int d, int m, int y) {
            this.d = d;
            this.m = m;
            this.y = y;
        }
    }

    private int numOfDaysLived;
    private Date birthday;
    private Date today;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initDatePicker();
        dateButton = findViewById(R.id.datePickerButton);
        dateButton.setText(getTodayDate());
        finalMessage = findViewById(R.id.finalMessage);
        finalMessage.setText("");
    }

    private String getTodayDate() {
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        month = month + 1;
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        today = new  Date(day,month,year);
        return makeDateString(day,month,year);
    }

    private void initDatePicker() {
        DatePickerDialog.OnDateSetListener dateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                month = month + 1;
                birthday = new Date(day,month,year);
                calculateNumOfDaysLived();
                String date = makeDateString(day,month,year);
                dateButton.setText(date);
            }
        };

        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        int style = AlertDialog.THEME_HOLO_LIGHT;

        datePickerDialog = new DatePickerDialog(this, style, dateSetListener, year, month, day);
        datePickerDialog.getDatePicker().setMaxDate(System.currentTimeMillis());
    }

    private void calculateNumOfDaysLived() {
        // Number of days before today
        int numBfrToday = today.y * 365 + today.d;
        for (int i = 0; i < today.m - 1; i++) {
            numBfrToday += monthDays[i];
        }
        numBfrToday += countLeapYears(today);

        // Number of days before birthday
        int numBfrBirthday = birthday.y * 365 + birthday.d;
        for (int i = 0; i < birthday.m - 1; i++) {
            numBfrBirthday += monthDays[i];
        }
        numBfrBirthday += countLeapYears(birthday);

        numOfDaysLived = numBfrToday - numBfrBirthday;
    }

    private int countLeapYears(Date date) {
        int years = date.y;

        if(date.m <= 2) {
            years--;
        }

        return years/4 - years/100 + years/400;
    }

    private String makeDateString(int day, int month, int year) {
        return day + " " + getMonthFormat(month) + " " + year;
    }

    private String getMonthFormat(int month) {
        switch (month) {
            case 1:
                return "JAN";
            case 2:
                return "FEB";
            case 3:
                return "MAR";
            case 4:
                return "APR";
            case 5:
                return "MAY";
            case 6:
                return "JUN";
            case 7:
                return "JUL";
            case 8:
                return "AUG";
            case 9:
                return "SEP";
            case 10:
                return "OCT";
            case 11:
                return "NOV";
            case 12:
                return "DEC";

            // default should never happen
            default:
                return "JAN";
        }
    }

    public void openDatePicker(View view) {
        datePickerDialog.show();
    }

    public void showNumOfDaysLived(View view) {
        finalMessage.setText(String.format("Congratulations\nYou have already lived  \n%d days!!!!",numOfDaysLived));
    }
}