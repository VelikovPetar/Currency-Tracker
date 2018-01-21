package com.petarvelikov.currencytracker.viewmodel;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import android.support.annotation.NonNull;

import com.petarvelikov.currencytracker.model.HistoricalDataRecord;
import com.petarvelikov.currencytracker.model.HistoricalDataResponse;
import com.petarvelikov.currencytracker.model.network.CurrenciesDataRepository;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.inject.Inject;

import io.reactivex.Single;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class CurrencyDetailsViewModel extends ViewModel {

    private CurrenciesDataRepository dataRepository;
    private MutableLiveData<CurrencyDetailsViewState> viewState;
    private CompositeDisposable compositeDisposable;

    public static final String TIME_RANGE_DAY = "com.petarvelikov.currencytracker.TIME_TANGE_DAY";
    public static final String TIME_RANGE_WEEK = "com.petarvelikov.currencytracker.TIME_TANGE_WEEK";
    public static final String TIME_RANGE_MONTH = "com.petarvelikov.currencytracker.TIME_TANGE_MONTH";
    private static final String TIME_FORMAT = "HH:mm";
    private static final String DATE_FORMAT = "dd/MM";

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

    public void onTimeRangeChanged(String timeRange, String fromSymbol, String toSymbol) {
        switch (timeRange) {
            case TIME_RANGE_DAY:
                getHistoricalData(dataRepository.getHistoricalDataDaily(fromSymbol, toSymbol), timeRange);
                break;
            case TIME_RANGE_WEEK:
                getHistoricalData(dataRepository.getHistoricalDataWeekly(fromSymbol, toSymbol), timeRange);
                break;
            case TIME_RANGE_MONTH:
                getHistoricalData(dataRepository.getHistoricalDataMonthly(fromSymbol, toSymbol), timeRange);
                break;
        }
    }

    private void getHistoricalData(Single<HistoricalDataResponse> single, String timeRange) {
        Disposable d = single
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(response -> {
                    if (response != null && response.getData() != null) {
                        Map<Float, Double> chartData = convertHistoricalDataToChartData(response.getData());
                        List<String> chartLabels = generateLabels(response.getData(), timeRange);
                        viewState.setValue(currentViewState()
                                .setIsLoading(false)
                                .setHasError(false)
                                .setChartData(chartData)
                                .setChartLabels(chartLabels));
                    } else {
                        viewState.setValue(currentViewState()
                                .setIsLoading(false)
                                .setHasError(true)
                                .setChartData(null)
                                .setChartLabels(null));
                    }
                }, throwable -> {
                    viewState.setValue(currentViewState()
                            .setIsLoading(false)
                            .setHasError(true)
                            .setChartData(null)
                            .setChartLabels(null));
                });
        compositeDisposable.add(d);
    }

    private Map<Float, Double> convertHistoricalDataToChartData(List<HistoricalDataRecord> data) {
        Map<Float, Double> chartData = new HashMap<>();
        float i = 0;
        for (HistoricalDataRecord dataRecord : data) {
            chartData.put(i++, dataRecord.getOpen());
        }
        return chartData;
    }

    private List<String> generateLabels(List<HistoricalDataRecord> data, String timeRange) {
        int step = data.size() / 7;
        List<String> labels = new ArrayList<>();
        DateFormat format;
        if (timeRange.equals(TIME_RANGE_DAY)) {
            format = new SimpleDateFormat(TIME_FORMAT, Locale.getDefault());
        } else {
            format = new SimpleDateFormat(DATE_FORMAT, Locale.getDefault());
        }
        for (int i = 0; i < data.size(); ++i) {
            if (i % step == 0) {
                Date date = new Date(data.get(i).getTime() * 1000L);
                labels.add(format.format(date));
            }
        }
        return labels;
    }

    private CurrencyDetailsViewState currentViewState() {
        return this.viewState.getValue();
    }
}
