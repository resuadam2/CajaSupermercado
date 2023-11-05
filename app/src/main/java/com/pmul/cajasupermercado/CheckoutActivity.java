package com.pmul.cajasupermercado;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class CheckoutActivity  extends AppCompatActivity {

    protected ListView listaCambio; // lista de cambios

    protected EditText entero, decimal; // parte entera y decimal del pago

    protected TextView aPagar; // total a pagar

    protected Button calcular; // boton para calcular el cambio

    protected ArrayList<String> cambios; // lista de cambios

    protected ArrayAdapter<String> adaptador; // adaptador para la lista de cambios

    /**
     * Called when the activity is starting.  This is where most initialization
     * @param savedInstanceState If the activity is being re-initialized after
     *     previously being shut down then this Bundle contains the data it most
     *     recently supplied in {@link #onSaveInstanceState}.  <b><i>Note: Otherwise it is null.</i></b>
     *
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_checkout);

        listaCambio = (ListView) this.findViewById(R.id.listaCambio);

        entero = (EditText) this.findViewById(R.id.etPagoParteEntera);

        decimal = (EditText) this.findViewById(R.id.etPagoParteDecimal);

        calcular = (Button) this.findViewById(R.id.btnCalcularCambio);

        cambios = new ArrayList<String>();

        adaptador = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, cambios);

        listaCambio.setAdapter(adaptador);

        Bundle bundle = getIntent().getExtras();
        aPagar = (TextView) this.findViewById(R.id.tvAPagar);
        aPagar.setText(bundle.getString("total"));

        calcular.setOnClickListener(v -> calcularCambio());
    }

    /**
     * Calcula el cambio a devolver al cliente y actualiza el ListView
     */
    private void calcularCambio() {
        int pagoEntero = Integer.parseInt(entero.getText().toString());
        int pagoDecimal = Integer.parseInt(decimal.getText().toString());
        double total = Double.parseDouble(aPagar.getText().toString());
        double pago = pagoEntero + pagoDecimal/100.0;

        if (pago < total) {
            Toast.makeText(this, "El pago es menor que el total", Toast.LENGTH_SHORT).show();
            return;
        } else {
            total = total * 100;
            pago = pago * 100;
            if(pago == total) {
                Toast.makeText(this, "No hay cambio", Toast.LENGTH_SHORT).show();
                return;
            } else {
                double cambio = pago - total;
                int cambioEntero = (int) cambio;
                int cambioDecimal = (int) ((cambio - cambioEntero) * 100);
                cambioGrande(cambioEntero);
                cambioPeque(cambioDecimal);
                adaptador.notifyDataSetChanged();
            }
        }

    }

    /**
     * Calcula el cambio a devolver al cliente y añade cada moneda al ArrayList
     * @param cambioDecimal cambio parte decimal
     */
    private void cambioPeque(int cambioDecimal) {
        if(cambioDecimal >= 50) {
            cambios.add("Monedas de 50 cents: " + cambioDecimal/50);
            cambioDecimal = cambioDecimal % 50;
        } else if(cambioDecimal >= 20) {
            cambios.add("Monedas de 20 cents: " + cambioDecimal/20);
            cambioDecimal = cambioDecimal % 20;
        } else if(cambioDecimal >= 10) {
            cambios.add("Monedas de 10 cents: " + cambioDecimal/10);
            cambioDecimal = cambioDecimal % 10;
        } else if(cambioDecimal >= 5) {
            cambios.add("Monedas de 5 cents: " + cambioDecimal/5);
            cambioDecimal = cambioDecimal % 5;
        } else if(cambioDecimal >= 2) {
            cambios.add("Monedas de 2 cents: " + cambioDecimal/2);
            cambioDecimal = cambioDecimal % 2;
        } else if(cambioDecimal >= 1) {
            cambios.add("Monedas de 1 cent: " + cambioDecimal/1);
            cambioDecimal = cambioDecimal % 1;
        }
    }

    /**
     * Calcula el cambio a devolver al cliente y añade cada billete al ArrayList
     * @param cambioEntero cambio parte entera
     */
    private void cambioGrande(int cambioEntero) {
        if (cambioEntero >= 500) {
            cambios.add("Billetes de 500€: " + cambioEntero/500);
            cambioEntero = cambioEntero % 500;
        } else if (cambioEntero >= 200) {
            cambios.add("Billetes de 200€: " + cambioEntero/200);
            cambioEntero = cambioEntero % 200;
        } else if (cambioEntero >= 100) {
            cambios.add("Billetes de 100€: " + cambioEntero/100);
            cambioEntero = cambioEntero % 100;
        } else if (cambioEntero >= 50) {
            cambios.add("Billetes de 50€: " + cambioEntero/50);
            cambioEntero = cambioEntero % 50;
        } else if (cambioEntero >= 20) {
            cambios.add("Billetes de 20€: " + cambioEntero/20);
            cambioEntero = cambioEntero % 20;
        } else if (cambioEntero >= 10) {
            cambios.add("Billetes de 10€: " + cambioEntero/10);
            cambioEntero = cambioEntero % 10;
        } else if (cambioEntero >= 5) {
            cambios.add("Billetes de 5€: " + cambioEntero/5);
            cambioEntero = cambioEntero % 5;
        } else if (cambioEntero >= 2) {
            cambios.add("Billetes de 2€: " + cambioEntero/2);
            cambioEntero = cambioEntero % 2;
        } else if (cambioEntero >= 1) {
            cambios.add("Billetes de 1€: " + cambioEntero/1);
            cambioEntero = cambioEntero % 1;
        }
    }

}
