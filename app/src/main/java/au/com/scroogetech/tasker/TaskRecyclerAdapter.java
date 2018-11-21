package au.com.scroogetech.tasker;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import java.util.Collections;
import java.util.List;

import au.com.scroogetech.tasker.data.TaskItem;
import au.com.scroogetech.tasker.data.TaskRepository;

public class TaskRecyclerAdapter extends RecyclerView.Adapter<TaskRecyclerAdapter.HomeViewHolder> {

    private List<TaskItem> taskItems = Collections.emptyList();
    //private final LayoutInflater layoutInflater;

    private TaskViewModel taskViewModel;
    private Context context;

    //constructor
    public TaskRecyclerAdapter(Context context){
        //layoutInflater = LayoutInflater.from(context);
        this.context = context;
    }

    //create views
    @NonNull
    @Override
    public HomeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType){
        //create a new view
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.task_layout,parent,false);

        HomeViewHolder hVH = new HomeViewHolder(v);
        taskViewModel = ViewModelProviders.of((FragmentActivity) this.context).get(TaskViewModel.class);

        return hVH;
    }

    @Override
    public void onBindViewHolder(@NonNull HomeViewHolder holder, int position){

        holder.taskText.setText(taskItems.get(position).getTaskName());
        if (taskItems.get(position).getTaskChecked() == 0){
            holder.taskCheckbox.setChecked(false);
        }else {
            holder.taskCheckbox.setChecked(true);
        }

        holder.taskCheckbox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //taskViewModel.setTaskChecked(taskItems.get(position));
                if (holder.taskCheckbox.isChecked()) {
                    taskItems.get(position).setTaskChecked(1);
                } else {
                    taskItems.get(position).setTaskChecked(0);
                }
                taskViewModel.setTaskChecked(taskItems.get(position));
            }
        });
    }

    @Override
    public int getItemCount(){
        return taskItems.size();
    }

    public void setTasks(List<TaskItem> taskItems){
        this.taskItems = taskItems;
        notifyDataSetChanged();
    }

    public List<TaskItem> getUpdatedTasks(){
        return this.taskItems;
    }


    //create the view holder
    public static class HomeViewHolder extends RecyclerView.ViewHolder{
        public View itemView;
        public TextView taskText;
        public CheckBox taskCheckbox;


        public HomeViewHolder(View itemView){
            super(itemView);
            this.itemView = itemView;
            taskText = (TextView) itemView.findViewById(R.id.taskNameTextView);
            taskCheckbox = (CheckBox) itemView.findViewById(R.id.taskCheckBox);
        }
    }

}
