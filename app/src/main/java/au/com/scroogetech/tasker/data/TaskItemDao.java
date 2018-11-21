package au.com.scroogetech.tasker.data;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import java.util.List;

@Dao
public interface TaskItemDao {

    @Query("SELECT * FROM TASKLIST_TABLE ORDER BY itemID ASC")
    LiveData<List<TaskItem>> getAllTasks();

    @Query("SELECT * FROM TASKLIST_TABLE WHERE taskChecked = 1")
    List<TaskItem> getCheckedTasks();

    @Query("DELETE FROM TASKLIST_TABLE WHERE taskChecked = 1")
    void deleteCheckedItems();

    @Query("UPDATE TASKLIST_TABLE SET taskChecked = :checked WHERE itemID = :taskID")
    void setTaskChecked(int checked, int taskID);

    @Query("SELECT * FROM TASKLIST_TABLE WHERE itemID = :itemID")
    LiveData<TaskItem> getTaskItemByID(String itemID);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void createNewTaskItem(TaskItem taskItem);

    @Query("DELETE FROM TASKLIST_TABLE WHERE itemID = :taskID")
    void deleteTaskItem(int taskID);

    @Query("DELETE From TASKLIST_TABLE")
    void deleteAllTasks();
}
