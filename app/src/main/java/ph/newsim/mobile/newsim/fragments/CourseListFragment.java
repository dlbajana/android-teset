package ph.newsim.mobile.newsim.fragments;


import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Collections;
import java.util.List;

import ph.newsim.mobile.newsim.R;
import ph.newsim.mobile.newsim.adapter.CourseListAdapter;
import ph.newsim.mobile.newsim.database.LocalDataSource;
import ph.newsim.mobile.newsim.model.Course;
import ph.newsim.mobile.newsim.util.SRequest;

public class CourseListFragment extends Fragment {

    public static final String KEY = "branch_id";

    public static final int LIST_TYPE_EMPTY = -1;
    public static final int LIST_TYPE_ALL = 0;
    public static final int LIST_TYPE_SEARCH = 1;

    private int mBranchId;
    private int mListType = LIST_TYPE_ALL;

    private Context mContext;
    private LocalDataSource mLocalDataSource;
    private List<Course> mCourseDataSet;
    private List<Course> mSearchCourseDataSet = Collections.emptyList();

    private CourseListAdapter mCourseListAdapter;
    private LinearLayoutManager mLinearLayoutManager;

    private final int visibleThreshold = 3;
    private int lastVisibleItem, totalItemCount;
    private boolean isLoading;

    public CourseListFragment() {

    }

    public static CourseListFragment newInstance(int branchId){
        Bundle args = new Bundle();
        args.putInt(KEY, branchId);

        CourseListFragment courseListFragment = new CourseListFragment();
        courseListFragment.setArguments(args);

        return courseListFragment;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        Log.i("CourseListFragment", "onAttach: " + mBranchId);
        mContext = context;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBranchId = getArguments() != null ? getArguments().getInt(KEY) : 0;
        Log.i("CourseListFragment", "onCreate: " + mBranchId);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        Log.i("CourseListFragment", "onCreateView: " + mBranchId);
        View rootView = inflater.inflate(R.layout.fragment_course_list, container, false);
        mLocalDataSource = new LocalDataSource(mContext);
        mCourseDataSet = mLocalDataSource.retrieveCourseByBranchId(mBranchId);

        mCourseListAdapter = new CourseListAdapter(mContext, mCourseDataSet);
        mLinearLayoutManager = new LinearLayoutManager(mContext);

        RecyclerView recyclerView = (RecyclerView) rootView.findViewById(R.id.recycler_view_course);
        recyclerView.setLayoutManager(mLinearLayoutManager);
        recyclerView.setAdapter(mCourseListAdapter);
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                totalItemCount = mLinearLayoutManager.getItemCount();
                lastVisibleItem = mLinearLayoutManager.findLastVisibleItemPosition();

                if (dy > 0){
                    if (!isLoading && totalItemCount <= (lastVisibleItem + visibleThreshold)) {
                        loadMore(totalItemCount);
                        isLoading = true;
                    }
                }
            }
        });

        if (mLinearLayoutManager.getItemCount() == 0){
            loadMore(0);
        }
        return rootView;

    }

    public void loadMore(int skipCount) {
        mCourseDataSet.add(null);
        mCourseListAdapter.notifyItemInserted(mCourseDataSet.size() - 1);

        SRequest request = new SRequest();
        request.pullCourse(mBranchId, skipCount, new SRequest.RequestCallback() {

            @Override
            public void onSuccessful(JSONObject jsonData) {
                try{
                    storeCourseData(jsonData);
                    mCourseDataSet.remove(mCourseDataSet.size() - 1);
                    mCourseListAdapter.notifyItemRemoved(mCourseDataSet.size() - 1);
                    mCourseDataSet = mLocalDataSource.retrieveCourseByBranchId(mBranchId);
                    mCourseListAdapter.setDataSet(mCourseDataSet);
                    mCourseListAdapter.notifyDataSetChanged();
                    isLoading = false;
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailed() {
                mCourseDataSet.remove(mCourseDataSet.size() - 1);
                mCourseListAdapter.notifyItemRemoved(mCourseDataSet.size() - 1);

                Course course = new Course();
                course.setViewType(Course.VIEW_TYPE_LIMIT);
                mCourseDataSet.add(course);
                mCourseListAdapter.notifyItemInserted(mCourseDataSet.size());
                mCourseListAdapter.notifyDataSetChanged();
            }

            @Override
            public void onError(Exception e) {
                e.printStackTrace();
            }
        });
    }

    public int storeCourseData(JSONObject jsonData) throws JSONException {
        // Storing Course Table
        JSONArray jsonArrayCourse = jsonData.getJSONArray("data");
        for(int i = 0; i < jsonArrayCourse.length(); i++){
            JSONObject jsonCourse = jsonArrayCourse.getJSONObject(i);
            Log.i("CourseListFragment", jsonCourse.getString("duration"));
            Course course = new Course(
                    jsonCourse.getInt("id"),
                    jsonCourse.getInt("branch"),
                    jsonCourse.getString("code"),
                    jsonCourse.getString("description"),
                    jsonCourse.getString("category"),
                    jsonCourse.getString("course_type"),
                    jsonCourse.optString("level", ""),
                    jsonCourse.getString("duration"),
                    jsonCourse.optString("status", ""));

            mLocalDataSource.store(course);
        }
        return jsonArrayCourse.length();
    }

    public void notifySearchStarted(String query){
        mListType = LIST_TYPE_SEARCH;

    }

    public void notifySearchStop(){
        mListType = LIST_TYPE_ALL;

    }
}