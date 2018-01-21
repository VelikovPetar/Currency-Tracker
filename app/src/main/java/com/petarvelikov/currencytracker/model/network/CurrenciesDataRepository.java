package com.petarvelikov.currencytracker.model.network;

import com.petarvelikov.currencytracker.model.CryptoCurrency;
import com.petarvelikov.currencytracker.model.CurrencyIconsResponse;
import com.petarvelikov.currencytracker.model.HistoricalDataResponse;

import java.util.List;

import io.reactivex.Single;

public interface CurrenciesDataRepository {

    Single<List<CryptoCurrency>> getAllCurrencies(int start, int limit, String convert);

    Single<CurrencyIconsResponse> getCurrencyIcons();

    Single<CryptoCurrency> getCurrencyById(String id, String convert);

    Single<HistoricalDataResponse> getHistoricalDataDaily(String fromSymbol, String toSymbol);

    Single<HistoricalDataResponse> getHistoricalDataWeekly(String fromSymbol, String toSymbol);

    Single<HistoricalDataResponse> getHistoricalDataMonthly(String fromSymbol, String toSymbol);

}
