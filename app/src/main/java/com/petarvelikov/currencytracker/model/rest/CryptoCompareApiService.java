package com.petarvelikov.currencytracker.model.rest;

import com.petarvelikov.currencytracker.model.CurrencyIconsResponse;

import retrofit2.Call;
import retrofit2.http.GET;

public interface CryptoCompareApiService {

    @GET("data/all/coinlist")
    Call<CurrencyIconsResponse> getCurrenciesIcons();
}
