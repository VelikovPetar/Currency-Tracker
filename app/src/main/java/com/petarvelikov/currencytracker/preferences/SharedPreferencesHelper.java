package com.petarvelikov.currencytracker.preferences;

import android.content.SharedPreferences;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class SharedPreferencesHelper {

  private static final String KEY_LAST_ICONS_UPDATE = "last_icons_update";
  private static final String KEY_BASE_CURRENCY_KEY = "base_currency";
  private static final String BASE_CURRENCY = "EUR";

  private SharedPreferences sharedPreferences;

  @Inject
  public SharedPreferencesHelper(SharedPreferences sharedPreferences) {
    this.sharedPreferences = sharedPreferences;
    if (!sharedPreferences.contains(KEY_BASE_CURRENCY_KEY)) {
      setBaseCurrency(BASE_CURRENCY);
    }
  }

  public String getBaseCurrency() {
    return sharedPreferences.getString(KEY_BASE_CURRENCY_KEY, BASE_CURRENCY);
  }

  public void setBaseCurrency(String currency) {
    sharedPreferences.edit()
        .putString(KEY_BASE_CURRENCY_KEY, currency)
        .apply();
  }

  public long getLastTimeOfIconsLoad() {
    return sharedPreferences.getLong(KEY_LAST_ICONS_UPDATE, 0);
  }

  public void setLastTimeOfIconsLoaded(long time) {
    sharedPreferences.edit()
        .putLong(KEY_LAST_ICONS_UPDATE, time)
        .apply();
  }
}
