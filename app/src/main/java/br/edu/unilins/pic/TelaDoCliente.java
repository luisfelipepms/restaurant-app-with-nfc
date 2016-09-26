package br.edu.unilins.pic;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import java.math.BigDecimal;

public class TelaDoCliente extends AppCompatActivity {
    String numero_mesa;
    String valor = "0.00";
    TextView txt_TotalGasto;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tela_do_cliente);
        ImageButton btn_RealizarPedido = (ImageButton)findViewById(R.id.btn_RealizarPedido);
        ImageButton btn_FecharPedido = (ImageButton)findViewById(R.id.btn_FecharPedido);
        TextView txt_NumeroMesa = (TextView)findViewById(R.id.txt_NumeroMesa);
        txt_TotalGasto = (TextView)findViewById(R.id.txt_TotalGasto);

        Bundle extras = getIntent().getExtras();
        if(extras!=null){
            numero_mesa = extras.getString("parametro_numero_mesa");
        }

        txt_NumeroMesa.setText("Mesa " + numero_mesa);


        btn_RealizarPedido.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(TelaDoCliente.this, Pedidos.class);

                startActivityForResult(intent, 2);
            }
        });

        btn_FecharPedido.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogInterface.OnClickListener diaOnClickListener = new DialogInterface.OnClickListener(){
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        switch (which){
                            case DialogInterface.BUTTON_POSITIVE:
                                startActivity(new Intent(TelaDoCliente.this, FecharPedido.class).putExtra("parametro_total", valor));
                                finish();
                                break;

                            case DialogInterface.BUTTON_NEGATIVE:
                                break;
                        }
                    }
                };
                AlertDialog.Builder builder = new AlertDialog.Builder(TelaDoCliente.this);
                builder.setMessage("Tem certeza?").setPositiveButton("Sim", diaOnClickListener).setNegativeButton("Não", diaOnClickListener).show();
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if(resultCode == 2){
            valor = somaTotal(valor, data.getStringExtra("VALOR")).replace(".", ",");
            txt_TotalGasto.setText("Total gasto até o momento: R$" + valor);
        }else{
            Toast.makeText(this, "Não ok", Toast.LENGTH_SHORT).show();
        }

        super.onActivityResult(requestCode, resultCode, data);
    }

    private String somaTotal(String valorAntigo, String valorNovo){

        valorAntigo = valorAntigo.replace(',', '.');
        valorNovo = valorNovo.replace(',', '.');

        BigDecimal valorAntigoSoma = new BigDecimal(valorAntigo);
        BigDecimal valorNovoSoma = new BigDecimal(valorNovo);

        String retorno = valorAntigoSoma.add(valorNovoSoma).toString();

        return String.valueOf(retorno);

    }


}
