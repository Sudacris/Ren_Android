package com.ren;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

/**
 * Created by user on 10/22/2016.
 */
public class testfragment extends Fragment {
    //helloworld
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        if(container == null)
        {
            return null;
        }
        return inflater.inflate(R.layout.activity_test, container, false);
    }
}
