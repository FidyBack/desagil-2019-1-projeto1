package br.pro.hashi.ensino.desagil.projeto1;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        TextView palavraTela = findViewById(R.id.text_mostrado);
        Button buttonDia = findViewById(R.id.button_bom_dia);
        Button buttonNoite = findViewById(R.id.button_boa_noite);
        Button buttonAgua = findViewById(R.id.buttom_agua);
        Button buttonComida = findViewById(R.id.buttom_comida);
        Button buttonDormir = findViewById(R.id.buttom_dormir);

        buttonDia.setOnClickListener((view) -> {
            String content = buttonDia.getText().toString();
            palavraTela.setText(content);
        });

        buttonNoite.setOnClickListener((view) -> {
            String content = buttonNoite.getText().toString();
            palavraTela.setText(content);
        });

        buttonAgua.setOnClickListener((view) -> {
            String content = buttonAgua.getText().toString();
            palavraTela.setText(content);
        });

        buttonComida.setOnClickListener((view) -> {
            String content = buttonComida.getText().toString();
            palavraTela.setText(content);
        });

        buttonDormir.setOnClickListener((view) -> {
            String content = buttonDormir.getText().toString();
            palavraTela.setText(content);
        });
    }
}

