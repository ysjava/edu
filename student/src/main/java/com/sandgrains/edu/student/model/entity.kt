package com.sandgrains.edu.student.model

import com.sandgrains.edu.student.ui.home.JavaRecommend
import com.sandgrains.edu.student.ui.home.java.IJavaCourseAdapter
import com.sandgrains.edu.student.utils.base.DiffUiDataCallback

//import androidx.room.Entity
//import androidx.room.PrimaryKey


data class ResultResponse<T>(
        val status: String,
        val data: T,
        val message: String = "",
        val code: Int = -1
)

data class LoginFormState(
        var userPhoneError: Int? = null,
        var passwordError: Int? = null,
        var isDataValid: Boolean = false
)

//@Entity
data class Course(
        val name: String,
        val id: String,
        val imgUrl: String,
        val type: Int,//1.java 2......
        val tryWatchVideoInfoList: List<TryWatchVideoInfo>?,
        val desc: String,
        val chapterList: List<Chapter>,
        val studyNumber: Int = 999,
        val price: Int = 0,
//        @PrimaryKey(autoGenerate = true)
        val cid: Int = 0,
) : IJavaCourseAdapter, JavaRecommend {
    override fun areItemsTheSame(newItem: JavaRecommend): Boolean {
        val ni = newItem is Course

        return ni && this.id == (newItem as Course).id
    }

    override fun areContentsTheSame(newItem: JavaRecommend): Boolean {
        return this == newItem
    }

    override fun isSame(old: IJavaCourseAdapter): Boolean {
        val ni = old is Course

        return this === old || (ni && this.id == (old as Course).id)
    }

    override fun isUiContentSame(old: IJavaCourseAdapter): Boolean {
        return this === old || this.equals(old)
    }

}

fun build(name: String, price: Int, id: String = "-1"): Course {
    return Course(name, id, "url", 1, null, "desc", listOf(), price = price)
}

data class Chapter(val name: String, val sectionList: List<Section>)

data class Section(val name: String, val videoUri: String, val id: String = "")
data class Student(val name: String, val phone: String, val pushId: String)
data class Question(
        val id: String, val courseId: String, val sectionId: String,
        val questioner: Student, val title: String, val content: String,
        val picList: List<String>?, val createDate: String, val answerList: List<Answer>?
) {
    data class Answer(
            val id: String,
            val questionId: String,
            val content: String,
            val createDate: String,
            val answerer: Student
    )
}

data class TryWatchVideoInfo(val title: String, val url: String)
data class BannerModel(val list: List<Banner>) : IJavaCourseAdapter {
    data class Banner(val id: Int, val url: String)

    override fun isSame(old: IJavaCourseAdapter): Boolean {
        return this === old || old is BannerModel
    }

    override fun isUiContentSame(old: IJavaCourseAdapter): Boolean {
        return this === old || (old as BannerModel).list == this.list
    }
}

data class TypeRecommendCourse(val title: String, val list: List<Course>) : IJavaCourseAdapter {
    override fun isSame(old: IJavaCourseAdapter?): Boolean {
        return this === old || old is TypeRecommendCourse
    }

    override fun isUiContentSame(old: IJavaCourseAdapter?): Boolean {
        return this === old || ((old as TypeRecommendCourse).list == this.list && old.title == this.title)
    }
}

