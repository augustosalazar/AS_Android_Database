package com.example.androiddatabasetest.Adapters;


import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.example.androiddatabasetest.model.DataEntry;
import com.example.androiddatabasetest.R;

import java.util.List;

public class CustomAdapter extends BaseAdapter implements View.OnClickListener {
    private Context context;
    private List<DataEntry> listEntries;

    public CustomAdapter(Context context,List<DataEntry> listEntries) {
        this.context = context;
        this.listEntries = listEntries;
    }



    @Override
    public int getCount() {
        return listEntries.size();
    }

    @Override
    public Object getItem(int i) {
        return listEntries.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int position, View view, ViewGroup viewGroup) {
        DataEntry entry = listEntries.get(position);

        if (view == null) {
            LayoutInflater inflater = (LayoutInflater) context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.row_layout, null);
        }

        TextView f1 = (TextView) view.findViewById(R.id.tvField1);
        TextView f2 = (TextView) view.findViewById(R.id.tvField2);

        f1.setText(String.valueOf(entry.get_field1()));
        f2.setText(String.valueOf(entry.get_field2()));

        Log.d(CustomAdapter.class.getSimpleName(), "Id "+String.valueOf(entry.get_id()));
        Log.d(CustomAdapter.class.getSimpleName(), "Field1 "+String.valueOf(entry.get_field1()));
        Log.d(CustomAdapter.class.getSimpleName(), "Field2 "+String.valueOf(entry.get_field2()));

        Button btnRemove = (Button) view.findViewById(R.id.btnRemove);
        btnRemove.setFocusableInTouchMode(false);
        btnRemove.setFocusable(false);
        //btnRemove.setOnClickListener(this);
        btnRemove.setTag(entry);
        view.setTag(entry);

        return view;
    }

    @Override
    public void onClick(View view) {
        DataEntry entry = (DataEntry) view.getTag();
        listEntries.remove(entry);
        notifyDataSetChanged();
    }
}
