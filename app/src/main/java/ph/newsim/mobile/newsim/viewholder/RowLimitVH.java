package ph.newsim.mobile.newsim.viewholder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import ph.newsim.mobile.newsim.R;

public class RowLimitVH extends RecyclerView.ViewHolder{

    private TextView mMessage;

    public RowLimitVH(View itemView) {
        super(itemView);
        mMessage = (TextView) itemView.findViewById(R.id.label_message);
    }

    public void setMessage(String message){
        mMessage.setText(message);
    }

}
