package ph.newsim.mobile.newsim.ui;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.dd.CircularProgressButton;

import org.json.JSONObject;

import ph.newsim.mobile.newsim.R;
import ph.newsim.mobile.newsim.api.Newsim;
import ph.newsim.mobile.newsim.database.LocalDataSource;
import ph.newsim.mobile.newsim.model.User;
import ph.newsim.mobile.newsim.util.JSONDataHandler;
import ph.newsim.mobile.newsim.util.SAuthenticate;

public class SignInActivity extends AppCompatActivity implements View.OnClickListener{

    private CircularProgressButton mCircularProgressSignInButton;

    private EditText mEmailField;
    private EditText mPasswordField;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);

        // Toolbar Initialization
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        assert toolbar != null;
        toolbar.setTitle("Welcome aboard!");
        setSupportActionBar(toolbar);

        // Cover Image Initialization
        ImageView coverImage = (ImageView) findViewById(R.id.img_sign_in_cover_photo);
        assert coverImage != null;
        coverImage.setColorFilter(Color.argb(180, 236, 28, 36));
        Glide.with(this).load(R.drawable.cover_nta_aerial).centerCrop().into(coverImage);

        // EmailField Initialization
        mEmailField = (EditText) findViewById(R.id.field_login_email);

        // PasswordField Initialization
        mPasswordField = (EditText) findViewById(R.id.field_login_password);

        // SignInButton Initialization
        mCircularProgressSignInButton = (CircularProgressButton) findViewById(R.id.button_sign_in);
        assert mCircularProgressSignInButton != null;
        mCircularProgressSignInButton.setIndeterminateProgressMode(true);
        mCircularProgressSignInButton.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.button_sign_in:
                User user = new User(mEmailField.getText().toString(), mPasswordField.getText().toString());
                startServerLogin(user);
                break;
        }
    }

    private void startServerLogin(User user){
        mCircularProgressSignInButton.setProgress(0);
        mCircularProgressSignInButton.setProgress(50);
//        mEmailField.setEnabled(false);
//        mPasswordField.setEnabled(false);

//        new SAuthenticate().attemptLoginInBackground(user, new SAuthenticate.LoginCallback() {
//
//            @Override
//            public void onSuccessful(JSONObject jsonUser, JSONObject jsonData) {
//                mCircularProgressSignInButton.setProgress(100);
//                LocalDataSource localDataSource = new LocalDataSource(SignInActivity.this);
//                localDataSource.clear();
//                localDataSource.storeJSONData(SignInActivity.this, jsonData);
//                startMainActivity(JSONDataHandler.toUser(jsonUser));
//            }
//
//            @Override
//            public void onFailed() {
//                mEmailField.setEnabled(true);
//                mPasswordField.setEnabled(true);
//                mCircularProgressSignInButton.setProgress(-1);
//                Toast.makeText(SignInActivity.this, "Email and password did not match!", Toast.LENGTH_LONG).show();
//            }
//
//            @Override
//            public void onError(Exception e) {
//                mEmailField.setEnabled(true);
//                mPasswordField.setEnabled(true);
//                Toast.makeText(SignInActivity.this, "Error: " + e.getMessage(), Toast.LENGTH_LONG).show();
//            }
//        });

        new Newsim().auth(user, new Newsim.RequestCallback() {
            @Override
            public void onSuccessful(JSONObject response) {
                JSONObject jsonUser = response.optJSONObject("user");
                JSONObject jsonData = response.optJSONObject("data");
                mCircularProgressSignInButton.setProgress(100);
                LocalDataSource localDataSource = new LocalDataSource(SignInActivity.this);
                localDataSource.clear();
                localDataSource.storeJSONData(SignInActivity.this, jsonData);
                startMainActivity(JSONDataHandler.toUser(jsonUser));
            }

            @Override
            public void onFailed(String message) {
                mEmailField.setEnabled(true);
                mPasswordField.setEnabled(true);
                mCircularProgressSignInButton.setProgress(-1);
                Toast.makeText(SignInActivity.this, "Email and password did not match!", Toast.LENGTH_LONG).show();
            }

            @Override
            public void onError(Exception e) {
                mEmailField.setEnabled(true);
                mPasswordField.setEnabled(true);
                Toast.makeText(SignInActivity.this, "Error: " + e.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }

    private void startMainActivity(User user){
        final Intent intent = new Intent(SignInActivity.this, MainActivity.class);
        intent.putExtra(User.KEY, user);
        new CountDownTimer(1000, 1000) {
            public void onTick(long millisUntilFinished) {

            }

            public void onFinish() {
                startActivity(intent);
            }
        }.start();
    }

}
