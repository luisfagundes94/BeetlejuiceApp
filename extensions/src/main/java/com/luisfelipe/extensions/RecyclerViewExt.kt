package com.luisfelipe.extensions

import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

fun Fragment.horizontalRecyclerViewLayout() = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)

fun Fragment.verticalRecyclerViewLayout() = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)

fun RecyclerView.setupScroll(
    layoutManager: LinearLayoutManager,
    methodToInvokeAtEnd: () -> Unit
) {
    this.addOnScrollListener(object : RecyclerView.OnScrollListener() {
        override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
            if (checkIfIsTheListEnd(layoutManager, dy)) {
                methodToInvokeAtEnd()
            }
        }
    })
}

private fun checkIfIsTheListEnd(layoutManager: LinearLayoutManager, dy: Int): Boolean {
    with(layoutManager) {
        val visibleItemCount = childCount
        val totalItemCount = itemCount
        val firstVisibleItemPosition = findFirstVisibleItemPosition()
        return@checkIfIsTheListEnd visibleItemCount + firstVisibleItemPosition >= totalItemCount && dy > 0
    }
}
