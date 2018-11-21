package au.com.scroogetech.tasker.data;

import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.os.AsyncTask;
import android.util.Log;

import java.util.List;

public class TaskRepository {

    private TaskItemDao mTaskDao;
    private LiveData<List<TaskItem>> mAllTasks;
    private List<TaskItem> allCheckedTasks;

    public TaskRepository(Application application){
        TaskItemDatabase db = TaskItemDatabase.getDatabase(application);
        mTaskDao = db.taskItemDao();
        mAllTasks = mTaskDao.getAllTasks();
        //allCheckedTasks = mTaskDao.getCheckedTasks();
    }

    public LiveData<List<TaskItem>> getAllTasks(){
        return mAllTasks;
    }

    public void insertTask(TaskItem taskItem){
        new insertAsyncTask(mTaskDao).execute(taskItem);
    }

    public void deleteAllTasks(){
        new deleteAllAsyncTask(mTaskDao).execute();
    }

    public void deleteTaskItem(TaskItem taskItem){
        new deleteTaskAsyncTask(mTaskDao).execute(taskItem);
    }

    public List<TaskItem> getCheckedTasks(){
        return allCheckedTasks;
    }

    public void deleteCheckedTasks(){
        new deleteCheckedTasksAsyncTask(mTaskDao).execute();
    }

    public void setTaskChecked(TaskItem taskItem){
        new setTaskCheckedAsyncTask(mTaskDao).execute(taskItem);
    }

    //async tasks

    private static class insertAsyncTask extends AsyncTask<TaskItem, Void, Void> {

        private TaskItemDao mAsyncTaskDao;

        insertAsyncTask(TaskItemDao dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(final TaskItem... params) {
            TaskItem newTaskItem = new TaskItem(params[0].getTaskName(),params[0].getTaskDesc(),params[0].getTaskChecked());
            mAsyncTaskDao.createNewTaskItem(newTaskItem);

            return null;
        }
    }

    private static class deleteAllAsyncTask extends AsyncTask<TaskItem, Void, Void> {

        private TaskItemDao mAsyncTaskDao;

        deleteAllAsyncTask(TaskItemDao dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(final TaskItem... params) {
            mAsyncTaskDao.deleteAllTasks();

            return null;
        }
    }

    private static class deleteTaskAsyncTask extends AsyncTask<TaskItem, Void, Void> {

        private TaskItemDao mAsyncTaskDao;

        deleteTaskAsyncTask(TaskItemDao dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(final TaskItem... params) {
            mAsyncTaskDao.deleteTaskItem(params[0].getItemID());

            return null;
        }
    }

    private static class deleteCheckedTasksAsyncTask extends AsyncTask<TaskItem, Void, Void> {

        private TaskItemDao mAsyncTaskDao;

        deleteCheckedTasksAsyncTask(TaskItemDao dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(final TaskItem... params) {
            mAsyncTaskDao.deleteCheckedItems();

            return null;
        }
    }

    private static class setTaskCheckedAsyncTask extends AsyncTask<TaskItem, Void, Void> {

        private TaskItemDao mAsyncTaskDao;

        setTaskCheckedAsyncTask(TaskItemDao dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(final TaskItem... params) {

            mAsyncTaskDao.setTaskChecked(params[0].getTaskChecked(),params[0].getItemID());

            return null;
        }
    }
}

