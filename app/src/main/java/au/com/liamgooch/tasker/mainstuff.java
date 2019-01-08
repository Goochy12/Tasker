//package au.com.scroogetech.tasker;
//
//import android.arch.lifecycle.Observer;
//import android.arch.lifecycle.ViewModelProviders;
//import android.content.Intent;
//import android.os.Bundle;
//import android.support.annotation.Nullable;
//import android.support.design.widget.FloatingActionButton;
//import android.support.v4.app.Fragment;
//import android.support.v4.app.FragmentManager;
//import android.support.v4.app.FragmentTransaction;
//import android.support.v7.app.AppCompatActivity;
//import android.support.v7.widget.LinearLayoutManager;
//import android.support.v7.widget.RecyclerView;
//import android.support.v7.widget.Toolbar;
//import android.view.View;
//import android.view.Menu;
//import android.view.MenuItem;
//import android.widget.TextView;
//
//import java.util.List;
//
//import au.com.scroogetech.tasker.data.TaskItem;
//import au.com.scroogetech.tasker.data.TaskItemDatabase;
//
//public class Login extends AppCompatActivity {
//
//    private TextView noTasksMessage;
//
//    private RecyclerView taskRecycler;
//    private TaskRecyclerAdapter taskRecyclerAdapter;
//    private RecyclerView.LayoutManager taskRecyclerLayoutManager;
//
//    private TaskItemDatabase db;
//    private TaskViewModel taskViewModel;
//
////    private FragmentManager fragmentManager = getSupportFragmentManager();
//////    private FragmentTransaction fragmentTransaction;
////    private Fragment addTaskFragment = new AddTaskFragment();
//
//
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        setTheme(R.style.SplashStyle);
//
//        super.onCreate(savedInstanceState);
//
//        setTheme(R.style.AppTheme_NoActionBar);
//        setContentView(R.layout.activity_login);
//
//        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
//        setSupportActionBar(toolbar);
//
//        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
//        fab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                fabOnClick();
//            }
//        });
//
//        //get the recycler
//        taskRecycler = (RecyclerView) findViewById(R.id.taskRecyclerView);
//
//        //use a linear layout
//        taskRecyclerLayoutManager = new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false);
//        taskRecycler.setLayoutManager(taskRecyclerLayoutManager);
//
//        taskRecyclerAdapter = new TaskRecyclerAdapter(this);
//        taskRecycler.setAdapter(taskRecyclerAdapter);
//
//
//
//        //get display items
//        noTasksMessage = (TextView) findViewById(R.id.noTasksTextView);
//
//        taskViewModel = ViewModelProviders.of(this).get(TaskViewModel.class);
//        taskViewModel.getAllTasks().observe(this, new Observer<List<TaskItem>>() {
//            @Override
//            public void onChanged(@Nullable List<TaskItem> taskItems) {
//                taskRecyclerAdapter.setTasks(taskItems);
//                if (taskItems.size() > 0){
//                    noTasksMessage.setVisibility(View.INVISIBLE);
//                }else{
//                    noTasksMessage.setVisibility(View.VISIBLE);
//                }
//
//            }
//        });
//    }
//
//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        // Inflate the menu; this adds items to the action bar if it is present.
//        getMenuInflater().inflate(R.menu.menu_main, menu);
//        return true;
//    }
//
//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        // Handle action bar item clicks here. The action bar will
//        // automatically handle clicks on the Home/Up button, so long
//        // as you specify a parent activity in AndroidManifest.xml.
//
//        switch (item.getItemId()){
//            case R.id.action_clear_selected:
//                taskViewModel.deleteCheckedTasks();
//
//                return true;
//            case R.id.action_clear_all:
//                taskViewModel.deleteAllTasks();
//                return true;
//            case R.id.action_settings:
//                return true;
//            default:
//                return super.onOptionsItemSelected(item);
//        }
//    }
//
//    //my functions
//
//    public void fabOnClick(){
////        taskViewModel.insert(new TaskItem("New Task","Desc",0));
//
//        //launch add task activity
//        newTask();
//
//
//    }
//
//    public void newTask(){
//        Intent launchAddTask = new Intent(this, AddTaskActivity.class);
//
//        startActivity(launchAddTask);
//    }
//}
