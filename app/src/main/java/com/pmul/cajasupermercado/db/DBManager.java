package com.pmul.cajasupermercado.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.Random;

public class DBManager extends SQLiteOpenHelper {

    public static final String DB_NAME = "cajaSuperDB";

    public static final int DB_VERSION = 1;

    public static final String TABLE_PRODUCTS = "products";
    public static final String PRODUCTS_COL_ID = "id";
    public static final String PRODUCTS_COL_NAME = "name";
    public static final String PRODUCTS_COL_STOCK = "stock";
    public static final String PRODUCTS_COL_PRICE = "price";
    public static final String PRODUCTS_COL_WARNING_STOCK = "w_stock";

    public static final String TABLE_CARRITO = "carrito";

    public static final String CARRITO_COL_ID = "id";
    public static final String CARRITO_COL_CANT = "quantity";

    public DBManager(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        Log.i("DBManager", DB_NAME + " creating " + TABLE_PRODUCTS + " and " + TABLE_CARRITO);

        try {
            db.beginTransaction();
            db.execSQL("CREATE TABLE IF NOT EXISTS " +
                    TABLE_PRODUCTS + " (" +
                    PRODUCTS_COL_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    PRODUCTS_COL_NAME + " string(255) NOT NULL UNIQUE, " +
                    PRODUCTS_COL_STOCK + " INTEGER NOT NULL, " +
                    PRODUCTS_COL_PRICE + " real NOT NULL, " +
                    PRODUCTS_COL_WARNING_STOCK + " INTEGER NOT NULL )");

            db.execSQL("CREATE TABLE IF NOT EXISTS " +
                    TABLE_CARRITO + " (" +
                    CARRITO_COL_ID + " INTEGER PRIMARY KEY, " +
                    CARRITO_COL_CANT + " INTEGER NOT NULL, " +
                    " FOREIGN KEY (" + CARRITO_COL_ID + ") REFERENCES "+
                     TABLE_PRODUCTS + "(" + PRODUCTS_COL_ID + "))");
            db.setTransactionSuccessful();
            createBasics();

        } catch (SQLException exc) {
            Log.e("DBManager.onCreate", exc.getMessage());
        } finally {
            db.endTransaction();
        }
    }

    private void createBasics() {
        String [] names = {"Patata", "Tomate", "Pan", "Vino", "Leche", "Agua", "Huevos", "Harina", "Yogurt", "Chocolate"};
        Random stocks = new Random(15);
        Random prices = new Random(10);

        for(String str : names) {
            int stock_actual = stocks.nextInt(100);
            addProduct(str, stock_actual, stock_actual/10, prices.nextDouble());
        }
        addProduct("Bajo de stock", 5, 10, 5.2);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.i("DBManager", DB_NAME + " " + oldVersion + " -> " + newVersion);
        try {
            db.beginTransaction();
            db.execSQL(" DROP TABLE IF EXISTS " + TABLE_PRODUCTS);
            db.execSQL(" DROP TABLE IF EXISTS " + TABLE_CARRITO);
            db.setTransactionSuccessful();
        } catch (SQLException exc) {
            Log.e("DBManager.onUpgrade", exc.getMessage());
        } finally {
            db.endTransaction();
        }
    }

    public void addProduct(String name, int stock, int w_stock, double price) {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = null;
        ContentValues values = new ContentValues();

        values.put(PRODUCTS_COL_NAME, name);
        values.put(PRODUCTS_COL_PRICE, price);
        values.put(PRODUCTS_COL_STOCK, stock);
        values.put(PRODUCTS_COL_WARNING_STOCK, w_stock);

        try {
            db.beginTransaction();
            cursor = db.query( TABLE_PRODUCTS,
                    new String[] {PRODUCTS_COL_ID},
                    PRODUCTS_COL_NAME + "= ?",
                    new String[] { name }, null, null, null, "1");
            if(cursor.getCount() > 0 ) {
                db.update( TABLE_PRODUCTS,
                        values,
                        PRODUCTS_COL_ID + " = ?",
                        new String [] {cursor.getString(0)});
            } else {
                db.insert(TABLE_PRODUCTS,
                        null,
                        values);
            }
            db.setTransactionSuccessful();
        } catch (SQLException exc) {
            Log.e("DBManager.addProducto", exc.getMessage());
        } finally {
            cursor.close();
            db.endTransaction();
        }
    }

    public Cursor getAllProducts() {
        SQLiteDatabase db = this.getReadableDatabase();

        return db.query(DBManager.TABLE_PRODUCTS, null, null, null, null, null, PRODUCTS_COL_NAME)
    }
}
