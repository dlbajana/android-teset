package ph.newsim.mobile.newsim.viewholder;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import ph.newsim.mobile.newsim.R;
import ph.newsim.mobile.newsim.adapter.NewsListAdapter;
import ph.newsim.mobile.newsim.database.BranchesDataSource;
import ph.newsim.mobile.newsim.model.Branch;
import ph.newsim.mobile.newsim.model.Post;

public class PostListMultipleImageVH extends RecyclerView.ViewHolder implements View.OnClickListener{

    private NewsListAdapter.OnPostClickListener mOnPostClickListener;

    private Post mPost;
    private View mItemView;

    private TextView mPostTitle;
    private TextView mPostDate;
    private TextView mPostDescription;
    private ImageView mPostProfileImage;
    private ImageView mPostImage1;
    private ImageView mPostImage2;
    private ImageView mPostImage3;
    private ImageView mPostImage4;

    public PostListMultipleImageVH(View itemView) {
        super(itemView);
        mItemView = itemView;
        mPostTitle = (TextView) itemView.findViewById(R.id.label_post_title);
        mPostDate = (TextView) itemView.findViewById(R.id.label_post_date);
        mPostDescription = (TextView) itemView.findViewById(R.id.label_post_description);
        mPostProfileImage = (ImageView) itemView.findViewById(R.id.img_branch_profile);
        mPostImage1 = (ImageView) itemView.findViewById(R.id.img_post_image_1);
        mPostImage2 = (ImageView) itemView.findViewById(R.id.img_post_image_2);
        mPostImage3 = (ImageView) itemView.findViewById(R.id.img_post_image_3);
        mPostImage4 = (ImageView) itemView.findViewById(R.id.img_post_image_4);
    }

    public void bindData(Context context, Post post){
        BranchesDataSource branchesDataSource = new BranchesDataSource();
        Branch branch = branchesDataSource.getBranchByCode(post.getBranch());

        mPost = post;
        mPostTitle.setText(post.getTitle());
        mPostDescription.setText(post.getDescription());
        mPostDate.setText(branch.getName() + " • " + post.getRelativeDate());
        Glide.with(context).load(branch.getCoverPhoto()).diskCacheStrategy(DiskCacheStrategy.SOURCE).centerCrop().into(mPostProfileImage);

        if (mPost.getImages().length > 3){
            Glide.with(context).load(mPost.getImages()[0]).diskCacheStrategy(DiskCacheStrategy.SOURCE).into(mPostImage1);
            Glide.with(context).load(mPost.getImages()[1]).diskCacheStrategy(DiskCacheStrategy.SOURCE).into(mPostImage2);
            Glide.with(context).load(mPost.getImages()[2]).diskCacheStrategy(DiskCacheStrategy.SOURCE).into(mPostImage3);
            Glide.with(context).load(mPost.getImages()[3]).diskCacheStrategy(DiskCacheStrategy.SOURCE).into(mPostImage4);
        }else{
            Glide.with(context).load(mPost.getImages()[0]).diskCacheStrategy(DiskCacheStrategy.SOURCE).into(mPostImage1);
            Glide.with(context).load(mPost.getImages()[1]).diskCacheStrategy(DiskCacheStrategy.SOURCE).into(mPostImage2);
            Glide.with(context).load(mPost.getImages()[2]).diskCacheStrategy(DiskCacheStrategy.SOURCE).into(mPostImage3);
            mPostImage4.setVisibility(View.INVISIBLE);

        }
        mItemView.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        assert mOnPostClickListener !=null;
        mOnPostClickListener.onPostClick(mPost);
    }

    public void setOnPostClickListener(NewsListAdapter.OnPostClickListener onPostClickListener) {
        mOnPostClickListener = onPostClickListener;
    }

}
