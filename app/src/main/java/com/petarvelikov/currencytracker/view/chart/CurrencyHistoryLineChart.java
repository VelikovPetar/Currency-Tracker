package com.petarvelikov.currencytracker.view.chart;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.petarvelikov.currencytracker.R;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class CurrencyHistoryLineChart extends LineChart {

    public CurrencyHistoryLineChart(Context context) {
        super(context);
        setup();
    }

    public CurrencyHistoryLineChart(Context context, AttributeSet attrs) {
        super(context, attrs);
        setup();
    }

    public CurrencyHistoryLineChart(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        setup();
    }

    private void setup() {
        getAxisRight().setEnabled(false);
        getXAxis().setPosition(XAxis.XAxisPosition.BOTTOM);
        setNoDataText("");
        setTouchEnabled(false);
    }

    public void setHistoricalData(Map<Float, Double> chartData, List<String> chartLabels, String currencyName) {
        List<Entry> entries = new ArrayList<>();
        for (Float key : chartData.keySet()) {
            entries.add(new Entry(key, chartData.get(key).floatValue()));
        }
        LineDataSet dataSet = new LineDataSet(entries, currencyName);
        dataSet.setColor(ContextCompat.getColor(getContext(), R.color.colorAccent));
        dataSet.setCircleColor(ContextCompat.getColor(getContext(), R.color.colorAccent));
        dataSet.setDrawCircleHole(false);
        dataSet.setCircleRadius(1f);
        dataSet.setValueTextColor(ContextCompat.getColor(getContext(), R.color.colorAccent));
        LineData lineData = new LineData(dataSet);
        getXAxis().setValueFormatter(new XAxisValueFormatter(chartLabels));
        setData(lineData);
        invalidate();
    }
}
