package com.example.charttest;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.LimitLine;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.highlight.ChartHighlighter;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;
import com.github.mikephil.charting.renderer.LineChartRenderer;
import com.github.mikephil.charting.renderer.YAxisRenderer;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements
        OnChartValueSelectedListener {

    private CustomLineChart chart;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        chart = findViewById(R.id.chart1);
        chart.setOnChartValueSelectedListener(this);

        // no description text
        chart.getDescription().setEnabled(false);

        // enable touch gestures
        chart.setTouchEnabled(true);

        chart.setDragDecelerationFrictionCoef(0.9f);

        // enable scaling and dragging
        chart.setDragEnabled(true);
        chart.setScaleEnabled(false);
        chart.setDrawGridBackground(false);
        chart.setHighlightPerDragEnabled(true);

        chart.setMarkViews(new BlackMarkView(this), new YellowMarkView(this), new GreenMarkView(this));
        chart.setExtraBottomOffset(10);
        chart.setExtraLeftOffset(30);

        // if disabled, scaling can be done on x- and y-axis separately
        chart.setPinchZoom(true);

        // set an alternative background color
        chart.setBackgroundColor(Color.WHITE);

        //chart.animateX(1500);

        // get the legend (only possible after setting data)
        Legend l = chart.getLegend();

        // modify the legend ...
        l.setForm(Legend.LegendForm.LINE);
        l.setEnabled(false);
//        l.setTypeface(tfLight);
//        l.setTextSize(11f);
//        l.setTextColor(Color.WHITE);//dataset这几个字的颜色
//        l.setVerticalAlignment(Legend.LegendVerticalAlignment.BOTTOM);//底下的文字datasheet放底部
//        l.setHorizontalAlignment(Legend.LegendHorizontalAlignment.LEFT);//底下datasheet居左
//        l.setOrientation(Legend.LegendOrientation.HORIZONTAL);//datasheet这几个文字是横着放还是竖着放
//        l.setDrawInside(false);
//        l.setYOffset(11f);

        XAxis xAxis = chart.getXAxis();
        xAxis.setEnabled(true);
        xAxis.setTextSize(11f);
        xAxis.setTextColor(Color.GRAY);//顶部的x轴文字的颜色
        xAxis.setDrawGridLines(false);
        xAxis.setLabelCount(2, true);
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setDrawAxisLine(true);
        xAxis.setYOffset(10);
//        xAxis.setTypeface(tfLight);

        YAxis leftAxis = chart.getAxisLeft();
        leftAxis.setTextColor(Color.GRAY);
        leftAxis.setDrawGridLines(false);//是否画横线
        leftAxis.setDrawLabels(false);
//        leftAxis.setXOffset(20);
        //leftAxis.enableGridDashedLine(10, 10, 0);
        //leftAxis.setLabelCount(3, true);
//        leftAxis.setAxisMaximum(60f);//最大值
//        leftAxis.setAxisMinimum(50f);//最小值
//        leftAxis.setCenterAxisLabels(true);
//        leftAxis.setTypeface(tfLight);
        //leftAxis.setGranularityEnabled(false);
        //是否绘制0所在的网格线
        //leftAxis.setDrawZeroLine(false);


        YAxis rightAxis = chart.getAxisRight();
        rightAxis.setEnabled(true);
        rightAxis.setDrawLabels(false);
        rightAxis.setDrawGridLines(false);

//        rightAxis.setTypeface(tfLight);
//        rightAxis.setTextColor(Color.RED);//右边y轴得文字
//        rightAxis.setAxisMaximum(900);
//        rightAxis.setAxisMinimum(-200);
//        rightAxis.setDrawGridLines(false);
//        rightAxis.setDrawZeroLine(false);
//        rightAxis.setGranularityEnabled(false);

        setData(20, 30);
    }

    @Override
    public void onValueSelected(Entry e, Highlight h) {
        chart.centerViewToAnimated(e.getX(), e.getY(), chart.getData().getDataSetByIndex(h.getDataSetIndex())
                .getAxisDependency(), 500);
    }

    @Override
    public void onNothingSelected() {

    }

    private void setData(int count, float range) {

        ArrayList<Entry> values1 = new ArrayList<>();
        int xMin = 100000;
        int xMax = -1;
        float yMin = 100000;
        float yMax = -1;

        for (int i = 0; i < count; i++) {
            float val = (float) (Math.random() * (range / 2f)) + 50;
            values1.add(new Entry(i, val));

            if (i < xMin) {
                xMin = i;
            }
            if (i > xMax) {
                xMax = i;
            }
            if (val < yMin) {
                yMin = val;
            }
            if (val > yMax) {
                yMax = val;
            }
        }

        ArrayList<Entry> values2 = new ArrayList<>();

        for (int i = 0; i < count; i++) {
            float val = (float) (Math.random() * range) + 450;
            values2.add(new Entry(i, val));

            if (i < xMin) {
                xMin = i;
            }
            if (i > xMax) {
                xMax = i;
            }
            if (val < yMin) {
                yMin = val;
            }
            if (val > yMax) {
                yMax = val;
            }
        }

        ArrayList<Entry> values3 = new ArrayList<>();

        for (int i = 0; i < count; i++) {
            float val = (float) (Math.random() * range) + 500;
            values3.add(new Entry(i, val));

            if (i < xMin) {
                xMin = i;
            }
            if (i > xMax) {
                xMax = i;
            }
            if (val < yMin) {
                yMin = val;
            }
            if (val > yMax) {
                yMax = val;
            }
        }

        XAxis xAxis = chart.getXAxis();
        xAxis.setAxisMaximum(xMax);
        xAxis.setAxisMinimum(xMin);
        YAxis axisLeft = chart.getAxisLeft();
        axisLeft.setAxisMaximum(yMax + 100);
        axisLeft.setAxisMinimum(yMin - 100);

        axisLeft.removeAllLimitLines();

        LimitLine hightLimit = new LimitLine(yMin, String.valueOf((int) Math.ceil(yMin)));
        hightLimit.setLineColor(Color.GRAY); //设置线的颜色
        hightLimit.setTextColor(Color.GRAY);  //设置限制线上label字体的颜色
        hightLimit.setLabelPosition(LimitLine.LimitLabelPosition.LEFT_TOP);//标签位置
        hightLimit.enableDashedLine(10f, 10f, 0);  //设置虚线
        axisLeft.setDrawLimitLinesBehindData(true);
        axisLeft.addLimitLine(hightLimit);

        hightLimit = new LimitLine(yMax, String.valueOf((int) Math.ceil(yMax)));
        hightLimit.setLineColor(Color.GRAY); //设置线的颜色
        hightLimit.setTextColor(Color.GRAY);  //设置限制线上label字体的颜色
        hightLimit.setLabelPosition(LimitLine.LimitLabelPosition.LEFT_TOP);//标签位置
        hightLimit.enableDashedLine(10f, 10f, 0);  //设置虚线
        axisLeft.setDrawLimitLinesBehindData(true);
        axisLeft.addLimitLine(hightLimit);

        int middle = (int) Math.ceil((yMax + yMin) / 2);
        hightLimit = new LimitLine(middle, String.valueOf(middle));
        hightLimit.setLineColor(Color.GRAY); //设置线的颜色
        hightLimit.setTextColor(Color.GRAY);  //设置限制线上label字体的颜色
        hightLimit.setLabelPosition(LimitLine.LimitLabelPosition.LEFT_TOP);//标签位置
        hightLimit.enableDashedLine(10f, 10f, 0);  //设置虚线
        axisLeft.setDrawLimitLinesBehindData(true);
        axisLeft.addLimitLine(hightLimit);


        LineDataSet set1, set2, set3;

        if (chart.getData() != null &&
                chart.getData().getDataSetCount() > 0) {
            set1 = (LineDataSet) chart.getData().getDataSetByIndex(0);
            set2 = (LineDataSet) chart.getData().getDataSetByIndex(1);
            set3 = (LineDataSet) chart.getData().getDataSetByIndex(2);
            set1.setValues(values1);
            set2.setValues(values2);
            set3.setValues(values3);
            chart.getData().notifyDataChanged();
            chart.notifyDataSetChanged();
        } else {
            // create a dataset and give it a type
            set1 = new LineDataSet(values1, "DataSet 1");

            set1.setAxisDependency(YAxis.AxisDependency.LEFT);
            set1.setColor(Color.BLACK);
            set1.setLineWidth(2f);
            set1.setHighLightColor(Color.GRAY);
            set1.setDrawCircleHole(false);
            set1.setDrawValues(false);
            set1.setDrawCircles(false);
            set1.setDrawHorizontalHighlightIndicator(false);
            //set1.setCircleColor(Color.WHITE);
            //set1.setCircleRadius(3f);
            //set1.setFillAlpha(65);
            //set1.setFillColor(ColorTemplate.getHoloBlue());
            //set1.setFillFormatter(new MyFillFormatter(0f));
            //set1.setDrawHorizontalHighlightIndicator(false);
            //set1.setVisible(false);
            //set1.setCircleHoleColor(Color.WHITE);

            // create a dataset and give it a type
            set2 = new LineDataSet(values2, "DataSet 2");
            set2.setAxisDependency(YAxis.AxisDependency.LEFT);
            set2.setColor(Color.YELLOW);
            set2.setLineWidth(2f);
            set2.setDrawCircleHole(false);
            set2.setHighLightColor(Color.GRAY);
            set2.setDrawValues(false);
            set2.setDrawCircles(false);
            set2.setDrawHorizontalHighlightIndicator(false);
            //set2.setCircleColor(Color.WHITE);
            //set2.setCircleRadius(3f);
            //set2.setFillAlpha(65);
            //set2.setFillColor(Color.RED);
            //set2.setFillFormatter(new MyFillFormatter(900f));

            set3 = new LineDataSet(values3, "DataSet 3");
            set3.setAxisDependency(YAxis.AxisDependency.LEFT);
            set3.setColor(Color.GREEN);
            set3.setLineWidth(2f);
            set3.setDrawCircleHole(false);
            set3.setHighLightColor(Color.GRAY);
            set3.setDrawValues(false);
            set3.setDrawCircles(false);
            set3.setDrawHorizontalHighlightIndicator(false);
            //set3.setCircleColor(Color.WHITE);
            //set3.setCircleRadius(3f);
            //set3.setFillAlpha(65);
            //set3.setFillColor(ColorTemplate.colorWithAlpha(Color.YELLOW, 200));

            // create a data object with the data sets
            LineData data = new LineData(set1, set2, set3);
            data.setValueTextColor(Color.WHITE);
            data.setValueTextSize(9f);

            // set data
            chart.setData(data);
            chart.invalidate();
        }
    }
}
