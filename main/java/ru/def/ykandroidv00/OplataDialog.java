package ru.def.ykandroidv00;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.widget.EditText;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;

public class OplataDialog extends DialogFragment {
    private static final String TAG = null;
    private static short nalbez=-1;
    private static boolean dialogclosed=false;
    ZakazPodrobnosti context;


    public void setNalbez(short nalbez) {
        this.nalbez = nalbez;
    }
    public void setDialogclosed(boolean dialogclosed) {
        this.dialogclosed = dialogclosed;
    }
    public static short getNalbez(){return nalbez;}
    public static boolean getDialogclosed(){return dialogclosed;}

    public void onAttach(Context context) {
        super.onAttach(context);
        this.context = (ZakazPodrobnosti) context;
    }

    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        final EditText input = new EditText(getActivity());
        input.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_DECIMAL);
        return builder
                .setTitle("Случилась оплата!!! ")
                .setMessage("Выбран статус оплачено. Выбери как оплатили?")
                .setIcon(android.R.drawable.ic_menu_agenda)
                .setPositiveButton("на счет", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        nalbez=1;
                        dialogclosed=true;
                        ((ZakazPodrobnosti) getActivity()).dialogButPressed();
                        ((ZakazPodrobnosti) getActivity()).dialogClosed();

                    }
                })
                .setNeutralButton("Нахер, отмена!отмена!!!", new DialogInterface.OnClickListener()  {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent intent2=new Intent(getActivity(),Zakazi.class);
                        startActivity(intent2);
                    }
                })
                .setNegativeButton("Наликом", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        nalbez=0;
                        dialogclosed=true;
                        ((ZakazPodrobnosti) getActivity()).dialogButPressed();
                        ((ZakazPodrobnosti) getActivity()).dialogClosed();


                    }
                })
                .create();
    }

}
