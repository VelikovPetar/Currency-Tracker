package com.petarvelikov.currencytracker.model.rest;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;

import com.petarvelikov.currencytracker.consts.Constants;
import com.petarvelikov.currencytracker.model.ApiResponse;
import com.petarvelikov.currencytracker.model.CryptoCurrency;
import com.petarvelikov.currencytracker.model.CurrencyIcon;
import com.petarvelikov.currencytracker.model.CurrencyIconsResponse;
import com.petarvelikov.currencytracker.model.database.CurrencyDatabase;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import io.reactivex.Observable;
import io.reactivex.SingleObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
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
    CurrencyDatabase currencyDatabase;

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

    @Override
    public LiveData<ApiResponse<List<CryptoCurrency>>> getAllCurrenciesSingle(int start, int limit, String convert) {
        MutableLiveData<ApiResponse<List<CryptoCurrency>>> liveData = new MutableLiveData<>();
        coinMarketCapApiService.getAllCurrenciesSingle(start, limit, convert)
                .subscribeOn(Schedulers.io())
                .flatMapObservable(Observable::fromIterable)
                .map(currency -> {
                    CurrencyIcon currencyIcon = currencyDatabase.currencyDao().getCurrencyIconBySymbol(currency.getSymbol());
                    if (currencyIcon != null) {
                        currency.setImageUrl(currencyIcon.getImageUrl());
                    }
                    return currency;
                })
                .toList()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SingleObserver<List<CryptoCurrency>>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onSuccess(List<CryptoCurrency> currencies) {
                        liveData.setValue(new ApiResponse<>(currencies));
                    }

                    @Override
                    public void onError(Throwable e) {
                        liveData.setValue(new ApiResponse<>(e));
                    }
                });
        return liveData;
    }

    @Override
    public Call<CurrencyIconsResponse> getCurrenciesIcons() {
        return cryptoCompareApiService.getCurrenciesIcons();
    }
}
