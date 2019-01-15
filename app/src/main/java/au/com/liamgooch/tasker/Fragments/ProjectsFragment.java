package au.com.liamgooch.tasker.Fragments;


import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import au.com.liamgooch.tasker.Activities.AddProjectActivity;
import au.com.liamgooch.tasker.Activities.AddTaskActivity;
import au.com.liamgooch.tasker.Fragments.adapters.ProjectsRecycleAdapter;
import au.com.liamgooch.tasker.R;
import au.com.liamgooch.tasker.data.Account_Type_Enum;

import static au.com.liamgooch.tasker.data.String_Values.ACCOUNT_TYPE;
import static au.com.liamgooch.tasker.data.String_Values.ACCOUNT_UID;

public class ProjectsFragment extends Fragment {

    private ProgressBar allProjectsProgressBar;

    private FirebaseAuth mAuth;
    private FirebaseDatabase database;

    private Account_Type_Enum account_type;
    private String uid;

    public ProjectsRecycleAdapter projectsRecyclerAdapter;
    private RecyclerView projectsRecycler;
    private RecyclerView.LayoutManager projectsRecyclerLayoutManager;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_projects, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        allProjectsProgressBar = (ProgressBar) view.findViewById(R.id.all_projects_progressBar);
//        allTasksProgressBar.setVisibility(View.VISIBLE);
//        allTasksProgressBar.animate();

        mAuth = FirebaseAuth.getInstance();

        FloatingActionButton addTaskButton = (FloatingActionButton) view.findViewById(R.id.add_project_fab);
        addTaskButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent launchAddTask = new Intent(view.getContext(), AddProjectActivity.class);
                launchAddTask.putExtra(ACCOUNT_TYPE,account_type);
                launchAddTask.putExtra(ACCOUNT_UID,uid);
                startActivity(launchAddTask);
            }
        });

        database = FirebaseDatabase.getInstance();
//        tasksReference = database.getReference(uid).child("user_tasks");
//        tasksReference.addValueEventListener(user_tasks_value_event_listener);

        //get the recycler
        projectsRecycler = (RecyclerView) view.findViewById(R.id.all_projects_recycler_view);

        //use a linear layout
        projectsRecyclerLayoutManager = new LinearLayoutManager(view.getContext(),RecyclerView.VERTICAL,false);
        projectsRecycler.setLayoutManager(projectsRecyclerLayoutManager);

        TextView noTasksTV = getActivity().findViewById(R.id.noUserTasksTextView);
        noTasksTV.setVisibility(View.GONE);
        projectsRecyclerAdapter = new ProjectsRecycleAdapter(view.getContext(),uid, account_type, allProjectsProgressBar,noTasksTV);
        projectsRecycler.setAdapter(projectsRecyclerAdapter);
    }
}
