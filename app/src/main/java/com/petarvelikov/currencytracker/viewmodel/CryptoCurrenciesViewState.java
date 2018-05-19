package com.petarvelikov.currencytracker.viewmodel;

import android.support.annotation.NonNull;

import com.petarvelikov.currencytracker.model.CryptoCurrency;

import java.util.ArrayList;
import java.util.List;

public class CryptoCurrenciesViewState {

  private List<CryptoCurrency> currencies;
  private boolean isLoading;
  private boolean isEndReached;
  private boolean hasError;

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
  public CryptoCurrenciesViewState setLoading(boolean isLoading) {
    this.isLoading = isLoading;
    return this;
  }

  public boolean hasError() {
    return this.hasError;
  }

  public boolean isEndReached() {
    return this.isEndReached;
  }

  @NonNull
  public CryptoCurrenciesViewState setEndReached(boolean isEndReached) {
    this.isEndReached = isEndReached;
    return this;
  }

  @NonNull
  public CryptoCurrenciesViewState setCryptoCurrencies(List<CryptoCurrency> currencies) {
    this.currencies = currencies;
    return this;
  }

  @NonNull
  public CryptoCurrenciesViewState addCryptoCurrencies(List<CryptoCurrency> currencies) {
    this.currencies.addAll(currencies);
    return this;
  }

  @NonNull
  public CryptoCurrenciesViewState setHasError(boolean hasError) {
    this.hasError = hasError;
    return this;
  }
}
