package com.petarvelikov.currencytracker.model.network;

import com.petarvelikov.currencytracker.model.CryptoCurrency;
import com.petarvelikov.currencytracker.model.CurrencyIcon;
import com.petarvelikov.currencytracker.model.CurrencyIconsResponse;
import com.petarvelikov.currencytracker.model.HistoricalDataResponse;
import com.petarvelikov.currencytracker.model.database.CurrencyDatabase;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import io.reactivex.Observable;
import io.reactivex.Single;
import io.reactivex.schedulers.Schedulers;

@Singleton
public class CurrenciesDataRepositoryImpl implements CurrenciesDataRepository {

    @Inject
    CoinMarketCapApiService coinMarketCapApiService;
    @Inject
    CryptoCompareApiService cryptoCompareApiService;
    @Inject
    CurrencyDatabase currencyDatabase;

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
    public Single<CryptoCurrency> getCurrencyById(String id, String convert) {
        return coinMarketCapApiService.getCurrencyById(id, convert)
                .filter(list -> !list.isEmpty())
                .flatMapSingle(list -> Single.just(list.get(0)));
    }

    @Override
    public Single<CurrencyIconsResponse> getCurrencyIcons() {
        return cryptoCompareApiService.getCurrenciesIcons();
    }

    @Override
    public Single<HistoricalDataResponse> getHistoricalDataDaily(String fromSymbol, String toSymbol) {
        int dataRecordsCount = 24;
        return cryptoCompareApiService.getHistoricalDataPerHour(fromSymbol, toSymbol, dataRecordsCount);
    }

    @Override
    public Single<HistoricalDataResponse> getHistoricalDataWeekly(String fromSymbol, String toSymbol) {
        int dataRecordsCount = 7 * 24;
        return cryptoCompareApiService.getHistoricalDataPerHour(fromSymbol, toSymbol, dataRecordsCount);
    }

    @Override
    public Single<HistoricalDataResponse> getHistoricalDataMonthly(String fromSymbol, String toSymbol) {
        int dataRecordsCount = 31;
        return cryptoCompareApiService.getHistoricalDataPerDay(fromSymbol, toSymbol, dataRecordsCount);
    }
}
