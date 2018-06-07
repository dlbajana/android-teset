package ph.newsim.mobile.newsim.ui;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;

import org.apache.commons.lang3.text.WordUtils;
import org.json.JSONObject;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import de.hdodenhof.circleimageview.CircleImageView;
import ph.newsim.mobile.newsim.R;
import ph.newsim.mobile.newsim.database.LocalPreferences;
import ph.newsim.mobile.newsim.database.RankDataSource;
import ph.newsim.mobile.newsim.fragments.DateDialogFragment;
import ph.newsim.mobile.newsim.fragments.GenderDialogFragment;
import ph.newsim.mobile.newsim.fragments.ImageDialogFragment;
import ph.newsim.mobile.newsim.fragments.InputDialogFragment;
import ph.newsim.mobile.newsim.fragments.RankDialogFragment;
import ph.newsim.mobile.newsim.model.User;
import ph.newsim.mobile.newsim.util.FilePath;
import ph.newsim.mobile.newsim.util.JSONDataHandler;
import ph.newsim.mobile.newsim.util.SRequest;

public class ProfileUpdateActivity extends AppCompatActivity implements View.OnClickListener{

    public static final int PROFILE_PHOTO_FILE_REQUEST = 1;
    public static final int COVER_PHOTO_FILE_REQUEST = 2;

    public static final int PROFILE_PHOTO_CAMERA_REQUEST = 3;
    public static final int COVER_PHOTO_CAMERA_REQUEST = 4;

    private Uri fileUri;

    public String mSelectedProfilePhotoPath;
    public String mSelectedCoverPhotoPath;

    private DateDialogFragment mDateDialogFragment;
    private GenderDialogFragment mGenderDialogFragment;
    private InputDialogFragment mInputDialogFragment;
    private RankDialogFragment mRankDialogFragment;
    private ImageDialogFragment mImageDialogFragment;
    private User mUser;

    private ProgressDialog mProgressDialog;
    private LocalPreferences mLocalPreferences;
    private int mCurrentSelectedRankIndex;

    private CircleImageView mProfileImage;
    private ImageView mCoverPhoto;
    private EditText mFirstName;
    private EditText mMiddleName;
    private EditText mLastName;
    private EditText mExtnName;
    private TextView mRank;
    private TextView mGender;
    private TextView mBirthDate;
    private TextView mBirthPlace;
    private TextView mAddress;
    private TextView mCompanyName;
    private TextView mCompanyContact;
    private TextView mDepartment;
    private TextView mMobileNo;
    private TextView mTelephoneNo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_update);

        // LocalPreferences Initialization
        mLocalPreferences = new LocalPreferences(this);
        mUser = mLocalPreferences.retrieveUserDetails();

        // Toolbar Initialization
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        assert toolbar != null;
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ProfileUpdateActivity.this.finish();
            }
        });
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_action_close);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        // DatePicker Initialization
        mDateDialogFragment = new DateDialogFragment();

        // GenderPicker Initialization
        mGenderDialogFragment = new GenderDialogFragment();

        // RankPicker Initialization
        mRankDialogFragment = new RankDialogFragment();

        // InputDialog Initialization
        mInputDialogFragment = new InputDialogFragment();

        // ImageDialog Initialization
        mImageDialogFragment = new ImageDialogFragment();

        // Profile Image Initialization
        mProfileImage = (CircleImageView) findViewById(R.id.profile_image);
        mProfileImage.setColorFilter(Color.argb(100, 0, 0, 0));
        Glide.with(this).load(mUser.getProfilePhotoURI()).diskCacheStrategy(DiskCacheStrategy.SOURCE).centerCrop().listener(new RequestListener<String, GlideDrawable>() {
            @Override
            public boolean onException(Exception e, String model, Target<GlideDrawable> target, boolean isFirstResource) {
                mProfileImage.setColorFilter(Color.argb(0, 0, 0, 0));
                mProfileImage.setImageResource(R.drawable.default_profile_empty);
                return true;
            }

            @Override
            public boolean onResourceReady(GlideDrawable resource, String model, Target<GlideDrawable> target, boolean isFromMemoryCache, boolean isFirstResource) {
                return false;
            }
        }).into(mProfileImage);

        // Cover Photo Initialization
        mCoverPhoto = (ImageView) findViewById(R.id.img_profile_cover_photo);
        mCoverPhoto.setColorFilter(Color.argb(100, 0, 0, 0));
        Glide.with(this).load(mUser.getCoverPhotoURI()).diskCacheStrategy(DiskCacheStrategy.SOURCE).centerCrop().listener(new RequestListener<String, GlideDrawable>() {
            @Override
            public boolean onException(Exception e, String model, Target<GlideDrawable> target, boolean isFirstResource) {
                mCoverPhoto.setImageResource(R.drawable.default_cover_photo);
                mCoverPhoto.setColorFilter(Color.argb(0, 0, 0, 0));
                return true;
            }

            @Override
            public boolean onResourceReady(GlideDrawable resource, String model, Target<GlideDrawable> target, boolean isFromMemoryCache, boolean isFirstResource) {
                return false;
            }
        }).into(mCoverPhoto);

        // FirstName Initialization
        mFirstName = (EditText) findViewById(R.id.field_first_name);

        // MiddleName Initialization
        mMiddleName = (EditText) findViewById(R.id.field_middle_name);

        // LastName Initialization
        mLastName = (EditText) findViewById(R.id.field_last_name);

        // ExtnName Initialization
        mExtnName = (EditText) findViewById(R.id.field_extn_name);

        // Rank Initialization
        LinearLayout layoutRank = (LinearLayout) findViewById(R.id.layout_rank);
        assert layoutRank != null;
        layoutRank.setOnClickListener(this);
        mRank = (TextView) findViewById(R.id.label_rank);

        // Gender Initialization
        LinearLayout layoutGender = (LinearLayout) findViewById(R.id.layout_gender);
        assert layoutGender != null;
        layoutGender.setOnClickListener(this);
        mGender = (TextView) findViewById(R.id.label_gender);

        // BirthDate Initialization
        LinearLayout layoutBirthDate = (LinearLayout) findViewById(R.id.layout_birth_date);
        assert layoutBirthDate != null;
        layoutBirthDate.setOnClickListener(this);
        mBirthDate = (TextView) findViewById(R.id.label_birth_date);

        // BirthPlace Initialization
        LinearLayout layoutBirthPlace = (LinearLayout) findViewById(R.id.layout_birth_place);
        assert layoutBirthPlace != null;
        layoutBirthPlace.setOnClickListener(this);
        mBirthPlace = (TextView) findViewById(R.id.label_birth_place);

        // Address initialization
        LinearLayout layoutAddress = (LinearLayout) findViewById(R.id.layout_address);
        assert layoutAddress != null;
        layoutAddress.setOnClickListener(this);
        mAddress = (TextView) findViewById(R.id.label_address);

        // Company Initialization
        LinearLayout layoutCompany = (LinearLayout) findViewById(R.id.layout_company);
        assert layoutCompany != null;
        layoutCompany.setOnClickListener(this);
        mCompanyName = (TextView) findViewById(R.id.label_company);

        // CompanyContact Initialization
        LinearLayout layoutCompanyContact = (LinearLayout) findViewById(R.id.layout_company_contact);
        assert layoutCompanyContact != null;
        layoutCompanyContact.setOnClickListener(this);
        mCompanyContact = (TextView) findViewById(R.id.label_company_contact);

        // Department Initialization
        LinearLayout layoutDepartment = (LinearLayout) findViewById(R.id.layout_department);
        assert layoutDepartment != null;
        layoutDepartment.setOnClickListener(this);
        mDepartment = (TextView) findViewById(R.id.label_department);

        // MobileNo Initialization
        LinearLayout layoutMobileNo = (LinearLayout) findViewById(R.id.layout_mobile_no);
        assert layoutMobileNo != null;
        layoutMobileNo.setOnClickListener(this);
        mMobileNo = (TextView) findViewById(R.id.label_mobile_no);

        // TelephoneNo Initialization
        LinearLayout layoutTelephoneNo = (LinearLayout) findViewById(R.id.layout_telephone_no);
        assert layoutTelephoneNo != null;
        layoutTelephoneNo.setOnClickListener(this);
        mTelephoneNo = (TextView) findViewById(R.id.label_telephone_no);

        // Image Overlay Initialization
        ImageView profilePhotoOverlay = (ImageView) findViewById(R.id.img_overlay_profile);
        assert profilePhotoOverlay != null;
        profilePhotoOverlay.setOnClickListener(this);

        ImageView coverPhotoOverlay = (ImageView) findViewById(R.id.img_overlay_cover_photo);
        assert coverPhotoOverlay != null;
        coverPhotoOverlay.setOnClickListener(this);

        mProgressDialog = new ProgressDialog(this);
        mProgressDialog.setIndeterminate(true);
        mProgressDialog.setTitle("Saving profile");
        mProgressDialog.setMessage("Connecting to server. Please wait.");

        bindUserData(mUser);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.layout_rank:
                showRankPickerDialog();
                break;
            case R.id.layout_gender:
                showGenderPickerDialog();
                break;
            case R.id.layout_birth_date:
                showDatePickerDialog(v);
                break;
            case R.id.layout_birth_place:
                showInputDialog(v);
                break;
            case R.id.layout_address:
                showInputDialog(v);
                break;
            case R.id.layout_company:
                showInputDialog(v);
                break;
            case R.id.layout_company_contact:
                showInputDialog(v);
                break;
            case R.id.layout_department:
                showInputDialog(v);
                break;
            case R.id.layout_mobile_no:
                showInputDialog(v);
                break;
            case R.id.layout_telephone_no:
                showInputDialog(v);
                break;
            case R.id.img_overlay_profile:
                showImageDialog(v);
                break;
            case R.id.img_overlay_cover_photo:
                showImageDialog(v);
                break;
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_profile_update, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.action_update_profile:
                mUser.setFirstName(mFirstName.getText().toString());
                mUser.setMiddleName(mMiddleName.getText().toString());
                mUser.setLastName(mLastName.getText().toString());
                mUser.setExtnName(mExtnName.getText().toString());
                startProfileUpdate(mUser);
                break;
        }
        return true;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode == Activity.RESULT_OK){
            switch (requestCode){
                case PROFILE_PHOTO_FILE_REQUEST:
                    if (data != null){
                        Uri selectedFileUri = data.getData();
                        Glide.with(this).load(selectedFileUri).centerCrop().into(mProfileImage);
                        mSelectedProfilePhotoPath = FilePath.getPath(this, selectedFileUri);
                    }
                    break;
                case COVER_PHOTO_FILE_REQUEST:
                    if (data != null){
                        Uri selectedFileUri = data.getData();
                        Glide.with(this).load(selectedFileUri).centerCrop().into(mCoverPhoto);
                        mSelectedCoverPhotoPath = FilePath.getPath(this, selectedFileUri);
                    }
                    break;
                case PROFILE_PHOTO_CAMERA_REQUEST:
                    Glide.with(this).load(fileUri).centerCrop().into(mProfileImage);
                    mSelectedProfilePhotoPath = FilePath.getPath(this, fileUri);
                    break;
                case COVER_PHOTO_CAMERA_REQUEST:
                    Glide.with(this).load(fileUri).centerCrop().into(mCoverPhoto);
                    mSelectedCoverPhotoPath = FilePath.getPath(this, fileUri);
                    break;
            }
        }
    }

    private void showDatePickerDialog(View v) {
        switch (v.getId()){
            case R.id.layout_birth_date:
                mDateDialogFragment.setOnDateSetListener(new DatePickerDialog.OnDateSetListener() {

                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                        mBirthDate.setText(monthOfYear + 1 + "/" + dayOfMonth + "/" + year);
                        mUser.setBirthDate(monthOfYear + 1 + "/" + dayOfMonth + "/" + year);
                    }
                });
                mDateDialogFragment.show(getSupportFragmentManager(), "datePicker");
                break;
        }
    }

    private void showGenderPickerDialog(){
        mGenderDialogFragment.setCheckedItem(mGender.getText().toString().toUpperCase().equals("FEMALE") ? 1 : 0);
        mGenderDialogFragment.setOnClickListener(new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                mGender.setText(which == 0 ? "Male" : "Female");
                mUser.setGender(which == 0 ? "MALE" : "FEMALE");
            }
        });
        mGenderDialogFragment.show(getSupportFragmentManager(), "genderPicker");
    }

    private void showRankPickerDialog(){
        mRankDialogFragment.setCurrentSelectedItem(mCurrentSelectedRankIndex);
        mRankDialogFragment.setOnClickListener(new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                RankDataSource rankDataSource = new RankDataSource();
                mCurrentSelectedRankIndex = which;
                mRank.setText(rankDataSource.getRankCode()[which]);
                mUser.setRank(rankDataSource.getRankCode()[which]);
            }
        });
        mRankDialogFragment.show(getSupportFragmentManager(), "rankPicker");
    }

    private void showInputDialog(View v){
        switch (v.getId()){
            case R.id.layout_birth_place:
                mInputDialogFragment.setTitle("Birthplace");
                mInputDialogFragment.setHint("City of birth");
                mInputDialogFragment.setOnPositiveButtonClickListener(new InputDialogFragment.OnPositiveButtonClickListener() {
                    @Override
                    public void onPositiveButtonClick(String inputValue) {
                        mBirthPlace.setText(inputValue);
                        mUser.setBirthPlace(inputValue);
                    }
                });
                mInputDialogFragment.show(getSupportFragmentManager(), "inputDialog");
                break;
            case R.id.layout_address:
                mInputDialogFragment.setTitle("Address");
                mInputDialogFragment.setHint("Permanent Address");
                mInputDialogFragment.setOnPositiveButtonClickListener(new InputDialogFragment.OnPositiveButtonClickListener() {
                    @Override
                    public void onPositiveButtonClick(String inputValue) {
                        mAddress.setText(inputValue);
                        mUser.setAddress(inputValue);
                    }
                });
                mInputDialogFragment.show(getSupportFragmentManager(), "inputDialog");
                break;
            case R.id.layout_company:
                mInputDialogFragment.setTitle("Company Name");
                mInputDialogFragment.setHint("Company Name");
                mInputDialogFragment.setOnPositiveButtonClickListener(new InputDialogFragment.OnPositiveButtonClickListener() {
                    @Override
                    public void onPositiveButtonClick(String inputValue) {
                        mCompanyName.setText(inputValue);
                        mUser.setCompanyName(inputValue);
                    }
                });
                mInputDialogFragment.show(getSupportFragmentManager(), "inputDialog");
                break;
            case R.id.layout_company_contact:
                mInputDialogFragment.setTitle("Company Contact No.");
                mInputDialogFragment.setHint("Telephone No. or Mobile No.");
                mInputDialogFragment.setOnPositiveButtonClickListener(new InputDialogFragment.OnPositiveButtonClickListener() {
                    @Override
                    public void onPositiveButtonClick(String inputValue) {
                        mCompanyContact.setText(inputValue);
                        mUser.setCompanyContact(inputValue);
                    }
                });
                mInputDialogFragment.show(getSupportFragmentManager(), "inputDialog");
                break;
            case R.id.layout_department:
                mInputDialogFragment.setTitle("Department");
                mInputDialogFragment.setHint("Enter Department");
                mInputDialogFragment.setOnPositiveButtonClickListener(new InputDialogFragment.OnPositiveButtonClickListener() {
                    @Override
                    public void onPositiveButtonClick(String inputValue) {
                        mDepartment.setText(inputValue);
                        mUser.setDepartment(inputValue);
                    }
                });
                mInputDialogFragment.show(getSupportFragmentManager(), "inputDialog");
                break;
            case R.id.layout_mobile_no:
                mInputDialogFragment.setTitle("Mobile No");
                mInputDialogFragment.setHint("11-digit mobile no.");
                mInputDialogFragment.setOnPositiveButtonClickListener(new InputDialogFragment.OnPositiveButtonClickListener() {
                    @Override
                    public void onPositiveButtonClick(String inputValue) {
                        mMobileNo.setText(inputValue);
                        mUser.setMobileNo(inputValue);
                    }
                });
                mInputDialogFragment.show(getSupportFragmentManager(), "inputDialog");
                break;
            case R.id.layout_telephone_no:
                mInputDialogFragment.setTitle("Telephone No.");
                mInputDialogFragment.setHint("Telephone No.");
                mInputDialogFragment.setOnPositiveButtonClickListener(new InputDialogFragment.OnPositiveButtonClickListener() {
                    @Override
                    public void onPositiveButtonClick(String inputValue) {
                        mTelephoneNo.setText(inputValue);
                        mUser.setTelephoneNo(inputValue);
                    }
                });
                mInputDialogFragment.show(getSupportFragmentManager(), "inputDialog");
        }
    }

    private void showImageDialog(View v){
        switch (v.getId()){
            case R.id.img_overlay_profile:
                mImageDialogFragment.setTitle("Select profile photo");
                mImageDialogFragment.show(getSupportFragmentManager(), "imageDialog");
                mImageDialogFragment.setOnClickListener(new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        switch (which){
                            case 0:
                                captureImage(PROFILE_PHOTO_CAMERA_REQUEST);
                                break;
                            case 1:
                                selectImage(PROFILE_PHOTO_FILE_REQUEST);
                                break;
                        }
                    }
                });
                break;
            case R.id.img_overlay_cover_photo:
                mImageDialogFragment.setTitle("Select cover photo");
                mImageDialogFragment.show(getSupportFragmentManager(), "imageDialog");
                mImageDialogFragment.setOnClickListener(new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        switch (which){
                            case 0:
                                captureImage(COVER_PHOTO_CAMERA_REQUEST);
                                break;
                            case 1:
                                selectImage(COVER_PHOTO_FILE_REQUEST);
                                break;
                        }
                    }
                });
                break;
        }
    }

    private void bindUserData(User user) {
        mFirstName.setText(WordUtils.capitalizeFully(user.getFirstName()));
        mMiddleName.setText(WordUtils.capitalizeFully(user.getMiddleName()));
        mLastName.setText(WordUtils.capitalizeFully(user.getLastName()));
        mExtnName.setText(user.getExtnName());
        mRank.setText(user.getRank());
        mGender.setText(WordUtils.capitalizeFully(user.getGender()));
        mBirthDate.setText(user.getBirthDate());
        mBirthPlace.setText(user.getBirthPlace());
        mAddress.setText(user.getAddress());
        mCompanyName.setText(user.getCompanyName());
        mCompanyContact.setText(user.getCompanyContact());
        mDepartment.setText(user.getDepartment());
        mMobileNo.setText(user.getMobileNo());
        mTelephoneNo.setText(user.getTelephoneNo());
    }

    private void selectImage(int requestCode){
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("image/jpeg");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Choose Image to Upload"), requestCode);
    }

    private void captureImage(int requestCode){
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        fileUri = getOutputMediaFileUri();
        intent.putExtra(MediaStore.EXTRA_OUTPUT, fileUri);
        startActivityForResult(intent, requestCode);
    }

    private void startProfileUpdate(User user){
        mProgressDialog.show();
        SRequest request = new SRequest();
        request.updateProfileInfo(user, mSelectedProfilePhotoPath, mSelectedCoverPhotoPath, new SRequest.RequestCallback() {
            @Override
            public void onSuccessful(JSONObject jsonData) {
                mProgressDialog.hide();
                User returnedUser = JSONDataHandler.toUser(jsonData.optJSONObject("user"));
                LocalPreferences localPreferences = new LocalPreferences(ProfileUpdateActivity.this);
                localPreferences.storeUserDetails(returnedUser);
                Toast.makeText(ProfileUpdateActivity.this, "Profile Update Successful", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailed() {
                mProgressDialog.hide();
                Toast.makeText(ProfileUpdateActivity.this, "Profile Update Failed", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onError(Exception e) {
                mProgressDialog.hide();
                Toast.makeText(ProfileUpdateActivity.this, "Error occured while connecting to the server.", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public Uri getOutputMediaFileUri() {
        // External sdcard location
        File mediaStorageDir = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES), "");

        // Create the storage directory if it does not exist
        if (!mediaStorageDir.exists()) {
            if (!mediaStorageDir.mkdirs()) {
                Log.d("ProfileUpdateActivity", "Oops! Failed create " + "Android File Upload" + " directory");
                return null;
            }
        }
        // Create a media file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault()).format(new Date());

        return Uri.fromFile(new File(mediaStorageDir.getPath() + File.separator + "IMG_" + timeStamp + ".jpg"));
    }

}
