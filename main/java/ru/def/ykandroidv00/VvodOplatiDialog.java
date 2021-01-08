package ru.def.ykandroidv00;
//
//import android.app.Dialog;
//import android.content.Context;
//import android.content.DialogInterface;
//import android.content.Intent;
//import android.os.Bundle;
//import android.text.InputType;
//import android.widget.EditText;
//import android.widget.TextView;
//
//import androidx.appcompat.app.AlertDialog;
//import androidx.fragment.app.DialogFragment;
//
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.widget.EditText;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;

public class VvodOplatiDialog extends DialogFragment {
//
//    private Debitors context;
//    private static String oplatili = "0";
//    int textId;
//    TextView textView;
//    String kagent;
//
//    public static String getNalil() {
//        return oplatili;
//
//    }
//
//    public void setOplatili(String oplatili)
//    {
//        this.oplatili = oplatili;
//    }
//
//    public void onAttach(Context context) {
//        super.onAttach(context);
//        this.context = (Debitors) context;
//    }
//
//    public Dialog onCreateDialog(Bundle savedInstanceState) {
//        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
//        kagent=getArguments().getString("kagent");
//        final String text = getArguments().getString("text");
//
//        final EditText input = new EditText(getActivity());
//        input.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_DECIMAL);
//        return builder
//                .setTitle("Сколько же заплатили "+ kagent)
//                .setIcon(android.R.drawable.ic_dialog_info)
//                .setMessage("Сколько  \"" + text + "\"?")
//                .setView(input)
//                .setPositiveButton("Сохранить", new DialogInterface.OnClickListener() {
//                    public void onClick(DialogInterface dialog, int id) {
//                        oplatili = input.getText().toString();
//                        ((VvodZalivki) getActivity()).okClicked();
//                    }
//                })
//                .setNegativeButton("Отмена", null)
//                .create();
//    }
//}



//public class OplataDialog extends DialogFragment {
    private static final String TAG = null;
    private static short nalbez=-1;
    private static String oplati="0";
    Debitors context;
    private String kagent;


    public void setNalbez(short nalbez) {
        this.nalbez = nalbez;
    }
    public void setOplati(String oplati) {
        this.oplati = oplati;
    }
    public static short getNalbez(){return nalbez;}
    public static String getOplati(){return oplati;}

    public void onAttach(Context context) {
        super.onAttach(context);
        this.context = (Debitors) context;
    }

    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        kagent=getArguments().getString("kagent");
        final EditText input = new EditText(getActivity());
        input.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_DECIMAL);
        return builder
                .setTitle("Случилась оплата от " + kagent)
                .setMessage("Ввведи сумму и способ оплаты?")
                .setView(input)
                .setIcon(android.R.drawable.ic_menu_sort_by_size)
                .setPositiveButton("на счет", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        nalbez=1;
                        oplati=input.getText().toString();
                        ((Debitors)getActivity()).oplati();
                        ((Debitors)getActivity()).saveOplataToFile();

                    }
                })
                .setNeutralButton("Нахер, отмена!отмена!!!", new DialogInterface.OnClickListener()  {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent intent2=new Intent(getActivity(),MainActivity.class);
                        startActivity(intent2);
                    }
                })
                .setNegativeButton("Наликом", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        nalbez=0;
                        oplati=input.getText().toString();
                        ((Debitors)getActivity()).oplati();
                    }
                })
                .create();

    }

}