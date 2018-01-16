package com.petarvelikov.currencytracker.di.module;

import android.arch.lifecycle.ViewModelProvider;

import com.petarvelikov.currencytracker.model.network.CryptoCurrenciesApiRepository;
import com.petarvelikov.currencytracker.model.network.CryptoCurrenciesApiRepositoryImpl;
import com.petarvelikov.currencytracker.viewmodel.ViewModelFactory;

import dagger.Binds;
import dagger.Module;

@Module
public abstract class BinderModule {

    @Binds
    public abstract CryptoCurrenciesApiRepository bindRepository(CryptoCurrenciesApiRepositoryImpl impl);

    @Binds
    public abstract ViewModelProvider.Factory bindViewModelFactory(ViewModelFactory factory);
}
