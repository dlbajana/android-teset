package ph.newsim.mobile.newsim.api;

import android.content.Context;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import ph.newsim.mobile.newsim.database.LocalDataSource;
import ph.newsim.mobile.newsim.model.User;
import ph.newsim.mobile.newsim.util.AppController;
import ph.newsim.mobile.newsim.util.SRequest;

public class Newsim {

    public static final String BASE_URL = "http://104.238.96.209/~newsimtms/mobile/app/";
    public static final String AUTH_URL = BASE_URL + "auth";
    public static final String REGISTRATION_URL = BASE_URL + "register";

    public void auth(final User user, final RequestCallback requestCallback){
        StringRequest stringRequest = new StringRequest(Request.Method.POST, Newsim.AUTH_URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String  response) {
                        try {
                            JSONObject objectResponse = new JSONObject(response);
                            if(objectResponse.getString("response").equals("SUCCESS")){
                                requestCallback.onSuccessful(objectResponse);
                            }else{
                                requestCallback.onFailed("FAILED");
                            }
                        } catch (JSONException e) {
                            requestCallback.onError(e);
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        requestCallback.onError(error);
                    }
                }){

            @Override
            protected Map<String,String> getParams(){
                Map<String,String> params = new HashMap<>();
                params.put("email", user.getEmail());
                params.put("password", user.getPassword());
                params.put("account_type", user.getAccountType());
                if(user.getAccountType().equals("FACEBOOK")){
                    params.put("first_name", user.getFirstName());
                    params.put("last_name", user.getMiddleName());
                    params.put("birthdate", user.getLastName());
                    params.put("profile_photo", user.getProfilePhotoURI());
                    params.put("cover_photo", user.getCoverPhotoURI());
                }
                return params;

            }

        };
        AppController.getInstance().addToRequestQueue(stringRequest);

    }

    public void register(final User user, final RequestCallback requestCallback){

        StringRequest stringRequest = new StringRequest(Request.Method.POST, Newsim.REGISTRATION_URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String  response) {
                        try {
                            JSONObject objectResponse = new JSONObject(response);
                            if(objectResponse.getString("response").equals("SUCCESS")){
                                requestCallback.onSuccessful(objectResponse);
                            }else if(objectResponse.getString("response").equals("DUPLICATE_ENTRY")){
                                requestCallback.onFailed(objectResponse.getString("response"));
                            }else{
                                requestCallback.onFailed("FAILED");
                            }
                        } catch (JSONException e) {
                            requestCallback.onError(e);
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        requestCallback.onError(error);
                    }
                }){

            @Override
            protected Map<String,String> getParams(){
                Map<String,String> params = new HashMap<>();
                params.put("email", user.getEmail());
                params.put("password", user.getPassword());
                params.put("account_type", user.getAccountType());
                params.put("first_name", user.getFirstName());
                params.put("middle_name", user.getMiddleName());
                params.put("last_name", user.getLastName());
                params.put("extn_name", user.getExtnName());
                params.put("gender", user.getAccountType());
                return params;

            }

        };
        AppController.getInstance().addToRequestQueue(stringRequest);

    }

    public interface RequestCallback {
        void onSuccessful(JSONObject response);
        void onFailed(String message);
        void onError(Exception e);
    }
}
