package ph.newsim.mobile.newsim.ui;

import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

import org.json.JSONObject;

import ph.newsim.mobile.newsim.R;
import ph.newsim.mobile.newsim.adapter.SignUpPagerAdapter;
import ph.newsim.mobile.newsim.custom.SignUpViewPager;
import ph.newsim.mobile.newsim.custom.SwipeDirection;
import ph.newsim.mobile.newsim.fragments.SignUpFragment;
import ph.newsim.mobile.newsim.fragments.SignUpResultFragment;
import ph.newsim.mobile.newsim.model.User;
import ph.newsim.mobile.newsim.util.JSONDataHandler;
import ph.newsim.mobile.newsim.util.SRegister;

public class SignUpActivity extends AppCompatActivity {

    private SignUpViewPager mSignUpViewPager;
    private SignUpResultFragment mSignUpResultFragment;
    private CollapsingToolbarLayout mCollapsingToolbarLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        // Toolbar Initialization
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        assert toolbar != null;
        setSupportActionBar(toolbar);

        // Init Collapsing Toolbar
        mCollapsingToolbarLayout = (CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar_register);
        assert mCollapsingToolbarLayout != null;
        mCollapsingToolbarLayout.setTitle("Sign Up");

        // Cover Image Initialization
        ImageView coverImage = (ImageView) findViewById(R.id.img_sign_up_cover_photo);
        assert coverImage != null;
        coverImage.setColorFilter(Color.argb(180, 236, 28, 36));
        Glide.with(this).load(R.drawable.cover_team).centerCrop().into(coverImage);

        // SignUpFragment Initialization
        SignUpFragment signUpFragment = SignUpFragment.newInstance();
        signUpFragment.setRegisterCallback(new SRegister.RegisterCallback() {
            @Override
            public void onSuccessful(JSONObject jsonError, JSONObject jsonUser) {
                User user = JSONDataHandler.toUser(jsonUser);
                displayResultFragment(user);
            }

            @Override
            public void onFailed() {
                // Ignored
            }

            @Override
            public void onError(Exception e) {
                // Ignored
            }
        });

        // SignUpResultFragment Initialization
        mSignUpResultFragment = SignUpResultFragment.newInstance();

        // ViewPagerAdapter Initialization
        SignUpPagerAdapter signUpPagerAdapter = new SignUpPagerAdapter(getSupportFragmentManager(), signUpFragment, mSignUpResultFragment);

        // ViewPager Initialization
        mSignUpViewPager = (SignUpViewPager) findViewById(R.id.view_pager_sign_up);
        assert mSignUpViewPager != null;
        mSignUpViewPager.setAdapter(signUpPagerAdapter);
        mSignUpViewPager.setAllowedSwipeDirection(SwipeDirection.none);
    }

    private void displayResultFragment(User user){
        mCollapsingToolbarLayout.setTitle("Welcome!");
        mSignUpViewPager.setCurrentItem(1);
        mSignUpResultFragment.setUser(user);
    }

}
