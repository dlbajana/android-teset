package ph.newsim.mobile.newsim.model;

import org.json.JSONObject;

public class Response {

    public static final int FAILED = 0;
    public static final int SUCCESSFUL = 1;
    public static final int ERROR = -1;

    private int mResult;
    private JSONObject mJSONData;
    private Exception mException;

    public Response(){
        mResult = FAILED;
        mJSONData = null;
        mException = null;
    }

    public int getResult() {
        return mResult;
    }

    public void setResult(int result) {
        mResult = result;
    }

    public JSONObject getJSONData() {
        return mJSONData;
    }

    public void setJSONData(JSONObject JSONData) {
        mJSONData = JSONData;
    }

    public Exception getException() {
        return mException;
    }

    public void setException(Exception exception) {
        mException = exception;
    }
}
