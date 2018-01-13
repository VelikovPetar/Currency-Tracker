package com.petarvelikov.currencytracker.view;

import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Toast;

import com.petarvelikov.currencytracker.R;
import com.petarvelikov.currencytracker.app.CurrencyTrackerApplication;
import com.petarvelikov.currencytracker.model.CryptoCurrency;
import com.petarvelikov.currencytracker.viewmodel.CryptoCurrenciesViewModel;
import com.petarvelikov.currencytracker.viewmodel.CryptoCurrenciesViewState;

import javax.inject.Inject;

public class MainActivity extends AppCompatActivity {

    // TODO Remove tests
    private static final String TAG = "MainActivity";

    @Inject
    ViewModelProvider.Factory viewModelFactory;
    private CryptoCurrenciesViewModel ccViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        CurrencyTrackerApplication app = (CurrencyTrackerApplication) getApplication();
        app.getAppComponent().inject(this);
        ccViewModel = ViewModelProviders
                .of(this, viewModelFactory)
                .get(CryptoCurrenciesViewModel.class);
        ccViewModel.getViewState().observe(this, this::updateUi);
        ccViewModel.loadCurrencies();
        bindUi();
    }

    private void bindUi() {
        BottomNavigationView navigationView = findViewById(R.id.bottomNavigationView);
        navigationView.setOnNavigationItemSelectedListener(item -> {
            switch (item.getItemId()) {
                case R.id.actionExchangeRates:
                    // TODO Display exchange rates fragment
                    Toast.makeText(MainActivity.this, R.string.exchange_rates, Toast.LENGTH_SHORT).show();
                    break;
                case R.id.actionCryptoCurrencies:
                    // TODO Display crypto currencies fragment
                    Toast.makeText(MainActivity.this, R.string.crypto_currencies, Toast.LENGTH_SHORT).show();
                    break;
            }
            return true;
        });
    }

    private void updateUi(CryptoCurrenciesViewState viewState) {
        Log.d(TAG, "Is loading: " + viewState.isLoading());
        for (CryptoCurrency cc : viewState.getCurrencies()) {
            Log.d(TAG, cc.getName());
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
    }
}
