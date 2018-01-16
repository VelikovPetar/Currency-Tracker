package com.petarvelikov.currencytracker.viewmodel;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MediatorLiveData;
import android.arch.lifecycle.ViewModel;

import com.petarvelikov.currencytracker.model.CurrencyIconsResponse;
import com.petarvelikov.currencytracker.model.rest.CryptoCurrenciesApiRepository;

import javax.inject.Inject;

public class BootstrapViewModel extends ViewModel {

    private CryptoCurrenciesApiRepository apiRepository;
    private MediatorLiveData<BootstrapViewState> viewState;

    @Inject
    public BootstrapViewModel(CryptoCurrenciesApiRepository apiRepository) {
        this.apiRepository = apiRepository;
        this.viewState = new MediatorLiveData<>();
        this.viewState.setValue(new BootstrapViewState());
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        apiRepository.cancelCalls();
    }

    public LiveData<BootstrapViewState> getViewState() {
        return this.viewState;
    }

    public void load() {
        viewState.setValue(currentViewState()
                .setLoading(true)
                .setError(false)
                .setErrorMessage(null));
        viewState.addSource(apiRepository.getCurrenciesIcons(), apiResponse -> {
            if (apiResponse != null) {
                CurrencyIconsResponse response = apiResponse.getResponse();
                if (response != null) {
                    viewState.setValue(currentViewState()
                            .setLoading(false)
                            .setError(false)
                            .setErrorMessage(null));
                } else if (apiResponse.getError() != null) {
                    Throwable throwable = apiResponse.getError();
                    viewState.setValue(currentViewState()
                            .setLoading(false)
                            .setError(true)
                            .setErrorMessage(throwable.getMessage()));
                }
            }
        });
    }

    private BootstrapViewState currentViewState() {
        return this.viewState.getValue();
    }
}
