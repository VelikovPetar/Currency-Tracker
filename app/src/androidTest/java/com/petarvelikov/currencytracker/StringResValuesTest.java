package com.petarvelikov.currencytracker;

import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.*;

@RunWith(AndroidJUnit4.class)
public class StringResValuesTest {

    @Test
    public void displayCurrencyValues() {
        Context context = InstrumentationRegistry.getTargetContext();
        String[] currencyValues = context.getResources().getStringArray(R.array.available_currencies);
        for (String currencyValue : currencyValues) {
            System.out.println(currencyValue);
        }
    }

    @Test
    public void matchCurrencyNamesWithCountryNames() {
        Context context = InstrumentationRegistry.getTargetContext();
        String[] currencyValues = context.getResources().getStringArray(R.array.available_currencies);
        for (String currencyValue : currencyValues) {
            String[] parts = currencyValue.split("\\|");
            assertTrue(parts[0].startsWith(parts[parts.length - 1]));
        }
    }
}
