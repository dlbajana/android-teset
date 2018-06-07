package ph.newsim.mobile.newsim.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

import ph.newsim.mobile.newsim.R;

public class SplashFragment extends Fragment {


    public SplashFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_splash, container, false);

        // CompanyLogo Initialization
        ImageView companyLogo = (ImageView) rootView.findViewById(R.id.img_company_logo);
        Glide.with(getActivity()).load(R.drawable.logo_newsim).into(companyLogo);

        // BackgroundImage Initialization
        ImageView splashBackground = (ImageView) rootView.findViewById(R.id.splash_background);
        Glide.with(getActivity()).load(R.drawable.bg_splash).centerCrop().into(splashBackground);

        return rootView;
    }

    public static SplashFragment newInstance() {
        return new SplashFragment();
    }
}
