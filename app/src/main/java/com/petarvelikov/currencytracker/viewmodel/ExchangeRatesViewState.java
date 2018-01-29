package com.petarvelikov.currencytracker.viewmodel;

import android.support.annotation.NonNull;

import com.petarvelikov.currencytracker.model.ExchangeRate;

import java.util.List;
import java.util.Map;

public class ExchangeRatesViewState {

    private List<ExchangeRate> exchangeRates;
    private boolean isLoading;
    private boolean hasError;

    public List<ExchangeRate> getExchangeRates() {
        return exchangeRates;
    }

    public boolean isLoading() {
        return isLoading;
    }

    public boolean hasError() {
        return hasError;
    }

    @NonNull
    public ExchangeRatesViewState setExchangeRates(List<ExchangeRate> exchangeRates) {
        this.exchangeRates = exchangeRates;
        return this;
    }

    @NonNull
    public ExchangeRatesViewState setIsLoading(boolean isLoading) {
        this.isLoading = isLoading;
        return this;
    }

    @NonNull
    public ExchangeRatesViewState setHasError(boolean hasError) {
        this.hasError = hasError;
        return this;
    }
}
