package br.edu.unilins.pic;

import android.app.AlertDialog;
import android.app.PendingIntent;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.nfc.NdefMessage;
import android.nfc.NdefRecord;
import android.nfc.NfcAdapter;
import android.os.Build;
import android.os.Bundle;
import android.os.Parcelable;
import android.provider.Settings;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;


public class MainActivity extends ActionBarActivity {
    NfcAdapter nfcAdapter;
    String numeroMesa;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        nfcAdapter = NfcAdapter.getDefaultAdapter(this);
        if(nfcAdapter!=null && nfcAdapter.isEnabled()){
            Toast toast = Toast.makeText(this, "NFC Habilitado!", Toast.LENGTH_LONG);
            toast.show();
        }else {
            AlertDialog.Builder alertbox = new AlertDialog.Builder(this);
            alertbox.setTitle("NFC");
            alertbox.setMessage("NFC desabilitado! Deseja habilita-lo?");
            alertbox.setPositiveButton("Sim", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                        Intent intent = new Intent(Settings.ACTION_NFC_SETTINGS);
                        startActivity(intent);
                    } else {
                        Intent intent = new Intent(Settings.ACTION_WIRELESS_SETTINGS);
                        startActivity(intent);
                    }
                }
            });
            alertbox.setNegativeButton("Não", new DialogInterface.OnClickListener(){
                @Override
                public void onClick(DialogInterface dialog, int which) {

                }
            });
            alertbox.show();

        }

        //ImageView img_TelaInicial = (ImageView)findViewById(R.id.img_TelaInicial);
        //Button btn_abrirPedidos = (Button)findViewById(R.id.btn_abrirPedidos);
        Button btn_InserirNumeroMesa = (Button)findViewById(R.id.btn_InserirNumeroMesa);

        btn_InserirNumeroMesa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity((new Intent(MainActivity.this, InserirNumeroDaMesa.class)));
            }
        });
    }

    @Override
    protected void onNewIntent(Intent intent) {

        if(intent.hasExtra(nfcAdapter.EXTRA_TAG)){
            Parcelable[] parcelables = intent.getParcelableArrayExtra(nfcAdapter.EXTRA_NDEF_MESSAGES);

            if(parcelables!=null && parcelables.length>0){
                readTextFromMessage((NdefMessage) parcelables[0]);
            }else{
                Toast toast = Toast.makeText(this, "Sem mensagem NDEF!", Toast.LENGTH_LONG);
            }
        }

        startActivity(new Intent(MainActivity.this, TelaDoCliente.class).putExtra("parametro_numero_mesa", numeroMesa));

        super.onNewIntent(intent);
    }

    @Override
    protected void onResume() {

        Intent intent = new Intent(this, MainActivity.class);
        intent.addFlags(Intent.FLAG_RECEIVER_REPLACE_PENDING);

        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, 0);
        IntentFilter[] intentFilters = new IntentFilter[]{};

        nfcAdapter.enableForegroundDispatch(this, pendingIntent, intentFilters, null);

        super.onResume();

    }

    @Override
    protected void onPause() {

        nfcAdapter.disableForegroundDispatch(this);

        super.onPause();
    }

    private void readTextFromMessage(NdefMessage ndefMessage){
        NdefRecord[] ndefRecords = ndefMessage.getRecords();

        if(ndefRecords!=null && ndefRecords.length>0){
            NdefRecord ndefRecord = ndefRecords[0];

            String tagContent = NFC.getTextFromNdefRecord(ndefRecord);

            numeroMesa = tagContent;

        }
    }

}
