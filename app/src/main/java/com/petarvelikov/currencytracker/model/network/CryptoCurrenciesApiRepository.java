package com.petarvelikov.currencytracker.model.network;

import android.arch.lifecycle.LiveData;

import com.petarvelikov.currencytracker.model.ApiResponse;
import com.petarvelikov.currencytracker.model.CryptoCurrency;
import com.petarvelikov.currencytracker.model.CurrencyIconsResponse;

import java.util.List;

public interface CryptoCurrenciesApiRepository {

    LiveData<ApiResponse<List<CryptoCurrency>>> getAllCurrencies(int start, int limit, String convert);

    LiveData<ApiResponse<CurrencyIconsResponse>> getCurrenciesIcons();

    LiveData<ApiResponse<CryptoCurrency>> getCurrencyById(String id, String convert);

    // TODO Proper disposal of singles!
    void cancelCalls();
}
