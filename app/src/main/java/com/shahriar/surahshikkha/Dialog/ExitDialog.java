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
public class ExitDialog extends Dialog {

    Context context;
    String exitText;
    String cancelText;
    String contentText;
    AlertDialogCommandInterface callBack;
    public ExitDialog(@NonNull Context context,String exitText, String cancelText, String contentText, AlertDialogCommandInterface callBack) {
        super(context,R.style.AlertDialogTheme);
        this.callBack = callBack;
        this.context = context;
        this.cancelText = cancelText;
        this.exitText = exitText;
        this.contentText = contentText;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.dialog_exit);
        setCancelable(false);

        Button okButton = (Button)findViewById(R.id.exit_dialog_btn_ok);
        Button cancelButton = (Button)findViewById(R.id.exit_dialog_btn_cancel);
        Typeface typeface = ResourcesCompat.getFont(context, R.font.solaimanlipi);
        cancelButton.setTypeface(typeface);
        okButton.setTypeface(typeface);
        cancelButton.setText(cancelText);
        okButton.setText(exitText);

        TextView exitText = (TextView) findViewById(R.id.exit_dialog_content);
        exitText.setTypeface(typeface);
        exitText.setText(contentText);
        okButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
                if (callBack != null){
                    callBack.onPositiveButtonClicked();
                }

            }
        });

        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
                if (callBack != null){
                    callBack.onNegativeButtonClicked();
                }

            }
        });
    }
}
