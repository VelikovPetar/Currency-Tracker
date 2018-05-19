package com.petarvelikov.currencytracker.view.fragment;

import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
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
import com.petarvelikov.currencytracker.preferences.SharedPreferencesHelper;
import com.petarvelikov.currencytracker.resources.ExchangeRatesResourcesHelper;
import com.petarvelikov.currencytracker.view.adapter.ExchangeRatesAdapter;
import com.petarvelikov.currencytracker.viewmodel.ExchangeRatesViewModel;
import com.petarvelikov.currencytracker.viewmodel.ExchangeRatesViewState;

import java.util.ArrayList;

import javax.inject.Inject;

public class ExchangeRatesFragment extends Fragment {

  private static final String TAG = "ExchangeRatesFragment";
  @Inject
  ViewModelProvider.Factory viewModelFactory;
  @Inject
  ExchangeRatesResourcesHelper resourcesHelper;
  @Inject
  SharedPreferencesHelper sharedPreferencesHelper;

  private RecyclerView recyclerView;
  private ProgressBar progressBar;
  private TextView txtError;
  private SwipeRefreshLayout swipeRefreshLayout;
  private ExchangeRatesAdapter adapter;
  private ExchangeRatesViewModel erViewModel;

  @Override
  public void onAttach(Context context) {
    super.onAttach(context);
    CurrencyTrackerApplication app = (CurrencyTrackerApplication) getActivity().getApplication();
    app.getAppComponent().inject(this);
  }

  @Override
  public void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    erViewModel = ViewModelProviders
        .of(this, viewModelFactory)
        .get(ExchangeRatesViewModel.class);
  }

  @Nullable
  @Override
  public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
    View view = inflater.inflate(R.layout.fragment_exchange_rates, container, false);
    bindUi(view);
    return view;
  }

  @Override
  public void onActivityCreated(@Nullable Bundle savedInstanceState) {
    super.onActivityCreated(savedInstanceState);
    if (erViewModel.getViewState().hasObservers()) {
      erViewModel.getViewState().removeObservers(this);
    }
    erViewModel.getViewState().observe(this, this::updateUi);
    erViewModel.load(sharedPreferencesHelper.getBaseCurrency(), false);
  }

  private void bindUi(View view) {
    recyclerView = view.findViewById(R.id.recyclerExchangeRates);
    recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
    adapter = new ExchangeRatesAdapter(new ArrayList<>(), resourcesHelper);
    recyclerView.setAdapter(adapter);
    progressBar = view.findViewById(R.id.progressExchangeRates);
    txtError = view.findViewById(R.id.txtExchangeRatesError);
    txtError.setOnClickListener(event -> erViewModel.load(sharedPreferencesHelper.getBaseCurrency(), false));
    swipeRefreshLayout = view.findViewById(R.id.swipeRefreshExchangeRates);
    swipeRefreshLayout.setColorSchemeColors(ContextCompat.getColor(getContext(), R.color.colorAccent));
    swipeRefreshLayout.setOnRefreshListener(() -> erViewModel.load(sharedPreferencesHelper.getBaseCurrency(), true));
  }

  private void updateUi(ExchangeRatesViewState viewState) {
    progressBar.setVisibility(viewState.isLoading() ? View.VISIBLE : View.GONE);
    if (!viewState.isLoading()) {
      swipeRefreshLayout.setRefreshing(false);
    }
    txtError.setVisibility(viewState.hasError() ? View.VISIBLE : View.GONE);
    if (viewState.getExchangeRates() != null) {
      adapter.setExchangeRates(viewState.getExchangeRates());
    }
  }
}
