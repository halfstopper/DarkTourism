package com.example.android.darktourism;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class Itinerary extends Fragment {

    DbHelper dbHelper;
    List<Places> listPlace = new ArrayList<Places>();
    List<Travel> listTravel = new ArrayList<Travel>();
    Spinner spinnerChoiceFrom;
    Spinner spinnerChoiceTo;
    View mView;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view = inflater.inflate(R.layout.fragment_itinerary, container, false);
        dbHelper = new DbHelper(getActivity().getApplicationContext());
        dbHelper.createDataBase();
        listPlace = dbHelper.getAllPlaces();
        listTravel = dbHelper.getAllTravel();

        mView = view;
        selectChoiceInfo();

        view.findViewById(R.id.btnGetTravel).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onClickFindTravel(view);
            }
        });

        return view;
    }

    public void selectChoiceInfo (){
        spinnerChoiceFrom = (Spinner)mView.findViewById(R.id.spinnerAttractionChoiceFrom);
        spinnerChoiceTo = (Spinner)mView.findViewById(R.id.spinnerAttractionChoiceTo);

        List<String> list = new ArrayList<String>();

        //listPlace = dbHelper.getAllPlaces();
        list.add("Choose Attractions:");
        for (Places places: listPlace){
            list.add(places.getPlaceName());
        }
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(getActivity(),
                android.R.layout.simple_spinner_item, list);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerChoiceFrom.setAdapter(dataAdapter);
        spinnerChoiceTo.setAdapter(dataAdapter);

    }


    public void onClickFindTravel(View view) {
        spinnerChoiceFrom = (Spinner)mView.findViewById(R.id.spinnerAttractionChoiceFrom);
        spinnerChoiceTo = (Spinner)mView.findViewById(R.id.spinnerAttractionChoiceTo);
        TextView TravelBus = (TextView) mView.findViewById(R.id.txtviewBusInfo);
        TextView TravelCab = (TextView) mView.findViewById(R.id.txtviewCabInfo);
        String FromPlace = String.valueOf(spinnerChoiceFrom.getSelectedItem());
        String ToPlace = String.valueOf(spinnerChoiceTo.getSelectedItem());

        if (FromPlace.equals("Former Ford Factory"))
            FromPlace = "Ford Factory";
        if (FromPlace.equals("Singapore National Museum"))
            FromPlace = "National Museum Of Singapore";
        if (FromPlace.equals("Fort Canning Hill"))
            FromPlace = "Ford Canning Hill";
        if (FromPlace.equals("Civilian War Memorial"))
            FromPlace = "War Memorial Park";


        for (Travel travel:listTravel){


            if (travel.getFrom().equals(FromPlace)){
                String info[] = travel.getTo(ToPlace).split(",");
                TravelBus.setText("Public Transport: $"+ info[0] + " Traveling Time: " + info[1] + "min");
                TravelCab.setText("Cab Ride        : $"+ info[2] + " Traveling Time: " + info[3] + "min");
            }

        }


    }

}
