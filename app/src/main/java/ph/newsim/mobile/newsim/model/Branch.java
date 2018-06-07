package ph.newsim.mobile.newsim.model;

import android.os.Parcel;
import android.os.Parcelable;

public class Branch implements Parcelable{

    public static final String KEY = "BRANCH_KEY";

    private int mId;
    private String mName;
    private int mCoverPhoto;
    private String mAddress;
    private String mPhoneNumber;
    private String mEmail;
    private String mFacebookPage;
    private String mlocationURI;

    // Constructors

    public Branch(int id, String name, int coverPhoto, String address, String phoneNumber, String email, String facebookPage) {
        mId = id;
        mName = name;
        mCoverPhoto = coverPhoto;
        mAddress = address;
        mPhoneNumber = phoneNumber;
        mEmail = email;
        mFacebookPage = facebookPage;
    }

    public Branch(String name, String address, String phoneNumber, int coverPhoto, String locationURI) {
        mName = name;
        mAddress = address;
        mPhoneNumber = phoneNumber;
        mCoverPhoto = coverPhoto;
        mlocationURI = locationURI;
    }

    protected Branch(Parcel in) {
        mId = in.readInt();
        mName = in.readString();
        mCoverPhoto = in.readInt();
        mAddress = in.readString();
        mPhoneNumber = in.readString();
        mEmail = in.readString();
        mFacebookPage = in.readString();
        mlocationURI = in.readString();
    }

    public static final Creator<Branch> CREATOR = new Creator<Branch>() {
        @Override
        public Branch createFromParcel(Parcel in) {
            return new Branch(in);
        }

        @Override
        public Branch[] newArray(int size) {
            return new Branch[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(mId);
        dest.writeString(mName);
        dest.writeInt(mCoverPhoto);
        dest.writeString(mAddress);
        dest.writeString(mPhoneNumber);
        dest.writeString(mEmail);
        dest.writeString(mFacebookPage);
        dest.writeString(mlocationURI);
    }

    // Getters and Setters

    public int getId() {
        return mId;
    }

    public void setId(int id) {
        mId = id;
    }

    public String getName() {
        return mName;
    }

    public void setName(String name) {
        mName = name;
    }

    public int getCoverPhoto() {
        return mCoverPhoto;
    }

    public void setCoverPhoto(int coverPhoto) {
        mCoverPhoto = coverPhoto;
    }

    public String getAddress() {
        return mAddress;
    }

    public void setAddress(String address) {
        mAddress = address;
    }

    public String getPhoneNumber() {
        return mPhoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        mPhoneNumber = phoneNumber;
    }

    public String getEmail() {
        return mEmail;
    }

    public void setEmail(String email) {
        mEmail = email;
    }

    public String getFacebookPage() {
        return mFacebookPage;
    }

    public void setFacebookPage(String facebookPage) {
        mFacebookPage = facebookPage;
    }

    public String getlocationURI() {
        return mlocationURI;
    }

    public void setlocationURI(String mlocationURI) {
        this.mlocationURI = mlocationURI;
    }
}
