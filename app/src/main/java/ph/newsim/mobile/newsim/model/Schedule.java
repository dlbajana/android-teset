package ph.newsim.mobile.newsim.model;

import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Schedule implements Parcelable{

    public static final String KEY = "SCHEDULE_KEY";

    public static final int VIEW_TYPE_LIMIT = -1;
    public static final int VIEW_TYPE_LOADING = 0;
    public static final int VIEW_TYPE_CONTENT = 1;

    private int mId;
    private String mCourseCode;
    private String mCourseDescription;
    private String mDuration;
    private String mDateStart;
    private String mDateEnd;
    private String mBatch;
    private String mSession;
    private String mTimeStartH;
    private String mTimeStartM;
    private String mTimeEndH;
    private String mTimeEndM;
    private String mVenue;
    private String mRoom;
    private String mInstructor;
    private String mAssessor;
    private String mTimeSchedule;
    private String mBranchName;
    private int mTraineeCount;

    private int viewType;

    // Constructors

    public Schedule(int id, String courseCode, String courseDescription, String duration, String dateStart, String dateEnd, String batch, String session, String timeStartH, String timeStartM, String timeEndH, String timeEndM, String venue, String room, String instructor, String assessor, String timeSchedule, String branchName, int traineeCount) {
        mId = id;
        mCourseCode = courseCode;
        mCourseDescription = courseDescription;
        mDuration = duration;
        mDateStart = dateStart;
        mDateEnd = dateEnd;
        mBatch = batch;
        mSession = session;
        mTimeStartH = timeStartH;
        mTimeStartM = timeStartM;
        mTimeEndH = timeEndH;
        mTimeEndM = timeEndM;
        mVenue = venue;
        mRoom = room;
        mInstructor = instructor;
        mAssessor = assessor;
        mTimeSchedule = timeSchedule;
        mBranchName = branchName;
        mTraineeCount = traineeCount;
        viewType = VIEW_TYPE_CONTENT;
    }

    public Schedule() {
        viewType = VIEW_TYPE_LOADING;
    }

    protected Schedule(Parcel in) {
        mId = in.readInt();
        mCourseCode = in.readString();
        mCourseDescription = in.readString();
        mDuration = in.readString();
        mDateStart = in.readString();
        mDateEnd = in.readString();
        mBatch = in.readString();
        mSession = in.readString();
        mTimeStartH = in.readString();
        mTimeStartM = in.readString();
        mTimeEndH = in.readString();
        mTimeEndM = in.readString();
        mVenue = in.readString();
        mRoom = in.readString();
        mInstructor = in.readString();
        mAssessor = in.readString();
        mTimeSchedule = in.readString();
        mBranchName = in.readString();
        mTraineeCount = in.readInt();
        viewType = in.readInt();
    }

    public static final Creator<Schedule> CREATOR = new Creator<Schedule>() {
        @Override
        public Schedule createFromParcel(Parcel in) {
            return new Schedule(in);
        }

        @Override
        public Schedule[] newArray(int size) {
            return new Schedule[size];
        }
    };



    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(mId);
        dest.writeString(mCourseCode);
        dest.writeString(mCourseDescription);
        dest.writeString(mDuration);
        dest.writeString(mDateStart);
        dest.writeString(mDateEnd);
        dest.writeString(mBatch);
        dest.writeString(mSession);
        dest.writeString(mTimeStartH);
        dest.writeString(mTimeStartM);
        dest.writeString(mTimeEndH);
        dest.writeString(mTimeEndM);
        dest.writeString(mVenue);
        dest.writeString(mRoom);
        dest.writeString(mInstructor);
        dest.writeString(mAssessor);
        dest.writeString(mTimeSchedule);
        dest.writeString(mBranchName);
        dest.writeInt(mTraineeCount);
        dest.writeInt(viewType);
    }

    // Getters and Setters

    public int getId() {
        return mId;
    }

    public void setId(int id) {
        mId = id;
    }

    public String getCourseCode() {
        return mCourseCode;
    }

    public void setCourseCode(String courseCode) {
        mCourseCode = courseCode;
    }

    public String getCourseDescription() {
        return mCourseDescription;
    }

    public void setCourseDescription(String courseDescription) {
        mCourseDescription = courseDescription;
    }

    public String getDuration() {
        return mDuration;
    }

    public void setDuration(String duration) {
        mDuration = duration;
    }

    public String getDateStart() {
        return mDateStart;
    }

    public void setDateStart(String dateStart) {
        mDateStart = dateStart;
    }

    public String getFormattedDateStart(String format){
        String formattedDate = "";
        SimpleDateFormat inputFormat = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat outputFormat = new SimpleDateFormat(format); //MMM d yyyy
        try {
            Date initialDate = inputFormat.parse(mDateStart);
            formattedDate = outputFormat.format(initialDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return formattedDate;

    }

    public String getDateEnd() {
        return mDateEnd;
    }

    public void setDateEnd(String dateEnd) {
        mDateEnd = dateEnd;
    }

    public String getFormattedDateEnd(String format){
        String formattedDate = "";
        SimpleDateFormat inputFormat = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat outputFormat = new SimpleDateFormat(format);
        try {
            Date initialDate = inputFormat.parse(mDateEnd);
            formattedDate = outputFormat.format(initialDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return formattedDate;

    }

    public String getBatch() {
        return mBatch;
    }

    public void setBatch(String batch) {
        mBatch = batch;
    }

    public String getSession() {
        return mSession;
    }

    public void setSession(String session) {
        mSession = session;
    }

    public String getTimeStartH() {
        return mTimeStartH;
    }

    public void setTimeStartH(String timeStartH) {
        mTimeStartH = timeStartH;
    }

    public String getTimeStartM() {
        return mTimeStartM;
    }

    public void setTimeStartM(String timeStartM) {
        mTimeStartM = timeStartM;
    }

    public String getFormattedTimeStart(){
        String formattedTime;
        int hourIntValue = Integer.parseInt(mTimeStartH);

        if (hourIntValue > 12){
            hourIntValue -= 12;
            formattedTime = hourIntValue + ":" + mTimeStartM + " PM";
        }else{
            formattedTime = hourIntValue + ":" + mTimeStartM + " AM";
        }

        return formattedTime;

    }

    public String getTimeEndH() {
        return mTimeEndH;
    }

    public void setTimeEndH(String timeEndH) {
        mTimeEndH = timeEndH;
    }

    public String getTimeEndM() {
        return mTimeEndM;
    }

    public void setTimeEndM(String timeEndM) {
        mTimeEndM = timeEndM;
    }

    public String getFormattedTimeEnd(){
        String formattedTime = "";
        int hourIntValue = Integer.parseInt(mTimeEndH);

        if (hourIntValue > 12){
            hourIntValue -= 12;
            formattedTime = hourIntValue + ":" + mTimeEndM + " PM";
        }else{
            formattedTime = hourIntValue + ":" + mTimeEndM + " AM";
        }

        return formattedTime;
    }

    public String getVenue() {
        return mVenue;
    }

    public void setVenue(String venue) {
        mVenue = venue;
    }

    public String getRoom() {
        return mRoom;
    }

    public void setRoom(String room) {
        mRoom = room;
    }

    public String getInstructor() {
        if (mInstructor == null){
            return "";
        }else if (mInstructor.equals("null")){
            return "";
        }else{
            return mInstructor;
        }
    }

    public void setInstructor(String instructor) {
        mInstructor = instructor;
    }

    public String getAssessor() {
        if (mAssessor == null){
            return "";
        }else if (mAssessor.equals("null")){
            return "";
        }else{
            return mAssessor;
        }
    }

    public void setAssessor(String assessor) {
        mAssessor = assessor;
    }

    public String getTimeSchedule() {
        return mTimeSchedule;
    }

    public void setTimeSchedule(String timeSchedule) {
        mTimeSchedule = timeSchedule;
    }

    public String getBranchName() {
        return mBranchName;
    }

    public void setBranchName(String branchName) {
        mBranchName = branchName;
    }

    public int getTraineeCount() {
        return mTraineeCount;
    }

    public void setTraineeCount(int traineeCount) {
        mTraineeCount = traineeCount;
    }

    public int getIntDuration(){
        String[] dayCount = mDuration.split("\\s+");
        int duration;
        try{
            duration = Integer.parseInt(dayCount[0]);
        }catch (NumberFormatException e){
            duration = 0;
        }
        return duration;
    }

    public int getViewType() {
        return viewType;
    }

    public void setViewType(int viewType) {
        this.viewType = viewType;
    }
}
