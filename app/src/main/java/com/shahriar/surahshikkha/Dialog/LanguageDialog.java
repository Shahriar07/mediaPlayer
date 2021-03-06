package com.shahriar.surahshikkha.Dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.shahriar.surahshikkha.Adapter.SortDialogItemAdapter;
import com.shahriar.surahshikkha.Interfaces.DialogItemTouchListener;
import com.shahriar.surahshikkha.Interfaces.OnRecycleViewClicked;
import com.shahriar.surahshikkha.LayoutManager.ScrollingLinearLayoutManager;
import com.shahriar.surahshikkha.Listeners.RecyclerItemTouchListener;
import com.shahriar.surahshikkha.R;
import com.shahriar.surahshikkha.Utility.Utility;

import java.util.ArrayList;

/**
 * Created by H. M. Shahriar on 5/3/2018.
 */
public class LanguageDialog extends Dialog {


    private Context context;
    private RecyclerView dlg_priority_lvw = null;
    private TextView titleView;
    String title;
    String buttonText;
    int selectedItem;
    private RecyclerView.LayoutManager mLayoutManager;
    DialogItemTouchListener listener;
    ArrayList<String> itemList;

    public LanguageDialog(@NonNull Context context, String title, String buttonText, ArrayList<String> itemList, int selectedItem, DialogItemTouchListener listener) {
        super(context);
        this.context = context;
        this.listener = listener;
        this.title = title;
        this.itemList = itemList;
        this.selectedItem = selectedItem;
        this.buttonText = buttonText;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.language_dialog_layout);
        dlg_priority_lvw = (RecyclerView) findViewById(R.id.dlg_list_items);
        if (title != null){
            titleView = (TextView) findViewById(R.id.id_dialog_title);
            titleView.setTypeface(Utility.getTypeFace(context));
            titleView.setText(title);
        }
        mLayoutManager = new ScrollingLinearLayoutManager(context,5);
        // ListView
        dlg_priority_lvw.setLayoutManager(mLayoutManager);
        SortDialogItemAdapter adapter = new SortDialogItemAdapter(context,itemList,selectedItem,0);
        dlg_priority_lvw.setAdapter(adapter);
//        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(context, LinearLayoutManager.VERTICAL);
//        dividerItemDecoration.setDrawable(context.getResources().getDrawable(R.drawable.divider_item_decoration));
//        dlg_priority_lvw.addItemDecoration(dividerItemDecoration);

        //ListView
        dlg_priority_lvw.addOnItemTouchListener(new RecyclerItemTouchListener(context, dlg_priority_lvw, new OnRecycleViewClicked(){
            @Override
            public void onClick(View view, int position) {
                Log.d(getClass().getSimpleName(), "Item Selected " + position);
                dismiss();
                if (listener != null){
                    listener.onDialogItemSelected(position);
                }
            }

            @Override
            public void onLongClick(View view, int position) {
                dismiss();
                if (listener != null){
                    listener.onDialogItemSelected(position);
                }
            }
        }));
        Button cancelButton = (Button)findViewById(R.id.lng_dlg_btn_cancel);
        cancelButton.setTypeface(Utility.getTypeFace(context));
        cancelButton.setText(buttonText);
        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
    }
}
