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
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.Collections;
import java.util.List;

import au.com.liamgooch.tasker.Activities.EditTaskActivity;
import au.com.liamgooch.tasker.R;
import au.com.liamgooch.tasker.TaskViewModel;
import au.com.liamgooch.tasker.data.Account_Type_Enum;
import au.com.liamgooch.tasker.data.TaskChanger;
import au.com.liamgooch.tasker.data.TaskItem;

import static au.com.liamgooch.tasker.data.String_Values.ACCOUNT_UID;

public class TaskRecyclerAdapter extends RecyclerView.Adapter<TaskRecyclerAdapter.HomeViewHolder> {

    public static final String TASK_ID = "TASK_ID";

    private List<TaskItem> taskItems = Collections.emptyList();
    //private final LayoutInflater layoutInflater;

    private TaskViewModel taskViewModel;
    private Context context;

    private String uid;
    private Account_Type_Enum account_type;
    private ProgressBar allTasksProgressBar;

    private TextView noTasksTextView;

    //constructor
    public TaskRecyclerAdapter(Context context, String uid, Account_Type_Enum account_type, ProgressBar allTasksProgressBar, TextView noTasksTextView){
        //layoutInflater = LayoutInflater.from(context);
        this.context = context;
        this.uid = uid;
        this.account_type = account_type;
        this.allTasksProgressBar = allTasksProgressBar;
        this.noTasksTextView = noTasksTextView;
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

        allTasksProgressBar.setVisibility(View.GONE);

        holder.taskText.setText(taskItems.get(position).getTaskName());
        holder.timeText.setText(taskItems.get(position).getTimeDateString());

        if (taskItems.get(position).getTaskChecked() == 0){
            holder.taskCheckbox.setChecked(false);
            holder.taskText.setPaintFlags(holder.taskText.getPaintFlags() & (~ Paint.STRIKE_THRU_TEXT_FLAG));
            holder.timeText.setText(taskItems.get(position).getTimeDateString());
        }else {
            holder.taskCheckbox.setChecked(true);
            holder.taskText.setPaintFlags(Paint.STRIKE_THRU_TEXT_FLAG);
            holder.timeText.setText(context.getString(R.string.task_complete));
        }

        TaskChanger taskChanger = new TaskChanger(uid);



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
                    taskChanger.updateChecked(taskItems.get(position));
                } else {
                    taskItems.get(position).setTaskChecked(0);
                    taskChanger.updateChecked(taskItems.get(position));

                }
//                taskViewModel.setTaskChecked(taskItems.get(position));
                notifyDataSetChanged();
            }
        });

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent editActivityLauncher = new Intent(context,EditTaskActivity.class);

                String id = String.valueOf(taskItems.get(position).getItemID());
                editActivityLauncher.putExtra(TASK_ID,id);
                editActivityLauncher.putExtra(ACCOUNT_UID,uid);

                context.startActivity(editActivityLauncher);
            }
        });
    }

    @Override
    public int getItemCount(){
        return taskItems.size();
    }

    public void setTasks(List<TaskItem> taskItems){
        this.taskItems.clear();
        this.taskItems.addAll(taskItems);
        allTasksProgressBar.setVisibility(View.GONE);
        if (this.taskItems.isEmpty()){
            noTasksTextView.setVisibility(View.VISIBLE);
        }else {
            noTasksTextView.setVisibility(View.GONE);
        }
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
