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
        String BASE_URL_FIXER = "http://data.fixer.io/api/";
        String ACCESS_KEY_FIXER = "5b369c8be90cfe8add19c07bb069a26d";
        String BASE_URL_COUNTRY_FLAGS = "http://www.countryflags.io/%country_code%/flat/48.png";
    }
}
