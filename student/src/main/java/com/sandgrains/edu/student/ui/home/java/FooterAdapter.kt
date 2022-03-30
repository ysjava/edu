package com.sandgrains.edu.student.ui.home.java

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import androidx.core.view.isVisible
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.lifecycleScope
import androidx.paging.LoadState
import androidx.paging.LoadStateAdapter
import androidx.recyclerview.widget.RecyclerView
import com.sandgrains.edu.student.R
import com.sandgrains.edu.student.logic.network.EduNetwork
import com.sandgrains.edu.student.utils.base.ServiceError
import com.sandgrains.edu.student.utils.custom.NestedBGABanner
import com.sandgrains.edu.student.utils.logd
import kotlinx.coroutines.launch

class FooterAdapter(val retry: () -> Unit,val lifecycleOwner: LifecycleOwner,val context: Context) : LoadStateAdapter<FooterAdapter.ViewHolder>() {

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
//        val progressBar: ProgressBar = itemView.findViewById(R.id.progress_bar)
//        val retryButton: Button = itemView.findViewById(R.id.retry_button)
//        val errorMsg: TextView = itemView.findViewById(R.id.tv_error_msg)
        val banner: NestedBGABanner = itemView.findViewById(R.id.banner)
    }

    override fun onBindViewHolder(holder: ViewHolder, loadState: LoadState) {
        lifecycleOwner.lifecycleScope.launch {
//            val banner = EduNetwork.getBanner()
//
//            val viewList = arrayListOf<View>()
//            val modelList = mutableListOf<JavaCourseModel.RecommendModel>()
//            val colorResList = listOf(R.color.test_color1, R.color.test_color2, R.color.test_color3)
//            for ((i, d) in banner.recomList.withIndex()) {
//                val view = LayoutInflater.from(context).inflate(R.layout.item_page, null, false)
//                view.findViewById<ImageView>(R.id.image).setImageResource(colorResList[i % 2])
//                viewList.add(view)
//                modelList.add(d)
//            }
//
//            holder.banner.setData(viewList, modelList, null)
        }

        "onBindViewHolder".logd()
//        holder.progressBar.isVisible = loadState is LoadState.Loading
//        holder.retryButton.isVisible = loadState is LoadState.Error
//        holder.errorMsg.isVisible = loadState is LoadState.Error
    }

    override fun onCreateViewHolder(parent: ViewGroup, loadState: LoadState): ViewHolder {
        "onCreateViewHolder".logd()
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_header, parent, false)
        val holder = ViewHolder(view)
//        holder.retryButton.setOnClickListener {
//            retry()
//        }

        //val error = (loadState as? LoadState.Error)?.error

//        error?.let {
//            val er = it as? ServiceError
//            holder.errorMsg.text = er?.msg ?: it.message
//        }
        return holder

    }
}