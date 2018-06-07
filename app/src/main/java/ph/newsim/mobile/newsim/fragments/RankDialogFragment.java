package ph.newsim.mobile.newsim.fragments;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;

import java.util.List;

import ph.newsim.mobile.newsim.database.RankDataSource;
import ph.newsim.mobile.newsim.model.Rank;

public class RankDialogFragment extends android.support.v4.app.DialogFragment implements DialogInterface.OnClickListener{

    private DialogInterface.OnClickListener mOnClickListener;
    private int mCurrentSelectedItem;

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        RankDataSource rankDataSource = new RankDataSource();

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("Select a Rank");
        builder.setSingleChoiceItems(rankDataSource.getRankDescription(), mCurrentSelectedItem, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                mCurrentSelectedItem = which;
            }
        });
        builder.setPositiveButton("Ok", this);
        builder.setNegativeButton("Cancel", null);
        return builder.create();
    }

    @Override
    public void onClick(DialogInterface dialog, int which) {
        if (mOnClickListener != null){
            mOnClickListener.onClick(dialog, mCurrentSelectedItem);
        }
    }

    public void setOnClickListener(DialogInterface.OnClickListener onClickListener) {
        mOnClickListener = onClickListener;
    }

    public void setCurrentSelectedItem(int currentSelectedItem) {
        mCurrentSelectedItem = currentSelectedItem;
    }
}
