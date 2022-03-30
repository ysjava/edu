package com.sandgrains.edu.student.utils.base

import android.renderscript.Element
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView

abstract class BaseRecyclerAdapter<DataType>(var dataList: MutableList<DataType> = mutableListOf()) :
        RecyclerView.Adapter<RecyclerView.ViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val itemLayoutId = getItemLayoutId(viewType)
        val itemView = LayoutInflater.from(parent.context).inflate(itemLayoutId, parent, false)
        return createViewHolder(viewType, itemView)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        bindViewHolder(holder, dataList[position],position)
    }

    override fun getItemCount(): Int {
        return dataList.size
    }

    override fun getItemViewType(position: Int): Int {
        return getItemViewType(position, dataList[position])
    }

    abstract fun getItemLayoutId(viewType: Int): Int
    abstract fun bindViewHolder(holder: RecyclerView.ViewHolder, data: DataType,position: Int)
    abstract fun createViewHolder(viewType: Int, itemView: View): RecyclerView.ViewHolder

    open fun getItemViewType(position: Int, data: DataType): Int {
        return 0
    }

    fun updateDataList(dataList: List<DataType>) {
        this.dataList.clear()
        this.dataList.addAll(dataList)
        notifyDataSetChanged()
    }

    fun updateData(data: DataType, position: Int) {
        dataList[position] = data
        notifyItemChanged(position)
    }

    fun refreshByDiff(diffResult: DiffUtil.DiffResult, newList: List<DataType>) {
        dataList.clear()
        dataList.addAll(newList)

        diffResult.dispatchUpdatesTo(this)
    }
}