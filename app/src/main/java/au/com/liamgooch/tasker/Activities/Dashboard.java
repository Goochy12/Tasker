package au.com.liamgooch.tasker.Activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;
import au.com.liamgooch.tasker.Fragments.GroupsFragment;
import au.com.liamgooch.tasker.Fragments.ProjectsFragment;
import au.com.liamgooch.tasker.Fragments.TasksFragment;
import au.com.liamgooch.tasker.Fragments.adapters.DashboardViewPagerAdapter;
import au.com.liamgooch.tasker.R;
import au.com.liamgooch.tasker.data.TaskItem;

import android.view.Menu;
import android.view.MenuItem;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import static au.com.liamgooch.tasker.Activities.StartActivity.ACCOUNT_TYPE;
import static au.com.liamgooch.tasker.Activities.StartActivity.ACCOUNT_UID;
import static au.com.liamgooch.tasker.Activities.StartActivity.DATABASE_VERSION;

public class Dashboard extends AppCompatActivity {

    private FirebaseUser currentUser;
    private FirebaseAuth mAuth;
    private FirebaseDatabase database;
    private DatabaseReference databaseReference;

    private String account_type;
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
    private List<TaskItem> user_tasks;
    private List<TaskItem> project_tasks;

    private SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);
        setTheme(R.style.AppTheme_NoActionBar);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //bottom nav
        bottomNavigation = (BottomNavigationView) findViewById(R.id.dash_bottom_nav_activityRef);
        bottomNavigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        //set a null tint on the selected item
        bottomNavigation.setItemIconTintList(null);

        mAuth = FirebaseAuth.getInstance();

        Intent loginIntent = getIntent();
        account_type = loginIntent.getStringExtra(ACCOUNT_TYPE);
        uid = loginIntent.getStringExtra(ACCOUNT_UID);


        if (account_type.equals("admin")){
            setAdminLayout();
        }else {
            setUserLayout();
        }

        //get the items
        tasksItem = bottomNavigation.getMenu().getItem(0);
        projectsItem = bottomNavigation.getMenu().getItem(1);
        groupsItem = bottomNavigation.getMenu().getItem(2);


        tasksFrag = new TasksFragment(user_tasks,project_tasks);
        projectsFrag = new ProjectsFragment();
        groupsFrag = new GroupsFragment();

        vpPager = (ViewPager) findViewById(R.id.fragment_viewpager);

        adapterViewPager = new DashboardViewPagerAdapter(getSupportFragmentManager(),tasksFrag,projectsFrag,groupsFrag);
        vpPager.setAdapter(adapterViewPager);
        vpPager.setOffscreenPageLimit(3);
        vpPager.addOnPageChangeListener(mPageChangeListener);


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
    }

    ValueEventListener user_tasks_value_event_listener = new ValueEventListener() {
        @Override
        public void onDataChange(DataSnapshot dataSnapshot) {

        }

        @Override
        public void onCancelled(DatabaseError databaseError) {

        }
    };

    ValueEventListener projects_value_event_listener = new ValueEventListener() {
        @Override
        public void onDataChange(DataSnapshot dataSnapshot) {

        }

        @Override
        public void onCancelled(DatabaseError databaseError) {

        }
    };

    public void convertToTasks(ArrayList<ArrayList<String>> tasks){
        for (int i = 0; i < tasks.size(); i ++){
            String name = tasks.get(i).get(0);
            String desc = tasks.get(i).get(1);
            String startDate = tasks.get(i).get(2);
            String startTime = tasks.get(i).get(3);
            String endDate = tasks.get(i).get(4);
            String endTime = tasks.get(i).get(5);
            String members = tasks.get(i).get(6);
            String[] groupMembers = members.split(",");
            String project = tasks.get(i).get(7);
        }
    }
    public void setAdminLayout(){

    }

    public void setUserLayout(){

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        this.dashAppBar = menu;
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.

        switch (item.getItemId()){
            case R.id.action_clear_selected:
//                taskViewModel.deleteCheckedTasks();

                return true;
            case R.id.action_clear_all:
//                taskViewModel.deleteAllTasks();
                return true;
            case R.id.action_settings:
                return true;
            case R.id.action_logout:
                mAuth.signOut();

                Intent loginIntent = new Intent(this,Login.class);
                startActivity(loginIntent);

                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

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


}
