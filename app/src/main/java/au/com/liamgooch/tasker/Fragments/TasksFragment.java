package au.com.liamgooch.tasker.Fragments;


import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import au.com.liamgooch.tasker.R;
import au.com.liamgooch.tasker.Fragments.adapters.TaskRecyclerAdapter;

public class TasksFragment extends Fragment implements ValueEventListener {

    private RecyclerView taskRecycler;
    private LinearLayoutManager taskRecyclerLayoutManager;
    private TaskRecyclerAdapter taskRecyclerAdapter;

    private FirebaseDatabase database;
    private DatabaseReference tasksReference;
    private String uid;

    private ArrayList<ArrayList<String>> all_tasks_list;

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

            }
        });

        database = FirebaseDatabase.getInstance();
        tasksReference = database.getReference(uid).child("all_tasks");
        tasksReference.addValueEventListener(this);

        //get the recycler
        taskRecycler = (RecyclerView) view.findViewById(R.id.task_fragment_recyclerView);

        //use a linear layout
        taskRecyclerLayoutManager = new LinearLayoutManager(view.getContext(),RecyclerView.VERTICAL,false);
        taskRecycler.setLayoutManager(taskRecyclerLayoutManager);

        taskRecyclerAdapter = new TaskRecyclerAdapter(view.getContext());
        taskRecycler.setAdapter(taskRecyclerAdapter);
    }

    @Override
    public void onDataChange(DataSnapshot dataSnapshot) {
        for(DataSnapshot eachTask : dataSnapshot.getChildren()){
            eachTask.child("name").getValue();
        }
    }

    @Override
    public void onCancelled(DatabaseError databaseError) {

    }
}
