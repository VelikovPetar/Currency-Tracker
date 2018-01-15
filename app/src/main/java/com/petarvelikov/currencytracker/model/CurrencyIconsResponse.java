package com.petarvelikov.currencytracker.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.Map;

public class CurrencyIconsResponse {

    @SerializedName("Response")
    @Expose
    private String response;

    @SerializedName("Data")
    @Expose
    private Map<String, CurrencyIcon> data;

    public String getResponse() {
        return response;
    }

    public void setResponse(String response) {
        this.response = response;
    }

    public Map<String, CurrencyIcon> getData() {
        return data;
    }

    public void setData(Map<String, CurrencyIcon> data) {
        this.data = data;
    }
}
