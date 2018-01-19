package com.petarvelikov.currencytracker.viewmodel;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MediatorLiveData;
import android.arch.lifecycle.ViewModel;
import android.support.annotation.NonNull;

import com.petarvelikov.currencytracker.model.CryptoCurrency;
import com.petarvelikov.currencytracker.model.network.CurrenciesDataRepository;

import javax.inject.Inject;

public class CurrencyDetailsViewModel extends ViewModel {

    private CurrenciesDataRepository dataRepository;
    private MediatorLiveData<CurrencyDetailsViewState> viewState;

    @Inject
    public CurrencyDetailsViewModel(CurrenciesDataRepository dataRepository) {
        this.dataRepository = dataRepository;
        this.viewState = new MediatorLiveData<>();
        this.viewState.setValue(new CurrencyDetailsViewState());
    }

    @NonNull
    public LiveData<CurrencyDetailsViewState> getViewState() {
        return this.viewState;
    }

    public void load(String currencyId, String convert, boolean isSwipeRefresh) {
        viewState.setValue(currentViewState()
                .setIsLoading(!isSwipeRefresh)
                .setHasError(false));
        viewState.addSource(dataRepository.getCurrencyById(currencyId, convert), apiResponse -> {
            if (apiResponse != null) {
                CryptoCurrency currency = apiResponse.getResponse();
                if (currency != null) {
                    viewState.setValue(currentViewState()
                            .setCryptoCurrency(currency)
                            .setIsLoading(false)
                            .setHasError(false));
                } else if (apiResponse.getError() != null) {
                    viewState.setValue(currentViewState()
                            .setIsLoading(false)
                            .setHasError(true));
                }
            } else {
                viewState.setValue(currentViewState()
                        .setIsLoading(false)
                        .setHasError(true));
            }

        });
    }

    public CurrencyDetailsViewState currentViewState() {
        return this.viewState.getValue();
    }
}
