package com.restaurant.project.mikuyapp.storage.sqlite;

public class SqlGlobal {

    /* BD*/
    public static final String BD_NOMBRE = "mikuyBD";
    /* Tables*/
    public static final String TBL_PLATES = "tbl_plates";
    public static final String TBL_RESERVATION = "tbl_reservation";
    // Atributes Plates
    public static final String PLATE_CODE = "code";
    public static final String PLATE_NAME = "name";
    public static final String PLATE_PRICE = "price";
    public static final String PLATE_CATEGORY = "category";
    //Atributes Reservation
    public static final String RESERVE_CODE = "codereserve";
    public static final String RESERVE_DATE = "datehour";
    public static final String RESERVE_AMOUNT = "amount";
    public static final String RESERVE_STATE = "state";
    //Types Category
    public static final String CATEGORY_MENU = "menu";
    public static final String CATEGORY_LETTER = "carta";
    public static final String CATEGORY_DESSERT = "postre";
    public static final String CATEGORY_ENTRY = "entrada";

    public static final String CREATE_TBL_PLATES = " CREATE TABLE " + TBL_PLATES + "( "
            + PLATE_CODE + " VARCHAR(4) PRIMARY KEY  NOT NULL, "
            + PLATE_NAME + " VARCHAR(30) NOT NULL, "
            + PLATE_PRICE + " REAL NOT NULL , "
            + PLATE_CATEGORY + " VARCHAR(10) NOT NULL )";

    public static final String CREATE_TBL_RESERVATION = "CREATE TABLE" + TBL_RESERVATION + "("
            + RESERVE_CODE + " VARCHAR(5) PRIMARY KEY NOT NULL,"
            + RESERVE_DATE + " VARCHAR(40) NOT NULL,"
            + RESERVE_AMOUNT + " FLOAT NOT NULL,"
            + RESERVE_STATE + " INTEGER(1) NOT NULL )";

    public static final String DELETE_TBL_PLATES = "DROP TABLE IS EXITS " + TBL_PLATES;
    public static final String TRUNCATE_TABLE_PLATES = "DELETE FROM " + TBL_PLATES;
    public static final String PLATES_LIST_SENTENCE = "SELECT " + PLATE_CODE + "," + PLATE_NAME + "," +
            PLATE_PRICE + "," + PLATE_CATEGORY + " FROM " + TBL_PLATES;
}
