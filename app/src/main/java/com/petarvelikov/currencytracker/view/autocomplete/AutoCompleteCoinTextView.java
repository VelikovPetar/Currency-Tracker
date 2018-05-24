package com.petarvelikov.currencytracker.view.autocomplete;

import android.content.Context;
import android.graphics.Rect;
import android.support.v7.widget.AppCompatAutoCompleteTextView;
import android.util.AttributeSet;

public class AutoCompleteCoinTextView extends AppCompatAutoCompleteTextView {

  public AutoCompleteCoinTextView(Context context) {
    super(context);
  }

  public AutoCompleteCoinTextView(Context context, AttributeSet attrs) {
    super(context, attrs);
  }

  public AutoCompleteCoinTextView(Context context, AttributeSet attrs, int defStyleAttr) {
    super(context, attrs, defStyleAttr);
  }

  @Override
  public boolean enoughToFilter() {
    return true;
  }

  @Override
  protected void onFocusChanged(boolean focused, int direction, Rect previouslyFocusedRect) {
    super.onFocusChanged(focused, direction, previouslyFocusedRect);
    if (focused) {
      performFiltering(getText(), 0);
    }
  }
}
