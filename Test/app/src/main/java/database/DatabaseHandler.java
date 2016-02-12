package database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;


import com.test.Advertise;

import java.util.ArrayList;

/**
 * Created by toshiba on 2/8/2016.
 */
public class DatabaseHandler extends SQLiteOpenHelper {

    // Database Version
    private static final int DATABASE_VERSION = 1;

    // Database Namec
    private static final String DATABASE_NAME = "test";

    // User table name
    private static final String TABLE_ADVERTISE = "advertise";

    // Customer Table Columns names
    private static final String KEY_ID = "id";
    private static final String KEY_TITLE = "title";
    private static final String KEY_URI = "uri";

    public DatabaseHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        String CREATE_ADVERTISE_TABLE = "CREATE TABLE " + TABLE_ADVERTISE + "("
                + KEY_ID + " TEXT,"
                + KEY_TITLE + " TEXT,"
                + KEY_URI + " TEXT"
                + ")";

        db.execSQL(CREATE_ADVERTISE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        // Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_ADVERTISE);

        // Create tables again
        onCreate(db);
    }

    /**
     * All CRUD(Create, Read, Update, Delete) Operations
     */

    // Adding new customer record
    public void addData(Advertise advertise) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_ID, advertise.getId());
        values.put(KEY_TITLE, advertise.getTitle());
        values.put(KEY_URI, advertise.getUri());

        Log.e("Inserted", "Inserted");
        // Inserting Row
        db.insert(TABLE_ADVERTISE, null, values);
        db.close(); // Closing database connection
    }

    // Getting All Customers
    public ArrayList<Advertise> getAllData() {
        ArrayList<Advertise> advertiseList = new ArrayList<Advertise>();
        // Select All Query
        String selectQuery = "SELECT * FROM " + TABLE_ADVERTISE;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                Advertise advertise = new Advertise(cursor.getString(0), cursor.getString(1), cursor.getString(2));
                // Adding customers to list
                advertiseList.add(advertise);
            } while (cursor.moveToNext());
        }

        db.close();
        // return advertise list
        return advertiseList;
    }

    public boolean isEmpty() {
        String countQuery = "SELECT * FROM " + TABLE_ADVERTISE;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        cursor.moveToFirst();
        if (cursor.getCount() > 0) {
            cursor.close();
            db.close();
            return true;
        }
        cursor.close();
        db.close();
        return false;
    }
}
