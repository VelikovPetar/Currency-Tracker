package com.petarvelikov.currencytracker.resources;

import android.app.Application;
import android.content.Context;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class ResourceProvider {

  private Context context;

  @Inject
  public ResourceProvider(Application app) {
    this.context = app;
  }

  public String getString(int resId) {
    return context.getString(resId);
  }

  public String[] getStringArray(int resId) {
    return context.getResources().getStringArray(resId);
  }
}
