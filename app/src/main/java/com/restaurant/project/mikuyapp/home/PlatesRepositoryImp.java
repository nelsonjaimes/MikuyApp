package com.restaurant.project.mikuyapp.home;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.annotation.NonNull;

import com.restaurant.project.mikuyapp.domain.model.mikuy.response.ListPlateResponseEntity;
import com.restaurant.project.mikuyapp.menutoday.model.Plate;
import com.restaurant.project.mikuyapp.storage.sqlite.SqlGlobal;
import com.restaurant.project.mikuyapp.storage.sqlite.SqlLiteHelper;

import java.util.ArrayList;
import java.util.List;

public class PlatesRepositoryImp implements PlatesRepository {

    private final SQLiteDatabase sqLiteDatabase;
    public PlatesRepositoryImp(Context context) {
        SqlLiteHelper sqlLiteHelper = new SqlLiteHelper(context);
        sqLiteDatabase = sqlLiteHelper.getWritableDatabase();
    }

    @Override
    public void savePlatesListSqlLite(@NonNull List<ListPlateResponseEntity.
            PlateResponseEntity> platesList) {
        sqLiteDatabase.execSQL(SqlGlobal.DELETE_TABLE_PLATES);
        for (ListPlateResponseEntity.PlateResponseEntity plate : platesList) {
            ContentValues contentValues = new ContentValues();
            contentValues.put(SqlGlobal.PLATE_CODE, plate.getCode());
            contentValues.put(SqlGlobal.PLATE_NAME, plate.getName());
            contentValues.put(SqlGlobal.PLATE_PRICE, plate.getPrice());
            contentValues.put(SqlGlobal.PLATE_CATEGORY, plate.getCategory());
            sqLiteDatabase.insert(SqlGlobal.TBL_PLATES, null, contentValues);
        }
    }

    @Override
    public List<Plate> getListPlates() {
        List<Plate> plateList = new ArrayList<>();
        Cursor c = sqLiteDatabase.rawQuery(SqlGlobal.PLATES_LIST_SENTENCE, null);
        if (c.moveToFirst()) {
            do {
                String code = c.getString(0);
                String name = c.getString(1);
                float price = c.getFloat(2);
                String categori = c.getString(3);
                plateList.add(new Plate(code, name, price, categori));
            } while (c.moveToNext());
        }
        c.close();
        return plateList;
    }

    @Override
    public void closeConnection() {
        sqLiteDatabase.close();
    }
}
