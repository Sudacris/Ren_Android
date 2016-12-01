package com.ren;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by user on 11/18/2016.
 */

public class newImageAdapter extends ArrayAdapter {
    //imageAdapter
    List list = new ArrayList();
    public newImageAdapter(Context context, int resource) {
        super(context, resource);
    }

    public void add(myImage object) {
        super.add(object);
        list.add(object);
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View row;
        row = convertView;
        imageHolder imageHolder;
        if(row == null){
            LayoutInflater inflater = (LayoutInflater) this.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            row = inflater.inflate(R.layout.records, parent, false);
            imageHolder = new imageHolder();
            imageHolder.tx_name = (TextView)row.findViewById(R.id.name);
            imageHolder.tx_url = (TextView)row.findViewById(R.id.url);
            imageHolder.imageView = (ImageView)row.findViewById(R.id.tx_image) ;
            row.setTag(imageHolder);
        }
        else{
            imageHolder = (imageHolder)row.getTag();
        }
        myImage image = (myImage) this.getItem(position);
        imageHolder.tx_name.setText(image.getName());
        imageHolder.tx_url.setText(image.getUrl());
        imageHolder.imageView.setImageBitmap(image.getBitmap());
        return row;
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    static class imageHolder
    {
        TextView tx_name, tx_url;
        ImageView imageView;
    }
}
