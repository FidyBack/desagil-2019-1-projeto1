package br.pro.hashi.ensino.desagil.projeto1;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.provider.ContactsContract;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;

public class MessageActivity extends AppCompatActivity {

    private static final int REQUEST_SEND_SMS = 0;

    private void startsSMSActivity(){
        Intent intent = new Intent(this, SMSActivity.class);
        TextView palavraTela = findViewById(R.id.text_mostrado);
        intent.putExtra("palavra", palavraTela.getText().toString());
        startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message);

        // ==== Lista de Palavras ====

        TextView palavraTela = findViewById(R.id.text_mostrado);
        Button buttonDia = findViewById(R.id.button_bom_dia);
        Button buttonNoite = findViewById(R.id.button_boa_noite);
        Button buttonAgua = findViewById(R.id.buttom_agua);
        Button buttonComida = findViewById(R.id.buttom_comida);
        Button buttonDormir = findViewById(R.id.buttom_dormir);

        buttonDia.setOnClickListener((view) -> {
            String content = buttonDia.getText().toString();
            palavraTela.setText(palavraTela.getText().toString() + " " + content);
        });

        buttonNoite.setOnClickListener((view) -> {
            String content = buttonNoite.getText().toString();
            palavraTela.setText(palavraTela.getText().toString() + " " + content);
        });

        buttonAgua.setOnClickListener((view) -> {
            String content = buttonAgua.getText().toString();
            palavraTela.setText(palavraTela.getText().toString() + " " + content);
        });

        buttonComida.setOnClickListener((view) -> {
            String content = buttonComida.getText().toString();
            palavraTela.setText(palavraTela.getText().toString() + " " + content);
        });

        buttonDormir.setOnClickListener((view) -> {
            String content = buttonDormir.getText().toString();
            palavraTela.setText(palavraTela.getText().toString() + " " + content);
        });

        // ==== SMS ====

        Button buttonPermissionSMS = findViewById(R.id.button_getPermissionSMS);

        buttonPermissionSMS.setOnClickListener((view) -> {
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.SEND_SMS) == PackageManager.PERMISSION_GRANTED) {
                startsSMSActivity();
            } else {

                String[] permissions = new String[]{
                        Manifest.permission.SEND_SMS,
                };

                ActivityCompat.requestPermissions(this, permissions, REQUEST_SEND_SMS);
            }
        });
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == REQUEST_SEND_SMS && grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            startsSMSActivity();
        }
    }
}

