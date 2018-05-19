package com.petarvelikov.currencytracker.viewmodel;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;

import com.petarvelikov.currencytracker.model.ExchangeRate;
import com.petarvelikov.currencytracker.model.ExchangeRatesResponse;
import com.petarvelikov.currencytracker.model.network.ExchangeRatesRepository;
import com.petarvelikov.currencytracker.resources.ExchangeRatesResourcesHelper;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

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
        setLoading(isSwipeRefresh);
        Disposable d = exchangeRatesRepository.getExchangeRates(base)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(exchangeRates -> setSuccess(exchangeRates, base),
                        throwable -> setError());
        compositeDisposable.add(d);
    }

    private void setLoading(boolean isSwipeRefresh) {
        viewState.setValue(currentViewState()
                .setIsLoading(!isSwipeRefresh)
                .setHasError(false));
    }

    private void setSuccess(ExchangeRatesResponse response, String base) {
        if (response == null || response.getRates() == null) {
            setError();
            return;
        }
        List<ExchangeRate> exchangeRates = convertExchangeRatesMapToList(response.getRates(), base);
        viewState.setValue(currentViewState()
                .setIsLoading(false)
                .setHasError(false)
                .setExchangeRates(exchangeRates));
    }

    private void setError() {
        viewState.setValue(currentViewState()
                .setIsLoading(false)
                .setHasError(true)
                .setExchangeRates(null));
    }

    private List<ExchangeRate> convertExchangeRatesMapToList(Map<String, Double> exchangeRatesMap, String to) {
        List<ExchangeRate> exchangeRates = new ArrayList<>();
        for (String from : exchangeRatesMap.keySet()) {
            if (resourcesHelper.isCurrencyAvailable(from)) {
                exchangeRates.add(new ExchangeRate(from, to, 1.0 / exchangeRatesMap.get(from)));
            }
        }
        return exchangeRates;
    }

    private ExchangeRatesViewState currentViewState() {
        return this.viewState.getValue();
    }
}
