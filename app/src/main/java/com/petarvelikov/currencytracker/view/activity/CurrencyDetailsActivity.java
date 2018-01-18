package com.petarvelikov.currencytracker.view.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import com.petarvelikov.currencytracker.R;
import com.petarvelikov.currencytracker.model.CryptoCurrency;

public class CurrencyDetailsActivity extends AppCompatActivity {

    private static final String TAG = "CurrencyDetails";

    public static final String CURRENCY = "com.petarvelikov.currencytracker.CURRENCY";
    private static final String DEFAULT_NAME = "Bitcoin";
    private static final String DEFAULT_SYMBOL = "BTC";
    private static final String TITLE_FORMAT = "%name (%symbol)";

    private CryptoCurrency cryptoCurrency;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_currency_details);
        bindUi();
    }

    private void bindUi() {
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            cryptoCurrency = extras.getParcelable(CURRENCY);
            if (cryptoCurrency != null) {
                setupToolbar();
            } else {
                showError();
                setupDefaultToolbar();
            }
        } else {
            showError();
            setupDefaultToolbar();
        }
    }

    private void setupToolbar() {
        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle(TITLE_FORMAT
                .replace("%name", cryptoCurrency.getName())
                .replace("%symbol", cryptoCurrency.getSymbol()));
        setSupportActionBar(toolbar);
        toolbar.setNavigationIcon(R.drawable.ic_action_back);
        toolbar.setNavigationOnClickListener(view -> CurrencyDetailsActivity.this.finish());
    }

    private void showError() {
        // TODO Show error
    }

    private void setupDefaultToolbar() {
        // TODO Show default toolbar
    }
}
