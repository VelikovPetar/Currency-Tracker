package com.petarvelikov.currencytracker.viewmodel;

import android.support.annotation.NonNull;

import com.petarvelikov.currencytracker.model.CryptoCurrency;

public class CurrencyDetailsViewState {

    private CryptoCurrency cryptoCurrency;
    private boolean isLoading;
    private boolean hasError;

    public CurrencyDetailsViewState() {

    }

    public CryptoCurrency getCryptoCurrency() {
        return cryptoCurrency;
    }

    public boolean isLoading() {
        return isLoading;
    }

    public boolean hasError() {
        return hasError;
    }

    @NonNull
    public CurrencyDetailsViewState setCryptoCurrency(CryptoCurrency cryptoCurrency) {
        this.cryptoCurrency = cryptoCurrency;
        return this;
    }

    @NonNull
    public CurrencyDetailsViewState setIsLoading(boolean isLoading) {
        this.isLoading = isLoading;
        return this;
    }

    @NonNull
    public CurrencyDetailsViewState setHasError(boolean hasError) {
        this.hasError = hasError;
        return this;
    }
}
