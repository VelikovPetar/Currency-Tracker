package com.petarvelikov.currencytracker.view.fragment;

import android.content.Context;
import android.support.v4.app.Fragment;

import com.petarvelikov.currencytracker.app.CurrencyTrackerApplication;
import com.petarvelikov.currencytracker.preferences.SharedPreferencesHelper;

import javax.inject.Inject;

public class TransactionsFragment extends Fragment {

  @Inject
  SharedPreferencesHelper sharedPreferencesHelper;

  @Override
  public void onAttach(Context context) {
    super.onAttach(context);
    CurrencyTrackerApplication app = (CurrencyTrackerApplication) getActivity().getApplication();
    app.getAppComponent().inject(this);
  }
}
