package com.petarvelikov.currencytracker.di.component;

import com.petarvelikov.currencytracker.di.module.AppModule;
import com.petarvelikov.currencytracker.di.module.BinderModule;
import com.petarvelikov.currencytracker.view.activity.AddTransactionActivity;
import com.petarvelikov.currencytracker.view.activity.BootstrapActivity;
import com.petarvelikov.currencytracker.view.activity.CurrencyDetailsActivity;
import com.petarvelikov.currencytracker.view.fragment.CryptoCurrenciesFragment;
import com.petarvelikov.currencytracker.view.fragment.ExchangeRatesFragment;
import com.petarvelikov.currencytracker.view.fragment.TransactionsFragment;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = {AppModule.class, BinderModule.class})
public interface AppComponent {

  void inject(BootstrapActivity activity);

  void inject(CurrencyDetailsActivity activity);

  void inject(CryptoCurrenciesFragment fragment);

  void inject(ExchangeRatesFragment fragment);

  void inject(TransactionsFragment fragment);

  void inject(AddTransactionActivity activity);
}
