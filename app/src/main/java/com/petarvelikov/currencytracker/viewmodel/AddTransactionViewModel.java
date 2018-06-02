package com.petarvelikov.currencytracker.viewmodel;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import android.util.Log;

import com.petarvelikov.currencytracker.model.CryptoCurrency;
import com.petarvelikov.currencytracker.model.Transaction;
import com.petarvelikov.currencytracker.model.TransactionStatus;
import com.petarvelikov.currencytracker.model.database.CurrencyDatabase;
import com.petarvelikov.currencytracker.model.network.CurrenciesDataRepository;
import com.petarvelikov.currencytracker.preferences.SharedPreferencesHelper;
import com.petarvelikov.currencytracker.utils.CalendarUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import javax.inject.Inject;

import io.reactivex.Completable;
import io.reactivex.CompletableObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class AddTransactionViewModel extends ViewModel {

  private static final String TAG = AddTransactionViewModel.class.getSimpleName();

  private MutableLiveData<List<CryptoCurrency>> currencies;
  private MutableLiveData<TransactionStatus> transactionStatus;
  private CurrenciesDataRepository dataRepository;
  private CurrencyDatabase currencyDatabase;
  private SharedPreferencesHelper sharedPreferencesHelper;
  private CompositeDisposable compositeDisposable;

  @Inject
  public AddTransactionViewModel(CurrenciesDataRepository dataRepository,
                                 CurrencyDatabase currencyDatabase,
                                 SharedPreferencesHelper sharedPreferencesHelper) {
    this.dataRepository = dataRepository;
    this.currencyDatabase = currencyDatabase;
    this.sharedPreferencesHelper = sharedPreferencesHelper;
    this.currencies = new MutableLiveData<>();
    this.currencies.setValue(new ArrayList<>());
    this.transactionStatus = new MutableLiveData<>();
    this.transactionStatus.setValue(TransactionStatus.INITIAL);
    this.compositeDisposable = new CompositeDisposable();
  }

  @Override
  protected void onCleared() {
    super.onCleared();
    compositeDisposable.clear();
  }

  public LiveData<List<CryptoCurrency>> getCurrencies() {
    return currencies;
  }

  public LiveData<TransactionStatus> getTransactionStatus() {
    return transactionStatus;
  }

  public void loadAvailableCoins() {
    Disposable d = dataRepository.getAllCurrencies(0, -1, sharedPreferencesHelper.getBaseCurrency())
        .subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
        .subscribe(
            cryptoCurrencies -> currencies.setValue(cryptoCurrencies),
            throwable -> {
            });
    compositeDisposable.add(d);
  }

  public void onCompleteTransactionDataEntered(String coinName, double coinAmount,
                                               double coinPrice, String dateString, boolean isPurchase) {
    SimpleDateFormat dateFormat = new SimpleDateFormat("dd.mm.yyyy", Locale.ENGLISH);
    Date date;
    try {
      date = dateFormat.parse(dateString);
    } catch (ParseException e) {
      Log.e(TAG, "Invalid date format. Using today.");
      date = new Date();
    }
    long timestamp = CalendarUtils.millisecondsFromDate(date);
    String baseCurrency = sharedPreferencesHelper.getBaseCurrency();
    Transaction transaction = buildTransactionModel(coinName, coinAmount, coinPrice, timestamp,
        isPurchase, baseCurrency);
    saveTransaction(transaction);
  }

  public void onCancelTransaction() {

  }

  private void saveTransaction(Transaction transaction) {
    transactionStatus.setValue(TransactionStatus.LOADING);
    Completable.fromAction(() -> currencyDatabase.transactionsDao().insert(transaction))
        .subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
        .subscribe(new CompletableObserver() {
          @Override
          public void onSubscribe(Disposable d) {
            compositeDisposable.add(d);
          }

          @Override
          public void onComplete() {
            transactionStatus.setValue(TransactionStatus.SUCCESS);
          }

          @Override
          public void onError(Throwable e) {
            transactionStatus.setValue(TransactionStatus.ERROR);
            Log.e(TAG, "Error inserting transaction into database.");
            if (e != null && e.getMessage() != null) {
              Log.e(TAG, e.getMessage());
            } else {
              Log.e(TAG, "Unknown database error.");
            }
          }
        });

  }

  private Transaction buildTransactionModel(String coinName, double coinAmount,
                                            double coinPrice, long timestamp,
                                            boolean isPurchase, String baseCurrency) {
    Transaction.Builder builder = new Transaction.Builder()
        .setCoinName(coinName)
        .setCoinAmount(coinAmount)
        .setCoinPrice(coinPrice)
        .setDateOfTransaction(timestamp)
        .setIsPurchase(isPurchase)
        .setBaseCurrency(baseCurrency);
    return builder.build();
  }
}
