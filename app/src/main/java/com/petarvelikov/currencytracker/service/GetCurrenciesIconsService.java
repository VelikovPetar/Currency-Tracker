package com.petarvelikov.currencytracker.service;

import android.app.IntentService;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.util.Log;

import com.petarvelikov.currencytracker.app.CurrencyTrackerApplication;
import com.petarvelikov.currencytracker.model.CurrencyIcon;
import com.petarvelikov.currencytracker.model.CurrencyIconsResponse;
import com.petarvelikov.currencytracker.model.database.CurrencyDatabase;
import com.petarvelikov.currencytracker.model.rest.CryptoCurrenciesApiRepository;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import retrofit2.Response;

public class GetCurrenciesIconsService extends IntentService {

    private static final String TAG = "IconsService";

    @Inject
    CryptoCurrenciesApiRepository apiRepository;
    @Inject
    CurrencyDatabase currencyDatabase;

    public GetCurrenciesIconsService() {
        super(GetCurrenciesIconsService.class.getName());
    }

    public GetCurrenciesIconsService(String name) {
        super(name);
    }

    @Override
    public void onCreate() {
        super.onCreate();
        CurrencyTrackerApplication app = (CurrencyTrackerApplication) getApplication();
        app.getAppComponent().inject(this);
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        if (currencyDatabase.currencyDao().getNumberOfIcons() > 0) {
            Log.d(TAG, "Urls loaded!");
            return;
        }
        Log.d(TAG, "Loading urls...");
        try {
            Response<CurrencyIconsResponse> response = apiRepository.getCurrenciesIcons().execute();
            CurrencyIconsResponse iconsResponse = response.body();
            if (iconsResponse != null) {
                List<CurrencyIcon> icons = new ArrayList<>();
                icons.addAll(iconsResponse.getData().values());
                currencyDatabase
                        .currencyDao()
                        .insertMultiple(icons.toArray(new CurrencyIcon[0]));
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
}
