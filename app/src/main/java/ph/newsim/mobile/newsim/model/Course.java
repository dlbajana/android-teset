package ph.newsim.mobile.newsim.model;

import android.os.Parcel;
import android.os.Parcelable;

public class Course implements Parcelable{

    public static final String KEY = "COURSE_KEY";

    public static final String CATEGORY_COMMON = "COMMON";
    public static final String CATEGORY_DECK = "DECK";
    public static final String CATEGORY_ENGINE = "ENGINE";

    public static final String TYPE_ANTI_PIRACY_AWARENESS_TRAINING = "ANTI PIRACY AWARENESS TRAINING";
    public static final String TYPE_ASSESSMENT = "ASSESSMENT";
    public static final String TYPE_COMMON = "COMMON";
    public static final String TYPE_DECK = "DECK";
    public static final String TYPE_ENGINE = "ENGINE";
    public static final String TYPE_TRAINING = "TRAINING";

    public static final String STATUS_REGULAR = "REGULAR";
    public static final String STATUS_SPECIAL = "SPECIAL";

    public static final int VIEW_TYPE_LIMIT = -1;
    public static final int VIEW_TYPE_LOADING = 0;
    public static final int VIEW_TYPE_CONTENT = 1;

    private int mId;
    private int mBranchId;
    private String mCode;
    private String mDescription;
    private String mCategory;
    private String mType;
    private String mLevel;
    private String mDuration;
    private String mStatus;

    private int viewType;

    // Constructors

    public Course() {
        viewType = VIEW_TYPE_LOADING;
    }

    public Course(int id, int branchId, String code, String description, String category, String type) {
        mId = id;
        mBranchId = branchId;
        mCode = code;
        mDescription = description;
        mCategory = category;
        mType = type;
        mLevel = "";
        mDuration = "";
        mStatus = STATUS_REGULAR;
        viewType = VIEW_TYPE_CONTENT;

    }

    public Course(int id, int branchId, String code, String description, String category, String type, String level, String duration, String status) {
        mId = id;
        mBranchId = branchId;
        mCode = code;
        mDescription = description;
        mCategory = category;
        mType = type;
        mLevel = level;
        mDuration = duration;
        mStatus = status;
        viewType = VIEW_TYPE_CONTENT;
    }

    protected Course(Parcel in) {
        mId = in.readInt();
        mBranchId = in.readInt();
        mCode = in.readString();
        mDescription = in.readString();
        mCategory = in.readString();
        mType = in.readString();
        mLevel = in.readString();
        mDuration = in.readString();
        mStatus = in.readString();
        viewType = in.readInt();
    }

    public static final Creator<Course> CREATOR = new Creator<Course>() {
        @Override
        public Course createFromParcel(Parcel in) {
            return new Course(in);
        }

        @Override
        public Course[] newArray(int size) {
            return new Course[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(mId);
        dest.writeInt(mBranchId);
        dest.writeString(mCode);
        dest.writeString(mDescription);
        dest.writeString(mCategory);
        dest.writeString(mType);
        dest.writeString(mLevel);
        dest.writeString(mDuration);
        dest.writeString(mStatus);
        dest.writeInt(viewType);
    }

    // Getters and Setters

    public int getId() {
        return mId;
    }

    public void setId(int id) {
        mId = id;
    }

    public int getBranchId() {
        return mBranchId;
    }

    public void setBranchId(int branchId) {
        mBranchId = branchId;
    }

    public String getCode() {
        return mCode;
    }

    public void setCode(String code) {
        mCode = code;
    }

    public String getDescription() {
        return mDescription;
    }

    public void setDescription(String description) {
        mDescription = description;
    }

    public String getCategory() {
        return mCategory;
    }

    public void setCategory(String category) {
        mCategory = category;
    }

    public String getType() {
        return mType;
    }

    public void setType(String type) {
        mType = type;
    }

    public String getLevel() {
        return mLevel;
    }

    public void setLevel(String level) {
        mLevel = level;
    }

    public String getDuration() {
        return mDuration;
    }

    public void setDuration(String duration) {
        mDuration = duration;
    }

    public String getStatus() {
        return mStatus;
    }

    public void setStatus(String status) {
        mStatus = status;
    }

    public int getViewType() {
        return viewType;
    }

    public void setViewType(int viewType) {
        this.viewType = viewType;
    }

}
