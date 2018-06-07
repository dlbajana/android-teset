package ph.newsim.mobile.newsim.fragments;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.dd.CircularProgressButton;

import org.json.JSONObject;

import ph.newsim.mobile.newsim.R;
import ph.newsim.mobile.newsim.api.Newsim;
import ph.newsim.mobile.newsim.database.LocalDataSource;
import ph.newsim.mobile.newsim.model.User;
import ph.newsim.mobile.newsim.ui.MainActivity;
import ph.newsim.mobile.newsim.ui.SignInActivity;
import ph.newsim.mobile.newsim.util.JSONDataHandler;
import ph.newsim.mobile.newsim.util.SAuthenticate;

public class SignUpResultFragment extends Fragment implements View.OnClickListener{

    private User mUser;
    private TextView mEmail;
    private TextView mFullName;
    private CircularProgressButton mCircularProgressContinueButton;

    public SignUpResultFragment() {}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_sign_up_result, container, false);

        // EmailLabel Initialization
        mEmail = (TextView) rootView.findViewById(R.id.label_user_email);

        // FullNameLabel initialization
        mFullName = (TextView) rootView.findViewById(R.id.label_user_full_name);

        // SignInLink Initialization
        TextView linkSignIn = (TextView) rootView.findViewById(R.id.link_sign_in);
        linkSignIn.setOnClickListener(this);

        // ContinueButton Initialization
        mCircularProgressContinueButton = (CircularProgressButton) rootView.findViewById(R.id.button_continue);
        mCircularProgressContinueButton.setIndeterminateProgressMode(true);
        mCircularProgressContinueButton.setOnClickListener(this);
        return rootView;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.button_continue:
                startServerLogin(mUser);
                break;
            case R.id.link_sign_in:
                startSignInActivity();
                break;
        }
    }

    public static SignUpResultFragment newInstance(){
        return new SignUpResultFragment();
    }

    private void startServerLogin(final User user){
        mCircularProgressContinueButton.setProgress(0);
        mCircularProgressContinueButton.setProgress(50);

//        new SAuthenticate().attemptLoginInBackground(user, new SAuthenticate.LoginCallback() {
//
//            @Override
//            public void onSuccessful(JSONObject jsonUser, JSONObject jsonData) {
//                mCircularProgressContinueButton.setProgress(100);
//                LocalDataSource localDataSource = new LocalDataSource(getActivity());
//                localDataSource.clear();
//                localDataSource.storeJSONData(getActivity(), jsonData);
//                startMainActivity(JSONDataHandler.toUser(jsonUser));
//            }
//
//            @Override
//            public void onFailed() {
//                mCircularProgressContinueButton.setProgress(-1);
//                Toast.makeText(getActivity(), "Email and password did not match!", Toast.LENGTH_LONG).show();
//            }
//
//            @Override
//            public void onError(Exception e) {
//                Toast.makeText(getActivity(), "Error: " + e.getMessage(), Toast.LENGTH_LONG).show();
//            }
//        });

        startMainActivity(user);

//        new Newsim().auth(user, new Newsim.RequestCallback() {
//            @Override
//            public void onSuccessful(JSONObject response) {
//                mCircularProgressContinueButton.setProgress(100);
//                LocalDataSource localDataSource = new LocalDataSource(getActivity());
//                localDataSource.clear();
//                JSONObject jsonUser = response.optJSONObject("user");
//                JSONObject jsonData = response.optJSONObject("data");
//                localDataSource.storeJSONData(getActivity(), jsonData);
//                startMainActivity(JSONDataHandler.toUser(jsonUser));
//            }
//
//            @Override
//            public void onFailed(String message) {
//                mCircularProgressContinueButton.setProgress(-1);
//                Toast.makeText(getActivity(), "Email and password did not match!", Toast.LENGTH_LONG).show();
//            }
//
//            @Override
//            public void onError(Exception e) {
//                Toast.makeText(getActivity(), "Error: " + e.getMessage(), Toast.LENGTH_LONG).show();
//            }
//        });
    }

    private void startMainActivity(User user){
        final Intent intent = new Intent(getActivity(), MainActivity.class);
        intent.putExtra(User.KEY, user);
        new CountDownTimer(1000, 1000) {
            public void onTick(long millisUntilFinished) {

            }

            public void onFinish() {
                startActivity(intent);
            }
        }.start();
    }

    private void startSignInActivity(){
        Intent intent = new Intent(getActivity(), SignInActivity.class);
        startActivity(intent);
    }

    public void setUser(User user) {
        mUser = user;
        mEmail.setText(getActivity().getString(R.string.register_email, user.getEmail()));
    }

}
