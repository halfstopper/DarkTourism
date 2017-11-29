package com.example.android.darktourism;

import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //selectChoiceInfo();

        //TODO 3.1 get a reference to the toolbar and inflate it using setSupportActionBar
        //Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        //setSupportActionBar(toolbar);

        //TODO 3.2 [go to res/values] put your tab names in the string resources
        //For the fragment string name

        //TODO 3.3 get a reference to the tab_layout widget
        //TODO 3.4 call the addTab method to add a tab
        //TODO 3.5 fill the space using setTabGravity method

        // Create an instance of the tab layout from the view.
        TabLayout tabLayout = (TabLayout) findViewById(R.id.tab_layout);
        // Set the text for each tab.
        tabLayout.addTab(tabLayout.newTab().setText(R.string.tab_label1));
        tabLayout.addTab(tabLayout.newTab().setText(R.string.tab_label2));
        tabLayout.addTab(tabLayout.newTab().setText(R.string.tab_label3));
        // Set the tabs to fill the entire layout.
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);

        // Use PagerAdapter to manage page views in fragments.

        //TODO 3.6 get a reference to the viewPager widget
        // Using PagerAdapter to manage page views in fragments.
        // Each page is represented by its own fragment.
        // This is another example of the adapter pattern.
        final ViewPager viewPager = (ViewPager) findViewById(R.id.pager);
        //TODO 3.7 create an instance of the PagerAdapter class
        final PageAdapter adapter = new PageAdapter
                (getSupportFragmentManager(), tabLayout.getTabCount());
        //TODO 3.8 assign the adapter to the viewPager using setAdapter
        viewPager.setAdapter(adapter);
        // Setting a listener for clicks

        //TODO 3.9 synchronise the tabs to the swipes by calling the viewPager.addOnPageChangeListener
        //Highlight the tab and reflect the appropriate fragment

        viewPager.addOnPageChangeListener(new
                TabLayout.TabLayoutOnPageChangeListener(tabLayout));

        //TODO 3.10 respond to clicks on the tabs by calling tabLayout.addOnPageChangeListener
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
                //Leave this alone if there's nothing to do, probably can include Toast

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
            }
        });

    }



}
