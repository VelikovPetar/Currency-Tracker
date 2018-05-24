package com.petarvelikov.currencytracker.view.autocomplete;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Filter;
import android.widget.ImageView;
import android.widget.TextView;

import com.petarvelikov.currencytracker.R;
import com.petarvelikov.currencytracker.consts.Constants;
import com.petarvelikov.currencytracker.model.CryptoCurrency;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class AutoCompleteCoinAdapter extends ArrayAdapter<CryptoCurrency> {

  private List<CryptoCurrency> activeData;
  private List<CryptoCurrency> originalData;
  private int layoutResourceId;

  public AutoCompleteCoinAdapter(@NonNull Context context, int layoutResourceId, @NonNull List<CryptoCurrency> cryptoCurrencies) {
    super(context, layoutResourceId, cryptoCurrencies);
    this.activeData = cryptoCurrencies;
    this.layoutResourceId = layoutResourceId;
    this.originalData = new ArrayList<>(activeData);
  }

  @NonNull
  @Override
  public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
    if (convertView == null) {
      LayoutInflater layoutInflater = LayoutInflater.from(getContext());
      convertView = layoutInflater.inflate(layoutResourceId, parent, false);
    }
    CryptoCurrency cryptoCurrency = activeData.get(position);
    ImageView coinIcon = convertView.findViewById(R.id.imgAutoCompleteCoinIcon);
    TextView coinName = convertView.findViewById(R.id.txtAutoCompleteCoinName);
    Picasso.with(getContext())
        .load(Constants.API_CONSTANTS.BASE_URL_ICONS + cryptoCurrency.getImageUrl())
        .resize(48, 48)
        .placeholder(R.drawable.ic_launcher_foreground)
        .centerCrop()
        .into(coinIcon);
    coinName.setText(cryptoCurrency.getName());
    return convertView;
  }

  @Override
  public int getCount() {
    return activeData.size();
  }

  @Nullable
  @Override
  public CryptoCurrency getItem(int position) {
    return activeData.get(position);
  }

  @NonNull
  @Override
  public Filter getFilter() {
    return new Filter() {
      @Override
      protected FilterResults performFiltering(CharSequence constraint) {
        FilterResults filterResults = new FilterResults();
        if (constraint == null || constraint.length() == 0) {
          filterResults.values = originalData;
          filterResults.count = originalData.size();
          return filterResults;
        }
        String prefix = constraint.toString().toLowerCase();
        List<CryptoCurrency> matches = findMatches(prefix);
        filterResults.values = matches;
        filterResults.count = matches.size();
        return filterResults;
      }

      @Override
      protected void publishResults(CharSequence constraint, FilterResults results) {
        if (results.values != null) {
          activeData = (List<CryptoCurrency>) results.values;
        } else {
          activeData = null;
        }
        if (results.count > 0) {
          notifyDataSetChanged();
        } else {
          notifyDataSetInvalidated();
        }
      }
    };
  }

  private List<CryptoCurrency> findMatches(String prefix) {
    List<CryptoCurrency> matches = new ArrayList<>();
    for (CryptoCurrency cc : originalData) {
      if (cc.getName().toLowerCase().startsWith(prefix)) {
        matches.add(cc);
      }
    }
    return matches;
  }
}
