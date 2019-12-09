package ru.ratnikoff.lesson5;

import android.app.Dialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;


/**
 * Created by SM on 23.01.2017.
 */

public class DialogExitApp extends DialogFragment implements View.OnClickListener {

    private View form = null;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        form = getActivity().getLayoutInflater()
                .inflate(R.layout.dialog_exit, null);
        Button b = form.findViewById(R.id.ExitOk);
        b.setOnClickListener(this);
        b = form.findViewById(R.id.ExitNo);
        b.setOnClickListener(this);
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        return builder.setView(form).create();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ExitOk:
                getActivity().finish();
                break;
            case R.id.ExitNo:
                dismiss();
                break;
        }
    }
}
