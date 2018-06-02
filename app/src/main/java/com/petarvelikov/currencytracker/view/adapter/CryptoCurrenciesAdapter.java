package com.petarvelikov.currencytracker.view.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.petarvelikov.currencytracker.R;
import com.petarvelikov.currencytracker.consts.Constants;
import com.petarvelikov.currencytracker.model.CryptoCurrency;
import com.petarvelikov.currencytracker.preferences.SharedPreferencesHelper;
import com.petarvelikov.currencytracker.resources.ExchangeRatesResourcesHelper;
import com.squareup.picasso.Picasso;

import java.util.List;
import java.util.Locale;

public class CryptoCurrenciesAdapter extends RecyclerView.Adapter<CryptoCurrenciesAdapter.CryptoCurrencyViewHolder> {

  private List<CryptoCurrency> currencies;
  private OnClickListener listener;
  private SharedPreferencesHelper sharedPreferencesHelper;
  private ExchangeRatesResourcesHelper resourcesHelper;

  public CryptoCurrenciesAdapter(List<CryptoCurrency> currencies, OnClickListener listener,
                                 SharedPreferencesHelper sharedPreferencesHelper,
                                 ExchangeRatesResourcesHelper resourcesHelper) {
    this.currencies = currencies;
    this.listener = listener;
    this.sharedPreferencesHelper = sharedPreferencesHelper;
    this.resourcesHelper = resourcesHelper;
  }

  public void setCurrencies(List<CryptoCurrency> currencies) {
    this.currencies = currencies;
    notifyDataSetChanged();
  }

  @Override
  public CryptoCurrencyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
    View view = LayoutInflater
        .from(parent.getContext())
        .inflate(R.layout.list_item_crypto_currency, parent, false);
    return new CryptoCurrencyViewHolder(view);
  }

  @Override
  public void onBindViewHolder(CryptoCurrencyViewHolder holder, int position) {
    CryptoCurrency currency = this.currencies.get(position);
    String baseCurrency = sharedPreferencesHelper.getBaseCurrency();
    /**
     * Edge case which happens when changing base currency.
     * Changing the currency initiates loading of new data.
     * Between the time of launch of the request and the time the response is received calling
     * {@link CryptoCurrency#getPriceAnyCurrency(String)} results in null since the
     * {@link CryptoCurrency} instance still holds data for the previous base currency,
     * and we are trying to get info for the new base currency.
     */
    if (currency.getPriceAnyCurrency(baseCurrency) == null) {
      return;
    }
    holder.setName(currency.getName());
    holder.setSymbol(currency.getSymbol());
    holder.setValue(String.format(Locale.getDefault(), "%s %s",
        resourcesHelper.getCurrencySymbol(baseCurrency),
        currency.getPriceAnyCurrency(baseCurrency)));
    holder.setIcon(currency.getImageUrl());
    holder.itemView.setOnClickListener(event -> listener.onClick(currency.getId(), currency.getName(), currency.getSymbol()));
  }

  @Override
  public int getItemCount() {
    return this.currencies.size();
  }

  public interface OnClickListener {
    void onClick(String id, String name, String symbol);
  }

  static class CryptoCurrencyViewHolder extends RecyclerView.ViewHolder {

    private TextView txtName, txtSymbol, txtValue;
    private ImageView imgIcon;

    CryptoCurrencyViewHolder(View itemView) {
      super(itemView);
      txtName = itemView.findViewById(R.id.txtCurrencyName);
      txtSymbol = itemView.findViewById(R.id.txtCurrencySymbol);
      txtValue = itemView.findViewById(R.id.txtCurrencyValue);
      imgIcon = itemView.findViewById(R.id.imgCurrencyIcon);
    }

    void setName(String name) {
      this.txtName.setText(name);
    }

    void setSymbol(String symbol) {
      this.txtSymbol.setText(symbol);
    }

    void setValue(String value) {
      this.txtValue.setText(value);
    }

    void setIcon(String url) {
      Picasso.with(itemView.getContext())
          .load(Constants.API_CONSTANTS.BASE_URL_ICONS + url)
          .resize(48, 48)
          .placeholder(R.drawable.ic_placeholder)
          .centerCrop()
          .into(imgIcon);
    }
  }
}
