package com.petarvelikov.currencytracker.view.fragment;

import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.petarvelikov.currencytracker.R;
import com.petarvelikov.currencytracker.app.CurrencyTrackerApplication;
import com.petarvelikov.currencytracker.consts.Constants;
import com.petarvelikov.currencytracker.model.network.NetworkUtils;
import com.petarvelikov.currencytracker.view.activity.CurrencyDetailsActivity;
import com.petarvelikov.currencytracker.view.adapter.CryptoCurrenciesAdapter;
import com.petarvelikov.currencytracker.view.adapter.EndlessRecyclerViewScrollListener;
import com.petarvelikov.currencytracker.viewmodel.CryptoCurrenciesViewModel;
import com.petarvelikov.currencytracker.viewmodel.CryptoCurrenciesViewState;

import java.util.ArrayList;

import javax.inject.Inject;

public class CryptoCurrenciesFragment extends Fragment implements CryptoCurrenciesAdapter.OnClickListener {

    private static final String TAG = "CCFragment";

    @Inject
    ViewModelProvider.Factory viewModelFactory;
    @Inject
    NetworkUtils networkUtils;

    private RecyclerView recyclerView;
    private ProgressBar progressBar;
    private TextView txtError;
    private SwipeRefreshLayout swipeRefreshLayout;
    private EndlessRecyclerViewScrollListener scrollListener;
    private CryptoCurrenciesAdapter adapter;
    private CryptoCurrenciesViewModel ccViewModel;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        CurrencyTrackerApplication app = (CurrencyTrackerApplication) getActivity().getApplication();
        app.getAppComponent().inject(this);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ccViewModel = ViewModelProviders
                .of(this, viewModelFactory)
                .get(CryptoCurrenciesViewModel.class);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_crypto_currencies, container, false);
        bindUi(view);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if (ccViewModel.getViewState().hasObservers()) {
            ccViewModel.getViewState().removeObservers(this);
        }
        ccViewModel.getViewState().observe(this, this::updateUi);
        if (savedInstanceState == null) {
            // TODO Read the convert value from shared preferences
            ccViewModel.loadCurrencies(0, Constants.API_CONSTANTS.LIMIT, "EUR", false);
        }
    }

    @Override
    public void onClick(String id, String name, String symbol) {
        Intent intent = new Intent(getContext(), CurrencyDetailsActivity.class);
        intent.putExtra(CurrencyDetailsActivity.CURRENCY_ID, id);
        intent.putExtra(CurrencyDetailsActivity.CURRENCY_NAME, name);
        intent.putExtra(CurrencyDetailsActivity.CURRENCY_SYMBOL, symbol);
        startActivity(intent);
    }

    private void bindUi(View view) {
        recyclerView = view.findViewById(R.id.recyclerCryptoCurrencies);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);
        adapter = new CryptoCurrenciesAdapter(new ArrayList<>(), this);
        recyclerView.setAdapter(adapter);
        scrollListener = new EndlessRecyclerViewScrollListener(layoutManager) {
            @Override
            public void onLoadMore(int start, RecyclerView recyclerView) {
                // TODO Read the convert value from shared preferences
                // TODO Maybe check the network on model level
                if (networkUtils.isConnected()) {
                    ccViewModel.loadCurrencies(start, Constants.API_CONSTANTS.LIMIT, "EUR", false);
                }
            }
        };
        recyclerView.addOnScrollListener(scrollListener);
        progressBar = view.findViewById(R.id.progressCryptoCurrencies);
        txtError = view.findViewById(R.id.txtCurrenciesError);
        txtError.setOnClickListener(event -> {
            // TODO Read the convert value from shared preferences
            ccViewModel.loadCurrencies(0, Constants.API_CONSTANTS.LIMIT, "EUR", false);
        });
        swipeRefreshLayout = view.findViewById(R.id.swipeRefreshCurrencyList);
        swipeRefreshLayout.setColorSchemeColors(ContextCompat.getColor(getContext(), R.color.colorAccent));
        swipeRefreshLayout.setOnRefreshListener(() -> {
            // TODO Read the convert value from shared preferences
            ccViewModel.loadCurrencies(0, Constants.API_CONSTANTS.LIMIT, "EUR", true);
        });
    }

    private void updateUi(CryptoCurrenciesViewState viewState) {
        progressBar.setVisibility(viewState.isLoading() && viewState.getCurrenciesCount() == 0 ? View.VISIBLE : View.GONE);
        if (!viewState.isLoading()) {
            swipeRefreshLayout.setRefreshing(false);
        }
        if (viewState.getCurrenciesCount() == 0) {
            txtError.setVisibility(viewState.hasError() ? View.VISIBLE : View.GONE);
        }
        scrollListener.setLoading(viewState.isLoading());
        scrollListener.setEndReached(viewState.isEndReached());
        adapter.setCurrencies(viewState.getCurrencies());
    }
}
