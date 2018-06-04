package com.petarvelikov.currencytracker.view.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.petarvelikov.currencytracker.BuildConfig;
import com.petarvelikov.currencytracker.R;
import com.petarvelikov.currencytracker.consts.Constants;
import com.petarvelikov.currencytracker.model.ExchangeRate;
import com.petarvelikov.currencytracker.resources.ExchangeRatesResourcesHelper;
import com.squareup.picasso.Picasso;

import java.util.List;
import java.util.Locale;

public class ExchangeRatesAdapter extends RecyclerView.Adapter<ExchangeRatesAdapter.ExchangeRateViewHolder> {

  private List<ExchangeRate> exchangeRates;
  private ExchangeRatesResourcesHelper resourcesHelper;


  public ExchangeRatesAdapter(List<ExchangeRate> exchangeRates,
                              ExchangeRatesResourcesHelper resourcesHelper) {
    this.exchangeRates = exchangeRates;
    this.resourcesHelper = resourcesHelper;
  }

  public void setExchangeRates(List<ExchangeRate> exchangeRates) {
    this.exchangeRates = exchangeRates;
    notifyDataSetChanged();
  }

  @Override
  public ExchangeRateViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
    View view = LayoutInflater
        .from(parent.getContext())
        .inflate(R.layout.list_item_exchange_rate, parent, false);
    return new ExchangeRateViewHolder(view);
  }

  @Override
  public void onBindViewHolder(ExchangeRateViewHolder holder, int position) {
    ExchangeRate exchangeRate = exchangeRates.get(position);
    holder.setExchangeFrom(exchangeRate.getFrom());
    holder.setExchangeRate(String.format(Locale.getDefault(), " %.6f", exchangeRate.getExchangeRate()));
    if (BuildConfig.FIXER_API_VERSION == 2) {
      holder.setCurrencySymbol(resourcesHelper.getCurrencySymbol("EUR"));
    } else {
      holder.setCurrencySymbol(resourcesHelper.getCurrencySymbol(exchangeRate.getTo()));
    }
    holder.setCurrencyCountryFlag(resourcesHelper.getCurrencyCountry(exchangeRate.getFrom()));
  }

  @Override
  public int getItemCount() {
    return exchangeRates.size();
  }

  static class ExchangeRateViewHolder extends RecyclerView.ViewHolder {

    private TextView txtExchangeFrom, txtExchangeRate, txtCurrencySymbol;
    private ImageView imgCurrencyCountryFlag;

    ExchangeRateViewHolder(View itemView) {
      super(itemView);
      txtExchangeFrom = itemView.findViewById(R.id.txtExchangeFrom);
      txtExchangeRate = itemView.findViewById(R.id.txtExchangeRate);
      txtCurrencySymbol = itemView.findViewById(R.id.txtCurrencyShortSymbol);
      imgCurrencyCountryFlag = itemView.findViewById(R.id.imgCurrencyCountryFlag);
    }

    void setExchangeFrom(String exchangeFrom) {
      txtExchangeFrom.setText(exchangeFrom);
    }

    void setExchangeRate(String exchangeRate) {
      txtExchangeRate.setText(exchangeRate);
    }

    void setCurrencySymbol(String symbol) {
      txtCurrencySymbol.setText(symbol);
    }

    void setCurrencyCountryFlag(String countryCode) {
      Picasso.with(itemView.getContext())
          .load(Constants.API_CONSTANTS.BASE_URL_COUNTRY_FLAGS.replace("%country_code%", countryCode))
          .resize(48, 48)
          .placeholder(R.drawable.ic_placeholder)
          .centerCrop()
          .into(imgCurrencyCountryFlag);
    }
  }
}
