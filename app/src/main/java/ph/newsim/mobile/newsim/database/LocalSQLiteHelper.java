package ph.newsim.mobile.newsim.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class LocalSQLiteHelper extends SQLiteOpenHelper {

    private Context mContext;
    public static final int DATABASE_VERSION = 1;
    public static final String TAG = LocalSQLiteHelper.class.getSimpleName();

    // Database Name
    public static final String DATABASE_NAME = "local.db";

    // Table Names
    public static final String TABLE_NEWS = "news";
    public static final String TABLE_IMAGE = "image";
    public static final String TABLE_COURSE = "course";
    public static final String TABLE_SCHEDULE = "schedule";

    // News Fields
    public static final String NEWS_ID = "_id";
    public static final String NEWS_TITLE = "title";
    public static final String NEWS_DESCRIPTION = "description";
    public static final String NEWS_BRANCH = "branch";
    public static final String NEWS_DATE_POSTED = "created_at";

    // Image Fields
    public static final String IMAGE_ID_NEWS = "_id_news";
    public static final String  IMAGE_URI = "image_uri";
    public static final String IMAGE_DESCRIPTION = "description";

    // CourseOld Fields
    public static final String COURSE_ID = "_id";
    public static final String COURSE_BRANCH_ID = "branch_id";
    public static final String COURSE_CODE = "code";
    public static final String COURSE_DESCRIPTION = "description";
    public static final String COURSE_CATEGORY = "category";
    public static final String COURSE_TYPE = "type";
    public static final String COURSE_LEVEL = "level";
    public static final String COURSE_DURATION = "duration";
    public static final String COURSE_STATUS = "status";

    // Schedule Fields
    public static final String SCHEDULE_ID = "_id";
    public static final String SCHEDULE_COURSE_CODE = "course";
    public static final String SCHEDULE_COURSE_DESCRIPTION = "course_description";
    public static final String SCHEDULE_DURATION = "course_duration";
    public static final String SCHEDULE_FROM = "from_";
    public static final String SCHEDULE_TO = "to_";
    public static final String SCHEDULE_BATCH = "batch";
    public static final String SCHEDULE_SESSION = "session";
    public static final String SCHEDULE_S_TIME_H = "s_time_h";
    public static final String SCHEDULE_S_TIME_M = "s_time_m";
    public static final String SCHEDULE_E_TIME_H = "e_time_h";
    public static final String SCHEDULE_E_TIME_M = "e_time_m";
    public static final String SCHEDULE_VENUE = "venue";
    public static final String SCHEDULE_ROOM = "room";
    public static final String SCHEDULE_INSTRUCTOR = "instructor";
    public static final String SCHEDULE_ASSESSOR = "assessor";
    public static final String SCHEDULE_TIME_SCHEDULE = "time_schedule";
    public static final String SCHEDULE_BRANCH_NAME = "branch";
    public static final String SCHEDULE_TRAINEE_COUNT = "trainee_count";

    public LocalSQLiteHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        mContext = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        try{
            initTableNews(db);
            initImage(db);
            initCourse(db);
            initSchedule(db);
        }catch(Exception e){
            Log.i(TAG, "Method onCreate Exception: " + e.getMessage());
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NEWS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_IMAGE);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_COURSE);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_SCHEDULE);
        onCreate(db);
    }

    // News Table Initialization
    public void initTableNews(SQLiteDatabase db) throws Exception{
        String query = "CREATE TABLE " + TABLE_NEWS + "(" +
                NEWS_ID + " INTEGER PRIMARY KEY, " +
                NEWS_TITLE + " VARCHAR(100), " +
                NEWS_DESCRIPTION + " VARCHAR(1000), " +
                NEWS_BRANCH + " VARCHAR(50), " +
                NEWS_DATE_POSTED + " VARCHAR(20)" +
                ");";
        db.execSQL(query);
    }

    // Image Table Initialization
    public void initImage(SQLiteDatabase db) throws Exception{
        String query = "CREATE TABLE " + TABLE_IMAGE + "(" +
                IMAGE_ID_NEWS + " INTEGER, " +
                IMAGE_URI + " VARCHAR(300), " +
                IMAGE_DESCRIPTION + " VARCHAR(1000)" +
                ");";
        db.execSQL(query);
    }

    // CourseOld Table Initialization
    public void initCourse(SQLiteDatabase db) throws Exception{
        String query = "CREATE TABLE " + TABLE_COURSE + "(" +
                COURSE_ID + " INTEGER, " +
                COURSE_BRANCH_ID + " INTEGER, " +
                COURSE_CODE + " VARCHAR(50), " +
                COURSE_DESCRIPTION + " VARCHAR(300), " +
                COURSE_CATEGORY + " VARCHAR(100), " +
                COURSE_TYPE + " VARCHAR(100), " +
                COURSE_LEVEL + " VARCHAR(100), " +
                COURSE_DURATION + " VARCHAR(100), " +
                COURSE_STATUS + " VARCHAR(100)" +
                ");";
        db.execSQL(query);
    }

    // Schedule Table Initialization
    public void initSchedule(SQLiteDatabase db) throws Exception{
        String query = "CREATE TABLE " + TABLE_SCHEDULE + "(" +
                SCHEDULE_ID + " INTEGER PRIMARY KEY, " +
                SCHEDULE_COURSE_CODE + " VARCHAR(50), " +
                SCHEDULE_COURSE_DESCRIPTION + " VARCHAR(300), " +
                SCHEDULE_DURATION + " VARCHAR(50), " +
                SCHEDULE_FROM + " VARCHAR(50), " +
                SCHEDULE_TO + " VARCHAR(50), " +
                SCHEDULE_BATCH + " VARCHAR(50), " +
                SCHEDULE_SESSION + " VARCHAR(50), " +
                SCHEDULE_S_TIME_H + " VARCHAR(50), " +
                SCHEDULE_S_TIME_M + " VARCHAR(50), " +
                SCHEDULE_E_TIME_H + " VARCHAR(50), " +
                SCHEDULE_E_TIME_M + " VARCHAR(50), " +
                SCHEDULE_VENUE + " VARCHAR(50), " +
                SCHEDULE_ROOM + " VARCHAR(50), " +
                SCHEDULE_INSTRUCTOR + " VARCHAR(100), " +
                SCHEDULE_ASSESSOR + " VARCHAR(100), " +
                SCHEDULE_TIME_SCHEDULE + " VARCHAR(100), " +
                SCHEDULE_BRANCH_NAME + " VARCHAR(50), " +
                SCHEDULE_TRAINEE_COUNT + " INTEGER" +
                ");";
        db.execSQL(query);
    }

}
