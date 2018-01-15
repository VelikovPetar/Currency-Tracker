package com.petarvelikov.currencytracker.model.rest;

import com.petarvelikov.currencytracker.model.CryptoCurrency;

import java.util.List;

import io.reactivex.Single;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface CoinMarketCapApiService {

    // TODO Remove this
    @GET("ticker/")
    Call<List<CryptoCurrency>> getAllCurrencies(@Query("start") int start,
                                                @Query("limit") int limit,
                                                @Query("convert") String convert);

    @GET("ticker/")
    Single<List<CryptoCurrency>> getAllCurrenciesSingle(@Query("start") int start,
                                                        @Query("limit") int limit,
                                                        @Query("convert") String convert);
}
