package com.sandgrains.edu.learnmodel.paging3.model

import com.google.gson.annotations.SerializedName
import com.sandgrains.edu.learnmodel.paging3.model.Repo

class RepoResponse(
    @SerializedName("items") val items: List<Repo> = emptyList()
)