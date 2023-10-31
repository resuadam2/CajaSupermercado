package com.pmul.cajasupermercado;

import android.content.Context;
import android.graphics.Color;
import android.os.Build;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.pmul.cajasupermercado.model.Producto;

import java.text.DecimalFormat;
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

        TextView lblStock = (TextView) v.findViewById(R.id.lblStock);
        lblStock.setText(R.string.stock);
        TextView lblWStock = (TextView) v.findViewById(R.id.lblW_Stock);
        lblWStock.setText(R.string.w_stock);

        Producto producto = getItem(position);
        TextView nombre = (TextView) v.findViewById(R.id.lvProductsName);
        TextView stock = (TextView) v.findViewById(R.id.lvProductsCant);
        TextView precio_uni = (TextView) v.findViewById(R.id.lvProductsPriceUni);
        TextView w_stock = (TextView) v.findViewById(R.id.lvProductsPriceTotal);

        nombre.setText(producto.getName());
        stock.setText(Integer.toString(producto.getStock())); //Esto es la cantidad del ticket en el caso del carrito
        // Crea un objeto DecimalFormat para formatear el número
        DecimalFormat formato = new DecimalFormat("#.##");
        precio_uni.setText(formato.format(producto.getPrice()) + context.getString(R.string.euro));
        w_stock.setText(formato.format(producto.getW_stock()));

        if(producto.getStock() < producto.getW_stock()) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                stock.setTextColor(context.getColor(R.color.warning_stock));
            } else {
                stock.setTextColor(Color.RED);
            }
        } else {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                stock.setTextColor(context.getColor(R.color.white));
            } else {
                stock.setTextColor(Color.WHITE);
            }
        }

        return v;
    }
}
