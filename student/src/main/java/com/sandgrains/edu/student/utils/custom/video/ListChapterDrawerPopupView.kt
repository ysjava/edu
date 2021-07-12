package com.sandgrains.edu.student.utils.custom.video

import android.content.Context
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.lxj.xpopup.core.DrawerPopupView
import com.sandgrains.edu.student.R
import com.sandgrains.edu.student.model.Section

class ListChapterDrawerPopupView(context: Context, private val dataList: MutableList<Section>, val selectedSection: String, val callback: SelectedCallback) : DrawerPopupView(context) {
    private lateinit var recyclerView: RecyclerView
    override fun getImplLayoutId(): Int {
        return R.layout.popup_chaptr_drawer_list
    }

    override fun onCreate() {
        super.onCreate()
        recyclerView = findViewById(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(context)
        recyclerView.adapter = Adapter()
    }


    inner class Adapter : RecyclerView.Adapter<Adapter.ViewHolder>() {

        inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
            val name: TextView = itemView.findViewById(R.id.tv_name);
            val image: ImageView = itemView.findViewById(R.id.iv_image);
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
            val view = LayoutInflater.from(parent.context).inflate(R.layout.item_chapter_drawer, parent, false)
            return ViewHolder(view)
        }

        override fun onBindViewHolder(holder: ViewHolder, position: Int) {

            holder.name.text = dataList[position].name
            if (dataList[position].name == selectedSection) {
                holder.name.setTextColor(Color.RED)
                holder.image.setImageResource(R.drawable.ic_play_fill_red)
            } else {
                holder.name.setTextColor(Color.rgb( 183, 183, 183))
                holder.image.setImageResource(R.drawable.ic_play_fill)
            }

            holder.itemView.setOnClickListener {
                callback.optionSelected(dataList[position])
            }
        }

        override fun getItemCount(): Int {
            return dataList.size
        }
    }
}
