package com.sandgrains.edu.learnmodel.paging3

import android.util.Log
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.sandgrains.edu.learnmodel.paging3.api.GitHubService
import com.sandgrains.edu.learnmodel.paging3.model.Repo
import java.lang.Exception

class MyPagingDataSource(private val gitHubService: GitHubService) : PagingSource<Int, Repo>() {
    override fun getRefreshKey(state: PagingState<Int, Repo>): Int? {
        return null
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Repo> {
        return try {
            val page = params.key ?: 1 // set page 1 as default
            val pageSize = params.loadSize
            val prevKey = if (page > 1) page - 1 else null
            Log.e("kBKADA", "$pageSize")
            //val repoResponse = gitHubService.searchRepos(page, pageSize)
            val repoItems = if (page==1){
                pageData(0).subList(0, 1)
            }else
                listOf()

            val nextKey = if (repoItems.isNotEmpty()) page + 1 else null



            LoadResult.Page(repoItems, null, nextKey)
        } catch (e: Exception) {
            e.printStackTrace()
            LoadResult.Error(e)
        }


        //页数
//        val page = params.key ?: 1
//        //每一页的数据
//        val pageSize = params.loadSize
//
//        val list = mutableListOf<Course>()
//
//        for (i in 0..20) {
//            list.add(Course(i, "我是名称为$i 的兄弟", "desc"))
//        }
//        Log.e("kbahsa",""+list.size)
//        val prevKey = if (page > 1) page - 1 else null
//        val nextKey = if (list.isNotEmpty()) page + 1 else null
//        return LoadResult.Page(list, prevKey, nextKey)
    }

    private fun pageData(page: Int): List<Repo> {

        val array: Array<List<Repo>> = arrayOf(
                listOf(Repo(1, "page1,1" + (1..100).random(), null, 10),
                        Repo(2, "page1,2", null, 10),
                        Repo(3, "page1,3", null, 10),
                        Repo(4, "page1,4", null, 10),
                        Repo(5, "page1,5", null, 10),
                        Repo(6, "page1,6", null, 10),
                        Repo(7, "page1,7", null, 10),
                        Repo(8, "page1,8", null, 10),
                        Repo(9, "page1,9", null, 10),
                        Repo(10, "page1,10", null, 10),
                        Repo(11, "page1,11", null, 10),
                        Repo(12, "page1,12", null, 10)
                ),
                listOf(Repo(13, "page2,1", null, 10),
                        Repo(14, "page2,2", null, 10),
                        Repo(15, "page2,3", null, 10),
                        Repo(16, "page2,4", null, 10),
                        Repo(17, "page2,5", null, 10),
                        Repo(18, "page2,6", null, 10),
                        Repo(19, "page2,7", null, 10),
                        Repo(20, "page2,8", null, 10),
                        Repo(21, "page2,9", null, 10),
                        Repo(22, "page2,10", null, 10),
                        Repo(23, "page2,11", null, 10),
                        Repo(24, "page2,12", null, 10)
                ),
                listOf(Repo(25, "page3,1", null, 10),
                        Repo(26, "page3,2", null, 10),
                        Repo(27, "page3,3", null, 10),
                        Repo(28, "page3,4", null, 10),
                        Repo(29, "page3,5", null, 10),
                        Repo(30, "page3,6", null, 10),
                        Repo(31, "page3,7", null, 10),
                        Repo(32, "page3,8", null, 10),
                        Repo(33, "page3,9", null, 10),
                        Repo(34, "page3,10", null, 10),
                        Repo(35, "page3,11", null, 10),
                        Repo(36, "page3,12", null, 10)
                ),
                listOf(Repo(37, "page4,1", null, 10),
                        Repo(38, "page4,2", null, 10),
                        Repo(39, "page4,3", null, 10),
                        Repo(40, "page4,4", null, 10),
                        Repo(41, "page4,5", null, 10),
                        Repo(42, "page4,6", null, 10),
                        Repo(43, "page4,7", null, 10),
                        Repo(44, "page4,8", null, 10),
                        Repo(45, "page4,9", null, 10),
                        Repo(46, "page4,10", null, 10),
                        Repo(47, "page4,11", null, 10),
                        Repo(48, "page4,12", null, 10)
                )
        )

        return array[0]
    }

}