package com.petarvelikov.currencytracker.viewmodel;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import android.support.annotation.NonNull;

import com.petarvelikov.currencytracker.consts.Constants;
import com.petarvelikov.currencytracker.model.network.CurrenciesDataRepository;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;

public class CryptoCurrenciesViewModel extends ViewModel {

    private CurrenciesDataRepository dataRepository;
    private MutableLiveData<CryptoCurrenciesViewState> viewState;
    private CompositeDisposable compositeDisposable;

    @Inject
    public CryptoCurrenciesViewModel(CurrenciesDataRepository dataRepository) {
        this.dataRepository = dataRepository;
        this.viewState = new MutableLiveData<>();
        this.viewState.setValue(new CryptoCurrenciesViewState());
        this.compositeDisposable = new CompositeDisposable();
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        compositeDisposable.clear();
    }

    @NonNull
    public LiveData<CryptoCurrenciesViewState> getViewState() {
        return this.viewState;
    }

    public void loadCurrencies(int start, int limit, String convert, boolean isSwipeRefresh) {
        viewState.setValue(currentViewState()
                .setLoading(!isSwipeRefresh)
                .setHasError(false));
        Disposable d = dataRepository.getAllCurrencies(start, limit, convert)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(response -> {
                    if (start == 0) {
                        viewState.setValue(currentViewState()
                                .setCryptoCurrencies(response)
                                .setLoading(false)
                                .setHasError(false));
                    } else {
                        viewState.setValue(currentViewState()
                                .addCryptoCurrencies(response)
                                .setLoading(false)
                                .setHasError(false));
                    }
                }, throwable -> {
                    String message = throwable.getMessage();
                    // TODO Test if this works properly
                    if (Constants.ERROR.HTTP_404_NOT_FOUND.equals(message.trim())) {
                        viewState.setValue(currentViewState()
                                .setLoading(false)
                                .setEndReached(true)
                                .setHasError(false));
                    } else {
                        viewState.setValue(currentViewState()
                                .setLoading(false)
                                .setEndReached(false)
                                .setHasError(true));
                    }
                });
        compositeDisposable.add(d);
    }

    private CryptoCurrenciesViewState currentViewState() {
        return this.viewState.getValue();
    }
}
