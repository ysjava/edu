package com.sandgrains.edu.student.ui.video

import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestListener
import com.hi.dhl.binding.viewbind
import com.nostra13.universalimageloader.core.ImageLoader
import com.nostra13.universalimageloader.core.listener.SimpleImageLoadingListener
import com.sandgrains.edu.student.R
import com.sandgrains.edu.student.databinding.ActivityQuesionDetailBinding
import com.sandgrains.edu.student.model.Question
import com.sandgrains.edu.student.utils.custom.HtmlTextView
import com.sandgrains.edu.student.utils.initWindow
import de.hdodenhof.circleimageview.CircleImageView

class QuestionDetailActivity : AppCompatActivity(R.layout.activity_quesion_detail) {
    private val viewModel by lazy { ViewModelProvider(this).get(QuestionDetailViewModel::class.java) }
    private val binding: ActivityQuesionDetailBinding by viewbind()
    private lateinit var adapter: Adapter

    companion object {
        const val QUESTION_ID = "question_id"
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val id = intent.getStringExtra(QUESTION_ID)
        initWindow(window)
        initWidget()
        initData(id)
        initObserve()
    }

    private fun initWidget() {
        binding.recyclerView.layoutManager = LinearLayoutManager(this)
        adapter = Adapter()
        binding.recyclerView.adapter = adapter
        binding.ivBack.setOnClickListener {
            finish()
        }
    }

    private fun initObserve() {
        viewModel.getQuestionById.observe(this, { result ->
            val question = result.getOrNull()
            if (question != null) {
                binding.apply {
                    tvTitle.text = question.title
                    ivPortrait.setImageResource(randomColor())
                    tvPicName.text = question.questioner.name.substring(0, 1)
                    tvStuName.text = question.questioner.name
                    tvDate.text = question.createDate
                    tvContent.setHtmlFromString(question.content)
                    "${question.answerList?.size ?: 0} 回答".also { tvAnswerNumber.text = it }
                    question.answerList?.let {
                        adapter.updateDataList(it)
                    }
                    tvToolbarTitle.text = question.title
                }
            }
        })
    }

    private fun initData(id: String?) {
        id?.let { viewModel.getQuestionById(id) }
    }

    class Adapter(private var dataList: MutableList<Question.Answer> = mutableListOf()) :
        RecyclerView.Adapter<Adapter.ViewHolder>() {
        fun updateDataList(dataList: List<Question.Answer>) {
            this.dataList.clear()
            this.dataList.addAll(dataList)

            notifyDataSetChanged()
        }

        class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
            val portrait: CircleImageView = itemView.findViewById(R.id.iv_answer_portrait)
            val name: TextView = itemView.findViewById(R.id.tv_answer_name)
            val picName: TextView = itemView.findViewById(R.id.tv_pic_name)
            val date: TextView = itemView.findViewById(R.id.tv_date)
            val content: HtmlTextView = itemView.findViewById(R.id.tv_content)
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
            val view =
                LayoutInflater.from(parent.context).inflate(R.layout.item_answer, parent, false)

            return ViewHolder(view)
        }

        override fun onBindViewHolder(holder: ViewHolder, position: Int) {
            val answer = dataList[position]
            holder.apply {
                portrait.setImageResource(randomColor())
                name.text = answer.answerer.name
                picName.text = answer.answerer.name.substring(0, 1)
                date.text = answer.createDate
                content.setHtmlFromString(answer.content)

            }
        }

        override fun getItemCount(): Int {
            return dataList.size
        }
    }
}

private fun randomColor(): Int {
    val colors = arrayOf(
            R.color.black,
            R.color.gray,
            R.color.red,
            R.color.test_color1,
            R.color.test_color2,
            R.color.test_color3,
            R.color.teal_200
    )
    val p = (colors.indices).random()
    return colors[p]
}