package com.petarvelikov.currencytracker.viewmodel;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import android.support.annotation.NonNull;

import com.petarvelikov.currencytracker.model.network.CurrenciesDataRepository;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class CurrencyDetailsViewModel extends ViewModel {

    private CurrenciesDataRepository dataRepository;
    private MutableLiveData<CurrencyDetailsViewState> viewState;
    private CompositeDisposable compositeDisposable;

    @Inject
    public CurrencyDetailsViewModel(CurrenciesDataRepository dataRepository) {
        this.dataRepository = dataRepository;
        this.viewState = new MutableLiveData<>();
        this.viewState.setValue(new CurrencyDetailsViewState());
        this.compositeDisposable = new CompositeDisposable();
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        compositeDisposable.clear();
    }

    @NonNull
    public LiveData<CurrencyDetailsViewState> getViewState() {
        return this.viewState;
    }

    public void load(String currencyId, String convert, boolean isSwipeRefresh) {
        viewState.setValue(currentViewState()
                .setIsLoading(!isSwipeRefresh)
                .setHasError(false));
        Disposable d = dataRepository.getCurrencyById(currencyId, convert)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(currency -> {
                    viewState.setValue(currentViewState()
                            .setCryptoCurrency(currency)
                            .setIsLoading(false)
                            .setHasError(false));
                }, throwable -> {
                    viewState.setValue(currentViewState()
                            .setIsLoading(false)
                            .setHasError(true));
                });
        compositeDisposable.add(d);
    }

    public CurrencyDetailsViewState currentViewState() {
        return this.viewState.getValue();
    }
}
