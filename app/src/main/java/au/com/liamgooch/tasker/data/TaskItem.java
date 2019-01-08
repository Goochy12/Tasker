package au.com.liamgooch.tasker.data;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import io.reactivex.annotations.NonNull;

@Entity(tableName = "tasklist_table")
public class TaskItem {

    @PrimaryKey(autoGenerate = true)
    @NonNull
    private int itemID;

    private String taskName;
    private String taskDesc;
    private int dayDue;
    private int monthDue;
    private int yearDue;
    private int hourDue;
    private int minuteDue;
    private int taskChecked;
    private int reminder;
    private String timeDateString;

    public TaskItem(String taskName, String taskDesc, int dayDue, int monthDue, int yearDue, int minuteDue, int hourDue,
                    int taskChecked, int reminder, String timeDateString){
        this.taskName = taskName;
        this.taskDesc = taskDesc;
        this.dayDue = dayDue;
        this.monthDue = monthDue;
        this.yearDue = yearDue;
        this.minuteDue = minuteDue;
        this.hourDue = hourDue;
        this.taskChecked = taskChecked;
        this.reminder = reminder;
        this.timeDateString = timeDateString;
    }

    public int getItemID() {
        return itemID;
    }

    public void setItemID(int itemID) {
        this.itemID = itemID;
    }

    public String getTaskName() {
        return taskName;
    }

    public void setTaskName(String taskName) {
        this.taskName = taskName;
    }

    public String getTaskDesc() {
        return taskDesc;
    }

    public void setTaskDesc(String taskDesc) {
        this.taskDesc = taskDesc;
    }

    public int getTaskChecked() {
        return taskChecked;
    }

    public void setTaskChecked(int taskChecked) {
        this.taskChecked = taskChecked;
    }

    public int getMinuteDue() {
        return minuteDue;
    }

    public void setMinuteDue(int minuteDue) {
        this.minuteDue = minuteDue;
    }

    public int getHourDue() {
        return hourDue;
    }

    public void setHourDue(int hourDue) {
        this.hourDue = hourDue;
    }

    public int getYearDue() {
        return yearDue;
    }

    public void setYearDue(int yearDue) {
        this.yearDue = yearDue;
    }

    public int getMonthDue() {
        return monthDue;
    }

    public void setMonthDue(int monthDue) {
        this.monthDue = monthDue;
    }

    public int getDayDue() {
        return dayDue;
    }

    public void setDayDue(int dayDue) {
        this.dayDue = dayDue;
    }

    public int getReminder() {
        return reminder;
    }

    public void setReminder(int reminder) {
        this.reminder = reminder;
    }

    public String getTimeDateString() {
        return timeDateString;
    }

    public void setTimeDateString(String timeDateString) {
        this.timeDateString = timeDateString;
    }
}
