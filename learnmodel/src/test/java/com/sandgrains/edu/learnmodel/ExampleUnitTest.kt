package com.sandgrains.edu.learnmodel

import org.junit.Test

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class ExampleUnitTest {
    @Test
    fun addition_isCorrect() {
        val arr = intArrayOf(1, 2, 3, 4)
        MyUtils.test(arr, 6)
//        println(arr)
        for (a in arr) {
            print("$a, ")
        }
    }

    fun test(a: IntArray, k: Int) {
        val copy = a.copyOf()
        for (i in a.indices) {
            val index = (i + k) % a.size
            a[index] = copy[i]
        }
    }

    fun test2(a: IntArray, k: Int) {
        val nk = k % a.size
        // temp:保存这次循环被替换的值。  temp2:保存上次循环被替换的值

        var right = nk % a.size
        var temp: Int
        var temp2 = a[0]

        val visited = BooleanArray(a.size)

        for (i in a.indices) {
            temp = a[right]
            a[right] = temp2

            if (visited[(right + nk) % a.size]) {
                temp2 = a[right + 1]
                visited[right] = true
                right = (right + nk + 1) % a.size
            } else {
                temp2 = temp
                visited[right] = true
                right = (right + nk) % a.size

            }

        }


    }

    fun rotate(nums: IntArray, k: Int) {
        var k = k
        val length = nums.size
        k %= length
        reverse(nums, 0, length - 1) //先反转全部的元素
        reverse(nums, 0, k - 1) //在反转前k个元素
        reverse(nums, k, length - 1) //接着反转剩余的
    }

    //把数组中从[start，end]之间的元素两两交换,也就是反转
    fun reverse(nums: IntArray, start: Int, end: Int) {
        var start = start
        var end = end
        while (start < end) {
            val temp = nums[start]
            nums[start++] = nums[end]
            nums[end--] = temp
        }
    }
}