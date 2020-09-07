package com.hemanth.cabshare;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;

public class CustomAdapter extends BaseAdapter {
    LinearLayout linearLayout;
    Context context;
    ArrayList<String> dest;
    ArrayList<String> date;
    ArrayList<String> time;
    LayoutInflater inflater;

    public CustomAdapter(Context context, ArrayList<String> dest, ArrayList<String> date, ArrayList<String> time) {
        this.context = context;
        this.dest = dest;
        this.date = date;
        this.time = time;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return dest.size();
    }

    @Override
    public Object getItem(int i) {
        ArrayList<String> arrayList = new ArrayList<>();
        arrayList.add(dest.get(i));
        arrayList.add(date.get(i));
        arrayList.add(time.get(i));
        return arrayList;
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        view = inflater.inflate(R.layout.list_item_myrides, null);
        TextView txt_dest = (TextView) view.findViewById(R.id.dest);
        TextView txt_date = (TextView) view.findViewById(R.id.date);
        linearLayout = view.findViewById(R.id.item);
        final TextView txt_time = (TextView) view.findViewById(R.id.time);
        txt_date.setText(date.get(i));
        txt_dest.setText(dest.get(i));
        txt_time.setText(time.get(i));
        return view;
    }
}
