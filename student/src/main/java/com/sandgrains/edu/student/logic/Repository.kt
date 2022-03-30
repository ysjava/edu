package com.sandgrains.edu.student.logic

import android.util.Log
import androidx.lifecycle.liveData
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.sandgrains.edu.student.logic.network.EduNetwork
import com.sandgrains.edu.student.model.*
import com.sandgrains.edu.student.model.account.PawLoginModel
import com.sandgrains.edu.student.ui.find.DataModel
import com.sandgrains.edu.student.ui.find.LiveInfo
import com.sandgrains.edu.student.ui.find.Post
import com.sandgrains.edu.student.ui.home.JavaPagingSource
import com.sandgrains.edu.student.ui.home.java.CourseJavaTestActivity
import com.sandgrains.edu.student.ui.home.java.IJavaCourseAdapter
import com.sandgrains.edu.student.utils.base.NetRequestException
import com.sandgrains.edu.student.utils.base.ServiceError
import com.sandgrains.edu.student.utils.loge
import kotlinx.coroutines.*

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import retrofit2.await
import java.time.LocalDateTime
import kotlin.coroutines.CoroutineContext

object Repository {
    class CustomException(msg: String) : Exception(msg)

    private fun <T> fire(context: CoroutineContext, block: suspend () -> Result<T>) =
            liveData(context) {
                val result = try {
                    block()
                } catch (e: Exception) {
                    "fire $e".loge("TESGTASD")
                    Result.failure(e)
                }
                emit(result)
            }

    /**
     * 登陆或注册
     *
     * @param model 登陆model
     *
     * */
    fun loginByPaw(model: PawLoginModel) = fire(Dispatchers.IO) {
        val response = EduNetwork.loginByPaw(model)

        if (response.status == "ok") {
            val rspModel = response.data
            Result.success(rspModel)
        } else {
            Result.failure(RuntimeException("response status is ${response.status}"))
        }
    }

    fun requestCode(phone: String) = fire(Dispatchers.IO) {
        val response = EduNetwork.requestCode(phone)

        if (response.status == "ok") {
            val rspModel = response.data
            Result.success(rspModel)
        } else {
            Result.failure(RuntimeException("response status is ${response.status}"))
        }
    }

    fun getJavaCourseData() = fire(Dispatchers.IO) {
        //val response = EduNetwork.getJavaCourseData()
        val resultList = mutableListOf<IJavaCourseAdapter>()
        val bannerModelCall = EduNetwork.getBannerData()
        val typeRecomListCall = EduNetwork.getTypeRecomData()
        val courseListCall = EduNetwork.getCourses(1, 10)


        resultList.add(bannerModelCall)

        typeRecomListCall.forEach {
            resultList.add(it)
        }

        courseListCall.forEach {
            resultList.add(it)
        }

        if (true) {//response.status == "ok"
//            val rspModel = response.data
            Result.success(resultList)
        } else {
            Result.failure(RuntimeException("response status is {response.status}"))
        }
    }

    fun getJavaCourseData2() = fire(Dispatchers.IO) {
        //val response = EduNetwork.checkCourseIsPay("q")

        val courseListCall = EduNetwork.getCourses3(1, 10).subList(0,1)
        val resultList = mutableListOf<CourseJavaTestActivity.Course>()
        courseListCall.forEach {
            resultList.add(it)
        }

        if (true) {//response.status == "ok"
//            val rspModel = response.data
            Result.success(resultList)
        } else {
            Result.failure(ServiceError(404,"hahah response status is {response.status}"))
        }
    }

    fun checkCourseIsPay(id: String) = fire(Dispatchers.IO) {
//        val response = EduNetwork.checkCourseIsPay(id)

        if (true) {//response.status == "ok"
//            val rspModel = response.data
            Result.success(Pair(id, "UNPAID"))
        } else {
            Result.failure(RuntimeException("response status is "))//${response.status}
        }
    }

    fun getCourseSortedList(sort: String) = fire(Dispatchers.IO) {
        val response = EduNetwork.getCourseSortedList(sort)

        if (response.status == "ok") {
            val rspModel = response.data
            Result.success(rspModel)
        } else {
            Result.failure(RuntimeException("response status is ${response.status}"))
        }
    }

    fun getCourseById(courseId: String) = fire(Dispatchers.IO) {
//        val response = EduNetwork.getCourseById(courseId)
        if (true) {//response.status == "ok"
//            val rspModel = response.data

            val course = Course(
                    "杨老师的娜娜",
                    courseId,
                    "url",
                    1,
                    listOf(
                            TryWatchVideoInfo(
                                    "我是该课程的第一个试看",
                                    "http://9890.vod.myqcloud.com/9890_4e292f9a3dd011e6b4078980237cc3d3.f20.mp4"
                            ),
                            TryWatchVideoInfo(
                                    "我是该课程的第二个试看",
                                    "http://7xjmzj.com1.z0.glb.clouddn.com/20171026175005_JObCxCE2.mp4"
                            ),
                            TryWatchVideoInfo(
                                    "我是该课程的第三个试看",
                                    "https://res.exexm.com/cw_145225549855002"
                            )
                    ),
                    "desc",
                    listOf()
            )

            Result.success(course)
        } else {
            Result.failure(CustomException("response.message"))
//            Result.failure(MyException.ServiceError(response.code,response.msg))

        }
    }

    fun getQuestionsBySectionId(sectionId: String) = fire(Dispatchers.IO) {
        val response = EduNetwork.getQuestionsBySectionId(sectionId)

        if (response.status == "ok") {
            val rspModel = response.data
            Result.success(rspModel)
        } else {
            Result.failure(RuntimeException("response status is ${response.status}"))
        }
    }

    fun getTotalNumberOfQuestionsByCourseId(courseId: String) = fire(Dispatchers.IO) {
        val response = EduNetwork.getTotalNumberOfQuestionsByCourseId(courseId)

        if (response.status == "ok") {
            val rspModel = response.data
            Result.success(rspModel)
        } else {
            Result.failure(RuntimeException("response status is ${response.status}"))
        }
    }

    fun getQuestionById(id: String) = fire(Dispatchers.IO) {
//        val response = EduNetwork.getQuestionById(id)
        //接口暂未完成，自己构造一个假数据
        val picList = arrayOf(
                "https://italker-im-new.oss-cn-hongkong.aliyuncs.com/%E6%97%A5%E6%9C%AC%E8%B1%86%E8%85%90%E7%85%B2.jpg",
                "https://italker-im-new.oss-cn-hongkong.aliyuncs.com/%E9%9D%92%E6%A4%92%E8%82%89%E4%B8%9D.jpg",
                "https://italker-im-new.oss-cn-hongkong.aliyuncs.com/Hydrangeas.jpg",
                "https://italker-im-new.oss-cn-hongkong.aliyuncs.com/%E9%BA%BB%E5%A9%86%E8%B1%86%E8%85%90.png",
                "https://italker-im-new.oss-cn-hongkong.aliyuncs.com/huadian/baiju.jpeg",
                "https://italker-im-new.oss-cn-hongkong.aliyuncs.com/huadian/bankaimeigui.jpg",
                "https://italker-im-new.oss-cn-hongkong.aliyuncs.com/huadian/dinxianghua.jpg",
                "https://italker-im-new.oss-cn-hongkong.aliyuncs.com/huadian/jidanhua.jpg",
                "https://italker-im-new.oss-cn-hongkong.aliyuncs.com/huadian/juhua.jpg",
                "https://italker-im-new.oss-cn-hongkong.aliyuncs.com/huadian/lanju.jpg"
        )
        var p: Int
        val answerList = mutableListOf<Question.Answer>()
        for (i in 0..2) {
            p = (picList.indices).random()
            val answer = Question.Answer(
                    "1",
                    "1",
                    "我是回答的内容，我觉我可以来个图片 <img src='${picList[p]}'/>",
                    "2021-07-13  10:08.54",
                    Student("回答者$i 号", "111", "111")
            )
            answerList.add(answer)
        }

        val question = Question(
                "1", "1", "1", Student("欧阳娜娜", "111", "111"), "这是问题的title",
                "这个是问题的\ncontent,我还带图片的<img src='https://img-blog.csdn.net/20140707162657281'/>  再来一张图片 <img src='https://img1.baidu.com/it/u=2192265457,2884791613&fm=26&fmt=auto&gp=0.jpg'/>",
                null, "2021-07-13  10:08.54", answerList
        )

        if (true) {//response.status == "ok"
//            val rspModel = response.data
            Result.success(question)
        } else {
            Result.failure(RuntimeException("response status is god"))//"response status is ${response.status}"
        }
    }

    fun getFindData() = fire(Dispatchers.IO) {
        val list = mutableListOf(
                DataModel(
                        1, null, null,
                        LiveInfo("id", LocalDateTime.now(), "测试直播标题", "desc", "ysjava")
                )
        )
        for (i in 1..12) {
            val dataModel: DataModel = if (i == 5 || i == 10) {
                DataModel(
                        3, null, "url", null
                )
            } else {
                DataModel(
                        2,
                        Post("id", "第$i 个Post", "a", "a", arrayListOf(), arrayListOf(), 10),
                        null,
                        null
                )
            }
            list.add(dataModel)
        }
//        val response = EduNetwork.getFindData()
        if (true) {//response.status == "ok"
//            val rspModel = response.data
            Result.success(list)
        } else {
            Result.failure(RuntimeException("response status is god"))//"response status is ${response.status}"
        }
    }


    fun getFindData2() = flow {
        //val response = EduNetwork.getFindData()

        val list = mutableListOf(
                DataModel(
                        1, null, null,
                        LiveInfo("id", LocalDateTime.now(), "测试直播标题", "desc", "ysjava")
                )
        )
        for (i in 1..12) {
            val dataModel: DataModel = if (i == 5 || i == 10) {
                DataModel(
                        3, null, "url", null
                )
            } else {
                DataModel(
                        2,
                        Post("id", "第$i 个Post", "a", "a", arrayListOf(), arrayListOf(), 10),
                        null,
                        null
                )
            }
            list.add(dataModel)
        }

//        val result = response.run {
//            if (status == "ok")
//                Result.success(response.data)
//            else
//                Result.failure(ServiceError(code, message))
//        }

        emit(list)
    }.flowOn(Dispatchers.IO)


//    fun getCoursesByTypeAndSort(type: Int,sort: String): Flow<PagingData<Course>> {
//        return Pager(
//            config = PagingConfig(10),
//            pagingSourceFactory = { JavaPagingSource(type,sort) }
//        ).flow
//    }

    fun getCoursesByCustom(catchError: (e: NetRequestException) -> Unit): Flow<PagingData<com.sandgrains.edu.student.ui.home.JavaRecommend>> {
        return Pager(
                config = PagingConfig(1),
                pagingSourceFactory = { JavaPagingSource(catchError) }
        ).flow
    }


}