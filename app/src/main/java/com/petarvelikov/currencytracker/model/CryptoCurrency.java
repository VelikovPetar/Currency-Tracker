package com.petarvelikov.currencytracker.model;


import java.util.HashMap;

public class CryptoCurrency extends HashMap<String, String> {

  private static final String TAG = "CryptoCurrency";

  private static final String CURRENCY_PLACEHOLDER = "%currency%";

  private static final String KEY_ID = "id";
  private static final String KEY_NAME = "name";
  private static final String KEY_SYMBOL = "symbol";
  private static final String KEY_RANK = "rank";
  private static final String KEY_PRICE_USD = "price_usd";
  private static final String KEY_PRICE_BTC = "price_btc";
  private static final String KEY_24H_VOLUME_USD = "24h_volume_usd";
  private static final String KEY_MARKET_CAP_USD = "market_cap_usd";
  private static final String KEY_TOTAL_SUPPLY = "total_supply";
  private static final String KEY_AVAILABLE_SUPPLY = "available_supply";
  private static final String KEY_MAX_SUPPLY = "max_supply";
  private static final String KEY_PERCENT_CHANGE_1H = "percent_change_1h";
  private static final String KEY_PERCENT_CHANGE_24H = "percent_change_24h";
  private static final String KEY_PERCENT_CHANGE_7D = "percent_change_7d";
  private static final String KEY_LAST_UPDATED = "last_updated";
  private static final String KEY_PRICE_ANY_CURRENCY = "price_" + CURRENCY_PLACEHOLDER;
  private static final String KEY_24H_VOLUME_ANY_CURRENCY = "24h_volume_" + CURRENCY_PLACEHOLDER;
  private static final String KEY_MARKET_CAP_ANY_CURRENCY = "market_cap_" + CURRENCY_PLACEHOLDER;


  private String imageUrl;

  public String getId() {
    return get(KEY_ID);
  }

  public void setId(String id) {
    put(KEY_ID, id);
  }

  public String getName() {
    return get(KEY_NAME);
  }

  public void setName(String name) {
    put(KEY_NAME, name);
  }

  public String getSymbol() {
    return get(KEY_SYMBOL);
  }

  public void setSymbol(String symbol) {
    put(KEY_SYMBOL, symbol);
  }

  public String getRank() {
    return get(KEY_RANK);
  }

  public void setRank(String rank) {
    put(KEY_RANK, rank);
  }

  public String getPriceUsd() {
    return get(KEY_PRICE_USD);
  }

  public void setPriceUsd(String priceUsd) {
    put(KEY_PRICE_USD, priceUsd);
  }

  public String getPriceBtc() {
    return get(KEY_PRICE_BTC);
  }

  public void setPriceBtc(String priceBtc) {
    put(KEY_PRICE_BTC, priceBtc);
  }

  public String get24hVolumeUsd() {
    return get(KEY_24H_VOLUME_USD);
  }

  public void set24hVolumeUsd(String _24hVolumeUsd) {
    put(KEY_24H_VOLUME_USD, _24hVolumeUsd);
  }

  public String getMarketCapUsd() {
    return get(KEY_MARKET_CAP_USD);
  }

  public void setMarketCapUsd(String marketCapUsd) {
    put(KEY_MARKET_CAP_USD, marketCapUsd);
  }

  public String getAvailableSupply() {
    return get(KEY_AVAILABLE_SUPPLY);
  }

  public void setAvailableSupply(String availableSupply) {
    put(KEY_AVAILABLE_SUPPLY, availableSupply);
  }

  public String getTotalSupply() {
    return get(KEY_TOTAL_SUPPLY);
  }

  public void setTotalSupply(String totalSupply) {
    put(KEY_TOTAL_SUPPLY, totalSupply);
  }

  public String getMaxSupply() {
    return get(KEY_MAX_SUPPLY);
  }

  public void setMaxSupply(String maxSupply) {
    put(KEY_MAX_SUPPLY, maxSupply);
  }

  public String getPercentChange1h() {
    return get(KEY_PERCENT_CHANGE_1H);
  }

  public void setPercentChange1h(String percentChange1h) {
    put(KEY_PERCENT_CHANGE_1H, percentChange1h);
  }

  public String getPercentChange24h() {
    return get(KEY_PERCENT_CHANGE_24H);
  }

  public void setPercentChange24h(String percentChange24h) {
    put(KEY_PERCENT_CHANGE_24H, percentChange24h);
  }

  public String getPercentChange7d() {
    return get(KEY_PERCENT_CHANGE_7D);
  }

  public void setPercentChange7d(String percentChange7d) {
    put(KEY_PERCENT_CHANGE_7D, percentChange7d);
  }

  public String getLastUpdated() {
    return get(KEY_LAST_UPDATED);
  }

  public void setLastUpdated(String lastUpdated) {
    put(KEY_LAST_UPDATED, lastUpdated);
  }

  public String getPriceAnyCurrency(String currency) {
    String key = createKey(KEY_PRICE_ANY_CURRENCY, currency);
    return get(key);
  }

  public void setPriceAnyCurrency(String currency, String price) {
    String key = createKey(KEY_PRICE_ANY_CURRENCY, currency);
    put(key, price);
  }

  public String get24hVolumeAnyCurrency(String currency) {
    String key = createKey(KEY_24H_VOLUME_ANY_CURRENCY, currency);
    return get(key);
  }

  public void set24hVolumeANyCurrency(String currency, String volume) {
    String key = createKey(KEY_24H_VOLUME_ANY_CURRENCY, currency);
    put(key, volume);
  }

  public String getMarketCapAnyCurrency(String currency) {
    String key = createKey(KEY_MARKET_CAP_ANY_CURRENCY, currency);
    return get(key);
  }

  public void setMarketCapAnyCurrency(String currency, String marketCap) {
    String key = createKey(KEY_MARKET_CAP_ANY_CURRENCY, currency);
    put(key, marketCap);
  }

  private String createKey(String base, String replacement) {
    return base.replace(CURRENCY_PLACEHOLDER, replacement.toLowerCase().trim());
  }

  public String getImageUrl() {
    return imageUrl;
  }

  public void setImageUrl(String imageUrl) {
    this.imageUrl = imageUrl;
  }

}
