package au.com.liamgooch.tasker.Fragments.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.RecyclerView;
import au.com.liamgooch.tasker.R;
import au.com.liamgooch.tasker.data.Account_Type_Enum;
import au.com.liamgooch.tasker.data.ProjectItem;

public class ProjectsRecycleAdapter extends RecyclerView.Adapter<ProjectsRecycleAdapter.ViewHolder> {

    private Context context;
    private String uid;
    private Account_Type_Enum account_type;
    private ProgressBar allProjectsProgressBar;
    private TextView noProjectsTextView;

    private ArrayList<ProjectItem> projectsList = new ArrayList<>();

    public ProjectsRecycleAdapter(Context context, String uid, Account_Type_Enum account_type, ProgressBar allProjectsProgressBar, TextView noProjectsTV){
        this.context = context;
        this.uid = uid;
        this.account_type = account_type;
        this.allProjectsProgressBar = allProjectsProgressBar;
        this.noProjectsTextView = noProjectsTV;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        //create a new view
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.task_layout,parent,false);

        ViewHolder hVH = new ViewHolder(v);
//        taskViewModel = ViewModelProviders.of((FragmentActivity) this.context).get(TaskViewModel.class);

        return hVH;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return projectsList.size();
    }

    public void setTasks(ArrayList<ProjectItem> projectsList){
        this.projectsList.clear();
        this.projectsList.addAll(projectsList);

        allProjectsProgressBar.setVisibility(View.GONE);
        if (this.projectsList.isEmpty()){
            noProjectsTextView.setVisibility(View.VISIBLE);
        }else {
            noProjectsTextView.setVisibility(View.GONE);
        }
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }
}
