package com.example.android.darktourism;


public class Places {
    private String PlaceName;
    private String Address;
    private String Remarks;
    private String Coordinate;

    public Places(){

    }

    public Places (String placeName, String address, String remarks, String coordinate){

        this.PlaceName = placeName;
        this.Address = address;
        this.Remarks = remarks;
        this.Coordinate = coordinate;
    }

    public String getPlaceName() {
        return PlaceName;
    }

    public void setPlaceName(String placeName) {
        PlaceName = placeName;
    }

    public String getAddress() {
        return Address;
    }

    public void setAddress(String address) {
        Address = address;
    }

    public String getRemarks() {
        return Remarks;
    }

    public void setRemarks(String remarks) {
        Remarks = remarks;
    }

    public String getCoordinate() {
        return Coordinate;
    }

    public void setCoordinate(String coordinate) {
        Coordinate = coordinate;
    }
}
