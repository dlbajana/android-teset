package ph.newsim.mobile.newsim.ui;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.app.FragmentManager;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.facebook.login.LoginManager;

import org.apache.commons.lang3.text.WordUtils;

import ph.newsim.mobile.newsim.R;
import ph.newsim.mobile.newsim.database.LocalPreferences;
import ph.newsim.mobile.newsim.fragments.CourseListFragment;
import ph.newsim.mobile.newsim.fragments.NewsListFragment;
import ph.newsim.mobile.newsim.model.User;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener, View.OnClickListener {

    public static final String TAG_NEWS_FRAGMENT = "news_fragment";

    private User mUser;
    private LoginManager mLoginManager;

    private NewsListFragment mNewsListFragment;
    private CourseListFragment mCourseListFragment;
    private LocalPreferences mLocalPreferences;
    private ImageView mNavProfilePhoto;
    private ImageView mNavCoverPhoto;
    private TextView mNavFullName;
    private TextView mNavEmail;

    private FragmentManager mFragmentManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Toolbar Initialization
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_main);
        toolbar.setTitle("Home");
        setSupportActionBar(toolbar);

        // Floating Action Button
        final FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        assert fab != null;
        fab.setOnClickListener(this);

        // LoginManager Initialization
        mLoginManager = LoginManager.getInstance();

        // User Object Initialization
        mUser = getIntent().getParcelableExtra(User.KEY);

        // LocalPreferences Initialization
        mLocalPreferences = new LocalPreferences(this);
        mLocalPreferences.storeUserDetails(mUser);

        // DrawerLayout Initialization
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        assert drawer != null;
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        // NavigationView Initialization
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        assert navigationView != null;
        navigationView.setNavigationItemSelectedListener(this);

        // HeaderView Initialization
        View headerView = navigationView.getHeaderView(0);

        // CoverPhoto Initialization
        mNavCoverPhoto = (ImageView) headerView.findViewById(R.id.img_nav_header_cover);
        mNavCoverPhoto.setColorFilter(Color.argb(100, 0, 0, 0));

        // ProfilePhoto Initialization
        mNavProfilePhoto = (ImageView) headerView.findViewById(R.id.img_nav_header_profile_image);

        // FullName Initialization
        mNavFullName = (TextView) headerView.findViewById(R.id.label_nav_header_full_name);

        // Email Initialization
        mNavEmail = (TextView) headerView.findViewById(R.id.label_nav_header_email);

        // NewsListFragment Initialization
        mNewsListFragment = NewsListFragment.newInstance();

        // FragmentManager Initialization
        mFragmentManager = getSupportFragmentManager();
        mFragmentManager.beginTransaction().replace(R.id.main_place_holder, mNewsListFragment, TAG_NEWS_FRAGMENT).commit();

    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_places) {
            Intent intent = new Intent(this, PlacesActivity.class);
            startActivity(intent);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.nav_profile) {
            startProfileActivity();
        } else if (id == R.id.nav_course){
            startCourseOfferedActivity();
//        } else if (id == R.id.nav_reserve){
//            startReservationActivity();
        } else if (id == R.id.nav_certificate) {
            // Todo: Codes for Certificate Verification
        }else if (id == R.id.nav_account_settings) {
            startSettingsActivity();
        } else if (id == R.id.nav_sign_out) {
            signOutCurrentUser();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        assert drawer != null;
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    protected void onPostResume() {
        super.onPostResume();
        bindUserData(mLocalPreferences.retrieveUserDetails());
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.fab:
                mNewsListFragment.scrollToTop();
        }
    }

    private void signOutCurrentUser(){
        mLocalPreferences.setUserExist(false);
        mLoginManager.logOut();
        Glide.get(this).clearMemory();
        startLoginIndexActivity();
    }

    private void startLoginIndexActivity(){
        Intent intent = new Intent(this, LoginIndexActivity.class);
        startActivity(intent);
    }

    private void startProfileActivity(){
        Intent intent = new Intent(this, ProfileActivity.class);
        startActivity(intent);
    }

    private void startCourseOfferedActivity() {
        Intent intent = new Intent(this, CoursesOfferedActivity.class);
        startActivity(intent);
    }

    private void startReservationActivity(){
        Intent intent = new Intent(this, ScheduleActivity.class);
        startActivity(intent);
    }

    private void startSettingsActivity() {
        Intent intent = new Intent(this, SettingsActivity.class);
        startActivity(intent);
    }

    private void bindUserData(User user){
        Glide.with(this).load(user.getProfilePhotoURI()).diskCacheStrategy(DiskCacheStrategy.SOURCE).centerCrop().into(mNavProfilePhoto);
        Glide.with(this).load(user.getCoverPhotoURI()).diskCacheStrategy(DiskCacheStrategy.SOURCE).centerCrop().listener(new RequestListener<String, GlideDrawable>() {
            @Override
            public boolean onException(Exception e, String model, Target<GlideDrawable> target, boolean isFirstResource) {
                mNavCoverPhoto.setImageResource(R.drawable.default_cover_photo);
                mNavCoverPhoto.setColorFilter(Color.argb(0, 0, 0, 0));
                return true;
            }

            @Override
            public boolean onResourceReady(GlideDrawable resource, String model, Target<GlideDrawable> target, boolean isFromMemoryCache, boolean isFirstResource) {
                return false;
            }
        }).into(mNavCoverPhoto);
        mNavFullName.setText(WordUtils.capitalizeFully(user.getFirstName() + " " + user.getLastName()));
        mNavEmail.setText(user.getEmail());
    }

}
