package com.petarvelikov.currencytracker.view.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.petarvelikov.currencytracker.R;
import com.petarvelikov.currencytracker.consts.Constants;
import com.petarvelikov.currencytracker.model.Transaction;
import com.petarvelikov.currencytracker.utils.CalendarUtils;
import com.squareup.picasso.Picasso;

import java.util.Date;
import java.util.List;
import java.util.Locale;

public class TransactionsAdapter extends RecyclerView.Adapter<TransactionsAdapter.TransactionViewHolder> {

  private List<Transaction> transactions;
  private OnTransactionClickListener listener;

  public TransactionsAdapter(List<Transaction> transactions, OnTransactionClickListener listener) {
    this.transactions = transactions;
    this.listener = listener;
  }

  public void setTransactions(List<Transaction> transactions) {
    this.transactions = transactions;
    notifyDataSetChanged();
  }

  @Override
  public TransactionViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
    View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_transaction, parent, false);
    return new TransactionViewHolder(view);
  }

  @Override
  public void onBindViewHolder(TransactionViewHolder holder, int position) {
    Transaction transaction = transactions.get(position);
    holder.setCurrencyIcon(transaction.getCoinImageUrl());
    holder.setCurrencyName(transaction.getCoinName());
    String dateText = CalendarUtils.dateToSimpleDateString(new Date(transaction.getDateOfTransaction()));
    holder.setTransactionDate(dateText);
    holder.setCoinAmount("" + transaction.getCoinAmount(), transaction.getIsPurchase());
    holder.setCoinValueAtPurchaseTime("" + transaction.getCoinPrice(), transaction.getBaseCurrency(), transaction.getIsPurchase());
    holder.itemView.setOnClickListener(v -> {
      listener.onTransactionClick(transaction.getUid());
    });
  }

  @Override
  public int getItemCount() {
    return transactions.size();
  }

  public interface OnTransactionClickListener {
    void onTransactionClick(int transactionId);
  }

  static class TransactionViewHolder extends RecyclerView.ViewHolder {

    private ImageView imgCurrencyIcon;
    private TextView txtCurrencyName;
    private TextView txtTransactionDate;
    private TextView txtCoinAmount;
    private TextView txtCoinValueAtPurchaseTime;

    public TransactionViewHolder(View itemView) {
      super(itemView);
      imgCurrencyIcon = itemView.findViewById(R.id.imgTransactionCurrencyIcon);
      txtCurrencyName = itemView.findViewById(R.id.txtTransactionCurrencyName);
      txtTransactionDate = itemView.findViewById(R.id.txtTransactionDate);
      txtCoinAmount = itemView.findViewById(R.id.txtTransactionCoinAmount);
      txtCoinValueAtPurchaseTime = itemView.findViewById(R.id.txtCoinValueAtPurchaseTime);
    }

    public void setCurrencyIcon(String url) {
      Picasso.with(itemView.getContext())
          .load(Constants.API_CONSTANTS.BASE_URL_ICONS + url)
          .resize(48, 48)
          .placeholder(R.drawable.ic_placeholder)
          .centerCrop()
          .into(imgCurrencyIcon);
    }

    public void setCurrencyName(String name) {
      txtCurrencyName.setText(name);
    }

    public void setTransactionDate(String date) {
      String text = String.format(Locale.getDefault(), "%s %s",
          itemView.getContext().getString(R.string.transaction_date), date);
      txtTransactionDate.setText(text);
    }

    public void setCoinAmount(String amount, int isPurchase) {
      int textId = isPurchase == 1 ? R.string.amount_purchased : R.string.amount_sold;
      String text = String.format(Locale.getDefault(), "%s %s",
          itemView.getContext().getString(textId), amount);
      txtCoinAmount.setText(text);
    }

    public void setCoinValueAtPurchaseTime(String value, String baseCurrency, int isPurchase) {
      int textId = isPurchase == 1 ? R.string.coin_value_at_purchase_time : R.string.coin_value_at_sell_time;
      String text = String.format(Locale.getDefault(), "%s %s %s",
          itemView.getContext().getString(textId), value, baseCurrency);
      txtCoinValueAtPurchaseTime.setText(text);
    }
  }
}
