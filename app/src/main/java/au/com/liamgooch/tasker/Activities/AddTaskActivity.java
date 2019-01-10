package au.com.liamgooch.tasker.Activities;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import androidx.lifecycle.ViewModelProviders;

import android.content.Intent;
import android.icu.util.Calendar;
import android.os.Build;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.DialogFragment;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.text.format.DateFormat;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TimePicker;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import au.com.liamgooch.tasker.R;
import au.com.liamgooch.tasker.TaskViewModel;
import au.com.liamgooch.tasker.data.TaskItem;
import au.com.liamgooch.tasker.data.TaskSync;

import static au.com.liamgooch.tasker.Activities.StartActivity.ACCOUNT_TYPE;
import static au.com.liamgooch.tasker.Activities.StartActivity.ACCOUNT_UID;
import static au.com.liamgooch.tasker.data.TaskSync.TASK_NAME;

public class AddTaskActivity extends AppCompatActivity implements DatePickerDialog.OnDateSetListener, TimePickerDialog.OnTimeSetListener {

    private EditText taskNameText;
    private EditText taskDescriptionText;
    private Button timeButton;
    private Button dateButton;
    private CheckBox reminder;

    private String taskName;
    private String taskDescription;
    private int taskTimeHour;
    private int taskTimeMinute;
    private int taskTimeDay;
    private int taskTimeMonth;
    private int taskTimeYear;

    private String accountType;
    private String uid;
    private TaskSync taskSync;

    private TaskViewModel taskViewModel;

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_task);

        Intent intent = getIntent();
        accountType = intent.getStringExtra(ACCOUNT_TYPE);
//        uid = intent.getStringExtra(ACCOUNT_UID);
//        taskSync = intent.getParcelableExtra("tasksync");
        taskSync = (TaskSync) intent.getParcelableExtra("tasksync");

        taskNameText = (EditText) findViewById(R.id.enterTaskName);
        taskDescriptionText = (EditText) findViewById(R.id.enterDescription);

        timeButton = (Button) findViewById(R.id.timeButton);
        dateButton = (Button) findViewById(R.id.dateButton);
        reminder = (CheckBox) findViewById(R.id.reminderCheckbox);

        timeButton.setText(getTime());
        dateButton.setText(getDate());

        taskViewModel = ViewModelProviders.of(this).get(TaskViewModel.class);

        //set current values
        setTimeDate();

        timeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showTimePickerDialog();
            }
        });

        dateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDatePickerDialog();
            }
        });

        Button addMemberButton = (Button) findViewById(R.id.addGroupMembersButton);
        if (accountType.equals("admin")){
            addMemberButton.setEnabled(true);
        }else{
            addMemberButton.setEnabled(false);
        }

        addMemberButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });


        Button addTaskButton = (Button) findViewById(R.id.addTaskButton);
        addTaskButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                taskName = taskNameText.getText().toString();
                taskDescription = taskDescriptionText.getText().toString();

                int r = 0;
                if (reminder.isChecked()){
                    r = 1;
                }


//                ArrayList<Integer> eventTime = new ArrayList<>();
//                eventTime.add(taskTimeYear);
//                eventTime.add(taskTimeMonth);
//                eventTime.add(taskTimeDay);
//                eventTime.add(taskTimeHour);
//                eventTime.add(taskTimeMinute);

//                ArrayList<Integer> timeList = getReminderValue(eventTime);

                String startDate = getDate();
                String startTime = getTime();

                String endDate = "" + taskTimeDay + "/" + taskTimeMonth + "/" + taskTimeYear;
                String endTime = "" + taskTimeHour + ":" + taskTimeMinute;
                String timeDate = endDate + " @ " + endTime;



                TaskItem taskItem = new TaskItem(taskName,taskDescription, startDate,startTime,endDate,endTime, "null","null",
                        "",0,r, timeDate);

                taskSync.addUserTask(taskItem);
//                taskViewModel.insert(taskItem);
                finish();

            }
        });

        addTaskButton.setEnabled(false);

        taskNameText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                if (taskNameText.getText().toString().equals("")){
                    addTaskButton.setEnabled(false);
                }else {
                    addTaskButton.setEnabled(true);
                }

            }
        });
    }

//    private ArrayList<Integer> getReminderValue(ArrayList<Integer> eventTime) {
//        ArrayList<Integer> timeList = new ArrayList<>();
//        int year = eventTime.get(0);
//        int month = eventTime.get(1);
//        int day = eventTime.get(2);
//        int hour = eventTime.get(3);
//        int minute = eventTime.get(4);
//
//        if (reminderSpinner.getSelectedItemPosition() == 0){
//            timeList.clear();
//        }else if(reminderSpinner.getSelectedItemPosition() == 1){
//            timeList = eventTime;
//        }else if (reminderSpinner.getSelectedItemPosition() == 2){
//            minute = timeList.get(4);
//            if (minute - 5) =
//            timeList.get(4) = timeList.get(4) - 5;
//        }
//
//        return timeList;
//    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public String getTime(){
        String time;
        Calendar c = Calendar.getInstance();
        int hour = c.get(Calendar.HOUR_OF_DAY);
        int minute = c.get(Calendar.MINUTE);

        time = "" + hour + ":" + minute;

        return time;
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public String getDate(){
        String date;
        Calendar c = Calendar.getInstance();
        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH + 1);
        int day = c.get(Calendar.DAY_OF_MONTH);

        date = "" + day + "/" + month + "/" + year;

        return date;
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public void setTimeDate(){
        Calendar c = Calendar.getInstance();
        taskTimeYear = c.get(Calendar.YEAR);
        taskTimeMonth = c.get(Calendar.MONTH + 1);
        taskTimeDay = c.get(Calendar.DAY_OF_MONTH);
        taskTimeHour = c.get(Calendar.HOUR_OF_DAY);
        taskTimeMinute = c.get(Calendar.MINUTE);
    }

    public void storeTaskValues(){
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public void validateTimeSet(){
        Calendar c = Calendar.getInstance();

    }

    public void showTimePickerDialog() {
        DialogFragment timePickerFragment = new TimePickerFragment();
        timePickerFragment.show(getSupportFragmentManager(), "timePicker");

    }

    public void showDatePickerDialog() {
        DialogFragment datePickerFragment = new DatePickerFragment();
        datePickerFragment.show(getSupportFragmentManager(), "datePicker");
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
        taskTimeHour = hourOfDay;
        taskTimeMinute = minute;

        validateTimeSet();

        String time;
        time = "" + taskTimeHour + ":" + taskTimeMinute;

        timeButton.setText(time);
    }

    @Override
    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
        taskTimeYear = year;
        taskTimeMonth = monthOfYear + 1;
        taskTimeDay = dayOfMonth;

        String date;
        date = "" + taskTimeDay + "/" + taskTimeMonth + "/" + taskTimeYear;

        dateButton.setText(date);
    }

    //DATE AND TIME SELECTORS

    public static class TimePickerFragment extends DialogFragment {

        @RequiresApi(api = Build.VERSION_CODES.N)
        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            // Use the current time as the default values for the picker
            final Calendar c = Calendar.getInstance();
            int hour = c.get(Calendar.HOUR_OF_DAY);
            int minute = c.get(Calendar.MINUTE);

            // Create a new instance of TimePickerDialog and return it
            TimePickerDialog tp = new TimePickerDialog(getActivity(), (TimePickerDialog.OnTimeSetListener) getActivity(), hour, minute,
                    DateFormat.is24HourFormat(getActivity()));

            return tp;
        }
    }

    public static class DatePickerFragment extends DialogFragment
            implements DatePickerDialog.OnDateSetListener {

        @RequiresApi(api = Build.VERSION_CODES.N)
        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            // Use the current date as the default date in the picker
            final Calendar c = Calendar.getInstance();
            int year = c.get(Calendar.YEAR);
            int month = c.get(Calendar.MONTH);
            int day = c.get(Calendar.DAY_OF_MONTH);

            // Create a new instance of DatePickerDialog and return it
            DatePickerDialog dp = new DatePickerDialog(getActivity(), (DatePickerDialog.OnDateSetListener) getActivity(), year, month, day);
            dp.getDatePicker().setMinDate(System.currentTimeMillis() - 1000);

            return dp;
        }

        @Override
        public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {

        }
    }
}
