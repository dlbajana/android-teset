package ph.newsim.mobile.newsim.ui;

import android.content.Intent;
import android.graphics.Bitmap;
import android.media.Image;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.graphics.drawable.RoundedBitmapDrawable;
import android.support.v4.graphics.drawable.RoundedBitmapDrawableFactory;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.BitmapImageViewTarget;
import com.bumptech.glide.request.target.Target;

import org.apache.commons.lang3.text.WordUtils;
import org.w3c.dom.Text;

import java.io.InputStream;

import ph.newsim.mobile.newsim.R;
import ph.newsim.mobile.newsim.database.LocalPreferences;
import ph.newsim.mobile.newsim.model.User;

public class ProfileActivity extends AppCompatActivity {

    private User mUser;
    private LocalPreferences mLocalPreferences;

    private FloatingActionButton mFABProfileImage;
    private ImageView mCoverPhoto;
    private TextView mFullName;
    private TextView mEmail;
    private TextView mGender;
    private TextView mBirthDate;
    private TextView mBirthPlace;
    private TextView mAddress;
    private TextView mRank;
    private TextView mCompanyName;
    private TextView mCompanyContact;
    private TextView mDepartment;
    private TextView mMobileNo;
    private TextView mTelephoneNo;
    private TextView mEmailAddress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        // Toolbar Initialization
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        assert toolbar != null;
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ProfileActivity.this.finish();
            }
        });
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        mLocalPreferences = new LocalPreferences(this);
        mUser = mLocalPreferences.retrieveUserDetails();

        // FAB Profile Image Initialization
        mFABProfileImage = (FloatingActionButton) findViewById(R.id.fab_profile_photo);

        // Cover Photo Initialization
        mCoverPhoto = (ImageView) findViewById(R.id.profile_cover_photo);

        // FullName Initialization
        mFullName = (TextView) findViewById(R.id.label_profile_fullname);

        // Email Initialization
        mEmail = (TextView) findViewById(R.id.label_profile_email);

        // Gender Initialization
        mGender = (TextView) findViewById(R.id.label_profile_gender);

        // BirthDate Initialization
        mBirthDate = (TextView) findViewById(R.id.label_profile_birth_date);

        // BirthPlace Initialization
        mBirthPlace = (TextView) findViewById(R.id.label_profile_birth_place);

        // Address initialization
        mAddress = (TextView) findViewById(R.id.label_profile_address);

        // Rank Initialization
        mRank = (TextView) findViewById(R.id.label_profile_rank);

        // CompanyName Initialization
        mCompanyName = (TextView) findViewById(R.id.label_profile_company);

        // CompanyContact Initialization
        mCompanyContact = (TextView) findViewById(R.id.label_profile_company_contact);

        // Department Initialization
        mDepartment = (TextView) findViewById(R.id.label_profile_department);

        // MobileNo Initialization
        mMobileNo = (TextView) findViewById(R.id.label_profile_mobile_no);

        // TelephoneNo Initialization
        mTelephoneNo = (TextView) findViewById(R.id.label_profile_telephone_no);

        // EmailAddress Initialization
        mEmailAddress = (TextView) findViewById(R.id.label_profile_email_address);

    }

    @Override
    protected void onPostResume() {
        super.onPostResume();
        bindUserData(mLocalPreferences.retrieveUserDetails());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_profile, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_update_profile){
            startProfileUpdateActivity();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void startProfileUpdateActivity(){
        Intent intent = new Intent(this, ProfileUpdateActivity.class);
        startActivity(intent);
    }

    private void bindUserData(User user){
        Glide.with(this).load(user.getProfilePhotoURI()).asBitmap().diskCacheStrategy(DiskCacheStrategy.SOURCE).centerCrop().into(new BitmapImageViewTarget(mFABProfileImage){

            @Override
            protected void setResource(Bitmap resource) {
                super.setResource(resource);
                RoundedBitmapDrawable circularBitmapDrawable = RoundedBitmapDrawableFactory.create(ProfileActivity.this.getResources(), resource);
                circularBitmapDrawable.setCircular(true);
                mFABProfileImage.setImageDrawable(circularBitmapDrawable);
                mFABProfileImage.setVisibility(View.VISIBLE);
            }
        });
        Glide.with(this).load(user.getCoverPhotoURI()).diskCacheStrategy(DiskCacheStrategy.SOURCE).centerCrop().into(mCoverPhoto);
        mFullName.setText(WordUtils.capitalizeFully(user.getFirstName() + " " + user.getLastName()));
        mEmail.setText(user.getEmail());
        mGender.setText(user.getGender());
        mBirthDate.setText(user.getBirthDate());
        mBirthPlace.setText(user.getBirthPlace());
        mAddress.setText(user.getAddress());
        mRank.setText(user.getRank());
        mCompanyName.setText(user.getCompanyName());
        mCompanyContact.setText(user.getCompanyContact());
        mDepartment.setText(user.getDepartment());
        mMobileNo.setText(user.getMobileNo());
        mTelephoneNo.setText(user.getTelephoneNo());
        mEmailAddress.setText(user.getEmail());
    }

}