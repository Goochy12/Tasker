package au.com.scroogetech.tasker.data;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
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

                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(), TaskItemDatabase.class,"taskdb").build();
                }
            }
        }
        return INSTANCE;
    }
}
