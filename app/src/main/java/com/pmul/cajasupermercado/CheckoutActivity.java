package com.pmul.cajasupermercado;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
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

    protected EditText cantidadPagada; // cantidad pagada

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

        cantidadPagada = (EditText) this.findViewById(R.id.etPago);

        calcular = (Button) this.findViewById(R.id.btnCalcularCambio);

        cambios = new ArrayList<String>();

        adaptador = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, cambios);

        listaCambio.setAdapter(adaptador);

        Bundle bundle = getIntent().getExtras();
        aPagar = (TextView) this.findViewById(R.id.tvTotalImporte);
        aPagar.setText(Double.toString(bundle.getDouble("total")));

        calcular.setOnClickListener(v -> calcularCambio());

        if(cantidadPagada.getText().length() == 0) calcular.setEnabled(false);

        cantidadPagada.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if(s.length() == 0) calcular.setEnabled(false);
                else calcular.setEnabled(true);
            }
        });
    }

    /**
     * Calcula el cambio a devolver al cliente y actualiza el ListView
     */
    private void calcularCambio() {
        cambios.clear();
        adaptador.clear();
        adaptador.notifyDataSetChanged();
        double pago = Double.parseDouble(cantidadPagada.getText().toString());
        double total = Double.parseDouble(aPagar.getText().toString());

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
                int cambioEuros = (int) cambio;
                int cambioCents = (int) ((cambio - cambioEuros) * 100);
                cambioGrande(cambioEuros);
                cambioPeque(cambioCents);
                adaptador.notifyDataSetChanged();
            }
        }

    }

    /**
     * Calcula el cambio a devolver al cliente y añade cada moneda al ArrayList
     * @param cambioCents cambio parte decimal
     */
    private void cambioPeque(int cambioCents) {
        if(cambioCents >= 50) {
            cambios.add("Monedas de 50 cents: " + cambioCents/50);
            cambioCents = cambioCents % 50;
        }
        if(cambioCents >= 20) {
            cambios.add("Monedas de 20 cents: " + cambioCents/20);
            cambioCents = cambioCents % 20;
        }
        if(cambioCents >= 10) {
            cambios.add("Monedas de 10 cents: " + cambioCents/10);
            cambioCents = cambioCents % 10;
        }
        if(cambioCents >= 5) {
            cambios.add("Monedas de 5 cents: " + cambioCents/5);
            cambioCents = cambioCents % 5;
        }
        if(cambioCents >= 2) {
            cambios.add("Monedas de 2 cents: " + cambioCents/2);
            cambioCents = cambioCents % 2;
        }
        if(cambioCents >= 1) {
            cambios.add("Monedas de 1 cent: " + cambioCents/1);
            cambioCents = cambioCents % 1;
        }
    }

    /**
     * Calcula el cambio a devolver al cliente y añade cada billete al ArrayList
     * @param cambioEuros cambio parte entera
     */
    private void cambioGrande(int cambioEuros) {
        if (cambioEuros >= 500) {
            cambios.add("Billetes de 500€: " + cambioEuros/500);
            cambioEuros = cambioEuros % 500;
        }
        if (cambioEuros >= 200) {
            cambios.add("Billetes de 200€: " + cambioEuros/200);
            cambioEuros = cambioEuros % 200;
        }
        if (cambioEuros >= 100) {
            cambios.add("Billetes de 100€: " + cambioEuros/100);
            cambioEuros = cambioEuros % 100;
        }
        if (cambioEuros >= 50) {
            cambios.add("Billetes de 50€: " + cambioEuros/50);
            cambioEuros = cambioEuros % 50;
        }
        if (cambioEuros >= 20) {
            cambios.add("Billetes de 20€: " + cambioEuros/20);
            cambioEuros = cambioEuros % 20;
        }
        if (cambioEuros >= 10) {
            cambios.add("Billetes de 10€: " + cambioEuros/10);
            cambioEuros = cambioEuros % 10;
        }
        if (cambioEuros >= 5) {
            cambios.add("Billetes de 5€: " + cambioEuros/5);
            cambioEuros = cambioEuros % 5;
        }
        if (cambioEuros >= 2) {
            cambios.add("Monedas de 2€: " + cambioEuros/2);
            cambioEuros = cambioEuros % 2;
        }
        if (cambioEuros >= 1) {
            cambios.add("Monedas de 1€: " + cambioEuros/1);
            cambioEuros = cambioEuros % 1;
        }
    }

}
