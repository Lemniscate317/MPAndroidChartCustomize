package com.example.charttest;

import com.github.mikephil.charting.animation.ChartAnimator;
import com.github.mikephil.charting.interfaces.dataprovider.LineDataProvider;
import com.github.mikephil.charting.renderer.LineChartRenderer;
import com.github.mikephil.charting.utils.ViewPortHandler;

/**
 * Function:
 *
 * @Author: 钰涛
 * @CreateDate: 2020/4/27 17:40
 * Copyright (c) , 冲潮科技 All Rights Reserved.
 */
public class CustomLineChartRender extends LineChartRenderer {
    public CustomLineChartRender(LineDataProvider chart, ChartAnimator animator, ViewPortHandler viewPortHandler) {
        super(chart, animator, viewPortHandler);
    }
}
