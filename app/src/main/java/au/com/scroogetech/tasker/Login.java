package au.com.scroogetech.tasker;

import android.app.ProgressDialog;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.List;

import au.com.scroogetech.tasker.data.TaskItem;
import au.com.scroogetech.tasker.data.TaskItemDatabase;

public class Login extends AppCompatActivity {

    public static final String TAG = "TaskerTAG";
    public static final String ACCOUNT_TYPE = "account_type";
    public static final String ACCOUNT_UID = "account_uid";

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
    private TextView emailBox;
    private TextView passwordBox;

    private Context context;
    private SharedPreferences sharedPreferences;

    private FirebaseAuth mAuth;
    private FirebaseUser currentUser;
    private FirebaseDatabase database;
    private DatabaseReference userReference;
    private String uid;

//    private FragmentManager fragmentManager = getSupportFragmentManager();
////    private FragmentTransaction fragmentTransaction;
//    private Fragment addTaskFragment = new AddTaskFragment();

    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        currentUser = mAuth.getCurrentUser();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //set splash theme
        setTheme(R.style.SplashStyle);

        super.onCreate(savedInstanceState);

        //set regular theme and inflate the layout
        setTheme(R.style.AppTheme_NoActionBar);
        setContentView(R.layout.activity_login);

        //start progress bar/ dialog

        //get the context of this activity
        context = this;
        //get the shared preferences for the login data
        sharedPreferences = getSharedPreferences("login",MODE_PRIVATE);

        //initialise Firebase auth
        mAuth = FirebaseAuth.getInstance();
        //get user database
        database = FirebaseDatabase.getInstance();
        try{
            userReference = database.getReference("accounts");
        }catch (NullPointerException e){

        }

        //get button views
        loginButton = (Button) findViewById(R.id.loginButton);
        createAccountButton = (TextView) findViewById(R.id.createAccountView);

        //get user fields
        emailBox = (TextView) findViewById(R.id.enterEmailBox);
        passwordBox = (TextView) findViewById(R.id.enterPasswordBox);

        //set a listener for the login button
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) { {
                accountType = "admin";
                openDashboard();

//                String email = emailBox.getText().toString();
//                String password = passwordBox.getText().toString();
//
//                    mAuth.signInWithEmailAndPassword(email, password)
//                            .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
//                                @Override
//                                public void onComplete(@NonNull Task<AuthResult> task) {
//                                    if (task.isSuccessful()) {
//                                        // Sign in success, update UI with the signed-in user's information
//                                        Log.d(TAG, "signInWithEmail:success");
//                                        currentUser = mAuth.getCurrentUser();
//                                        uid = currentUser.getUid();
//                                        accountType = getAccountType();
//                                        openDashboard();
//                                    } else {
//                                        // If sign in fails, display a message to the user.
//                                        Log.w(TAG, "signInWithEmail:failure", task.getException());
//                                        Toast.makeText(context, "Authentication failed.",
//                                                Toast.LENGTH_SHORT).show();
//                                    }
//                                }
//                            });
                }
            }
        });

        //set a listener for create account button
        createAccountButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent createAccountIntent = new Intent(context,CreateAccount.class);
                startActivityForResult(createAccountIntent,1);
            }
        });

        //get the user and check if logged in
        currentUser = mAuth.getCurrentUser();
        if (currentUser != null){
            userLoggedIn = true;
            uid = currentUser.getUid();
            accountType = getAccountType();

            //open the dashboard
            openDashboard();
        }

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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1){
            if (resultCode == RESULT_OK){
                String emailText = data.getStringExtra("email");
                String passwordText = data.getStringExtra("password");
                emailBox.setText(emailText);
                passwordBox.setText(passwordText);
            }
        }
    }

    private void openDashboard(){
        //close progress bar/dialog

        Intent dashBoard = new Intent(this,Dashboard.class);
        sharedPreferences.edit().putBoolean("logged_in",true);
        sharedPreferences.edit().putString("account_type",accountType);
        sharedPreferences.edit().putString("uid",uid);

        dashBoard.putExtra(ACCOUNT_TYPE,accountType);
        dashBoard.putExtra(ACCOUNT_UID,uid);
        startActivity(dashBoard);

    }

    private String getAccountType(){
        DatabaseReference databaseReference = userReference.child(uid);
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                try{
                    accountType = dataSnapshot.child("type").getValue().toString();
                }catch (NullPointerException e){

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        return accountType;
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
