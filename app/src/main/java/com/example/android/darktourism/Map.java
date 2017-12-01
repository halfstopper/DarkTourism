package com.example.android.darktourism;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


/**
 * A simple {@link Fragment} subclass.
 */
public class Map extends Fragment {

    View mView;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        View view = inflater.inflate(R.layout.fragment_map, container, false);
        // Inflate the layout for this fragment




        //For Button. Fragment work different from usual mainactivity done in class, take note
        //The view function below is for example
/*        view.findViewById(R.id.btnGetData).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onClickFindInfo(view);
            }
        });*/


        mView = view;
        return view;
    }

}
