package com.test;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
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
import android.view.Window;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.squareup.picasso.Picasso;

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
        recyclerView.setAdapter(mAdapter);

        recyclerView.addOnItemTouchListener(
                new RecyclerItemClickListener(Dashboard.this, new RecyclerItemClickListener.OnItemClickListener() {
                    @Override
                    public void onItemClick(View view, int position) {
                        // TODO Handle item click
                        fullScreenDialog(advertiseList.get(position));
                    }
                })
        );
    }

    // Dialog.
    Dialog fullScreen;
    // ImageView
    private ImageView iv_full_screen;

    private void fullScreenDialog(Advertise advertise) {

        fullScreen.show();

        int screenSize = getResources().getConfiguration().screenLayout &
                Configuration.SCREENLAYOUT_SIZE_MASK;

        Log.e("ScreenSize: ", "" + screenSize);

        switch (screenSize) {
            case Configuration.SCREENLAYOUT_SIZE_LARGE: {
                Picasso.with(this).load(advertise.getUri()).into(iv_full_screen);
            }
            break;
            case Configuration.SCREENLAYOUT_SIZE_NORMAL: {
                Picasso.with(this).load(advertise.getUri()).into(iv_full_screen);
            }
            break;
            case Configuration.SCREENLAYOUT_SIZE_SMALL: {
                Picasso.with(this).load(advertise.getUri()).into(iv_full_screen);
            }
            break;
            default:
                break;
        }

        Window window = fullScreen.getWindow();
        window.setLayout(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
    }

    private void findById() {

        // RecyclerView
        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);

        // Dialog
        fullScreen = new Dialog(Dashboard.this);
        fullScreen.requestWindowFeature(Window.FEATURE_NO_TITLE);
        fullScreen.setContentView(R.layout.full_screen_view);
        fullScreen.setCanceledOnTouchOutside(true);

        iv_full_screen = (ImageView) fullScreen.findViewById(R.id.iv_full_screen);
    }

    public static class RecyclerItemClickListener implements RecyclerView.OnItemTouchListener {
        private OnItemClickListener mListener;

        public interface OnItemClickListener {
            public void onItemClick(View view, int position);
        }

        GestureDetector mGestureDetector;

        public RecyclerItemClickListener(Context context, OnItemClickListener listener) {
            mListener = listener;
            mGestureDetector = new GestureDetector(context, new GestureDetector.SimpleOnGestureListener() {
                @Override
                public boolean onSingleTapUp(MotionEvent e) {
                    return true;
                }
            });
        }

        @Override
        public boolean onInterceptTouchEvent(RecyclerView view, MotionEvent e) {
            View childView = view.findChildViewUnder(e.getX(), e.getY());
            if (childView != null && mListener != null && mGestureDetector.onTouchEvent(e)) {
                mListener.onItemClick(childView, view.getChildAdapterPosition(childView));
            }
            return false;
        }

        @Override
        public void onTouchEvent(RecyclerView view, MotionEvent motionEvent) {
        }

        @Override
        public void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept) {

        }
    }
}
