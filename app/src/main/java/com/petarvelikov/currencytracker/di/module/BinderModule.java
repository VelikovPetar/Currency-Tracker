package com.petarvelikov.currencytracker.di.module;

import android.arch.lifecycle.ViewModelProvider;

import com.petarvelikov.currencytracker.model.network.CurrenciesDataRepository;
import com.petarvelikov.currencytracker.model.network.CurrenciesDataRepositoryImpl;
import com.petarvelikov.currencytracker.viewmodel.ViewModelFactory;

import dagger.Binds;
import dagger.Module;

@Module
public abstract class BinderModule {

    @Binds
    public abstract CurrenciesDataRepository bindRepository(CurrenciesDataRepositoryImpl impl);

    @Binds
    public abstract ViewModelProvider.Factory bindViewModelFactory(ViewModelFactory factory);
}
