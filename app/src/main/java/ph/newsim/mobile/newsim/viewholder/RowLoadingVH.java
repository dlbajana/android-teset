package ph.newsim.mobile.newsim.viewholder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ProgressBar;

import ph.newsim.mobile.newsim.R;

public class RowLoadingVH extends RecyclerView.ViewHolder{

    private ProgressBar mProgressBar;

    public RowLoadingVH(View itemView) {
        super(itemView);
        mProgressBar = (ProgressBar) itemView.findViewById(R.id.row_progress_bar);
    }

}
