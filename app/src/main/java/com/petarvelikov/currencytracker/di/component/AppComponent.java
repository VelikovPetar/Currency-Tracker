package com.petarvelikov.currencytracker.di.component;

import com.petarvelikov.currencytracker.di.module.AppModule;
import com.petarvelikov.currencytracker.di.module.BinderModule;
import com.petarvelikov.currencytracker.view.MainActivity;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = {AppModule.class, BinderModule.class})
public interface AppComponent {

    void inject(MainActivity activity);
}
