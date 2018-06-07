package ph.newsim.mobile.newsim.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import ph.newsim.mobile.newsim.fragments.SignUpFragment;
import ph.newsim.mobile.newsim.fragments.SignUpResultFragment;

public class SignUpPagerAdapter extends FragmentPagerAdapter{

    private SignUpFragment mSignUpFragment;
    private SignUpResultFragment mSignUpResultFragment;

    public SignUpPagerAdapter(FragmentManager fm, SignUpFragment signUpFragment, SignUpResultFragment signUpResultFragment) {
        super(fm);
        mSignUpFragment = signUpFragment;
        mSignUpResultFragment = signUpResultFragment;
    }

    @Override
    public Fragment getItem(int position) {
        return position == 0 ? mSignUpFragment : mSignUpResultFragment;
    }

    @Override
    public int getCount() {
        return 2;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        switch (position) {
            case 0:
                return "Register Main";
            case 1:
                return "Register Success";
        }
        return null;
    }
}
