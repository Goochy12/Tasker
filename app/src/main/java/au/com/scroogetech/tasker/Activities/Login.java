package au.com.scroogetech.tasker.Activities;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.View;
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

import static au.com.scroogetech.tasker.Activities.StartActivity.ACCOUNT_TYPE;
import static au.com.scroogetech.tasker.Activities.StartActivity.ACCOUNT_UID;
import static au.com.scroogetech.tasker.Activities.StartActivity.TAG;

import au.com.scroogetech.tasker.R;
import au.com.scroogetech.tasker.TaskRecyclerAdapter;
import au.com.scroogetech.tasker.TaskViewModel;
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
//
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
        Intent dashBoard = new Intent(this,Dashboard.class);
        sharedPreferences.edit().putBoolean("logged_in",true).apply();
        sharedPreferences.edit().putString("account_type",accountType).apply();
        sharedPreferences.edit().putString("uid",uid).apply();

        dashBoard.putExtra(ACCOUNT_TYPE,accountType);
        dashBoard.putExtra(ACCOUNT_UID,uid);
        startActivity(dashBoard);
        finish();

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
