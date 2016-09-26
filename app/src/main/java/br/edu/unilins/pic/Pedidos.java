package br.edu.unilins.pic;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;


public class Pedidos extends ActionBarActivity {
    String valor;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pedidos);
        final Spinner spn_Tipos = (Spinner)findViewById(R.id.spn_Tipos);
        final Spinner spn_Pratos = (Spinner)findViewById(R.id.spn_Pratos);
        final Button btn_EnviarPedido = (Button)findViewById(R.id.btn_EnviarPedido);
        final Button btn_Cancelar = (Button)findViewById(R.id.btn_Cancelar);

        spn_Tipos.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position == 0) {
                    ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(Pedidos.this, R.array.array_Bebidas, android.R.layout.simple_spinner_item);
                    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    spn_Pratos.setAdapter(adapter);
                } else if (spn_Tipos.getSelectedItemPosition() == 1) {
                    ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(Pedidos.this, R.array.array_Pizzas, android.R.layout.simple_spinner_item);
                    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    spn_Pratos.setAdapter(adapter);
                } else if (spn_Tipos.getSelectedItemPosition() == 2) {
                    ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(Pedidos.this, R.array.array_Lanches, android.R.layout.simple_spinner_item);
                    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    spn_Pratos.setAdapter(adapter);
                } else if (spn_Tipos.getSelectedItemPosition() == 3) {
                    ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(Pedidos.this, R.array.array_Porcoes, android.R.layout.simple_spinner_item);
                    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    spn_Pratos.setAdapter(adapter);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        spn_Pratos.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                valor = parent.getItemAtPosition(position).toString();

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        btn_EnviarPedido.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String[] valorSeparado = valor.split("\\$");
                String valor2 = valorSeparado[1];
                Intent intent = new Intent();
                intent.putExtra("VALOR", valor2);
                setResult(2, intent);
                finish();
            }
        });

        btn_Cancelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        }

}



