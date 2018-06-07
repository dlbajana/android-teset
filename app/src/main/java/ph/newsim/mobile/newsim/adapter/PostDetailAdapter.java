package ph.newsim.mobile.newsim.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import ph.newsim.mobile.newsim.R;
import ph.newsim.mobile.newsim.database.BranchesDataSource;
import ph.newsim.mobile.newsim.model.Post;
import ph.newsim.mobile.newsim.viewholder.PostBodyImageVH;
import ph.newsim.mobile.newsim.viewholder.PostDetailHeaderNoImageVH;
import ph.newsim.mobile.newsim.viewholder.PostDetailHeaderWithImageVH;

public class PostDetailAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{

    public static final int VIEW_TYPE_HEADER_NO_IMAGE = 0;
    public static final int VIEW_TYPE_HEADER_WITH_IMAGE = 1;
    public static final int VIEW_TYPE_BODY = 2;

    private Context mContext;
    private Post mPost;
    private BranchesDataSource mBranchesDataSource;

    public PostDetailAdapter(Context context, Post post) {
        mContext = context;
        mPost = post;
        mBranchesDataSource = new BranchesDataSource();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(mContext);
        View rootView;
        switch (viewType){
            case VIEW_TYPE_HEADER_NO_IMAGE:
                rootView = layoutInflater.inflate(R.layout.layout_post_header_no_image, parent, false);
                return new PostDetailHeaderNoImageVH(rootView);
            case VIEW_TYPE_HEADER_WITH_IMAGE:
                rootView = layoutInflater.inflate(R.layout.layout_post_header_with_image, parent, false);
                return new PostDetailHeaderWithImageVH(rootView);
            case VIEW_TYPE_BODY:
                rootView = layoutInflater.inflate(R.layout.layout_post_body_images, parent, false);
                return new PostBodyImageVH(rootView);
            default:
                return null;
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof PostDetailHeaderNoImageVH) {
            ((PostDetailHeaderNoImageVH) holder).bindData(mContext, mPost);
        }else if (holder instanceof PostDetailHeaderWithImageVH) {
            ((PostDetailHeaderWithImageVH) holder).bindData(mContext, mPost);
        }else if (holder instanceof PostBodyImageVH) {
            ((PostBodyImageVH) holder).bindData(mContext, mPost.getImages()[position - 1], mPost.getImgDescription()[position - 1]);
        }
    }

    @Override
    public int getItemCount() {
        int imagesCount = mPost.getImages().length;
        if(imagesCount <= 1){
            return 1;
        }else{
            return imagesCount + 1;
        }
    }

    @Override
    public int getItemViewType(int position) {
        int viewType;
        if (position == 0) {
            if (mPost.getImages().length == 1) {
                viewType = VIEW_TYPE_HEADER_WITH_IMAGE;
            } else {
                viewType = VIEW_TYPE_HEADER_NO_IMAGE;
            }
        } else {
            viewType = VIEW_TYPE_BODY;
        }
        return viewType;
    }
}
