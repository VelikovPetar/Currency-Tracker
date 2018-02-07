package com.petarvelikov.currencytracker.preferences;

import android.content.SharedPreferences;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class PreferencesHelper {

    private static final String BASE_CURRENCY_KEY = "base_currency";
    private static final String BASE_CURRENCY = "EUR";

    private SharedPreferences sharedPreferences;

    @Inject
    public PreferencesHelper(SharedPreferences sharedPreferences) {
        this.sharedPreferences = sharedPreferences;
        setBaseCurrency(BASE_CURRENCY);
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
