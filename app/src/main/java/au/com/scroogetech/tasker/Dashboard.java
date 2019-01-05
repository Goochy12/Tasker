package au.com.scroogetech.tasker;

import android.content.Intent;
import android.os.Bundle;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import au.com.scroogetech.tasker.Fragments.GroupsFragment;
import au.com.scroogetech.tasker.Fragments.ProjectsFragment;
import au.com.scroogetech.tasker.Fragments.TasksFragment;

import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import static au.com.scroogetech.tasker.Login.ACCOUNT_TYPE;
import static au.com.scroogetech.tasker.Login.ACCOUNT_UID;

public class Dashboard extends AppCompatActivity {

    private FirebaseUser currentUser;
    private FirebaseAuth mAuth;

    private String account_type;
    private String uid;

    //bottom nav
    public BottomNavigationView navigation;
    private Menu dashAppBar;
    private MenuItem tasksItem;
    private MenuItem projectsItem;
    private MenuItem groupsItem;

    //fragments
    private FragmentManager fragMan;
    private Fragment taskFrag = new TasksFragment();
    private Fragment projectsFrag = new ProjectsFragment();
    private Fragment groupsFrag = new GroupsFragment();
    private Fragment active;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //bottom nav
        navigation = (BottomNavigationView) findViewById(R.id.dash_bottom_nav_activityRef);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        //set a null tint on the selected item
        navigation.setItemIconTintList(null);

        mAuth = FirebaseAuth.getInstance();

        Intent loginIntent = getIntent();
        account_type = loginIntent.getStringExtra(ACCOUNT_TYPE);
        uid = loginIntent.getStringExtra(ACCOUNT_UID);



        if (account_type.equals("admin")){
            setAdminLayout();
        }else {
            setUserLayout();
        }

        //set a null tint on the selected item
        navigation.setItemIconTintList(null);

        //get the items
        tasksItem = navigation.getMenu().getItem(0);
        projectsItem = navigation.getMenu().getItem(1);
        groupsItem = navigation.getMenu().getItem(2);
//
//        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
//        fab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
//            }
//        });
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
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {

            switch (item.getItemId()) {
                case R.id.bottom_nav_allTasks:
                    //mTextMessage.setText(R.string.title_home);

                    //set icon to clicked
//                    tasksItem.setIcon(R.drawable.tasksItemClicked);

                    //set other icons to not clicked
//                    projectsItem.setIcon(R.drawable.projects_default);
//                    groupsItem.setIcon(R.drawable.groups_default);

                    //load task fragment
                    fragMan.beginTransaction().hide(active).show(taskFrag).commit();
                    active = taskFrag;

                    return true;
                case R.id.bottom_nav_projects:


                    //load project fragment
                    fragMan.beginTransaction().hide(active).show(projectsFrag).commit();
                    active = projectsFrag;

                    return true;

                case R.id.bottom_nav_groups:

                    //load groups fragment
                    fragMan.beginTransaction().hide(active).show(groupsFrag).commit();
                    active = groupsFrag;

                    return true;
            }
            return false;
        }
    };

}
