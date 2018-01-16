package com.petarvelikov.currencytracker.di.module;

import android.arch.persistence.room.Room;
import android.content.Context;

import com.petarvelikov.currencytracker.model.database.CurrencyDatabase;
import com.petarvelikov.currencytracker.model.rest.CoinMarketCapApiService;
import com.petarvelikov.currencytracker.model.rest.CryptoCompareApiService;

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
    private static final String BASE_URL_COIN_MARKET_CAP = "https://api.coinmarketcap.com/v1/";
    private static final String BASE_URL_CRYPTO_COMPARE = "https://min-api.cryptocompare.com/";

    private Context context;

    public AppModule(Context context) {
        this.context = context;
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
                .baseUrl(BASE_URL_COIN_MARKET_CAP)
                .addConverterFactory(factory)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();
    }

    @Provides
    @Singleton
    @Named(RETROFIT_NAME_CRYPTO_COMPARE)
    Retrofit provideCryptoCompareRetrofit(GsonConverterFactory factory) {
        return new Retrofit.Builder()
                .baseUrl(BASE_URL_CRYPTO_COMPARE)
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
