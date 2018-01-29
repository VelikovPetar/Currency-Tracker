package com.petarvelikov.currencytracker.view.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.petarvelikov.currencytracker.R;
import com.petarvelikov.currencytracker.model.ExchangeRate;

import java.util.List;

public class ExchangeRatesAdapter extends RecyclerView.Adapter<ExchangeRatesAdapter.ExchangeRateViewHolder> {

    private List<ExchangeRate> exchangeRates;


    public ExchangeRatesAdapter(List<ExchangeRate> exchangeRates) {
        this.exchangeRates = exchangeRates;
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
        holder.setExchangeRate(String.valueOf(exchangeRate.getExchangeRate()));
    }

    @Override
    public int getItemCount() {
        return exchangeRates.size();
    }

    static class ExchangeRateViewHolder extends RecyclerView.ViewHolder {

        private TextView txtExchangeFrom, txtExchangeRate;

        ExchangeRateViewHolder(View itemView) {
            super(itemView);
            txtExchangeFrom = itemView.findViewById(R.id.txtExchangeFrom);
            txtExchangeRate = itemView.findViewById(R.id.txtExchangeRate);
        }

        void setExchangeFrom(String exchangeFrom) {
            txtExchangeFrom.setText(exchangeFrom);
        }

        void setExchangeRate(String exchangeRate) {
            txtExchangeRate.setText(exchangeRate);
        }
    }
}
