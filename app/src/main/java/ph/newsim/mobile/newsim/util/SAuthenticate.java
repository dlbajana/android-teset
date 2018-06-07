package ph.newsim.mobile.newsim.util;

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

public class SAuthenticate{

    public interface LoginCallback{
        void onSuccessful(JSONObject jsonUser, JSONObject jsonData);
        void onFailed();
        void onError(Exception e);
    }

    public static final String SERVER_ADDRESS = "http://192.168.254.27/mobile/";

    public void attemptLoginInBackground(User user, LoginCallback loginCallback){
        new ServerLoginAsyncTask(user, loginCallback).execute();
    }

    private class ServerLoginAsyncTask extends AsyncTask<Void, Void, Response>{

        private User mUser;
        private LoginCallback mLoginCallback;

        public ServerLoginAsyncTask(User user, LoginCallback logincallback) {
            mUser = user;
            mLoginCallback = logincallback;
        }

        @Override
        protected Response doInBackground(Void... params) {
            Response response = new Response();

            try {
                String link = "auth";
                String data = URLEncoder.encode("email", "UTF-8") + "=" + URLEncoder.encode(mUser.getEmail(), "UTF-8");
                data += "&" + URLEncoder.encode("password", "UTF-8") + "=" + URLEncoder.encode(mUser.getPassword(), "UTF-8");
                data += "&" + URLEncoder.encode("account_type", "UTF-8") + "=" + URLEncoder.encode(mUser.getAccountType(), "UTF-8");

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

                try {
                    JSONObject jsonResponse = new JSONObject(sb.toString());
                    JSONObject jsonUser = jsonResponse.optJSONObject("user");

                    if (jsonUser != null){
                        response.setJSONData(jsonResponse);
                        response.setResult(Response.SUCCESSFUL);
                    } else {
                        response.setResult(Response.FAILED);
                    }
                } catch (Exception e) {
                    response.setException(e);
                }
            }catch(Exception e){
                response.setException(e);
            }

            return response;
        }

        @Override
        protected void onPostExecute(Response response) {
            super.onPostExecute(response);
            switch (response.getResult()){
                case Response.SUCCESSFUL:
                    mLoginCallback.onSuccessful(response.getJSONData().optJSONObject("user"), response.getJSONData().optJSONObject("data"));
                    break;

                case Response.FAILED:
                    mLoginCallback.onFailed();
                    break;

                case Response.ERROR:
                    mLoginCallback.onError(response.getException());
                    break;
            }
        }

    }

}
