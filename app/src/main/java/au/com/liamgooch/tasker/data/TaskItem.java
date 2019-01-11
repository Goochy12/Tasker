package au.com.liamgooch.tasker.data;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import io.reactivex.annotations.NonNull;

@Entity(tableName = "tasklist_table")
public class TaskItem {

    @PrimaryKey(autoGenerate = true)
    @NonNull
    private int dbItemID;

    private String itemID;

    private String taskName;
    private String taskDesc;

    private String startDate;
    private String startTime;
    private String endDate;
    private String endTime;

    private String project;

    private String task_group;
    private String groupMembers;

    private int taskChecked;
    private int reminder;

    private String timeDateString;

    public TaskItem(String itemID, String taskName, String taskDesc, String startDate, String startTime, String endDate, String endTime, String project,
                    String task_group, String groupMembers, int taskChecked, int reminder, String timeDateString){
        this.itemID = itemID;

        this.taskName = taskName;
        this.taskDesc = taskDesc;

        this.startDate = startDate;
        this.startTime = startTime;
        this.endDate = endDate;
        this.endTime = endTime;

        this.project = project;
        this.task_group = task_group;
        this.groupMembers = groupMembers;

        this.taskChecked = taskChecked;
        this.reminder = reminder;

        this.timeDateString = timeDateString;
    }

    public String getItemID() {
        return itemID;
    }

    public void setItemID(String itemID) {
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

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public String getProject() {
        return project;
    }

    public void setProject(String project) {
        this.project = project;
    }

    public String getTask_group() {
        return task_group;
    }

    public void setTask_group(String task_group) {
        this.task_group = task_group;
    }

    public String getGroupMembers() {
        return groupMembers;
    }

    public void setGroupMembers(String groupMembers) {
        this.groupMembers = groupMembers;
    }

    public int getTaskChecked() {
        return taskChecked;
    }

    public void setTaskChecked(int taskChecked) {
        this.taskChecked = taskChecked;
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

    public int getDbItemID() {
        return dbItemID;
    }

    public void setDbItemID(int dbItemID) {
        this.dbItemID = dbItemID;
    }
}
