package ru.def.ykandroidv00;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.widget.EditText;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;

public class SynchDialog extends DialogFragment {
    private static final String TAG = null;
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        final EditText input = new EditText(getActivity());
        input.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_DECIMAL);
        return builder
                .setTitle("Cинхронизация?")
                .setMessage("Начать синхронизацию с FTP?")
                .setIcon(android.R.drawable.ic_menu_agenda)
                .setPositiveButton("Понеслась", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        Intent intent = new Intent(getActivity(), DlFTPActivity.class);
                        startActivity(intent);
                    }
                })
                .setNegativeButton("Ну, нахер", null)
                .create();
    }




}
