package com.petarvelikov.currencytracker.model.rest;

import android.arch.lifecycle.LiveData;

import com.petarvelikov.currencytracker.model.ApiResponse;
import com.petarvelikov.currencytracker.model.CryptoCurrency;
import com.petarvelikov.currencytracker.model.CurrencyIconsResponse;

import java.util.List;

import retrofit2.Call;

public interface CryptoCurrenciesApiRepository {

    LiveData<ApiResponse<List<CryptoCurrency>>> getAllCurrencies(int start, int limit, String convert);

    Call<CurrencyIconsResponse> getCurrenciesIcons();

    LiveData<ApiResponse<List<CryptoCurrency>>> getAllCurrenciesSingle(int start, int limit, String convert);
}
