package ph.newsim.mobile.newsim.viewholder;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.signature.StringSignature;

import ph.newsim.mobile.newsim.R;

public class PostBodyImageVH extends RecyclerView.ViewHolder{

    private ImageView mPhoto;
    private TextView mPhotoDescription;

    public PostBodyImageVH(View itemView) {
        super(itemView);
        mPhoto = (ImageView) itemView.findViewById(R.id.img_post_image);
        mPhotoDescription = (TextView) itemView.findViewById(R.id.label_image_description);
    }

    public void bindData(Context context, String imageURI, String imageDescription){
        Glide.with(context).load(imageURI).diskCacheStrategy(DiskCacheStrategy.SOURCE).fitCenter().into(mPhoto);
        if (imageDescription.equals("")){
            mPhotoDescription.setVisibility(View.GONE);
        }else{
            mPhotoDescription.setVisibility(View.VISIBLE);
            mPhotoDescription.setText(imageDescription);
        }
    }

}