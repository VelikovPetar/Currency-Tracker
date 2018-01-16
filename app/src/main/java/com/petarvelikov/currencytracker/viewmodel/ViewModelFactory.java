package com.petarvelikov.currencytracker.viewmodel;

import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;
import android.support.annotation.NonNull;

import javax.inject.Inject;

public class ViewModelFactory implements ViewModelProvider.Factory {
    private CryptoCurrenciesViewModel ccViewModel;
    private BootstrapViewModel bootstrapViewModel;

    @Inject
    public ViewModelFactory(CryptoCurrenciesViewModel ccViewModel, BootstrapViewModel bootstrapViewModel) {
        this.ccViewModel = ccViewModel;
        this.bootstrapViewModel = bootstrapViewModel;
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
        throw new IllegalArgumentException("Unknown class name");
    }
}
