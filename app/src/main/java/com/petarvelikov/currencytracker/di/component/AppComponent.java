package com.petarvelikov.currencytracker.di.component;

import com.petarvelikov.currencytracker.di.module.AppModule;
import com.petarvelikov.currencytracker.di.module.BinderModule;
import com.petarvelikov.currencytracker.service.GetCurrenciesIconsService;
import com.petarvelikov.currencytracker.view.fragment.CryptoCurrenciesFragment;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = {AppModule.class, BinderModule.class})
public interface AppComponent {

    void inject(CryptoCurrenciesFragment fragment);

    void inject(GetCurrenciesIconsService service);
}
