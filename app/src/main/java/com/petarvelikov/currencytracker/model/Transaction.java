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
  private String coinName;

  @ColumnInfo(name = "currency_price")
  private double coinPrice;

  @ColumnInfo(name = "quantity")
  private double coinAmount;

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

  public Transaction() {

  }

  private Transaction(Builder builder) {
    this.coinName = builder.coinName;
    this.coinAmount = builder.coinAmount;
    this.coinPrice = builder.coinPrice;
    this.dateOfTransaction = builder.dateOfTransaction;
    this.isPurchase = builder.isPurchase ? 1 : 0;
    this.baseCurrency = builder.baseCurrency;
  }

  public int getUid() {
    return uid;
  }

  public void setUid(int uid) {
    this.uid = uid;
  }

  public String getCoinName() {
    return coinName;
  }

  public void setCoinName(String coinName) {
    this.coinName = coinName;
  }

  public double getCoinPrice() {
    return coinPrice;
  }

  public void setCoinPrice(double coinPrice) {
    this.coinPrice = coinPrice;
  }

  public double getCoinAmount() {
    return coinAmount;
  }

  public void setCoinAmount(double coinAmount) {
    this.coinAmount = coinAmount;
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
    if (Double.compare(that.coinPrice, coinPrice) != 0) return false;
    if (Double.compare(that.coinAmount, coinAmount) != 0) return false;
    if (dateOfTransaction != that.dateOfTransaction) return false;
    if (isPurchase != that.isPurchase) return false;
    return coinName != null ? coinName.equals(that.coinName) : that.coinName == null;
  }

  @Override
  public int hashCode() {
    int result;
    long temp;
    result = uid;
    result = 31 * result + (coinName != null ? coinName.hashCode() : 0);
    temp = Double.doubleToLongBits(coinPrice);
    result = 31 * result + (int) (temp ^ (temp >>> 32));
    temp = Double.doubleToLongBits(coinAmount);
    result = 31 * result + (int) (temp ^ (temp >>> 32));
    result = 31 * result + (int) (dateOfTransaction ^ (dateOfTransaction >>> 32));
    result = 31 * result + isPurchase;
    return result;
  }

  public static class Builder {
    private String coinName;
    private double coinAmount;
    private double coinPrice;
    private long dateOfTransaction;
    private boolean isPurchase;
    private String baseCurrency;

    public Builder setCoinName(String coinName) {
      this.coinName = coinName;
      return this;
    }

    public Builder setCoinAmount(double coinAmount) {
      this.coinAmount = coinAmount;
      return this;
    }

    public Builder setCoinPrice(double coinPrice) {
      this.coinPrice = coinPrice;
      return this;
    }

    public Builder setDateOfTransaction(long dateOfTransaction) {
      this.dateOfTransaction = dateOfTransaction;
      return this;
    }

    public Builder setIsPurchase(boolean purchase) {
      isPurchase = purchase;
      return this;
    }

    public Builder setBaseCurrency(String baseCurrency) {
      this.baseCurrency = baseCurrency;
      return this;
    }

    public Transaction build() {
      return new Transaction(this);
    }
  }
}
