
package ph.newsim.mobile.newsim.ui;

import android.support.design.widget.TabLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import ph.newsim.mobile.newsim.R;
import ph.newsim.mobile.newsim.adapter.CourseOfferedPagerAdapter;
import ph.newsim.mobile.newsim.database.BranchesDataSource;
import ph.newsim.mobile.newsim.fragments.CourseListFragment;

public class CoursesOfferedActivity extends AppCompatActivity {

    private CourseListFragment mListFragmentBacolod;
    private CourseListFragment mListFragmentCebu;
    private CourseListFragment mListFragmentDavao;
    private CourseListFragment mListFragmentIloilo;
    private CourseListFragment mListFragmentMakati;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_courses_offered);

        // Toolbar Initialization
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        assert toolbar != null;
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CoursesOfferedActivity.this.finish();
            }
        });
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        mListFragmentBacolod = CourseListFragment.newInstance(BranchesDataSource.BRANCH_BACOLOD);
        mListFragmentCebu = CourseListFragment.newInstance(BranchesDataSource.BRANCH_CEBU);
        mListFragmentDavao = CourseListFragment.newInstance(BranchesDataSource.BRANCH_DAVAO);
        mListFragmentIloilo = CourseListFragment.newInstance(BranchesDataSource.BRANCH_ILOILO);
        mListFragmentMakati = CourseListFragment.newInstance(BranchesDataSource.BRANCH_MAKATI);

        CourseOfferedPagerAdapter courseOfferedPagerAdapter = new CourseOfferedPagerAdapter(getSupportFragmentManager());
        courseOfferedPagerAdapter.setListFragmentBacolod(mListFragmentBacolod);
        courseOfferedPagerAdapter.setListFragmentCebu(mListFragmentCebu);
        courseOfferedPagerAdapter.setListFragmentDavao(mListFragmentDavao);
        courseOfferedPagerAdapter.setListFragmentIloilo(mListFragmentIloilo);
        courseOfferedPagerAdapter.setListFragmentMakati(mListFragmentMakati);

        ViewPager viewPager = (ViewPager) findViewById(R.id.container);
        assert viewPager != null;
        viewPager.setAdapter(courseOfferedPagerAdapter);
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                Log.i("CoursesOfferedActivity", "ViewPager Position: " + position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
        assert tabLayout != null;
        tabLayout.setupWithViewPager(viewPager);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_courses_offered, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        return super.onOptionsItemSelected(item);
    }

}
