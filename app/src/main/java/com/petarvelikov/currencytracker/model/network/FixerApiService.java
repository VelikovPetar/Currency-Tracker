package com.petarvelikov.currencytracker.model.network;

import com.petarvelikov.currencytracker.model.ExchangeRatesResponse;

import javax.inject.Singleton;

import io.reactivex.Single;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface FixerApiService {

    @GET("latest")
    Single<ExchangeRatesResponse> getExchangeRates(@Query("base") String base,
                                                   @Query("access_key") String accessKey);

    @GET("latest")
    Single<ExchangeRatesResponse> getExchangeRates(@Query("base") String base);
}
