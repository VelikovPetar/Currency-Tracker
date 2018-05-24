package com.petarvelikov.currencytracker.model;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Index;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

@Entity(tableName = "icons", indices = {@Index(value = "symbol", unique = true)})
public class CurrencyIcon {

  @PrimaryKey(autoGenerate = true)
  private int uid;

  @ColumnInfo(name = "symbol")
  @SerializedName("Symbol")
  @Expose
  private String symbol;

  @ColumnInfo(name = "name")
  @SerializedName("CoinName")
  @Expose
  @NonNull
  private String name = "";

  @ColumnInfo(name = "imageUrl")
  @SerializedName("ImageUrl")
  @Expose
  private String imageUrl;

  public int getUid() {
    return uid;
  }

  public void setUid(int uid) {
    this.uid = uid;
  }

  public String getSymbol() {
    return symbol;
  }

  public void setSymbol(String symbol) {
    this.symbol = symbol;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getImageUrl() {
    return imageUrl;
  }

  public void setImageUrl(String imageUrl) {
    this.imageUrl = imageUrl;
  }

  @Override
  public String toString() {
    return name;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;

    CurrencyIcon that = (CurrencyIcon) o;

    return name.equals(that.name);
  }

  @Override
  public int hashCode() {
    return name.hashCode();
  }
}
