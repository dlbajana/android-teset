package ph.newsim.mobile.newsim.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import ph.newsim.mobile.newsim.fragments.CourseListFragment;
import ph.newsim.mobile.newsim.fragments.NewsListFragment;

public class MainPagerAdapter extends FragmentPagerAdapter {

    private NewsListFragment mNewsListFragment;
    private CourseListFragment mCourseListFragment;

    public MainPagerAdapter(FragmentManager fm, NewsListFragment newsListFragment, CourseListFragment courseListFragment) {
        super(fm);
        mNewsListFragment = newsListFragment;
        mCourseListFragment = courseListFragment;
    }

    @Override
    public Fragment getItem(int position) {
        if (position == 0){
            return mNewsListFragment;
        }else{
            return mCourseListFragment;
        }
    }

    @Override
    public int getCount() {
        return 2;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        switch (position) {
            case 0:
                return "NEWS";
            case 1:
                return "COURSE";
        }
        return null;
    }
}
