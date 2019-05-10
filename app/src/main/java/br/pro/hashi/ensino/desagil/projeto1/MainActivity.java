package br.pro.hashi.ensino.desagil.projeto1;
import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;


public class MainActivity extends AppCompatActivity {
    private static final int REQUEST_SEND_SMS = 0;

    private void startsMessageActivity() {
        Intent intent = new Intent(this, MessageActivity.class);
        TextView palavraTela = findViewById(R.id.text_mostrado);
        intent.putExtra("palavramessage", palavraTela.getText().toString());
        startActivity(intent);
    }

    private void startsSMSActivity() {
        Intent intent = new Intent(this, SMSActivity.class);
        TextView palavraTela = findViewById(R.id.text_mostrado);
        intent.putExtra("palavrasms", palavraTela.getText().toString());
        startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//        TextView textMassage = findViewById(R.id.text_mostrado);
//        Bundle extras = getIntent().getExtras();
//        String message = extras.getString("palavramain");
//        textMassage.setText(message);

        Button buttonMessageActivity = findViewById(R.id.button_goListActivity);
        Button buttonSMSActivity = findViewById(R.id.button_goSMSActivity);

        buttonMessageActivity.setOnClickListener((view) -> startsMessageActivity());
        buttonSMSActivity.setOnClickListener((view) -> startsSMSActivity());

        buttonSMSActivity.setOnClickListener((view)-> {
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
