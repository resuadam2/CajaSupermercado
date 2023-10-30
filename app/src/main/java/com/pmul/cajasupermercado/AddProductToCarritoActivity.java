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

        for (int i = 0; i < this.adaptadorProductos.getCount(); i++) {
            Producto producto = this.adaptadorProductos.getItem(i);
            Log.i(producto.getName(), producto.getName()+ ":" + producto.getStock() + " " + producto.getW_stock());
            if(producto.getStock() < producto.getW_stock()){
                this.adaptadorProductos.setWarningStock(i);
                Log.i("Bajo de stock", "Bajo de stock en el if" );

            } else {
                this.adaptadorProductos.setNormalStock(i);
                Log.i("Bajo de stock", "Bajo de stock en el else" );
            }
        }
        this.adaptadorProductos.notifyDataSetChanged();
    }



    private void onCancel(View v) {
        this.setResult(RESULT_CANCELED);
        this.finish();
    }
}
