package ph.newsim.mobile.newsim.fragments;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import ph.newsim.mobile.newsim.R;
import ph.newsim.mobile.newsim.adapter.ScheduleListAdapter;
import ph.newsim.mobile.newsim.database.LocalDataSource;
import ph.newsim.mobile.newsim.model.Schedule;
import ph.newsim.mobile.newsim.ui.ReservationActivity;
import ph.newsim.mobile.newsim.util.JSONDataHandler;
import ph.newsim.mobile.newsim.util.SRequest;

public class ScheduleResultListFragment extends Fragment implements ScheduleListAdapter.OnScheduleClickListener{

    private Context mContext;
    private List<Schedule> mScheduleDataSet = new ArrayList<Schedule>();

    private ScheduleListAdapter mScheduleListAdapter;
    private LinearLayoutManager mLinearLayoutManager;

    private final int visibleThreshold = 3;
    private int lastVisibleItem, totalItemCount;
    private boolean isLoading;

    private String mQuery;
    private int mBranchId = 5;

    public ScheduleResultListFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_schedule_result_list, container, false);

        mLinearLayoutManager = new LinearLayoutManager(getActivity());

        // ScheduleListAdapter Initialization
        mScheduleListAdapter = new ScheduleListAdapter(getActivity(), mScheduleDataSet);
        mScheduleListAdapter.setOnScheduleClickListener(this);

        RecyclerView recyclerView = (RecyclerView) rootView.findViewById(R.id.recycler_view_schedule_result);
        recyclerView.setLayoutManager(mLinearLayoutManager);
        recyclerView.setAdapter(mScheduleListAdapter);
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
        return rootView;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mContext = context;
    }

    @Override
    public void onScheduleClick(Schedule schedule) {
        startReservationActivity(schedule);
    }

    private void loadMore(int skipCount){
        mScheduleDataSet.add(null);
        mScheduleListAdapter.notifyItemInserted(mScheduleDataSet.size() - 1);

        SRequest request = new SRequest();
        request.pullMoreScheduleByQuery(mQuery, mBranchId, skipCount, new SRequest.RequestCallback() {

            @Override
            public void onSuccessful(JSONObject jsonData) {
                try {
                    mScheduleDataSet.remove(mScheduleDataSet.size() - 1);
                    mScheduleListAdapter.notifyItemRemoved(mScheduleDataSet.size() - 1);
                    storeScheduleData(jsonData);
                    mScheduleListAdapter.setDataSet(mScheduleDataSet);
                    mScheduleListAdapter.notifyDataSetChanged();
                    isLoading = false;
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailed() {
                mScheduleDataSet.remove(mScheduleDataSet.size() -1);
                mScheduleListAdapter.notifyItemRemoved(mScheduleDataSet.size() - 1);
                Schedule schedule = new Schedule();
                schedule.setViewType(Schedule.VIEW_TYPE_LIMIT);
                mScheduleDataSet.add(schedule);
                mScheduleListAdapter.notifyItemInserted(mScheduleDataSet.size());
                mScheduleListAdapter.notifyDataSetChanged();
            }

            @Override
            public void onError(Exception e) {
                e.printStackTrace();
            }
        });
    }

    public void startSearch(String query, int branchId){
        mQuery = query;
        mBranchId = branchId;
        isLoading = false;
        SRequest request = new SRequest();
        request.pullScheduleByQuery(query, mBranchId, new SRequest.RequestCallback() {
            @Override
            public void onSuccessful(JSONObject jsonData) {
                mScheduleDataSet.clear();
                try{
                    JSONArray jsonArraySchedule = jsonData.getJSONArray("schedule");
                    for(int i = 0; i < jsonArraySchedule.length(); i++){
                        JSONObject jsonSchedule = jsonArraySchedule.getJSONObject(i);
                        Schedule schedule = new Schedule(jsonSchedule.getInt("id"), jsonSchedule.getString("course"), jsonSchedule.getString("description"),
                                jsonSchedule.getString("duration"), jsonSchedule.getString("from_"), jsonSchedule.getString("to_"),
                                jsonSchedule.getString("batch"), jsonSchedule.getString("session"), jsonSchedule.getString("s_time_h"),
                                jsonSchedule.getString("s_time_m"), jsonSchedule.getString("e_time_h"), jsonSchedule.getString("e_time_m"),
                                jsonSchedule.getString("venue"), jsonSchedule.getString("room"), jsonSchedule.getString("ins1"),
                                jsonSchedule.getString("ass1"), jsonSchedule.getString("time_schedule"), jsonSchedule.getString("branch"), jsonSchedule.optInt("trainee_count", 0));
                        mScheduleDataSet.add(schedule);
                    }
                }catch (JSONException e){
                    Log.i("ScheduleResultList", "JSONException: " + e.getMessage());
                }
                mScheduleListAdapter.setDataSet(mScheduleDataSet);
                mScheduleListAdapter.notifyDataSetChanged();
            }

            @Override
            public void onFailed() {
                mScheduleDataSet.clear();
                mScheduleListAdapter.setDataSet(mScheduleDataSet);
                mScheduleListAdapter.notifyDataSetChanged();
                Log.i("ScheduleResultList", "Request Failed: ");
            }

            @Override
            public void onError(Exception e) {
                Log.i("ScheduleResultList", "Request Error: " + e.getMessage());
            }
        });

    }

    public void notifySelectedBranchChanged(int newBranchId){
        startSearch(mQuery, newBranchId);
    }

    private int storeScheduleData(JSONObject jsonData) throws JSONException{

        // Storing Schedule Table
        JSONArray jsonArraySchedule = jsonData.getJSONArray("schedule");
        for(int i = 0; i < jsonArraySchedule.length(); i++){
            JSONObject jsonSchedule = jsonArraySchedule.getJSONObject(i);
            Schedule schedule = new Schedule(jsonSchedule.getInt("id"), jsonSchedule.getString("course"), jsonSchedule.getString("description"),
                    jsonSchedule.getString("duration"), jsonSchedule.getString("from_"), jsonSchedule.getString("to_"),
                    jsonSchedule.getString("batch"), jsonSchedule.getString("session"), jsonSchedule.getString("s_time_h"),
                    jsonSchedule.getString("s_time_m"), jsonSchedule.getString("e_time_h"), jsonSchedule.getString("e_time_m"),
                    jsonSchedule.getString("venue"), jsonSchedule.getString("room"), jsonSchedule.getString("ins1"),
                    jsonSchedule.getString("ass1"), jsonSchedule.getString("time_schedule"), jsonSchedule.getString("branch"), jsonSchedule.optInt("trainee_count", 0));
            mScheduleDataSet.add(schedule);
        }
        return jsonArraySchedule.length();
    }

    private void startReservationActivity(Schedule schedule){
        Intent intent = new Intent(mContext, ReservationActivity.class);
        intent.putExtra(Schedule.KEY, schedule);
        startActivity(intent);
    }
}
