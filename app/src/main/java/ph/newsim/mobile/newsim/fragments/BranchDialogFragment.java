package ph.newsim.mobile.newsim.fragments;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;

public class BranchDialogFragment extends DialogFragment implements DialogInterface.OnClickListener{

    private DialogInterface.OnClickListener mOnClickListener;
    private int mCheckedItem;
    private int mCurrentSelectedItem;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        String[] gender = {"Newsim Bacolod", "Newsim Cebu", "Newsim Davao", "Newsim Ilo-ilo", "Newsim Marconi"};

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("Select a Branch");
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
