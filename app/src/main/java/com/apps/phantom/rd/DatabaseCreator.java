package com.apps.phantom.rd;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.apps.phantom.rd.database.RandDBaseHelper;

/**
 * Created by Phantom on 8/11/2016.
 */
public class DatabaseCreator {

    private static DatabaseCreator sDatabaseCreator;

    private Context mContext;

    private SQLiteDatabase mDatabase;

    public static DatabaseCreator get(Context context) {
        if ( sDatabaseCreator == null) {
            sDatabaseCreator = new DatabaseCreator(context);
        }
        return sDatabaseCreator ;
    }
    private DatabaseCreator(Context context){
        mContext = context.getApplicationContext();
        mDatabase = new RandDBaseHelper(mContext)
                .getWritableDatabase();

    }
}
