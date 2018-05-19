package com.petarvelikov.currencytracker.di.module;

import android.app.Application;
import android.arch.persistence.room.Room;
import android.content.Context;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;

import com.petarvelikov.currencytracker.BuildConfig;
import com.petarvelikov.currencytracker.consts.Constants;
import com.petarvelikov.currencytracker.model.database.CurrencyDatabase;
import com.petarvelikov.currencytracker.model.network.CoinMarketCapApiService;
import com.petarvelikov.currencytracker.model.network.CryptoCompareApiService;
import com.petarvelikov.currencytracker.model.network.FixerApiService;

import javax.inject.Named;
import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

@Module
public class AppModule {

  private static final String RETROFIT_NAME_COIN_MARKET_CAP = "coinmarketcap";
  private static final String RETROFIT_NAME_CRYPTO_COMPARE = "cryptocompare";
  private static final String RETROFIT_NAME_FIXER = "fixer";

  private Application app;

  public AppModule(Application app) {
    this.app = app;
  }

  @Provides
  @Singleton
  Application provideApplication() {
    return this.app;
  }

  @Provides
  @Singleton
  SharedPreferences provideSharedPreferences() {
    return PreferenceManager.getDefaultSharedPreferences(app.getApplicationContext());
  }

  @Provides
  @Singleton
  ConnectivityManager provideConnectivityManager() {
    return (ConnectivityManager) this.app.getSystemService(Context.CONNECTIVITY_SERVICE);
  }

  @Provides
  @Singleton
  CurrencyDatabase provideCurrencyDatabase() {
    return Room.databaseBuilder(app, CurrencyDatabase.class, "icons")
        .fallbackToDestructiveMigration() // This destroys user data -> do it with a migration
        .build();
  }

  @Provides
  @Singleton
  CoinMarketCapApiService provideCoinMarketCapApiService(
      @Named(RETROFIT_NAME_COIN_MARKET_CAP) Retrofit retrofit) {
    return retrofit.create(CoinMarketCapApiService.class);
  }

  @Provides
  @Singleton
  CryptoCompareApiService provideCryptoCompareApiService(
      @Named(RETROFIT_NAME_CRYPTO_COMPARE) Retrofit retrofit) {
    return retrofit.create(CryptoCompareApiService.class);
  }

  @Provides
  @Singleton
  FixerApiService provideFixerApiService(@Named(RETROFIT_NAME_FIXER) Retrofit retrofit) {
    return retrofit.create(FixerApiService.class);
  }

  @Provides
  @Singleton
  @Named(RETROFIT_NAME_COIN_MARKET_CAP)
  Retrofit provideCoinMarketCapRetrofit(GsonConverterFactory gsonFactory, RxJava2CallAdapterFactory rxFactory) {
    return new Retrofit.Builder()
        .baseUrl(Constants.API_CONSTANTS.BASE_URL_COIN_MARKET_CAP)
        .addConverterFactory(gsonFactory)
        .addCallAdapterFactory(rxFactory)
        .build();
  }

  @Provides
  @Singleton
  @Named(RETROFIT_NAME_CRYPTO_COMPARE)
  Retrofit provideCryptoCompareRetrofit(GsonConverterFactory gsonFactory,
                                        RxJava2CallAdapterFactory rxFactory,
                                        OkHttpClient client) {
    return new Retrofit.Builder()
        .baseUrl(Constants.API_CONSTANTS.BASE_URL_CRYPTO_COMPARE)
        .client(client)
        .addConverterFactory(gsonFactory)
        .addCallAdapterFactory(rxFactory)
        .build();
  }

  @Provides
  @Singleton
  @Named(RETROFIT_NAME_FIXER)
  Retrofit provideFixerRetrofit(GsonConverterFactory gsonFactory,
                                RxJava2CallAdapterFactory rxFactory,
                                OkHttpClient client) {
    String baseUrl;
    if (BuildConfig.FIXER_API_VERSION == 1) {
      baseUrl = Constants.API_CONSTANTS.BASE_URL_FIXER_V1;
    } else {
      baseUrl = Constants.API_CONSTANTS.BASE_URL_FIXER_V2;
    }
    return new Retrofit.Builder()
        .baseUrl(baseUrl)
        .client(client)
        .addConverterFactory(gsonFactory)
        .addCallAdapterFactory(rxFactory)
        .build();
  }

  @Provides
  @Singleton
  @NonNull
  OkHttpClient provideHttpClient(HttpLoggingInterceptor interceptor) {
    OkHttpClient okHttpClient = new OkHttpClient.Builder()
        .addInterceptor(interceptor)
        .build();
    return okHttpClient;
  }

  @Provides
  @Singleton
  @NonNull
  HttpLoggingInterceptor provideHttpLoggingInterceptor() {
    HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
    if (BuildConfig.DEBUG) {
      interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
    } else {
      interceptor.setLevel(HttpLoggingInterceptor.Level.NONE);
    }
    return interceptor;
  }

  @Provides
  @Singleton
  GsonConverterFactory provideGsonConverterFactory() {
    return GsonConverterFactory.create();
  }

  @Provides
  @Singleton
  RxJava2CallAdapterFactory provideRxJava2CallAdapterFactory() {
    return RxJava2CallAdapterFactory.create();
  }

}
