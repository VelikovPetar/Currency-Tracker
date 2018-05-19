package com.petarvelikov.currencytracker.model.network;


import com.petarvelikov.currencytracker.model.ExchangeRatesResponse;

import io.reactivex.Single;

public interface ExchangeRatesRepository {

  Single<ExchangeRatesResponse> getExchangeRates(String base);
}
