package br.edu.unilins.pic;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

public class FecharPedido extends AppCompatActivity {
    String total;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fechar_pedido);
        TextView txt_TotalFinal = (TextView)findViewById(R.id.txt_TotalFinal);
        ImageButton btn_Finalizar = (ImageButton)findViewById(R.id.btn_Finalizar);
        Bundle extras = getIntent().getExtras();
        if(extras!=null){
            total = extras.getString("parametro_total");

        }else{
            Toast.makeText(this, "Sem parametro", Toast.LENGTH_SHORT);
        }

        txt_TotalFinal.setText("R$" + total);

        btn_Finalizar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }
}
