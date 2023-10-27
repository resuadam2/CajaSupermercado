package com.pmul.cajasupermercado;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.pmul.cajasupermercado.model.Producto;

import java.util.ArrayList;

public class ProductosAdapter extends BaseAdapter {
    private Context context;
    private ArrayList<Producto> productos;
    private int layout;

    public ProductosAdapter(Context contexto, int layout, ArrayList<Producto> productos) {
        this.context = contexto;
        this.layout = layout;
        this.productos = productos;
    }

    @Override
    public int getCount() {
        return productos.size();
    }

    @Override
    public Producto getItem(int position) {
        return productos.get(position);
    }

    @Override
    public long getItemId(int position) {
        return productos.get(position).getId();
    }

    @Override
    public View getView(int position, View view, ViewGroup parent) {
        View v = view;

        if(v == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            v = inflater.inflate(layout, null);
        }

        Producto producto = getItem(position);
        TextView nombre = (TextView) v.findViewById(R.id.lvProductsName);
        TextView cant = (TextView) v.findViewById(R.id.lvProductsCant);
        TextView precio_uni = (TextView) v.findViewById(R.id.lvProductsPriceUni);
        TextView precio_total = (TextView) v.findViewById(R.id.lvProductsPriceTotal);

        nombre.setText(producto.getName());
        cant.setText(producto.getStock()); //Esto es la cantidad del ticket en el caso del carrito
        precio_uni.setText(Double.toString(producto.getPrice()));
        precio_total.setText(Double.toString(producto.getPrice()*producto.getStock()));

        return v;
    }
}
