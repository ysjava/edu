package com.sandgrains.edu.learnmodel.bitmap

import android.content.res.Resources
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.util.LruCache


class test {
}


fun decodeSampledBitmapFromResource(res: Resources, resId: Int, reqWidth: Int, reqHeight: Int): Bitmap {
    //1. 设置options.inJustDecodeBounds为true  解析图片原始宽高，并不会真正的去加载图片
    val options = BitmapFactory.Options()
    options.inJustDecodeBounds = true
    val bitmap = BitmapFactory.decodeResource(res, resId, options)

    //2. 计算采样率
    options.inSampleSize = calculateInSampleSize(options, reqWidth, reqHeight)
    //3.
    options.inJustDecodeBounds = false
    //4.重新加载
    return BitmapFactory.decodeResource(res, resId, options)
}

fun calculateInSampleSize(options: BitmapFactory.Options, reqWidth: Int, reqHeight: Int): Int {
    val width = options.outWidth
    val height = options.outHeight
    var inSampleSize = 1

    //bitmap实际宽高大于目标宽高
    if (width > reqWidth || height > reqHeight) {
        //缩减一半
        val halfWidth = width / 2
        val halfHeight = height / 2

        //计算最大的inSampleSize值，该值为2的幂次方，并同时保持这两个值
        //高度和宽度大于要求的高度和宽度
        while ((halfHeight / inSampleSize) >= reqHeight && (halfWidth / inSampleSize) >= reqWidth) {
            inSampleSize *= 2
        }
    }

    return inSampleSize
}


fun lurCache(bitmap: Bitmap) {
    //拿到应用最大内存   单位kb
    val maxMemory = (Runtime.getRuntime().maxMemory() / 1024).toInt()
    //只占用1/8
    val cacheSize = maxMemory / 8
    val memoryCache = object : LruCache<String, Bitmap>(cacheSize) {
        //计算缓存对象的大小
        override fun sizeOf(key: String, value: Bitmap): Int {
            return value.rowBytes * value.height / 1024 //kb
        }
    }


    //从LruCache获取缓存对象
    memoryCache.get("")
    //向LruCache添加缓存对象
    memoryCache.put("",bitmap)

}
