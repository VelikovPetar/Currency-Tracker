package com.petarvelikov.currencytracker.viewmodel;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;

import com.petarvelikov.currencytracker.model.ExchangeRate;
import com.petarvelikov.currencytracker.model.network.ExchangeRatesRepository;
import com.petarvelikov.currencytracker.resources.ExchangeRatesResourcesHelper;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class ExchangeRatesViewModel extends ViewModel {

    private ExchangeRatesRepository exchangeRatesRepository;
    private MutableLiveData<ExchangeRatesViewState> viewState;
    private CompositeDisposable compositeDisposable;
    private ExchangeRatesResourcesHelper resourcesHelper;

    @Inject
    public ExchangeRatesViewModel(ExchangeRatesRepository exchangeRatesRepository,
                                  ExchangeRatesResourcesHelper resourcesHelper) {
        this.exchangeRatesRepository = exchangeRatesRepository;
        this.viewState = new MutableLiveData<>();
        this.viewState.setValue(new ExchangeRatesViewState());
        this.compositeDisposable = new CompositeDisposable();
        this.resourcesHelper = resourcesHelper;
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        this.compositeDisposable.clear();
    }

    public LiveData<ExchangeRatesViewState> getViewState() {
        return this.viewState;
    }

    public void load(String base, boolean isSwipeRefresh) {
        viewState.setValue(currentViewState()
                .setIsLoading(!isSwipeRefresh)
                .setHasError(false));
        Disposable d = exchangeRatesRepository.getExchangeRates(base)
                .filter(response -> response.getRates() != null)
                .map(response -> {
                    List<ExchangeRate> exchangeRates = new ArrayList<>();
                    for (String key : response.getRates().keySet()) {
                        if (resourcesHelper.isCurrencyAvailable(key)) {
                            exchangeRates.add(new ExchangeRate(key, base, 1.0 / response.getRates().get(key)));
                        }
                    }
                    return exchangeRates;
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(exchangeRates -> {
                    viewState.setValue(currentViewState()
                            .setIsLoading(false)
                            .setHasError(false)
                            .setExchangeRates(exchangeRates));
                }, throwable -> {
                    viewState.setValue(currentViewState()
                            .setIsLoading(false)
                            .setHasError(true)
                            .setExchangeRates(null));
                });
        compositeDisposable.add(d);
    }

    private ExchangeRatesViewState currentViewState() {
        return this.viewState.getValue();
    }
}
