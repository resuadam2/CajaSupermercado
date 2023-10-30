package com.pmul.cajasupermercado;

import android.content.Context;
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

        Producto producto = getItem(position);
        TextView nombre = (TextView) v.findViewById(R.id.lvProductsName);
        TextView cant = (TextView) v.findViewById(R.id.lvProductsCant);
        TextView precio_uni = (TextView) v.findViewById(R.id.lvProductsPriceUni);
        TextView precio_total = (TextView) v.findViewById(R.id.lvProductsPriceTotal);

        nombre.setText(producto.getName());
        cant.setText(Integer.toString(producto.getStock())); //Esto es la cantidad del ticket en el caso del carrito
        // Crea un objeto DecimalFormat para formatear el número
        DecimalFormat formato = new DecimalFormat("#.##");
        precio_uni.setText(formato.format(producto.getPrice()) + context.getString(R.string.euro));
        precio_total.setText(formato.format(producto.getPrice()*producto.getStock()) + context.getString(R.string.euro));

        return v;
    }

    public View getViewForItem(int position) {
        if (position < 0 || position >= getCount()) {
            return null; // Maneja casos fuera de rango
        }
        View itemView = getView(position, null, null);
        return itemView;
    }

    /**
     * Metodo para cambiar el fondo de la vista de un producto bajo de stock
     */
    public void setWarningStock(int position) {
        View v = getViewForItem(position);
        Log.i("v", v.getId() + "" );
        LinearLayout linear = (LinearLayout) v;
        Log.i("v", linear.getId() + "" );
        Log.i("v", linear.getBackground() + "" );

        GridLayout grid = (GridLayout) v.findViewById(R.id.grid);
        TextView stock = (TextView) v.findViewById(R.id.lvProductsCant);
        linear.setBackgroundColor(context.getResources().getColor(R.color.warning_stock));
        grid.setBackgroundColor(context.getResources().getColor(R.color.warning_stock));
        stock.setTextColor(context.getResources().getColor(R.color.warning_stock));
        Log.i("v", linear.getBackground() + "" );

    }

    public void setNormalStock(int i) {
        View v = getView(i, null, null);
        Log.i("v", v.toString() );

        GridLayout grid = (GridLayout) v.findViewById(R.id.grid);
        grid.setBackgroundColor(context.getResources().getColor(R.color.white));
    }
}
