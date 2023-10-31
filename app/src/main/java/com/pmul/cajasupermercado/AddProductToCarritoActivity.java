package com.pmul.cajasupermercado;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ListView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.pmul.cajasupermercado.db.DBManager;
import com.pmul.cajasupermercado.model.Producto;

import java.util.ArrayList;


public class AddProductToCarritoActivity extends AppCompatActivity {
    private ArrayList<Producto> productos;
    private ProductosAdapter adaptadorProductos;

    private DBManager dbManager;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_add_product_to_carrito);

        ListView lvProductos = (ListView) this.findViewById(R.id.lvProductos);

        dbManager = new DBManager(this);

        this.productos = new ArrayList<>();
        this.productos = dbManager.getAllProducts();
        this.adaptadorProductos = new ProductosAdapter(
                this,
                R.layout.lvproductos,
                this.productos
        );

        lvProductos.setAdapter(this.adaptadorProductos);
    }

    @Override
    protected void onResume() {
        super.onResume();
        // Recorremos el adaptador para ver el stock de cada producto


    }



    private void onCancel(View v) {
        this.setResult(RESULT_CANCELED);
        this.finish();
    }
}
