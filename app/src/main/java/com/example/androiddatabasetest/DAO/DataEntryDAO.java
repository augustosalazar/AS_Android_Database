package com.example.androiddatabasetest.DAO;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.example.androiddatabasetest.model.DataEntry;
import com.example.androiddatabasetest.model.DatabaseHandler;

import java.util.ArrayList;
import java.util.List;

public class DataEntryDAO {

    public static final String TAG = "DataEntryDAO";


    private SQLiteDatabase mDatabase;
    private DatabaseHandler mDbHelper;
    private Context mContext;
    private String[] mAllColumns = { DatabaseHandler.KEY_ID,
            DatabaseHandler.KEY_FIELD1, DatabaseHandler.KEY_FIELD2 };


    public DataEntryDAO(Context context) {
        this.mContext = context;
        mDbHelper = new DatabaseHandler(context);
        // open the database
        try {
            open();
        } catch (SQLException e) {
            Log.e(TAG, "SQLException on openning database " + e.getMessage());
            e.printStackTrace();
        }
    }



    // Adding new entry
    public long addDataEntry(DataEntry entry) {

        Long index;

        Log.d(TAG,
                "addDataEntry " + entry.get_field1() + " "
                        + entry.get_field2());


        ContentValues values = new ContentValues();
        values.put(DatabaseHandler.KEY_FIELD1, entry.get_field1());
        values.put(DatabaseHandler.KEY_FIELD2, entry.get_field2());

        // Inserting Row
        index = mDatabase.insert(DatabaseHandler.TABLE, null, values);

        Log.d(TAG, "addDataEntry returned index " + index);

        return index;
    }

    // Getting single entry
    public DataEntry geDataEntry(int id) {

        Log.d(TAG, "getDataEntry " + id);

        Cursor cursor = mDatabase.query(
                DatabaseHandler.TABLE,
                new String[] { DatabaseHandler.KEY_ID, DatabaseHandler.KEY_FIELD1, DatabaseHandler.KEY_FIELD2},
                DatabaseHandler.KEY_ID + "=?",
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


    // Getting All Entries
    public List<DataEntry> getAllEntries() {

        Log.d(TAG, "getAllEntries ");

        List<DataEntry> entryList = new ArrayList<DataEntry>();
        // Select All Query
        String selectQuery = "SELECT  * FROM " + DatabaseHandler.TABLE;

        Cursor cursor = mDatabase.rawQuery(selectQuery, null);

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

        String countQuery = "SELECT  * FROM " + DatabaseHandler.TABLE;

        Cursor cursor = mDatabase.rawQuery(countQuery, null);
        count = cursor.getCount();
        cursor.close();

        // return count
        return count;
    }

    // Updating single contact
    public int updateEntry(DataEntry entry) {

        ContentValues values = new ContentValues();
        values.put(DatabaseHandler.KEY_FIELD1, entry.get_field1());
        values.put(DatabaseHandler.KEY_FIELD2, entry.get_field2());

        // updating row
        return mDatabase.update(DatabaseHandler.TABLE, values, DatabaseHandler.KEY_ID + " = ?",
                new String[] { String.valueOf(entry.get_id()) });
    }

    // Deleting single contact
    public void deleteEntry(DataEntry entry) {

        mDatabase.delete(
                DatabaseHandler.TABLE, DatabaseHandler.KEY_ID + " = ?",
                new String[] { String.valueOf(entry.get_id()) }
        );
    }


    public void open() throws SQLException {
        mDatabase = mDbHelper.getWritableDatabase();
    }

    public void close() {
        mDbHelper.close();
    }
}
