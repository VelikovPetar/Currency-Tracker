package com.petarvelikov.currencytracker.view.activity;

import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.petarvelikov.currencytracker.R;
import com.petarvelikov.currencytracker.app.CurrencyTrackerApplication;
import com.petarvelikov.currencytracker.viewmodel.BootstrapViewModel;
import com.petarvelikov.currencytracker.viewmodel.BootstrapViewState;

import javax.inject.Inject;

public class BootstrapActivity extends AppCompatActivity {

    @Inject
    ViewModelProvider.Factory viewModelFactory;

    private ProgressBar progressBar;
    private TextView txtMessage;
    private Button btnRetry;
    private BootstrapViewModel bootstrapViewModel;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bootstrap);
        CurrencyTrackerApplication app = (CurrencyTrackerApplication) getApplication();
        app.getAppComponent().inject(this);
        bindUi();
        bootstrapViewModel = ViewModelProviders.of(this, viewModelFactory).get(BootstrapViewModel.class);
        bootstrapViewModel.getViewState().observe(this, this::updateUi);
        bootstrapViewModel.load();
    }

    private void bindUi() {
        this.progressBar = findViewById(R.id.progressBootstrap);
        this.txtMessage = findViewById(R.id.txtBootstrapMessage);
        this.btnRetry = findViewById(R.id.btnRetry);
        this.btnRetry.setOnClickListener(event -> {
            bootstrapViewModel.load();
        });
    }

    private void updateUi(BootstrapViewState viewState) {
        // TODO Test all interactions and maybe refactor
        if (viewState.isLoading()) {
            progressBar.setVisibility(View.VISIBLE);
            if (!viewState.hasError()) {
                txtMessage.setText("");
            }
        } else {
            progressBar.setVisibility(View.INVISIBLE);
            if (!viewState.hasError()) {
                btnRetry.setVisibility(View.GONE);
                launchMainActivity();
                return;
            }
        }
        if (viewState.hasError() && viewState.getErrorMessage() != null) {
            txtMessage.setText(viewState.getErrorMessage());
            btnRetry.setVisibility(View.VISIBLE);
        }
    }

    private void launchMainActivity() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();
    }
}
