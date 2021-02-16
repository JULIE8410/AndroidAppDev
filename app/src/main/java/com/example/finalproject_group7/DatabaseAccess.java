package com.example.finalproject_group7;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

public class DatabaseAccess {

    private SQLiteOpenHelper openHelper;
    private SQLiteDatabase database;
    private static DatabaseAccess instance;

    /**
     * Private constructor to aboid object creation from outside classes.
     *
     * @param context
     */
    private DatabaseAccess(Context context) {
        this.openHelper = new DatabaseOpenHelper(context);
    }

    /**
     * Return a singleton instance of DatabaseAccess.
     *
     * @param context the Context
     * @return the instance of DabaseAccess
     */
    public static DatabaseAccess getInstance(Context context) {
        if (instance == null) {
            instance = new DatabaseAccess(context);
        }
        return instance;
    }

    /**
     * Open the database connection.
     */
    public void open() {
        try{
            this.database = openHelper.getWritableDatabase();
            Log.d("DB PROJECT","Database Open");
        }catch(Exception ex){
            Log.d("DB PROJECT", "Database opening error " + ex.getMessage());
        }

    }

    /**
     * Close the database connection.
     */
    public void close() {
        if (database != null) {
            this.database.close();
        }
    }

    /**
     * Read all quotes from the database.
     *
     * @return a List of quotes
     */
    public List<String[]> getItems() {
        //List<Data> DBRecList = new ArrayList<>();
        List<String[]> DBRecList = new ArrayList<>();

        Log.d("DB DEMO", "Reading ITEM table recs error before adding data item");
        //cursor:
        // abstract byte[]	getBlob(int columnIndex);

        //  int nameFieldColumnIndex = people.getColumnIndex(PhoneLookup.DISPLAY_NAME);
        //  String contact = people.getString(nameFieldColumnIndex);

        //String date = cursor.getString(cursor.getColumnIndex("date"));


        try{
            Cursor cursor = database.rawQuery("SELECT * FROM ITEM", null);
            if(cursor != null) {  //Make it accessible only when data exists
                cursor.moveToFirst(); //Go first
                while (!cursor.isAfterLast()) { //If not the last

                    String[] eachRec=new String[3];
/*
                    Data dataObj=new Data(cursor.getInt(cursor.getColumnIndex("ItemID")),  //this corresponds to the item ID
                            cursor.getString(cursor.getColumnIndex("ItemName")),   //this corresponds to the item Name
                            cursor.getDouble(cursor.getColumnIndex("ItemPrice")),  //this corresponds to the item Price
                            cursor.getString(cursor.getColumnIndex("itemDescription")), //this corresponds to the item Description
                             cursor.getDouble(cursor.getColumnIndex("itemDiscountedPrice")), //this corresponds to the item discounted Price
                            tempBitmap);  //this corresponds to the image column
                            */
                    eachRec[0]=cursor.getString(cursor.getColumnIndex("ItemName"));
                    eachRec[1]=cursor.getString(cursor.getColumnIndex("itemDescription"));
                    eachRec[2]=cursor.getString(cursor.getColumnIndex("itemDescription"));

                    //DBRecList.add(dataObj); //Adding record to the list of string array
                    DBRecList.add(eachRec);
                    Log.d("DB Project", "add an item obj" );
                    cursor.moveToNext();

                } //end of while
                cursor.close();
            }//end of if

        }catch(Exception ex){
            Log.d("DB DEMO", "Reading ITEM table recs error" + ex.getMessage());
        } // end of catch

        return DBRecList;
    } //end of getItems();







    public List<Bitmap> getImage() {
        List<Bitmap> DBRecList = new ArrayList<>();

        //cursor:
        // abstract byte[]	getBlob(int columnIndex);

        //  int nameFieldColumnIndex = people.getColumnIndex(PhoneLookup.DISPLAY_NAME);
        //  String contact = people.getString(nameFieldColumnIndex);

        //String date = cursor.getString(cursor.getColumnIndex("date"));


        try{
            Cursor cursor = database.rawQuery("SELECT * FROM ITEMORDER", null);
            if(cursor != null) {  //Make it accessible only when data exists
                cursor.moveToFirst(); //Go first
                while (!cursor.isAfterLast()) { //If not the last

                    byte[] imageByeArray=cursor.getBlob(cursor.getColumnIndex("itemImage"));
                    Bitmap tempBitmap=BitmapFactory.decodeByteArray(imageByeArray,0,imageByeArray.length);

                    DBRecList.add(tempBitmap); //Adding record to the list of string array
                    Log.d("DB Project", "add an order obj" );
                    cursor.moveToNext();
                } //end of while

                cursor.close();
            }//end of if

        }catch(Exception ex){
            Log.d("DB DEMO", "getting image from ITEM table recs error" + ex.getMessage());
        } // end of catch

        return DBRecList;
    } //end of getItems();

}
