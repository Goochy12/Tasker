package au.com.scroogetech.tasker.Activities;

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
import android.widget.TextView;
import android.widget.TimePicker;

import au.com.scroogetech.tasker.R;
import au.com.scroogetech.tasker.TaskViewModel;
import au.com.scroogetech.tasker.data.TaskItem;

import static au.com.scroogetech.tasker.TaskRecyclerAdapter.TASK_ID;

public class EditTaskActivity extends AppCompatActivity implements TimePickerDialog.OnTimeSetListener, DatePickerDialog.OnDateSetListener{

    private EditText taskNameText;
    private EditText taskDescriptionText;
    private Button timeButton;
    private Button dateButton;
    private CheckBox reminder;

    private int taskID;

    private String taskName;
    private String taskDescription;
    private int taskTimeHour;
    private int taskTimeMinute;
    private int taskTimeDay;
    private int taskTimeMonth;
    private int taskTimeYear;
    private int taskChecked;
    private int taskReminder;

    TaskViewModel taskViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_task);

        taskViewModel = ViewModelProviders.of(this).get(TaskViewModel.class);

        Intent fromHome = getIntent();
        String id = fromHome.getStringExtra(TASK_ID);
        taskID = Integer.parseInt(id);

        TaskItem taskItem = taskViewModel.getTaskItem(taskID);

        taskNameText = (EditText) findViewById(R.id.enterTaskName);
        taskDescriptionText = (EditText) findViewById(R.id.enterDescription);

        timeButton = (Button) findViewById(R.id.timeButton);
        dateButton = (Button) findViewById(R.id.dateButton);
        reminder = (CheckBox) findViewById(R.id.reminderCheckbox);

        taskNameText.setText(taskItem.getTaskName());
        taskDescriptionText.setText(taskItem.getTaskDesc());
        taskTimeDay = taskItem.getDayDue();
        taskTimeMonth = taskItem.getMonthDue();
        taskTimeYear = taskItem.getYearDue();
        taskTimeHour = taskItem.getHourDue();
        taskTimeMinute = taskItem.getMinuteDue();
        taskChecked = taskItem.getTaskChecked();
        taskReminder = taskItem.getReminder();
        if (taskReminder != 0){
            reminder.setChecked(true);
        }

        String time = "" + taskTimeHour + ":" + taskTimeMinute;
        String date = "" + taskTimeDay + "/" + taskTimeMonth + "/" + taskTimeYear;

        timeButton.setText(time);
        dateButton.setText(date);

        TextView editTextView = (TextView) findViewById(R.id.AddTaskHeader);
        Button applyButton = (Button) findViewById(R.id.addTaskButton);

        //set text
        applyButton.setText("Apply");
        editTextView.setText("Edit Task");

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


        Button addTaskButton = (Button) findViewById(R.id.addTaskButton);
        addTaskButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                taskName = taskNameText.getText().toString();
                taskDescription = taskDescriptionText.getText().toString();
                int r = 0;
                if(reminder.isChecked()){
                    r = 1;
                }

                String date = "" + taskTimeDay + "/" + taskTimeMonth + "/" + taskTimeYear;
                String time = "" + taskTimeHour + ":" + taskTimeMinute;
                String timeDate = date + " @ " + time;

                TaskItem taskItem = new TaskItem(taskName,taskDescription,
                        taskTimeDay, taskTimeMonth, taskTimeYear, taskTimeMinute, taskTimeHour, taskChecked, r, timeDate);
                taskViewModel.updateTaskItem(taskItem,taskID);
                finish();

            }
        });

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

        reminder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (reminder.isChecked()){

                }else{

                }
            }
        });
    }



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
        int month = c.get(Calendar.MONTH);
        int day = c.get(Calendar.DAY_OF_MONTH);

        date = "" + day + "/" + month + "/" + year;

        return date;
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public void setTimeDate(){
        Calendar c = Calendar.getInstance();
        taskTimeYear = c.get(Calendar.YEAR);
        taskTimeMonth = c.get(Calendar.MONTH);
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
        DialogFragment timePickerFragment = new AddTaskActivity.TimePickerFragment();
        timePickerFragment.show(getSupportFragmentManager(), "timePicker");

    }

    public void showDatePickerDialog() {
        DialogFragment datePickerFragment = new AddTaskActivity.DatePickerFragment();
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
        taskTimeMonth = monthOfYear;
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
