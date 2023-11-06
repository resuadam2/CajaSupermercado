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

    protected TextView aPagar, tvCambio; // total a pagar y cambio

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

        tvCambio = (TextView) this.findViewById(R.id.tvCambio);

        cambios = new ArrayList<String>();

        adaptador = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, cambios);

        listaCambio.setAdapter(adaptador);

        Bundle bundle = getIntent().getExtras();
        aPagar = (TextView) this.findViewById(R.id.tvTotalImporte);
        aPagar.setText(Double.toString(bundle.getDouble("total")));

        calcular.setOnClickListener(v -> calcularCambio());

        if(cantidadPagada.getText().length() == 0) calcular.setEnabled(false);

        // TODO Limitamos el editText cantidadPagada para que solo admita dos decimales usando un inputFilter

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
        tvCambio.setText(this.getString(R.string.cambio));
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
                tvCambio.setText(this.getString(R.string.cambio) + ": 0");
                return;
            } else {
                double cambio = pago - total;
                tvCambio.setText(this.getString(R.string.cambio) + ": " + Double.toString(cambio/100)); // Mostramos el cambio en euros
                int cambioEntero = (int) cambio;
                calcularCambioEntero(cambioEntero); // Calculamos el cambio en billetes y monedas
                adaptador.notifyDataSetChanged();
            }
        }

    }

    /**
     * Calcula el cambio a devolver al cliente y añade cada billete al ArrayList
     * @param cambioCents cambio en centimos
     */
    private void calcularCambioEntero(int cambioCents) {
        if (cambioCents >= 50000) {
            cambios.add("Billetes de 500€: " + cambioCents/50000);
            cambioCents = cambioCents % 50000;
        }
        if (cambioCents >= 20000) {
            cambios.add("Billetes de 200€: " + cambioCents/20000);
            cambioCents = cambioCents % 20000;
        }
        if (cambioCents >= 10000) {
            cambios.add("Billetes de 100€: " + cambioCents/10000);
            cambioCents = cambioCents % 10000;
        }
        if (cambioCents >= 5000) {
            cambios.add("Billetes de 50€: " + cambioCents/5000);
            cambioCents = cambioCents % 5000;
        }
        if (cambioCents >= 2000) {
            cambios.add("Billetes de 20€: " + cambioCents/2000);
            cambioCents = cambioCents % 2000;
        }
        if (cambioCents >= 1000) {
            cambios.add("Billetes de 10€: " + cambioCents/1000);
            cambioCents = cambioCents % 1000;
        }
        if (cambioCents >= 500) {
            cambios.add("Billetes de 5€: " + cambioCents/500);
            cambioCents = cambioCents % 500;
        }
        if (cambioCents >= 200) {
            cambios.add("Monedas de 2€: " + cambioCents/200);
            cambioCents = cambioCents % 200;
        }
        if (cambioCents >= 100) {
            cambios.add("Monedas de 1€: " + cambioCents/100);
            cambioCents = cambioCents % 100;
        }
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

}
