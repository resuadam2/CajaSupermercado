package com.pmul.cajasupermercado;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.Button;

import java.util.ArrayList;

import com.pmul.cajasupermercado.model.Producto;

public class MainActivity extends AppCompatActivity {
    private ArrayList<Producto> productos;
    private ProductosAdapter adaptadorProductos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ListView lvProductos = (ListView) this.findViewById(R.id.lvProductos);


        this.productos = new ArrayList<>();
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

    private boolean deleteProducto() {
        return false;
    }

    private void editProducto() {
    }
}