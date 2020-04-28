package com.example.charttest;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.icu.text.UFormat;
import android.os.Build;
import android.util.AttributeSet;
import android.util.Log;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.LimitLine;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.highlight.ChartHighlighter;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.interfaces.datasets.IDataSet;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
import com.github.mikephil.charting.renderer.YAxisRenderer;
import com.github.mikephil.charting.utils.Utils;

import java.util.List;

/**
 * Function:
 *
 * @Author: 钰涛
 * @CreateDate: 2020/4/27 15:10
 * Copyright (c) , 冲潮科技 All Rights Reserved.
 */
public class CustomLineChart extends LineChart {
    private BlackMarkView mBlackMarkView;
    private YellowMarkView mYellowMarkView;
    private GreenMarkView mGreenMarkView;

    public static int LIMIT_LINE_Y_LEFT = 10000;

    private Context mContext;
    private Bitmap mBitmap;
    private Paint mPaint;

    public void setMarkViews(BlackMarkView blackMarkView, YellowMarkView yellowMarkView, GreenMarkView greenMarkView) {
        mBlackMarkView = blackMarkView;
        mYellowMarkView = yellowMarkView;
        mGreenMarkView = greenMarkView;
    }

    public CustomLineChart(Context context) {
        this(context, null);
    }

    public CustomLineChart(Context context, AttributeSet attrs) {
        super(context, attrs);
        mContext = context;

        mBitmap = getBitmap(getContext(), R.mipmap.ic_launcher);
        mPaint = new Paint();
    }

    private static Bitmap getBitmap(Context context, int vectorDrawableId) {
        Bitmap bitmap = null;
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.LOLLIPOP) {
            Drawable vectorDrawable = context.getDrawable(vectorDrawableId);
            bitmap = Bitmap.createBitmap(vectorDrawable.getIntrinsicWidth(),
                    vectorDrawable.getIntrinsicHeight(), Bitmap.Config.ARGB_8888);
            Canvas canvas = new Canvas(bitmap);
            vectorDrawable.setBounds(0, 0, canvas.getWidth(), canvas.getHeight());
            vectorDrawable.draw(canvas);
        } else {
            bitmap = BitmapFactory.decodeResource(context.getResources(), vectorDrawableId);
        }
        return bitmap;
    }

    @Override
    protected void init() {
        super.init();

        setRendererLeftYAxis(new YAxisRenderer(mViewPortHandler, mAxisLeft, mLeftAxisTransformer) {
            @Override
            public void renderLimitLines(Canvas c) {
                //super.renderLimitLines(c);
                List<LimitLine> limitLines = mYAxis.getLimitLines();

                if (limitLines == null || limitLines.size() <= 0)
                    return;

                float[] pts = mRenderLimitLinesBuffer;
                pts[0] = 0;
                pts[1] = 0;
                Path limitLinePath = mRenderLimitLines;
                limitLinePath.reset();

                for (int i = 0; i < limitLines.size(); i++) {

                    LimitLine l = limitLines.get(i);

                    if (!l.isEnabled())
                        continue;

                    int clipRestoreCount = c.save();
                    mLimitLineClippingRect.set(mViewPortHandler.getContentRect());
                    mLimitLineClippingRect.inset(0.f, -l.getLineWidth());
                    c.clipRect(mLimitLineClippingRect);

                    mLimitLinePaint.setStyle(Paint.Style.STROKE);
                    mLimitLinePaint.setColor(l.getLineColor());
                    mLimitLinePaint.setStrokeWidth(l.getLineWidth());
                    mLimitLinePaint.setPathEffect(l.getDashPathEffect());

                    pts[1] = l.getLimit();

                    mTrans.pointValuesToPixel(pts);

                    limitLinePath.moveTo(mViewPortHandler.contentLeft(), pts[1]);
                    limitLinePath.lineTo(mViewPortHandler.contentRight(), pts[1]);

                    c.drawPath(limitLinePath, mLimitLinePaint);
                    limitLinePath.reset();
                    // c.drawLines(pts, mLimitLinePaint);

                    String label = l.getLabel();

                    // if drawing the limit-value label is enabled
                    if (label != null && !label.equals("")) {

                        mLimitLinePaint.setStyle(l.getTextStyle());
                        mLimitLinePaint.setPathEffect(null);
                        mLimitLinePaint.setColor(l.getTextColor());
                        mLimitLinePaint.setTypeface(l.getTypeface());
                        mLimitLinePaint.setStrokeWidth(0.5f);
                        mLimitLinePaint.setTextSize(l.getTextSize());

                        final float labelLineHeight = Utils.calcTextHeight(mLimitLinePaint, label);
                        float xOffset = Utils.convertDpToPixel(4f) + l.getXOffset();
                        float yOffset = l.getLineWidth() + labelLineHeight + l.getYOffset();

                        final LimitLine.LimitLabelPosition position = l.getLabelPosition();

//                        if (position == LimitLine.LimitLabelPosition.RIGHT_TOP) {
//
//                            mLimitLinePaint.setTextAlign(Paint.Align.RIGHT);
//                            c.drawText(label,
//                                    mViewPortHandler.contentRight() - xOffset,
//                                    pts[1] - yOffset + labelLineHeight, mLimitLinePaint);
//
//                        } else if (position == LimitLine.LimitLabelPosition.RIGHT_BOTTOM) {
//
//                            mLimitLinePaint.setTextAlign(Paint.Align.RIGHT);
//                            c.drawText(label,
//                                    mViewPortHandler.contentRight() - xOffset,
//                                    pts[1] + yOffset, mLimitLinePaint);
//
//                        } else if (position == LimitLine.LimitLabelPosition.LEFT_TOP) {
//
//                            mLimitLinePaint.setTextAlign(Paint.Align.LEFT);
//                            c.drawText(label,
//                                    mViewPortHandler.contentLeft() + xOffset,
//                                    pts[1] - yOffset + labelLineHeight, mLimitLinePaint);
//
//                        } else {
//
//                            mLimitLinePaint.setTextAlign(Paint.Align.LEFT);
//                            c.drawText(label,
//                                    mViewPortHandler.offsetLeft() + xOffset,
//                                    pts[1] + yOffset, mLimitLinePaint);
//                        }

                        c.restoreToCount(clipRestoreCount);
                        clipRestoreCount = c.save();


                        Rect rect = new Rect();
                        mLimitLinePaint.getTextBounds(label, 0, label.length(), rect);

                        mLimitLinePaint.setTextAlign(Paint.Align.LEFT);
                        c.drawText(label,
                                mViewPortHandler.contentLeft() - xOffset - rect.width(),
                                pts[1] + rect.height() / 2, mLimitLinePaint);
                    }

                    c.restoreToCount(clipRestoreCount);
                }
            }
        });

        setHighlighter(new ChartHighlighter(this) {
            @Override
            protected Highlight getHighlightForX(float xVal, float x, float y) {
//                return super.getHighlightForX(xVal, x, y);
                List<Highlight> closestValues = getHighlightsAtXValue(xVal, x, y);

                if (closestValues.isEmpty()) {
                    return null;
                }

//                float leftAxisMinDist = getMinimumDistance(closestValues, y, YAxis.AxisDependency.LEFT);
//                float rightAxisMinDist = getMinimumDistance(closestValues, y, YAxis.AxisDependency.RIGHT);
//
//                YAxis.AxisDependency axis = leftAxisMinDist < rightAxisMinDist ? YAxis.AxisDependency.LEFT : YAxis.AxisDependency.RIGHT;
//
//                Highlight detail = getClosestHighlightByPixel(closestValues, x, y, axis, mChart.getMaxHighlightDistance());

                int i = 0;
                int targetIdx = 0;
                float smallestVal = 100;
                for (Highlight highlight : closestValues) {
                    float abs = Math.abs(xVal - highlight.getX());
                    if (abs < smallestVal) {
                        smallestVal = abs;
                        targetIdx = i;
                    }
                    i++;
                }

                Highlight highlight = closestValues.get(targetIdx);


                return highlight;
            }
        });
    }

    /**
     * draws all MarkerViews on the highlighted positions
     */
    @Override
    protected void drawMarkers(Canvas canvas) {
        //super.drawMarkers(canvas);

        // if there is no marker view or drawing marker is disabled
        if (!isDrawMarkersEnabled() || !valuesToHighlight())
            return;

        for (int i = 0; i < mIndicesToHighlight.length; i++) {

            Highlight highlight = mIndicesToHighlight[i];

            IDataSet set = mData.getDataSetByIndex(highlight.getDataSetIndex());

            Entry e = mData.getEntryForHighlight(mIndicesToHighlight[i]);
            int entryIndex = set.getEntryIndex(e);

            // make sure entry not null
            if (e == null || entryIndex > set.getEntryCount() * mAnimator.getPhaseX())
                continue;

            float[] pos = getMarkerPosition(highlight);

            // check bounds
            if (!mViewPortHandler.isInBounds(pos[0], pos[1]))
                continue;

            //设置数据按照黑黄绿的顺序

            Entry blackEntry = null;
            Entry yellowEntry = null;
            Entry greenEntry = null;

            for (int j = 0; j < mData.getDataSetCount(); j++) {
                ILineDataSet dataSetByIndex = mData.getDataSetByIndex(j);
                List<Entry> entriesForXValue = dataSetByIndex.getEntriesForXValue(e.getX());
                if (entriesForXValue.size() > 0) {
                    Entry entry = entriesForXValue.get(0);
                    if (j == 0) {
                        blackEntry = entry;
                    } else if (j == 1) {
                        yellowEntry = entry;
                    } else if (j == 2) {
                        greenEntry = entry;
                    }
                }
            }

            int height = 0;
            int marginTop = 20;

            // callbacks to update the content
            if (blackEntry != null) {
                mBlackMarkView.refreshContent(blackEntry, highlight);

                int width = mBlackMarkView.getWidth();
                float xLeft = pos[0] - width / 2;
                if (xLeft < mViewPortHandler.contentLeft()) {
                    xLeft = mViewPortHandler.contentLeft();
                }
                if (xLeft + width > mViewPortHandler.contentRight()) {
                    xLeft = mViewPortHandler.contentRight() - width;
                }
                // draw the marker
                mBlackMarkView.draw(canvas, xLeft, 0);

                height = mBlackMarkView.getHeight() + marginTop;
            }
            if (yellowEntry != null) {
                mYellowMarkView.refreshContent(yellowEntry, highlight);

                int width = mYellowMarkView.getWidth();
                float xLeft = pos[0] - width / 2;
                if (xLeft < mViewPortHandler.contentLeft()) {
                    xLeft = mViewPortHandler.contentLeft();
                }
                if (xLeft + width > mViewPortHandler.contentRight()) {
                    xLeft = mViewPortHandler.contentRight() - width;
                }
                // draw the marker
                mYellowMarkView.draw(canvas, xLeft, height);

                height += mYellowMarkView.getHeight() + marginTop;
            }
            if (greenEntry != null) {
                mGreenMarkView.refreshContent(greenEntry, highlight);

                int width = mGreenMarkView.getWidth();
                float xLeft = pos[0] - width / 2;
                if (xLeft < mViewPortHandler.contentLeft()) {
                    xLeft = mViewPortHandler.contentLeft();
                }
                if (xLeft + width > mViewPortHandler.contentRight()) {
                    xLeft = mViewPortHandler.contentRight() - width;
                }
                // draw the marker
                mGreenMarkView.draw(canvas, xLeft, height);
            }


        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        if (mViewPortHandler != null) {
            canvas.drawBitmap(mBitmap, mViewPortHandler.contentRight() - mBitmap.getWidth() - 10, mViewPortHandler.contentTop() + 10, mPaint);
        }
        super.onDraw(canvas);
    }
}
