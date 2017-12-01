package com.example.android.darktourism;


import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class Attraction extends Fragment {


    DbHelper dbHelper;
    Button btnGetData;
    Button btnGetMap;
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
        btnGetMap = (Button) view.findViewById(R.id.btnGetMap);
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


        view.findViewById(R.id.btnGetMap).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                spinnerChoiceInfo = (Spinner)mView.findViewById(R.id.spinnerAttractionChoiceInfo);
                String myLocation = String.valueOf(spinnerChoiceInfo.getSelectedItem());
                Uri.Builder builder = new Uri.Builder(); // Collect information and pass it to the method
                builder.scheme("geo").opaquePart("0,0").appendQueryParameter("q",myLocation);

                Uri geoLocation = builder.build();
                Intent mapIntent = new Intent(Intent.ACTION_VIEW, geoLocation);
                mapIntent.setPackage("com.google.android.apps.maps");
                startActivity(mapIntent);


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
