package com.petarvelikov.currencytracker.view.fragment;

import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.petarvelikov.currencytracker.R;
import com.petarvelikov.currencytracker.app.CurrencyTrackerApplication;
import com.petarvelikov.currencytracker.view.adapter.CryptoCurrenciesAdapter;
import com.petarvelikov.currencytracker.viewmodel.CryptoCurrenciesViewModel;
import com.petarvelikov.currencytracker.viewmodel.CryptoCurrenciesViewState;

import java.util.ArrayList;

import javax.inject.Inject;

public class CryptoCurrenciesFragment extends Fragment {

    private static final String TAG = "CCFragment";

    @Inject
    ViewModelProvider.Factory viewModelFactory;

    private RecyclerView recyclerView;
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
        ccViewModel = ViewModelProviders.of(this, viewModelFactory).get(CryptoCurrenciesViewModel.class);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_crypto_currencies, container, false);
        recyclerView = view.findViewById(R.id.recyclerCryptoCurrencies);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        adapter = new CryptoCurrenciesAdapter(new ArrayList<>());
        recyclerView.setAdapter(adapter);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if (ccViewModel.getViewState().hasObservers()) {
            ccViewModel.getViewState().removeObservers(this);
        }
        ccViewModel.getViewState().observe(this, this::updateUi);
        ccViewModel.loadCurrencies();
    }

    private void updateUi(CryptoCurrenciesViewState viewState) {
        Log.d(TAG, "Is loading: " + viewState.isLoading());
        adapter.setCurrencies(viewState.getCurrencies());
    }
}