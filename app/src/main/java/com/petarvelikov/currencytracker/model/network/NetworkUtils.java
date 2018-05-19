package com.petarvelikov.currencytracker.model.network;

import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class NetworkUtils {

  @Inject
  ConnectivityManager connectivityManager;

  @Inject
  public NetworkUtils() {
  }

  public boolean isConnected() {
    NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
    return activeNetworkInfo != null && activeNetworkInfo.isConnected();
  }
}
