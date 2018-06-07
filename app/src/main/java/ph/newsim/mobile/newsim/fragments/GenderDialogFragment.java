package ph.newsim.mobile.newsim.fragments;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;

public class GenderDialogFragment extends DialogFragment implements DialogInterface.OnClickListener{

    private DialogInterface.OnClickListener mOnClickListener;
    private int mCheckedItem;
    private int mCurrentSelectedItem;

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        String[] gender = {"Male", "Female"};

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("Select a Gender");
        builder.setSingleChoiceItems(gender, mCheckedItem, new DialogInterface.OnClickListener() {
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

    public void setCheckedItem(int checkedItem) {
        mCheckedItem = checkedItem;
        mCurrentSelectedItem = checkedItem;
    }
}
