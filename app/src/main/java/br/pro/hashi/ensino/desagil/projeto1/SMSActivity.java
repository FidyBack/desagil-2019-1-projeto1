package br.pro.hashi.ensino.desagil.projeto1;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.telephony.PhoneNumberUtils;
import android.telephony.SmsManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseException;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.LinkedList;


public class SMSActivity extends AppCompatActivity implements ValueEventListener {

    private ListView listView;


    private ArrayList<String> nomesContatos = new ArrayList<>();

    private void startsMainActivity() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    private void showToast(String text) {
        Toast toast = Toast.makeText(this, text, Toast.LENGTH_SHORT);
        toast.show();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sms);

        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, nomesContatos);

        listView = findViewById(R.id.listview_Android_Contacts);
        listView.setAdapter(arrayAdapter);

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference contatosFBReference = database.getReference("Contatos");

        contatosFBReference.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                String key = dataSnapshot.getKey();
                nomesContatos.add(key);
                arrayAdapter.notifyDataSetChanged();
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        Button buttonSendMessage = findViewById(R.id.button_sendMessage);


        Button buttonMainActivity = findViewById(R.id.button_goMainActivity);
        buttonMainActivity.setOnClickListener((view) -> startsMainActivity());

        TextView textMassage = findViewById(R.id.text_mostrado);
        Bundle extras = getIntent().getExtras();
        String message = extras.getString("palavrasms");
        textMassage.setText(message);

        EditText destinatario = findViewById(R.id.destinatario);

        listView.setOnItemClickListener((parent, view, position, id) -> {
            DatabaseReference phone = contatosFBReference.child(nomesContatos.get(position));

            phone.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    String text;
                    try {
                        // O método getValue recebe como parâmetro uma
                        // classe Java que representa o tipo de dado
                        // que você acredita estar lá. Se você errar,
                        // esse método vai lançar uma DatabaseException.
                        text = dataSnapshot.getValue().toString();
                    } catch (DatabaseException exception) {
                        text = "Failed to parse value";
                    }
                    destinatario.setText(text);
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });
        });

        buttonSendMessage.setOnClickListener((view) -> {
            String message1 = textMassage.getText().toString();

            if (message1.isEmpty()) {
                showToast("Mensagem inválida!");
                return;
            }

            String phone = destinatario.getText().toString();

            if (!PhoneNumberUtils.isGlobalPhoneNumber(phone)) {
                showToast("Número inválido!");
                return;
            }

            SmsManager manager = SmsManager.getDefault();
            manager.sendTextMessage(phone, null, message1, null, null);

            destinatario.setText("");
        });
    }

    @Override
    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

    }

    @Override
    public void onCancelled(@NonNull DatabaseError databaseError) {

    }
}
