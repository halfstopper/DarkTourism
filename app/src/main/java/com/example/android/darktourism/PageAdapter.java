package com.example.android.darktourism;

//Make Sure is the android.support.v4, it will ask to choose which one to import that is similar
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

public class PageAdapter extends FragmentStatePagerAdapter {

    int mNumOfTabs;

    public PageAdapter(FragmentManager fm, int mNumOfTabs){
        super(fm);
        this.mNumOfTabs = mNumOfTabs;
    }

    //TODO 2.1  - implement the method skeleton by using Ctrl + O
    //TODO 2.2  - getItem takes in a position and returns a fragment



    //Linking and choosing which fragment to use
    @Override
    public Fragment getItem(int position) {
        switch (position){
            case 0:
                return new Attraction();
            case 1:
                return new Map();
            case 2:
                return new Itinerary();
            default:
                return null;
        }
    }

    //            depending on the position chosen
    //TODO 2.3  - getCount returns the number of tabs
    @Override
    public int getCount() {
        return mNumOfTabs;
    }

}