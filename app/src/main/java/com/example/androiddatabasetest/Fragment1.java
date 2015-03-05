package com.example.androiddatabasetest;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

public class Fragment1 extends Fragment {

	private static final String TAG = Fragment1.class.getSimpleName();
	private Callback callback;
	private Button mBoton;
	private ListView mListView;
	private DatabaseHandler db;

	public Fragment1() {
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.fragment1, container, false);

		Log.d(TAG, "onCreateView create dbManager");
		db = new DatabaseHandler(getActivity().getApplicationContext());
		db.getWritableDatabase();

		mBoton = (Button) rootView.findViewById(R.id.button1);
		mListView = (ListView) rootView.findViewById(R.id.carListView);

        mListView.setFocusable(false);
        mListView.setFocusableInTouchMode(false);
        mListView.setClickable(false);

		mBoton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Log.d(TAG, "Fragment1 click");
				callback.onFragment1AgregarClick();
			}
		});

        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> arg0, View view, int position, long index) {
                Log.d("List","Edit index "+index);
                callback.onFragment1EditClick((DataEntry)view.getTag());
            }
        });

		
		return rootView;
	}
	
	@Override
	public void onResume() {
		super.onResume();
		loadData();
		
	}
	
	private void loadData(){
		// Reading all entries
		Log.d(TAG, "Reading all contacts..");

		List<DataEntry> entries = db.getAllEntries();

        CustomAdapter adapter = new CustomAdapter(getActivity(), entries);

        mListView.setAdapter(adapter);

	}

	@Override
	public void onAttach(Activity activity) {
		try {
			callback = (Callback) activity;
		} catch (ClassCastException e) {
			Log.d(TAG, e.getMessage());
			throw new ClassCastException(activity.toString()
					+ " must implement CarDataFragment.Callback");
		}
		super.onAttach(activity);
	}

    public void delete(DataEntry entry) {
        db.deleteEntry(entry);
        loadData();
        // listPhonebook.remove(view.getId());

    }

    public interface Callback {
		public void onFragment1AgregarClick();
        public void onFragment1EditClick(DataEntry dataEntry);
	}

}
