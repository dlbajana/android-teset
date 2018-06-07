package ph.newsim.mobile.newsim.ui;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.facebook.AccessToken;
import com.facebook.AccessTokenTracker;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.Profile;
import com.facebook.ProfileTracker;
import com.facebook.appevents.AppEventsLogger;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;

import org.json.JSONException;
import org.json.JSONObject;

import java.net.URI;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import ph.newsim.mobile.newsim.R;
import ph.newsim.mobile.newsim.api.Newsim;
import ph.newsim.mobile.newsim.database.LocalDataSource;
import ph.newsim.mobile.newsim.database.LocalPreferences;
import ph.newsim.mobile.newsim.fragments.IndexFragment;
import ph.newsim.mobile.newsim.fragments.SplashFragment;
import ph.newsim.mobile.newsim.model.Course;
import ph.newsim.mobile.newsim.model.Post;
import ph.newsim.mobile.newsim.model.Schedule;
import ph.newsim.mobile.newsim.model.User;
import ph.newsim.mobile.newsim.util.AppController;
import ph.newsim.mobile.newsim.util.JSONDataHandler;
import ph.newsim.mobile.newsim.util.SAuthenticate;
import ph.newsim.mobile.newsim.util.SRegister;

public class LoginIndexActivity extends AppCompatActivity
        implements IndexFragment.OnButtonClickListener{

    public static final String TAG_INDEX_FRAGMENT = "index_fragment";
    public static final String TAG_SPLASH_FRAGMENT = "splash_fragment";

    private FragmentManager mFragmentManager;
    private IndexFragment mIndexFragment;
    private SplashFragment mSplashFragment;

    private LocalPreferences mLocalPreferences;

    private CallbackManager mCallbackManager;
    private AccessTokenTracker mAccessTokenTracker;
    private ProfileTracker mProfileTracker;
    private LoginManager mLoginManager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_index);

        FacebookSdk.sdkInitialize(getApplicationContext());
        AppEventsLogger.activateApp(this);

        // Fragment Manager Initialization
        mFragmentManager = getSupportFragmentManager();

        // Fragments Initialization
        mIndexFragment = IndexFragment.newInstance();
        mIndexFragment.setOnButtonClickListener(this);
        mSplashFragment = SplashFragment.newInstance();

        // LocalPreferences Initialization
        mLocalPreferences = new LocalPreferences(this);

        // CallbackManager Initialization
        mCallbackManager = CallbackManager.Factory.create();

        // LoginManager Initialization
        mLoginManager = LoginManager.getInstance();

        // AccessTokenTracker Initialization
        mAccessTokenTracker = new AccessTokenTracker() {

            @Override
            protected void onCurrentAccessTokenChanged(AccessToken oldAccessToken, AccessToken currentAccessToken) {
            }
        };
        mAccessTokenTracker.startTracking();

        // ProfileTracker Initialization
        mProfileTracker = new ProfileTracker() {

            @Override
            protected void onCurrentProfileChanged(Profile oldProfile, Profile currentProfile) {
            }
        };
        mProfileTracker.startTracking();

        // Login the user automatically if user exist in the LocalPreferences
        if (mLocalPreferences.isUserExist()){
            displaySplash();
            startServerLogin(mLocalPreferences.retrieveUserDetails());
        }else{
            displayIndex();
        }
    }

    @Override
    public void onSignInButtonClick() {
        Intent intent = new Intent(LoginIndexActivity.this, SignInActivity.class);
        startActivity(intent);
    }

    @Override
    public void onSignUpButtonClick() {
        Intent intent = new Intent(LoginIndexActivity.this, SignUpActivity.class);
        startActivity(intent);
    }

    @Override
    public void onGoogleButtonClick() {
        // Todo: Add the code for button click
    }

    @Override
    public void onFacebookButtonClick() {
        mLoginManager.logInWithReadPermissions(this, Arrays.asList("public_profile", "email", "user_birthday", "user_friends"));
        mLoginManager.registerCallback(mCallbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                Log.i("LoginIndexActivity", "Facebook Login Successful");
                displaySplash();

                GraphRequest graphRequest = GraphRequest.newMeRequest(loginResult.getAccessToken(), new GraphRequest.GraphJSONObjectCallback() {

                    @Override
                    public void onCompleted(JSONObject object, GraphResponse response) {
                        Uri profilePhotoURL = Profile.getCurrentProfile().getProfilePictureUri(200, 200);
                        String photoURL = profilePhotoURL.toString();
                        try{
                            JSONObject objectCover = object.optJSONObject("cover");
                            JSONObject objectPicture = object.optJSONObject("picture");
                            JSONObject objectData = objectPicture.optJSONObject("data");
                            String coverPhotoURI = objectCover.getString("source");

                            User user = new User(
                                    User.ACCOUNT_TYPE_FACEBOOK,
                                    object.optString("email", ""),
                                    object.optString("first_name", ""),
                                    object.optString("middle_name", ""),
                                    object.optString("last_name", ""),
                                    object.optString("gender", ""),
                                    object.optString("birthday", ""),
                                    photoURL,
                                    coverPhotoURI);
                            startServerLogin(user);
                        }catch (JSONException e){
                            Log.i("LoginIndexActivity", "GraphRequestFailed");
                        }
                    }
                });
                Bundle parameters = new Bundle();
                parameters.putString("fields", "id, name, first_name, middle_name, last_name, gender, birthday, email, picture, cover");
                graphRequest.setParameters(parameters);
                graphRequest.executeAsync();
            }

            @Override
            public void onCancel() {
                Log.i("LoginIndexActivity", "Facebook Login Cancelled");
            }

            @Override
            public void onError(FacebookException error) {
                Log.i("LoginIndexActivity", "Facebook Login Error");
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        mCallbackManager.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    protected void onStop() {
        super.onStop();
        mAccessTokenTracker.stopTracking();
        mProfileTracker.stopTracking();
    }

    private void startServerLogin(User user){

        new Newsim().auth(user, new Newsim.RequestCallback() {
            @Override
            public void onSuccessful(JSONObject response) {
                JSONObject jsonUser = response.optJSONObject("user");
                JSONObject jsonData = response.optJSONObject("data");
                LocalDataSource localDataSource = new LocalDataSource(LoginIndexActivity.this);
                localDataSource.clear();
                localDataSource.storeJSONData(LoginIndexActivity.this, jsonData);
                startMainActivity(jsonUser);
            }

            @Override
            public void onFailed(String message) {
                displayIndex();
                Toast.makeText(LoginIndexActivity.this, "Unable to connect to the server.", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onError(Exception e) {
                displayIndex();
                Toast.makeText(LoginIndexActivity.this, "Unable to connect to the server.", Toast.LENGTH_SHORT).show();
            }
        });

    }

    private void startMainActivity(JSONObject jsonUser){
        Intent intent = new Intent(LoginIndexActivity.this, MainActivity.class);
        intent.putExtra(User.KEY, JSONDataHandler.toUser(jsonUser));
        startActivity(intent);
    }

    private void displayIndex(){
        mFragmentManager.beginTransaction().replace(R.id.login_index_place_holder, mIndexFragment, TAG_INDEX_FRAGMENT).commit();
    }

    private void displaySplash(){
        mFragmentManager.beginTransaction().replace(R.id.login_index_place_holder, mSplashFragment, TAG_SPLASH_FRAGMENT).commit();
    }

}
