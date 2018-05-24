package com.petarvelikov.currencytracker.view.activity;

import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import com.petarvelikov.currencytracker.R;
import com.petarvelikov.currencytracker.app.CurrencyTrackerApplication;
import com.petarvelikov.currencytracker.model.CryptoCurrency;
import com.petarvelikov.currencytracker.view.autocomplete.AutoCompleteCoinAdapter;
import com.petarvelikov.currencytracker.view.autocomplete.AutoCompleteCoinTextView;
import com.petarvelikov.currencytracker.viewmodel.AddTransactionViewModel;

import java.util.List;

import javax.inject.Inject;

public class AddTransactionActivity extends AppCompatActivity {

  private static final String TAG = AddTransactionActivity.class.getSimpleName();

  @Inject
  ViewModelProvider.Factory viewModelFactory;

  private AutoCompleteCoinTextView autoCompleteCoin;

  private AddTransactionViewModel viewModel;

  @Override
  protected void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_add_transaction);
    CurrencyTrackerApplication app = (CurrencyTrackerApplication) getApplication();
    app.getAppComponent().inject(this);
    viewModel = ViewModelProviders
        .of(this, viewModelFactory)
        .get(AddTransactionViewModel.class);
    viewModel.getCurrencies().observe(this, this::updateAutoCompleteCoinAdapter);
    bindUi();
  }

  private void bindUi() {
    setupToolbar();
    setupAutoCompleteCoin();
  }

  private void setupToolbar() {
    Toolbar toolbar = findViewById(R.id.toolbar);
    toolbar.setTitle(R.string.add_transaction);
    setSupportActionBar(toolbar);
    toolbar.setNavigationIcon(R.drawable.ic_action_back);
    toolbar.setNavigationOnClickListener(view -> AddTransactionActivity.this.finish());
  }

  private void setupAutoCompleteCoin() {
    autoCompleteCoin = findViewById(R.id.autocompleteCoin);
    viewModel.loadAvailableCoins();
  }

  private void updateAutoCompleteCoinAdapter(List<CryptoCurrency> cryptoCurrencies) {
    AutoCompleteCoinAdapter adapter =
        new AutoCompleteCoinAdapter(this, R.layout.item_autocomplete_coin, cryptoCurrencies);
    autoCompleteCoin.setAdapter(adapter);
  }
}
