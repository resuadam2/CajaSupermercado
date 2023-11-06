package com.pmul.cajasupermercado;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
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

    private ListView lvProductos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        lvProductos = (ListView) this.findViewById(R.id.lvProductos);

        dbManager = new DBManager(this);

        this.productos = new ArrayList<>();
        this.productos = dbManager.getAllCarrito();
        this.adaptadorProductos = new CarritoAdapter(
                this,
                R.layout.lvproductos,
                this.productos
        );

        lvProductos.setAdapter(adaptadorProductos);

        lvProductos.setOnItemClickListener((parent, view, position, id) -> editProducto(position));

        lvProductos.setOnItemLongClickListener((parent, view, position, id) -> deleteProducto(position));

        Button btAdd = this.findViewById(R.id.btAdd);
        btAdd.setOnClickListener(v -> addProducto());

        Button btCheckout = this.findViewById(R.id.btCheckout);
        btCheckout.setOnClickListener(v -> checkout());

        updateCarrito();
    }

    @Override
    protected void onStart() {
        super.onStart();
        productos.clear();
        productos.addAll(dbManager.getAllCarrito());
        adaptadorProductos.notifyDataSetChanged();
        updateCarrito();
    }

    /**
     * Método que inicia una nueva actividad checkoutactivity en la cual se introduce el dinero y se calcula el cambio
     */
    private void checkout() {
        Intent intent = new Intent(this, CheckoutActivity.class);
        intent.putExtra("total", totalCarrito());
        this.startActivity(intent);
    }

    /**
     * inicia una nueva actividad addproducttocarritoactivity para añadir un nuevo producto a la lista como resultado de la
     * otra actividad  (startactivityforresult)
     */
    private void addProducto() {
        this.startActivityForResult(new Intent(this, AddProductToCarritoActivity.class), CODIGO_ADICION_PRODUCT);
    }

    /**
     * Método que recoge el nuevo producto añadido al carrito y lo añade al ArrayList
     * @param requestCode The integer request code originally supplied to
     *                    startActivityForResult(), allowing you to identify who this
     *                    result came from.
     * @param resultCode The integer result code returned by the child activity
     *                   through its setResult().
     * @param data An Intent, which can return result data to the caller
     *               (various data can be attached to Intent "extras").
     *
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(resultCode == RESULT_OK && requestCode == CODIGO_ADICION_PRODUCT) {
            productos.clear(); // La idea de Dario funciona
            productos.addAll(dbManager.getAllCarrito());
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
        TextView tvTotal = (TextView) this.findViewById(R.id.total);
        tvTotal.setText(Double.toString(totalCarrito()));
    }

    /**
     * Método que elimina un producto del carrito
     * @param position posición del producto en el ArrayList
     * @return true si se ha eliminado correctamente, false en caso contrario
     */
    private boolean deleteProducto(int position) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(R.string.delete_product_from_carrito);
        builder.setMessage(this.getApplicationContext().getString(R.string.delete_product_from_carrito_message) + productos.get(position).getName() + "?");
        builder.setPositiveButton(R.string.delete, (dialog, which) -> {
            if(dbManager.deleteProductFromCarrito(productos.get(position).getId())){
                productos.remove(position);
                adaptadorProductos.notifyDataSetChanged();
                updateCarrito();
            } else {
                Toast.makeText(this, R.string.cant_delete_product_from_carrito, Toast.LENGTH_LONG).show();
            }
        });
        builder.setNegativeButton(R.string.cancel, null);
        builder.create().show();
        return true;
    }

    /**
     * Método que inicia un AlertDialog para editar la cantidad de un producto del carrito
     * @param position posición del producto en el ArrayList
     */
    private void editProducto(int position) {

    }

}

