package au.com.scroogetech.tasker.data;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import io.reactivex.annotations.NonNull;

@Entity(tableName = "tasklist_table")
public class TaskItem {

    @PrimaryKey(autoGenerate = true)
    @NonNull
    private int itemID;

    private String taskName;
    private String taskDesc;
    private int taskChecked;

    public TaskItem(String taskName, String taskDesc, int taskChecked){
        this.taskName = taskName;
        this.taskDesc = taskDesc;
        this.taskChecked = taskChecked;
    }

    public int getItemID() {
        return itemID;
    }

    public void setItemID(int itemID) {
        this.itemID = itemID;
    }

    public String getTaskName() {
        return taskName;
    }

    public void setTaskName(String taskName) {
        this.taskName = taskName;
    }

    public String getTaskDesc() {
        return taskDesc;
    }

    public void setTaskDesc(String taskDesc) {
        this.taskDesc = taskDesc;
    }

    public int getTaskChecked() {
        return taskChecked;
    }

    public void setTaskChecked(int taskChecked) {
        this.taskChecked = taskChecked;
    }
}
