package ph.newsim.mobile.newsim.fragments;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

import ph.newsim.mobile.newsim.R;

public class InputDialogFragment extends DialogFragment implements DialogInterface.OnClickListener{

    public interface OnPositiveButtonClickListener {
        void onPositiveButtonClick(String inputValue);
    }

    private OnPositiveButtonClickListener mOnPositiveButtonClickListener;

    private String mTitle;
    private String mHint;

    private EditText mInputField;

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View dialog = inflater.inflate(R.layout.layout_input_dialog, null);
        mInputField = (EditText) dialog.findViewById(R.id.dialog_input);
        mInputField.setHint(mHint);

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle(mTitle == null ? "" : mTitle);
        builder.setView(dialog);
        builder.setPositiveButton("Ok", this);
        builder.setNegativeButton("Cancel", null);
        return builder.create();
    }

    @Override
    public void onClick(DialogInterface dialog, int which) {
        if (mOnPositiveButtonClickListener != null)
            mOnPositiveButtonClickListener.onPositiveButtonClick(mInputField.getText().toString());

    }

    public void setOnPositiveButtonClickListener(OnPositiveButtonClickListener onPositiveButtonClickListener) {
        mOnPositiveButtonClickListener = onPositiveButtonClickListener;
    }

    public void setTitle(String title) {
        mTitle = title;
    }

    public void setHint(String hint) {
        mHint = hint;
    }
}
