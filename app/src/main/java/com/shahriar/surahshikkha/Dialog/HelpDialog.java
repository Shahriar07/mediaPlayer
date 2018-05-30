package com.shahriar.surahshikkha.Dialog;

import android.app.Dialog;
import android.content.Context;
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
public class HelpDialog extends Dialog {

    AlertDialogCommandInterface callBack;
    public HelpDialog(@NonNull Context context) {
        super(context,R.style.AlertDialogTheme);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.dialog_help_outlined);
        setCancelable(false);

        Button okButton = (Button)findViewById(R.id.help_dialog_btn_ok);
        okButton.setText(getContext().getApplicationContext().getString(R.string.ok));

        TextView dialogTitle = (TextView) findViewById(R.id.help_dialog_title);
        dialogTitle.setText(getContext().getApplicationContext().getString(R.string.help_outlined_title));

        TextView instructionText = (TextView) findViewById(R.id.helpDialogContent);
        TextView textbody = (TextView) findViewById(R.id.helpDialogBody);
        TextView instructionFooter = (TextView) findViewById(R.id.helpFooterDialogContent);

        instructionText.setText(getContext().getApplicationContext().getString(R.string.instruction1));
        textbody.setText(getContext().getApplicationContext().getString(R.string.instruction2));
        instructionFooter.setText(getContext().getApplicationContext().getString(R.string.instruction3));
        okButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
    }
}
