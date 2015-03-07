package com.example.androiddatabasetest;

import android.content.Context;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBar;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Choreographer.FrameCallback;
import android.os.Build;
import android.view.inputmethod.InputMethodManager;

public class MainActivity extends ActionBarActivity implements
		Fragment1.Callback, Fragment2.Callback {

	private Fragment1 mFragment1;
	private Fragment2 mFragment2;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		mFragment1 = new Fragment1();


		if (savedInstanceState == null) {
			getSupportFragmentManager().beginTransaction()
					.add(R.id.container, mFragment1).commit();
		}

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	@Override
	public void onFragment1AgregarClick() {

        if (mFragment2 != null){
            mFragment2 = null;
        }

        mFragment2 = new Fragment2();

        Bundle bundle = new Bundle();
        bundle.putInt("indice",-1);
        mFragment2.setArguments(bundle);
		getSupportFragmentManager().beginTransaction()
				.replace(R.id.container, mFragment2)
				.addToBackStack("mFragment2").commit();
        getSupportFragmentManager().beginTransaction().remove(mFragment1);

	}

    @Override
    public void onFragment1EditClick(DataEntry dataEntry) {

        if (mFragment2 != null){
            mFragment2 = null;
        }

        mFragment2 = new Fragment2();

        Bundle bundle = new Bundle();
        bundle.putInt("indice",dataEntry.get_id());
        mFragment2.setArguments(bundle);
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.container, mFragment2)
                .addToBackStack("mFragment2").commit();
    }

    @Override
	public void onFragment2AgregarEntryClick() {

        getSupportFragmentManager().beginTransaction()
                .replace(R.id.container, mFragment1).commit();


        InputMethodManager inputManager = (InputMethodManager)
                getSystemService(Context.INPUT_METHOD_SERVICE);

        inputManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(),
                InputMethodManager.HIDE_NOT_ALWAYS);

  }

    public void CLICk(View view) {

        DataEntry entry = (DataEntry) view.getTag();
        Log.d(MainActivity.class.getSimpleName(),"CLICK"+entry.get_field1());
        mFragment1.delete(entry);
    }
}
