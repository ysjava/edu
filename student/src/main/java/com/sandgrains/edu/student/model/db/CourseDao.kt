//package com.sandgrains.edu.student.model.db
//
//import androidx.room.Dao
//import androidx.room.Insert
//import androidx.room.Query
//import com.sandgrains.edu.student.model.Course
//
//@Dao
//interface CourseDao {
//
//    @Insert
//    fun insertCourse(course: Course)
//
//    @Query("select * from Course where type=:type")
//    fun queryCourseByType(type: Int): List<Course>
//}