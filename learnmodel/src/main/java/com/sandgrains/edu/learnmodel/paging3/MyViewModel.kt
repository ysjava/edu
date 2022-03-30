package com.sandgrains.edu.learnmodel.paging3

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.sandgrains.edu.learnmodel.paging3.api.GitHubService
import com.sandgrains.edu.learnmodel.paging3.model.Repo
import kotlinx.coroutines.flow.Flow

class MyViewModel():ViewModel() {

    fun getData(): Flow<PagingData<Repo>> {
        return getCoursesByCustom()
            .cachedIn(viewModelScope)
    }

    private val gitHubService = GitHubService.create()
    private fun getCoursesByCustom(): Flow<PagingData<Repo>> {
        return Pager(
            config = PagingConfig(30),
            pagingSourceFactory = { MyPagingDataSource(gitHubService) }
        ).flow
    }
}