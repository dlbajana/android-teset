package ph.newsim.mobile.newsim.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import ph.newsim.mobile.newsim.model.Course;
import ph.newsim.mobile.newsim.model.Post;
import ph.newsim.mobile.newsim.model.Schedule;
import ph.newsim.mobile.newsim.util.JSONDataHandler;


public class
LocalDataSource {

    private LocalSQLiteHelper mLocalSQLiteHelper;

    public LocalDataSource(Context context) {
        mLocalSQLiteHelper = new LocalSQLiteHelper(context);
    }

    private SQLiteDatabase open(){
        return mLocalSQLiteHelper.getWritableDatabase();
    }

    private void close(SQLiteDatabase database){
        database.close();
    }

    public void store(Post post){
        SQLiteDatabase db = open();
        db.beginTransaction();

        ContentValues postValues = new ContentValues();
        postValues.put(LocalSQLiteHelper.NEWS_ID, post.getId());
        postValues.put(LocalSQLiteHelper.NEWS_TITLE, post.getTitle());
        postValues.put(LocalSQLiteHelper.NEWS_DESCRIPTION, post.getDescription());
        postValues.put(LocalSQLiteHelper.NEWS_BRANCH, post.getBranch());
        postValues.put(LocalSQLiteHelper.NEWS_DATE_POSTED, post.getDate());

        db.insert(LocalSQLiteHelper.TABLE_NEWS, null, postValues);

        for (int i = 0; i < post.getImages().length; i++){
            ContentValues imageValues = new ContentValues();
            imageValues.put(LocalSQLiteHelper.IMAGE_ID_NEWS, post.getId());
            imageValues.put(LocalSQLiteHelper.IMAGE_URI, post.getImages()[i]);
            imageValues.put(LocalSQLiteHelper.IMAGE_DESCRIPTION, post.getImgDescription()[i]);

            db.insert(LocalSQLiteHelper.TABLE_IMAGE, null, imageValues);
        }

        db.setTransactionSuccessful();
        db.endTransaction();
        close(db);

    }

    public void store(Course course){
        SQLiteDatabase db = open();
        db.beginTransaction();

        ContentValues courseValues = new ContentValues();
        courseValues.put(LocalSQLiteHelper.COURSE_ID, course.getId());
        courseValues.put(LocalSQLiteHelper.COURSE_BRANCH_ID, course.getBranchId());
        courseValues.put(LocalSQLiteHelper.COURSE_CODE, course.getCode());
        courseValues.put(LocalSQLiteHelper.COURSE_DESCRIPTION, course.getDescription());
        courseValues.put(LocalSQLiteHelper.COURSE_CATEGORY, course.getCategory());
        courseValues.put(LocalSQLiteHelper.COURSE_TYPE, course.getType());
        courseValues.put(LocalSQLiteHelper.COURSE_LEVEL, course.getLevel());
        courseValues.put(LocalSQLiteHelper.COURSE_DURATION, course.getDuration());
        courseValues.put(LocalSQLiteHelper.COURSE_STATUS, course.getStatus());

        db.insert(LocalSQLiteHelper.TABLE_COURSE, null, courseValues);
        db.setTransactionSuccessful();
        db.endTransaction();
        close(db);
    }

    public void store(Schedule schedule){
        SQLiteDatabase db = open();
        db.beginTransaction();

        ContentValues scheduleValues = new ContentValues();
        scheduleValues.put(LocalSQLiteHelper.SCHEDULE_ID, schedule.getId());
        scheduleValues.put(LocalSQLiteHelper.SCHEDULE_COURSE_CODE, schedule.getCourseCode());
        scheduleValues.put(LocalSQLiteHelper.SCHEDULE_COURSE_DESCRIPTION, schedule.getCourseDescription());
        scheduleValues.put(LocalSQLiteHelper.SCHEDULE_DURATION, schedule.getDuration());
        scheduleValues.put(LocalSQLiteHelper.SCHEDULE_FROM, schedule.getDateStart());
        scheduleValues.put(LocalSQLiteHelper.SCHEDULE_TO, schedule.getDateEnd());
        scheduleValues.put(LocalSQLiteHelper.SCHEDULE_BATCH, schedule.getBatch());
        scheduleValues.put(LocalSQLiteHelper.SCHEDULE_SESSION, schedule.getSession());
        scheduleValues.put(LocalSQLiteHelper.SCHEDULE_S_TIME_H, schedule.getTimeStartH());
        scheduleValues.put(LocalSQLiteHelper.SCHEDULE_S_TIME_M, schedule.getTimeStartM());
        scheduleValues.put(LocalSQLiteHelper.SCHEDULE_E_TIME_H, schedule.getTimeEndH());
        scheduleValues.put(LocalSQLiteHelper.SCHEDULE_E_TIME_M , schedule.getTimeEndM());
        scheduleValues.put(LocalSQLiteHelper.SCHEDULE_VENUE, schedule.getVenue());
        scheduleValues.put(LocalSQLiteHelper.SCHEDULE_ROOM, schedule.getRoom());
        scheduleValues.put(LocalSQLiteHelper.SCHEDULE_INSTRUCTOR, schedule.getInstructor());
        scheduleValues.put(LocalSQLiteHelper.SCHEDULE_ASSESSOR, schedule.getAssessor());
        scheduleValues.put(LocalSQLiteHelper.SCHEDULE_TIME_SCHEDULE, schedule.getTimeSchedule());
        scheduleValues.put(LocalSQLiteHelper.SCHEDULE_BRANCH_NAME, schedule.getBranchName());
        scheduleValues.put(LocalSQLiteHelper.SCHEDULE_TRAINEE_COUNT, schedule.getTraineeCount());

        db.insert(LocalSQLiteHelper.TABLE_SCHEDULE, null, scheduleValues);
        db.setTransactionSuccessful();
        db.endTransaction();
        close(db);
    }

    public List<Post> retrievePost(){
        List<Post> postArrayList = new ArrayList<>();
        SQLiteDatabase db = open();
        String query = "SELECT " + LocalSQLiteHelper.NEWS_ID + ", " + LocalSQLiteHelper.NEWS_TITLE + ", " + LocalSQLiteHelper.NEWS_DESCRIPTION + ", " + LocalSQLiteHelper.NEWS_BRANCH + ", " + LocalSQLiteHelper.NEWS_DATE_POSTED
                + " FROM " + LocalSQLiteHelper.TABLE_NEWS + " ORDER BY " + LocalSQLiteHelper.NEWS_ID + " DESC;";

        Cursor cursor = db.rawQuery(query, null);
        while(cursor.moveToNext()){
            String secondQuery = "SELECT " + LocalSQLiteHelper.IMAGE_ID_NEWS + ", " + LocalSQLiteHelper.IMAGE_URI + ", " + LocalSQLiteHelper.IMAGE_DESCRIPTION
                    + " FROM " + LocalSQLiteHelper.TABLE_IMAGE + " WHERE " + LocalSQLiteHelper.IMAGE_ID_NEWS + " = " +cursor.getInt(cursor.getColumnIndex(LocalSQLiteHelper.NEWS_ID)) + ";";

            Cursor cursorSecond = db.rawQuery(secondQuery, null);
            String[] imagesURI = new String[cursorSecond.getCount()];
            String[] imagesDescription = new String[cursorSecond.getCount()];
            int rowIndex = 0;
            while(cursorSecond.moveToNext()){
                imagesURI[rowIndex] = cursorSecond.getString(cursorSecond.getColumnIndex(LocalSQLiteHelper.IMAGE_URI));
                imagesDescription[rowIndex] = cursorSecond.getString(cursorSecond.getColumnIndex(LocalSQLiteHelper.IMAGE_DESCRIPTION));
                rowIndex++;
            }
            cursorSecond.close();

            Post currentRow = new Post(
                    cursor.getInt(cursor.getColumnIndex(LocalSQLiteHelper.NEWS_ID)),
                    cursor.getString(cursor.getColumnIndex(LocalSQLiteHelper.NEWS_BRANCH)),
                    cursor.getString(cursor.getColumnIndex(LocalSQLiteHelper.NEWS_TITLE)),
                    cursor.getString(cursor.getColumnIndex(LocalSQLiteHelper.NEWS_DESCRIPTION)),
                    cursor.getString(cursor.getColumnIndex(LocalSQLiteHelper.NEWS_DATE_POSTED)),
                    imagesURI,
                    imagesDescription
            );
            postArrayList.add(currentRow);
        }
        cursor.close();
        close(db);
        return postArrayList;
    }

    public List<Course> retrieveCourse(){
        List<Course> courseArrayList = new ArrayList<>();
        SQLiteDatabase db = open();
        String query = "SELECT " + LocalSQLiteHelper.COURSE_ID + ", " + LocalSQLiteHelper.COURSE_BRANCH_ID +", " + LocalSQLiteHelper.COURSE_CODE + ", " + LocalSQLiteHelper.COURSE_DESCRIPTION + ", "
                + LocalSQLiteHelper.COURSE_CATEGORY + ", " + LocalSQLiteHelper.COURSE_TYPE + ", " + LocalSQLiteHelper.COURSE_LEVEL + ", " + LocalSQLiteHelper.COURSE_DURATION + ", " + LocalSQLiteHelper.COURSE_STATUS
                + " FROM " + LocalSQLiteHelper.TABLE_COURSE + ";";

        Cursor cursor = db.rawQuery(query, null);
        while(cursor.moveToNext()){
            Course currentRow = new Course(
                    cursor.getInt(cursor.getColumnIndex(LocalSQLiteHelper.COURSE_ID)), cursor.getInt(cursor.getColumnIndex(LocalSQLiteHelper.COURSE_BRANCH_ID)), cursor.getString(cursor.getColumnIndex(LocalSQLiteHelper.COURSE_CODE)),
                    cursor.getString(cursor.getColumnIndex(LocalSQLiteHelper.COURSE_DESCRIPTION)), cursor.getString(cursor.getColumnIndex(LocalSQLiteHelper.COURSE_CATEGORY)), cursor.getString(cursor.getColumnIndex(LocalSQLiteHelper.COURSE_TYPE)),
                    cursor.getString(cursor.getColumnIndex(LocalSQLiteHelper.COURSE_LEVEL)), cursor.getString(cursor.getColumnIndex(LocalSQLiteHelper.COURSE_DURATION)), cursor.getString(cursor.getColumnIndex(LocalSQLiteHelper.COURSE_STATUS)));
            courseArrayList.add(currentRow);
        }
        cursor.close();
        close(db);
        return courseArrayList;
    }

    public List<Course> retrieveCourseByBranchId(int branchId){
        List<Course> courseArrayList = new ArrayList<>();
        SQLiteDatabase db = open();
        String query = "SELECT " + LocalSQLiteHelper.COURSE_ID + ", " + LocalSQLiteHelper.COURSE_BRANCH_ID +", " + LocalSQLiteHelper.COURSE_CODE + ", " + LocalSQLiteHelper.COURSE_DESCRIPTION + ", "
                + LocalSQLiteHelper.COURSE_CATEGORY + ", " + LocalSQLiteHelper.COURSE_TYPE + ", " + LocalSQLiteHelper.COURSE_LEVEL + ", " + LocalSQLiteHelper.COURSE_DURATION + ", " + LocalSQLiteHelper.COURSE_STATUS
                + " FROM " + LocalSQLiteHelper.TABLE_COURSE + " WHERE " + LocalSQLiteHelper.COURSE_BRANCH_ID + " = " + branchId + " ORDER BY " + LocalSQLiteHelper.COURSE_CODE + " ASC, " + LocalSQLiteHelper.COURSE_ID + " ASC;";

        Cursor cursor = db.rawQuery(query, null);
        while(cursor.moveToNext()){
            Course currentRow = new Course(
                    cursor.getInt(cursor.getColumnIndex(LocalSQLiteHelper.COURSE_ID)), cursor.getInt(cursor.getColumnIndex(LocalSQLiteHelper.COURSE_BRANCH_ID)), cursor.getString(cursor.getColumnIndex(LocalSQLiteHelper.COURSE_CODE)),
                    cursor.getString(cursor.getColumnIndex(LocalSQLiteHelper.COURSE_DESCRIPTION)), cursor.getString(cursor.getColumnIndex(LocalSQLiteHelper.COURSE_CATEGORY)), cursor.getString(cursor.getColumnIndex(LocalSQLiteHelper.COURSE_TYPE)),
                    cursor.getString(cursor.getColumnIndex(LocalSQLiteHelper.COURSE_LEVEL)), cursor.getString(cursor.getColumnIndex(LocalSQLiteHelper.COURSE_DURATION)), cursor.getString(cursor.getColumnIndex(LocalSQLiteHelper.COURSE_STATUS)));
            courseArrayList.add(currentRow);
        }
        cursor.close();
        close(db);
        return courseArrayList;
    }

    public List<Schedule> retrieveSchedule(){
        List<Schedule> scheduleArrayList = new ArrayList<>();
        SQLiteDatabase db = open();

        String query = "SELECT " + LocalSQLiteHelper.SCHEDULE_ID + ", " + LocalSQLiteHelper.SCHEDULE_COURSE_CODE + ", " + LocalSQLiteHelper.SCHEDULE_COURSE_DESCRIPTION + ", " + LocalSQLiteHelper.SCHEDULE_DURATION + ", " + LocalSQLiteHelper.SCHEDULE_FROM + ", " + LocalSQLiteHelper.SCHEDULE_TO
                + ", " + LocalSQLiteHelper.SCHEDULE_BATCH + ", " + LocalSQLiteHelper.SCHEDULE_SESSION + ", " + LocalSQLiteHelper.SCHEDULE_S_TIME_H + ", " + LocalSQLiteHelper.SCHEDULE_S_TIME_M
                + ", " + LocalSQLiteHelper.SCHEDULE_E_TIME_H + ", " + LocalSQLiteHelper.SCHEDULE_E_TIME_M + ", " + LocalSQLiteHelper.SCHEDULE_VENUE + ", " + LocalSQLiteHelper.SCHEDULE_VENUE
                + ", " + LocalSQLiteHelper.SCHEDULE_ROOM + ", " + LocalSQLiteHelper.SCHEDULE_INSTRUCTOR + ", " + LocalSQLiteHelper.SCHEDULE_ASSESSOR + ", " + LocalSQLiteHelper.SCHEDULE_TIME_SCHEDULE
                + ", " + LocalSQLiteHelper.SCHEDULE_BRANCH_NAME + ", " + LocalSQLiteHelper.SCHEDULE_TRAINEE_COUNT + " FROM " + LocalSQLiteHelper.TABLE_SCHEDULE + " WHERE " + LocalSQLiteHelper.SCHEDULE_FROM + " > date('now') ORDER BY " + LocalSQLiteHelper.SCHEDULE_FROM
                + " ASC, " + LocalSQLiteHelper.SCHEDULE_COURSE_CODE + " ASC;";

        Cursor cursor = db.rawQuery(query, null);
        while(cursor.moveToNext()){
            Schedule schedule = new Schedule(
                    cursor.getInt(cursor.getColumnIndex(LocalSQLiteHelper.SCHEDULE_ID)),
                    cursor.getString(cursor.getColumnIndex(LocalSQLiteHelper.SCHEDULE_COURSE_CODE)),
                    cursor.getString(cursor.getColumnIndex(LocalSQLiteHelper.SCHEDULE_COURSE_DESCRIPTION)),
                    cursor.getString(cursor.getColumnIndex(LocalSQLiteHelper.SCHEDULE_DURATION)),
                    cursor.getString(cursor.getColumnIndex(LocalSQLiteHelper.SCHEDULE_FROM)),
                    cursor.getString(cursor.getColumnIndex(LocalSQLiteHelper.SCHEDULE_TO)),
                    cursor.getString(cursor.getColumnIndex(LocalSQLiteHelper.SCHEDULE_BATCH)),
                    cursor.getString(cursor.getColumnIndex(LocalSQLiteHelper.SCHEDULE_SESSION)),
                    cursor.getString(cursor.getColumnIndex(LocalSQLiteHelper.SCHEDULE_S_TIME_H)),
                    cursor.getString(cursor.getColumnIndex(LocalSQLiteHelper.SCHEDULE_S_TIME_M)),
                    cursor.getString(cursor.getColumnIndex(LocalSQLiteHelper.SCHEDULE_E_TIME_H)),
                    cursor.getString(cursor.getColumnIndex(LocalSQLiteHelper.SCHEDULE_E_TIME_M)),
                    cursor.getString(cursor.getColumnIndex(LocalSQLiteHelper.SCHEDULE_VENUE)),
                    cursor.getString(cursor.getColumnIndex(LocalSQLiteHelper.SCHEDULE_ROOM)),
                    cursor.getString(cursor.getColumnIndex(LocalSQLiteHelper.SCHEDULE_INSTRUCTOR)),
                    cursor.getString(cursor.getColumnIndex(LocalSQLiteHelper.SCHEDULE_ASSESSOR)),
                    cursor.getString(cursor.getColumnIndex(LocalSQLiteHelper.SCHEDULE_TIME_SCHEDULE)),
                    cursor.getString(cursor.getColumnIndex(LocalSQLiteHelper.SCHEDULE_BRANCH_NAME)),
                    cursor.getInt(cursor.getColumnIndex(LocalSQLiteHelper.SCHEDULE_TRAINEE_COUNT))
            );
            scheduleArrayList.add(schedule);
        }
        cursor.close();
        close(db);
        return scheduleArrayList;
    }

    public List<Schedule> retrieveScheduleByBranchId(int branchId){
        List<Schedule> scheduleArrayList = new ArrayList<>();
        SQLiteDatabase db = open();

        String query = "SELECT " + LocalSQLiteHelper.SCHEDULE_ID + ", " + LocalSQLiteHelper.SCHEDULE_COURSE_CODE + ", " + LocalSQLiteHelper.SCHEDULE_COURSE_DESCRIPTION + ", " + LocalSQLiteHelper.SCHEDULE_DURATION + ", "
                + LocalSQLiteHelper.SCHEDULE_FROM + ", " + LocalSQLiteHelper.SCHEDULE_TO + ", " + LocalSQLiteHelper.SCHEDULE_BATCH + ", " + LocalSQLiteHelper.SCHEDULE_SESSION + ", " + LocalSQLiteHelper.SCHEDULE_S_TIME_H + ", "
                + LocalSQLiteHelper.SCHEDULE_S_TIME_M + ", " + LocalSQLiteHelper.SCHEDULE_E_TIME_H + ", " + LocalSQLiteHelper.SCHEDULE_E_TIME_M + ", " + LocalSQLiteHelper.SCHEDULE_VENUE + ", " + LocalSQLiteHelper.SCHEDULE_VENUE
                + ", " + LocalSQLiteHelper.SCHEDULE_ROOM + ", " + LocalSQLiteHelper.SCHEDULE_INSTRUCTOR + ", " + LocalSQLiteHelper.SCHEDULE_ASSESSOR + ", " + LocalSQLiteHelper.SCHEDULE_TIME_SCHEDULE + ", " + LocalSQLiteHelper.SCHEDULE_BRANCH_NAME
                + ", " + LocalSQLiteHelper.SCHEDULE_TRAINEE_COUNT + " FROM " + LocalSQLiteHelper.TABLE_SCHEDULE + " WHERE " + LocalSQLiteHelper.SCHEDULE_FROM + " > date('now') AND " + LocalSQLiteHelper.SCHEDULE_BRANCH_NAME + " = \""
                + BranchesDataSource.getBranchCode(branchId) + "\" ORDER BY " + LocalSQLiteHelper.SCHEDULE_FROM + " ASC, " + LocalSQLiteHelper.SCHEDULE_COURSE_CODE + " ASC;";

        Cursor cursor = db.rawQuery(query, null);
        while(cursor.moveToNext()){
            Schedule schedule = new Schedule(
                    cursor.getInt(cursor.getColumnIndex(LocalSQLiteHelper.SCHEDULE_ID)),
                    cursor.getString(cursor.getColumnIndex(LocalSQLiteHelper.SCHEDULE_COURSE_CODE)),
                    cursor.getString(cursor.getColumnIndex(LocalSQLiteHelper.SCHEDULE_COURSE_DESCRIPTION)),
                    cursor.getString(cursor.getColumnIndex(LocalSQLiteHelper.SCHEDULE_DURATION)),
                    cursor.getString(cursor.getColumnIndex(LocalSQLiteHelper.SCHEDULE_FROM)),
                    cursor.getString(cursor.getColumnIndex(LocalSQLiteHelper.SCHEDULE_TO)),
                    cursor.getString(cursor.getColumnIndex(LocalSQLiteHelper.SCHEDULE_BATCH)),
                    cursor.getString(cursor.getColumnIndex(LocalSQLiteHelper.SCHEDULE_SESSION)),
                    cursor.getString(cursor.getColumnIndex(LocalSQLiteHelper.SCHEDULE_S_TIME_H)),
                    cursor.getString(cursor.getColumnIndex(LocalSQLiteHelper.SCHEDULE_S_TIME_M)),
                    cursor.getString(cursor.getColumnIndex(LocalSQLiteHelper.SCHEDULE_E_TIME_H)),
                    cursor.getString(cursor.getColumnIndex(LocalSQLiteHelper.SCHEDULE_E_TIME_M)),
                    cursor.getString(cursor.getColumnIndex(LocalSQLiteHelper.SCHEDULE_VENUE)),
                    cursor.getString(cursor.getColumnIndex(LocalSQLiteHelper.SCHEDULE_ROOM)),
                    cursor.getString(cursor.getColumnIndex(LocalSQLiteHelper.SCHEDULE_INSTRUCTOR)),
                    cursor.getString(cursor.getColumnIndex(LocalSQLiteHelper.SCHEDULE_ASSESSOR)),
                    cursor.getString(cursor.getColumnIndex(LocalSQLiteHelper.SCHEDULE_TIME_SCHEDULE)),
                    cursor.getString(cursor.getColumnIndex(LocalSQLiteHelper.SCHEDULE_BRANCH_NAME)),
                    cursor.getInt(cursor.getColumnIndex(LocalSQLiteHelper.SCHEDULE_TRAINEE_COUNT))
            );
            scheduleArrayList.add(schedule);
        }
        cursor.close();
        close(db);
        return scheduleArrayList;
    }

    public void clear(){
        SQLiteDatabase db = open();
        db.beginTransaction();

        db.execSQL("DELETE FROM " + LocalSQLiteHelper.TABLE_NEWS);
        db.execSQL("DELETE FROM " + LocalSQLiteHelper.TABLE_IMAGE);
        db.execSQL("DELETE FROM " + LocalSQLiteHelper.TABLE_COURSE);
        db.execSQL("DELETE FROM " + LocalSQLiteHelper.TABLE_SCHEDULE);

        db.setTransactionSuccessful();
        db.endTransaction();
        close(db);
    }

    public void storeJSONData(Context context, JSONObject jsonData){
        JSONDataHandler jsonDataHandler = new JSONDataHandler(jsonData);
        LocalDataSource localDataSource = new LocalDataSource(context);
        for(Post post : jsonDataHandler.getPostArray()){
            localDataSource.store(post);
        }

        for(Course course : jsonDataHandler.getCourseArray()){
            localDataSource.store(course);
        }

        for(Schedule schedule : jsonDataHandler.getScheduleArray()){
            localDataSource.store(schedule);
        }
    }

}