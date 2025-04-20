package com.example.substracker

import android.view.View
import androidx.recyclerview.widget.RecyclerView

/*
It extends RecyclerView.AdapterDataObserver, which lets you respond to changes in the adapter’s data.
rv is the RecyclerView showing your list of tasks.
ev is the "empty view"—a TextView, ImageView, or any View that says “no data.”
 */
class EmptyDataObserver (rv: RecyclerView?, ev: View?): RecyclerView.AdapterDataObserver() {
    private var emptyView: View? = null // Stores references to the empty view
    private var recyclerView: RecyclerView? = null // Stores references to the RecyclerView.

    /*Stores the RecyclerView and empty view.
    Immediately checks if the list is empty. */
    init {
        recyclerView = rv
        emptyView = ev
        checkIfEmpty()
    }

    /*
    This is the core logic:
    If the adapter’s itemCount == 0, it shows the empty view and hides the RecyclerView.
    Otherwise, it shows the RecyclerView and hides the empty message.*/
    private fun checkIfEmpty() {
        if (emptyView != null && recyclerView!!.adapter != null) {
            val emptyViewVisible = recyclerView!!.adapter!!.itemCount == 0
            emptyView!!.visibility = if (emptyViewVisible) View.VISIBLE else View.GONE
            recyclerView!!.visibility = if (emptyViewVisible) View.GONE else View.VISIBLE
        }
    }

    /*This is triggered whenever the adapter data changes (e.g., when you call setData()).
    It calls checkIfEmpty() again to update the UI.*/
    override fun onChanged() {
        super.onChanged()
        checkIfEmpty()
    }
}
