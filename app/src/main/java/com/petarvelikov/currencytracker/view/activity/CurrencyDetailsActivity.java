package com.petarvelikov.currencytracker.view.activity;

import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TableLayout;
import android.widget.TextView;

import com.petarvelikov.currencytracker.R;
import com.petarvelikov.currencytracker.app.CurrencyTrackerApplication;
import com.petarvelikov.currencytracker.view.chart.CurrencyHistoryLineChart;
import com.petarvelikov.currencytracker.viewmodel.CurrencyDetailsViewModel;
import com.petarvelikov.currencytracker.viewmodel.CurrencyDetailsViewState;

import java.util.List;
import java.util.Map;

import javax.inject.Inject;

public class CurrencyDetailsActivity extends AppCompatActivity {

    private static final String TAG = "CurrencyDetails";

    public static final String CURRENCY_ID = "com.petarvelikov.currencytracker.CURRENCY_ID";
    public static final String CURRENCY_NAME = "com.petarvelikov.currencytracker.CURRENCY_NAME";
    public static final String CURRENCY_SYMBOL = "com.petarvelikov.currencytracker.CURRENCY_SYMBOL";
    private static final String DEFAULT_ID = "bitcoin";
    private static final String DEFAULT_NAME = "Bitcoin";
    private static final String DEFAULT_SYMBOL = "BTC";
    private static final String TITLE_FORMAT = "%name (%symbol)";

    @Inject
    ViewModelProvider.Factory viewModelFactory;

    private String currencyId, currencyName, currencySymbol;
    private CurrencyDetailsViewModel currencyDetailsViewModel;
    private TextView txtName, txtSymbol, txtPrice, txtMarketCap, txtPercentHourly, txtPercentDaily,
            txtPercentWeekly, txtDataError, txtChartError;
    private ProgressBar progressBarData, progressBarChart;
    private TableLayout layoutDetails;
    private SwipeRefreshLayout swipeRefreshLayout;
    private CurrencyHistoryLineChart lineChart;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_currency_details);
        CurrencyTrackerApplication app = (CurrencyTrackerApplication) getApplication();
        bindUi();
        app.getAppComponent().inject(this);
        currencyDetailsViewModel = ViewModelProviders.of(this, viewModelFactory).get(CurrencyDetailsViewModel.class);
        currencyDetailsViewModel.getViewState().observe(this, this::updateUi);
        // TODO Read convert value from preferences
        currencyDetailsViewModel.load(currencyId, "EUR", false);
        // TODO Check where to call
        currencyDetailsViewModel.onTimeRangeChanged(CurrencyDetailsViewModel.TIME_RANGE_DAY, currencySymbol, "USD");
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
        txtName = findViewById(R.id.txtCurrencyDetailsName);
        txtSymbol = findViewById(R.id.txtCurrencyDetailsSymbol);
        txtPrice = findViewById(R.id.txtCurrencyDetailsPrice);
        txtMarketCap = findViewById(R.id.txtCurrencyDetailsMarketCap);
        txtPercentHourly = findViewById(R.id.txtCurrencyDetailsPercentHourly);
        txtPercentDaily = findViewById(R.id.txtCurrencyDetailsPercentDaily);
        txtPercentWeekly = findViewById(R.id.txtCurrencyDetailsPercentWeekly);
        txtDataError = findViewById(R.id.txtDetailsError);
        txtDataError.setOnClickListener(view -> {
            // TODO Read convert value from preferences
            currencyDetailsViewModel.load(currencyId, "EUR", false);
        });
        progressBarData = findViewById(R.id.progressCurrencyDetails);
        layoutDetails = findViewById(R.id.layoutCurrencyDetails);
        swipeRefreshLayout = findViewById(R.id.swipeRefreshCurrencyDetails);
        swipeRefreshLayout.setColorSchemeColors(ContextCompat.getColor(this, R.color.colorAccent));
        swipeRefreshLayout.setOnRefreshListener(() -> {
            // TODO Read convert value from preferences
            currencyDetailsViewModel.load(currencyId, "EUR", true);
        });
        lineChart = findViewById(R.id.lineChart);
        progressBarChart = findViewById(R.id.progressChart);
        txtChartError = findViewById(R.id.txtChartError);
        txtChartError.setOnClickListener(view -> {
            currencyDetailsViewModel.onTimeRangeChanged(CurrencyDetailsViewModel.TIME_RANGE_DAY, currencySymbol, "USD");
        });
    }

    private void setupChart(Map<Float, Double> chartData, List<String> chartLabels) {
        lineChart.setHistoricalData(chartData, chartLabels, currencyName);
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

    private void updateUi(CurrencyDetailsViewState viewState) {
        if (viewState.isLoadingChart()) {
            progressBarChart.setVisibility(View.VISIBLE);
            txtChartError.setVisibility(View.GONE);
            lineChart.setVisibility(View.GONE);
        } else {
            progressBarChart.setVisibility(View.GONE);
            if (viewState.hasChartError()) {
                txtChartError.setVisibility(View.VISIBLE);
                lineChart.setVisibility(View.GONE);
            } else {
                txtChartError.setVisibility(View.GONE);
                lineChart.setVisibility(View.VISIBLE);
                if (viewState.getChartData() != null && viewState.getChartLabels() != null) {
                    setupChart(viewState.getChartData(), viewState.getChartLabels());
                }
            }
        }
        if (viewState.isLoading()) {
            progressBarData.setVisibility(View.VISIBLE);
            txtDataError.setVisibility(View.GONE);
            layoutDetails.setVisibility(View.GONE);
        } else {
            progressBarData.setVisibility(View.GONE);
            swipeRefreshLayout.setRefreshing(false);
            if (viewState.getCryptoCurrency() != null) {
                txtDataError.setVisibility(View.GONE);
                layoutDetails.setVisibility(View.VISIBLE);
                txtName.setText(viewState.getCryptoCurrency().getName());
                txtSymbol.setText(viewState.getCryptoCurrency().getSymbol());
                txtPrice.setText(viewState.getCryptoCurrency().getPriceUsd());
                txtMarketCap.setText(viewState.getCryptoCurrency().getMarketCapUsd());
                txtPercentHourly.setText(viewState.getCryptoCurrency().getPercentChange1h());
                txtPercentDaily.setText(viewState.getCryptoCurrency().getPercentChange24h());
                txtPercentWeekly.setText(viewState.getCryptoCurrency().getPercentChange7d());
            } else if (viewState.hasError()) {
                txtDataError.setVisibility(View.VISIBLE);
                layoutDetails.setVisibility(View.GONE);
            }
        }
    }
}
