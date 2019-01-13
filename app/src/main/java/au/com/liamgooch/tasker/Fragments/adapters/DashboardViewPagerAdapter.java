package au.com.liamgooch.tasker.Fragments.adapters;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import au.com.liamgooch.tasker.Fragments.GroupsFragment;
import au.com.liamgooch.tasker.Fragments.ProjectsFragment;
import au.com.liamgooch.tasker.Fragments.TasksFragment;

public class DashboardViewPagerAdapter extends FragmentPagerAdapter {

    private static int NUM_ITEMS = 3;
    private TasksFragment tasksFragment;
    private ProjectsFragment projectsFragment;
    private GroupsFragment groupsFragment;
    private FragmentManager fm;

    public DashboardViewPagerAdapter(@NonNull FragmentManager fm, TasksFragment tasksFragment, ProjectsFragment projectsFragment,
                                     GroupsFragment groupsFragment) {
        super(fm);
        this.fm = fm;
        this.tasksFragment = tasksFragment;
        this.projectsFragment = projectsFragment;
        this.groupsFragment = groupsFragment;
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0: // Fragment # 0 - This will show FirstFragment
                return tasksFragment;
            case 1: // Fragment # 0 - This will show FirstFragment different title
                return projectsFragment;
            case 2: // Fragment # 1 - This will show SecondFragment
                return groupsFragment;
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return NUM_ITEMS;
    }

}
