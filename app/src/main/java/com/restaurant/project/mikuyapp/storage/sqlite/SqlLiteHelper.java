package com.restaurant.project.mikuyapp.storage.sqlite;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class SqlLiteHelper extends SQLiteOpenHelper {
    public SqlLiteHelper(Context context) {
        super(context, SqlGlobal.BD_NOMBRE, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SqlGlobal.CREATE_TBL_PLATES);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(SqlGlobal.DELETE_TBL_PLATES);
        onCreate(db);
    }
}
