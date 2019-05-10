package br.pro.hashi.ensino.desagil.projeto1;

import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class Codificador extends AppCompatActivity implements View.OnClickListener {
    private String armazenador = null;
    private Translator translator;
    String mensagemFinal;

    //private List<Character> mensagem = Arrays.asList();
    static int count = 0;
    //private TimerTask timer;
    private Boolean open;
    private Handler handler;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        translator = new Translator();
        open = false;
        mensagemFinal = " ";
        setContentView(R.layout.activity_main);
        Button codex = findViewById(R.id.button_morse);
        TextView tela = findViewById(R.id.text_mostrado);
        //TextView tradu = findViewById(R.id.text_traduzido);
        handler = new Handler();
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                relogio(tela, this);
                handler.postDelayed(this,1000);

            }
        };
        handler.postDelayed(runnable,1000);

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

        System.out.println(count);




    }


    @Override
    public void onClick(View v) {
    }



    public void relogio(TextView tela, Runnable runnable){
        count ++;
        System.out.println(count);
        if (open == true){

            if (count == 2){
                this.armazenador = " ";
                tela.setText(tela.getText().toString() + armazenador);
                System.out.println("Letra");
            }
            if (count == 5){
                this.armazenador = "  ";
                tela.setText(tela.getText().toString() + armazenador);
                System.out.println("Palavra");
                stopRepeatingTask(runnable);
                translate(tela);
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

    public void translate(TextView tela){
        List<Character> mensagens = new ArrayList<>();
        String str = tela.getText().toString();
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

        tela.setText(tela.getText().toString() + getMensagemFinal());
        System.out.println(getMensagemFinal());

    }

    public void setMensagemFinal(String mensagemFinal) {
        this.mensagemFinal = mensagemFinal;
    }

    public String getMensagemFinal() {
        return mensagemFinal;
    }
}