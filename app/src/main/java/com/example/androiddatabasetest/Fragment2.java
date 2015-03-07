package com.example.androiddatabasetest;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

public class Fragment2 extends Fragment {

	private static final String TAG = Fragment2.class.getSimpleName();
	private Callback callback;
	private Button mBoton;
	private EditText mEditTextField1;
	private EditText mEditTextField2;
	private DatabaseHandler db;
    private DataEntry entry;
    private int  indice;

	@Override
	public View onCreateView(LayoutInflater inflater,
			@Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

		View rootView = inflater.inflate(R.layout.fragment2, container, false);

        indice = getArguments().getInt("indice",-1);

        Log.d(Fragment2.class.getSimpleName(),"indice "+indice);



        mBoton = (Button) rootView.findViewById(R.id.buttonAceptar);
		mEditTextField1 = (EditText) rootView.findViewById(R.id.editText1);
		mEditTextField2 = (EditText) rootView.findViewById(R.id.editText2);



		mBoton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				saveData();
			}
		});

		return rootView;
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);

		db = new DatabaseHandler(getActivity().getApplicationContext());
        db.getWritableDatabase();
        if (indice != -1){
            entry = db.geDataEntry(indice);
            mEditTextField1.setText(String.valueOf(entry.get_field1()));
            mEditTextField2.setText(String.valueOf(entry.get_field2()));
        }
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

    @Override
    public void onPause() {
        db = null;
        super.onPause();
    }

    protected void saveData() {


		if (mEditTextField1.getText().toString().equals("")
				&& mEditTextField1.getText().toString().equals("")) {

			new AlertDialog.Builder(getActivity())
		    .setTitle("Entrada de datos")
		    .setMessage("Debe agregar numeros para ambos campos")
		    .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
		        public void onClick(DialogInterface dialog, int which) { 
		        	 dialog.cancel();
		        }
		     })
		    .setIcon(android.R.drawable.ic_dialog_alert)
		     .show();

			return;
		}

        if (indice != -1){
            Log.d(TAG, "Edit entry");

            entry.set_field1(Integer.valueOf(mEditTextField1
                    .getText().toString()));
            entry.set_field2(Integer.valueOf(mEditTextField2
                    .getText().toString()));
            db.updateEntry(entry);

            mEditTextField1.setText("");
            mEditTextField2.setText("");

            callback.onFragment2AgregarEntryClick();

        } else {

            DataEntry entry = new DataEntry(Integer.valueOf(mEditTextField1
                    .getText().toString()), Integer.valueOf(mEditTextField2
                    .getText().toString()));

            db.addDataEntry(entry);

            Log.d(TAG, "Adding new entry");

            mEditTextField1.setText("");
            mEditTextField2.setText("");

            callback.onFragment2AgregarEntryClick();
        }
	}

	public interface Callback {
		public void onFragment2AgregarEntryClick();
	}

}
