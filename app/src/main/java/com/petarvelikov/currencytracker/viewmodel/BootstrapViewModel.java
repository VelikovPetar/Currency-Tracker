package com.petarvelikov.currencytracker.viewmodel;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import android.support.annotation.NonNull;
import android.util.Log;

import com.petarvelikov.currencytracker.consts.Constants;
import com.petarvelikov.currencytracker.model.CurrencyIcon;
import com.petarvelikov.currencytracker.model.database.CurrencyDatabase;
import com.petarvelikov.currencytracker.model.network.CurrenciesDataRepository;
import com.petarvelikov.currencytracker.preferences.SharedPreferencesHelper;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import io.reactivex.Single;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class BootstrapViewModel extends ViewModel {

  private static final String TAG = BootstrapViewModel.class.getSimpleName();

  private CurrenciesDataRepository dataRepository;
  private CurrencyDatabase currencyDatabase;
  private MutableLiveData<BootstrapViewState> viewState;
  private CompositeDisposable compositeDisposable;
  private SharedPreferencesHelper sharedPreferencesHelper;

  @Inject
  public BootstrapViewModel(CurrenciesDataRepository dataRepository,
                            CurrencyDatabase currencyDatabase,
                            SharedPreferencesHelper sharedPreferencesHelper) {
    this.dataRepository = dataRepository;
    this.currencyDatabase = currencyDatabase;
    this.viewState = new MutableLiveData<>();
    this.viewState.setValue(new BootstrapViewState());
    this.compositeDisposable = new CompositeDisposable();
    this.sharedPreferencesHelper = sharedPreferencesHelper;
  }

  @Override
  protected void onCleared() {
    super.onCleared();
    compositeDisposable.clear();
  }

  @NonNull
  public LiveData<BootstrapViewState> getViewState() {
    return this.viewState;
  }

  public void load() {
    viewState.setValue(currentViewState()
        .setLoading(true)
        .setHasError(false)
        .setErrorMessage(null));
    Disposable d = Single.defer(() -> Single.just(currencyDatabase.currencyDao().getNumberOfIcons()))
        .toObservable()
        .takeWhile(rows -> {
          if (rows > 0 && !shouldUpdateIcons()) {
            setSuccess();
          }
          return rows == 0 || shouldUpdateIcons();
        })
        .flatMapSingle(rows -> dataRepository.getCurrencyIcons())
        .subscribeOn(Schedulers.io())
        .observeOn(Schedulers.io())
        .subscribe(response -> {
          Map<String, CurrencyIcon> data = response.getData();
          if (data != null) {
            List<CurrencyIcon> icons = new ArrayList<>();
            icons.addAll(data.values());
            currencyDatabase.currencyDao().insertMultiple(icons.toArray(new CurrencyIcon[0]));
            sharedPreferencesHelper.setLastTimeOfIconsLoaded(System.currentTimeMillis());
            setSuccess();
          }
        }, throwable -> {
          setFailure(throwable.getMessage());
        });
    compositeDisposable.add(d);
  }

  private BootstrapViewState currentViewState() {
    return this.viewState.getValue();
  }

  private void setSuccess() {
    viewState.postValue(currentViewState()
        .setLoading(false)
        .setHasError(false)
        .setErrorMessage(null));
  }

  private void setFailure(String message) {
    viewState.postValue(currentViewState()
        .setLoading(false)
        .setHasError(true)
        .setErrorMessage(message));
    Log.e(TAG, message);
  }

  private boolean shouldUpdateIcons() {
    long lastUpdateTime = sharedPreferencesHelper.getLastTimeOfIconsLoad();
    long currentTime = System.currentTimeMillis();
    return currentTime - lastUpdateTime >= Constants.API_CONSTANTS.ONE_WEEK_IN_MILLISECONDS;
  }
}
