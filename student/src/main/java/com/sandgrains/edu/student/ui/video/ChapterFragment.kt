package com.sandgrains.edu.student.ui.video

import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.hi.dhl.binding.viewbind
import com.sandgrains.edu.student.R
import com.sandgrains.edu.student.databinding.FragmentChapterBinding
import com.sandgrains.edu.student.model.Chapter
import com.sandgrains.edu.student.model.Section
import com.sandgrains.edu.student.utils.custom.video.SelectedCallback

class ChapterFragment : Fragment(R.layout.fragment_chapter) {
    private val binding: FragmentChapterBinding by viewbind()
    private val viewModel by lazy { ViewModelProvider(activity!!).get(PlayViewModel::class.java) }

    private val lm = LinearLayoutManager(context)
    private lateinit var adapter: Adapter
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        initObserve()
        binding.recyclerView.layoutManager = lm
        binding.recyclerView.itemAnimator?.changeDuration = 0
        this.adapter = Adapter(selectedCallback = object : SelectedCallback {
            override fun optionSelected(section: Section) {
                viewModel.updateSectionSelected(Pair(section,"ChapterFragment"))
                Toast.makeText(context, "你选择了:${section.name}", Toast.LENGTH_SHORT).show()
            }
        })
        binding.recyclerView.adapter = adapter
    }


    private fun initObserve() {
        viewModel.loadData.observe(viewLifecycleOwner, { result ->
//            val course = result.getOrNull()
//            if (course != null) {
//
//            }
            val videoUrlList = listOf(
                    "http://9890.vod.myqcloud.com/9890_4e292f9a3dd011e6b4078980237cc3d3.f20.mp4",
                    "http://7xjmzj.com1.z0.glb.clouddn.com/20171026175005_JObCxCE2.mp4",
                    "https://res.exexm.com/cw_145225549855002")

            val chapterList = mutableListOf<Chapter>()
            for (i in 0..5) {
                val sections = mutableListOf<Section>()
                for (j in 0..5) {
                    val section = Section("我是第${i}章，第${j}小节", videoUrlList[j%3])
                    sections.add(section)
                }
                val chapter = Chapter("第${i}章", sections)
                chapterList.add(chapter)
            }

            val adapter = adapter
            adapter.dataList.clear()
            adapter.dataList.addAll(chapterList)
            adapter.updateData(chapterList)

        })

        viewModel.sectionSelected.observe(viewLifecycleOwner, {
            //如果是我这界面自己触发的选中就不用处理了
            if (it.second == "ChapterFragment") return@observe
            //需要设置选中状态的位置
            var posi = -1
            adapter.openDataList.forEachIndexed { position, triple ->
                if (triple.first == it.first.name) posi = position
            }

            if (posi != -1)
                adapter.setSectionSelected(posi)
        })
    }


    class Adapter(val dataList: MutableList<Chapter> = mutableListOf(), private val selectedCallback: SelectedCallback) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
        //三个数据分别是 章节名｜小节名； 章节总时间｜小节总时间； 视频url
        val openDataList = mutableListOf<Triple<String, String, String>>()
        private val titlePositions = mutableListOf<Int>()

        init {
            formatData(dataList)
        }

        fun setSectionSelected(position: Int){
            selectedText = openDataList[position].first
            notifyItemChanged(posi)
            notifyItemChanged(position)
            posi = position
        }

        fun updateData(dataList: MutableList<Chapter>) {
            formatData(dataList)
            notifyDataSetChanged()
        }

        private fun formatData(dataList: MutableList<Chapter>) {
            var i = 0
            //整理数据
            dataList.forEach { chapter ->
                openDataList.add(Triple(chapter.name, "188:29", ""))
                titlePositions.add(i++)
                chapter.sectionList.forEach {
                    openDataList.add(Triple(it.name, "12:24", it.videoUri))
                    i++
                }
            }
        }

        class ChapterViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
            val name = itemView.findViewById<TextView>(R.id.tv_course_name)
        }

        class SectionViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView), Cloneable {
            val icon = itemView.findViewById<ImageView>(R.id.iv_icon)
            val name = itemView.findViewById<TextView>(R.id.tv_section_name)
            val time = itemView.findViewById<TextView>(R.id.tv_section_time)
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {

            val layoutId = if (viewType == 1) R.layout.item_course_chapter else R.layout.item_course_section
            val view = LayoutInflater.from(parent.context).inflate(layoutId, parent, false)
            return if (viewType == 1) ChapterViewHolder(view) else SectionViewHolder(view)
        }

        //        private var sectionViewHolder: SectionViewHolder? = null
        private var posi: Int = -1
        private var selectedText: String = ""
        override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
            if (holder is ChapterViewHolder) {
                val str = openDataList[position].first + openDataList[position].second
                holder.name.text = str
            } else if (holder is SectionViewHolder) {
                holder.apply {
                    name.text = openDataList[position].first
                    time.text = openDataList[position].second

                    if (openDataList[position].first == selectedText) {
                        icon.setImageResource(R.drawable.ic_player_red)
                        name.setTextColor(Color.RED)
                    } else {
                        icon.setImageResource(R.drawable.ic_player)
                        name.setTextColor(Color.rgb(75, 75, 75))
                    }

                    itemView.setOnClickListener {
                        if (posi != -1)
                            notifyItemChanged(posi)

                        icon.setImageResource(R.drawable.ic_player_red)
                        name.setTextColor(Color.RED)
                        val section = Section(openDataList[position].first,openDataList[position].third)
                        selectedCallback.optionSelected(section)
                        posi = position
                        selectedText = name.text.toString()
                    }
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