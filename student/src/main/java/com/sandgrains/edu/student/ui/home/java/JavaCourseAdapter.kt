package com.sandgrains.edu.student.ui.home.java

import android.view.LayoutInflater
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.sandgrains.edu.student.R
import com.sandgrains.edu.student.model.BannerModel
import com.sandgrains.edu.student.model.Course
import com.sandgrains.edu.student.model.TypeRecommendCourse
import com.sandgrains.edu.student.utils.base.BaseRecyclerAdapter
import com.sandgrains.edu.student.utils.base.DiffUiDataCallback
import com.sandgrains.edu.student.utils.base.ItemClickCallback
import com.sandgrains.edu.student.utils.custom.NestedBGABanner
import com.sandgrains.edu.student.utils.custom.PageIndicator
import com.sandgrains.edu.student.utils.custom.PageTurningLayout
import com.sandgrains.edu.student.utils.custom.RoundAngleImageView
import com.sandgrains.edu.student.utils.logd
import java.util.*

interface IJavaCourseAdapter : DiffUiDataCallback.UiDataDiffer<IJavaCourseAdapter>
class JavaCourseAdapter(
    private val context: Fragment,
    private val callback: ItemClickCallback<IJavaCourseAdapter>? = null
) : BaseRecyclerAdapter<IJavaCourseAdapter>() {
    private var tagViewHolder: PageTurningViewHolder? = null
    override fun getItemLayoutId(viewType: Int): Int {
        return viewType
    }

    val map = hashMapOf<Int, PageTurningViewHolder?>()
    override fun bindViewHolder(
        holder: RecyclerView.ViewHolder,
        data: IJavaCourseAdapter,
        position: Int
    ) {
        when (holder) {
            is BannerViewHolder -> {
                data as BannerModel
                val list = data.list
                val viewList = arrayListOf<View>()
                val modelList = mutableListOf<BannerModel.Banner>()
                val colorResList =
                    listOf(R.color.test_color1, R.color.test_color2, R.color.test_color3)
                for ((i, d) in list.withIndex()) {
                    val view = LayoutInflater.from(context.context)
                        .inflate(R.layout.item_page, null, false)
                    view.findViewById<ImageView>(R.id.image).setImageResource(colorResList[i % 2])
                    viewList.add(view)
                    modelList.add(d)
                }

                holder.banner.setData(viewList, modelList, null)
            }
            is PageTurningViewHolder -> {
                data as TypeRecommendCourse
                var vh = map[position]
                if (vh == null) {
                    vh = holder
                   map[position] = vh
                }
//                syncViewHolderState(tagViewHolder,holder)
                val list = data.list
                val colorResList =
                    listOf(R.color.test_color1, R.color.test_color2, R.color.test_color3)

                holder.pageLayout.pageIndicator = holder.indicator
                holder.pageLayout.removeAllViews()
                holder.title.text = data.title
                val viewList = mutableListOf<View>()
                list.forEach {
                    val item = LayoutInflater.from(context.context)
                        .inflate(R.layout.item_course, holder.pageLayout, false)
                    item.findViewById<RoundAngleImageView>(R.id.iv_img)
                        .setImageResource(colorResList.random())
                    item.findViewById<TextView>(R.id.tv_course_name).text = it.name
                    item.findViewById<TextView>(R.id.tv_course_desc).text = it.desc
                    item.findViewById<TextView>(R.id.tv_course_price).text = "¥ ${it.price}"
                    viewList.add(item)
                    //holder.pageLayout.addView(itew)
                }

                holder.pageLayout.addViews(viewList)


                holder.pageLayout.configPageTurningConfig {
                    onSelectViewChange = { _, index ->
                        callback?.onItemClick(data.list[index], -1)
                    }
                }

                if (vh != holder) {
                    holder.syncState(vh)
                    map[position] = holder
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

    override fun createViewHolder(viewType: Int, itemView: View): RecyclerView.ViewHolder {

        return when (viewType) {
            R.layout.item_banner -> BannerViewHolder(itemView)
            R.layout.item_page_turning -> PageTurningViewHolder(itemView)
            R.layout.item_course -> CourseViewHolder(itemView)
            else -> CourseViewHolder(itemView)
        }
    }

    override fun getItemViewType(position: Int, data: IJavaCourseAdapter): Int {
        return when (data) {
            is BannerModel -> {
                R.layout.item_banner
            }
            is TypeRecommendCourse -> {
                R.layout.item_page_turning
            }
            is Course -> {
                R.layout.item_course
            }
            else -> R.layout.item_course
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
            pageLayout.scrollToIndex(
                holder.pageLayout.getCurIndex(),
                holder.pageLayout.getChildWidth()
            )
        }
    }

    class TitleViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    }

    class CourseViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val img: RoundAngleImageView = itemView.findViewById(R.id.iv_img)
        val name: TextView = itemView.findViewById(R.id.tv_course_name)
        val desc: TextView = itemView.findViewById(R.id.tv_course_desc)
        val price: TextView = itemView.findViewById(R.id.tv_course_price)
        val studyNumber: TextView = itemView.findViewById(R.id.tv_course_study_number)
    }
}