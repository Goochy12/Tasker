package au.com.liamgooch.tasker;

import android.app.Application;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

import au.com.liamgooch.tasker.data.TaskItem;
import au.com.liamgooch.tasker.data.TaskRepository;

public class TaskViewModel extends AndroidViewModel {

    private TaskRepository taskRepository;
    private LiveData<List<TaskItem>> allTasks;


    public TaskViewModel(Application application) {
        super(application);

        taskRepository = new TaskRepository(application);
        allTasks = taskRepository.getAllTasks();
    }

    public LiveData<List<TaskItem>> getAllTasks(){return allTasks;}

    public void insert(TaskItem taskItem){
        taskRepository.insertTask(taskItem);
    }

    public void deleteAllTasks(){
        taskRepository.deleteAllTasks();
    }

    public List<TaskItem> getCheckedTasks(){
        return taskRepository.getCheckedTasks();
    }

    public void deleteCheckedTasks(){
        taskRepository.deleteCheckedTasks();
    }

    public void deleteTask(TaskItem taskItem){
        taskRepository.deleteTaskItem(taskItem);
    }

    public void setTaskChecked(TaskItem taskItem){
        taskRepository.setTaskChecked(taskItem);
    }

    public TaskItem getTaskItem(int itemID) { return taskRepository.getTaskItem(itemID); }

    public void updateTaskItem(TaskItem taskItem, int id) { taskRepository.updateTaskItem(taskItem, id); }
}
