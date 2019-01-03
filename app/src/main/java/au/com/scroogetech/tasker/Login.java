package au.com.scroogetech.tasker;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.TextView;

import java.util.List;

import au.com.scroogetech.tasker.data.TaskItem;
import au.com.scroogetech.tasker.data.TaskItemDatabase;

public class Login extends AppCompatActivity {

    private TextView noTasksMessage;

    private RecyclerView taskRecycler;
    private TaskRecyclerAdapter taskRecyclerAdapter;
    private RecyclerView.LayoutManager taskRecyclerLayoutManager;

    private TaskItemDatabase db;
    private TaskViewModel taskViewModel;

    private boolean userLoggedIn = false;
    private String accountType;

    private Button loginButton;
    private TextView createAccountButton;
    private Context context;
    private SharedPreferences sharedPreferences;

//    private FragmentManager fragmentManager = getSupportFragmentManager();
////    private FragmentTransaction fragmentTransaction;
//    private Fragment addTaskFragment = new AddTaskFragment();



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //set splash theme
        setTheme(R.style.SplashStyle);

        super.onCreate(savedInstanceState);

        //set regular theme and inflate the layout
        setTheme(R.style.AppTheme_NoActionBar);
        setContentView(R.layout.activity_login);

        //get the context of this activity
        context = this;
        //get the shared preferences for the login data
        sharedPreferences = getSharedPreferences("login",MODE_PRIVATE);

        //get button views
        loginButton = (Button) findViewById(R.id.loginButton);
        createAccountButton = (TextView) findViewById(R.id.createAccountView);

        //set a listener for the login button
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getAccountType();
                openDashboard();
            }
        });

        //set a listener for create account button
        createAccountButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent createAccountIntent = new Intent(context,CreateAccount.class);
                startActivity(createAccountIntent);
            }
        });

//
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
    }

    private void openDashboard(){
        Intent dashBoard;
        if (accountType.isEmpty()){
//            dashBoard = new Intent(this,);
        }
        else if (accountType.equals("admin")){

        }else{

        }
    }

    private void getAccountType(){
//        this.accountType = "admin";
    }
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
}
