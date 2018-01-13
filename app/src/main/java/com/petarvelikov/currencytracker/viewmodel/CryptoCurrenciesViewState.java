package com.petarvelikov.currencytracker.viewmodel;

import android.support.annotation.NonNull;

import com.petarvelikov.currencytracker.model.CryptoCurrency;

import java.util.ArrayList;
import java.util.List;

public class CryptoCurrenciesViewState {

    private List<CryptoCurrency> currencies;
    private boolean isLoading;

    public CryptoCurrenciesViewState() {
        this.currencies = new ArrayList<>();
    }

    @NonNull
    public List<CryptoCurrency> getCurrencies() {
        return this.currencies;
    }

    public int getCurrenciesCount() {
        return this.currencies.size();
    }

    public boolean isLoading() {
        return this.isLoading;
    }

    @NonNull
    public CryptoCurrenciesViewState addCryptoCurrencies(List<CryptoCurrency> currencies) {
        this.currencies.addAll(currencies);
        return this;
    }

    @NonNull
    public CryptoCurrenciesViewState setLoading(boolean isLoading) {
        this.isLoading = isLoading;
        return this;
    }
}