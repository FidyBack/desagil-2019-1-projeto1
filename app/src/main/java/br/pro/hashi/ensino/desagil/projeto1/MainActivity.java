package br.pro.hashi.ensino.desagil.projeto1;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener, View.OnClickListener {

    private static final int REQUEST_SEND_SMS = 0;
    private static int count = 0;
    private String armazenador = null;
    private Translator translator;
    private String mensagemFinal;
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

//       ==========NÃO TOQUE NISSO==========
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        navigationView.setNavigationItemSelectedListener(this);
//      ==========NÃO TOQUE NISSO==========

        handler = new Handler();
        translator = new Translator();
        open = false;
        mensagemFinal = "";

        Button erase = findViewById(R.id.Apagar);
        Button codex = findViewById(R.id.button_morse);
        TextView tela = findViewById(R.id.text_mostrado);
        TextView tela_morse = findViewById(R.id.text_morse);

        erase.setOnClickListener((view)-> {
            setErase(tela, tela_morse);
        });

        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                relogio(tela, this, tela_morse);
                handler.postDelayed(this, 500);
            }
        };
        handler.postDelayed(runnable, 500);

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
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
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

    public void relogio(TextView tela, Runnable runnable, TextView tela_traduzida) {
        count++;

        if (open) {
            if (count == 3) {
                this.armazenador = " ";
                tela.setText(tela.getText().toString() + armazenador);
            }

            if (count == 6) {
                this.armazenador = "  ";
                tela.setText(tela.getText().toString() + armazenador);
                stopRepeatingTask(runnable);
                translate(tela, tela_traduzida);
            }
        }
    }

    public void setErase(TextView tela_traduzida, TextView tela) {
        this.mensagemFinal = " ";
        tela_traduzida.setText(" ");
        tela.setText(" ");
    }

    public void setRelogio() {
        this.count = 0;
    }

    public void setOpen() {
        this.open = true;
    }

    void stopRepeatingTask(Runnable runnable) {
        handler.removeCallbacks(runnable);
    }

    public void translate(TextView tela, TextView tela_traduzida) {
        List<Character> mensagens = new ArrayList<>();
        String str = tela.getText().toString();
        String traduzida = tela_traduzida.getText().toString();
        String[] arrOfStr = str.split(" ", 0);
        for (String a : arrOfStr) {
            try {
                mensagens.add(translator.morseToChar(a));

            } catch (java.lang.NullPointerException ex) {
            }
        }
        StringBuilder sb = new StringBuilder();
        for (Character ch : mensagens) {
            sb.append(ch);
        }
        setMensagemFinal(sb.toString());

        tela_traduzida.setText(getMensagemFinal());
    }

    public String getMensagemFinal() {
        return " " + mensagemFinal;
    }

    public void setMensagemFinal(String mensagemFinal) {
        this.mensagemFinal = mensagemFinal;
    }
}
