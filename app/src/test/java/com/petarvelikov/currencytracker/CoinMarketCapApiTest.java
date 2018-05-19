package com.petarvelikov.currencytracker;

import com.petarvelikov.currencytracker.consts.Constants;
import com.petarvelikov.currencytracker.model.CryptoCurrency;
import com.petarvelikov.currencytracker.model.network.CoinMarketCapApiService;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

import static junit.framework.Assert.assertNotNull;

@RunWith(JUnit4.class)
public class CoinMarketCapApiTest {

  private CoinMarketCapApiService service;

  @Before
  public void setUp() {
    Retrofit retrofit = new Retrofit.Builder()
        .baseUrl(Constants.API_CONSTANTS.BASE_URL_COIN_MARKET_CAP)
        .addConverterFactory(GsonConverterFactory.create())
        .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
        .build();
    service = retrofit.create(CoinMarketCapApiService.class);
  }

  @Test
  public void testResponseIsCorrectForDifferentCurrencyRequest() {
    service.getCurrencyById("bitcoin", "EUR")
        .subscribe(currencies -> {
          CryptoCurrency cryptoCurrency = currencies.get(0);
          assertNotNull(cryptoCurrency.getPriceAnyCurrency("EUR"));
          assertNotNull(cryptoCurrency.get24hVolumeAnyCurrency("EUR"));
          assertNotNull(cryptoCurrency.getMarketCapAnyCurrency("EUR"));
          System.out.print(cryptoCurrency.getPriceAnyCurrency("EUR"));
        });
  }
}
