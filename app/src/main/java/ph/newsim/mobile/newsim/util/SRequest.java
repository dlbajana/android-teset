package ph.newsim.mobile.newsim.util;

import android.app.DownloadManager;
import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;

import ph.newsim.mobile.newsim.model.Response;
import ph.newsim.mobile.newsim.model.User;

public class SRequest {

    public interface RequestCallback {
        void onSuccessful(JSONObject jsonData);
        void onFailed();
        void onError(Exception e);
    }

    public static final String TAG = SRequest.class.getSimpleName();
    public static final String SERVER_ADDRESS = "http://104.238.96.209/~newsimtms/mobile/app/";

    public void pullPost(int lastPostId, String direction, RequestCallback requestCallback){
        new PullPostAsyncTask(lastPostId, direction, requestCallback).execute();
    }

    public void pullCourse(int branchId, int skipCount, RequestCallback requestCallback){
        new PullCourseAsyncTask(branchId, skipCount, requestCallback).execute();
    }

    public void pullMoreSchedule(int branchId, int skipCount, RequestCallback requestCallback){
        new FetchMoreScheduleAsyncTask(branchId, skipCount, requestCallback).execute();
    }

    public void pullScheduleByQuery(String query, int branchId, RequestCallback requestCallback){
        new FindScheduleAsyncTask(query, branchId, requestCallback).execute();
    }

    public void pullMoreScheduleByQuery(String query, int branchId, int skipCount, RequestCallback requestCallback){
        new FindMoreScheduleAsyncTask(query, branchId, skipCount, requestCallback).execute();
    }

    public void updateProfileInfo(User user, String profilePhotoPath, String coverPhotoPath, RequestCallback requestCallback){
        new ProfileUpdateAsyncTask(user, profilePhotoPath, coverPhotoPath, requestCallback).execute();
    }

    private class PullPostAsyncTask extends AsyncTask<Void, Void, Response> {

        private RequestCallback mRequestCallback;
        private int mLastPostID;
        private String mDirection;

        public PullPostAsyncTask(int lastPostId, String direction, RequestCallback requestCallback) {
            mLastPostID = lastPostId;
            mDirection = direction;
            mRequestCallback = requestCallback;
        }

        @Override
        protected Response doInBackground(Void... params) {
            Response response = new Response();

            try {
                String link = SERVER_ADDRESS + "pullPost";
                String data = URLEncoder.encode("id", "UTF-8") + "=" + URLEncoder.encode(mLastPostID + "", "UTF-8");
                data += "&" + URLEncoder.encode("direction", "UTF-8") + "=" + URLEncoder.encode(mDirection, "UTF-8");

                URL url = new URL(link);
                URLConnection conn = url.openConnection();
                conn.setDoOutput(true);

                OutputStreamWriter wr = new OutputStreamWriter(conn.getOutputStream());
                wr.write(data);
                wr.flush();

                BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                StringBuilder sb = new StringBuilder();

                String line;

                while ((line = reader.readLine()) != null) {
                    sb.append(line);
                    break;
                }

                JSONObject jsonResponse = new JSONObject(sb.toString());
                JSONArray jsonNews = jsonResponse.optJSONArray("data");

                if (jsonNews != null) {
                    if (jsonNews.length() > 1) {
                        response.setJSONData(jsonResponse);
                        response.setResult(Response.SUCCESSFUL);

                    } else {
                        response.setResult(Response.FAILED);

                    }

                } else {
                    response.setResult(Response.FAILED);

                }

            } catch (Exception e) {
                response.setException(e);

            }

            return response;
        }

        @Override
        protected void onPostExecute(Response response) {
            super.onPostExecute(response);
            switch (response.getResult()){
                case Response.SUCCESSFUL:
                    mRequestCallback.onSuccessful(response.getJSONData());
                    break;

                case Response.FAILED:
                    mRequestCallback.onFailed();
                    break;

                case Response.ERROR:
                    mRequestCallback.onError(response.getException());
                    break;
            }
        }
    }

    private class PullCourseAsyncTask extends AsyncTask<Void, Void, Response>{

        private RequestCallback mRequestCallback;
        private int mBranchId;
        private int mSkipCount;

        public PullCourseAsyncTask(int branchId, int skipCount, RequestCallback requestCallback) {
            mBranchId = branchId;
            mSkipCount = skipCount;
            mRequestCallback = requestCallback;
        }

        @Override
        protected Response doInBackground(Void... params) {
            Response response = new Response();

            try {
                String link = SERVER_ADDRESS + "pullCourse";
                String data = URLEncoder.encode("branch_id", "UTF-8") + "=" + URLEncoder.encode(mBranchId + "", "UTF-8");
                data += "&" + URLEncoder.encode("skip_count", "UTF-8") + "=" + URLEncoder.encode(mSkipCount + "", "UTF-8");

                URL url = new URL(link);
                URLConnection conn = url.openConnection();
                conn.setDoOutput(true);

                OutputStreamWriter wr = new OutputStreamWriter(conn.getOutputStream());
                wr.write(data);
                wr.flush();

                BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                StringBuilder sb = new StringBuilder();

                String line;

                while ((line = reader.readLine()) != null) {
                    sb.append(line);
                    break;
                }

                JSONObject jsonResponse = new JSONObject(sb.toString());
                JSONArray jsonCourse = jsonResponse.optJSONArray("data");

                if (jsonCourse != null) {
                    if (jsonCourse.length() > 1) {
                        response.setJSONData(jsonResponse);
                        response.setResult(Response.SUCCESSFUL);
                    } else {
                        response.setResult(Response.FAILED);
                    }

                } else {
                    response.setResult(Response.FAILED);

                }

            } catch (Exception e) {
                response.setException(e);
            }
            return response;
        }

        @Override
        protected void onPostExecute(Response response) {
            super.onPostExecute(response);
            switch (response.getResult()){
                case Response.SUCCESSFUL:
                    mRequestCallback.onSuccessful(response.getJSONData());
                    break;

                case Response.FAILED:
                    mRequestCallback.onFailed();
                    break;

                case Response.ERROR:
                    mRequestCallback.onError(response.getException());
                    break;
            }
        }
    }

    private class FetchMoreScheduleAsyncTask extends AsyncTask<Void, Void, Response>{

        private int mBranchId;
        private int mSkipCount;
        private RequestCallback mRequestCallback;

        public FetchMoreScheduleAsyncTask(int branchId, int skipCount, RequestCallback requestCallback) {
            mRequestCallback = requestCallback;
            mSkipCount = skipCount;
            mBranchId = branchId;
        }

        @Override
        protected Response doInBackground(Void... params) {
            Response response = new Response();
            try {
                String link = SERVER_ADDRESS + "pullSchedule";
                String data = URLEncoder.encode("branch_id", "UTF-8") + "=" + URLEncoder.encode(mBranchId + "", "UTF-8");
                data += "&" + URLEncoder.encode("skip_count", "UTF-8") + "=" + URLEncoder.encode(mSkipCount + "", "UTF-8");

                URL url = new URL(link);
                URLConnection conn = url.openConnection();
                conn.setDoOutput(true);

                OutputStreamWriter wr = new OutputStreamWriter(conn.getOutputStream());
                wr.write(data);
                wr.flush();

                BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                StringBuilder sb = new StringBuilder();

                String line;

                while ((line = reader.readLine()) != null) {
                    sb.append(line);
                    break;
                }

                JSONObject jsonResponse = new JSONObject(sb.toString());
                JSONArray jsonSchedule = jsonResponse.optJSONArray("schedule");

                if (jsonSchedule != null) {
                    if (jsonSchedule.length() > 1) {
                        response.setJSONData(jsonResponse);
                        response.setResult(Response.SUCCESSFUL);
                    } else {
                        response.setResult(Response.FAILED);
                    }

                } else {
                    response.setResult(Response.FAILED);

                }

            } catch (Exception e) {
                response.setException(e);
            }
            return response;
        }

        @Override
        protected void onPostExecute(Response response) {
            super.onPostExecute(response);
            switch (response.getResult()){
                case Response.SUCCESSFUL:
                    mRequestCallback.onSuccessful(response.getJSONData());
                    break;

                case Response.FAILED:
                    mRequestCallback.onFailed();
                    break;

                case Response.ERROR:
                    mRequestCallback.onError(response.getException());
                    break;
            }
        }
    }

    private class FindScheduleAsyncTask extends AsyncTask<Void, Void, Response>{

        private RequestCallback mRequestCallback;
        private String mQuery;
        private int mBranchId;

        public FindScheduleAsyncTask(String query, int branchId, RequestCallback requestCallback) {
            mRequestCallback = requestCallback;
            mQuery = query;
            mBranchId = branchId;
        }

        @Override
        protected Response doInBackground(Void... params) {
            Response response = new Response();
            try{
                String link = SERVER_ADDRESS + "findschedule.php";
                String data = URLEncoder.encode("query", "UTF-8") + "=" + URLEncoder.encode(mQuery + "", "UTF-8");
                data += "&" + URLEncoder.encode("branch_id", "UTF-8") + "=" + URLEncoder.encode(mBranchId + "", "UTF-8");

                URL url = new URL(link);
                URLConnection conn = url.openConnection();
                conn.setDoOutput(true);

                OutputStreamWriter wr = new OutputStreamWriter(conn.getOutputStream());
                wr.write(data);
                wr.flush();

                BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                StringBuilder sb = new StringBuilder();

                String line;

                while ((line = reader.readLine()) != null) {
                    sb.append(line);
                    break;
                }

                JSONObject jsonResponse = new JSONObject(sb.toString());
                JSONArray jsonSchedule = jsonResponse.optJSONArray("schedule");

                if (jsonSchedule != null) {
                    if (jsonSchedule.length() > 1) {
                        response.setJSONData(jsonResponse);
                        response.setResult(Response.SUCCESSFUL);
                    } else {
                        response.setResult(Response.FAILED);
                    }

                } else {
                    response.setResult(Response.FAILED);

                }

            }catch (Exception e){

            }
            return response;
        }

        @Override
        protected void onPostExecute(Response response) {
            super.onPostExecute(response);
            switch (response.getResult()){
                case Response.SUCCESSFUL:
                    mRequestCallback.onSuccessful(response.getJSONData());
                    break;

                case Response.FAILED:
                    mRequestCallback.onFailed();
                    break;

                case Response.ERROR:
                    mRequestCallback.onError(response.getException());
                    break;
            }
        }
    }

    private class FindMoreScheduleAsyncTask extends AsyncTask<Void, Void, Response>{

        private String mQuery;
        private int mBranchId;
        private int mSkipCount;
        private RequestCallback mRequestCallback;

        public FindMoreScheduleAsyncTask(String query, int branchId, int skipCount, RequestCallback requestCallback) {
            mQuery = query;
            mBranchId = branchId;
            mSkipCount = skipCount;
            mRequestCallback = requestCallback;
        }

        @Override
        protected Response doInBackground(Void... params) {
            Response response = new Response();
            try{
                String link = SERVER_ADDRESS + "findmoreschedule.php";
                String data = URLEncoder.encode("query", "UTF-8") + "=" + URLEncoder.encode(mQuery + "", "UTF-8");
                data += "&" + URLEncoder.encode("branch_id", "UTF-8") + "=" + URLEncoder.encode(mBranchId + "", "UTF-8");
                data += "&" + URLEncoder.encode("skip_count", "UTF-8") + "=" + URLEncoder.encode(mSkipCount + "", "UTF-8");

                URL url = new URL(link);
                URLConnection conn = url.openConnection();
                conn.setDoOutput(true);

                OutputStreamWriter wr = new OutputStreamWriter(conn.getOutputStream());
                wr.write(data);
                wr.flush();

                BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                StringBuilder sb = new StringBuilder();

                String line;

                while ((line = reader.readLine()) != null) {
                    sb.append(line);
                    break;
                }

                JSONObject jsonResponse = new JSONObject(sb.toString());
                JSONArray jsonSchedule = jsonResponse.optJSONArray("schedule");

                if (jsonSchedule != null) {
                    if (jsonSchedule.length() > 1) {
                        response.setJSONData(jsonResponse);
                        response.setResult(Response.SUCCESSFUL);
                    } else {
                        response.setResult(Response.FAILED);
                    }

                } else {
                    response.setResult(Response.FAILED);

                }

            }catch (Exception e){

            }
            return response;
        }

        @Override
        protected void onPostExecute(Response response) {
            super.onPostExecute(response);
            switch (response.getResult()){
                case Response.SUCCESSFUL:
                    mRequestCallback.onSuccessful(response.getJSONData());
                    break;

                case Response.FAILED:
                    mRequestCallback.onFailed();
                    break;

                case Response.ERROR:
                    mRequestCallback.onError(response.getException());
                    break;
            }
        }
    }

    private class ProfileUpdateAsyncTask extends AsyncTask<Void, Void, Response> {

        private RequestCallback mRequestCallback;
        private User mUser;
        private String mProfilePhotoPath, mCoverPhotoPath;

        public ProfileUpdateAsyncTask(User user, String profilePhotoPath, String coverPhotoPath, RequestCallback requestCallback) {
            mUser = user;
            mProfilePhotoPath = profilePhotoPath;
            mCoverPhotoPath = coverPhotoPath;
            mRequestCallback = requestCallback;
        }

        @Override
        protected Response doInBackground(Void... params)
        {
            Response response = new Response();

            if (mProfilePhotoPath != null) {
                FileUpload fileUpload = new FileUpload("http://104.238.96.209/~newsimtms/mobile/app/uploadImage", new FileUpload.UploadCallback() {
                    @Override
                    public void onSuccessful(String outputFileName) {
                        mUser.setProfilePhotoURI("http://104.238.96.209/~newsimtms/mobile/uploads/" + outputFileName);
                        Log.i(TAG, "Upload Successful");
                    }

                    @Override
                    public void onFailed(String message) {
                        Log.i(TAG, "Upload Failed");
                    }

                    @Override
                    public void onError(Exception e) {
                        Log.i(TAG, "Upload Error: " + e.getMessage());
                    }
                });
                fileUpload.uploadImageOnSameThread(mProfilePhotoPath, "profile_" + mUser.getId() + "_" + System.currentTimeMillis() + ".jpg");
            }else{
                Log.i(TAG, "Upload Successful");
            }

            if (mCoverPhotoPath != null) {
                FileUpload fileUpload = new FileUpload("http://104.238.96.209/~newsimtms/mobile/app/uploadImage", new FileUpload.UploadCallback() {
                    @Override
                    public void onSuccessful(String outputFileName) {
                        mUser.setCoverPhotoURI("http://104.238.96.209/~newsimtms/mobile/uploads/" + outputFileName);
                    }

                    @Override
                    public void onFailed(String message) {
                        Log.i("SRequest", "Failed: " + message);
                    }

                    @Override
                    public void onError(Exception e) {
                        Log.i("SRequest", "Failed: " + e.getMessage());
                    }
                });
                fileUpload.uploadImageOnSameThread(mCoverPhotoPath, "cover_" + mUser.getId() + "_" + System.currentTimeMillis() + ".jpg");
            }

            try{
                String link = SERVER_ADDRESS + "updateProfile";
                String data = URLEncoder.encode("id", "UTF-8") + "=" + URLEncoder.encode(mUser.getId() + "", "UTF-8");
                data += "&" + URLEncoder.encode("first_name", "UTF-8") + "=" + URLEncoder.encode(mUser.getFirstName() + "", "UTF-8");
                data += "&" + URLEncoder.encode("middle_name", "UTF-8") + "=" + URLEncoder.encode(mUser.getMiddleName() + "", "UTF-8");
                data += "&" + URLEncoder.encode("last_name", "UTF-8") + "=" + URLEncoder.encode(mUser.getLastName() + "", "UTF-8");
                data += "&" + URLEncoder.encode("extn_name", "UTF-8") + "=" + URLEncoder.encode(mUser.getExtnName() + "", "UTF-8");
                data += "&" + URLEncoder.encode("gender", "UTF-8") + "=" + URLEncoder.encode(mUser.getGender() + "", "UTF-8");
                data += "&" + URLEncoder.encode("birthdate", "UTF-8") + "=" + URLEncoder.encode(mUser.getBirthDate() + "", "UTF-8");
                data += "&" + URLEncoder.encode("birthplace", "UTF-8") + "=" + URLEncoder.encode(mUser.getBirthPlace() + "", "UTF-8");
                data += "&" + URLEncoder.encode("mobile_no", "UTF-8") + "=" + URLEncoder.encode(mUser.getMobileNo() + "", "UTF-8");
                data += "&" + URLEncoder.encode("telephone_no", "UTF-8") + "=" + URLEncoder.encode(mUser.getTelephoneNo() + "", "UTF-8");
                data += "&" + URLEncoder.encode("address", "UTF-8") + "=" + URLEncoder.encode(mUser.getAddress() + "", "UTF-8");
                data += "&" + URLEncoder.encode("rank", "UTF-8") + "=" + URLEncoder.encode(mUser.getRank() + "", "UTF-8");
                data += "&" + URLEncoder.encode("company_name", "UTF-8") + "=" + URLEncoder.encode(mUser.getCompanyName() + "", "UTF-8");
                data += "&" + URLEncoder.encode("company_contact", "UTF-8") + "=" + URLEncoder.encode(mUser.getCompanyContact() + "", "UTF-8");
                data += "&" + URLEncoder.encode("profile_photo", "UTF-8") + "=" + URLEncoder.encode(mUser.getProfilePhotoURI() + "", "UTF-8");
                data += "&" + URLEncoder.encode("cover_photo", "UTF-8") + "=" + URLEncoder.encode(mUser.getCoverPhotoURI() + "", "UTF-8");
                data += "&" + URLEncoder.encode("department", "UTF-8") + "=" + URLEncoder.encode(mUser.getDepartment() + "", "UTF-8");

                URL url = new URL(link);
                URLConnection conn = url.openConnection();
                conn.setDoOutput(true);

                OutputStreamWriter wr = new OutputStreamWriter(conn.getOutputStream());
                wr.write(data);
                wr.flush();

                BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                StringBuilder sb = new StringBuilder();

                String line;

                while ((line = reader.readLine()) != null) {
                    sb.append(line);
                    break;
                }

                try{
                    JSONObject jsonResponse = new JSONObject(sb.toString());
                    JSONObject jsonError = jsonResponse.optJSONObject("error");
                    JSONObject jsonUser = jsonResponse.optJSONObject("user");

                    if ((jsonError == null) && (jsonUser != null)){
                        response.setJSONData(jsonResponse);
                        response.setResult(Response.SUCCESSFUL);
                    }else{
                        response.setResult(Response.FAILED);
                    }
                }catch (Exception e){
                    response.setException(e);
                }
            }catch(Exception e) {
                response.setException(e);
            }
            return response;
        }

        @Override
        protected void onPostExecute(Response response) {
            super.onPostExecute(response);
            switch (response.getResult()){
                case Response.SUCCESSFUL:
                    mRequestCallback.onSuccessful(response.getJSONData());
                    break;
                case Response.FAILED:
                    mRequestCallback.onFailed();
                    break;
                case Response.ERROR:
                    mRequestCallback.onError(response.getException());
                    break;
            }
        }

    }

}
