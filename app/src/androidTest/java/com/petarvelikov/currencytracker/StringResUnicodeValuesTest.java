package com.petarvelikov.currencytracker;

import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(AndroidJUnit4.class)
public class StringResUnicodeValuesTest {

    @Test
    public void displayCurrencyValues() {
        Context context = InstrumentationRegistry.getTargetContext();
        String[] currencyValues = context.getResources().getStringArray(R.array.available_currencies);
        for (String currencyValue : currencyValues) {
            System.out.println(currencyValue);
        }
    }
}
