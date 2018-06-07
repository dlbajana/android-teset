package ph.newsim.mobile.newsim.util;

import android.content.Context;
import android.os.AsyncTask;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;

import ph.newsim.mobile.newsim.model.Response;
import ph.newsim.mobile.newsim.model.User;

public class SRegister {

    public interface RegisterCallback{
        void onSuccessful(JSONObject jsonError, JSONObject jsonUser);
        void onFailed();
        void onError(Exception e);
    }

    public static final String SERVER_ADDRESS = "http://104.238.96.209/~newsimtms/mobile/app/";

    public void attemptRegisterUserInBackground(User user, RegisterCallback registerCallback){
        new RegisterUserAsyncTask(user, registerCallback).execute();

    }

    /**
     *  Nested Inner Class
     */

    public class RegisterUserAsyncTask extends AsyncTask<Void, Void, Response>{

        private User mUser;
        private RegisterCallback mRegisterCallback;

        public RegisterUserAsyncTask(User user, RegisterCallback registerCallback) {
            mUser = user;
            mRegisterCallback = registerCallback;

        }

        @Override
        protected Response doInBackground(Void... params) {
            Response response = new Response();

            try{
                String link = SERVER_ADDRESS + "register";
                String data = URLEncoder.encode("account_type", "UTF-8") + "=" + URLEncoder.encode(mUser.getAccountType(), "UTF-8");
                data += "&" + URLEncoder.encode("email", "UTF-8") + "=" + URLEncoder.encode(mUser.getEmail(), "UTF-8");
                data += "&" + URLEncoder.encode("password", "UTF-8") + "=" + URLEncoder.encode(mUser.getPassword(), "UTF-8");
                data += "&" + URLEncoder.encode("first_name", "UTF-8") + "=" + URLEncoder.encode(mUser.getFirstName(), "UTF-8");
                data += "&" + URLEncoder.encode("middle_name", "UTF-8") + "=" + URLEncoder.encode(mUser.getMiddleName(), "UTF-8");
                data += "&" + URLEncoder.encode("last_name", "UTF-8") + "=" + URLEncoder.encode(mUser.getLastName(), "UTF-8");
                data += "&" + URLEncoder.encode("extn_name", "UTF-8") + "=" + URLEncoder.encode(mUser.getExtnName(), "UTF-8");
                data += "&" + URLEncoder.encode("gender", "UTF-8") + "=" + URLEncoder.encode(mUser.getGender(), "UTF-8");
                data += "&" + URLEncoder.encode("birthdate", "UTF-8") + "=" + URLEncoder.encode(mUser.getBirthDate(), "UTF-8");
                data += "&" + URLEncoder.encode("birthplace", "UTF-8") + "=" + URLEncoder.encode(mUser.getBirthPlace(), "UTF-8");
                data += "&" + URLEncoder.encode("mobile_no", "UTF-8") + "=" + URLEncoder.encode(mUser.getMobileNo(), "UTF-8");
                data += "&" + URLEncoder.encode("telephone_no", "UTF-8") + "=" + URLEncoder.encode(mUser.getTelephoneNo(), "UTF-8");
                data += "&" + URLEncoder.encode("address", "UTF-8") + "=" + URLEncoder.encode(mUser.getAddress(), "UTF-8");
                data += "&" + URLEncoder.encode("rank", "UTF-8") + "=" + URLEncoder.encode(mUser.getRank(), "UTF-8");
                data += "&" + URLEncoder.encode("company_name", "UTF-8") + "=" + URLEncoder.encode(mUser.getCompanyName(), "UTF-8");
                data += "&" + URLEncoder.encode("company_contact", "UTF-8") + "=" + URLEncoder.encode(mUser.getCompanyContact(), "UTF-8");
                data += "&" + URLEncoder.encode("profile_photo_uri", "UTF-8") + "=" + URLEncoder.encode(mUser.getProfilePhotoURI(), "UTF-8");
                data += "&" + URLEncoder.encode("cover_photo_uri", "UTF-8") + "=" + URLEncoder.encode(mUser.getCoverPhotoURI(), "UTF-8");
                data += "&" + URLEncoder.encode("department", "UTF-8") + "=" + URLEncoder.encode(mUser.getDepartment(), "UTF-8");

                URL url = new URL(link);
                URLConnection conn = url.openConnection();
                conn.setDoOutput(true);

                OutputStreamWriter wr = new OutputStreamWriter(conn.getOutputStream());
                wr.write(data);
                wr.flush();

                BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                StringBuilder sb = new StringBuilder();

                String line;

                // Read Server Response
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

            }catch (Exception e){
                response.setException(e);

            }
            return response;
        }

        @Override
        protected void onPostExecute(Response response) {
            super.onPostExecute(response);
            switch (response.getResult()){
                case Response.SUCCESSFUL:
                    mRegisterCallback.onSuccessful(response.getJSONData().optJSONObject("error"), response.getJSONData().optJSONObject("user"));
                    break;
                case Response.FAILED:
                    mRegisterCallback.onFailed();
                    break;
                case Response.ERROR:
                    mRegisterCallback.onError(response.getException());
                    break;
            }
        }
    }
}
