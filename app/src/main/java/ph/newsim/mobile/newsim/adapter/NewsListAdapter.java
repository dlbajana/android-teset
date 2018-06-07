package ph.newsim.mobile.newsim.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.Collections;
import java.util.List;

import ph.newsim.mobile.newsim.R;
import ph.newsim.mobile.newsim.model.Post;
import ph.newsim.mobile.newsim.viewholder.PostListDualImageVH;
import ph.newsim.mobile.newsim.viewholder.PostListMultipleImageVH;
import ph.newsim.mobile.newsim.viewholder.PostListNoImageVH;
import ph.newsim.mobile.newsim.viewholder.PostListSingleImageVH;
import ph.newsim.mobile.newsim.viewholder.RowLimitVH;
import ph.newsim.mobile.newsim.viewholder.RowLoadingVH;

public class NewsListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{

    public interface OnPostClickListener{
        void onPostClick(Post post);
    }

    private OnPostClickListener mOnPostClickListener;

    private Context mContext;
    private List<Post> mDataSet = Collections.emptyList();

    // Constructors

    public NewsListAdapter(Context context, List<Post> dataSet) {
        mContext = context;
        mDataSet = dataSet;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(mContext);
        View rootView;
        switch (viewType){
            case Post.VIEW_TYPE_LOADING:
                rootView = layoutInflater.inflate(R.layout.row_loading, parent, false);
                return new RowLoadingVH(rootView);

            case Post.VIEW_TYPE_NO_IMAGE:
                rootView = layoutInflater.inflate(R.layout.card_post_no_image, parent, false);
                return new PostListNoImageVH(rootView);

            case Post.VIEW_TYPE_SINGLE_IMAGE:
                rootView = layoutInflater.inflate(R.layout.card_post_single_image, parent, false);
                return new PostListSingleImageVH(rootView);

            case Post.VIEW_TYPE_DUAL_IMAGE:
                rootView = layoutInflater.inflate(R.layout.card_post_dual_image, parent, false);
                return new PostListDualImageVH(rootView);

            case Post.VIEW_TYPE_MULTIPLE_IMAGE:
                rootView = layoutInflater.inflate(R.layout.card_post_multiple_image, parent, false);
                return new PostListMultipleImageVH(rootView);

            default:
                rootView = layoutInflater.inflate(R.layout.row_limit, parent, false);
                return new RowLimitVH(rootView);
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        Post currentPost = mDataSet.get(position);

        if (holder instanceof PostListNoImageVH){
            ((PostListNoImageVH) holder).bindData(mContext, currentPost);
            if (mOnPostClickListener != null){
                ((PostListNoImageVH) holder).setOnPostClickListener(mOnPostClickListener);
            }

        }else if (holder instanceof PostListSingleImageVH){
            ((PostListSingleImageVH) holder).bindData(mContext, currentPost);
            if (mOnPostClickListener != null){
                ((PostListSingleImageVH) holder).setOnPostClickListener(mOnPostClickListener);
            }

        }else if (holder instanceof  PostListDualImageVH){
            ((PostListDualImageVH) holder).bindData(mContext, currentPost);
            if (mOnPostClickListener != null){
                ((PostListDualImageVH) holder).setOnPostClickListener(mOnPostClickListener);
            }

        }else if (holder instanceof  PostListMultipleImageVH){
            ((PostListMultipleImageVH) holder).bindData(mContext, currentPost);
            if (mOnPostClickListener != null){
                ((PostListMultipleImageVH) holder).setOnPostClickListener(mOnPostClickListener);
            }
        }
    }

    @Override
    public int getItemCount() {
        return mDataSet == null ? 0 : mDataSet.size();
    }

    @Override
    public int getItemViewType(int position) {
        return mDataSet.get(position) == null ? Post.VIEW_TYPE_LOADING : mDataSet.get(position).getViewType();
    }

    // Setters

    public void setOnPostClickListener(OnPostClickListener onPostClickListener) {
        mOnPostClickListener = onPostClickListener;
    }

    public void setDataSet(List<Post> dataSet) {
        mDataSet = dataSet;
    }
}
