package com.petarvelikov.currencytracker.model;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;

@Entity(tableName = "transactions")
public class Transaction {

  @PrimaryKey(autoGenerate = true)
  private int uid;

  @ColumnInfo(name = "currency")
  private String currency;

  @ColumnInfo(name = "currency_price")
  private double currencyPrice;

  @ColumnInfo(name = "quantity")
  private double quantity;

  @ColumnInfo(name = "date_of_transaction")
  private long dateOfTransaction;

  @ColumnInfo(name = "is_purchase")
  private int isPurchase = 0;

  @ColumnInfo(name = "base_currency")
  private String baseCurrency;

  @Ignore
  private double currentCoinValue;

  @Ignore
  private String coinImageUrl;

  public int getUid() {
    return uid;
  }

  public void setUid(int uid) {
    this.uid = uid;
  }

  public String getCurrency() {
    return currency;
  }

  public void setCurrency(String currency) {
    this.currency = currency;
  }

  public double getCurrencyPrice() {
    return currencyPrice;
  }

  public void setCurrencyPrice(double currencyPrice) {
    this.currencyPrice = currencyPrice;
  }

  public double getQuantity() {
    return quantity;
  }

  public void setQuantity(double quantity) {
    this.quantity = quantity;
  }

  public long getDateOfTransaction() {
    return dateOfTransaction;
  }

  public void setDateOfTransaction(long dateOfTransaction) {
    this.dateOfTransaction = dateOfTransaction;
  }

  public int getIsPurchase() {
    return isPurchase;
  }

  public void setIsPurchase(int isPurchase) {
    this.isPurchase = isPurchase;
  }

  public String getBaseCurrency() {
    return baseCurrency;
  }

  public void setBaseCurrency(String baseCurrency) {
    this.baseCurrency = baseCurrency;
  }

  public double getCurrentCoinValue() {
    return currentCoinValue;
  }

  public void setCurrentCoinValue(double currentCoinValue) {
    this.currentCoinValue = currentCoinValue;
  }

  public String getCoinImageUrl() {
    return coinImageUrl;
  }

  public void setCoinImageUrl(String coinImageUrl) {
    this.coinImageUrl = coinImageUrl;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;

    Transaction that = (Transaction) o;

    if (uid != that.uid) return false;
    if (Double.compare(that.currencyPrice, currencyPrice) != 0) return false;
    if (Double.compare(that.quantity, quantity) != 0) return false;
    if (dateOfTransaction != that.dateOfTransaction) return false;
    if (isPurchase != that.isPurchase) return false;
    return currency != null ? currency.equals(that.currency) : that.currency == null;
  }

  @Override
  public int hashCode() {
    int result;
    long temp;
    result = uid;
    result = 31 * result + (currency != null ? currency.hashCode() : 0);
    temp = Double.doubleToLongBits(currencyPrice);
    result = 31 * result + (int) (temp ^ (temp >>> 32));
    temp = Double.doubleToLongBits(quantity);
    result = 31 * result + (int) (temp ^ (temp >>> 32));
    result = 31 * result + (int) (dateOfTransaction ^ (dateOfTransaction >>> 32));
    result = 31 * result + isPurchase;
    return result;
  }
}
