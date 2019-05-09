package br.pro.hashi.ensino.desagil.projeto1;


import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telephony.PhoneNumberUtils;
import android.telephony.SmsManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;


public class SMSFragment extends Fragment {
    private String[] nomeArray = {"Abel","Rodrigo","Thiago","Luvi","Roger"};

    private String[] contatoArray = {
            "11946225498",
            "11962861545",
            "7191670735",
            "11991237171",
            "12982426063"
    };


    public SMSFragment() {
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_sms, container, false);
    }

}
