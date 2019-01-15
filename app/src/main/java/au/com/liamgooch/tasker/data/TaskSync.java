package au.com.liamgooch.tasker.data;

import android.util.Log;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import au.com.liamgooch.tasker.Activities.Dashboard;
import au.com.liamgooch.tasker.Fragments.GroupsFragment;
import au.com.liamgooch.tasker.Fragments.ProjectsFragment;
import au.com.liamgooch.tasker.Fragments.TasksFragment;

import static au.com.liamgooch.tasker.data.String_Values.ACCOUNTS;
import static au.com.liamgooch.tasker.data.String_Values.GROUPS;
import static au.com.liamgooch.tasker.data.String_Values.GROUP_MEMBERS;
import static au.com.liamgooch.tasker.data.String_Values.PROJECTS;
import static au.com.liamgooch.tasker.data.String_Values.PROJECT_TASKS;
import static au.com.liamgooch.tasker.data.String_Values.TAG;
import static au.com.liamgooch.tasker.data.String_Values.TASKS_DB;
import static au.com.liamgooch.tasker.data.String_Values.USER_TASKS;

public class TaskSync {


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
    private ArrayList<ArrayList<String>> raw_projects = new ArrayList<>();
    private ArrayList<ArrayList<String>> raw_project_tasks = new ArrayList<>();

    public TaskSync(TasksFragment tasksFragment, ProjectsFragment projectsFragment, GroupsFragment groupsFragment,
                    String uid, Dashboard dashboard){
        this.tasksFragment = tasksFragment;
        this.projectsFragment = projectsFragment;
        this.groupsFragment = groupsFragment;
        this.uid = uid;
        this.dashboard = dashboard;

        database = FirebaseDatabase.getInstance();
        DatabaseReference dbR = database.getReference(ACCOUNTS).child(uid).child(TASKS_DB);
        try{
            tasksReference = dbR.child(USER_TASKS);
        }catch (NullPointerException ignore){
            Log.i(TAG, "TaskSync: Null Pointer getting user tasks");
        }

        try{
            projectsReference = dbR.child(PROJECTS);
        }catch (NullPointerException ignore){
            Log.i(TAG, "TaskSync: Null Pointer getting projects");
        }

        try{
            projectTasksReference = dbR.child(PROJECTS).child(PROJECT_TASKS);
        }catch (NullPointerException ignore){
            Log.i(TAG, "TaskSync: Null Pointer getting project tasks");
        }

        try{
            groupsReference = dbR.child(GROUPS);
        }catch (NullPointerException ignore){
            Log.i(TAG, "TaskSync: Null Pointer getting groups");
        }

        try{
            groupMembersReference = dbR.child(GROUPS).child(GROUP_MEMBERS);
        }catch (NullPointerException ignore){
            Log.i(TAG, "TaskSync: Null Pointer getting group members");
        }

    }

    public void syncTasks(){
        tasksReference.addValueEventListener(user_tasks_value_event_listener);
        projectsReference.addValueEventListener(projects_value_event_listener);
        projectTasksReference.addValueEventListener(project_tasks_value_event_listener);
        groupsReference.addValueEventListener(groups_value_event_listener);
        groupMembersReference.addValueEventListener(group_members_value_event_listener);
    }

    ValueEventListener user_tasks_value_event_listener = new ValueEventListener() {
        @Override
        public void onDataChange(DataSnapshot dataSnapshot) {
            raw_user_tasks.clear();

            for (DataSnapshot eachTask : dataSnapshot.getChildren()){
                ArrayList<String> list = new ArrayList<>();
                //get Task information
                try {
                    String id = (String) eachTask.child(String_Values.USER_TASK_ID).getValue();
                    String name = (String) eachTask.child(String_Values.TASK_NAME).getValue();
                    String desc = (String) eachTask.child(String_Values.TASK_DESC).getValue();

                    String startDate = (String) eachTask.child(String_Values.TASK_START_DATE).getValue();
                    String startTime = (String) eachTask.child(String_Values.TASK_START_TIME).getValue();
                    String endDate = (String) eachTask.child(String_Values.TASK_END_DATE).getValue();
                    String endTime = (String) eachTask.child(String_Values.TASK_END_TIME).getValue();

                    String project = (String) eachTask.child(String_Values.TASK_PROJECT).getValue();
                    String group = (String) eachTask.child(String_Values.TASK_GROUP).getValue();
                    String groupMembers = (String) eachTask.child(String_Values.TASK_GROUP_MEMBERS).getValue();

                    String taskChecked = Objects.requireNonNull(eachTask.child(String_Values.TASK_CHECKED).getValue()).toString();
                    String reminder = Objects.requireNonNull(eachTask.child(String_Values.TASK_REMINDER).getValue()).toString();

                    String timeDateString = (String) eachTask.child(String_Values.TASK_TIME_DATE_STRING).getValue();

                    list.add(id);
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

            ArrayList<TaskItem> taskItems = convertToTasks(raw_user_tasks);
            dashboard.setUser_tasks(taskItems);

        }

        @Override
        public void onCancelled(DatabaseError databaseError) {

        }
    };

    ValueEventListener projects_value_event_listener = new ValueEventListener() {
        @Override
        public void onDataChange(DataSnapshot dataSnapshot) {
            raw_projects.clear();

            for (DataSnapshot eachTask : dataSnapshot.getChildren()){
                ArrayList<String> list = new ArrayList<>();
                //get Task information
                try {
                    String id = (String) eachTask.child(String_Values.USER_TASK_ID).getValue();
                    String name = (String) eachTask.child(String_Values.TASK_NAME).getValue();
                    String desc = (String) eachTask.child(String_Values.TASK_DESC).getValue();

                    String startDate = (String) eachTask.child(String_Values.TASK_START_DATE).getValue();
                    String startTime = (String) eachTask.child(String_Values.TASK_START_TIME).getValue();
                    String endDate = (String) eachTask.child(String_Values.TASK_END_DATE).getValue();
                    String endTime = (String) eachTask.child(String_Values.TASK_END_TIME).getValue();

                    String groupMembers = (String) eachTask.child(String_Values.TASK_GROUP_MEMBERS).getValue();

                    String projectChecked = Objects.requireNonNull(eachTask.child(String_Values.TASK_CHECKED).getValue()).toString();
                    String reminder = Objects.requireNonNull(eachTask.child(String_Values.TASK_REMINDER).getValue()).toString();

                    String timeDateString = (String) eachTask.child(String_Values.TASK_TIME_DATE_STRING).getValue();

                    list.add(id);
                    list.add(name);
                    list.add(desc);

                    list.add(startDate);
                    list.add(startTime);
                    list.add(endDate);
                    list.add(endTime);

                    list.add(groupMembers);

                    list.add(projectChecked);
                    list.add(reminder);

                    list.add(timeDateString);

                    raw_projects.add(list);

                }catch (NullPointerException ignore){

                }
            }

            ArrayList<ProjectItem> projectItems = convertToProjects(raw_projects);
            dashboard.setProjects(projectItems);
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

    public ArrayList<TaskItem> convertToTasks(ArrayList<ArrayList<String>> tasks){
        ArrayList<TaskItem> itemList = new ArrayList<>();
        for (int i = 0; i < tasks.size(); i ++){
            String id = tasks.get(i).get(0);
            String name = tasks.get(i).get(1);
            String desc = tasks.get(i).get(2);

            String startDate = tasks.get(i).get(3);
            String startTime = tasks.get(i).get(4);
            String endDate = tasks.get(i).get(5);
            String endTime = tasks.get(i).get(6);

            String project = tasks.get(i).get(7);
            String group = tasks.get(i).get(8);
            String members = tasks.get(i).get(9);

            int taskChecked = Integer.parseInt(tasks.get(i).get(10));
            int reminder = Integer.parseInt(tasks.get(i).get(11));

            String timeDateString = tasks.get(i).get(12);

            TaskItem taskItem = new TaskItem(id,name,desc,startDate,startTime,endDate,endTime,project,group,members,taskChecked,reminder,timeDateString);
            itemList.add(taskItem);
        }

        return itemList;
    }

    private ArrayList<ProjectItem> convertToProjects(ArrayList<ArrayList<String>> raw_projects) {
        ArrayList<ProjectItem> projectItems = new ArrayList<>();

        return projectItems;
    }

}
