package com.sandgrains.edu.student.model.course

import com.sandgrains.edu.student.model.Course

data class JavaCourseModel(
    val recommendCourseList: List<RecommendModel>,
    val courseList: List<Course>
) {
    data class RecommendModel(val cId: String, val url: String)
}
