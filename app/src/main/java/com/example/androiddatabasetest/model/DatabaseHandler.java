package com.example.androiddatabasetest.model;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DatabaseHandler extends SQLiteOpenHelper {

    // All Static variables
    // Database Version
    private static final int DATABASE_VERSION = 1;

    // Database Name
    private static final String DATABASE_NAME = "theDataBase";

    // Contacts table name
    public static final String TABLE = "theTable";

    // Contacts Table Columns names
    public static final String KEY_ID = "id";
    public static final String KEY_FIELD1 = "field1";
    public static final String KEY_FIELD2 = "field2";
    public static final String TAG = DatabaseHandler.class.getSimpleName();


    public DatabaseHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        Log.d(TAG, "DatabaseHandler Constructor");
    }

    public DatabaseHandler(Context context, String name, CursorFactory factory,
                           int version) {
        super(context, name, factory, version);
    }

    @Override
    public SQLiteDatabase getWritableDatabase() {
        return super.getWritableDatabase();
    }

    @Override
    public SQLiteDatabase getReadableDatabase() {
        return super.getReadableDatabase();
    }


    @Override
    public void onCreate(SQLiteDatabase db) {

        String CREATE_TABLE = "CREATE TABLE " + TABLE + "("
                + KEY_ID + " integer primary key," + KEY_FIELD1 + " integer,"
                + KEY_FIELD2 + " integer" + ")";

        Log.d(TAG, "onCreate " + CREATE_TABLE);

        try {
            if (db.isOpen()) {
                db.execSQL(CREATE_TABLE);
            }
        } catch (Exception e) {
            Log.d("onCreateDB", e.getMessage());
            return;
        } finally {

        }

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        Log.d(TAG, "onUpgrade ");

        // Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS " + TABLE);

        // Create tables again
        onCreate(db);
    }

    public void deleteAll(){
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE, null, null);
    }

}
