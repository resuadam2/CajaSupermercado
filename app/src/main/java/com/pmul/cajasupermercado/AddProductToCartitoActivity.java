package com.pmul.cajasupermercado;

import android.os.Bundle;
import android.widget.ListView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.pmul.cajasupermercado.model.Producto;

import java.util.ArrayList;
import android.widget.CursorAdapter;


public class AddProductToCartitoActivity extends AppCompatActivity {
    private ArrayList<Producto> productos;
    private ProductosAdapter adaptadorProductos;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_add_product_to_carrito);

        ListView lvProductos = (ListView) this.findViewById(R.id.lvProductos);


        this.productos = new ArrayList<>();
        this.adaptadorProductos = new ProductosAdapter(
                this,
                R.layout.lvproductos,
                this.productos
        );
    }

    @Override
    protected void onResume() {
        super.onResume();


    }
}
