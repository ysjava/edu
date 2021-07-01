package com.sandgrains.edu.student

import android.Manifest
import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.animation.ArgbEvaluator
import android.animation.ObjectAnimator
import android.content.Intent
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.util.Property
import androidx.appcompat.app.AppCompatActivity
import com.hi.dhl.binding.viewbind
import com.permissionx.guolindev.PermissionX
import com.sandgrains.edu.student.databinding.ActivityLaunchBinding
import com.sandgrains.edu.student.persistence.getPushId
import com.sandgrains.edu.student.persistence.isBind
import com.sandgrains.edu.student.persistence.isLogin
import com.sandgrains.edu.student.ui.MainActivity
import com.sandgrains.edu.student.ui.account.LoginActivity

class LaunchActivity : AppCompatActivity() {
    lateinit var bgDrawable: ColorDrawable
    private val binding: ActivityLaunchBinding by viewbind()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_launch)
        val color = resources.getColor(R.color.teal_700, null)
        bgDrawable = ColorDrawable(color)
        //设置背景为ColorDrawable 便于后面动画更新
        binding.layLaunch.background = bgDrawable
        //开始一个动画，改变背景
        startAnim(0.5f, this::waitPushIdSetting)
    }

    private fun startAnim(endProgress: Float, callback: Runnable) {

        //最终的颜色
        val finalColor: Int = resources.getColor(R.color.white, null)
        val evaluator = ArgbEvaluator()
        val endColor = evaluator.evaluate(endProgress, bgDrawable.color, finalColor) as Int

        // 构建一个属性动画
        val valueAnimator = ObjectAnimator.ofObject(this, property, evaluator, endColor)
        //时长
        valueAnimator.duration = 1500
//        valueAnimator.setIntValues(bgDrawable.color, endColor)
        valueAnimator.addListener(object : AnimatorListenerAdapter() {
            override fun onAnimationEnd(animation: Animator?) {
                super.onAnimationEnd(animation)
                //动画结束是进行逻辑处理
                callback.run()
            }
        })

        valueAnimator.start()
    }

    //为LaunchActivity添加一个color属性，通过这个属性来操作bgColor
    private val property = object : Property<LaunchActivity, Any>(Any::class.java, "color") {
        override fun get(`object`: LaunchActivity): Any {
            return `object`.bgDrawable.color
        }

        override fun set(`object`: LaunchActivity, value: Any?) {
            value?.let {
                `object`.bgDrawable.color = it as Int
            }
        }

    }

    //等待个推的pushId
    private fun waitPushIdSetting() {
        if (isLogin()) {
            //已经登陆， 没bindPushId的话就继续等待绑定完成
            if (isBind()) {
                waitPushIdSettingDone()
                return
            }
        } else {
            if (getPushId().isNotEmpty()) {
                //没有登陆就不能进行绑定pushId，所以只要个推设置了id就行了，登陆后再进行绑定
                waitPushIdSettingDone()
                return
            }
        }

        //重复执行，等待个推设置pushId
        window.decorView.postDelayed(this::waitPushIdSetting, 1000)
    }

    //个推的pushId设置完成
    private fun waitPushIdSettingDone() {
        startAnim(1.0f, this::skip)
    }

    //设置完成，开始跳转
    private fun skip() {
        //权限获取
        PermissionX.init(this)
                .permissions(Manifest.permission.CALL_PHONE, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                .onExplainRequestReason { scope, deniedList ->
                    scope.showRequestReasonDialog(deniedList, "你需要同意以下权限才能正常使用！", "确定", "取消")
                }
                .onForwardToSettings { scope, deniedList ->
                    scope.showForwardToSettingsDialog(deniedList, "您需要在程序设置中手动开启权限！", "我明白了", "取消")
                }
                .explainReasonBeforeRequest()
                .request { allGranted, _, _ ->
                    if (allGranted) {
                        if (isLogin()) {
                            startActivity(Intent(this@LaunchActivity, MainActivity::class.java))
                        } else {
                            startActivity(Intent(this@LaunchActivity, LoginActivity::class.java))
                        }
                    }
                    finish()
                }

    }
}