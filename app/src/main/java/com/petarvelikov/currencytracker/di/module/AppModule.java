package com.petarvelikov.currencytracker.di.module;

import android.app.Application;
import android.arch.persistence.room.Room;
import android.content.Context;
import android.net.ConnectivityManager;

import com.petarvelikov.currencytracker.consts.Constants;
import com.petarvelikov.currencytracker.model.database.CurrencyDatabase;
import com.petarvelikov.currencytracker.model.network.CoinMarketCapApiService;
import com.petarvelikov.currencytracker.model.network.CryptoCompareApiService;
import com.petarvelikov.currencytracker.model.network.FixerApiService;

import javax.inject.Named;
import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
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
    ConnectivityManager provideConnectivityManager() {
        return (ConnectivityManager) this.app.getSystemService(Context.CONNECTIVITY_SERVICE);
    }

    @Provides
    @Singleton
    CurrencyDatabase provideCurrencyDatabase() {
        return Room.databaseBuilder(app, CurrencyDatabase.class, "icons").build();
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
    Retrofit provideCryptoCompareRetrofit(GsonConverterFactory gsonFactory, RxJava2CallAdapterFactory rxFactory) {
        return new Retrofit.Builder()
                .baseUrl(Constants.API_CONSTANTS.BASE_URL_CRYPTO_COMPARE)
                .addConverterFactory(gsonFactory)
                .addCallAdapterFactory(rxFactory)
                .build();
    }

    @Provides
    @Singleton
    @Named(RETROFIT_NAME_FIXER)
    Retrofit provideFixerRetrofit(GsonConverterFactory gsonFactory, RxJava2CallAdapterFactory rxFactory) {
        return new Retrofit.Builder()
                .baseUrl(Constants.API_CONSTANTS.BASE_URL_FIXER)
                .addConverterFactory(gsonFactory)
                .addCallAdapterFactory(rxFactory)
                .build();
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
