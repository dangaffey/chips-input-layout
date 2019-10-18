package com.tylersuehr.chips;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class ChipsInputLayout extends LinearLayout implements EmptyChipsListener {

    public ChipsLayout chipsLayout;
    public EditText editText;
    public RelativeLayout chipsHeader;

    public ChipsInputLayout(Context context, AttributeSet attrs) {
        super(context, attrs);

        inflate(context, R.layout.layout_chips_input, this);

        editText = findViewById(R.id.edit_text);
        chipsLayout = findViewById(R.id.chips_layout);
        chipsHeader = findViewById(R.id.header);
        ChipOptions mOptions = new ChipOptions(context, attrs);
        chipsLayout.setChipsLayout(context, attrs, mOptions, editText, this);

    }

    public ChipsLayout getChipsLayout() {
        return chipsLayout;
    }

    public EditText getEditText() { return editText; }

    @Override
    public void updateChipsHeader(int count) {
        if (count > 0) {
            chipsLayout.setVisibility(View.VISIBLE);
            chipsHeader.setVisibility(View.VISIBLE);
        } else {
            chipsLayout.setVisibility(View.GONE);
            chipsHeader.setVisibility(View.GONE);
        }
    }
}

interface EmptyChipsListener {
    void updateChipsHeader(int count);
}
