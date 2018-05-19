package com.petarvelikov.currencytracker.viewmodel;

import android.support.annotation.NonNull;

import com.petarvelikov.currencytracker.model.ExchangeRate;

import java.util.List;

public class ExchangeRatesViewState {

  private List<ExchangeRate> exchangeRates;
  private boolean isLoading;
  private boolean hasError;

  public List<ExchangeRate> getExchangeRates() {
    return exchangeRates;
  }

  @NonNull
  public ExchangeRatesViewState setExchangeRates(List<ExchangeRate> exchangeRates) {
    this.exchangeRates = exchangeRates;
    return this;
  }

  public boolean isLoading() {
    return isLoading;
  }

  public boolean hasError() {
    return hasError;
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
