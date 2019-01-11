package au.com.liamgooch.tasker.data;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import static au.com.liamgooch.tasker.data.TaskSync.TASK_CHECKED;
import static au.com.liamgooch.tasker.data.TaskSync.TASK_DESC;
import static au.com.liamgooch.tasker.data.TaskSync.TASK_END_DATE;
import static au.com.liamgooch.tasker.data.TaskSync.TASK_END_TIME;
import static au.com.liamgooch.tasker.data.TaskSync.TASK_GROUP;
import static au.com.liamgooch.tasker.data.TaskSync.TASK_GROUP_MEMBERS;
import static au.com.liamgooch.tasker.data.TaskSync.TASK_NAME;
import static au.com.liamgooch.tasker.data.TaskSync.TASK_PROJECT;
import static au.com.liamgooch.tasker.data.TaskSync.TASK_REMINDER;
import static au.com.liamgooch.tasker.data.TaskSync.TASK_START_DATE;
import static au.com.liamgooch.tasker.data.TaskSync.TASK_START_TIME;
import static au.com.liamgooch.tasker.data.TaskSync.TASK_TIME_DATE_STRING;

public class TaskChanger {

    private String uid;

    public TaskChanger(String uid){
        this.uid = uid;
    }

    public void addUserTask(TaskItem taskItem){
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference().child("accounts")
                .child(uid).child("user_tasks").child("first");

        databaseReference.child(TASK_NAME).setValue(taskItem.getTaskName());
        databaseReference.child(TASK_DESC).setValue(taskItem.getTaskDesc());

        databaseReference.child(TASK_START_DATE).setValue(taskItem.getStartDate());
        databaseReference.child(TASK_START_TIME).setValue(taskItem.getStartTime());
        databaseReference.child(TASK_END_DATE).setValue(taskItem.getEndDate());
        databaseReference.child(TASK_END_TIME).setValue(taskItem.getEndTime());

        databaseReference.child(TASK_PROJECT).setValue(taskItem.getProject());
        databaseReference.child(TASK_GROUP).setValue(taskItem.getTask_group());
        databaseReference.child(TASK_GROUP_MEMBERS).setValue(taskItem.getGroupMembers());

        databaseReference.child(TASK_CHECKED).setValue(taskItem.getTaskChecked());
        databaseReference.child(TASK_REMINDER).setValue(taskItem.getReminder());

        databaseReference.child(TASK_TIME_DATE_STRING).setValue(taskItem.getTimeDateString());
    }
}
