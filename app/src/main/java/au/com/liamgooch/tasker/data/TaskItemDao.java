package au.com.liamgooch.tasker.data;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

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

    @Query("SELECT * FROM TASKLIST_TABLE WHERE itemID = :id")
    TaskItem getTaskItem(int id);

    @Query("UPDATE TASKLIST_TABLE SET taskName = :name, taskDesc = :desc, dayDue = :dayDue," +
            "monthDue = :monthDue, yearDue = :yearDue, hourDue = :hourDue, minuteDue = :minuteDue, taskChecked = :taskChecked," +
            "reminder = :reminder, timeDateString = :timeDateString WHERE itemID = :id")
    void updateTaskItem(int id, String name, String desc, int dayDue, int monthDue, int yearDue, int hourDue, int minuteDue, int taskChecked,
                        int reminder, String timeDateString);
}