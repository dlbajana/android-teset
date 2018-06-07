package ph.newsim.mobile.newsim.model;

import android.os.Parcel;
import android.os.Parcelable;

public class User implements Parcelable{

    public static final String KEY = "USER_KEY";

    public static final String ACCOUNT_TYPE_FACEBOOK = "FACEBOOK";
    public static final String ACCOUNT_TYPE_GOOGLE = "GOOGLE";
    public static final String ACCOUNT_TYPE_NEWSIM = "NEWSIM";

    private int mId;
    private String mAccountType;
    private String mEmail;
    private String mPassword;
    private String mFirstName;
    private String mMiddleName;
    private String mLastName;
    private String mExtnName;
    private String mGender;
    private String mBirthDate;
    private String mBirthPlace;
    private String mMobileNo;
    private String mTelephoneNo;
    private String mAddress;
    private String mRank;
    private String mCompanyName;
    private String mCompanyContact;
    private String mProfilePhotoURI;
    private String mCoverPhotoURI;
    private String mDepartment;
    private String mCreatedAt;

    // Constructors

    public User(int id, String accountType, String email, String password, String firstName, String middleName, String lastName, String extnName, String gender, String birthDate, String birthPlace, String mobileNo, String telephoneNo, String address, String rank, String companyName, String companyContact, String profilePhotoURI, String coverPhotoURI, String department, String createdAt) {
        mId = id;
        mAccountType = accountType;
        mEmail = email;
        mPassword = password;
        mFirstName = firstName;
        mMiddleName = middleName;
        mLastName = lastName;
        mExtnName = extnName;
        mGender = gender;
        mBirthDate = birthDate;
        mBirthPlace = birthPlace;
        mMobileNo = mobileNo;
        mTelephoneNo = telephoneNo;
        mAddress = address;
        mRank = rank;
        mCompanyName = companyName;
        mCompanyContact = companyContact;
        mProfilePhotoURI = profilePhotoURI;
        mCoverPhotoURI = coverPhotoURI;
        mDepartment = department;
        mCreatedAt = createdAt;
    }

    public User(String accountType, String email, String firstName, String middleName, String lastName, String gender, String birthDate, String profilePhotoURI, String coverPhotoURI){
        mAccountType = accountType;
        mEmail = email;
        mPassword = "";
        mFirstName = firstName;
        mMiddleName = middleName;
        mLastName = lastName;
        mGender = gender;
        mBirthDate = birthDate;
        mProfilePhotoURI = profilePhotoURI;
        mCoverPhotoURI = coverPhotoURI;
    }

    public User(String email, String password){
        mEmail = email;
        mPassword = password;
        mAccountType = ACCOUNT_TYPE_NEWSIM;
    }

    protected User(Parcel in) {
        mId = in.readInt();
        mAccountType = in.readString();
        mEmail = in.readString();
        mPassword = in.readString();
        mFirstName = in.readString();
        mMiddleName = in.readString();
        mLastName = in.readString();
        mExtnName = in.readString();
        mGender = in.readString();
        mBirthDate = in.readString();
        mBirthPlace = in.readString();
        mMobileNo = in.readString();
        mTelephoneNo = in.readString();
        mAddress = in.readString();
        mRank = in.readString();
        mCompanyName = in.readString();
        mCompanyContact = in.readString();
        mProfilePhotoURI = in.readString();
        mCoverPhotoURI = in.readString();
        mDepartment = in.readString();
        mCreatedAt = in.readString();
    }

    public static final Creator<User> CREATOR = new Creator<User>() {
        @Override
        public User createFromParcel(Parcel in) {
            return new User(in);
        }

        @Override
        public User[] newArray(int size) {
            return new User[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(mId);
        dest.writeString(mAccountType);
        dest.writeString(mEmail);
        dest.writeString(mPassword);
        dest.writeString(mFirstName);
        dest.writeString(mMiddleName);
        dest.writeString(mLastName);
        dest.writeString(mExtnName);
        dest.writeString(mGender);
        dest.writeString(mBirthDate);
        dest.writeString(mBirthPlace);
        dest.writeString(mMobileNo);
        dest.writeString(mTelephoneNo);
        dest.writeString(mAddress);
        dest.writeString(mRank);
        dest.writeString(mCompanyName);
        dest.writeString(mCompanyContact);
        dest.writeString(mProfilePhotoURI);
        dest.writeString(mCoverPhotoURI);
        dest.writeString(mDepartment);
        dest.writeString(mCreatedAt);
    }

    // Getters and Setters

    public int getId() {
        return mId;
    }

    public void setId(int id) {
        mId = id;
    }

    public String getAccountType() {
        return mAccountType;
    }

    public void setAccountType(String accountType) {
        mAccountType = accountType;
    }

    public String getEmail() {
        return mEmail;
    }

    public void setEmail(String email) {
        mEmail = email;
    }

    public String getPassword() {
        return mPassword == null ? "" : mPassword;
    }

    public void setPassword(String password) {
        mPassword = password;
    }

    public String getFirstName() {
        return mFirstName == null ? "" : mFirstName;
    }

    public void setFirstName(String firstName) {
        mFirstName = firstName;
    }

    public String getMiddleName() {
        return mMiddleName == null ? "" : mMiddleName;
    }

    public void setMiddleName(String middleName) {
        mMiddleName = middleName;
    }

    public String getLastName() {
        return mLastName == null ? "" : mLastName;
    }

    public void setLastName(String lastName) {
        mLastName = lastName;
    }

    public String getExtnName() {
        return mExtnName == null ? "" : mExtnName;
    }

    public void setExtnName(String extnName) {
        mExtnName = extnName;
    }

    public String getGender() {
        return mGender == null ? "" : mGender;
    }

    public void setGender(String gender) {
        mGender = gender;
    }

    public String getBirthDate() {
        return mBirthDate == null ? "" : mBirthDate;
    }

    public void setBirthDate(String birthDate) {
        mBirthDate = birthDate;
    }

    public String getBirthPlace() {
        return mBirthPlace == null ? "" : mBirthPlace;
    }

    public void setBirthPlace(String birthPlace) {
        mBirthPlace = birthPlace;
    }

    public String getMobileNo() {
        return mMobileNo == null ? "" : mMobileNo;
    }

    public void setMobileNo(String mobileNo) {
        mMobileNo = mobileNo;
    }

    public String getTelephoneNo() {
        return mTelephoneNo == null ? "" : mTelephoneNo;
    }

    public void setTelephoneNo(String telephoneNo) {
        mTelephoneNo = telephoneNo;
    }

    public String getAddress() {
        return mAddress == null ? "" : mAddress;
    }

    public void setAddress(String address) {
        mAddress = address;
    }

    public String getRank() {
        return mRank == null ? "" : mRank;
    }

    public void setRank(String rank) {
        mRank = rank;
    }

    public String getCompanyName() {
        return mCompanyName == null ? "" : mCompanyName;
    }

    public void setCompanyName(String companyName) {
        mCompanyName = companyName;
    }

    public String getCompanyContact() {
        return mCompanyContact == null ? "" : mCompanyContact;
    }

    public void setCompanyContact(String companyContact) {
        mCompanyContact = companyContact;
    }

    public String getProfilePhotoURI() {
        return mProfilePhotoURI == null ? "" : mProfilePhotoURI;
    }

    public void setProfilePhotoURI(String profilePhotoURI) {
        mProfilePhotoURI = profilePhotoURI;
    }

    public String getCoverPhotoURI() {
        return mCoverPhotoURI == null ? "" : mCoverPhotoURI;
    }

    public void setCoverPhotoURI(String coverPhotoURI) {
        mCoverPhotoURI = coverPhotoURI;
    }

    public String getDepartment() {
        return mDepartment == null ? "" : mDepartment;
    }

    public void setDepartment(String department) {
        mDepartment = department;
    }

    public String getCreatedAt() {
        return mCreatedAt == null ? "" : mCreatedAt;
    }

    public void setCreatedAt(String createdAt) {
        mCreatedAt = createdAt;
    }

}
