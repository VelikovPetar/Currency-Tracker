package com.petarvelikov.currencytracker.viewmodel;

import android.support.annotation.NonNull;

import com.petarvelikov.currencytracker.model.CryptoCurrency;

import java.util.List;
import java.util.Map;

public class CurrencyDetailsViewState {

  private CryptoCurrency cryptoCurrency;
  private boolean isLoading;
  private boolean hasError;
  private boolean isLoadingChart;
  private boolean hasChartError;
  private Map<Float, Double> chartData;
  private List<String> chartLabels;

  public CurrencyDetailsViewState() {

  }

  public CryptoCurrency getCryptoCurrency() {
    return cryptoCurrency;
  }

  @NonNull
  public CurrencyDetailsViewState setCryptoCurrency(CryptoCurrency cryptoCurrency) {
    this.cryptoCurrency = cryptoCurrency;
    return this;
  }

  public boolean isLoading() {
    return isLoading;
  }

  public boolean hasError() {
    return hasError;
  }

  public boolean isLoadingChart() {
    return this.isLoadingChart;
  }

  public boolean hasChartError() {
    return this.hasChartError;
  }

  public Map<Float, Double> getChartData() {
    return this.chartData;
  }

  @NonNull
  public CurrencyDetailsViewState setChartData(Map<Float, Double> chartData) {
    this.chartData = chartData;
    return this;
  }

  public List<String> getChartLabels() {
    return this.chartLabels;
  }

  @NonNull
  public CurrencyDetailsViewState setChartLabels(List<String> chartLabels) {
    this.chartLabels = chartLabels;
    return this;
  }

  @NonNull
  public CurrencyDetailsViewState setIsLoading(boolean isLoading) {
    this.isLoading = isLoading;
    return this;
  }

  @NonNull
  public CurrencyDetailsViewState setHasError(boolean hasError) {
    this.hasError = hasError;
    return this;
  }

  @NonNull
  public CurrencyDetailsViewState setIsLoadingChart(boolean isLoadingChart) {
    this.isLoadingChart = isLoadingChart;
    return this;
  }

  @NonNull
  CurrencyDetailsViewState setHasChartError(boolean hasChartError) {
    this.hasChartError = hasChartError;
    return this;
  }
}
