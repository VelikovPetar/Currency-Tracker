package com.petarvelikov.currencytracker.viewmodel;

public class BootstrapViewState {

    private boolean isLoading;
    private boolean hasError;
    private String errorMessage;

    public boolean isLoading() {
        return isLoading;
    }

    public boolean hasError() {
        return hasError;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public BootstrapViewState setLoading(boolean isLoading) {
        this.isLoading = isLoading;
        return this;
    }

    public BootstrapViewState setHasError(boolean hasError) {
        this.hasError = hasError;
        return this;
    }

    public BootstrapViewState setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
        return this;
    }
}
