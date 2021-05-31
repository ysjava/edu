package com.sandgrains.edu.admin

import android.Manifest
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.permissionx.guolindev.PermissionX

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        //这段权限代码稍后放闪屏页中
        PermissionX.init(this)
            .permissions(Manifest.permission.CALL_PHONE, Manifest.permission.WRITE_EXTERNAL_STORAGE)
            .onExplainRequestReason { scope, deniedList ->
                scope.showRequestReasonDialog(deniedList, "你需要同意以下权限才能正常使用！", "确定", "取消")
            }
            .onForwardToSettings { scope, deniedList ->
                scope.showForwardToSettingsDialog(deniedList, "您需要在程序设置中手动开启权限！", "我明白了", "取消")
            }
            .explainReasonBeforeRequest()
            .request { allGranted, grantedList, deniedList ->
                if (allGranted) {
                    Toast.makeText(this, "授权完成", Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(this, "您拒绝了权限： $deniedList", Toast.LENGTH_SHORT).show()
                }
            }

    }
}