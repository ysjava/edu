package com.sandgrains.edu.student.ui.home

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.sandgrains.edu.student.logic.Repository
import com.sandgrains.edu.student.logic.network.EduNetwork
import com.sandgrains.edu.student.model.Course
import com.sandgrains.edu.student.model.ResultResponse
import com.sandgrains.edu.student.model.course.BannerModel
import com.sandgrains.edu.student.model.course.JavaCourseModel
import com.sandgrains.edu.student.model.course.TuiJian
import com.sandgrains.edu.student.ui.home.java.IJavaCourseAdapter
import com.sandgrains.edu.student.utils.base.NetRequestException
import com.sandgrains.edu.student.utils.base.NetWorkError
import com.sandgrains.edu.student.utils.base.ServiceError
import com.sandgrains.edu.student.utils.logd

interface JavaRecommend {
    fun areItemsTheSame(newItem: JavaRecommend): Boolean
    fun areContentsTheSame(newItem: JavaRecommend): Boolean
}

class JavaPagingSource(val catchError: (e: NetRequestException) -> Unit) :
    PagingSource<Int, JavaRecommend>() {
    override fun getRefreshKey(state: PagingState<Int, JavaRecommend>): Int? = null

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, JavaRecommend> {
        return try {

            //页数
            val page = params.key ?: 1
            //每一页的数据
            val pageSize = params.loadSize


            //进行网络请求数据   两次请求 第一次请求banner  然后是主要数据
//            val response = EduNetwork.getCourses(page, pageSize, type,sort)
            if (true) {//response.status == "ok"
//                val list = response.data
                val prevKey = if (page > 1) page - 1 else null  // 上一页，设置为空就没有上一页的效果
//                val nextKey = if (list.isNotEmpty()) page + 1 else nullx
                val list: List<JavaRecommend> = if (page == 1) {
                    val resultList = mutableListOf<JavaRecommend>()
                    val bannerModelCall = EduNetwork.getBannerData2()
                    val typeRecomListCall = EduNetwork.getTypeRecomData2()
                    val courseListCall = EduNetwork.getCourses2(1, 10)

                    resultList.add(bannerModelCall)

                    typeRecomListCall.forEach {
                        resultList.add(it)
                    }

                    courseListCall.forEach {
                        resultList.add(it)
                    }
                    resultList
                } else {
                    EduNetwork.getCourses2(page, 10)
                    listOf()
                }

                val nextKey = if (list.isNotEmpty()) page + 1 else null
                LoadResult.Page(list, prevKey, nextKey)
            } else {
                //服务端返回的自定义的错误信息
                LoadResult.Error(ServiceError(1, "1"))//response.code, response.message
            }
        } catch (e: Exception) {
            if (e is NetRequestException) catchError(e)
            LoadResult.Error(e)
        }
    }

    private fun <T> handleResponse(response: ResultResponse<T>): JavaPagingResult {
        return if (response.status == "ok") {
            Success(response.data)
        } else {
            Failure(ServiceError(response.code, response.message))
        }
    }


}

sealed class JavaPagingResult
class Success<T>(val data: T) : JavaPagingResult()
class Failure(e: NetRequestException) : JavaPagingResult()
