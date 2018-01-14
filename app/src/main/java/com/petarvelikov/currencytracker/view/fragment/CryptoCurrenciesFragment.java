package com.petarvelikov.currencytracker.view.fragment;

import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.petarvelikov.currencytracker.R;
import com.petarvelikov.currencytracker.app.CurrencyTrackerApplication;
import com.petarvelikov.currencytracker.view.adapter.CryptoCurrenciesAdapter;
import com.petarvelikov.currencytracker.view.adapter.EndlessRecyclerViewScrollListener;
import com.petarvelikov.currencytracker.viewmodel.CryptoCurrenciesViewModel;
import com.petarvelikov.currencytracker.viewmodel.CryptoCurrenciesViewState;

import java.util.ArrayList;

import javax.inject.Inject;

public class CryptoCurrenciesFragment extends Fragment {

    private static final String TAG = "CCFragment";
    private static final int LIMIT = 50;

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
        ccViewModel = ViewModelProviders
                .of(this, viewModelFactory)
                .get(CryptoCurrenciesViewModel.class);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_crypto_currencies, container, false);
        recyclerView = view.findViewById(R.id.recyclerCryptoCurrencies);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);
        adapter = new CryptoCurrenciesAdapter(new ArrayList<>());
        recyclerView.setAdapter(adapter);
        EndlessRecyclerViewScrollListener scrollListener = new EndlessRecyclerViewScrollListener(layoutManager) {
            @Override
            public void onLoadMore(int start, RecyclerView recyclerView) {
                ccViewModel.loadCurrencies(start, LIMIT);
            }
        };
        recyclerView.addOnScrollListener(scrollListener);
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
            ccViewModel.loadCurrencies(0, LIMIT);
        }
    }

    private void updateUi(CryptoCurrenciesViewState viewState) {
        adapter.setCurrencies(viewState.getCurrencies());
    }
}
