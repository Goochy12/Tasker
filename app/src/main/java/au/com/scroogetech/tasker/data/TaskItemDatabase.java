package au.com.scroogetech.tasker.data;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import android.content.Context;

@Database(entities = {TaskItem.class}, version = 1)
public abstract class TaskItemDatabase extends RoomDatabase {
    public abstract TaskItemDao taskItemDao();

    public static volatile TaskItemDatabase INSTANCE;

    static TaskItemDatabase getDatabase(final Context context){
        if(INSTANCE == null){
            synchronized (TaskItemDatabase.class){
                if (INSTANCE == null){
                    //create database here

                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(), TaskItemDatabase.class,"taskdb").allowMainThreadQueries()
                            .build();
                }
            }
        }
        return INSTANCE;
    }
}
