package ph.newsim.mobile.newsim.fragments;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;

public class ImageDialogFragment extends DialogFragment implements DialogInterface.OnClickListener{

    private DialogInterface.OnClickListener mOnClickListener;
    private String mTitle;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        String[] branches = {"Take a photo", "Choose from gallery"};

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle(mTitle).setItems(branches, this);
        return builder.create();
    }

    @Override
    public void onClick(DialogInterface dialog, int which) {
        if (mOnClickListener != null)
            mOnClickListener.onClick(dialog, which);
    }

    public void setTitle(String title) {
        mTitle = title;
    }

    public void setOnClickListener(DialogInterface.OnClickListener onClickListener) {
        mOnClickListener = onClickListener;
    }

}
