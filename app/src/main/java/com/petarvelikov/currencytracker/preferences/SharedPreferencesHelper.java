package com.petarvelikov.currencytracker.preferences;

import android.content.SharedPreferences;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class SharedPreferencesHelper {

    private static final String BASE_CURRENCY_KEY = "base_currency";
    private static final String BASE_CURRENCY = "CHF";

    private SharedPreferences sharedPreferences;

    @Inject
    public SharedPreferencesHelper(SharedPreferences sharedPreferences) {
        this.sharedPreferences = sharedPreferences;
        if (!sharedPreferences.contains(BASE_CURRENCY_KEY)) {
            setBaseCurrency(BASE_CURRENCY);
        }
    }

    public void setBaseCurrency(String currency) {
        sharedPreferences.edit()
                .putString(BASE_CURRENCY_KEY, currency)
                .apply();
    }

    public String getBaseCurrency() {
        return sharedPreferences.getString(BASE_CURRENCY_KEY, BASE_CURRENCY);
    }
}
