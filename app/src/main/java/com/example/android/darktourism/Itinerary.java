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
    ArrayList <String>itenary_arraylist = new ArrayList();
    ArrayList <Double> public_price = new ArrayList();
    ArrayList <Double> public_time = new ArrayList();
    ArrayList <Double> cab_price = new ArrayList();
    ArrayList <Double> cab_time = new ArrayList();
    ArrayList <Double> walking_time = new ArrayList();
    ArrayList <String> Mode_Transport = new ArrayList<>();



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

        //For Spinner Update (Drop Down Menu)
        selectChoiceInfo();

        //AddPlace Button Update
        view.findViewById(R.id.btnAddPlace).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                btnAddPlace(view);
                //onClickFindTravel(view);
            }
        });

        //Delete Place Button Update
        view.findViewById(R.id.btnDeletePlace).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                btnDeletePlace(view);
                //onClickFindTravel(view);
            }
        });
        //BruteForce Route Button Update
        view.findViewById(R.id.btnBruteRoute).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                btnBruteRoute(view);
                //onClickFindTravel(view);
            }
        });

        return view;
    }
    //DropDown Select Attractions
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
            itenary_arraylist.add(ToPlace);
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

    //Using Greedy Algorithm at first location with restraint with limited Budget
    public void btnBruteRoute(View view){
        TextView itenaryPlace = (TextView)mView.findViewById(R.id.Iternary_Path);
        if (public_price.size()>=1){
            public_price.clear();
            public_time.clear();
            cab_price.clear();
            cab_time.clear();
            walking_time.clear();
            Mode_Transport.clear();
        }
        double budget_travel= 0;
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
        for (int i =0; i<itenary_size-1 ; i ++){
            String FromPlace = itenary_arraylist.get(i);
            String ToPlace = itenary_arraylist.get(i+1);
            //Due to the name is different between 2 tables, I have to convert some place name manually. #Lazy to change DN
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
                    public_price.add(Double.parseDouble(info[0]));
                    public_time.add(Double.parseDouble(info[1]));
                    cab_price.add(Double.parseDouble(info[2]));
                    cab_time.add(Double.parseDouble(info[3]));
                    walking_time.add(Double.parseDouble(info[4]));
                }
            }
        }
        double duration = 0;
        double travel_spend = 0;
/*        for (int i =0; i<itenary_size-1 ; i ++){
            if (budget_travel <= 0){
                String time = String.valueOf(walking_time.get(i));
                Mode_Transport.add("To " + itenary_arraylist.get(i+1)+" By Walking in " + time + " min");
                duration += walking_time.get(i);
            }
            else if (budget_travel>cab_price.get(i)){
                String price = String.valueOf(cab_price.get(i));
                String time = String.valueOf(cab_time.get(i));
                Mode_Transport.add("To " + itenary_arraylist.get(i+1)+" By Cab $"+price+"  "+time+ "min");
                budget_travel -= cab_price.get(i);
                travel_spend += cab_price.get(i);
                duration += cab_time.get(i);

            }
            else{
                String price = String.valueOf(public_price.get(i));
                String time = String.valueOf(public_time.get(i));
                Mode_Transport.add("To " +itenary_arraylist.get(i+1)+" By Public Transport $"+price+" " + time+"min");
                budget_travel -= public_price.get(i);
                travel_spend += public_price.get(i);
                duration += public_time.get(i);
            }

        }*/


        for (int i =0; i<itenary_size-1 ; i ++){
            if (budget_travel>cab_price.get(i)){
                String price = String.valueOf(cab_price.get(i));
                String time = String.valueOf(cab_time.get(i));
                Mode_Transport.add("To " +itenary_arraylist.get(i+1)+" By Cab $"+price+"  "+time+ "min");
                budget_travel -= cab_price.get(i);
                travel_spend += cab_price.get(i);
                duration += cab_time.get(i);
            }
            else if (budget_travel>public_price.get(i)){
                String price = String.valueOf(public_price.get(i));
                String time = String.valueOf(public_time.get(i));
                Mode_Transport.add("To " + itenary_arraylist.get(i+1)+" By Public Transport $"+price+" " + time+"min");
                budget_travel -= public_price.get(i);
                travel_spend += public_price.get(i);
                duration += public_time.get(i);

            }
            else{
                String time = String.valueOf(walking_time.get(i));
                Mode_Transport.add("To " + itenary_arraylist.get(i+1)+" By Walking in " + time + " min");
                duration += walking_time.get(i);
            }

        }
        String total_duration = String.valueOf(duration);
        String total_spend = String.valueOf(travel_spend);
        Mode_Transport.add("Total Time By Walking/Public Transport/Cab = " + total_duration + "min");
        Mode_Transport.add("Total Expenditure On Transportation is $" + total_spend);

        StringBuilder builder = new StringBuilder();
        for (String line:Mode_Transport){
            builder.append(line + "\n");
        }
        itenaryPlace.setText(builder.toString());


    }

}
