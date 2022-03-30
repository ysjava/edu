package com.sandgrains.edu.student.logic.network


import com.sandgrains.edu.student.model.BannerModel
import com.sandgrains.edu.student.model.Course
import com.sandgrains.edu.student.model.TypeRecommendCourse
import com.sandgrains.edu.student.model.account.PawLoginModel
import com.sandgrains.edu.student.model.course.JavaCourseModel
import com.sandgrains.edu.student.model.course.TuiJian
import com.sandgrains.edu.student.ui.home.JavaRecommend
import com.sandgrains.edu.student.ui.home.java.CourseJavaTestActivity
import com.sandgrains.edu.student.ui.home.java.IJavaCourseAdapter
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlin.coroutines.suspendCoroutine

object EduNetwork {

    private val accountService = ServiceCreator.create(AccountService::class.java)

    //密码登陆
    suspend fun loginByPaw(model: PawLoginModel) = accountService.loginByPaw(model).await()

    private suspend fun <T> Call<T>.await(): T {
        return suspendCoroutine { continuation ->
            enqueue(object : Callback<T> {
                override fun onResponse(call: Call<T>, response: Response<T>) {
                    val body = response.body()
                    if (body != null) continuation.resume(body)
                    else continuation.resumeWithException(RuntimeException("response body is null"))
                }

                override fun onFailure(call: Call<T>, t: Throwable) {
                    continuation.resumeWithException(t)
                }
            })
        }
    }

    suspend fun requestCode(phone: String) = accountService.requestCode(phone).await()

    suspend fun checkCourseIsPay(id: String) = accountService.checkCourseIsPay(id).await()
    suspend fun getCourseSortedList(sort: String) = accountService.getCourseSortedList(sort).await()
    suspend fun getCourseById(courseId: String) = accountService.getCourseById(courseId).await()
    suspend fun getQuestionsBySectionId(sectionId: String) =
            accountService.getQuestionsBySectionId(sectionId).await()

    suspend fun getTotalNumberOfQuestionsByCourseId(courseId: String) =
            accountService.getTotalNumberOfQuestionsByCourseId(courseId).await()

    suspend fun getQuestionById(id: String) = accountService.getQuestionById(id).await()
    suspend fun getFindData() = accountService.getFindData()


    suspend fun getCourses(page: Int, pageSize: Int): List<IJavaCourseAdapter> {
        return pageData(page - 1)
        //accountService.getCourses(page, pageSize)
    }

    suspend fun getCourses3(page: Int, pageSize: Int): List<CourseJavaTestActivity.Course> {
        return listOf(
                CourseJavaTestActivity.Course("name1"+(1..100).random(),"1","",1,"")
        )
        //accountService.getCourses(page, pageSize)
    }

    suspend fun getCourses2(page: Int, pageSize: Int): List<JavaRecommend> {
        return pageData(page - 1)
    }

    //suspend fun getCourses2(page: Int, pageSize: Int) = accountService.getCourses(page, pageSize)


    suspend fun getBannerData(): IJavaCourseAdapter {
        val b1 = BannerModel.Banner(1, "u1")
        val b2 = BannerModel.Banner(2, "u2")
        val b3 = BannerModel.Banner(3, "u3")

        val bm = BannerModel(listOf(b1, b2, b3))
        return bm
        //accountService.getBannerData()
    }

    suspend fun getBannerData2(): JavaRecommend {
        val b = com.sandgrains.edu.student.model.course.BannerModel(listOf(JavaCourseModel.RecommendModel("1", "1"),
                JavaCourseModel.RecommendModel("2", "2"+(1..100).random()),
                JavaCourseModel.RecommendModel("3", "3")))
        return b
    }

    suspend fun getTypeRecomData(): List<IJavaCourseAdapter> {

        val list1 = listOf(
                com.sandgrains.edu.student.model.build("基于Vue3最新标准，实现后台前端综合解决方案", 368+(1..88).random(), "1"),
                com.sandgrains.edu.student.model.build("SpringBoot 2.x 实战仿B战高性能后端项目", 368, "2"),
                com.sandgrains.edu.student.model.build("C/C++气象数据中心实战，手把手教你做工业级项目", 368, "3"),
                com.sandgrains.edu.student.model.build("SpringBoot 2.x 实战仿B战高性能后端项目", 368, "4"),
                com.sandgrains.edu.student.model.build("基于Vue3最新标准，实现后台前端综合解决方案", 368, "5"),
                com.sandgrains.edu.student.model.build("SpringBoot 2.x 实战仿B战高性能后端项目", 368, "6"),
                com.sandgrains.edu.student.model.build("SpringBoot 2.x 实战仿B战高性能后端项目", 368, "7"),
                com.sandgrains.edu.student.model.build("C/C++气象数据中心实战，手把手教你做工业级项目", 368, "8")
        )

        val list2 = listOf(
                com.sandgrains.edu.student.model.build("基于Vue3最新标准，实现后台前端综合解决方案", 368+(99..188).random(), "9"),
                com.sandgrains.edu.student.model.build("SpringBoot 2.x 实战仿B战高性能后端项目", 368, "10"),
                com.sandgrains.edu.student.model.build("C/C++气象数据中心实战，手把手教你做工业级项目", 368, "11"),
                com.sandgrains.edu.student.model.build("SpringBoot 2.x 实战仿B战高性能后端项目", 368, "12"),
                com.sandgrains.edu.student.model.build("基于Vue3最新标准，实现后台前端综合解决方案", 368, "13"),
                com.sandgrains.edu.student.model.build("SpringBoot 2.x 实战仿B战高性能后端项目", 368, "14"),
                com.sandgrains.edu.student.model.build("SpringBoot 2.x 实战仿B战高性能后端项目", 368, "15"),
                com.sandgrains.edu.student.model.build("C/C++气象数据中心实战，手把手教你做工业级项目", 368, "16")
        )

        val tp1 = TypeRecommendCourse("新上好课", list1)
        val tp2 = TypeRecommendCourse("精品体系课", list2)
        return listOf(tp1,tp2)
        //accountService.getTypeRecomData()
    }

    suspend fun getTypeRecomData2(): List<JavaRecommend> {

        val list = listOf(
                com.sandgrains.edu.student.model.build("基于Vue3最新标准，实现后台前端综合解决方案", 368+(1..88).random(), "1"),
                com.sandgrains.edu.student.model.build("SpringBoot 2.x 实战仿B战高性能后端项目", 368, "2"),
                com.sandgrains.edu.student.model.build("C/C++气象数据中心实战，手把手教你做工业级项目", 368, "3"),
                com.sandgrains.edu.student.model.build("SpringBoot 2.x 实战仿B战高性能后端项目", 368, "4"),
                com.sandgrains.edu.student.model.build("基于Vue3最新标准，实现后台前端综合解决方案", 368, "5"),
                com.sandgrains.edu.student.model.build("SpringBoot 2.x 实战仿B战高性能后端项目", 368, "6"),
                com.sandgrains.edu.student.model.build("SpringBoot 2.x 实战仿B战高性能后端项目", 368, "7"),
                com.sandgrains.edu.student.model.build("C/C++气象数据中心实战，手把手教你做工业级项目", 368, "8")
        )
        val list2 = listOf(
                com.sandgrains.edu.student.model.build("基于Vue3最新标准，实现后台前端综合解决方案", 368, "9"),
                com.sandgrains.edu.student.model.build("SpringBoot 2.x 实战仿B战高性能后端项目", 368, "10"),
                com.sandgrains.edu.student.model.build("C/C++气象数据中心实战，手把手教你做工业级项目", 368, "11"),
                com.sandgrains.edu.student.model.build("SpringBoot 2.x 实战仿B战高性能后端项目", 368, "12"),
                com.sandgrains.edu.student.model.build("基于Vue3最新标准，实现后台前端综合解决方案", 368, "13"),
                com.sandgrains.edu.student.model.build("SpringBoot 2.x 实战仿B战高性能后端项目", 368, "14"),
                com.sandgrains.edu.student.model.build("SpringBoot 2.x 实战仿B战高性能后端项目", 368, "15"),
                com.sandgrains.edu.student.model.build("C/C++气象数据中心实战，手把手教你做工业级项目", 368, "16")
        )

        val tj1 = TuiJian("新上好课", list)
        val tj2 = TuiJian("精品体系课", list2)
        return listOf(tj1)
    }

    fun pageData(page: Int): List<Course> {

        val array: Array<List<Course>> = arrayOf(
                listOf(
                        Course("name1"+ (1..10).random(), "id1", "url", 1, null, "desc", listOf(), cid = 1),
                        Course("name2", "id2", "url", 1, null, "desc", listOf(), cid = 2),
                        Course("name3", "id3" + (1..10).random(), "url", 1, null, "desc", listOf(), cid = 1),
                        Course("name4", "id4", "url", 1, null, "desc", listOf(), cid = 1),
                        Course("name5", "id5", "url", 1, null, "desc", listOf(), cid = 1),
                        Course("name6", "id6", "url", 1, null, "desc", listOf(), cid = 1),
                        Course("name7", "id7", "url", 1, null, "desc", listOf(), cid = 1),
                        Course("name8", "id8", "url", 1, null, "desc", listOf(), cid = 1),
                        Course("name9", "id9", "url", 1, null, "desc", listOf(), cid = 1),
                        Course("name10", "id10", "url", 1, null, "desc", listOf(), cid = 1),
                        Course("name11", "id11", "url", 1, null, "desc", listOf(), cid = 1),
                        Course("name12", "id12", "url", 1, null, "desc", listOf(), cid = 1),

                        ),
                listOf(
                        Course("name13", "id13", "url", 1, null, "desc", listOf(), cid = 1),
                        Course("name14", "id14", "url", 1, null, "desc", listOf(), cid = 2),
                        Course("name15", "id15", "url", 1, null, "desc", listOf(), cid = 1),
                        Course("name16", "id16", "url", 1, null, "desc", listOf(), cid = 1),
                        Course("name17", "id17", "url", 1, null, "desc", listOf(), cid = 1),
                        Course("name18", "id18", "url", 1, null, "desc", listOf(), cid = 1),
                        Course("name19", "id19", "url", 1, null, "desc", listOf(), cid = 1),
                        Course("name20", "id20", "url", 1, null, "desc", listOf(), cid = 1),
                        Course("name21", "id21", "url", 1, null, "desc", listOf(), cid = 1),
                        Course("name22", "id22", "url", 1, null, "desc", listOf(), cid = 1),
                        Course("name23", "id23", "url", 1, null, "desc", listOf(), cid = 1),
                        Course("name24", "id24", "url", 1, null, "desc", listOf(), cid = 1),

                        ),
                listOf(
                        Course("name25", "id25", "url", 1, null, "desc", listOf(), cid = 1),
                        Course("name26", "id26", "url", 1, null, "desc", listOf(), cid = 2),
                        Course("name27", "id27", "url", 1, null, "desc", listOf(), cid = 1),
                        Course("name28", "id28", "url", 1, null, "desc", listOf(), cid = 1),
                        Course("name29", "id29", "url", 1, null, "desc", listOf(), cid = 1),
                        Course("name30", "id30", "url", 1, null, "desc", listOf(), cid = 1),
                        Course("name31", "id31", "url", 1, null, "desc", listOf(), cid = 1),
                        Course("name32", "id32", "url", 1, null, "desc", listOf(), cid = 1),
                        Course("name33", "id33", "url", 1, null, "desc", listOf(), cid = 1),
                        Course("name34", "id34", "url", 1, null, "desc", listOf(), cid = 1),
                        Course("name35", "id35", "url", 1, null, "desc", listOf(), cid = 1),
                        Course("name36", "id36", "url", 1, null, "desc", listOf(), cid = 1),

                        ),
                listOf(
                        Course("name37", "id37", "url", 1, null, "desc", listOf(), cid = 1),
                        Course("name38", "id38", "url", 1, null, "desc", listOf(), cid = 2),
                        Course("name39", "id39", "url", 1, null, "desc", listOf(), cid = 1),
                        Course("name40", "id40", "url", 1, null, "desc", listOf(), cid = 1),
                        Course("name41", "id41", "url", 1, null, "desc", listOf(), cid = 1),
                        Course("name42", "id42", "url", 1, null, "desc", listOf(), cid = 1),
                        Course("name43", "id43", "url", 1, null, "desc", listOf(), cid = 1),
                        Course("name44", "id44", "url", 1, null, "desc", listOf(), cid = 1),
                        Course("name45", "id45", "url", 1, null, "desc", listOf(), cid = 1),
                        Course("name46", "id46", "url", 1, null, "desc", listOf(), cid = 1),
                        Course("name47", "id47", "url", 1, null, "desc", listOf(), cid = 1),
                        Course("name48", "id48", "url", 1, null, "desc", listOf(), cid = 1),
                )
        )

        return array[page]
    }

}