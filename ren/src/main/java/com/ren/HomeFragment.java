package com.ren;


import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

/**
 * Created by Alvin on 6/30/2016.
 */
public class HomeFragment extends Fragment{
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.home_fragment_layout, container, false);
        final ArrayList<Fragment> list = new ArrayList<Fragment>();

        list.add(new testfragment());
        list.add(new testfragment2());
        list.add(new MyPostFragment());

        ViewPager pager = (ViewPager) v.findViewById(R.id.homePager);
        pager.setAdapter(new FragmentPagerAdapter(getChildFragmentManager()) {
            @Override
            public Fragment getItem(int i) {
                return list.get(i);
            }

            @Override
            public int getCount() {
                return list.size();
            }
            @Override
            public CharSequence getPageTitle(int position)
            {
                switch (position)
                {
                    case 0:
                        return "Top";
                    case 1:
                        return "New";
                    case 2:
                        return "My Post";
                    default:
                        return null;
                }
            }
        });
        TabLayout tablayout = (TabLayout)v.findViewById(R.id.tablayout);
        tablayout.setupWithViewPager(pager);
        return v;
    }

}
