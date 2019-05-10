package br.pro.hashi.ensino.desagil.projeto1;
import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;


public class MainActivity extends AppCompatActivity implements View.OnClickListener{
    private static final int REQUEST_SEND_SMS = 0;
    private String armazenador = null;
    private Translator translator;
    private String mensagemFinal;
    private static int count = 0;
    private Boolean open;
    private Handler handler;

    private void startsMessageActivity() {
        Intent intent = new Intent(this, MessageActivity.class);
        TextView palavraTela = findViewById(R.id.text_morse);
        intent.putExtra("palavramessage", palavraTela.getText().toString());
        startActivity(intent);
    }

    private void startsSMSActivity() {
        Intent intent = new Intent(this, SMSActivity.class);
        TextView palavraTela = findViewById(R.id.text_morse);
        intent.putExtra("palavrasms", palavraTela.getText().toString());
        startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        handler = new Handler();
        translator = new Translator();
        open = false;
        mensagemFinal = "";

        Button codex = findViewById(R.id.button_morse);
        TextView tela = findViewById(R.id.text_mostrado);
        TextView tela_morse = findViewById(R.id.text_morse);

        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                relogio(tela, this,tela_morse);
                handler.postDelayed(this,1000);
            }
        };
        handler.postDelayed(runnable,1000);

        Button buttonMessageActivity = findViewById(R.id.button_goListActivity);
        Button buttonSMSActivity = findViewById(R.id.button_goSMSActivity);

        buttonMessageActivity.setOnClickListener((view) -> startsMessageActivity());
        buttonSMSActivity.setOnClickListener((view) -> startsSMSActivity());

        buttonSMSActivity.setOnClickListener((view) -> {
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.SEND_SMS) == PackageManager.PERMISSION_GRANTED) {
                startsSMSActivity();
            } else {

                String[] permissions = new String[]{
                        Manifest.permission.SEND_SMS,
                };
                ActivityCompat.requestPermissions(this, permissions, REQUEST_SEND_SMS);
            }
        });

        try {
            Bundle extras = getIntent().getExtras();
            String message = extras.getString("palavramain");
            tela_morse.setText(message);
        } catch (java.lang.NullPointerException ex) {

        }

        codex.setOnClickListener((view) -> {
            setOpen();
            setRelogio();
            armazenador = ".";
            tela.setText(tela.getText().toString() + armazenador);

        });

        codex.setOnLongClickListener((view) -> {
            setOpen();
            setRelogio();
            armazenador = "-";
            tela.setText(tela.getText().toString() + armazenador);
            return true;
        });
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == REQUEST_SEND_SMS && grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            startsSMSActivity();
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        handler.removeCallbacksAndMessages(null);
    }

    @Override
    public void onClick(View v) {

    }

    public void relogio(TextView tela, Runnable runnable, TextView tela_traduzida){
        count ++;

        if (open){
            if (count == 2){
                this.armazenador = " ";
                tela.setText(tela.getText().toString() + armazenador);
            }

            if (count == 5){
                this.armazenador = "  ";
                tela.setText(tela.getText().toString() + armazenador);
                stopRepeatingTask(runnable);
                translate(tela, tela_traduzida);
            }
        }
    }

    public void setRelogio(){
        this.count = 0;
    }

    public void setOpen(){
        this.open = true;
    }

    void stopRepeatingTask(Runnable runnable)
    {
        handler.removeCallbacks(runnable);
    }

    public void translate(TextView tela, TextView tela_traduzida){
        List<Character> mensagens = new ArrayList<>();
        String str = tela.getText().toString();
        String traduzida = tela_traduzida.getText().toString();
        String[] arrOfStr = str.split(" ", 0);
        for (String a : arrOfStr){
            try{
                mensagens.add(translator.morseToChar(a));

            } catch (java.lang.NullPointerException ex){
            }
        }
        StringBuilder sb = new StringBuilder();
        for (Character ch: mensagens){
            sb.append(ch);
        }
        setMensagemFinal(sb.toString());

        tela_traduzida.setText(traduzida + getMensagemFinal());
    }

    public void setMensagemFinal(String mensagemFinal) {
        this.mensagemFinal = mensagemFinal;
    }

    public String getMensagemFinal() {
        return " " + mensagemFinal;
    }
}
