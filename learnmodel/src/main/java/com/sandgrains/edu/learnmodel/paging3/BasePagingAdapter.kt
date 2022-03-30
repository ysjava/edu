package com.sandgrains.edu.learnmodel.paging3

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.sandgrains.edu.learnmodel.R
import com.sandgrains.edu.learnmodel.paging3.model.Repo

data class Course(val id:Int,val name:String,val desc:String)
class BasePagingAdapter(val context:Context):PagingDataAdapter<Repo,BasePagingAdapter.ViewHolder>(diffCallback) {
    companion object{
        private val diffCallback = object : DiffUtil.ItemCallback<Repo>(){
            override fun areItemsTheSame(oldItem: Repo, newItem: Repo): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: Repo, newItem: Repo): Boolean {
                return oldItem == newItem
            }

        }
    }
    class ViewHolder(itemView:View):RecyclerView.ViewHolder(itemView){
//        val name: TextView = itemView.findViewById(R.id.tv_name)
//        val desc: TextView = itemView.findViewById(R.id.tv_desc)
        val name: TextView = itemView.findViewById(R.id.name_text)
        val description: TextView = itemView.findViewById(R.id.description_text)
        val starCount: TextView = itemView.findViewById(R.id.star_count_text)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val repo = getItem(position)
        if (repo != null) {
//            holder.name.text = repo.name
//            holder.desc.text = repo.desc
            holder.name.text = repo.name
//            holder.description.text = repo.description
            holder.starCount.text = repo.starCount.toString()
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemView = LayoutInflater.from(context).inflate(R.layout.repo_item,parent,false)
        return ViewHolder(itemView = itemView)
    }
}