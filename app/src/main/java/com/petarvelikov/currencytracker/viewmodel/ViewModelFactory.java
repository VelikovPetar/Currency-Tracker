package com.petarvelikov.currencytracker.viewmodel;

import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;
import android.support.annotation.NonNull;

import javax.inject.Inject;

public class ViewModelFactory implements ViewModelProvider.Factory {
    private CryptoCurrenciesViewModel ccViewModel;
    private BootstrapViewModel bootstrapViewModel;
    private CurrencyDetailsViewModel currencyDetailsViewModel;

    @Inject
    public ViewModelFactory(CryptoCurrenciesViewModel ccViewModel,
                            BootstrapViewModel bootstrapViewModel,
                            CurrencyDetailsViewModel currencyDetailsViewModel) {
        this.ccViewModel = ccViewModel;
        this.bootstrapViewModel = bootstrapViewModel;
        this.currencyDetailsViewModel = currencyDetailsViewModel;
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
        throw new IllegalArgumentException("Unknown class name");
    }
}
