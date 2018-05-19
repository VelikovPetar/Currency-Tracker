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
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.github.clans.fab.FloatingActionButton;
import com.petarvelikov.currencytracker.R;
import com.petarvelikov.currencytracker.app.CurrencyTrackerApplication;
import com.petarvelikov.currencytracker.preferences.SharedPreferencesHelper;
import com.petarvelikov.currencytracker.viewmodel.TransactionsViewModel;
import com.petarvelikov.currencytracker.viewmodel.TransactionsViewState;

import javax.inject.Inject;

public class TransactionsFragment extends Fragment {

  @Inject
  SharedPreferencesHelper sharedPreferencesHelper;
  @Inject
  ViewModelProvider.Factory viewModelFactory;

  private TransactionsViewModel viewModel;
  private RecyclerView recyclerViewTransactions;
  private ProgressBar progressBarTransactions;
  private TextView textViewTransactionsMessage;
  private FloatingActionButton fabAddTransaction;

  @Override
  public void onAttach(Context context) {
    super.onAttach(context);
    CurrencyTrackerApplication app = (CurrencyTrackerApplication) getActivity().getApplication();
    app.getAppComponent().inject(this);
  }

  @Override
  public void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    viewModel = ViewModelProviders
        .of(this, viewModelFactory)
        .get(TransactionsViewModel.class);
  }

  @Nullable
  @Override
  public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
    View view = inflater.inflate(R.layout.fragment_transactions, container, false);
    bindUi(view);
    return view;
  }

  @Override
  public void onActivityCreated(@Nullable Bundle savedInstanceState) {
    super.onActivityCreated(savedInstanceState);
    if (viewModel.getViewState().hasObservers()) {
      viewModel.getViewState().removeObservers(this);
    }
    viewModel.getViewState().observe(this, this::updateUi);
    viewModel.loadTransactions();
  }

  private void bindUi(View view) {
    recyclerViewTransactions = view.findViewById(R.id.recyclerTransactions);
    recyclerViewTransactions.setLayoutManager(new LinearLayoutManager(getContext()));
    // TODO: Adapter
    progressBarTransactions = view.findViewById(R.id.progressTransactions);
    textViewTransactionsMessage = view.findViewById(R.id.txtTransactionsMessage);
    fabAddTransaction = view.findViewById(R.id.fabAddTransaction);
    fabAddTransaction.setOnClickListener(clickedView -> {
      // TODO: Launch screen for new transaction
      Toast.makeText(getContext(), "Fab click.", Toast.LENGTH_SHORT).show();
    });
  }

  private void updateUi(TransactionsViewState viewState) {
    if (viewState.isLoading()) {
      progressBarTransactions.setVisibility(View.VISIBLE);
    } else {
      progressBarTransactions.setVisibility(View.GONE);
    }
    if (viewState.hasError()) {
      textViewTransactionsMessage.setText(R.string.error_while_reading_transactions);
      textViewTransactionsMessage.setVisibility(View.VISIBLE);
    } else {
      textViewTransactionsMessage.setVisibility(View.GONE);
    }
    if (viewState.getTransactions() != null) {
      if (viewState.getTransactions().size() == 0) {
        textViewTransactionsMessage.setText(R.string.no_transactions);
        textViewTransactionsMessage.setVisibility(View.VISIBLE);
        recyclerViewTransactions.setVisibility(View.GONE);
      } else {
        textViewTransactionsMessage.setVisibility(View.GONE);
        recyclerViewTransactions.setVisibility(View.VISIBLE);
      }
    }
  }
}
