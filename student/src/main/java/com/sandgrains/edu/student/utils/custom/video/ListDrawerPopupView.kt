package com.sandgrains.edu.student.utils.custom.video

import android.content.Context
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.lxj.xpopup.core.DrawerPopupView
import com.sandgrains.edu.student.R
import com.sandgrains.edu.student.model.Section

class ListDrawerPopupView (context: Context, private val dataLis: ArrayList<VideoParameterModel>, val videoParams: Array<String>, val callback: VideoParamSelectedCallback) : DrawerPopupView(context) {
    private lateinit var recyclerView: RecyclerView
    override fun getImplLayoutId(): Int {
        return R.layout.popup_drawer_list
    }

    override fun onCreate() {
        super.onCreate()
        recyclerView = findViewById(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(context)
        recyclerView.adapter = Adapter(dataLis)
    }

    inner class Adapter(dataList: ArrayList<VideoParameterModel>) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
        private val openDataList = mutableListOf<String>()
        private val titlePositions = mutableListOf<Int>()

        init {
            var p = 0
            //展开数据
            dataList.forEach { m ->
                openDataList.add(m.title)
                titlePositions.add(p++)
                m.options.forEach {
                    openDataList.add(it)
                    p++
                }
            }

        }

        inner class TitleViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
            val titleView = itemView.findViewById<TextView>(R.id.tv_title)

        }

        inner class OptionViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
            val optionView = itemView.findViewById<TextView>(R.id.tv_option)
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
            val layoutId = if (viewType == 1) R.layout.item_video_parms_title else R.layout.item_video_parms_option
            val view = LayoutInflater.from(parent.context).inflate(layoutId, parent, false)
            return if (viewType == 1) TitleViewHolder(view) else OptionViewHolder(view)
        }

        override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {

            if (holder is TitleViewHolder) {
                holder.titleView.text = openDataList[position]

            } else if (holder is OptionViewHolder) {
                holder.optionView.text = openDataList[position]
                holder.itemView.setOnClickListener { v ->
                    callback.callback(v as TextView)
                }
                val textView = holder.optionView
                if (textView.text == videoParams[1] || textView.text == videoParams[0]) {
                    textView.setTextColor(Color.RED)
                }
            }
        }

        override fun getItemViewType(position: Int): Int {
            return if (titlePositions.contains(position)) 1 else 2
        }

        override fun getItemCount(): Int {
            return openDataList.size
        }
    }

}

data class VideoParameterModel(
        val title: String,
        val options: List<String>
)

interface SelectedCallback {
    fun optionSelected(section: Section)
}

interface VideoParamSelectedCallback {
    fun callback(view: TextView)
}