package ph.newsim.mobile.newsim.viewholder;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import ph.newsim.mobile.newsim.R;
import ph.newsim.mobile.newsim.adapter.PlaceListAdapter;
import ph.newsim.mobile.newsim.model.Branch;

public class PlaceListVH extends RecyclerView.ViewHolder implements View.OnClickListener{

    private PlaceListAdapter.OnButtonClickListener mOnButtonClickListener;

    private Branch mBranch;

    private TextView mName;
    private TextView mAddress;
    private TextView mTelephone;
    private ImageView mCoverImage;
    private Button mButtonDirections;

    public PlaceListVH(View itemView) {
        super(itemView);
        mName = (TextView) itemView.findViewById(R.id.label_place_name);
        mAddress = (TextView) itemView.findViewById(R.id.label_place_address);
        mTelephone = (TextView) itemView.findViewById(R.id.label_place_telephone_no);
        mCoverImage = (ImageView) itemView.findViewById(R.id.img_place_cover);
        mButtonDirections = (Button) itemView.findViewById(R.id.button_directions);
        mButtonDirections.setOnClickListener(this);
    }

    public void bindData(Context context, Branch branch){
        mBranch= branch;
        mName.setText(branch.getName());
        mAddress.setText(branch.getAddress());
        mTelephone.setText(branch.getPhoneNumber());
        Glide.with(context).load(branch.getCoverPhoto()).into(mCoverImage);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.button_directions:
                mOnButtonClickListener.onDirectionsClick(mBranch);
                break;
        }
    }

    public void setOnButtonClickListener(PlaceListAdapter.OnButtonClickListener onButtonClickListener) {
        mOnButtonClickListener = onButtonClickListener;
    }
}
