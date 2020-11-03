package com.koaca.wmssystem;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class MemoDbHelper extends SQLiteOpenHelper {
    private static MemoDbHelper sInstance;
    private static final int DB_VERSION=1;
    public static final String DB_NAME="Wms.db";
    private static final String SQL_CREATE_ENTRIES=
            String.format("CREATE TABLE %s (%s INTEGER PRIMARY KEY AUTOINCREMENT ,%s TEXT ,%s INTEGER,%s TEXT,%s TEXT)",
                    MemoContract.MemoEntry.TABLE_NAME,
                    MemoContract.MemoEntry._ID,
                    MemoContract.MemoEntry.CARGO,
                    MemoContract.MemoEntry.OUTSOURCING,
                    MemoContract.MemoEntry.EQUIP,
                    MemoContract.MemoEntry.ETC);

    private static final String SQL_DELETE_ENTRIES=
            "drop table if exists "+MemoContract.MemoEntry.TABLE_NAME;
    public static MemoDbHelper getInstance(Context context){
        if(sInstance==null){
            sInstance=new MemoDbHelper(context);
        }
        return sInstance;
    }
    public MemoDbHelper(@Nullable Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }




    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_ENTRIES);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(SQL_DELETE_ENTRIES);
    }
}
