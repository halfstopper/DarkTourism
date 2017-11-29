package com.example.android.darktourism;

public class Travel {
    private String From;
    private String Marina_Bay_Sand;
    private String Bukit_Chandu;
    private String Ford_Factory;
    private String Labrador_Park;
    private String National_Museum_Of_Singapore;
    private String Ford_Canning_Hill;
    private String Kranji_War_Memorial;
    private String Fort_Siliso;
    private String War_Memorial_Park;
    private String St_Andrew_Cathedral;
    private String Changi_Chapel_Museum;

    public Travel (){

    }

    public Travel (String From,String Marina_Bay_Sand,String Bukit_Chandu,String Ford_Factory,String Labrador_Park,
                   String National_Museum_Of_Singapore,String Ford_Canning_Hill,String Kranji_War_Memorial,
                   String Fort_Siliso,String War_Memorial_Park,String St_Andrew_Cathedral,String Changi_Chapel_Museum){

        this.From = From;
        this.Marina_Bay_Sand = Marina_Bay_Sand;
        this.Bukit_Chandu = Bukit_Chandu;
        this.Ford_Factory = Ford_Factory;
        this.Labrador_Park = Labrador_Park;
        this.National_Museum_Of_Singapore = National_Museum_Of_Singapore;
        this.Ford_Canning_Hill = Ford_Canning_Hill;
        this.Kranji_War_Memorial = Kranji_War_Memorial;
        this.Fort_Siliso = Fort_Siliso;
        this.War_Memorial_Park = War_Memorial_Park;
        this.St_Andrew_Cathedral = St_Andrew_Cathedral;
        this.Changi_Chapel_Museum = Changi_Chapel_Museum;
    }

    public String getTo (String Choice){
        switch (Choice){
            case "Marina Bay Sands":
                return Marina_Bay_Sand;
            case "Bukit Chandu":
                return Bukit_Chandu;
            case "Former Ford Factory":
                return Ford_Factory;
            case "Labrador Park":
                return Labrador_Park;
            case "Singapore National Museum":
                return National_Museum_Of_Singapore;
            case "Fort Canning Hill":
                return Ford_Canning_Hill;
            case "Kranji War Memorial":
                return Kranji_War_Memorial;
            case "Fort_Siliso":
                return Fort_Siliso;
            case "Civilian War Memorial":
                return War_Memorial_Park;
            case "St Andrew's Cathedral":
                return St_Andrew_Cathedral;
            case "Changi Chapel & Museum":
                return Changi_Chapel_Museum;
            default:
                return "Wrong Place";
        }

    }

    public String getFrom() {



        return From;
    }

    public void setFrom(String from) {
        From = from;
    }

    public String getMarina_Bay_Sand() {
        return Marina_Bay_Sand;
    }

    public void setMarina_Bay_Sand(String marina_Bay_Sand) {
        Marina_Bay_Sand = marina_Bay_Sand;
    }

    public String getBukit_Chandu() {
        return Bukit_Chandu;
    }

    public void setBukit_Chandu(String bukit_Chandu) {
        Bukit_Chandu = bukit_Chandu;
    }

    public String getFord_Factory() {
        return Ford_Factory;
    }

    public void setFord_Factory(String ford_Factory) {
        Ford_Factory = ford_Factory;
    }

    public String getLabrador_Park() {
        return Labrador_Park;
    }

    public void setLabrador_Park(String labrador_Park) {
        Labrador_Park = labrador_Park;
    }

    public String getNational_Museum_Of_Singapore() {
        return National_Museum_Of_Singapore;
    }

    public void setNational_Museum_Of_Singapore(String national_Museum_Of_Singapore) {
        National_Museum_Of_Singapore = national_Museum_Of_Singapore;
    }

    public String getFord_Canning_Hill() {
        return Ford_Canning_Hill;
    }

    public void setFord_Canning_Hill(String ford_Canning_Hill) {
        Ford_Canning_Hill = ford_Canning_Hill;
    }

    public String getKranji_War_Memorial() {
        return Kranji_War_Memorial;
    }

    public void setKranji_War_Memorial(String kranji_War_Memorial) {
        Kranji_War_Memorial = kranji_War_Memorial;
    }

    public String getFort_Siliso() {
        return Fort_Siliso;
    }

    public void setFort_Siliso(String fort_Siliso) {
        Fort_Siliso = fort_Siliso;
    }

    public String getWar_Memorial_Park() {
        return War_Memorial_Park;
    }

    public void setWar_Memorial_Park(String war_Memorial_Park) {
        War_Memorial_Park = war_Memorial_Park;
    }

    public String getSt_Andrew_Cathedral() {
        return St_Andrew_Cathedral;
    }

    public void setSt_Andrew_Cathedral(String st_Andrew_Cathedral) {
        St_Andrew_Cathedral = st_Andrew_Cathedral;
    }

    public String getChangi_Chapel_Museum() {
        return Changi_Chapel_Museum;
    }

    public void setChangi_Chapel_Museum(String changi_Chapel_Museum) {
        Changi_Chapel_Museum = changi_Chapel_Museum;
    }
}
