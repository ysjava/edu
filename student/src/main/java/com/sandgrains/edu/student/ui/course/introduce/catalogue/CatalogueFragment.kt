package com.sandgrains.edu.student.ui.course.introduce.catalogue

import android.media.Image
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.hi.dhl.binding.viewbind
import com.sandgrains.edu.student.R
import com.sandgrains.edu.student.databinding.FragmentCatalogueBinding
import com.sandgrains.edu.student.databinding.FragmentCourseJavaBinding
import com.sandgrains.edu.student.databinding.Test2Binding

class CatalogueFragment : Fragment(R.layout.fragment_catalogue) {
    private val binding: FragmentCatalogueBinding by viewbind()
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        binding.recyclerView.layoutManager = LinearLayoutManager(context)
//        binding.recyclerView.adapter = Adapter(listOf("张三","ABCaas","ABCaas","ABCaas","ABCaas","ABCaas","李四","ABCaas","ABCaas","ABCaas","ABCaas","王武","ABCaas","ABCaas","ABCaas","ABCaas","ABCaas","ABCaas","ABCaas","ABCaas","ABCaas","ABCaas","ABCaas","ABCaas","ABCaas","ABCaas","ABCaas","ABCaas","ABCaas","ABCaas","ABCaas","ABCaas","ABCaas","ABCaas","ABCaas","ABCaas","ABCaas","ABCaas","ABCaas","ABCaas","ABCaas","ABCaas","ABCaas","ABCaas","ABCaas","ABCaas","ABCaas","ABCaas","ABCaas","ABCaas","ABCaas","ABCaas","ABCaas","ABCaas","ABCaas","ABCaas"))
        binding.recyclerView.adapter = Adapter(listOf(R.drawable.jianjie1, R.drawable.jianjie2))
    }

    class Adapter(val dataList: List<Int>) : RecyclerView.Adapter<Adapter.ViewHolder>() {
        class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
            val image = itemView.findViewById<ImageView>(R.id.iv_image)
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
            val view = LayoutInflater.from(parent.context).inflate(R.layout.item_image, parent, false)
            return ViewHolder(view)
        }

        override fun onBindViewHolder(holder: ViewHolder, position: Int) {
            holder.image.setImageResource(dataList[position])
        }

        override fun getItemCount(): Int {
            return dataList.size
        }
    }


}