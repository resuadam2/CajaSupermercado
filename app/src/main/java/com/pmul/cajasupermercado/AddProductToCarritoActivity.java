package com.pmul.cajasupermercado;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.pmul.cajasupermercado.db.DBManager;
import com.pmul.cajasupermercado.model.Producto;

import java.util.ArrayList;


public class AddProductToCarritoActivity extends AppCompatActivity {
    private ArrayList<Producto> productos;
    private ProductosAdapter adaptadorProductos;

    private DBManager dbManager;

    private EditText etCant;

    private Button btAddToCarrito;

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

        lvProductos.setOnItemClickListener((parent, view, position, id) -> selectProduct(position));

        btAddToCarrito = (Button) this.findViewById(R.id.btAdd);
        btAddToCarrito.setEnabled(false); //Hasta que haya un elemento seleccionado no podemos añadir nada

        btAddToCarrito.setOnClickListener(v -> addProductToCarrito());

        etCant = (EditText) this.findViewById(R.id.etCant);

        etCant.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                btAddToCarrito.setEnabled(cantidadOk(s));
            }
        });
    }

    private void selectProduct(int position) {
        ProductosAdapter.setSelected(position);
        btAddToCarrito.setEnabled(cantidadOk(etCant.getText())); //Ya existe un elemento seleccionado
        adaptadorProductos.notifyDataSetChanged();
    }

    private boolean cantidadOk(Editable s) {
        if(s.length() ==  0|| Integer.parseInt(s.toString()) == 0) return false;
        else return true;
    }

    private void addProductToCarrito() {
        try {
            if (dbManager.addProductToCarrito(
                    productos.get(ProductosAdapter.selected).getId(),
                    Integer.parseInt(etCant.getText().toString())
            )) {

                Intent data = new Intent();
                data.putExtra("id", productos.get(ProductosAdapter.selected).getId());
                data.putExtra("quantity", Integer.parseInt(etCant.getText().toString()));
                data.putExtra("name", productos.get(ProductosAdapter.selected).getName());
                data.putExtra("price", productos.get(ProductosAdapter.selected).getPrice());

                AddProductToCarritoActivity.this.setResult(RESULT_OK, data);
                AddProductToCarritoActivity.this.finish();
            } else {
                Toast.makeText(this, R.string.no_stock, Toast.LENGTH_LONG).show();
            }
        } catch (NumberFormatException exception) {
            Log.e("view.AddProductCarrito", exception.getMessage());
        }
    }

    private void onCancel(View v) {
        ProductosAdapter.setSelected(-1);
        this.setResult(RESULT_CANCELED);
        this.finish();
    }
}
