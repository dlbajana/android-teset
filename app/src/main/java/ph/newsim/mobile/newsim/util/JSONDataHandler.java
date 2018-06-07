package ph.newsim.mobile.newsim.util;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import ph.newsim.mobile.newsim.model.Course;
import ph.newsim.mobile.newsim.model.Post;
import ph.newsim.mobile.newsim.model.Schedule;
import ph.newsim.mobile.newsim.model.User;

public class JSONDataHandler {

    private List<Post> mPostArrayList = Collections.emptyList();
    private List<Course> mCourseArrayList = Collections.emptyList();
    private List<Schedule> mScheduleArrayList = Collections.emptyList();

    public JSONDataHandler(JSONObject jsonData) {
        try{
            mPostArrayList = convertToPostArray(jsonData.getJSONArray("posts"));
            mCourseArrayList = convertToCourseArray(jsonData.getJSONArray("courses"));
            mScheduleArrayList = convertToScheduleArray(jsonData.getJSONArray("schedules"));
        }catch(JSONException e){
            e.printStackTrace();
        }
    }

    public static User toUser(JSONObject jsonUser){
        if (jsonUser != null){
                return new User(
                        jsonUser.optInt("id", 0),
                        jsonUser.optString("type", ""),
                        jsonUser.optString("email", ""),
                        "",
                        jsonUser.optString("first_name", ""),
                        jsonUser.optString("middle_name", ""),
                        jsonUser.optString("last_name", ""),
                        jsonUser.optString("extn_name", ""),
                        jsonUser.optString("gender", ""),
                        jsonUser.optString("birthdate", ""),
                        jsonUser.optString("birthplace", ""),
                        jsonUser.optString("mobile_no", ""),
                        jsonUser.optString("telephone_no", ""),
                        jsonUser.optString("address", ""),
                        jsonUser.optString("rank", ""),
                        jsonUser.optString("company_name", ""),
                        jsonUser.optString("company_contact", ""),
                        jsonUser.optString("profile_photo", ""),
                        jsonUser.optString("cover_photo", ""),
                        jsonUser.optString("department", ""),
                        jsonUser.optString("created_at", ""));
        }
        return null;
    }

    public static List<Post> convertToPostArray(JSONArray jsonPostArray){
        List<Post> postArrayList = new ArrayList<>();
        try{
            for (int i = 0; i < jsonPostArray.length(); i++){
                JSONObject jsonPost = jsonPostArray.getJSONObject(i);
                JSONArray jsonArrayImagesURI = jsonPost.getJSONArray("post_images");

                String[] postImagesURI = new String[jsonArrayImagesURI.length()];
                String[] postImagesDescription = new String[jsonArrayImagesURI.length()];

                for(int j = 0; j < jsonArrayImagesURI.length(); j++){
                    JSONObject jsonImage = jsonArrayImagesURI.getJSONObject(j);
                    postImagesURI[j] = jsonImage.getString("uri");
                    postImagesDescription[j] = jsonImage.getString("description");
                }

                Post post = new Post(jsonPost.getInt("id"), jsonPost.getString("branch"), jsonPost.getString("title"), jsonPost.getString("description"),
                        jsonPost.getString("created_at"), postImagesURI, postImagesDescription);

                postArrayList.add(post);
            }
        }catch (JSONException e){
            e.printStackTrace();
            postArrayList = Collections.emptyList();
        }
        return postArrayList;
    }

    public static List<Course> convertToCourseArray(JSONArray jsonCourseArray){
        List<Course> courseArrayList = new ArrayList<>();
        try{
            for(int i = 0; i < jsonCourseArray.length(); i++){
                JSONObject jsonCourse = jsonCourseArray.getJSONObject(i);
                Course course = new Course(jsonCourse.getInt("id"), jsonCourse.getInt("branch"), jsonCourse.getString("code"),
                        jsonCourse.getString("description"), jsonCourse.getString("category"), jsonCourse.getString("course_type"));
                course.setDuration(jsonCourse.getString("duration"));

                courseArrayList.add(course);
            }
        }catch (JSONException e){
            e.printStackTrace();
            courseArrayList = Collections.emptyList();
        }
        return courseArrayList;
    }

    public static List<Schedule> convertToScheduleArray(JSONArray jsonScheduleArray){
        List<Schedule> scheduleArrayList = new ArrayList<>();
        try{
            for(int i = 0; i < jsonScheduleArray.length(); i++){
                JSONObject jsonSchedule = jsonScheduleArray.getJSONObject(i);
                Schedule schedule = new Schedule(jsonSchedule.getInt("id"), jsonSchedule.getString("course"), "description",
                        "duration", jsonSchedule.getString("from_"), jsonSchedule.getString("to_"),
                        jsonSchedule.getString("batch"), jsonSchedule.getString("session"), jsonSchedule.getString("s_time_h"),
                        jsonSchedule.getString("s_time_m"), jsonSchedule.getString("e_time_h"), jsonSchedule.getString("e_time_m"),
                        jsonSchedule.getString("venue"), jsonSchedule.getString("room"), jsonSchedule.getString("ins1"),
                        jsonSchedule.getString("ass1"), jsonSchedule.getString("time_schedule"), jsonSchedule.getString("branch"), 0);

                scheduleArrayList.add(schedule);
            }
        }catch (JSONException e){
            e.printStackTrace();
            scheduleArrayList = Collections.emptyList();
        }
        return scheduleArrayList;
    }

    public List<Post> getPostArray(){
        return mPostArrayList;
    }

    public List<Course> getCourseArray(){
        return mCourseArrayList;
    }

    public List<Schedule> getScheduleArray(){
        return mScheduleArrayList;
    }

}
