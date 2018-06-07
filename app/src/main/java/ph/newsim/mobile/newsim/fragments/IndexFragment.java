package ph.newsim.mobile.newsim.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import ph.newsim.mobile.newsim.R;

public class IndexFragment extends Fragment implements View.OnClickListener{

    public interface OnButtonClickListener{
        void onSignInButtonClick();
        void onSignUpButtonClick();
        void onGoogleButtonClick();
        void onFacebookButtonClick();
    }

    private OnButtonClickListener mOnButtonClickListener;

    public IndexFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_index, container, false);

        ImageView companyLogo = (ImageView) rootView.findViewById(R.id.img_company_logo);
        Glide.with(getActivity()).load(R.drawable.logo_newsim).into(companyLogo);

        ImageView backgroundImage = (ImageView) rootView.findViewById(R.id.img_index_background);
        Glide.with(getActivity()).load(R.drawable.cover_nta).into(backgroundImage);

        Button facebookLoginButton = (Button) rootView.findViewById(R.id.button_facebook);
        facebookLoginButton.setOnClickListener(this);

        Button googleLoginButton = (Button) rootView.findViewById(R.id.button_google_plus);
        googleLoginButton.setOnClickListener(this);

        Button signUpButton = (Button) rootView.findViewById(R.id.button_sign_up);
        signUpButton.setOnClickListener(this);

        TextView signInLink = (TextView) rootView.findViewById(R.id.link_sign_in);
        signInLink.setOnClickListener(this);

        return rootView;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.button_facebook:
                mOnButtonClickListener.onFacebookButtonClick();
                break;
            case R.id.button_google_plus:
                mOnButtonClickListener.onGoogleButtonClick();
                break;
            case R.id.button_sign_up:
                mOnButtonClickListener.onSignUpButtonClick();
                break;
            case R.id.link_sign_in:
                mOnButtonClickListener.onSignInButtonClick();
                break;
        }
    }

    public static IndexFragment newInstance(){
        return new IndexFragment();
    }

    public void setOnButtonClickListener(OnButtonClickListener onButtonClickListener) {
        mOnButtonClickListener = onButtonClickListener;
    }
}
