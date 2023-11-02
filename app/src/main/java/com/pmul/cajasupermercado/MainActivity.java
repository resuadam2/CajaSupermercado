package com.pmul.cajasupermercado;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;

import com.pmul.cajasupermercado.db.DBManager;
import com.pmul.cajasupermercado.model.Producto;

public class MainActivity extends AppCompatActivity {
    private ArrayList<Producto> productos;
    private ProductosAdapter adaptadorProductos;

    private DBManager dbManager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ListView lvProductos = (ListView) this.findViewById(R.id.lvProductos);

        dbManager = new DBManager(this);

        this.productos = new ArrayList<>();
        productos = dbManager.getAllCarrito();
        this.adaptadorProductos = new ProductosAdapter(
                this,
                R.layout.lvproductos,
                this.productos
        );

        lvProductos.setOnItemClickListener((parent, view, position, id) -> editProducto());

        lvProductos.setOnLongClickListener(v -> deleteProducto());

        Button btAdd = this.findViewById(R.id.btAdd);
        btAdd.setOnClickListener(v -> addProducto());

        Button btCheckout = this.findViewById(R.id.btCheckout);
        btCheckout.setOnClickListener(v -> checkout());

    }

    private void checkout() {
    }

    /**
     * inicia una nueva actividad addproducttocarritoactivity para añadir un nuevo producto a la lista como resultado de la
     * otra actividad  (startactivityforresult)
     */
    private void addProducto() {
        this.startActivityForResult(new Intent(this, AddProductToCarritoActivity.class), 1);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode == RESULT_OK) {
            adaptadorProductos.notifyDataSetChanged();
            updateCarrito();
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
        return total;
    }

    /**
     * Método que actualiza el textView con el precio total del carrito
     */
    private void updateCarrito() {
        TextView tvTotal = (TextView) this.findViewById(R.id.total);
        tvTotal.setText(Double.toString(totalCarrito()));
    }

    private boolean deleteProducto() {
        return false;
    }

    private void editProducto() {
    }
}