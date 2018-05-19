package com.petarvelikov.currencytracker.model;

public class ExchangeRate {

  private String from, to;
  private double exchangeRate;

  public ExchangeRate(String from, String to, double exchangeRate) {
    this.from = from;
    this.to = to;
    this.exchangeRate = exchangeRate;
  }

  public String getFrom() {
    return from;
  }

  public String getTo() {
    return to;
  }

  public double getExchangeRate() {
    return exchangeRate;
  }
}
