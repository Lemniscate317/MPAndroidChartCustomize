package com.example.charttest;

import android.content.Context;
import android.widget.TextView;

import com.github.mikephil.charting.components.MarkerView;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.highlight.Highlight;

/**
 * Function:
 *
 * @Author: 钰涛
 * @CreateDate: 2020/4/27 14:36
 * Copyright (c) , 冲潮科技 All Rights Reserved.
 */
public class GreenMarkView extends MarkerView {

    private final TextView mTv_text;

    /**
     * Constructor. Sets up the MarkerView with a custom layout resource.
     *
     * @param context
     */
    public GreenMarkView(Context context) {
        super(context, R.layout.item_green_markview);

        mTv_text = findViewById(R.id.tv_text);
    }

    @Override
    public void refreshContent(Entry e, Highlight highlight) {
        mTv_text.setText(e.getX() + "  ---  " + e.getY());

        super.refreshContent(e, highlight);
    }
}
