package com.example.androiddatabasetest;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
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
    private static final String TABLE = "theTable";

    // Contacts Table Columns names
    private static final String KEY_ID = "id";
    private static final String KEY_FIELD1 = "field1";
    private static final String KEY_FIELD2 = "field2";
    private static final String TAG = DatabaseHandler.class.getSimpleName();


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

    // Adding new entry
    public long addDataEntry(DataEntry entry) {

        Long index;

        Log.d(TAG,
                "addDataEntry " + entry.get_field1() + " "
                        + entry.get_field2());

        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_FIELD1, entry.get_field1());
        values.put(KEY_FIELD2, entry.get_field2());

        // Inserting Row
        index = db.insert(TABLE, null, values);
        db.close(); // Closing database connection

        Log.d(TAG, "addDataEntry returned index " + index);


        return index;
    }

    // Getting single entry
    public DataEntry geDataEntry(int id) {

        Log.d(TAG, "getDataEntry " + id);

        SQLiteDatabase db = this.getReadableDatabase();

        /*
        query(java.lang.String table,
               java.lang.String[] columns,
                java.lang.String selection,
                java.lang.String[] selectionArgs,
                java.lang.String groupBy,
                java.lang.String having,
                java.lang.String orderBy,
                java.lang.String limit)
       */
        Cursor cursor = db.query(
                TABLE,
                new String[] { KEY_ID, KEY_FIELD1, KEY_FIELD2},
                KEY_ID + "=?",
                new String[] { String.valueOf(id) },
                null,
                null,
                null,
                null
        );

        if (cursor != null)
            cursor.moveToFirst();

        DataEntry entry = new DataEntry(
                Integer.parseInt(cursor.getString(0)),
                Integer.parseInt(cursor.getString(1)),
                Integer.parseInt(cursor.getString(2)));

        Log.d(TAG,"getDataEntry entry " + " " + Integer.parseInt(cursor.getString(0)) + " " + entry.get_id());

        cursor.close();
        return entry;
    }

    public static String displayCharValues(String s) {
        StringBuilder sb = new StringBuilder();
        for (char c : s.toCharArray()) {
            sb.append((int) c).append(",");
        }
        return sb.toString();
    }

    // Getting All Entries
    public List<DataEntry> getAllEntries() {

        Log.d(TAG, "getAllEntries ");

        List<DataEntry> entryList = new ArrayList<DataEntry>();
        // Select All Query
        String selectQuery = "SELECT  * FROM " + TABLE;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                DataEntry entry = new DataEntry();
                entry.set_id(Integer.parseInt(cursor.getString(0)));
                entry.set_field1(Integer.parseInt(cursor.getString(1)));
                entry.set_field2(Integer.parseInt(cursor.getString(2)));
                entryList.add(entry);
            } while (cursor.moveToNext());
        }

        cursor.close();

        // return entry list
        return entryList;
    }

    // Getting entry Count
    public int getEntryCount() {
        int count;
        Log.d(TAG, "getEntryCount ");

        String countQuery = "SELECT  * FROM " + TABLE;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        count = cursor.getCount();
        cursor.close();

        // return count
        return count;
    }

    // Updating single contact
    public int updateEntry(DataEntry entry) {

        Log.d(TAG, "updateEntry!!!!!!!! ");

        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_FIELD1, entry.get_field1());
        values.put(KEY_FIELD2, entry.get_field2());

        // updating row
        return db.update(TABLE, values, KEY_ID + " = ?",
                new String[] { String.valueOf(entry.get_id()) });
    }

    // Deleting single contact
    public void deleteEntry(DataEntry entry) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(
                TABLE, KEY_ID + " = ?",
                new String[] { String.valueOf(entry.get_id()) }
        );
        db.close();
    }

    public void deleteAll(){
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE, null, null);
    }

}
