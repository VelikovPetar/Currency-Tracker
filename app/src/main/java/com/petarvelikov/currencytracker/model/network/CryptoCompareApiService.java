package com.petarvelikov.currencytracker.model.network;

import com.petarvelikov.currencytracker.model.CurrencyIconsResponse;
import com.petarvelikov.currencytracker.model.HistoricalDataResponse;

import io.reactivex.Single;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface CryptoCompareApiService {

  @GET("data/all/coinlist")
  Single<CurrencyIconsResponse> getCurrenciesIcons(@Query("extraParams") String extraParams);

  @GET("data/histohour")
  Single<HistoricalDataResponse> getHistoricalDataPerHour(@Query("fsym") String fromSymbol,
                                                          @Query("tsym") String toSymbol,
                                                          @Query("limit") int limit,
                                                          @Query("extraParams") String extraParams);

  @GET("data/histoday")
  Single<HistoricalDataResponse> getHistoricalDataPerDay(@Query("fsym") String fromSymbol,
                                                         @Query("tsym") String toSymbol,
                                                         @Query("limit") int limit,
                                                         @Query("extraParams") String extraParams);
}
