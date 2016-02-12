package connection;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;

import java.net.HttpURLConnection;
import java.net.URL;

public class ConnectionDetector {

    Context context;

    public ConnectionDetector(Context context) {
        this.context = context;
    }

    public boolean isConnectingToInternet() {

        boolean haveConnectedWifi = false;
        boolean haveConnectedMobile = false;

        ConnectivityManager cm = (ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo[] netInfo = cm.getAllNetworkInfo();
        for (NetworkInfo ni : netInfo) {
            if (ni.getTypeName().equalsIgnoreCase("WIFI"))
                if (ni.isConnected()) {
                    haveConnectedWifi = true;
                    return haveConnectedWifi;
                }
            if (ni.getTypeName().equalsIgnoreCase("MOBILE"))
                if (ni.isConnected()) {
                    haveConnectedMobile = true;
                    return haveConnectedMobile;
                }
        }

        /*if (haveConnectedWifi || haveConnectedMobile) {
            try {
                URL url = new URL("http://www.google.com");
                HttpURLConnection urlc = (HttpURLConnection) url
                        .openConnection();
                // urlc.setRequestProperty("User-Agent",
                // "Android Application:"+Z.APP_VERSION);
                urlc.setRequestProperty("Connection", "close");
                urlc.setConnectTimeout(1000 * 5); // mTimeout is in seconds
                urlc.connect();
                Log.e("Code : ", "" + urlc.getResponseCode());
                if (urlc.getResponseCode() == 200 || urlc.getResponseCode() == 302) {
                    return true;
                } else {
                    return false;
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            return false;
        } else {
            return false;
        }*/

        return false;
    }
}