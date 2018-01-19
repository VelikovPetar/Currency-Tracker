package com.petarvelikov.currencytracker.viewmodel;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MediatorLiveData;
import android.arch.lifecycle.ViewModel;
import android.support.annotation.NonNull;

import com.petarvelikov.currencytracker.model.CurrencyIconsResponse;
import com.petarvelikov.currencytracker.model.network.CryptoCurrenciesApiRepository;

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

    @NonNull
    public LiveData<BootstrapViewState> getViewState() {
        return this.viewState;
    }

    public void load() {
        viewState.setValue(currentViewState()
                .setLoading(true)
                .setHasError(false)
                .setErrorMessage(null));
        viewState.addSource(apiRepository.getCurrenciesIcons(), apiResponse -> {
            if (apiResponse != null) {
                CurrencyIconsResponse response = apiResponse.getResponse();
                if (response != null) {
                    viewState.setValue(currentViewState()
                            .setLoading(false)
                            .setHasError(false)
                            .setErrorMessage(null));
                } else if (apiResponse.getError() != null) {
                    Throwable throwable = apiResponse.getError();
                    viewState.setValue(currentViewState()
                            .setLoading(false)
                            .setHasError(true)
                            .setErrorMessage(throwable.getMessage()));
                }
            }
        });
    }

    private BootstrapViewState currentViewState() {
        return this.viewState.getValue();
    }
}
