package au.com.liamgooch.tasker.Activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;
import au.com.liamgooch.tasker.Fragments.GroupsFragment;
import au.com.liamgooch.tasker.Fragments.ProjectsFragment;
import au.com.liamgooch.tasker.Fragments.TasksFragment;
import au.com.liamgooch.tasker.Fragments.adapters.DashboardViewPagerAdapter;
import au.com.liamgooch.tasker.R;
import au.com.liamgooch.tasker.data.Account_Type_Enum;
import au.com.liamgooch.tasker.data.ProjectItem;
import au.com.liamgooch.tasker.data.TaskItem;
import au.com.liamgooch.tasker.data.TaskSync;

import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import static android.view.View.VISIBLE;
import static au.com.liamgooch.tasker.data.String_Values.ACCOUNTS;
import static au.com.liamgooch.tasker.data.String_Values.ACCOUNT_TYPE;
import static au.com.liamgooch.tasker.data.String_Values.ACCOUNT_UID;
import static au.com.liamgooch.tasker.data.String_Values.ADMIN;
import static au.com.liamgooch.tasker.data.String_Values.EMAIL;
import static au.com.liamgooch.tasker.data.String_Values.TAG;
import static au.com.liamgooch.tasker.data.String_Values.TYPE;

public class Dashboard extends AppCompatActivity {

    private FirebaseUser currentUser;
    private FirebaseAuth mAuth;
    private FirebaseDatabase database;
    private DatabaseReference databaseReference;

    private String uid;

    //bottom nav
    public BottomNavigationView bottomNavigation;
    private Menu dashAppBar;
    private MenuItem tasksItem;
    private MenuItem projectsItem;
    private MenuItem groupsItem;

    //fragments
    private FragmentPagerAdapter adapterViewPager;
    private ViewPager vpPager;
    private FragmentManager fragMan;

    private TasksFragment tasksFrag;
    private ProjectsFragment projectsFrag;
    private GroupsFragment groupsFrag;

    private ArrayList<ArrayList<String>> raw_user_tasks = new ArrayList<>();
    private ArrayList<ArrayList<String>> raw_project_tasks = new ArrayList<>();
    private ArrayList<TaskItem> user_tasks;
    private ArrayList<ProjectItem> projects;
    private ArrayList<TaskItem> project_tasks;

    private Account_Type_Enum account_type;

    private SharedPreferences sharedPreferences;

    private ProgressBar dashBoardProgressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);
        setTheme(R.style.AppTheme_NoActionBar);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        dashBoardProgressBar = (ProgressBar) findViewById(R.id.dashBoardActivityProgressBar);
        dashBoardProgressBar.setVisibility(VISIBLE);
        dashBoardProgressBar.animate();

        //bottom nav
        bottomNavigation = (BottomNavigationView) findViewById(R.id.dash_bottom_nav_activityRef);

        //set a null tint on the selected item
        bottomNavigation.setItemIconTintList(null);

        mAuth = FirebaseAuth.getInstance();

        Intent loginIntent = getIntent();
        uid = loginIntent.getStringExtra(ACCOUNT_UID);

//        if(account_type.isEmpty()){
//            Intent error = new Intent(this,Login.class);
//            startActivity(error);
//            finish();
//        }

        getAccountType();

    }

    public void getAccountType(){

        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference().child(ACCOUNTS).child(uid);
        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String acc = dataSnapshot.child(TYPE).getValue(String.class);

                if (acc.equals("admin")){
                    account_type = Account_Type_Enum.ADMIN;
                }else {
                    account_type = Account_Type_Enum.USER;
                }

                loadViews();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

//        try {
//            if (account_type.equals(ADMIN)){
//                account_type_enum = Account_Type_Enum.ADMIN;
//                setAdminLayout();
//            }else {
//                setUserLayout();
//                account_type_enum = Account_Type_Enum.USER;
//            }
//        }catch (NullPointerException e){
//            Log.i(TAG, "onCreate: error");
//            Intent error = new Intent(this,Login.class);
//            startActivity(error);
//            finish();
//        }
    }

    private void loadViews(){
        bottomNavigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        //get the items
        tasksItem = bottomNavigation.getMenu().getItem(0);
        projectsItem = bottomNavigation.getMenu().getItem(1);
        groupsItem = bottomNavigation.getMenu().getItem(2);

        tasksFrag = new TasksFragment(user_tasks,project_tasks,uid, account_type);
        projectsFrag = new ProjectsFragment();
        groupsFrag = new GroupsFragment();

        vpPager = (ViewPager) findViewById(R.id.fragment_viewpager);

        adapterViewPager = new DashboardViewPagerAdapter(getSupportFragmentManager(),tasksFrag,projectsFrag,groupsFrag);
        vpPager.setAdapter(adapterViewPager);
        vpPager.setOffscreenPageLimit(3);
        vpPager.addOnPageChangeListener(mPageChangeListener);

        dashBoardProgressBar.setVisibility(View.GONE);

        TaskSync taskSync = new TaskSync(tasksFrag,projectsFrag,groupsFrag,uid,this);

        //load task data

        //check if local data exists
        int dbV = 0;
        int onlineDBV = 0;
        //local db version
//        sharedPreferences.getInt(DATABASE_VERSION,dbV);
        //online db version


//        if (dbV == 0){
//            //download from online if exists
//        }else if(dbV > onlineDBV){
//            //upload local to online
//        }else if (onlineDBV > dbV){
//            //download online db
//        }

        taskSync.syncTasks();
    }

//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        // Inflate the menu; this adds items to the action bar if it is present.
//        getMenuInflater().inflate(R.menu.menu_main, menu);
//        this.dashAppBar = menu;
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
////                taskViewModel.deleteCheckedTasks();
//
//                return true;
//            case R.id.action_clear_all:
////                taskViewModel.deleteAllTasks();
//                return true;
//            case R.id.action_settings:
//                return true;
//            case R.id.action_logout:
//                mAuth.signOut();
//
//                Intent loginIntent = new Intent(this,Login.class);
//                startActivity(loginIntent);
//
//                finish();
//                return true;
//            default:
//                return super.onOptionsItemSelected(item);
//        }
//    }

    private ViewPager.OnPageChangeListener mPageChangeListener = new ViewPager.OnPageChangeListener() {
        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

        }

        @Override
        public void onPageSelected(int position) {
            changePage(position);

        }

        @Override
        public void onPageScrollStateChanged(int state) {

        }
    };

    private void changePage(int position){
        switch (position) {
            case 0:
//
//                    //set icon to clicked
                    tasksItem.setIcon(R.drawable.ic_tasks_clicked);

                    //set other icons to not clicked
                    projectsItem.setIcon(R.drawable.ic_projects_default);
                    groupsItem.setIcon(R.drawable.ic_groups_default);

                    break;

            case 1:

                    //set icon to clicked
                    projectsItem.setIcon(R.drawable.ic_projects_clicked);

                    //set other icons to not clicked
                    tasksItem.setIcon(R.drawable.ic_tasks_default);
                    groupsItem.setIcon(R.drawable.ic_groups_default);

                break;

            case 2:
//                set icon to clicked
                    groupsItem.setIcon(R.drawable.ic_groups_clicked);

                    //set other icons to not clicked
                    tasksItem.setIcon(R.drawable.ic_tasks_default);
                    projectsItem.setIcon(R.drawable.ic_projects_default);

                break;
        }
    }

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {

            switch (item.getItemId()) {
                case R.id.bottom_nav_allTasks:

                    vpPager.setCurrentItem(0);

                    return true;
                case R.id.bottom_nav_projects:

                    vpPager.setCurrentItem(1);

                    return true;

                case R.id.bottom_nav_groups:

                    vpPager.setCurrentItem(2);

                    return true;
            }
            return false;
        }
    };

//TODO : check this over and redesign callback

    public void setUser_tasks(ArrayList<TaskItem> user_tasks) {
        this.user_tasks = user_tasks;
        //set task frag - notify change
        while (tasksFrag == null){

        }
        while (tasksFrag.taskRecyclerAdapter == null){

        }
        compileTasks();
//        tasksFrag.taskRecyclerAdapter.setTasks();
    }

    public void setProjects(ArrayList<ProjectItem> projectItems){
        this.projects = projectItems;
        projectsFrag.projectsRecyclerAdapter.setTasks(projects);
    }

    public void setProject_tasks(ArrayList<TaskItem> project_tasks) {
        this.project_tasks = project_tasks;
    }

    public void compileTasks(){
        ArrayList<TaskItem> taskItems = new ArrayList<>();
        if (user_tasks != null){
            taskItems.addAll(user_tasks);
        }
        if (project_tasks != null){
            taskItems.addAll(project_tasks);
        }
        tasksFrag.taskRecyclerAdapter.setTasks(taskItems);
    }


}
