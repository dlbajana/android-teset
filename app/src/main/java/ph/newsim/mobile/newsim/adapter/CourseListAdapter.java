package ph.newsim.mobile.newsim.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.Collections;
import java.util.List;

import ph.newsim.mobile.newsim.R;
import ph.newsim.mobile.newsim.model.Course;
import ph.newsim.mobile.newsim.viewholder.CourseListVH;
import ph.newsim.mobile.newsim.viewholder.RowLimitVH;
import ph.newsim.mobile.newsim.viewholder.RowLoadingVH;

public class CourseListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{

    private Context mContext;
    private List<Course> mDataSet = Collections.emptyList();

    // Constructors

    public CourseListAdapter(Context context, List<Course> dataSet) {
        mContext = context;
        mDataSet = dataSet;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(mContext);
        View rootView;
        switch (viewType){
            case Course.VIEW_TYPE_LOADING:
                rootView = layoutInflater.inflate(R.layout.row_loading, parent, false);
                return new RowLoadingVH(rootView);
            case Course.VIEW_TYPE_CONTENT:
                rootView = layoutInflater.inflate(R.layout.row_course, parent, false);
                return new CourseListVH(rootView);
            default:
                rootView = layoutInflater.inflate(R.layout.row_limit, parent, false);
                return new RowLimitVH(rootView);
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        Course currentCourse = mDataSet.get(position);
        if (holder instanceof CourseListVH){
            ((CourseListVH) holder).bindData(mContext, currentCourse);
        }
    }

    @Override
    public int getItemCount() {
        return mDataSet == null ? 0 : mDataSet.size();
    }

    @Override
    public int getItemViewType(int position) {
        return mDataSet.get(position) == null ? Course.VIEW_TYPE_LOADING : mDataSet.get(position).getViewType();
    }

    // Setters

    public void setDataSet(List<Course> dataSet) {
        mDataSet = dataSet;
    }
}
