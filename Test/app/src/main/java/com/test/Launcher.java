package com.test;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Window;

import org.apache.http.NameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import config.Config;
import config.Constant;
import connection.ConnectionDetector;
import connection.JSONParser;
import database.DatabaseHandler;

public class Launcher extends AppCompatActivity {

    // Database Handler
    private DatabaseHandler dbHandler;
    // JSONParser
    private JSONParser jsonParser = new JSONParser();
    // Config
    private Config configuration;
    // isInternetPresent
    private boolean isInternetPresent;
    // Connection Detector
    private ConnectionDetector connectionDetector;
    // ArrayList
    private List<Advertise> advertiseList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_launcher);

        configuration = new Config();
        connectionDetector = new ConnectionDetector(Launcher.this);
        dbHandler = new DatabaseHandler(Launcher.this);

        new LoadingTask().execute();
    }

    private class LoadingTask extends AsyncTask<String, String, String> {
        int MyProgress;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            MyProgress = 0;
        }

        String id = "", title = "", uri = "";

        @Override
        protected String doInBackground(String... param) {

            try {
                // 2 second pause.
                while (MyProgress < 50) {
                    MyProgress++;
                    publishProgress("" + MyProgress);
                    SystemClock.sleep(50);
                }

                if (!dbHandler.isEmpty()) {
                    isInternetPresent = connectionDetector.isConnectingToInternet();
                    if (isInternetPresent) {
                        String url = configuration.BASE_URL;

                        Log.e("URL: ", url);
                        List<NameValuePair> params = new ArrayList<NameValuePair>();
                        JSONObject jsonObject = jsonParser.makeHttpRequestGetObject(url, "GET", params);
                        try {
                            JSONArray jsonArray = jsonObject.getJSONArray("data");
                            for (int i = 0; i < jsonArray.length(); i++) {
                                JSONObject jsonObjectData = jsonArray.getJSONObject(i);

                                if (jsonObjectData.has(Constant.KEY_ID)) {
                                    id = jsonObjectData.getString(Constant.KEY_ID);
                                }
                                if (jsonObjectData.has(Constant.KEY_TITLE)) {
                                    title = jsonObjectData.getString(Constant.KEY_TITLE);
                                }
                                if (jsonObjectData.has(Constant.KEY_URI)) {
                                    uri = jsonObjectData.getString(Constant.KEY_URI);
                                }

                                if (title.equals("null")) {
                                    title = "";
                                }

                                Advertise advertise = new Advertise(id, title, uri);
                                //advertiseList.add(advertise);
                                dbHandler.addData(advertise);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(final String result) {
            super.onPostExecute(result);
            Intent registerIntent = new Intent(Launcher.this, Dashboard.class);
            startActivity(registerIntent);
            finish();
        }
    }
}
