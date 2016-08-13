package com.apps.phantom.rd.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.apps.phantom.rd.R;
import com.apps.phantom.rd.database.RandDDbSchema.RandDTable;

/**
 * Created by Phantom on 8/11/2016.
 */
public class RandDBaseHelper extends SQLiteOpenHelper {
    private static final int VERSION = 1;
    private static final String DATABASE_NAME = "RandDBase.db";

    public RandDBaseHelper( Context context){
        super(context,DATABASE_NAME, null, VERSION);

    }

    @Override
    public void onCreate(SQLiteDatabase db){
        db.execSQL("create table " + RandDTable.NAME + "(" +
                RandDTable.Cols.ID + ", " +
                RandDTable.Cols.PREMIUM + ", " +
                RandDTable.Cols.DEDUCTIBLE + ", " +
                RandDTable.Cols.REMAINING +
                ")"
        );
    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion){

    }

}
