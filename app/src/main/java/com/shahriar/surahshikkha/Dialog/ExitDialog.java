package com.shahriar.surahshikkha.Dialog;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.shahriar.surahshikkha.Interfaces.AlertDialogCommandInterface;
import com.shahriar.surahshikkha.R;

/**
 * Created by H. M. Shahriar on 5/19/2018.
 */
public class ExitDialog extends Dialog {

    AlertDialogCommandInterface callBack;
    public ExitDialog(@NonNull Context context, AlertDialogCommandInterface callBack) {
        super(context,R.style.AlertDialogTheme);
        this.callBack = callBack;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.dialog_exit);
        setCancelable(false);

        Button okButton = (Button)findViewById(R.id.exit_dialog_btn_ok);
        Button cancelButton = (Button)findViewById(R.id.exit_dialog_btn_cancel);
        cancelButton.setText(getContext().getApplicationContext().getString(R.string.cancel));
        okButton.setText(getContext().getApplicationContext().getString(R.string.exit));

        TextView exitText = (TextView) findViewById(R.id.exit_dialog_content);
        exitText.setText(getContext().getApplicationContext().getString(R.string.exit_text));
        okButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (callBack != null){
                    callBack.onPositiveButtonClicked();
                }
                dismiss();
            }
        });

        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (callBack != null){
                    callBack.onNegativeButtonClicked();
                }
                dismiss();
            }
        });
    }
}
