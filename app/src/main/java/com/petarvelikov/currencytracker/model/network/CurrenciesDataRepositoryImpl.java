package com.petarvelikov.currencytracker.model.network;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;

import com.petarvelikov.currencytracker.model.ApiResponse;
import com.petarvelikov.currencytracker.model.CryptoCurrency;
import com.petarvelikov.currencytracker.model.CurrencyIcon;
import com.petarvelikov.currencytracker.model.CurrencyIconsResponse;
import com.petarvelikov.currencytracker.model.database.CurrencyDatabase;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;
import javax.inject.Singleton;

import io.reactivex.Observable;
import io.reactivex.Single;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

@Singleton
public class CurrenciesDataRepositoryImpl implements CurrenciesDataRepository {

    @Inject
    CoinMarketCapApiService coinMarketCapApiService;
    @Inject
    CryptoCompareApiService cryptoCompareApiService;
    @Inject
    CurrencyDatabase currencyDatabase;

    private Disposable disposable;

    @Inject
    public CurrenciesDataRepositoryImpl() {

    }

    @Override
    public Single<List<CryptoCurrency>> getAllCurrencies(int start, int limit, String convert) {
        return coinMarketCapApiService.getAllCurrencies(start, limit, convert)
                .subscribeOn(Schedulers.io())
                .flatMapObservable(Observable::fromIterable)
                .map(currency -> {
                    CurrencyIcon currencyIcon = currencyDatabase.currencyDao().getCurrencyIconBySymbol(currency.getSymbol());
                    if (currencyIcon != null) {
                        currency.setImageUrl(currencyIcon.getImageUrl());
                    }
                    return currency;
                })
                .toList();
    }

    @Override
    public LiveData<ApiResponse<CurrencyIconsResponse>> getCurrenciesIcons() {
        MutableLiveData<ApiResponse<CurrencyIconsResponse>> liveData = new MutableLiveData<>();
        disposable = Single.defer(() ->
                Single.just(currencyDatabase.currencyDao().getNumberOfIcons()))
                .toObservable()
                .takeWhile(rows -> {
                    if (rows > 0) {
                        liveData.postValue(new ApiResponse<>(CurrencyIconsResponse.ALREADY_LOADED));
                    }
                    return rows == 0;
                })
                .flatMapSingle(rows -> cryptoCompareApiService.getCurrenciesIcons())
                .subscribeOn(Schedulers.io())
                .observeOn(Schedulers.io())
                .subscribe(response -> {
                    Map<String, CurrencyIcon> data = response.getData();
                    if (data != null) {
                        List<CurrencyIcon> icons = new ArrayList<>();
                        icons.addAll(data.values());
                        currencyDatabase.currencyDao().insertMultiple(icons.toArray(new CurrencyIcon[0]));
                        liveData.postValue(new ApiResponse<>(response));
                    }
                }, throwable -> {
                    liveData.postValue(new ApiResponse<>(throwable));
                });
        return liveData;
    }

    @Override
    public Single<CryptoCurrency> getCurrencyById(String id, String convert) {
        return coinMarketCapApiService.getCurrencyById(id, convert)
                .filter(list -> !list.isEmpty())
                .flatMapSingle(list -> Single.just(list.get(0)));
    }

    @Override
    public void cancelCalls() {
        if (disposable != null && !disposable.isDisposed()) {
            disposable.dispose();
        }
    }
}
