package com.petarvelikov.currencytracker.view.activity;

import android.os.Bundle;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.Toast;

import com.petarvelikov.currencytracker.R;
import com.petarvelikov.currencytracker.view.fragment.CryptoCurrenciesFragment;
import com.petarvelikov.currencytracker.view.fragment.ExchangeRatesFragment;
import com.petarvelikov.currencytracker.view.fragment.TransactionsFragment;

public class MainActivity extends AppCompatActivity {

  private static final String TAG = "MainActivity";

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);
    bindUi(savedInstanceState);
  }

  private void bindUi(Bundle savedInstanceState) {
    setupToolbar();
    BottomNavigationView navigationView = findViewById(R.id.bottomNavigationView);
    navigationView.setOnNavigationItemSelectedListener(item -> {
      switch (item.getItemId()) {
        case R.id.actionCryptoCurrencies:
          replaceFragment(new CryptoCurrenciesFragment());
          setToolbarTitle(R.string.crypto_currencies);
          Toast.makeText(MainActivity.this, R.string.crypto_currencies, Toast.LENGTH_SHORT).show();
          break;
        case R.id.actionExchangeRates:
          replaceFragment(new ExchangeRatesFragment());
          setToolbarTitle(R.string.exchange_rates);
          Toast.makeText(MainActivity.this, R.string.exchange_rates, Toast.LENGTH_SHORT).show();
          break;
        case R.id.actionTransactions:
          setToolbarTitle(R.string.transactions);
          replaceFragment(new TransactionsFragment());
          break;
      }
      return true;
    });
    navigationView.setOnNavigationItemReselectedListener(item -> {
    });
    if (savedInstanceState == null) {
      navigationView.setSelectedItemId(R.id.actionCryptoCurrencies);
      replaceFragment(new CryptoCurrenciesFragment());
    }
  }

  private void setupToolbar() {
    Toolbar toolbar = findViewById(R.id.toolbar);
    setSupportActionBar(toolbar);
    setToolbarTitle(R.string.crypto_currencies);
  }

  private void setToolbarTitle(int stringId) {
    if (getSupportActionBar() != null) {
      getSupportActionBar().setTitle(stringId);
    }
  }

  private void replaceFragment(Fragment fragment) {
    getSupportFragmentManager()
        .beginTransaction()
        .replace(R.id.frameContainer, fragment)
        .commit();
  }

}
