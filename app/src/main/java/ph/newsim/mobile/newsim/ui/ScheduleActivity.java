package ph.newsim.mobile.newsim.ui;

import android.app.SearchManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.provider.SearchRecentSuggestions;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import org.apache.commons.lang3.text.WordUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

import ph.newsim.mobile.newsim.R;
import ph.newsim.mobile.newsim.adapter.ScheduleListAdapter;
import ph.newsim.mobile.newsim.database.BranchesDataSource;
import ph.newsim.mobile.newsim.database.LocalDataSource;
import ph.newsim.mobile.newsim.fragments.BranchDialogFragment;
import ph.newsim.mobile.newsim.fragments.ScheduleListFragment;
import ph.newsim.mobile.newsim.fragments.ScheduleResultListFragment;
import ph.newsim.mobile.newsim.model.Branch;
import ph.newsim.mobile.newsim.model.Schedule;
import ph.newsim.mobile.newsim.util.JSONDataHandler;
import ph.newsim.mobile.newsim.util.SRequest;

public class ScheduleActivity extends AppCompatActivity implements View.OnClickListener{

    public static final String LIST_SCHEDULE_FRAGMENT = "list_schedule_fragment";
    public static final String LIST_SEARCH_RESULT_FRAGMENT = "list_search_result_fragment";

    private FragmentManager mFragmentManager;
    private ScheduleListFragment mScheduleListFragment;
    private ScheduleResultListFragment mScheduleResultListFragment;

    private ImageView mBranchProfileImage;
    private TextView mBranchName;
    private TextView mTelephoneNo;

    private int currentSelectedBranchId = 5;
    private Fragment currentDisplayedFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_schedule);

        // Toolbar Initialization
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        assert toolbar != null;
        toolbar.setTitle("Online Reservation");
        setSupportActionBar(toolbar);
        assert toolbar != null;
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ScheduleActivity.this.finish();
            }
        });
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        // Fragment Manager Initialization
        mFragmentManager = getSupportFragmentManager();

        // Fragments Initialization
        mScheduleListFragment = new ScheduleListFragment();
        mScheduleResultListFragment = new ScheduleResultListFragment();
        displayList();

        // BranchLayout Initialization
        RelativeLayout layoutBranch = (RelativeLayout) findViewById(R.id.layout_branch);
        assert layoutBranch != null;
        layoutBranch.setOnClickListener(this);

        mBranchProfileImage = (ImageView) findViewById(R.id.img_branch_profile);
        mBranchName = (TextView) findViewById(R.id.label_branch_name);
        mTelephoneNo = (TextView) findViewById(R.id.label_telephone_no);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_schedule, menu);
        MenuItem searchItem = menu.findItem(R.id.action_search);
        MenuItemCompat.setOnActionExpandListener(searchItem, new MenuItemCompat.OnActionExpandListener() {
            @Override
            public boolean onMenuItemActionExpand(MenuItem item) {
                Log.i("ScheduleActivity", "SearchView expand");
                return true;
            }

            @Override
            public boolean onMenuItemActionCollapse(MenuItem item) {
                Log.i("ScheduleActivity", "SearchView collapse");
                displayList();
                return true;
            }
        });
        SearchView searchView = (SearchView) MenuItemCompat.getActionView(searchItem);
        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        ComponentName componentName = new ComponentName(this, ScheduleActivity.class);
        searchView.setSearchableInfo(searchManager.getSearchableInfo(componentName));
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_search){
            // Todo: Code for search button
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        setIntent(intent);
        if (Intent.ACTION_SEARCH.equals(intent.getAction())) {
            String query = intent.getStringExtra(SearchManager.QUERY);
            displaySearchResult();
            mScheduleResultListFragment.startSearch(query, currentSelectedBranchId);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.layout_branch:
                showBranchPickerDialog();
                break;
        }
    }

    private void displayList(){
        mFragmentManager.beginTransaction().replace(R.id.schedule_place_holder, mScheduleListFragment, LIST_SCHEDULE_FRAGMENT).commit();
        currentDisplayedFragment = mScheduleListFragment;
    }

    private void displaySearchResult(){
        mFragmentManager.beginTransaction().replace(R.id.schedule_place_holder, mScheduleResultListFragment, LIST_SEARCH_RESULT_FRAGMENT).commit();
        currentDisplayedFragment = mScheduleResultListFragment;
    }

    private void showBranchPickerDialog(){
        BranchDialogFragment branchDialogFragment = new BranchDialogFragment();
        branchDialogFragment.setCheckedItem(currentSelectedBranchId - 1);
        branchDialogFragment.setOnClickListener(new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                setSelectedBranch(which + 1);
            }
        });
        branchDialogFragment.show(getSupportFragmentManager(), "branchPicker");
    }

    private void setSelectedBranch(int branchId){
        BranchesDataSource branchesDataSource = new BranchesDataSource();
        Branch branch  = branchesDataSource.getBranchById(branchId);
        Glide.with(this).load(branch.getCoverPhoto()).centerCrop().into(mBranchProfileImage);
        mBranchName.setText(WordUtils.capitalizeFully(branch.getName()));
        mTelephoneNo.setText(branch.getPhoneNumber());
        currentSelectedBranchId = branchId;
        if (currentDisplayedFragment == mScheduleListFragment){
            mScheduleListFragment.notifySelectedBranchChanged(branchId);
        }else{
            mScheduleResultListFragment.notifySelectedBranchChanged(branchId);
        }
    }
}
