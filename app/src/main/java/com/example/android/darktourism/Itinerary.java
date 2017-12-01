package com.example.android.darktourism;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

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
    EditText Budget;
    ArrayList <String >itenary_arraylist = new ArrayList();


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view = inflater.inflate(R.layout.fragment_itinerary, container, false);
        dbHelper = new DbHelper(getActivity().getApplicationContext());
        dbHelper.createDataBase();
        listPlace = dbHelper.getAllPlaces();
        listTravel = dbHelper.getAllTravel();
        Budget =(EditText)view.findViewById(R.id.Budget);


        mView = view;
        selectChoiceInfo();

        view.findViewById(R.id.btnAddPlace).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                btnAddPlace(view);
                //onClickFindTravel(view);
            }
        });

        view.findViewById(R.id.btnDeletePlace).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                btnDeletePlace(view);
                //onClickFindTravel(view);
            }
        });

        view.findViewById(R.id.btnBruteRoute).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                btnBruteRoute(view);
                //onClickFindTravel(view);
            }
        });

        return view;
    }

    public void selectChoiceInfo (){
        //spinnerChoiceFrom = (Spinner)mView.findViewById(R.id.spinnerAttractionChoiceFrom);
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
        //spinnerChoiceFrom.setAdapter(dataAdapter);
        spinnerChoiceTo.setAdapter(dataAdapter);
    }

    //Adding Place to Itenary List
    public void btnAddPlace(View view){
        TextView itenaryPlace = (TextView)mView.findViewById(R.id.Iternary_Place);
        spinnerChoiceTo = (Spinner)mView.findViewById(R.id.spinnerAttractionChoiceTo);
        String ToPlace = String.valueOf(spinnerChoiceTo.getSelectedItem());
        if (ToPlace.equals("Choose Attractions:")){
            Toast.makeText(getActivity().getApplicationContext(),"Please Choose Attraction",Toast.LENGTH_SHORT).show();
        }
        else if (itenary_arraylist.isEmpty()){
            //Add Starting Location
            itenary_arraylist.add("Marina Bay Sands");
        }
        else if (itenary_arraylist.get(itenary_arraylist.size()-1).equals(ToPlace)){
            Toast.makeText(getActivity().getApplicationContext(),"Duplicate Latest Entry",Toast.LENGTH_SHORT).show();
        }
        else{
            itenary_arraylist.add(ToPlace);
            Toast.makeText(getActivity().getApplicationContext(),"Added "+ ToPlace + "To iternary List",Toast.LENGTH_SHORT).show();
        }
        StringBuilder builder = new StringBuilder();
        for (String line:itenary_arraylist){
            builder.append(line + "\n");
        }
        itenaryPlace.setText(builder.toString());
    }


    //Deleting Place from Itenary List
    public void btnDeletePlace(View view){
        TextView itenaryPlace = (TextView)mView.findViewById(R.id.Iternary_Place);
        if (itenary_arraylist!=null){
            itenary_arraylist.remove(itenary_arraylist.size()-1);
            Toast.makeText(getActivity().getApplicationContext(),"Remove Last Location",Toast.LENGTH_SHORT).show();
        }
        else{
            Toast.makeText(getActivity().getApplicationContext(),"Nothing To Delete",Toast.LENGTH_SHORT).show();
        }
        StringBuilder builder = new StringBuilder();
        for (String line:itenary_arraylist){
            builder.append(line + "\n");
        }
        itenaryPlace.setText(builder.toString());
    }

    //Using Greedy Algorithm with restraint
    public void btnBruteRoute(View view){

        double budget_travel;
        String text = Budget.getText().toString();
        if(!text.isEmpty())
            try
            {
                budget_travel = Double.parseDouble(text);
                // it means it is double
            } catch (Exception e1) {
                // this means it is not double
                e1.printStackTrace();
            }

        int itenary_size = itenary_arraylist.size();
        for (int i =0; i<itenary_size ; i ++){
            String FromPlace = itenary_arraylist.get(i);
            String ToPlace = itenary_arraylist.get(i+1);
        }


    }










    //Previous code to calculate between distance fare, retreive from SQLite database
/*    public void onClickFindTravel(View view) {
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
                //info[0] Public Transport Price
                //info[1] Public Transport Travelling Time
                //info[2] Cab Ride Price
                //info[3] Cab Ride Traveling time
                TravelBus.setText("Public Transport: $"+ info[0] + " Traveling Time: " + info[1] + "min");
                TravelCab.setText("Cab Ride        : $"+ info[2] + " Traveling Time: " + info[3] + "min");
            }

        }


    }*/

}
