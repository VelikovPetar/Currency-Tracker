package com.petarvelikov.currencytracker.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class HistoricalDataRecord {

  @SerializedName("time")
  @Expose
  private long time;
  @SerializedName("open")
  @Expose
  private double open;

  public long getTime() {
    return time;
  }

  public void setTime(long time) {
    this.time = time;
  }

  public double getOpen() {
    return open;
  }

  public void setOpen(double open) {
    this.open = open;
  }
}
