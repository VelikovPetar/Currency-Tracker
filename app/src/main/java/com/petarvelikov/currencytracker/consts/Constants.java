package com.petarvelikov.currencytracker.consts;

public class Constants {

    public interface ERROR {
        String HTTP_404_NOT_FOUND = "HTTP 404";
    }

    public interface API_CONSTANTS {
        int LIMIT = 50;
        String BASE_URL_COIN_MARKET_CAP = "https://api.coinmarketcap.com/v1/";
        String BASE_URL_CRYPTO_COMPARE = "https://min-api.cryptocompare.com/";
        String BASE_URL_ICONS = "https://www.cryptocompare.com";
        String BASE_URL_FIXER = "https://api.fixer.io/";
        String BASE_URL_COUNTRY_FLAGS = "http://www.countryflags.io/%country_code%/flat/48.png";
    }
}
