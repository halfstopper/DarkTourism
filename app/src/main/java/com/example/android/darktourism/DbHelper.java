package com.example.android.darktourism;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Build;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

public class DbHelper extends SQLiteOpenHelper{

    private static String DB_PATH = "";
    private static String DB_NAME = "Travel_Place.db";
    private SQLiteDatabase mDataBase;
    private Context mContext ;

    public DbHelper(Context context) {
        super(context, DB_NAME, null, 1);
        if (Build.VERSION.SDK_INT>=17){
            DB_PATH = context.getApplicationInfo().dataDir + "/databases/";
        }
        else{
            DB_PATH = "/data/data/"+context.getPackageName()+"/databases";
        }
        mContext =context;
    }

    @Override
    public synchronized void close() {
        if (mDataBase!=null){
            mDataBase.close();
        }
        super.close();

    }

    private boolean checkDataBase(){
        SQLiteDatabase tempDB = null;
        try{
            String path = DB_PATH+DB_NAME;
            tempDB = SQLiteDatabase.openDatabase(path,null,SQLiteDatabase.OPEN_READWRITE);

        }catch (Exception e){
            e.printStackTrace();
        }

        if (tempDB!=null){
            tempDB.close();
        }

        return tempDB!=null?true:false;
    }

    public void copyDataBase(){

        try {
            InputStream myInput = mContext.getAssets().open(DB_NAME);
            String OutputFileName = DB_PATH+DB_NAME;
            OutputStream myOutput = new FileOutputStream(OutputFileName);
            byte[]buffer = new byte[1024];
            int length;
            while((length=myInput.read(buffer))>0){
                myOutput.write(buffer,0,length);
            }
            myOutput.flush();
            myOutput.close();
            myInput.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void openDataBase(){
        String path = DB_PATH+DB_NAME;
        mDataBase = SQLiteDatabase.openDatabase(path,null,SQLiteDatabase.OPEN_READWRITE);
    }

    public void createDataBase(){
        boolean isDBExist = checkDataBase();
        if (isDBExist){

        }
        else{
            this.getReadableDatabase();
            try{
                copyDataBase();
            }
            catch(Exception ex){

            }
        }
    }

    //Select All Data From PlaceRecord
    public List<Places> getAllPlaces(){
        List<Places> temp = new ArrayList<Places>();
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor c;
        try{
            c=db.rawQuery("SELECT * FROM PLACERECORD",null);
            if (c==null) return null;
            c.moveToFirst();
            do {
                Places places = new Places(c.getString(c.getColumnIndex("PlaceName")),c.getString(c.getColumnIndex("Address")),c.getString(c.getColumnIndex("Remarks")),c.getString(c.getColumnIndex("Coordinate")));
                temp.add(places);
            }while (c.moveToNext());
            c.close();
        }catch (Exception e){
            e.printStackTrace();
        }
        return temp;
    }

    //Select All Data From TravelTable
    public List<Travel> getAllTravel(){
        List<Travel> temp = new ArrayList<Travel>();
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor c;
        try{
            c=db.rawQuery("SELECT * FROM TRAVELTABLE",null);
            if (c==null) return null;
            c.moveToFirst();
            do {
                Travel travel = new Travel(c.getString(c.getColumnIndex("From")),c.getString(c.getColumnIndex("Marina Bay Sands")),c.getString(c.getColumnIndex("Bukit Chandu")),c.getString(c.getColumnIndex("Ford Factory")),
                        c.getString(c.getColumnIndex("Labrador Park")),c.getString(c.getColumnIndex("National Museum Of Singapore")),c.getString(c.getColumnIndex("Ford Canning Hill")),c.getString(c.getColumnIndex("Kranji War Memorial")),
                        c.getString(c.getColumnIndex("Fort Siliso")),c.getString(c.getColumnIndex("War Memorial Park")),c.getString(c.getColumnIndex("St Andrew's Cathedral")),c.getString(c.getColumnIndex("Changi Chapel & Museum")));


                temp.add(travel);
            }while (c.moveToNext());
            c.close();
        }catch (Exception e){
            e.printStackTrace();
        }
        return temp;
    }


    @Override
    public void onCreate (SQLiteDatabase db){

    }

    @Override
    public void onUpgrade (SQLiteDatabase db, int oldVersion, int newVersion){

    }

}
