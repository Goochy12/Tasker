package au.com.liamgooch.tasker.data;

import android.util.Log;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Objects;

import static au.com.liamgooch.tasker.data.String_Values.ACCOUNTS;
import static au.com.liamgooch.tasker.data.String_Values.TAG;
import static au.com.liamgooch.tasker.data.String_Values.USER_TASKS;
import static au.com.liamgooch.tasker.data.String_Values.TASK_CHECKED;
import static au.com.liamgooch.tasker.data.String_Values.TASK_DESC;
import static au.com.liamgooch.tasker.data.String_Values.TASK_END_DATE;
import static au.com.liamgooch.tasker.data.String_Values.TASK_END_TIME;
import static au.com.liamgooch.tasker.data.String_Values.TASK_GROUP;
import static au.com.liamgooch.tasker.data.String_Values.TASK_GROUP_MEMBERS;
import static au.com.liamgooch.tasker.data.String_Values.TASK_NAME;
import static au.com.liamgooch.tasker.data.String_Values.TASK_PROJECT;
import static au.com.liamgooch.tasker.data.String_Values.TASK_REMINDER;
import static au.com.liamgooch.tasker.data.String_Values.TASK_START_DATE;
import static au.com.liamgooch.tasker.data.String_Values.TASK_START_TIME;
import static au.com.liamgooch.tasker.data.String_Values.TASK_TIME_DATE_STRING;
import static au.com.liamgooch.tasker.data.String_Values.USER_TASK_ID;

public class TaskChanger {

    private String uid;

    public TaskChanger(String uid){
        this.uid = uid;
    }

    public void addUserTask(TaskItem taskItem){
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference().child(ACCOUNTS)
                .child(uid).child(USER_TASKS).child(taskItem.getItemID());

        databaseReference.child(USER_TASK_ID).setValue(taskItem.getItemID());

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

    public TaskItem getUserTask(String id){
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference(ACCOUNTS)
                .child(uid).child(USER_TASKS).child(id);

        final TaskItem[] taskItem = new TaskItem[1];
        Log.i(TAG, "getUserTask after list create: " + uid);

        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                Log.i(TAG, "onDataChange: Getting single data");

                String name = (String) dataSnapshot.child(TASK_NAME).getValue();
                String desc = (String) dataSnapshot.child(TASK_DESC).getValue();

                String startDate = (String) dataSnapshot.child(TASK_START_DATE).getValue();
                String startTime = (String) dataSnapshot.child(TASK_START_TIME).getValue();
                String endDate = (String) dataSnapshot.child(TASK_END_DATE).getValue();
                String endTime = (String) dataSnapshot.child(TASK_END_TIME).getValue();

                String project = (String) dataSnapshot.child(TASK_PROJECT).getValue();
                String group = (String) dataSnapshot.child(TASK_GROUP).getValue();
                String groupMembers = (String) dataSnapshot.child(TASK_GROUP_MEMBERS).getValue();

                int taskChecked = Integer.parseInt(Objects.requireNonNull(dataSnapshot.child(TASK_CHECKED).getValue()).toString());
                int reminder = Integer.parseInt(Objects.requireNonNull(dataSnapshot.child(TASK_REMINDER).getValue()).toString());

                String timeDateString = (String) dataSnapshot.child(TASK_TIME_DATE_STRING).getValue();

                taskItem[0] = new TaskItem(id,name,desc,startDate,startTime,endDate,endTime,project,group,groupMembers,
                        taskChecked,reminder,timeDateString);

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.i(TAG, databaseError.getMessage());
            }
        });


        return taskItem[0];
    }

    public void updateUserTask(TaskItem taskItem){
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference().child(ACCOUNTS)
                .child(uid).child(USER_TASKS).child(taskItem.getItemID());

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

    public void updateChecked(TaskItem taskItem) {
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference().child(ACCOUNTS)
                .child(uid).child(USER_TASKS).child(taskItem.getItemID());

        databaseReference.child(TASK_CHECKED).setValue(taskItem.getTaskChecked());
    }

    public void removeUserAll() {
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference().child(ACCOUNTS)
                .child(uid).child(USER_TASKS);

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for(DataSnapshot eachTask : dataSnapshot.getChildren()){
                    String id = eachTask.child(USER_TASK_ID).getValue().toString();
                    databaseReference.child(id).removeValue();
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    public void removeUserSelected() {
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference().child(ACCOUNTS)
                .child(uid).child(USER_TASKS);

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for(DataSnapshot eachTask : dataSnapshot.getChildren()){
                    int checked = Integer.parseInt(eachTask.child(TASK_CHECKED).getValue().toString());
                    if (checked == 1){
                        String id = eachTask.child(USER_TASK_ID).getValue().toString();
                        databaseReference.child(id).removeValue();
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
}
