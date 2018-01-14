package com.petarvelikov.currencytracker.viewmodel;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MediatorLiveData;
import android.arch.lifecycle.ViewModel;
import android.util.Log;

import com.petarvelikov.currencytracker.model.rest.CryptoCurrenciesApiRepository;

import javax.inject.Inject;

public class CryptoCurrenciesViewModel extends ViewModel {

    private CryptoCurrenciesApiRepository apiRepository;
    private MediatorLiveData<CryptoCurrenciesViewState> viewState;

    @Inject
    public CryptoCurrenciesViewModel(CryptoCurrenciesApiRepository apiRepository) {
        this.apiRepository = apiRepository;
        this.viewState = new MediatorLiveData<>();
        this.viewState.setValue(new CryptoCurrenciesViewState());
    }

    public LiveData<CryptoCurrenciesViewState> getViewState() {
        return this.viewState;
    }

    public void loadCurrencies(int start, int limit) {
        currentViewState().setLoading(true);
        viewState.addSource(apiRepository.getAllCurrencies(start, limit), apiResponse -> {
            if (apiResponse != null) {
                if (apiResponse.getResponse() != null) {
                    viewState.setValue(
                            currentViewState().addCryptoCurrencies(apiResponse.getResponse())
                                    .setLoading(false));
                } else {
                    // TODO Error
                }
            } else {
                // TODO Error
            }
        });
    }

    private CryptoCurrenciesViewState currentViewState() {
        return this.viewState.getValue();
    }
}
