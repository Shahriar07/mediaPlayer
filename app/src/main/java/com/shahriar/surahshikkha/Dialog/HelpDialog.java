package com.shahriar.surahshikkha.Dialog;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.content.res.ResourcesCompat;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.shahriar.surahshikkha.Interfaces.AlertDialogCommandInterface;
import com.shahriar.surahshikkha.R;

/**
 * Created by H. M. Shahriar on 5/19/2018.
 */
public class HelpDialog extends Dialog {

    Context context;
    AlertDialogCommandInterface callBack;
    public HelpDialog(@NonNull Context context) {
        super(context,R.style.AlertDialogTheme);
        this.context = context;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.dialog_help_outlined);
        setCancelable(false);

        Button okButton = (Button)findViewById(R.id.help_dialog_btn_ok);
        Typeface typeface = ResourcesCompat.getFont(context, R.font.solaimanlipi);
        okButton.setTypeface(typeface);
        okButton.setText(getContext().getString(R.string.ok));

        TextView dialogTitle = (TextView) findViewById(R.id.help_dialog_title);
        dialogTitle.setTypeface(typeface);
        dialogTitle.setText(getContext().getApplicationContext().getString(R.string.help_outlined_title));

        TextView instructionText = (TextView) findViewById(R.id.helpDialogContent);
        TextView textbody = (TextView) findViewById(R.id.helpDialogBody);
        TextView instructionFooter = (TextView) findViewById(R.id.helpFooterDialogContent);
        instructionText.setTypeface(typeface);
        textbody.setTypeface(typeface);
        instructionFooter.setTypeface(typeface);

        instructionText.setText(getContext().getString(R.string.instruction1));
        textbody.setText(getContext().getApplicationContext().getString(R.string.instruction2));
        instructionFooter.setText(getContext().getString(R.string.instruction3));
        okButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
    }
}
