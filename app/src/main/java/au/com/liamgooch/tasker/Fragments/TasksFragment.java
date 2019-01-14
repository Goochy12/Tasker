package au.com.liamgooch.tasker.Fragments;


import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import au.com.liamgooch.tasker.Activities.AddTaskActivity;
import au.com.liamgooch.tasker.Activities.Login;
import au.com.liamgooch.tasker.R;
import au.com.liamgooch.tasker.Fragments.adapters.TaskRecyclerAdapter;
import au.com.liamgooch.tasker.data.Account_Type_Enum;
import au.com.liamgooch.tasker.data.TaskChanger;
import au.com.liamgooch.tasker.data.TaskItem;
import au.com.liamgooch.tasker.data.TaskSync;

import static au.com.liamgooch.tasker.data.String_Values.ACCOUNT_TYPE;
import static au.com.liamgooch.tasker.data.String_Values.ACCOUNT_UID;

@SuppressLint("ValidFragment")
public class TasksFragment extends Fragment {

    private RecyclerView taskRecycler;
    private LinearLayoutManager taskRecyclerLayoutManager;
    public TaskRecyclerAdapter taskRecyclerAdapter;

    private FirebaseDatabase database;
    private DatabaseReference tasksReference;
    private String uid;
    private Account_Type_Enum account_type;
    private TextView textView;

    private FirebaseAuth mAuth;
    private Menu dashAppBar;
    private TaskChanger taskChanger;

    private ArrayList<ArrayList<String>> all_tasks_list;

    private TaskSync taskSync;

    private ProgressBar allTasksProgressBar;

    @SuppressLint("ValidFragment")
    public TasksFragment(List<TaskItem> userTasks, List<TaskItem> projectTasks, String uid, Account_Type_Enum account_type){
        this.uid = uid;
        this.account_type = account_type;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        setHasOptionsMenu(true);
        return inflater.inflate(R.layout.fragment_tasks, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        allTasksProgressBar = (ProgressBar) view.findViewById(R.id.allTasksProgressBar);
//        allTasksProgressBar.setVisibility(View.VISIBLE);
//        allTasksProgressBar.animate();

        mAuth = FirebaseAuth.getInstance();

        FloatingActionButton addTaskButton = (FloatingActionButton) view.findViewById(R.id.add_task_fab);
        addTaskButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent launchAddTask = new Intent(view.getContext(), AddTaskActivity.class);
                launchAddTask.putExtra(ACCOUNT_TYPE,account_type);
                launchAddTask.putExtra(ACCOUNT_UID,uid);
                startActivity(launchAddTask);
            }
        });

        database = FirebaseDatabase.getInstance();
//        tasksReference = database.getReference(uid).child("user_tasks");
//        tasksReference.addValueEventListener(user_tasks_value_event_listener);

        //get the recycler
        taskRecycler = (RecyclerView) view.findViewById(R.id.task_fragment_recyclerView);

        //use a linear layout
        taskRecyclerLayoutManager = new LinearLayoutManager(view.getContext(),RecyclerView.VERTICAL,false);
        taskRecycler.setLayoutManager(taskRecyclerLayoutManager);

        TextView noTasksTV = getActivity().findViewById(R.id.noUserTasksTextView);
        noTasksTV.setVisibility(View.GONE);
        taskRecyclerAdapter = new TaskRecyclerAdapter(view.getContext(),uid, allTasksProgressBar,noTasksTV);
        taskRecycler.setAdapter(taskRecyclerAdapter);

        taskChanger = new TaskChanger(uid);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater menuInflater) {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_main,menu);
        this.dashAppBar = menu;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.

        switch (item.getItemId()){
            case R.id.action_clear_selected:
//                taskViewModel.deleteCheckedTasks();
                taskChanger.removeUserSelected();
                return true;
            case R.id.action_clear_all:
//                taskViewModel.deleteAllTasks();
                taskChanger.removeUserAll();
                return true;
            case R.id.action_settings:
                return true;
            case R.id.action_logout:
                mAuth.signOut();

                Intent loginIntent = new Intent(getContext(),Login.class);
                startActivity(loginIntent);

                Objects.requireNonNull(getActivity()).finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

}
