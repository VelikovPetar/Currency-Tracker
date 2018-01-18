package com.petarvelikov.currencytracker.viewmodel;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MediatorLiveData;
import android.arch.lifecycle.ViewModel;

import com.petarvelikov.currencytracker.consts.Constants;
import com.petarvelikov.currencytracker.model.network.CryptoCurrenciesApiRepository;

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

    public void loadCurrencies(int start, int limit, String convert) {
        viewState.setValue(currentViewState()
                .setLoading(true)
                .setHasError(false));
        viewState.addSource(apiRepository.getAllCurrencies(start, limit, convert), apiResponse -> {
            if (apiResponse != null) {
                if (apiResponse.getResponse() != null) {
                    viewState.setValue(currentViewState()
                            .addCryptoCurrencies(apiResponse.getResponse())
                            .setLoading(false)
                            .setHasError(false));
                } else if (apiResponse.getError() != null) {
                    String message = apiResponse.getError().getMessage();
                    // TODO Test if this works properly
                    if (Constants.ERROR.HTTP_404_NOT_FOUND.equals(message)) {
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
                }
            } else {
                viewState.setValue(currentViewState()
                        .setLoading(false)
                        .setEndReached(false)
                        .setHasError(true));
            }
        });
    }

    private CryptoCurrenciesViewState currentViewState() {
        return this.viewState.getValue();
    }
}
