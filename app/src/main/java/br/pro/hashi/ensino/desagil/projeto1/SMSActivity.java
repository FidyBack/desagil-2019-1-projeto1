package br.pro.hashi.ensino.desagil.projeto1;

import android.content.Intent;
import android.provider.Telephony;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telephony.PhoneNumberUtils;
import android.telephony.SmsManager;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class SMSActivity extends AppCompatActivity {
    private ListView listView;

    private String[] nomeArray = {"Abel","Rodrigo","Thiago","Luvi","Roger"};

    private String[] contatoArray = {
            "11946225498",
            "11962861545",
            "7191670735",
            "11991237171",
            "12982426063"
    };

    private void showToast(String text){
        Toast toast = Toast.makeText(this, text, Toast.LENGTH_SHORT);

        toast.show();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sms);

        CustomListAdapter whatever = new CustomListAdapter(this, nomeArray, contatoArray);
        listView = findViewById(R.id.listview_Android_Contacts);
        listView.setAdapter(whatever);
        TextView textMassage = findViewById(R.id.text_mostrado);

        Bundle extras = getIntent().getExtras();
        String message = extras.getString("palavra");
        textMassage.setText(message);


        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String message = textMassage.getText().toString();
                String phone = contatoArray[position];

                if (message.isEmpty()) {
                    showToast("Mensagem inválida!");
                    return;
                }

                if (!PhoneNumberUtils.isGlobalPhoneNumber(phone)) {
                    showToast("Número inválido!");
                    return;
                }

                SmsManager manager = SmsManager.getDefault();
                manager.sendTextMessage(phone, null, message, null, null);
            }
        });
    }
}
