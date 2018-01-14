package com.petarvelikov.currencytracker.view.adapter;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

public abstract class EndlessRecyclerViewScrollListener extends RecyclerView.OnScrollListener {

    private int visibleThreshold = 5;
    private int prevTotalItemCount = 0;
    private boolean isLoading = true;

    private LinearLayoutManager layoutManager;

    public EndlessRecyclerViewScrollListener(LinearLayoutManager layoutManager) {
        this.layoutManager = layoutManager;
    }

    @Override
    public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
        int totalItemCount = layoutManager.getItemCount();
        int lastVisibleItemPosition = layoutManager.findLastVisibleItemPosition();
        if (isLoading && (totalItemCount > prevTotalItemCount)) {
            isLoading = false;
            prevTotalItemCount = totalItemCount;
        }

        if (!isLoading && lastVisibleItemPosition > totalItemCount - visibleThreshold) {
            isLoading = true;
            onLoadMore(totalItemCount, recyclerView);
        }
    }

    public abstract void onLoadMore(int start, RecyclerView recyclerView);
}
