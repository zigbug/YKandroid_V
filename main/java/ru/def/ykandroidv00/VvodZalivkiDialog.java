package ru.def.ykandroidv00;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.text.InputType;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;

public class VvodZalivkiDialog extends DialogFragment {

    private VvodZalivki context;
    private static String nalil = "0";
    int textId;
    TextView textView;

    public static String getNalil() {
        return nalil;
    }

    public void setNalil(String nalil) {
        this.nalil = nalil;
    }

    public void onAttach(Context context) {
        super.onAttach(context);
        this.context = (VvodZalivki) context;
    }

    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        final String text = getArguments().getString("text");

        final EditText input = new EditText(getActivity());
        input.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_DECIMAL);
        return builder
                .setTitle("Ввод заливки")
                .setIcon(android.R.drawable.ic_dialog_info)
                .setMessage("Сколько налил \"" + text + "\"?")
                .setView(input)
                .setPositiveButton("Сохранить", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        nalil = input.getText().toString();
                        ((VvodZalivki) getActivity()).okClicked();
                    }
                })
                .setNegativeButton("Отмена", null)
                .create();
    }
}