package com.baris.nasaproject.ui.scroll

import android.widget.AbsListView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.baris.nasaproject.common.Constants
import com.baris.nasaproject.ui.main.MainViewModel

class RoverScrollListener(
    val layoutManager: LinearLayoutManager,
    val roverName: String,
    val viewModel: MainViewModel
) : RecyclerView.OnScrollListener() {

    private var isLoading = true
    private var firstVisibleItem = 0
    private var visibleItemCount = 0
    private var totalItemCount = 0

    override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
        super.onScrollStateChanged(recyclerView, newState)
        if (newState == AbsListView.OnScrollListener.SCROLL_STATE_TOUCH_SCROLL)
            isLoading = true
    }

    override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
        super.onScrolled(recyclerView, dx, dy)

        visibleItemCount = recyclerView.childCount
        totalItemCount = layoutManager.itemCount
        firstVisibleItem = layoutManager.findFirstVisibleItemPosition()

        if (isLoading && (visibleItemCount + firstVisibleItem) == totalItemCount) {
            isLoading = false
            viewModel.getRoverPhotos(
                roverName = roverName,
                sol = Constants.DEFAULT_SOL
            )
        }

    }

}