package com.petarvelikov.currencytracker.viewmodel;

import android.support.annotation.NonNull;

import com.petarvelikov.currencytracker.model.Transaction;

import java.util.ArrayList;
import java.util.List;

public class TransactionsViewState {

  private List<Transaction> transactions;
  private boolean isLoading;
  private boolean hasError;

  @NonNull
  public static TransactionsViewState initial() {
    return success(new ArrayList<>());
  }

  @NonNull
  public static TransactionsViewState loading() {
    return new TransactionsViewState(null, true, false);
  }

  @NonNull
  public static TransactionsViewState error() {
    return new TransactionsViewState(null, false, true);
  }

  @NonNull
  public static TransactionsViewState success(List<Transaction> transactions) {
    return new TransactionsViewState(transactions, false, false);
  }

  private TransactionsViewState(List<Transaction> transactions, boolean isLoading, boolean hasError) {
    this.transactions = transactions;
    this.isLoading = isLoading;
    this.hasError = hasError;
  }

  public List<Transaction> getTransactions() {
    return transactions;
  }

  public boolean isLoading() {
    return isLoading;
  }

  public boolean hasError() {
    return hasError;
  }
}
