package com.petarvelikov.currencytracker.viewmodel;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import android.support.annotation.NonNull;

import com.petarvelikov.currencytracker.model.CurrencyIcon;
import com.petarvelikov.currencytracker.model.database.CurrencyDatabase;
import com.petarvelikov.currencytracker.model.network.CurrenciesDataRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import io.reactivex.Single;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class BootstrapViewModel extends ViewModel {

  private CurrenciesDataRepository dataRepository;
  private CurrencyDatabase currencyDatabase;
  private MutableLiveData<BootstrapViewState> viewState;
  private CompositeDisposable compositeDisposable;

  @Inject
  public BootstrapViewModel(CurrenciesDataRepository dataRepository, CurrencyDatabase currencyDatabase) {
    this.dataRepository = dataRepository;
    this.currencyDatabase = currencyDatabase;
    this.viewState = new MutableLiveData<>();
    this.viewState.setValue(new BootstrapViewState());
    this.compositeDisposable = new CompositeDisposable();
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
          if (rows > 0) {
            setSuccess();
          }
          return rows == 0;
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
  }
}
