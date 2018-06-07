package ph.newsim.mobile.newsim.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.Collections;
import java.util.List;

import ph.newsim.mobile.newsim.R;
import ph.newsim.mobile.newsim.model.Course;
import ph.newsim.mobile.newsim.model.Schedule;
import ph.newsim.mobile.newsim.viewholder.RowLimitVH;
import ph.newsim.mobile.newsim.viewholder.RowLoadingVH;
import ph.newsim.mobile.newsim.viewholder.ScheduleListVH;

public class ScheduleListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{

    public interface OnScheduleClickListener{
        void onScheduleClick(Schedule schedule);
    }

    private OnScheduleClickListener mOnScheduleClickListener;

    private Context mContext;
    private List<Schedule> mDataSet = Collections.emptyList();

    public ScheduleListAdapter(Context context, List<Schedule> dataSet) {
        mContext = context;
        mDataSet = dataSet;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(mContext);
        View rootView;

        switch (viewType){
            case Schedule.VIEW_TYPE_LOADING:
                rootView = layoutInflater.inflate(R.layout.row_loading, parent, false);
                return new RowLoadingVH(rootView);
            case Schedule.VIEW_TYPE_CONTENT:
                rootView = layoutInflater.inflate(R.layout.row_schedule, parent, false);
                return new ScheduleListVH(rootView);
            default:
                rootView = layoutInflater.inflate(R.layout.row_limit, parent, false);
                return new RowLimitVH(rootView);
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        Schedule currentSchedule = mDataSet.get(position);

        if (holder instanceof ScheduleListVH){
            ((ScheduleListVH) holder).bindData(currentSchedule);
            if (mOnScheduleClickListener != null){
                ((ScheduleListVH) holder).setOnScheduleClickListener(mOnScheduleClickListener);
            }
        }
    }

    @Override
    public int getItemCount() {
        return mDataSet == null ? 0 : mDataSet.size();
    }

    @Override
    public int getItemViewType(int position) {
        return mDataSet.get(position) == null ? Schedule.VIEW_TYPE_LOADING : mDataSet.get(position).getViewType();
    }

    public void setOnScheduleClickListener(OnScheduleClickListener onScheduleClickListener) {
        mOnScheduleClickListener = onScheduleClickListener;
    }

    public void setDataSet(List<Schedule> dataSet) {
        mDataSet = dataSet;
    }
}
