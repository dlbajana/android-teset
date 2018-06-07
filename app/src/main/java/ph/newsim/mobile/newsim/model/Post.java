package ph.newsim.mobile.newsim.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import ph.newsim.mobile.newsim.util.RelativeDate;

public class Post implements Parcelable{

    public static final String KEY = "POST_KEY";

    public static final int VIEW_TYPE_LIMIT = -1;
    public static final int VIEW_TYPE_LOADING = 0;
    public static final int VIEW_TYPE_NO_IMAGE = 1;
    public static final int VIEW_TYPE_SINGLE_IMAGE = 2;
    public static final int VIEW_TYPE_DUAL_IMAGE = 3;
    public static final int VIEW_TYPE_MULTIPLE_IMAGE = 4;

    private int mId;
    private String mBranch;
    private String mTitle;
    private String mDescription;
    private String mDate;
    private String[] mImages;
    private String[] mImgDescription;

    private int viewType;

    // Constructors

    public Post() {
        viewType = VIEW_TYPE_LOADING;
    }

    public Post(int id, String branch, String title, String description, String date, String[] images, String[] imgDescription) {
        mId = id;
        mBranch = branch;
        mTitle = title;
        mDescription = description;
        mDate = date;
        mImages = images;
        mImgDescription = imgDescription;

        switch (images.length){
            case 0:
                viewType = VIEW_TYPE_NO_IMAGE;
                break;
            case 1:
                viewType = VIEW_TYPE_SINGLE_IMAGE;
                break;
            case 2:
                viewType = VIEW_TYPE_DUAL_IMAGE;
                break;
            default:
                viewType = VIEW_TYPE_MULTIPLE_IMAGE;
                break;
        }
    }

    protected Post(Parcel in) {
        mId = in.readInt();
        mBranch = in.readString();
        mTitle = in.readString();
        mDescription = in.readString();
        mDate = in.readString();
        mImages = in.createStringArray();
        mImgDescription = in.createStringArray();
        viewType = in.readInt();
    }

    public static final Creator<Post> CREATOR = new Creator<Post>() {
        @Override
        public Post createFromParcel(Parcel in) {
            return new Post(in);
        }

        @Override
        public Post[] newArray(int size) {
            return new Post[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(mId);
        dest.writeString(mBranch);
        dest.writeString(mTitle);
        dest.writeString(mDescription);
        dest.writeString(mDate);
        dest.writeStringArray(mImages);
        dest.writeStringArray(mImgDescription);
        dest.writeInt(viewType);
    }

    // Getters and Setters

    public int getId() {
        return mId;
    }

    public void setId(int id) {
        mId = id;
    }

    public String getBranch() {
        return mBranch;
    }

    public void setBranch(String branch) {
        mBranch = branch;
    }

    public String getTitle() {
        return mTitle;
    }

    public void setTitle(String title) {
        mTitle = title;
    }

    public String getDescription() {
        return mDescription;
    }

    public void setDescription(String description) {
        mDescription = description;
    }

    public String getDate() {
        return mDate;
    }

    public String getRelativeDate(){
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss", Locale.US);

        long dateMillis = 0;
        try{
            Date newDate = dateFormat.parse(mDate);
            dateMillis = newDate.getTime();
        } catch (ParseException ignored){

        }

        return RelativeDate.getRelativeDateTime(dateMillis, "EEEE, MMMM d");
    }

    public void setDate(String date) {
        mDate = date;
    }

    public String[] getImages() {
        return mImages;
    }

    public void setImages(String[] images) {
        mImages = images;
    }

    public String[] getImgDescription() {
        return mImgDescription;
    }

    public void setImgDescription(String[] imgDescription) {
        mImgDescription = imgDescription;
    }

    public int getViewType() {
        return viewType;
    }

    public void setViewType(int viewType) {
        this.viewType = viewType;
    }

}
