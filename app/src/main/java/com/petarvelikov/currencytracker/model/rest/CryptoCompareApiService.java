package com.petarvelikov.currencytracker.model.rest;

import com.petarvelikov.currencytracker.model.CurrencyIconsResponse;

import io.reactivex.Single;
import retrofit2.http.GET;

public interface CryptoCompareApiService {

    @GET("data/all/coinlist")
    Single<CurrencyIconsResponse> getCurrenciesIcons();
}
