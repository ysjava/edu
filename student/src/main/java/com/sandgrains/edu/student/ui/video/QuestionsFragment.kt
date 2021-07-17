package com.sandgrains.edu.student.ui.video

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.hi.dhl.binding.viewbind
import com.sandgrains.edu.student.R
import com.sandgrains.edu.student.databinding.FragmentQuestionsBinding
import com.sandgrains.edu.student.model.Question
import com.sandgrains.edu.student.model.Student

class QuestionsFragment : Fragment(R.layout.fragment_questions) {
    private val viewModel by lazy { ViewModelProvider(activity!!).get(PlayViewModel::class.java) }
    private val binding: FragmentQuestionsBinding by viewbind()
    private var courseId: String? = null
    private lateinit var adapter: Adapter
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        initObserve()
        courseId = arguments?.getString("course_id")
        val recyclerView = binding.recyclerView
        adapter = Adapter()
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(context)

        initData()
    }

    private fun initData() {
        courseId?.let {
            viewModel.getTotalNumberOfQuestionsByCourseId(it)
        }
    }

    private fun initObserve() {
        viewModel.getQuestionsBySectionId.observe(viewLifecycleOwner, { result ->
//            val data = result.getOrNull()
//            if (data != null) {
//                adapter.updateDataList(data)
//            }
            val dataList = mutableListOf<Question>()
            for (i in 0..5) {
                dataList.add(
                    Question(
                        "$i", "1", "1",
                        Student("我是学生$i", "1234", "111"),
                        "提出的第$i 个问题",
                        "这个问题则这么说呢也就是那样，对于想要得就去做相应的事就好了吧！", null, "2021-7-11 20:03", null
                    )
                )
            }
            adapter.updateDataList(dataList)
        })

        viewModel.getTotalNumberOfQuestionsByCourseId.observe(viewLifecycleOwner, { result ->
//            val data = result.getOrNull()
//            if (data != null) {
//                val s = data.split("|")
//                binding.tvQuestions.text = "共有${s[0]} 个问题， 已解决${s[1]}个"
//            }
            binding.tvQuestions.text = "共有199个问题， 已解决196个"
        })
    }

    inner class Adapter(private val dataList: MutableList<Question> = mutableListOf()) :
        RecyclerView.Adapter<Adapter.ViewHolder>() {

        inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
            val title: TextView = itemView.findViewById(R.id.tv_title)
            val userName: TextView = itemView.findViewById(R.id.tv_user_name)
            val date: TextView = itemView.findViewById(R.id.tv_date)
            val from: TextView = itemView.findViewById(R.id.tv_from)
            val content: TextView = itemView.findViewById(R.id.tv_content)
        }

        fun updateDataList(dataList: List<Question>) {
            this.dataList.clear()
            this.dataList.addAll(dataList)
            notifyDataSetChanged()
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
            val view =
                LayoutInflater.from(parent.context).inflate(R.layout.item_question, parent, false)
            return ViewHolder(view)
        }

        override fun onBindViewHolder(holder: ViewHolder, position: Int) {
            val question = dataList[position]
            holder.apply {
                title.text = question.title
                userName.text = question.questioner.name
                date.text = question.createDate
                from.text = "源自： ${question.sectionId}"
                content.text = question.content

                itemView.setOnClickListener {
                    val intent = Intent(activity, QuestionDetailActivity::class.java)
                    intent.putExtra(QuestionDetailActivity.QUESTION_ID, question.id)
                    startActivity(intent)
                }
            }
        }

        override fun getItemCount(): Int {
            return dataList.size
        }
    }
}