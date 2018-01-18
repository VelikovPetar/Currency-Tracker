package com.petarvelikov.currencytracker.di.module;

import android.arch.persistence.room.Room;
import android.content.Context;
import android.net.ConnectivityManager;

import com.petarvelikov.currencytracker.consts.Constants;
import com.petarvelikov.currencytracker.model.database.CurrencyDatabase;
import com.petarvelikov.currencytracker.model.network.CoinMarketCapApiService;
import com.petarvelikov.currencytracker.model.network.CryptoCompareApiService;

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

    private Context context;

    public AppModule(Context context) {
        this.context = context;
    }

    @Provides
    @Singleton
    Context provideContext() {
        return this.context;
    }

    @Provides
    @Singleton
    ConnectivityManager provideConnectivityManager() {
        return (ConnectivityManager) this.context.getSystemService(Context.CONNECTIVITY_SERVICE);
    }

    @Provides
    @Singleton
    CurrencyDatabase provideCurrencyDatabase() {
        return Room.databaseBuilder(context, CurrencyDatabase.class, "icons").build();
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
    @Named(RETROFIT_NAME_COIN_MARKET_CAP)
    Retrofit provideCoinMarketCapRetrofit(GsonConverterFactory factory) {
        return new Retrofit.Builder()
                .baseUrl(Constants.API_CONSTANTS.BASE_URL_COIN_MARKET_CAP)
                .addConverterFactory(factory)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();
    }

    @Provides
    @Singleton
    @Named(RETROFIT_NAME_CRYPTO_COMPARE)
    Retrofit provideCryptoCompareRetrofit(GsonConverterFactory factory) {
        return new Retrofit.Builder()
                .baseUrl(Constants.API_CONSTANTS.BASE_URL_CRYPTO_COMPARE)
                .addConverterFactory(factory)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();
    }

    @Provides
    @Singleton
    GsonConverterFactory provideGsonConverterFactory() {
        return GsonConverterFactory.create();
    }

}
