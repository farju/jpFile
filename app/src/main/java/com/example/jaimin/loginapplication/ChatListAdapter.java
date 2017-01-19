package com.example.jaimin.loginapplication;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by Jaimin on 5/23/2016.
 */
public class ChatListAdapter extends ArrayAdapter<HashMap<String, String>> {
    Context context;
    int resourceId;
    ArrayList<HashMap<String,String>> data;
    LinearLayout linearLayout;
    public ChatListAdapter(Context context, int resource, ArrayList<HashMap<String,String>> objects) {
        super(context, resource, objects);
        this.context=context;
        this.resourceId=resource;
        this.data = objects;
    }
    public View getView(int position, View convertView, ViewGroup parent) {
        View row = convertView;

        if (row == null) {
            LayoutInflater inflater = ((Activity) context).getLayoutInflater();
            row = inflater.inflate(resourceId, parent, false);
            linearLayout = (LinearLayout)row.findViewById(R.id.listView3);
            HashMap<String,String> cmsg = data.get(position);

        }

        return row;

    }

}
