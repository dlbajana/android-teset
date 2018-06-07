package ph.newsim.mobile.newsim.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import ph.newsim.mobile.newsim.database.BranchesDataSource;
import ph.newsim.mobile.newsim.fragments.CourseListFragment;

public class CourseOfferedPagerAdapter extends FragmentStatePagerAdapter{

    private CourseListFragment mListFragmentBacolod;
    private CourseListFragment mListFragmentCebu;
    private CourseListFragment mListFragmentDavao;
    private CourseListFragment mListFragmentIloilo;
    private CourseListFragment mListFragmentMakati;

    public CourseOfferedPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        switch (position){
            case 0:
                return mListFragmentBacolod;
            case 1:
                return mListFragmentCebu;
            case 2:
                return mListFragmentDavao;
            case 3:
                return mListFragmentIloilo;
            case 4:
                return mListFragmentMakati;
            default:
                return mListFragmentMakati;
        }

    }

    @Override
    public int getCount() {
        return 5;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return BranchesDataSource.getBranchCode(position + 1);
    }

    public void setListFragmentBacolod(CourseListFragment listFragmentBacolod) {
        mListFragmentBacolod = listFragmentBacolod;
    }

    public void setListFragmentCebu(CourseListFragment listFragmentCebu) {
        mListFragmentCebu = listFragmentCebu;
    }

    public void setListFragmentDavao(CourseListFragment listFragmentDavao) {
        mListFragmentDavao = listFragmentDavao;
    }

    public void setListFragmentIloilo(CourseListFragment listFragmentIloilo) {
        mListFragmentIloilo = listFragmentIloilo;
    }

    public void setListFragmentMakati(CourseListFragment listFragmentMakati) {
        mListFragmentMakati = listFragmentMakati;
    }
}
