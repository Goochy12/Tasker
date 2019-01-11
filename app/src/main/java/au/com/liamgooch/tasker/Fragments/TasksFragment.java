package au.com.liamgooch.tasker.Fragments;


import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.os.Parcelable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import au.com.liamgooch.tasker.Activities.AddTaskActivity;
import au.com.liamgooch.tasker.R;
import au.com.liamgooch.tasker.Fragments.adapters.TaskRecyclerAdapter;
import au.com.liamgooch.tasker.data.TaskItem;
import au.com.liamgooch.tasker.data.TaskSync;

import static au.com.liamgooch.tasker.Activities.StartActivity.ACCOUNT_TYPE;
import static au.com.liamgooch.tasker.Activities.StartActivity.ACCOUNT_UID;
import static au.com.liamgooch.tasker.Activities.StartActivity.TAG;

@SuppressLint("ValidFragment")
public class TasksFragment extends Fragment {

    private RecyclerView taskRecycler;
    private LinearLayoutManager taskRecyclerLayoutManager;
    public TaskRecyclerAdapter taskRecyclerAdapter;

    private FirebaseDatabase database;
    private DatabaseReference tasksReference;
    private String uid;
    private String accountType;
    private TextView textView;

    private ArrayList<ArrayList<String>> all_tasks_list;

    private TaskSync taskSync;

    @SuppressLint("ValidFragment")
    public TasksFragment(List<TaskItem> userTasks, List<TaskItem> projectTasks, TaskSync taskSync, String uid, String accountType){
        this.taskSync = taskSync;
        this.uid = uid;
        this.accountType = accountType;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_tasks, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        FloatingActionButton addTaskButton = (FloatingActionButton) view.findViewById(R.id.add_task_fab);
        addTaskButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent launchAddTask = new Intent(view.getContext(), AddTaskActivity.class);
                launchAddTask.putExtra(ACCOUNT_TYPE,accountType);
                launchAddTask.putExtra(ACCOUNT_UID,uid);
                launchAddTask.putExtra("tasksync", (Parcelable) taskSync);
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

        taskRecyclerAdapter = new TaskRecyclerAdapter(view.getContext());
        taskRecycler.setAdapter(taskRecyclerAdapter);
    }
}
