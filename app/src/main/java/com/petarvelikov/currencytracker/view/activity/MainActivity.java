package com.petarvelikov.currencytracker.view.activity;

import android.os.Bundle;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.petarvelikov.currencytracker.R;
import com.petarvelikov.currencytracker.view.fragment.CryptoCurrenciesFragment;
import com.petarvelikov.currencytracker.view.fragment.ExchangeRatesFragment;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        bindUi();
    }

    private void bindUi() {
        BottomNavigationView navigationView = findViewById(R.id.bottomNavigationView);
        navigationView.setOnNavigationItemSelectedListener(item -> {
            switch (item.getItemId()) {
                case R.id.actionCryptoCurrencies:
                    replaceFragment(new CryptoCurrenciesFragment());
                    Toast.makeText(MainActivity.this, R.string.crypto_currencies, Toast.LENGTH_SHORT).show();
                    break;
                case R.id.actionExchangeRates:
                    replaceFragment(new ExchangeRatesFragment());
                    Toast.makeText(MainActivity.this, R.string.exchange_rates, Toast.LENGTH_SHORT).show();
                    break;
            }
            return true;
        });
        navigationView.setOnNavigationItemReselectedListener(item -> {
        });
        navigationView.setSelectedItemId(R.id.actionCryptoCurrencies);
        replaceFragment(new CryptoCurrenciesFragment());
    }

    private void replaceFragment(Fragment fragment) {
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.frameContainer, fragment)
                .commit();
    }

}
