package ph.newsim.mobile.newsim.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.Collection;
import java.util.Collections;
import java.util.List;

import ph.newsim.mobile.newsim.R;
import ph.newsim.mobile.newsim.model.Branch;
import ph.newsim.mobile.newsim.viewholder.PlaceListVH;

public class PlaceListAdapter extends RecyclerView.Adapter<PlaceListVH>{

    public interface OnButtonClickListener{
        void onDirectionsClick(Branch branch);
        void onReviewClick(Branch branch);
    }

    private OnButtonClickListener mOnButtonClickListener;

    private Context mContext;
    private List<Branch> mDataSet = Collections.emptyList();

    // Constructors

    public PlaceListAdapter(Context context, List<Branch> dataSet) {
        mContext = context;
        mDataSet = dataSet;
    }

    @Override
    public PlaceListVH onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(mContext);
        View rootView = layoutInflater.inflate(R.layout.card_place, parent, false);
        return new PlaceListVH(rootView);
    }

    @Override
    public void onBindViewHolder(PlaceListVH holder, int position) {
        holder.bindData(mContext, mDataSet.get(position));
        holder.setOnButtonClickListener(mOnButtonClickListener);
    }

    @Override
    public int getItemCount() {
        return mDataSet == null ? 0 : mDataSet.size();
    }

    // Setters

    public void setOnButtonClickListener(OnButtonClickListener onButtonClickListener) {
        mOnButtonClickListener = onButtonClickListener;
    }

}
