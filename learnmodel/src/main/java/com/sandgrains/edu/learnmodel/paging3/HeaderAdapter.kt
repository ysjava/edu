package com.sandgrains.edu.learnmodel.paging3

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ProgressBar
import androidx.paging.LoadState
import androidx.paging.LoadStateAdapter
import androidx.recyclerview.widget.RecyclerView
import com.sandgrains.edu.learnmodel.R

class HeaderAdapter():LoadStateAdapter<HeaderAdapter.ViewHolder>() {

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val progressBar: ProgressBar = itemView.findViewById(R.id.progress_bar)
        val retryButton: Button = itemView.findViewById(R.id.retry_button)
    }

    override fun onBindViewHolder(holder: ViewHolder, loadState: LoadState) {

    }

    override fun onCreateViewHolder(parent: ViewGroup, loadState: LoadState): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.footer_item, parent, false)
        return ViewHolder(view)
    }
}