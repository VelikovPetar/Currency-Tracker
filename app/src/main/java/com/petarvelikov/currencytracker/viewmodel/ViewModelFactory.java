package com.petarvelikov.currencytracker.viewmodel;

import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;
import android.support.annotation.NonNull;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class ViewModelFactory implements ViewModelProvider.Factory {
  private CryptoCurrenciesViewModel ccViewModel;
  private BootstrapViewModel bootstrapViewModel;
  private CurrencyDetailsViewModel currencyDetailsViewModel;
  private ExchangeRatesViewModel exchangeRatesViewModel;

  @Inject
  public ViewModelFactory(CryptoCurrenciesViewModel ccViewModel,
                          BootstrapViewModel bootstrapViewModel,
                          CurrencyDetailsViewModel currencyDetailsViewModel,
                          ExchangeRatesViewModel exchangeRatesViewModel) {
    this.ccViewModel = ccViewModel;
    this.bootstrapViewModel = bootstrapViewModel;
    this.currencyDetailsViewModel = currencyDetailsViewModel;
    this.exchangeRatesViewModel = exchangeRatesViewModel;
  }

  @NonNull
  @Override
  public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
    if (modelClass.isAssignableFrom(CryptoCurrenciesViewModel.class)) {
      return (T) ccViewModel;
    }
    if (modelClass.isAssignableFrom(BootstrapViewModel.class)) {
      return (T) bootstrapViewModel;
    }
    if (modelClass.isAssignableFrom(CurrencyDetailsViewModel.class)) {
      return (T) currencyDetailsViewModel;
    }
    if (modelClass.isAssignableFrom(ExchangeRatesViewModel.class)) {
      return (T) exchangeRatesViewModel;
    }
    throw new IllegalArgumentException("Unknown class name");
  }
}
