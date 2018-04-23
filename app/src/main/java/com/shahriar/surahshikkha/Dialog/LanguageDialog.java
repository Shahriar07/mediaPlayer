package com.shahriar.surahshikkha.Dialog;

import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.widget.ArrayAdapter;

import com.shahriar.surahshikkha.Interfaces.LanguageDialogInterface;
import com.shahriar.surahshikkha.R;
import com.shahriar.surahshikkha.Utility.Constants;
import com.shahriar.surahshikkha.Utility.SharedPreferenceController;

import java.text.NumberFormat;

/**
 * Created by H. M. Shahriar on 4/15/2018.
 */


public class LanguageDialog {
    public AlertDialog createLanguageDialog(Context context, String title, final LanguageDialogInterface listener) {
        if (context != null) {
            AlertDialog.Builder builderSingle = new AlertDialog.Builder(context, R.style.MyDialogTheme);
            builderSingle.setTitle(title != null? title : context.getString(R.string.language_control));
            final ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(context, android.R.layout.simple_selectable_list_item);
            for (int i = 0; i < Constants.LANGUAGE_LIST.length; i++) {
                arrayAdapter.add(Constants.LANGUAGE_LIST[i]); // TODO: Need to get from single source
            }
            builderSingle.setNegativeButton(context.getString(R.string.cancel), new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                }
            });
            builderSingle.setAdapter(arrayAdapter, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    if (listener != null)
                        listener.selectedLanguageIndex(which);
                }
            });
            return builderSingle.create();
        }
        return null;
    }
}