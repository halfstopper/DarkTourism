package com.example.android.darktourism;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class Attraction extends Fragment {


    DbHelper dbHelper;
    Button btnGetData;
    List<Places> listPlace = new ArrayList<Places>();
    List<Travel> listTravel = new ArrayList<Travel>();
    Spinner spinnerChoiceInfo;

    View mView;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_attraction, container, false);
        btnGetData = (Button) view.findViewById(R.id.btnGetData);
        //container = (LinearLayout)findViewById(R.id.container);

        dbHelper = new DbHelper(getActivity().getApplicationContext());
        dbHelper.createDataBase();

        listPlace = dbHelper.getAllPlaces();
        listTravel = dbHelper.getAllTravel();


        //Everything in listPlace (Use for Loop to Attract Data)

        mView = view;
        selectChoiceInfo();

        view.findViewById(R.id.btnGetData).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onClickFindInfo(view);
            }
        });




        return view;
    }

    public void selectChoiceInfo (){
        spinnerChoiceInfo = (Spinner)mView.findViewById(R.id.spinnerAttractionChoiceInfo);
        List<String> list = new ArrayList<String>();

        //listPlace = dbHelper.getAllPlaces();
        list.add("Choose Attractions:");
        for (Places places: listPlace){
            list.add(places.getPlaceName());
        }
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(getActivity(),
                android.R.layout.simple_spinner_item, list);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerChoiceInfo.setAdapter(dataAdapter);
    }

    public void onClickFindInfo(View view) {
        spinnerChoiceInfo = (Spinner)mView.findViewById(R.id.spinnerAttractionChoiceInfo);
        TextView Remark = (TextView) mView.findViewById(R.id.txtviewRemark);
        TextView Address = (TextView) mView.findViewById(R.id.txtviewAddress);
        TextView Coordinate = (TextView) mView.findViewById(R.id.txtviewCoordinate);
        String ChoicePlace = String.valueOf(spinnerChoiceInfo.getSelectedItem());
        for (Places place : listPlace) {
            if (place.getPlaceName().equals(ChoicePlace))
            {
                Remark.setText(place.getRemarks());
                Address.setText(place.getAddress());
                Coordinate.setText(place.getCoordinate());
            }
        }

    }

}
