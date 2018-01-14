package com.petarvelikov.currencytracker.view.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.petarvelikov.currencytracker.R;
import com.petarvelikov.currencytracker.model.CryptoCurrency;

import java.util.List;

public class CryptoCurrenciesAdapter extends RecyclerView.Adapter<CryptoCurrenciesAdapter.CryptoCurrencyViewHolder> {

    private List<CryptoCurrency> currencies;

    public CryptoCurrenciesAdapter(List<CryptoCurrency> currencies) {
        this.currencies = currencies;
    }

    public void setCurrencies(List<CryptoCurrency> currencies) {
        this.currencies = currencies;
        notifyDataSetChanged();
    }

    @Override
    public CryptoCurrencyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_crypto_currency, parent, false);
        return new CryptoCurrencyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(CryptoCurrencyViewHolder holder, int position) {
        CryptoCurrency currency = this.currencies.get(position);
        holder.setName(currency.getName());
        holder.setSymbol(currency.getSymbol());
        holder.setValue(currency.getPriceEur());
    }

    @Override
    public int getItemCount() {
        return this.currencies.size();
    }

    static class CryptoCurrencyViewHolder extends RecyclerView.ViewHolder {

        private TextView txtName, txtSymbol, txtValue;

        CryptoCurrencyViewHolder(View itemView) {
            super(itemView);
            txtName = itemView.findViewById(R.id.txtCurrencyName);
            txtSymbol = itemView.findViewById(R.id.txtCurrencySymbol);
            txtValue = itemView.findViewById(R.id.txtCurrencyValue);
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
    }
}
