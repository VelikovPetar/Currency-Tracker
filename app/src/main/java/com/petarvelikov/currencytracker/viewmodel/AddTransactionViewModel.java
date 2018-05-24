package com.petarvelikov.currencytracker.viewmodel;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;

import com.petarvelikov.currencytracker.model.CryptoCurrency;
import com.petarvelikov.currencytracker.model.network.CurrenciesDataRepository;
import com.petarvelikov.currencytracker.preferences.SharedPreferencesHelper;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class AddTransactionViewModel extends ViewModel {

  private static final String TAG = AddTransactionViewModel.class.getSimpleName();

  private MutableLiveData<List<CryptoCurrency>> currencies;
  private CurrenciesDataRepository dataRepository;
  private SharedPreferencesHelper sharedPreferencesHelper;

  @Inject
  public AddTransactionViewModel(CurrenciesDataRepository dataRepository,
                                 SharedPreferencesHelper sharedPreferencesHelper) {
    this.dataRepository = dataRepository;
    this.sharedPreferencesHelper = sharedPreferencesHelper;
    this.currencies = new MutableLiveData<>();
    this.currencies.setValue(new ArrayList<>());
  }

  public LiveData<List<CryptoCurrency>> getCurrencies() {
    return currencies;
  }

  public void loadAvailableCoins() {
    dataRepository.getAllCurrencies(0, -1, sharedPreferencesHelper.getBaseCurrency())
        .subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
        .subscribe(
            cryptoCurrencies -> {
              currencies.setValue(cryptoCurrencies);
            },
            throwable -> {
            });
  }
}
