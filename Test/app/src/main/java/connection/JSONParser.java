package connection;

import android.util.Log;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.Iterator;
import java.util.List;

import config.Constant;

/**
 * Created by Snsepro50 on 4/1/2015.
 */
public class JSONParser {
    static InputStream is = null;
    static JSONObject jObj = null;
    static JSONArray jArray = null;
    static String json = "";
    static String jsonArray = "";
    public static int httpCode;

    session s = new session();

    // constructor
    public JSONParser() {
    }

    // function get json from url
    public JSONObject makeHttpRequestGetObject(String url, String method,
                                               List<NameValuePair> params) {
        String URL = url;
        URL = URL.replace(" ", "%20");
        Log.e("URL", URL);
        // Making HTTP request
        try {
            // check for request method
            if (method.equals("GET")) {
                HttpParams httpParameters = new BasicHttpParams();
                HttpConnectionParams
                        .setConnectionTimeout(httpParameters, 10000);
                HttpConnectionParams.setSoTimeout(httpParameters, 15000);
                // request method is GET
                DefaultHttpClient httpClient = new DefaultHttpClient(
                        httpParameters);
                /*
                 * String paramString = URLEncodedUtils.format(params, "utf-8");
				 * URLurl += "?" + paramString;
				 */
                HttpGet httpGet = new HttpGet(/* url */URL);
                /*if (SessionManager.getAccessToken() != null) {
                    httpGet.addHeader(Constant.TAG_AUTH_KEY,
                            SessionManager.getAccessToken());
                    Log.e("Auth Key : ", SessionManager.getAccessToken());
                }*/
                HttpResponse httpResponse = httpClient.execute(httpGet);
                httpCode = httpResponse.getStatusLine().getStatusCode();
                HttpEntity httpEntity = httpResponse.getEntity();
                if (httpEntity != null) {
                    is = httpEntity.getContent();
                }
            }
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            Log.e("in GET method", e.toString());
            e.printStackTrace();
        }
        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(
                    is, "iso-8859-1"), 8);
            // BufferedReader reader = new BufferedReader(new InputStreamReader(
            // is, "utf-8"), 8);
            StringBuilder sb = new StringBuilder();
            String line = null;
            while ((line = reader.readLine()) != null) {
                sb.append(line + "\n");
            }
            is.close();
            json = sb.toString();
            s.set_internet_weak("CONNECTED");
            Log.e("json in json Object", json.toString());
        } catch (Exception e) {
            s.set_internet_weak("TIMEOUT");
            // Log.e("Buffer Error", "Error converting result ->" +
            // e.toString());
            // Log.e("NO", s.get_internet_responce());
        }
        // try parse the string to a JSON object
        try {
            jObj = new JSONObject(json);
            Log.e("json Object", "" + jObj);
        } catch (JSONException e) {
            // Log.e("JSON Parser", "Error parsing data " + e.toString());
        }
        // return JSON Object
        return jObj;
    }
}
