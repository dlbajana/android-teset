package ph.newsim.mobile.newsim.viewholder;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import ph.newsim.mobile.newsim.R;
import ph.newsim.mobile.newsim.database.BranchesDataSource;
import ph.newsim.mobile.newsim.model.Branch;
import ph.newsim.mobile.newsim.model.Post;

public class PostDetailHeaderWithImageVH extends RecyclerView.ViewHolder{

    private TextView mTitle;
    private TextView mDescription;
    private TextView mDate;
    private TextView mBranchName;
    private ImageView mPhoto;
    private ImageView mPostProfileImage;

    public PostDetailHeaderWithImageVH(View itemView) {
        super(itemView);
        mPostProfileImage = (ImageView) itemView.findViewById(R.id.img_branch_profile);
        mPhoto = (ImageView) itemView.findViewById(R.id.img_post_image);
        mBranchName = (TextView) itemView.findViewById(R.id.label_branch_name);
        mTitle = (TextView) itemView.findViewById(R.id.label_post_title);
        mDescription = (TextView) itemView.findViewById(R.id.label_post_description);
        mDate = (TextView) itemView.findViewById(R.id.label_post_date);
    }

    public void bindData(Context context, Post post){
        BranchesDataSource branchesDataSource = new BranchesDataSource();
        Branch branch = branchesDataSource.getBranchByCode(post.getBranch());

        Glide.with(context).load(branch.getCoverPhoto()).diskCacheStrategy(DiskCacheStrategy.SOURCE).centerCrop().into(mPostProfileImage);
        Glide.with(context).load(post.getImages()[0]).diskCacheStrategy(DiskCacheStrategy.SOURCE).centerCrop().into(mPhoto);
        mBranchName.setText(branch.getName());
        mTitle.setText(post.getTitle());
        mDescription.setText(post.getDescription());
        mDate.setText(post.getRelativeDate());
    }

}
