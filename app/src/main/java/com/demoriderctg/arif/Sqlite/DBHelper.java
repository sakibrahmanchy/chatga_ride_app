package com.demoriderctg.arif.Sqlite;
import java.util.ArrayList;
import java.util.HashMap;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteDatabase;

public class DBHelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "ChatgaRide.db";
    public static final String CONTACTS_TABLE_NAME = "SearchHistory2";
    public static final String CONTACTS_COLUMN_ID = "id";
    public static final String CONTACTS_COLUMN_LATITUDE = "Latitude";
    public static final String CONTACTS_COLUMN_LONGITUDE = "Longitude";
    public static final String CONTACTS_COLUMN_SEARCHTIME = "SearchTime";
    public static final String CONTACTS_COLUMN_LOCATIONNAME = "LocationName";

    private HashMap hp;

    public DBHelper(Context context) {
        super(context, DATABASE_NAME , null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // TODO Auto-generated method stub
        db.execSQL(
                "CREATE TABLE SearchHistory2(\n" +
                        "   id INTEGER PRIMARY KEY AUTOINCREMENT,\n" +
                        "   LocationName           TEXT      NOT NULL,\n" +
                        "   Latitude            REAL       NOT NULL,\n" +
                        "   SearchTime        TEXT,\n" +
                        "   Longitude         REAL\n" +
                        ")"
        );
        //  crateTable();
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // TODO Auto-generated method stub
        db.execSQL("DROP TABLE IF EXISTS SearchHistory");
        onCreate(db);
    }
    public  void crateTable(){
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL(
                "CREATE TABLE SearchHistory2(\n" +
                        "   id INTEGER PRIMARY KEY AUTOINCREMENT,\n" +
                        "   LocationName           TEXT      NOT NULL,\n" +
                        "   Latitude            REAL       NOT NULL,\n" +
                        "   SearchTime        TEXT,\n" +
                        "   Longitude         REAL\n" +
                        ")"
        );
    }

    public boolean insertContact (String LocationName, double Latitude, double Longitude, String SearchTime) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues contentValues = new ContentValues();
        contentValues.put("LocationName", LocationName);
        contentValues.put("Latitude", Latitude);
        contentValues.put("Longitude", Longitude);
        contentValues.put("SearchTime", SearchTime);

        db.insert("SearchHistory2", null, contentValues);
        return true;

    }

    public Cursor getData(int id) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res =  db.rawQuery( "select * from SearchHistory2 where id="+id+"", null );
        return res;
    }



    public int numberOfRows(){
        SQLiteDatabase db = this.getReadableDatabase();
        int numRows = (int) DatabaseUtils.queryNumEntries(db, CONTACTS_TABLE_NAME);
        return numRows;
    }

    public boolean updateContact (Integer id, String LocationName, double Latitude, double Longitude, String SearchTime) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("LocationName", LocationName);
        contentValues.put("Latitude", Latitude);
        db.update("SearchHistory2", contentValues, "id = ? ", new String[] { Integer.toString(id) } );
        return true;
    }

    public Integer deleteContact (Integer id) {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete("SearchHistory2",
                "id = ? ",
                new String[] { Integer.toString(id) });
    }
    public boolean deleteAllHistory () {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("delete from "+ CONTACTS_TABLE_NAME);
        return  true;

    }

    public  Vmhistory getById (String searchText) {

        Vmhistory vmhistory = new Vmhistory();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res =  db.rawQuery( "select * from SearchHistory2 where LocationName ="+searchText, null );
        res.moveToFirst();

        while(res.isAfterLast() == false){
            vmhistory.locationName=(res.getString(res.getColumnIndex(CONTACTS_COLUMN_LOCATIONNAME)));
            vmhistory.letitude=(res.getDouble(res.getColumnIndex(CONTACTS_COLUMN_LATITUDE)));
            vmhistory.longitude=(res.getDouble(res.getColumnIndex(CONTACTS_COLUMN_LONGITUDE)));
            vmhistory.id=(res.getInt(res.getColumnIndex(CONTACTS_COLUMN_ID)));
            vmhistory.searchDate=(res.getString(res.getColumnIndex(CONTACTS_COLUMN_SEARCHTIME)));
            res.moveToNext();
        }
        return vmhistory;
    }

    public ArrayList<Vmhistory> getAllSearchList() {
        ArrayList<Vmhistory> search_list = new ArrayList<Vmhistory>();

        //hp = new HashMap();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res =  db.rawQuery( "select * from SearchHistory2", null );
        res.moveToFirst();

        while(res.isAfterLast() == false){
            Vmhistory vmhistory = new Vmhistory();
            vmhistory.locationName=(res.getString(res.getColumnIndex(CONTACTS_COLUMN_LOCATIONNAME)));
            vmhistory.letitude=(res.getDouble(res.getColumnIndex(CONTACTS_COLUMN_LATITUDE)));
            vmhistory.longitude=(res.getDouble(res.getColumnIndex(CONTACTS_COLUMN_LONGITUDE)));
            vmhistory.id=(res.getInt(res.getColumnIndex(CONTACTS_COLUMN_ID)));
            vmhistory.searchDate=(res.getString(res.getColumnIndex(CONTACTS_COLUMN_SEARCHTIME)));
            search_list.add(vmhistory);
            res.moveToNext();
        }
        return search_list;
    }
}