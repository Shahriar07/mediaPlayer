package com.shahriar.surahshikkha.CustomComponents;

import android.content.Context;
import android.util.AttributeSet;

/**
 * Created by H. M. Shahriar on 3/4/2018.
 */

public class CustomSpinner extends android.support.v7.widget.AppCompatSpinner {

        public CustomSpinner(Context context)
        { super(context); }

        public CustomSpinner(Context context, AttributeSet attrs)
        { super(context, attrs); }

        public CustomSpinner(Context context, AttributeSet attrs, int defStyle)
        { super(context, attrs, defStyle); }

        @Override public void
        setSelection(int position, boolean animate)
        {
            boolean sameSelected = position == getSelectedItemPosition();
            super.setSelection(position, animate);
            if (sameSelected) {
                OnItemSelectedListener onItemSelectedListener = getOnItemSelectedListener();
                if (onItemSelectedListener != null) {
                    onItemSelectedListener.onItemSelected(this, getSelectedView(), position, getSelectedItemId());
                }
            }
        }

        @Override public void setSelection(int position)
        {
            boolean sameSelected = position == getSelectedItemPosition();
            super.setSelection(position);
            if (sameSelected) {
                OnItemSelectedListener onItemSelectedListener = getOnItemSelectedListener();
                if (onItemSelectedListener != null) {
                    getOnItemSelectedListener().onItemSelected(this, getSelectedView(), position, getSelectedItemId());
                }
            }
        }
}
