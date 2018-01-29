package com.petarvelikov.currencytracker.model.network;

import com.petarvelikov.currencytracker.model.ExchangeRatesResponse;

import javax.inject.Inject;
import javax.inject.Singleton;

import io.reactivex.Single;

@Singleton
public class ExchangeRatesRepositoryImpl implements ExchangeRatesRepository {

    @Inject
    FixerApiService fixerApiService;

    @Inject
    public ExchangeRatesRepositoryImpl() {

    }

    @Override
    public Single<ExchangeRatesResponse> getExchangeRates(String base) {
        return fixerApiService.getExchangeRates(base);
    }
}
