package com.petarvelikov.currencytracker.viewmodel;

import android.support.annotation.NonNull;

public class BootstrapViewState {

  private boolean isLoading;
  private boolean hasError;
  private String errorMessage;

  public boolean isLoading() {
    return isLoading;
  }

  @NonNull
  public BootstrapViewState setLoading(boolean isLoading) {
    this.isLoading = isLoading;
    return this;
  }

  public boolean hasError() {
    return hasError;
  }

  public String getErrorMessage() {
    return errorMessage;
  }

  @NonNull
  public BootstrapViewState setErrorMessage(String errorMessage) {
    this.errorMessage = errorMessage;
    return this;
  }

  @NonNull
  public BootstrapViewState setHasError(boolean hasError) {
    this.hasError = hasError;
    return this;
  }
}
