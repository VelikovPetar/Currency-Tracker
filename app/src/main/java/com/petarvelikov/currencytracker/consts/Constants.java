package com.petarvelikov.currencytracker.consts;

public class Constants {

    public interface ERROR {
        String HTTP_404_NOT_FOUND = "404";
    }

    public interface API_CONSTANTS {
        int LIMIT = 50;
        String BASE_URL_COIN_MARKET_CAP = "https://api.coinmarketcap.com/v1/";
        String BASE_URL_CRYPTO_COMPARE = "https://min-api.cryptocompare.com/";
    }
}
