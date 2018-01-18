package com.petarvelikov.currencytracker.view.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import com.petarvelikov.currencytracker.R;

public class CurrencyDetailsActivity extends AppCompatActivity {

    private static final String TAG = "CurrencyDetails";

    public static final String CURRENCY_ID = "com.petarvelikov.currencytracker.CURRENCY_ID";
    public static final String CURRENCY_NAME = "com.petarvelikov.currencytracker.CURRENCY_NAME";
    public static final String CURRENCY_SYMBOL = "com.petarvelikov.currencytracker.CURRENCY_SYMBOL";
    private static final String DEFAULT_ID = "bitcoin";
    private static final String DEFAULT_NAME = "Bitcoin";
    private static final String DEFAULT_SYMBOL = "BTC";
    private static final String TITLE_FORMAT = "%name (%symbol)";

    private String currencyId, currencyName, currencySymbol;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_currency_details);
        bindUi();
    }

    private void bindUi() {
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            currencyId = extras.getString(CURRENCY_ID, DEFAULT_ID);
            currencyName = extras.getString(CURRENCY_NAME, DEFAULT_NAME);
            currencySymbol = extras.getString(CURRENCY_SYMBOL, DEFAULT_SYMBOL);
        } else {
            currencyName = DEFAULT_NAME;
            currencySymbol = DEFAULT_SYMBOL;
        }
        setupToolbar();
    }

    private void setupToolbar() {
        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle(TITLE_FORMAT
                .replace("%name", currencyName)
                .replace("%symbol", currencySymbol));
        setSupportActionBar(toolbar);
        toolbar.setNavigationIcon(R.drawable.ic_action_back);
        toolbar.setNavigationOnClickListener(view -> CurrencyDetailsActivity.this.finish());
    }
}
