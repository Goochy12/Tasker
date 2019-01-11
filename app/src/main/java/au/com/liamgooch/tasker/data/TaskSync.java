package au.com.liamgooch.tasker.data;

import android.os.Parcel;
import android.os.Parcelable;
import android.provider.CalendarContract;
import android.provider.ContactsContract;
import android.util.Log;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import androidx.room.Database;
import au.com.liamgooch.tasker.Activities.Dashboard;
import au.com.liamgooch.tasker.Fragments.GroupsFragment;
import au.com.liamgooch.tasker.Fragments.ProjectsFragment;
import au.com.liamgooch.tasker.Fragments.TasksFragment;

import static au.com.liamgooch.tasker.Activities.StartActivity.TAG;

public class TaskSync {

    //strings
    public static final String TASK_NAME = "task_name";
    public static final String TASK_DESC = "task_desc";

    public static final String TASK_START_DATE = "task_start_date";
    public static final String TASK_START_TIME = "task_start_time";
    public static final String TASK_END_DATE = "task_end_date";
    public static final String TASK_END_TIME = "task_end_time";

    public static final String TASK_PROJECT = "task_project";
    public static final String TASK_GROUP = "task_group";
    public static final String TASK_GROUP_MEMBERS = "task_group_members";

    public static final String TASK_CHECKED = "task_checked";
    public static final String TASK_REMINDER = "task_reminder";

    public static final String TASK_TIME_DATE_STRING = "task_time_date_string";


    private TasksFragment tasksFragment;
    private ProjectsFragment projectsFragment;
    private GroupsFragment groupsFragment;

    private DatabaseReference tasksReference;
    private DatabaseReference projectsReference;
    private DatabaseReference projectTasksReference;
    private DatabaseReference groupsReference;
    private DatabaseReference groupMembersReference;

    private FirebaseDatabase database;
    private String uid;
    private Dashboard dashboard;

    private ArrayList<ArrayList<String>> raw_user_tasks = new ArrayList<>();
    private ArrayList<ArrayList<String>> raw_project_tasks = new ArrayList<>();

    public TaskSync(TasksFragment tasksFragment, ProjectsFragment projectsFragment, GroupsFragment groupsFragment,
                    String uid, Dashboard dashboard){
        this.tasksFragment = tasksFragment;
        this.projectsFragment = projectsFragment;
        this.groupsFragment = groupsFragment;
        this.uid = uid;
        this.dashboard = dashboard;

        database = FirebaseDatabase.getInstance();
        DatabaseReference dbR = database.getReference("accounts").child(uid);
        try{
            tasksReference = dbR.child("user_tasks");
            Log.i(TAG, "TaskSync: In user tasks");
        }catch (NullPointerException ignore){
            Log.i(TAG, "TaskSync: Null Pointer getting user tasks");
        }

        try{
            projectsReference = dbR.child("projects");
        }catch (NullPointerException ignore){
            Log.i(TAG, "TaskSync: Null Pointer getting projects");
        }

        try{
            projectTasksReference = dbR.child("projects").child("projects_tasks");
        }catch (NullPointerException ignore){
            Log.i(TAG, "TaskSync: Null Pointer getting project tasks");
        }

        try{
            groupsReference = dbR.child("groups");
        }catch (NullPointerException ignore){
            Log.i(TAG, "TaskSync: Null Pointer getting groups");
        }

        try{
            groupMembersReference = dbR.child("groups").child("group_members");
        }catch (NullPointerException ignore){
            Log.i(TAG, "TaskSync: Null Pointer getting group members");
        }

    }

    public void syncTasks(){
        Log.i(TAG, "syncTasks: Initiated");
        tasksReference.addValueEventListener(user_tasks_value_event_listener);
        projectsReference.addValueEventListener(projects_value_event_listener);
        projectTasksReference.addValueEventListener(project_tasks_value_event_listener);
        groupsReference.addValueEventListener(groups_value_event_listener);
        groupMembersReference.addValueEventListener(group_members_value_event_listener);
    }

    ValueEventListener user_tasks_value_event_listener = new ValueEventListener() {
        @Override
        public void onDataChange(DataSnapshot dataSnapshot) {
            Log.i(TAG, "onDataChange: Initated");
            raw_user_tasks.clear();

            for (DataSnapshot eachTask : dataSnapshot.getChildren()){
                ArrayList<String> list = new ArrayList<>();
                //get Task information
                try {
                    String name = (String) eachTask.child(TASK_NAME).getValue();
                    String desc = (String) eachTask.child(TASK_DESC).getValue();

                    String startDate = (String) eachTask.child(TASK_START_DATE).getValue();
                    String startTime = (String) eachTask.child(TASK_START_TIME).getValue();
                    String endDate = (String) eachTask.child(TASK_END_DATE).getValue();
                    String endTime = (String) eachTask.child(TASK_END_TIME).getValue();

                    String project = (String) eachTask.child(TASK_PROJECT).getValue();
                    String group = (String) eachTask.child(TASK_GROUP).getValue();
                    String groupMembers = (String) eachTask.child(TASK_GROUP_MEMBERS).getValue();

                    String taskChecked = Objects.requireNonNull(eachTask.child(TASK_CHECKED).getValue()).toString();
                    String reminder = Objects.requireNonNull(eachTask.child(TASK_REMINDER).getValue()).toString();

                    String timeDateString = (String) eachTask.child(TASK_TIME_DATE_STRING).getValue();

                    list.add(name);
                    list.add(desc);

                    list.add(startDate);
                    list.add(startTime);
                    list.add(endDate);
                    list.add(endTime);

                    list.add(project);
                    list.add(group);
                    list.add(groupMembers);

                    list.add(taskChecked);
                    list.add(reminder);

                    list.add(timeDateString);

                    raw_user_tasks.add(list);

                }catch (NullPointerException ignore){

                }
            }

            List<TaskItem> taskItems = convertToTasks(raw_user_tasks);
            dashboard.setUser_tasks(taskItems);

        }

        @Override
        public void onCancelled(DatabaseError databaseError) {

        }
    };

    ValueEventListener projects_value_event_listener = new ValueEventListener() {
        @Override
        public void onDataChange(DataSnapshot dataSnapshot) {

        }

        @Override
        public void onCancelled(DatabaseError databaseError) {

        }
    };

    ValueEventListener project_tasks_value_event_listener = new ValueEventListener() {
        @Override
        public void onDataChange(DataSnapshot dataSnapshot) {

        }

        @Override
        public void onCancelled(DatabaseError databaseError) {

        }
    };

    ValueEventListener groups_value_event_listener = new ValueEventListener() {
        @Override
        public void onDataChange(DataSnapshot dataSnapshot) {

        }

        @Override
        public void onCancelled(DatabaseError databaseError) {

        }
    };

    ValueEventListener group_members_value_event_listener = new ValueEventListener() {
        @Override
        public void onDataChange(DataSnapshot dataSnapshot) {

        }

        @Override
        public void onCancelled(DatabaseError databaseError) {

        }
    };

    public List<TaskItem> convertToTasks(ArrayList<ArrayList<String>> tasks){
        List<TaskItem> itemList = new ArrayList<>();
        for (int i = 0; i < tasks.size(); i ++){
            String name = tasks.get(i).get(0);
            String desc = tasks.get(i).get(1);

            String startDate = tasks.get(i).get(2);
            String startTime = tasks.get(i).get(3);
            String endDate = tasks.get(i).get(4);
            String endTime = tasks.get(i).get(5);

            String project = tasks.get(i).get(6);
            String group = tasks.get(i).get(7);
            String members = tasks.get(i).get(8);

            int taskChecked = Integer.parseInt(tasks.get(i).get(9));
            int reminder = Integer.parseInt(tasks.get(i).get(10));

            String timeDateString = tasks.get(i).get(11);

            TaskItem taskItem = new TaskItem(name,desc,startDate,startTime,endDate,endTime,project,group,members,taskChecked,reminder,timeDateString);
            itemList.add(taskItem);
        }

        return itemList;
    }

}
