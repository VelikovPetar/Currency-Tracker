package com.petarvelikov.currencytracker.view.activity;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.petarvelikov.currencytracker.R;

import java.util.List;

public class BaseCurrencyMenuArrayAdapter extends ArrayAdapter<CharSequence> {

  private List<CharSequence> menuItems;

  public BaseCurrencyMenuArrayAdapter(@NonNull Context context, int resource, @NonNull List<CharSequence> objects) {
    super(context, resource, objects);
    menuItems = objects;
  }

  @NonNull
  @Override
  public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
    if (convertView == null) {
      convertView = LayoutInflater.from(getContext())
          .inflate(R.layout.list_item_base_currency_menu_item, parent, false);
    }
    TextView textView = (TextView) convertView;
    textView.setText(menuItems.get(position));
    return convertView;
  }
}
