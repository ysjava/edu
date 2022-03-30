package com.sandgrains.edu.student.ui.find


import android.graphics.Color
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.os.Message
import android.view.View
import android.widget.BaseAdapter
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.hi.dhl.binding.viewbind
import com.sandgrains.edu.student.R
import com.sandgrains.edu.student.databinding.FragmentFindBinding
import com.sandgrains.edu.student.utils.base.BaseRecyclerAdapter
import com.sandgrains.edu.student.utils.base.NetWorkError
import com.sandgrains.edu.student.utils.base.ServiceError
import com.sandgrains.edu.student.utils.custom.RoundAngleImageView
import com.sandgrains.edu.student.utils.loge
import java.time.LocalDateTime
import kotlin.concurrent.thread
import kotlin.random.Random

class FindFragment : Fragment(R.layout.fragment_find) {
    val binding: FragmentFindBinding by viewbind()

    val viewModel by lazy { ViewModelProvider(this).get(FindViewModel::class.java) }
    lateinit var adapter: Adapter
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        adapter = Adapter()
        binding.recyclerView.layoutManager = LinearLayoutManager(context)
        binding.recyclerView.adapter = adapter

        loadData()
        initObserve()

    }

    private fun initObserve() {
//        viewModel.loadData.observe(viewLifecycleOwner, { result ->
//            val dataModel = result.getOrNull()
//            if (dataModel != null) {
//                adapter.updateDataList(dataModel)
//            }
//        })
        //请求异常处理
        viewModel.requestException.observe(viewLifecycleOwner){
            when(it){
                is NetWorkError -> {
                    //网络错误
                }
                is ServiceError -> {
                    //服务器错误
                    "服务器错误! 错误码: ${it.code} \n msg: ${it.msg}".loge()
                }
            }
        }
    }

    private fun loadData() {
        viewModel.loadData().observe(viewLifecycleOwner){ result ->
            adapter.updateDataList(result)
        }
    }

    class Adapter : BaseRecyclerAdapter<DataModel>() {
        override fun getItemLayoutId(viewType: Int): Int {
            return viewType
        }

        override fun bindViewHolder(holder: RecyclerView.ViewHolder, data: DataModel,position: Int) {
            when (holder) {
                is LiveViewHolder -> {
                    holder.title.text = data.liveInfo?.title
                }
                is ADViewHolder -> {
//                    val colors = arrayOf(R.color.test_color1,R.color.test_color2,R.color.test_color3)
//                    holder.adImage.setImageResource(colors[(colors.indices).random()])
                }
                is PostViewHolder -> {
//                    holder.postTitle.text = data.post?.title
                    val picArray = arrayListOf(R.drawable.oynn, R.drawable.nn2)
                    val i = (0..1).random()
                    holder.postImage.setImageResource(picArray[i])
                }
            }
        }

        override fun createViewHolder(viewType: Int, itemView: View): RecyclerView.ViewHolder {
            return when (viewType) {
                R.layout.item_live -> LiveViewHolder(itemView)
                R.layout.item_find_ad -> ADViewHolder(itemView)
                R.layout.item_find_post -> PostViewHolder(itemView)
                else -> PostViewHolder(itemView)
            }
        }

        override fun getItemViewType(position: Int): Int {
            if (position == 0) return R.layout.item_live
            return if (position == 5 || position == 10) R.layout.item_find_ad else R.layout.item_find_post
        }


        class LiveViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
            val title: TextView = itemView.findViewById(R.id.tv_title)
        }

        class ADViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
            val adImage: RoundAngleImageView = itemView.findViewById(R.id.iv_ad_img)
        }

        class PostViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
            val postTitle: TextView = itemView.findViewById(R.id.tv_post_title)
            val postImage: ImageView = itemView.findViewById(R.id.iv_post_img)
        }

    }

}

data class Comment(
        val id: Int,
        val author: String,
        val content: String,
        val dateTime: LocalDateTime,
        val starts: Int
)

data class Post(
        val id: String,
        val title: String,
        val author: String,
        val content: String,
        val postTags: ArrayList<String>,
        val comments: ArrayList<Comment>,
        val starts: Int
)

data class LiveInfo(
        val id: String,
        val dateTime: LocalDateTime,
        val title: String,
        val desc: String,
        val author: String
)

data class DataModel(val type: Int, val post: Post?, val adUrl: String?, val liveInfo: LiveInfo?)