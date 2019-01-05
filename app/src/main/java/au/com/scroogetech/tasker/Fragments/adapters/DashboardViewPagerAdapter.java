package au.com.scroogetech.tasker.Fragments.adapters;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import au.com.scroogetech.tasker.Fragments.GroupsFragment;
import au.com.scroogetech.tasker.Fragments.ProjectsFragment;
import au.com.scroogetech.tasker.Fragments.TasksFragment;
import au.com.scroogetech.tasker.R;

public class DashboardViewPagerAdapter extends FragmentPagerAdapter {

    private static int NUM_ITEMS = 3;

    public DashboardViewPagerAdapter(@NonNull FragmentManager fm) {
        super(fm);
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0: // Fragment # 0 - This will show FirstFragment
                return new TasksFragment();
            case 1: // Fragment # 0 - This will show FirstFragment different title
                return new ProjectsFragment();
            case 2: // Fragment # 1 - This will show SecondFragment
                return new GroupsFragment();
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return NUM_ITEMS;
    }
}
