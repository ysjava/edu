package com.sandgrains.edu.student.utils.base


sealed class NetRequestException:Exception()
class NetWorkError(val msg: String = "网络错误！") : NetRequestException()
class ServiceError(val code: Int, val msg: String) : NetRequestException()