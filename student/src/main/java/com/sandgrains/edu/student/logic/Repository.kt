package com.sandgrains.edu.student.logic

import androidx.lifecycle.liveData
import com.sandgrains.edu.student.logic.network.EduNetwork
import com.sandgrains.edu.student.model.Question
import com.sandgrains.edu.student.model.Student
import com.sandgrains.edu.student.model.account.PawLoginModel

import kotlinx.coroutines.Dispatchers
import kotlin.coroutines.CoroutineContext

object Repository {

    private fun <T> fire(context: CoroutineContext, block: suspend () -> Result<T>) =
        liveData(context) {
            val result = try {
                block()
            } catch (e: Exception) {
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
        val response = EduNetwork.getJavaCourseData()

        if (response.status == "ok") {
            val rspModel = response.data
            Result.success(rspModel)
        } else {
            Result.failure(RuntimeException("response status is ${response.status}"))
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
        val response = EduNetwork.getCourseById(courseId)

        if (response.status == "ok") {
            val rspModel = response.data
            Result.success(rspModel)
        } else {
            Result.failure(RuntimeException("response status is ${response.status}"))
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
val picList = arrayOf("https://italker-im-new.oss-cn-hongkong.aliyuncs.com/%E6%97%A5%E6%9C%AC%E8%B1%86%E8%85%90%E7%85%B2.jpg",
"https://italker-im-new.oss-cn-hongkong.aliyuncs.com/%E9%9D%92%E6%A4%92%E8%82%89%E4%B8%9D.jpg",
"https://italker-im-new.oss-cn-hongkong.aliyuncs.com/Hydrangeas.jpg",
"https://italker-im-new.oss-cn-hongkong.aliyuncs.com/%E9%BA%BB%E5%A9%86%E8%B1%86%E8%85%90.png",
"https://italker-im-new.oss-cn-hongkong.aliyuncs.com/huadian/baiju.jpeg",
"https://italker-im-new.oss-cn-hongkong.aliyuncs.com/huadian/bankaimeigui.jpg",
"https://italker-im-new.oss-cn-hongkong.aliyuncs.com/huadian/dinxianghua.jpg",
"https://italker-im-new.oss-cn-hongkong.aliyuncs.com/huadian/jidanhua.jpg",
"https://italker-im-new.oss-cn-hongkong.aliyuncs.com/huadian/juhua.jpg",
"https://italker-im-new.oss-cn-hongkong.aliyuncs.com/huadian/lanju.jpg")
        var p: Int
        val answerList = mutableListOf<Question.Answer>()
        for (i in 0..2) {
            p = (picList.indices).random()
            val answer = Question.Answer(
                "1",
                "1",
                "我是回答的内容，我觉我可以来个图片 <img src='${picList[p]}",
                "2021-07-13  10:08.54",
                Student("回答者$i 号", "111", "111")
            )
            answerList.add(answer)
        }

        val question = Question(
            "1", "1", "1", Student("欧阳娜娜", "111", "111"), "这是问题的title",
            "这个是问题的content,我还带图片的<img src='https://img-blog.csdn.net/20140707162657281'/>  再来一张图片 <img src='https://img1.baidu.com/it/u=2192265457,2884791613&fm=26&fmt=auto&gp=0.jpg'/>",
            null, "2021-07-13  10:08.54", answerList
        )

        if (true) {//response.status == "ok"
//            val rspModel = response.data
            Result.success(question)
        } else {
            Result.failure(RuntimeException("response status is god"))//"response status is ${response.status}"
        }
    }


}