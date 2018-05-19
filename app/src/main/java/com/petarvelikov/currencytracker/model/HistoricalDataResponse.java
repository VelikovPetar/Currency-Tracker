package com.petarvelikov.currencytracker.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class HistoricalDataResponse {

  @SerializedName("Response")
  @Expose
  private String response;

  @SerializedName("Data")
  @Expose
  private List<HistoricalDataRecord> data;

  public String getResponse() {
    return response;
  }

  public void setResponse(String response) {
    this.response = response;
  }

  public List<HistoricalDataRecord> getData() {
    return data;
  }

  public void setData(List<HistoricalDataRecord> data) {
    this.data = data;
  }
}
