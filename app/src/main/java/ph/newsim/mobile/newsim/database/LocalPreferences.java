package ph.newsim.mobile.newsim.database;

import android.content.Context;
import android.content.SharedPreferences;

import ph.newsim.mobile.newsim.model.User;


public class LocalPreferences {

    private SharedPreferences mSharedPreferences;
    private SharedPreferences.Editor mEditor;
    public static final String SP_NAME = "user_details";

    public static final String KEY_ID = "id";
    public static final String KEY_ACCOUNT_TYPE = "account_type";
    public static final String KEY_EMAIL = "email";
    public static final String KEY_PASSWORD = "password";
    public static final String KEY_FIRST_NAME = "first_name";
    public static final String KEY_MIDDLE_NAME = "middle_name";
    public static final String KEY_LAST_NAME = "last_name";
    public static final String KEY_EXTN_NAME = "extn_name";
    public static final String KEY_GENDER = "gender";
    public static final String KEY_BIRTHDATE = "birthdate";
    public static final String KEY_BIRTHPLACE = "birthplace";
    public static final String KEY_MOBILE_NO = "mobile_no";
    public static final String KEY_TELEPHONE_NO = "telephone_no";
    public static final String KEY_ADDRESS = "address";
    public static final String KEY_RANK = "rank";
    public static final String KEY_COMPANY_NAME = "company_name";
    public static final String KEY_COMPANY_CONTACT = "company_contact";
    public static final String KEY_PROFILE_PHOTO_URI = "profile_photo_uri";
    public static final String KEY_COVER_PHOTO_URI = "cover_photo_uri";
    public static final String KEY_DEPARTMENT = "department";
    public static final String KEY_DATE_CREATED = "date_created";

    public static final String KEY_USER_EXIST = "user_exist";

    public LocalPreferences(Context context){
        mSharedPreferences = context.getSharedPreferences(SP_NAME, 0);
        mEditor = mSharedPreferences.edit();
        mEditor.apply();

    }

    public boolean isUserExist(){
        return mSharedPreferences.getBoolean(KEY_USER_EXIST, false);
    }

    public void setUserExist(boolean bol){
        mEditor.putBoolean(KEY_USER_EXIST, bol).apply();
    }

    public void storeUserDetails(User user){
        mEditor.putInt(KEY_ID, user.getId());
        mEditor.putString(KEY_ACCOUNT_TYPE, user.getAccountType());
        mEditor.putString(KEY_EMAIL, user.getEmail());
        mEditor.putString(KEY_PASSWORD, user.getPassword());
        mEditor.putString(KEY_FIRST_NAME, user.getFirstName());
        mEditor.putString(KEY_MIDDLE_NAME, user.getMiddleName());
        mEditor.putString(KEY_LAST_NAME, user.getLastName());
        mEditor.putString(KEY_EXTN_NAME, user.getExtnName());
        mEditor.putString(KEY_GENDER, user.getGender());
        mEditor.putString(KEY_BIRTHDATE, user.getBirthDate());
        mEditor.putString(KEY_BIRTHPLACE, user.getBirthPlace());
        mEditor.putString(KEY_MOBILE_NO, user.getMobileNo());
        mEditor.putString(KEY_TELEPHONE_NO, user.getTelephoneNo());
        mEditor.putString(KEY_ADDRESS, user.getAddress());
        mEditor.putString(KEY_RANK, user.getRank());
        mEditor.putString(KEY_COMPANY_NAME, user.getCompanyName());
        mEditor.putString(KEY_COMPANY_CONTACT, user.getCompanyContact());
        mEditor.putString(KEY_PROFILE_PHOTO_URI, user.getProfilePhotoURI());
        mEditor.putString(KEY_COVER_PHOTO_URI, user.getCoverPhotoURI());
        mEditor.putString(KEY_DEPARTMENT, user.getDepartment());
        mEditor.putString(KEY_DATE_CREATED, user.getCreatedAt());
        mEditor.putBoolean(KEY_USER_EXIST, true);
        mEditor.apply();

    }

    public User retrieveUserDetails(){
        return new User(
                mSharedPreferences.getInt(KEY_ID, -1),
                mSharedPreferences.getString(KEY_ACCOUNT_TYPE, ""),
                mSharedPreferences.getString(KEY_EMAIL, ""),
                mSharedPreferences.getString(KEY_PASSWORD, ""),
                mSharedPreferences.getString(KEY_FIRST_NAME, ""),
                mSharedPreferences.getString(KEY_MIDDLE_NAME, ""),
                mSharedPreferences.getString(KEY_LAST_NAME, ""),
                mSharedPreferences.getString(KEY_EXTN_NAME, ""),
                mSharedPreferences.getString(KEY_GENDER, ""),
                mSharedPreferences.getString(KEY_BIRTHDATE, ""),
                mSharedPreferences.getString(KEY_BIRTHPLACE, ""),
                mSharedPreferences.getString(KEY_MOBILE_NO, ""),
                mSharedPreferences.getString(KEY_TELEPHONE_NO, ""),
                mSharedPreferences.getString(KEY_ADDRESS, ""),
                mSharedPreferences.getString(KEY_RANK, ""),
                mSharedPreferences.getString(KEY_COMPANY_NAME, ""),
                mSharedPreferences.getString(KEY_COMPANY_CONTACT, ""),
                mSharedPreferences.getString(KEY_PROFILE_PHOTO_URI, ""),
                mSharedPreferences.getString(KEY_COVER_PHOTO_URI, ""),
                mSharedPreferences.getString(KEY_DEPARTMENT, ""),
                mSharedPreferences.getString(KEY_DATE_CREATED, "")
        );

    }

}
