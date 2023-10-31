package com.pmul.cajasupermercado.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Build;
import android.util.Log;

import com.pmul.cajasupermercado.model.Producto;

import java.text.DecimalFormat;
import java.util.ArrayList;
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



    private boolean isCreating = false;
    protected SQLiteDatabase currentDB = null;
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

        } catch (SQLException exc) {
            Log.e("DBManager.onCreate", exc.getMessage());
        } finally {
            db.endTransaction();
        }
        isCreating = true;
        currentDB = db;
        createBasics();
        isCreating = false;
        currentDB = null;
    }

    @Override
    public SQLiteDatabase getWritableDatabase() {
        SQLiteDatabase db;
        if(isCreating && currentDB != null) {
            db = currentDB;
        } else {
            db = super.getWritableDatabase();
        }
        return db;
    }

    @Override
    public SQLiteDatabase getReadableDatabase() {
        SQLiteDatabase db;
        if(isCreating && currentDB != null) {
            db = currentDB;
        } else {
            db = super.getReadableDatabase();
        }
        return db;
    }

    private void createBasics() {
        String [] names = {"Patata", "Tomate", "Pan", "Vino", "Leche", "Agua", "Huevos", "Harina", "Yogurt", "Chocolate"};
        Random stocks = new Random(15);
        Random prices = new Random(10);

        for(String str : names) {
            int stock_actual = stocks.nextInt(100);
            addProduct(str, stock_actual, stock_actual/10, round(prices.nextDouble()*10));
        }
        addProduct("Bajo de stock", 5, 10, 5.2);
    }

    private double round(double value) {
      return  Math.round(value * 100.0) / 100.0;
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

    /**
     * Añade un producto a la base de datos, si ya existe actualiza el stock, está sobrecargado para reutilizar la db del onCrate
     * @param db   base de datos
     * @param name nombre del producto
     * @param stock stock actual
     * @param w_stock stock mínimo
     * @param price precio
     */
    public void addProduct(SQLiteDatabase db, String name, int stock, int w_stock, double price) {
        db = this.getWritableDatabase();
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


    /**
     * Añade un producto a la base de datos, si ya existe actualiza el stock
     * @param name nombre del producto
     * @param stock stock actual
     * @param w_stock   stock mínimo
     * @param price precio
     */
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

    public ArrayList<Producto> getAllProducts() {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(DBManager.TABLE_PRODUCTS, null, null, null, null, null, PRODUCTS_COL_NAME);
        ArrayList<Producto> productos = new ArrayList<>();
        while(cursor.moveToNext()) {
            productos.add(new Producto(cursor.getInt(0), cursor.getString(1), cursor.getInt(2), cursor.getInt(4), cursor.getDouble(3)));
        }
        cursor.close();
        return productos;
    }
}
