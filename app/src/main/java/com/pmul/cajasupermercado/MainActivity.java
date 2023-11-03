package com.pmul.cajasupermercado;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import com.pmul.cajasupermercado.db.DBManager;
import com.pmul.cajasupermercado.model.Producto;

public class MainActivity extends AppCompatActivity {
    private ArrayList<Producto> productos;
    private CarritoAdapter adaptadorProductos;

    private DBManager dbManager;

    protected static final int CODIGO_ADICION_PRODUCT = 100;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ListView lvProductos = (ListView) this.findViewById(R.id.lvProductos);

        dbManager = new DBManager(this);

        this.productos = new ArrayList<>();
        this.productos = dbManager.getAllCarrito();
        this.adaptadorProductos = new CarritoAdapter(
                this,
                R.layout.lvproductos,
                this.productos
        );

        lvProductos.setAdapter(adaptadorProductos);

        lvProductos.setOnItemClickListener((parent, view, position, id) -> editProducto());

        lvProductos.setOnLongClickListener(v -> deleteProducto());

        Button btAdd = this.findViewById(R.id.btAdd);
        btAdd.setOnClickListener(v -> addProducto());

        Button btCheckout = this.findViewById(R.id.btCheckout);
        btCheckout.setOnClickListener(v -> checkout());

        updateCarrito();
    }

    @Override
    protected void onStart() {
        super.onStart();
        Toast.makeText(this, "ejecutando el onStart", Toast.LENGTH_SHORT).show();
        updateCarrito();
    }

    private void checkout() {
    }

    /**
     * inicia una nueva actividad addproducttocarritoactivity para añadir un nuevo producto a la lista como resultado de la
     * otra actividad  (startactivityforresult)
     */
    private void addProducto() {
        this.startActivityForResult(new Intent(this, AddProductToCarritoActivity.class), CODIGO_ADICION_PRODUCT);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(resultCode == RESULT_OK && requestCode == CODIGO_ADICION_PRODUCT) {
            Producto p = new Producto(
                    data.getExtras().getInt("id"),
                    data.getExtras().getString("name"),
                    data.getExtras().getInt("quantity"),
                    data.getExtras().getDouble("price")
            );

            if(this.productos.contains(p)) {
                this.productos.get(this.productos.indexOf(p)).setStock(p.getStock());
            } else {
                this.productos.add(p);
            }
            this.adaptadorProductos.notifyDataSetChanged();
            this.updateCarrito();
        }
    }

    /**
     * Método que recorre todos los elementos del ArrayList con los productos del carrito y suma los
     * subtotales para calcular el total a pagar
     */
    private double totalCarrito() {
        double total = 0;
        for(Producto p: productos) {
            total += p.getStock() * p.getPrice();
        }
        return Math.round(total * 100.0) / 100.0;
    }

    /**
     * Método que actualiza el textView con el precio total del carrito
     */
    private void updateCarrito() {
        this.productos = dbManager.getAllCarrito();
        this.adaptadorProductos.notifyDataSetChanged();
        TextView tvTotal = (TextView) this.findViewById(R.id.total);
        tvTotal.setText(Double.toString(totalCarrito()));
    }

    private boolean deleteProducto() {
        return false;
    }

    private void editProducto() {
    }
}