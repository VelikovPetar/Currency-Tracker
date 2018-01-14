package com.petarvelikov.currencytracker.model.rest;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;

import com.petarvelikov.currencytracker.consts.Constants;
import com.petarvelikov.currencytracker.model.ApiResponse;
import com.petarvelikov.currencytracker.model.CryptoCurrency;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

@Singleton
public class CryptoCurrenciesApiRepositoryImpl implements CryptoCurrenciesApiRepository {

    @Inject
    CoinMarketCapApiService coinMarketCapApiService;
    @Inject
    CryptoCompareApiService cryptoCompareApiService;

    @Inject
    public CryptoCurrenciesApiRepositoryImpl() {
    }

    @Override
    public LiveData<ApiResponse<List<CryptoCurrency>>> getAllCurrencies(int start, int limit, String convert) {
        MutableLiveData<ApiResponse<List<CryptoCurrency>>> liveData = new MutableLiveData<>();
        Call<List<CryptoCurrency>> call = coinMarketCapApiService.getAllCurrencies(start, limit, convert);
        call.enqueue(new Callback<List<CryptoCurrency>>() {
            @Override
            public void onResponse(Call<List<CryptoCurrency>> call, Response<List<CryptoCurrency>> response) {
                if (response.isSuccessful()) {
                    List<CryptoCurrency> currencies = response.body();
                    if (currencies != null) {
                        liveData.setValue(new ApiResponse<>(currencies));
                    } else {
                        liveData.setValue(new ApiResponse<>(new Throwable(response.toString())));
                    }
                } else {
                    if (String.valueOf(response.code()).equals(Constants.ERROR.HTTP_404_NOT_FOUND)) {
                        liveData.setValue(new ApiResponse<>(
                                new Throwable(Constants.ERROR.HTTP_404_NOT_FOUND)));
                    } else {
                        liveData.setValue(new ApiResponse<>(new Throwable(response.toString())));
                    }
                }
            }

            @Override
            public void onFailure(Call<List<CryptoCurrency>> call, Throwable t) {
                liveData.setValue(new ApiResponse<>(t));
            }
        });
        return liveData;
    }
}
