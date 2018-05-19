package com.petarvelikov.currencytracker.viewmodel;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import android.util.Log;

import com.petarvelikov.currencytracker.model.Transaction;
import com.petarvelikov.currencytracker.model.database.CurrencyDatabase;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Single;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class TransactionsViewModel extends ViewModel {

  private static final String TAG = TransactionsViewModel.class.getSimpleName();

  private MutableLiveData<TransactionsViewState> viewState;
  private CurrencyDatabase currencyDatabase;
  private CompositeDisposable compositeDisposable;

  @Inject
  public TransactionsViewModel(CurrencyDatabase currencyDatabase) {
    this.currencyDatabase = currencyDatabase;
    this.viewState = new MutableLiveData<>();
    this.viewState.setValue(TransactionsViewState.initial());
    this.compositeDisposable = new CompositeDisposable();
  }

  @Override
  protected void onCleared() {
    super.onCleared();
    compositeDisposable.clear();
  }

  public LiveData<TransactionsViewState> getViewState() {
    return this.viewState;
  }

  public void loadTransactions() {
    viewState.setValue(TransactionsViewState.loading());
    Disposable d = Single.defer(() -> Single.just(currencyDatabase.transactionsDao().getAll()))
        .subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
        .subscribe(
            this::handleSuccess,
            this::handleError);
    compositeDisposable.add(d);
  }

  private void handleSuccess(List<Transaction> transactions) {
    viewState.setValue(TransactionsViewState.success(transactions));
  }

  private void handleError(Throwable throwable) {
    Log.e(TAG, throwable != null ? throwable.getMessage() : "Error reading from database.");
    viewState.setValue(TransactionsViewState.error());
  }
}
