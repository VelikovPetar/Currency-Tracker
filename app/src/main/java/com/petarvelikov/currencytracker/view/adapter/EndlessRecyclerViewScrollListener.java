package com.petarvelikov.currencytracker.view.adapter;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

public abstract class EndlessRecyclerViewScrollListener extends RecyclerView.OnScrollListener {

  private int visibleThreshold = 5;
  private int prevTotalItemCount = 0;
  private boolean isLoading = true;
  private boolean isEndReached = false;

  private LinearLayoutManager layoutManager;

  public EndlessRecyclerViewScrollListener(LinearLayoutManager layoutManager) {
    this.layoutManager = layoutManager;
  }

  @Override
  public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
    int totalItemCount = layoutManager.getItemCount();
    int lastVisibleItemPosition = layoutManager.findLastVisibleItemPosition();
    if (isLoading && (totalItemCount > prevTotalItemCount)) {
      prevTotalItemCount = totalItemCount;
    }

    if (!isLoading && !isEndReached && lastVisibleItemPosition > totalItemCount - visibleThreshold) {
      onLoadMore(totalItemCount, recyclerView);
    }
  }

  public void setLoading(boolean isLoading) {
    this.isLoading = isLoading;
  }

  public void setEndReached(boolean isEndReached) {
    this.isEndReached = isEndReached;
  }

  public abstract void onLoadMore(int start, RecyclerView recyclerView);
}
