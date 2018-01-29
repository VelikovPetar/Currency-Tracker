package com.petarvelikov.currencytracker.model.network;

import com.petarvelikov.currencytracker.model.ExchangeRatesResponse;

import io.reactivex.Single;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface FixerApiService {

    @GET("latest")
    Single<ExchangeRatesResponse> getExchangeRates(@Query("base") String base);
}
