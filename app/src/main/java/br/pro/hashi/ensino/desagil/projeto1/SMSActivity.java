package br.pro.hashi.ensino.desagil.projeto1;

import android.provider.Telephony;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telephony.PhoneNumberUtils;
import android.telephony.SmsManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class SMSActivity extends AppCompatActivity {

    private void showToast(String text){
        Toast toast = Toast.makeText(this, text, Toast.LENGTH_SHORT);

        toast.show();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sms);

        TextView textMassage = findViewById(R.id.text_mostrado);
        Button buttonContact = findViewById(R.id.button_contact);
        Bundle extras = getIntent().getExtras();

        String message = extras.getString("palavra");
        textMassage.setText(message);

        buttonContact.setOnClickListener((view) -> {

            //The key argument here must match that used in the other activity
            if (message.isEmpty()) {
                    showToast("Mensagem inválida!");
                    return;
            }

            String phone = buttonContact.getText().toString();

            if (!PhoneNumberUtils.isGlobalPhoneNumber(phone)) {
                showToast("Número inválido!");
                return;
            }

                SmsManager manager = SmsManager.getDefault();
                manager.sendTextMessage(phone, null, message, null, null);
        });
    }
}
