package com.sandgrains.edu.student.ui.home.java

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.sandgrains.edu.student.R
import com.sandgrains.edu.student.model.Course
import com.sandgrains.edu.student.model.course.BannerModel
import com.sandgrains.edu.student.model.course.JavaCourseModel
import com.sandgrains.edu.student.model.course.TuiJian
import com.sandgrains.edu.student.ui.home.JavaRecommend
import com.sandgrains.edu.student.utils.base.ItemClickCallback
import com.sandgrains.edu.student.utils.custom.NestedBGABanner
import com.sandgrains.edu.student.utils.custom.PageIndicator
import com.sandgrains.edu.student.utils.custom.PageTurningLayout
import com.sandgrains.edu.student.utils.custom.RoundAngleImageView
import com.sandgrains.edu.student.utils.logd
import com.sandgrains.edu.student.utils.loge
import com.sandgrains.edu.student.utils.logi

class CourseAdapter(
        private val context: Context,
        private val callback: ItemClickCallback<JavaRecommend>? = null
) : PagingDataAdapter<JavaRecommend, RecyclerView.ViewHolder>(COMPARATOR) {
    private lateinit var recyclerView: RecyclerView
    private var tagViewHolder: PageTurningViewHolder? = null
    companion object {
        private val COMPARATOR = object : DiffUtil.ItemCallback<JavaRecommend>() {
            override fun areItemsTheSame(oldItem: JavaRecommend, newItem: JavaRecommend): Boolean {
                return oldItem.areItemsTheSame(newItem)
            }

            override fun areContentsTheSame(oldItem: JavaRecommend, newItem: JavaRecommend): Boolean {
                return oldItem.areContentsTheSame(newItem)
            }
        }
    }

    fun setRecyclerView(recyclerView: RecyclerView) {
        this.recyclerView = recyclerView
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(viewType, parent, false)

        return when (viewType) {
            R.layout.item_banner -> {
                "${view}".logd("onCreateViewHolder")
                BannerViewHolder(view)
            }
            R.layout.item_tmodel -> TitleViewHolder(view)
            R.layout.item_page_turning -> {
                "zhixin".logd("KLGUgkjbsjhgvs")
                PageTurningViewHolder(view)
            }
            R.layout.item_course -> CourseViewHolder(view)
            else -> CourseViewHolder(view)
        }
    }

    override fun getItemViewType(position: Int): Int {
        val data = getItem(position)
        return data?.let {
            when (data) {
                is BannerModel -> R.layout.item_banner
                is TuiJian -> R.layout.item_page_turning
                else -> R.layout.item_course
            }
        } ?: 0
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val data = getItem(position)
        when (holder) {
            is BannerViewHolder -> {
                "${holder}".logd("BannerViewHolder")
                // val size = holder.banner.views?.size
                //f (size!=null) return

                data as BannerModel
                val viewList = arrayListOf<View>()
                val modelList = mutableListOf<JavaCourseModel.RecommendModel>()
                val colorResList = listOf(R.color.test_color1, R.color.test_color2, R.color.test_color3)
                for ((i, d) in data.list.withIndex()) {
                    val view = LayoutInflater.from(context).inflate(R.layout.item_page, null, false)
                    view.findViewById<ImageView>(R.id.image).setImageResource(colorResList[i % 2])
                    viewList.add(view)
                    modelList.add(d)
                }

                holder.banner.setData(viewList, modelList, null)

            }
            is PageTurningViewHolder -> {

                "${holder.itemView}".logd("KLGUgkjbsjhgvs")
                data as TuiJian
                if (tagViewHolder == null) tagViewHolder = holder
                val colorResList = listOf(R.color.test_color1, R.color.test_color2, R.color.test_color3)
                holder.pageLayout.removeAllViews()
                holder.pageLayout.pageIndicator = holder.indicator
                holder.title.text = data.title
                data.list.forEach {
                    val item = LayoutInflater.from(context).inflate(R.layout.item_course, holder.pageLayout, false)
                    item.findViewById<RoundAngleImageView>(R.id.iv_img).setImageResource(colorResList.random())
                    item.findViewById<TextView>(R.id.tv_course_name).text = it.name
                    item.findViewById<TextView>(R.id.tv_course_desc).text = it.desc
                    item.findViewById<TextView>(R.id.tv_course_price).text = "¥ ${it.price}"
                    holder.pageLayout.addView(item)
                }

                holder.pageLayout.configPageTurningConfig {
                    onSelectViewChange = { _, index ->
                        callback?.onItemClick(data.list[index], -1)
                    }
                }

                if (tagViewHolder != holder) {
                    holder.syncState(tagViewHolder!!)
                    tagViewHolder = holder
                }

            }
            is CourseViewHolder -> {
                data as Course

                holder.itemView.setOnClickListener {
                    callback?.onItemClick(data, position)
                }

                Glide.with(context).load(data.imgUrl).placeholder(R.color.gray).into(holder.img)
                holder.name.text = data.name
                holder.desc.text = data.desc
                holder.price.text = if (data.price == 0) "免费" else "¥${data.price}"
                holder.studyNumber.text = "${data.studyNumber} 人学习"
            }
        }


    }


    class BannerViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val banner: NestedBGABanner = itemView.findViewById(R.id.banner)
    }

    class PageTurningViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val title: TextView = itemView.findViewById(R.id.tv_title)
        val more: TextView = itemView.findViewById(R.id.tv_more)
        val pageLayout: PageTurningLayout = itemView.findViewById(R.id.page_layout)
        val indicator: PageIndicator = itemView.findViewById(R.id.page_indicator)

        fun syncState(holder: PageTurningViewHolder) {
            pageLayout.scrollToIndex(holder.pageLayout.getCurIndex(),holder.pageLayout.getChildWidth())
        }
    }

    class TitleViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tModel: TextView = itemView.findViewById(R.id.tv_tmodel)
    }

    class CourseViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val img: RoundAngleImageView = itemView.findViewById(R.id.iv_img)
        val name: TextView = itemView.findViewById(R.id.tv_course_name)
        val desc: TextView = itemView.findViewById(R.id.tv_course_desc)
        val price: TextView = itemView.findViewById(R.id.tv_course_price)
        val studyNumber: TextView = itemView.findViewById(R.id.tv_course_study_number)
    }
}