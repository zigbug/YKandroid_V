package ru.def.ykandroidv00;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.widget.EditText;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;

public class AddCagentDialog extends DialogFragment {
    private static final String TAG = null;
    String polepres=" ";

    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        polepres=getArguments().getString("izpolya");
        final EditText input = new EditText(getActivity());
        input.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_DECIMAL);
        return builder
                .setTitle("Незарегистрированный " +polepres)
                .setMessage("Добавим в список?")
                .setIcon(android.R.drawable.ic_menu_agenda)
                .setPositiveButton("Понеслась", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        if (polepres.equals("контрагент")){
                        Intent intent = new Intent(getActivity(), CagentDialog.class);
                        startActivity(intent);
                        } else                {         Intent intent = new Intent(getActivity(), ArtGoodsDialog.class);
                        startActivity(intent); }
                    }
                })
                .setNegativeButton("Ну, нахер", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent intent2=new Intent(getActivity(),Zakazi.class);
                        startActivity(intent2);
                    }
                })
                .create();
    }
}
