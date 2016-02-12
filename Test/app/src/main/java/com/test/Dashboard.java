package com.test;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;

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

public class Dashboard extends AppCompatActivity {

    // ArrayList
    private List<Advertise> advertiseList = new ArrayList<>();
    // RecyclerView
    private RecyclerView recyclerView;
    // Adapter
    private AdvertiseAdapter mAdapter;
    // JSONParser
    private JSONParser jsonParser = new JSONParser();
    // Config
    private Config configuration;
    // isInternetPresent
    private boolean isInternetPresent;
    // Connection Detector
    private ConnectionDetector connectionDetector;
    // DatabaseHandler
    private DatabaseHandler dbHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        findById();

        configuration = new Config();
        connectionDetector = new ConnectionDetector(Dashboard.this);
        dbHandler = new DatabaseHandler(Dashboard.this);


        advertiseList = dbHandler.getAllData();
        mAdapter = new AdvertiseAdapter(advertiseList, Dashboard.this);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.addItemDecoration(new DividerItemDecoration(Dashboard.this, LinearLayoutManager.VERTICAL));
        recyclerView.setAdapter(mAdapter);

        recyclerView.addOnItemTouchListener(new RecyclerTouchListener(getApplicationContext(), recyclerView, new ClickListener() {
            @Override
            public void onClick(View view, int position) {
            }

            @Override
            public void onLongClick(View view, int position) {

            }
        }));


//        new GetData().execute();
    }

    private void findById() {

        // RecyclerView
        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
    }

    private class GetData extends AsyncTask<String, String, String> {
        int MyProgress;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            MyProgress = 0;
        }

        @Override
        protected String doInBackground(String... param) {

            try {

            } catch (Exception e) {
                e.printStackTrace();
            }

            return null;
        }

        @Override
        protected void onPostExecute(final String result) {
            super.onPostExecute(result);

            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                }
            });
        }
    }

    public interface ClickListener {
        void onClick(View view, int position);

        void onLongClick(View view, int position);
    }

    public static class RecyclerTouchListener implements RecyclerView.OnItemTouchListener {

        private GestureDetector gestureDetector;
        private Dashboard.ClickListener clickListener;

        public RecyclerTouchListener(Context context, final RecyclerView recyclerView, final Dashboard.ClickListener clickListener) {
            this.clickListener = clickListener;
            gestureDetector = new GestureDetector(context, new GestureDetector.SimpleOnGestureListener() {
                @Override
                public boolean onSingleTapUp(MotionEvent e) {
                    return true;
                }

                @Override
                public void onLongPress(MotionEvent e) {
                    View child = recyclerView.findChildViewUnder(e.getX(), e.getY());
                    if (child != null && clickListener != null) {
                        clickListener.onLongClick(child, recyclerView.getChildPosition(child));
                    }
                }
            });
        }

        @Override
        public boolean onInterceptTouchEvent(RecyclerView rv, MotionEvent e) {

            View child = rv.findChildViewUnder(e.getX(), e.getY());
            if (child != null && clickListener != null && gestureDetector.onTouchEvent(e)) {
                clickListener.onClick(child, rv.getChildPosition(child));
            }
            return false;
        }

        @Override
        public void onTouchEvent(RecyclerView rv, MotionEvent e) {
        }

        @Override
        public void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept) {

        }
    }
}
