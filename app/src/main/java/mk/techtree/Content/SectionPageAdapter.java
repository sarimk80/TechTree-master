package mk.techtree.Content;

import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.ArrayList;
import java.util.List;

public class SectionPageAdapter extends FragmentPagerAdapter {
    private final List<Fragment> fragments = new ArrayList<>();
    private final List<String> TitleList = new ArrayList<>();


    public SectionPageAdapter(FragmentManager fm) {
        super(fm);
    }

    public void AddFragments(Fragment fragment, String Title) {

        fragments.add(fragment);
        TitleList.add(Title);

    }

    @Override
    public Fragment getItem(int position) {
        return fragments.get(position);
    }


    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return TitleList.get(position);
    }

    @Override
    public int getCount() {
        return fragments.size();
    }
}
