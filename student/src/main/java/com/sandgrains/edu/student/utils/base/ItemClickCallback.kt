package com.sandgrains.edu.student.utils.base

interface ItemClickCallback<Data> {
    fun onItemClick(data: Data,position: Int)
}