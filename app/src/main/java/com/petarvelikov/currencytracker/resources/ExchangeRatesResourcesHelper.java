package com.petarvelikov.currencytracker.resources;

import com.petarvelikov.currencytracker.R;

import java.util.HashMap;
import java.util.Map;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class ExchangeRatesResourcesHelper {

    private ResourceProvider resourceProvider;
    private Map<String, String> currencyNames, currencySymbols, currencyCountries;

    @Inject
    public ExchangeRatesResourcesHelper(ResourceProvider resourceProvider) {
        this.resourceProvider = resourceProvider;
        prepareData(this.resourceProvider.getStringArray(R.array.available_currencies));
    }

    private void prepareData(String[] currencyData) {
        currencyNames = new HashMap<>();
        currencySymbols = new HashMap<>();
        currencyCountries = new HashMap<>();
        for (String currency : currencyData) {
            String[] parts = currency.split("\\|");
            currencyNames.put(parts[0], parts[1]);
            currencySymbols.put(parts[0], parts[2]);
            currencyCountries.put(parts[0], parts[3]);
        }
    }

    public boolean isCurrencyAvailable(String currency) {
        return currencyNames.containsKey(currency);
    }

    public String getCurrencyName(String currency) {
        String name = currencyNames.get(currency);
        if (name != null) {
            return name;
        }
        throw new IllegalArgumentException(resourceProvider.getString(R.string.currency_not_supported));
    }

    public String getCurrencySymbol(String currency) {
        String symbol = currencySymbols.get(currency);
        if (symbol != null) {
            return symbol;
        }
        throw new IllegalArgumentException(resourceProvider.getString(R.string.currency_not_supported));
    }

    public String getCurrencyCountry(String currency) {
        String country = currencyCountries.get(currency);
        if (country != null) {
            return country;
        }
        throw new IllegalArgumentException(resourceProvider.getString(R.string.currency_not_supported));
    }
}
