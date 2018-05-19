package com.petarvelikov.currencytracker.app;

import android.app.Application;

import com.petarvelikov.currencytracker.di.component.AppComponent;
import com.petarvelikov.currencytracker.di.component.DaggerAppComponent;
import com.petarvelikov.currencytracker.di.module.AppModule;

public class CurrencyTrackerApplication extends Application {

  private AppComponent appComponent;

  @Override
  public void onCreate() {
    super.onCreate();
    appComponent = DaggerAppComponent.builder()
        .appModule(new AppModule(this))
        .build();
  }

  public AppComponent getAppComponent() {
    return this.appComponent;
  }
}
