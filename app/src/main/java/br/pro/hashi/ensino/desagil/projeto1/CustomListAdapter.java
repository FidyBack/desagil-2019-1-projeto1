package br.pro.hashi.ensino.desagil.projeto1;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class CustomListAdapter extends ArrayAdapter {
    private final Activity context;

    //Armazena o nome do contato
    private final String[] nomeContato;

    //Armazena o número do contato
    private final String[] numeroContato;

    public CustomListAdapter(Activity context, String[] nomeContatoParam, String[] numeroContatoParam){

        super(context, R.layout.listview_row , nomeContatoParam);

        this.context = context;
        this.nomeContato = nomeContatoParam;
        this.numeroContato = numeroContatoParam;

    }

    public View getView(int position, View view, ViewGroup parent) {
        LayoutInflater inflater=context.getLayoutInflater();
        View rowView=inflater.inflate(R.layout.listview_row, null,true);

        //this code gets references to objects in the listview_row.xml file
        TextView nomeTextField = rowView.findViewById(R.id.nomeContato);
        TextView numeroTextField = rowView.findViewById(R.id.numeroContato);

        //this code sets the values of the objects to values from the arrays
        nomeTextField.setText(nomeContato[position]);
        numeroTextField.setText(numeroContato[position]);

        return rowView;

    };
};


