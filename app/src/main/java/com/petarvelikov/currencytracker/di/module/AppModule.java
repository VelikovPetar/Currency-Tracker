package com.petarvelikov.currencytracker.di.module;

import android.arch.lifecycle.ViewModelProvider;
import android.content.Context;

import com.petarvelikov.currencytracker.model.rest.CoinMarketCapApiService;
import com.petarvelikov.currencytracker.model.rest.CryptoCompareApiService;
import com.petarvelikov.currencytracker.viewmodel.ViewModelFactory;

import javax.inject.Named;
import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import retrofit2.Retrofit;
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
    public CoinMarketCapApiService provideCoinMarketCapApiService(
            @Named(RETROFIT_NAME_COIN_MARKET_CAP) Retrofit retrofit) {
        return retrofit.create(CoinMarketCapApiService.class);
    }

    @Provides
    @Singleton
    public CryptoCompareApiService provideCryptoCompareApiService(
            @Named(RETROFIT_NAME_CRYPTO_COMPARE) Retrofit retrofit) {
        return retrofit.create(CryptoCompareApiService.class);
    }

    @Provides
    @Singleton
    @Named(RETROFIT_NAME_COIN_MARKET_CAP)
    public Retrofit provideCoinMarketCapRetrofit(GsonConverterFactory factory) {
        return new Retrofit.Builder()
                .baseUrl(BASE_URL_COIN_MARKET_CAP)
                .addConverterFactory(factory)
                .build();
    }

    @Provides
    @Singleton
    @Named(RETROFIT_NAME_CRYPTO_COMPARE)
    public Retrofit provideCryptoCompareRetrofit(GsonConverterFactory factory) {
        return new Retrofit.Builder()
                .baseUrl(BASE_URL_CRYPTO_COMPARE)
                .addConverterFactory(factory)
                .build();
    }

    @Provides
    @Singleton
    public GsonConverterFactory provideGsonConverterFactory() {
        return GsonConverterFactory.create();
    }

}
