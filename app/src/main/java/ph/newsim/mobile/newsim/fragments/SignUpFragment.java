package ph.newsim.mobile.newsim.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.dd.CircularProgressButton;

import org.json.JSONObject;

import ph.newsim.mobile.newsim.R;
import ph.newsim.mobile.newsim.api.Newsim;
import ph.newsim.mobile.newsim.database.LocalDataSource;
import ph.newsim.mobile.newsim.model.User;
import ph.newsim.mobile.newsim.ui.SignInActivity;
import ph.newsim.mobile.newsim.util.SRegister;

public class SignUpFragment extends Fragment implements View.OnClickListener{

    private interface OnButtonClickListener{
        void onSignUpButtonClick();
        void onSignInLinkClick();
    }

    private SRegister.RegisterCallback mRegisterCallback = new SRegister.RegisterCallback() {
        @Override
        public void onSuccessful(JSONObject jsonError, JSONObject jsonUser) {

        }

        @Override
        public void onFailed() {

        }

        @Override
        public void onError(Exception e) {

        }
    };

    private TextView mEmailField;
    private TextView mFirstNameField;
    private TextView mMiddleNameField;
    private TextView mLastNameField;
    private TextView mPasswordField;
    private TextView mRePasswordField;
    private CircularProgressButton mCircularProgressSignUpButton;

    public SignUpFragment(){

    }

    public static SignUpFragment newInstance(){
        return new SignUpFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_sign_up, container, false);

        // Email Field Initialization
        mEmailField = (TextView) rootView.findViewById(R.id.field_email);

        // First Name Field Initialization
        mFirstNameField = (TextView) rootView.findViewById(R.id.field_first_name);

        // Middle Name Field Initialization
        mMiddleNameField = (TextView) rootView.findViewById(R.id.field_middle_name);

        // Last Name Field Initialization
        mLastNameField = (TextView) rootView.findViewById(R.id.field_last_name);

        // Password Field Initialization
        mPasswordField = (TextView) rootView.findViewById(R.id.field_password);

        // RePassword Field Initialization
        mRePasswordField = (TextView) rootView.findViewById(R.id.field_confirm_password);

        // SignInLink Initialization
        TextView linkSignIn = (TextView) rootView.findViewById(R.id.link_sign_in);
        linkSignIn.setOnClickListener(this);

        // SignUpButton Initialization
        mCircularProgressSignUpButton = (CircularProgressButton) rootView.findViewById(R.id.button_sign_up);
        mCircularProgressSignUpButton.setIndeterminateProgressMode(true);
        mCircularProgressSignUpButton.setOnClickListener(this);
        return rootView;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.link_sign_in:
                startSignInActivity();
                break;
            case R.id.button_sign_up:
                if (isInputValid()){
                    User user = new User(mEmailField.getText().toString(), mPasswordField.getText().toString());
                    user.setFirstName(mFirstNameField.getText().toString());
                    user.setMiddleName(mMiddleNameField.getText().toString());
                    user.setLastName(mLastNameField.getText().toString());
                    startServerRegistration(user);
                }else{
                    // Todo: Toast a message for invalid inputs
                }
                break;
        }
    }

    private void startServerRegistration(User user){
        setEnableFields(false);
        mCircularProgressSignUpButton.setProgress(0);
        mCircularProgressSignUpButton.setProgress(50);

//        new SRegister().attemptRegisterUserInBackground(user, new SRegister.RegisterCallback() {
//
//            @Override
//            public void onSuccessful(JSONObject jsonError, JSONObject jsonUser) {
//                setEnableFields(true);
//                clearFieldData();
//                mCircularProgressSignUpButton.setProgress(0);
//                mRegisterCallback.onSuccessful(jsonError, jsonUser);
//            }
//
//            @Override
//            public void onFailed() {
//                // Todo: Notify the user that the attempt to register the user has failed.
//                setEnableFields(true);
//                mRegisterCallback.onFailed();
//            }
//
//            @Override
//            public void onError(Exception e) {
//                // Todo: Notify the user that an error occurred while trying to register the account.
//                setEnableFields(true);
//                mRegisterCallback.onError(e);
//            }
//        });

        new Newsim().register(user, new Newsim.RequestCallback() {
            @Override
            public void onSuccessful(JSONObject response) {
                setEnableFields(true);
                clearFieldData();
                mCircularProgressSignUpButton.setProgress(0);
//                JSONObject jsonUser = response.optJSONObject("user");
//                JSONObject jsonData = response.optJSONObject("data");
                LocalDataSource localDataSource = new LocalDataSource(getActivity());
                localDataSource.clear();
                JSONObject jsonUser = response.optJSONObject("user");
                JSONObject jsonData = response.optJSONObject("data");
                localDataSource.storeJSONData(getActivity(), jsonData);
                mRegisterCallback.onSuccessful(null, jsonUser);

            }

            @Override
            public void onFailed(String message) {
                // Todo: Notify the user that the attempt to register the user has failed.
                setEnableFields(true);
                mRegisterCallback.onFailed();
            }

            @Override
            public void onError(Exception e) {
                // Todo: Notify the user that an error occurred while trying to register the account.
                setEnableFields(true);
                mRegisterCallback.onError(e);
            }
        });

    }

    private void startSignInActivity(){
        Intent intent = new Intent(getActivity(), SignInActivity.class);
        startActivity(intent);
    }

    private boolean isInputValid(){
        return mPasswordField.getText().toString().equals(mRePasswordField.getText().toString());
    }

    private void setEnableFields(boolean bol){
        mEmailField.setEnabled(bol);
        mFirstNameField.setEnabled(bol);
        mMiddleNameField.setEnabled(bol);
        mLastNameField.setEnabled(bol);
        mPasswordField.setEnabled(bol);
        mRePasswordField.setEnabled(bol);
    }

    public void clearFieldData(){
        mEmailField.setText("");
        mFirstNameField.setText("");
        mMiddleNameField.setText("");
        mLastNameField.setText("");
        mPasswordField.setText("");
        mRePasswordField.setText("");
    }

    // Setters
    public void setRegisterCallback(SRegister.RegisterCallback registerCallback) {
        mRegisterCallback = registerCallback;
    }
}
