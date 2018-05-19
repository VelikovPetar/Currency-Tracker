package com.petarvelikov.currencytracker.model;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
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

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;

    Transaction that = (Transaction) o;

    if (uid != that.uid) return false;
    if (Double.compare(that.currencyPrice, currencyPrice) != 0) return false;
    if (Double.compare(that.quantity, quantity) != 0) return false;
    if (dateOfTransaction != that.dateOfTransaction) return false;
    return currency.equals(that.currency);
  }

  @Override
  public int hashCode() {
    int result;
    long temp;
    result = uid;
    result = 31 * result + currency.hashCode();
    temp = Double.doubleToLongBits(currencyPrice);
    result = 31 * result + (int) (temp ^ (temp >>> 32));
    temp = Double.doubleToLongBits(quantity);
    result = 31 * result + (int) (temp ^ (temp >>> 32));
    result = 31 * result + (int) (dateOfTransaction ^ (dateOfTransaction >>> 32));
    return result;
  }
}
