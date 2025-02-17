package com.mirea.kt.ribo.homework_110;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

public class DBManager {
    private SQLiteOpenHelper sqLiteOpenHelper;

    public DBManager(SQLiteOpenHelper sqLiteOpenHelper) {
        this.sqLiteOpenHelper = sqLiteOpenHelper;
    }
    public boolean saveCarToDatabase(Car car){
        SQLiteDatabase db = this.sqLiteOpenHelper.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put("brand",car.getBrand());
        cv.put("number",car.getNumber());
        cv.put("year",car.getYear());

        long rowId = db.insert("TABLE_CARS",null,cv);
        cv.clear();
        db.close();
        return rowId != -1;
    }

    public ArrayList<Car> loadAllCarsFromDatabase(){
        ArrayList<Car> cars = new ArrayList<>();
        SQLiteDatabase db = this.sqLiteOpenHelper.getWritableDatabase();
        Cursor dbCursor = db.query("TABLE_CARS",null,null,null,null,null,null);
        if(dbCursor.moveToFirst()){
            do{
                String brand = dbCursor.getString(dbCursor.getColumnIndexOrThrow("brand"));
                String number = dbCursor.getString(dbCursor.getColumnIndexOrThrow("number"));
                int year = dbCursor.getInt(dbCursor.getColumnIndexOrThrow("year"));
                cars.add(new Car(brand,number,year));
            }while (dbCursor.moveToNext());
        }
        dbCursor.close();
        db.close();
        return cars;
    }
}
