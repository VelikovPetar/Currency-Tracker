package com.petarvelikov.currencytracker.model.network;

import com.petarvelikov.currencytracker.model.CryptoCurrency;

import java.util.List;

import io.reactivex.Single;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface CoinMarketCapApiService {

  @GET("ticker/")
  Single<List<CryptoCurrency>> getAllCurrencies(@Query("start") int start,
                                                @Query("limit") int limit,
                                                @Query("convert") String convert);

  @GET("ticker/")
  Single<List<CryptoCurrency>> getAllCurrencies(@Query("start") int start,
                                                @Query("convert") String convert);

  @GET("ticker/{id}/")
  Single<List<CryptoCurrency>> getCurrencyById(@Path("id") String id,
                                               @Query("convert") String convert);
}
