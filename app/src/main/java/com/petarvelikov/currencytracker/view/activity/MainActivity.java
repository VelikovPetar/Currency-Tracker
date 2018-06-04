package com.petarvelikov.currencytracker.view.activity;

import android.os.Bundle;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.petarvelikov.currencytracker.R;
import com.petarvelikov.currencytracker.app.CurrencyTrackerApplication;
import com.petarvelikov.currencytracker.preferences.SharedPreferencesHelper;
import com.petarvelikov.currencytracker.resources.ExchangeRatesResourcesHelper;
import com.petarvelikov.currencytracker.view.fragment.CryptoCurrenciesFragment;
import com.petarvelikov.currencytracker.view.fragment.CurrencyTrackerBaseFragment;
import com.petarvelikov.currencytracker.view.fragment.ExchangeRatesFragment;
import com.petarvelikov.currencytracker.view.fragment.TransactionsFragment;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

public class MainActivity extends AppCompatActivity {

  private static final String TAG = "MainActivity";

  @Inject
  ExchangeRatesResourcesHelper exchangeRatesResourcesHelper;
  @Inject
  SharedPreferencesHelper sharedPreferencesHelper;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);
    CurrencyTrackerApplication app = (CurrencyTrackerApplication) getApplication();
    app.getAppComponent().inject(this);
    bindUi(savedInstanceState);
  }

  @Override
  public boolean onCreateOptionsMenu(Menu menu) {
    getMenuInflater().inflate(R.menu.menu_base_currency, menu);
    MenuItem item = menu.findItem(R.id.menu_item_base_currency);
    Spinner spinner = (Spinner) item.getActionView();
    List<CharSequence> menuItems = createMenuItems();
    ArrayAdapter<CharSequence> adapter = new BaseCurrencyMenuArrayAdapter(this,
        R.layout.list_item_base_currency_menu_item, menuItems);
    int selectedIndex = findCurrentBaseCurrencyIndexInMenu(menuItems);
    spinner.setAdapter(adapter);
    spinner.setSelection(selectedIndex);
    spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
      @Override
      public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        String baseCurrency = spinner.getSelectedItem().toString();
        updateBaseCurrency(baseCurrency);
      }

      @Override
      public void onNothingSelected(AdapterView<?> parent) {

      }
    });
    return true;
  }

  private List<CharSequence> createMenuItems() {
    Map<String, String> currencySymbols = exchangeRatesResourcesHelper.getCurrencySymbols();
    List<CharSequence> menuItems = new ArrayList<>();
    menuItems.addAll(currencySymbols.keySet());
    return menuItems;
  }

  private void bindUi(Bundle savedInstanceState) {
    setupToolbar();
    BottomNavigationView navigationView = findViewById(R.id.bottomNavigationView);
    navigationView.setOnNavigationItemSelectedListener(item -> {
      switch (item.getItemId()) {
        case R.id.actionCryptoCurrencies:
          replaceFragment(new CryptoCurrenciesFragment());
          setToolbarTitle(R.string.crypto_currencies);
          break;
        case R.id.actionExchangeRates:
          replaceFragment(new ExchangeRatesFragment());
          setToolbarTitle(R.string.exchange_rates);
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

  private void updateBaseCurrency(String newBaseCurrency) {
    String oldBaseCurrency = sharedPreferencesHelper.getBaseCurrency();
    if (!oldBaseCurrency.equals(newBaseCurrency)) {
      sharedPreferencesHelper.setBaseCurrency(newBaseCurrency);
      CurrencyTrackerBaseFragment activeFragment =
          (CurrencyTrackerBaseFragment) getSupportFragmentManager().findFragmentById(R.id.frameContainer);
      if (activeFragment != null) {
        activeFragment.onBaseCurrencyChanged();
      }
    }
  }

  private int findCurrentBaseCurrencyIndexInMenu(List<CharSequence> menuItems) {
    CharSequence currentBaseCurrency = sharedPreferencesHelper.getBaseCurrency();
    return menuItems.indexOf(currentBaseCurrency);
  }

}
