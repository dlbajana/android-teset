package ph.newsim.mobile.newsim.util;

import android.app.Application;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.util.Base64;
import android.util.Log;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class MyApplication extends Application {

    private static MyApplication sInstance;

    @Override
    public void onCreate() {
        super.onCreate();
        sInstance = this;
        printHashKey();
    }

    public void printHashKey(){
        try{
            PackageInfo info = getPackageManager().getPackageInfo("ph.newsim.mobile.newsim", PackageManager.GET_SIGNATURES);
            for (Signature signature : info.signatures){
                MessageDigest md  = MessageDigest.getInstance("SHA");
                md.update(signature.toByteArray());
                Log.d("KeyHash:", Base64.encodeToString(md.digest(), Base64.DEFAULT));
            }

        }catch(PackageManager.NameNotFoundException e){

        }catch(NoSuchAlgorithmException e){

        }
    }

    public static MyApplication getsInstance() {
        return sInstance;
    }
}
