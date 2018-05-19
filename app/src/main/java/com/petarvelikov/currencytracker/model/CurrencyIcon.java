package com.petarvelikov.currencytracker.model;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Index;
import android.arch.persistence.room.PrimaryKey;

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

  public String getImageUrl() {
    return imageUrl;
  }

  public void setImageUrl(String imageUrl) {
    this.imageUrl = imageUrl;
  }
}
