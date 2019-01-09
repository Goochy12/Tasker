package au.com.liamgooch.tasker.Fragments.adapters;

import androidx.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.Intent;
import android.graphics.Paint;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import java.util.Collections;
import java.util.List;

import au.com.liamgooch.tasker.Activities.EditTaskActivity;
import au.com.liamgooch.tasker.R;
import au.com.liamgooch.tasker.TaskViewModel;
import au.com.liamgooch.tasker.data.TaskItem;

public class TaskRecyclerAdapter extends RecyclerView.Adapter<TaskRecyclerAdapter.HomeViewHolder> {

    public static final String TASK_ID = "TASK_ID";

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
        holder.timeText.setText(taskItems.get(position).getTimeDateString());

        if (taskItems.get(position).getTaskChecked() == 0){
            holder.taskCheckbox.setChecked(false);
            holder.taskText.setPaintFlags(holder.taskText.getPaintFlags() & (~ Paint.STRIKE_THRU_TEXT_FLAG));
            holder.timeText.setText(taskItems.get(position).getTimeDateString());
        }else {
            holder.taskCheckbox.setChecked(true);
            holder.taskText.setPaintFlags(Paint.STRIKE_THRU_TEXT_FLAG);
            holder.timeText.setText("Complete!");
        }



//        if (taskItems.get(position).getReminder() == 0){
//            holder.reminderText.setText("No Reminder");
//        }else {
//        }

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

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent editActivityLauncher = new Intent(context,EditTaskActivity.class);

                String id = String.valueOf(taskItems.get(position).getItemID());
                editActivityLauncher.putExtra(TASK_ID,id);

                context.startActivity(editActivityLauncher);
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
        public TextView timeText;
        public CheckBox taskCheckbox;


        public HomeViewHolder(View itemView){
            super(itemView);
            this.itemView = itemView;
            taskText = (TextView) itemView.findViewById(R.id.taskNameTextView);
            timeText = (TextView) itemView.findViewById(R.id.timeTextView);
            taskCheckbox = (CheckBox) itemView.findViewById(R.id.taskCheckBox);
        }
    }

}
