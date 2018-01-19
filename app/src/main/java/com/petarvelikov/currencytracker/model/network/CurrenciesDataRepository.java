package com.petarvelikov.currencytracker.model.network;

import com.petarvelikov.currencytracker.model.CryptoCurrency;
import com.petarvelikov.currencytracker.model.CurrencyIconsResponse;

import java.util.List;

import io.reactivex.Single;

public interface CurrenciesDataRepository {

    Single<List<CryptoCurrency>> getAllCurrencies(int start, int limit, String convert);

    Single<CurrencyIconsResponse> getCurrencyIcons();

    Single<CryptoCurrency> getCurrencyById(String id, String convert);

}
