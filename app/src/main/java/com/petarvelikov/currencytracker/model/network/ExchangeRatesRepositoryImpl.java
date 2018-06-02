package com.petarvelikov.currencytracker.model.network;

import com.petarvelikov.currencytracker.BuildConfig;
import com.petarvelikov.currencytracker.consts.Constants;
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
    if (BuildConfig.FIXER_API_VERSION == 1) {
      return fixerApiService.getExchangeRates(base);
    } else {
      // 'EUR' is the only base currency supported on the free api access.
      return fixerApiService.getExchangeRates("EUR", Constants.API_CONSTANTS.ACCESS_KEY_FIXER);
    }
  }
}
