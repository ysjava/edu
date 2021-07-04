package com.sandgrains.edu.student.model


data class ResultResponse<T>(val status: String, val data: T)
data class LoginFormState(
        var userPhoneError: Int? = null,
        var passwordError: Int? = null,
        var isDataValid: Boolean = false
)

data class Course(
        val name: String,
        val id: String,
        val imgUrl: String,
        val type: Int,
        val tryWatchUrl: String,
        val desc: String,
        val chapterList: List<Chapter>,
        val studyNumber: Int = 999,
        val price: Int = 0
)

data class Chapter(val name: String, val sectionList: List<Section>)

data class Section(val name: String, val videoUri: String)
////当oldName存在时，就表示name是oldName修改后的名字
//data class TypeEntity(var name: String, var oldName: String? = null)
//
//data class ShopEntity(
//    @SerializedName("shop_id") val sId: Int,
//    val name: String,
//    val icon: String,
//    val image: String,
//    val goodsTypes: String
//)
//
//data class GoodsEntity(
//    @SerializedName("goods_id") var gId: Int,
//    val type: String,
//    val name: String,
//    val price: String,
//    val icon: String,
//    val desc: String,
//    val recommend: Boolean,
//    val specList: List<SpecEntity>,
//    val state: Boolean,
//    val stockNum: Int?
//) {
//
//    fun build(): Goods2 {
//        val g = Goods2(type, name, price, icon, desc, recommend, 1, state, stockNum)
//        g.gId = gId
//        return g
//    }
//}
//
//data class SpecEntity(
//    @SerializedName("spec_id") val sId: Int,
//    val title: String,
//    val name: String,
//    val price: Double,
//    val stock: Int?
//) {
//    fun build(gId: Int): Spec {
//        val s = Spec(title, name, price, gId, stock)
//        s.sId = sId
//        return s
//    }
//}
//
//data class MerchantsEntity(
//    @SerializedName("m_id") val mId: Int,
//    val title: String,
//    val name: String,
//    val price: Double,
//    val stock: Int?
//)

