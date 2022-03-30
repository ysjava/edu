package com.sandgrains.edu.student.ui.course.introduce.brief

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.hi.dhl.binding.viewbind
import com.sandgrains.edu.student.R
import com.sandgrains.edu.student.databinding.FragmentBriefIntroductionBinding
import com.sandgrains.edu.student.databinding.FragmentCatalogueBinding

class BriefIntroductionFragment:Fragment(R.layout.fragment_brief_introduction) {

    private val binding: FragmentBriefIntroductionBinding by viewbind()
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        binding.recyclerView.layoutManager = LinearLayoutManager(context)
        binding.recyclerView.adapter = Adapter(listOf("ABCaas","ABCaas","ABCaas","ABCaas","ABCaas","ABCaas","ABCaas","ABCaas"))

    }



    class Adapter(val dataList: List<String>) : RecyclerView.Adapter<Adapter.ViewHolder>(){
        class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
            val text = itemView.findViewById<TextView>(R.id.tv_test)
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
            val view = LayoutInflater.from(parent.context).inflate(R.layout.item_test,parent,false)
            return ViewHolder(view)
        }

        override fun onBindViewHolder(holder: ViewHolder, position: Int) {
            holder.text.text = dataList[position]
        }

        override fun getItemCount(): Int {
            return  dataList.size
        }
    }

}