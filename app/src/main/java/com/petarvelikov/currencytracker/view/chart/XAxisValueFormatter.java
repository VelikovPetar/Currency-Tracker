package com.petarvelikov.currencytracker.view.chart;

import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;

import java.util.List;

public class XAxisValueFormatter implements IAxisValueFormatter {

    private List<String> values;

    public XAxisValueFormatter(List<String> values) {
        this.values = values;
    }

    @Override
    public String getFormattedValue(float value, AxisBase axis) {
        return values.get((int) value);
    }

}
