package com.sandgrains.edu.student.model.course

import com.sandgrains.edu.student.model.Course
import com.sandgrains.edu.student.ui.home.JavaRecommend

data class JavaCourseModel(
        val recommendCourseList: List<RecommendModel>,
        val courseList: List<Courses>
) {
    data class RecommendModel(val cId: String, val url: String) : JavaRecommend {
        override fun areItemsTheSame(newItem: JavaRecommend): Boolean {
            return this.cId == (newItem as RecommendModel).cId
        }

        override fun areContentsTheSame(newItem: JavaRecommend): Boolean {
            return this.url == (newItem as RecommendModel).url
        }

    }

    data class Courses(val title: String, val list: List<Course>) : JavaRecommend {
        override fun areItemsTheSame(newItem: JavaRecommend): Boolean {
            return true
        }

        override fun areContentsTheSame(newItem: JavaRecommend): Boolean {
            return true
        }

    }
}

data class BannerModel(val list: List<JavaCourseModel.RecommendModel>) : JavaRecommend {
    override fun areItemsTheSame(newItem: JavaRecommend): Boolean {
        return newItem is BannerModel
    }

    override fun areContentsTheSame(newItem: JavaRecommend): Boolean {
        return (newItem as BannerModel).list == this.list
    }
}

data class TuiJian(val title: String, val list: List<Course>) : JavaRecommend {
    override fun areItemsTheSame(newItem: JavaRecommend): Boolean {
        return newItem is TuiJian
    }

    override fun areContentsTheSame(newItem: JavaRecommend): Boolean {
        val ni = (newItem as TuiJian)
        return ni.list == this.list && ni.title == this.title
    }

}