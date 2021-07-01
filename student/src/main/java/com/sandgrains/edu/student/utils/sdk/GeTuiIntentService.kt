package com.sandgrains.edu.student.utils.sdk

import android.content.Context
import android.util.Log
import com.igexin.sdk.GTIntentService
import com.igexin.sdk.message.GTCmdMessage
import com.igexin.sdk.message.GTNotificationMessage
import com.igexin.sdk.message.GTTransmitMessage
import com.sandgrains.edu.student.persistence.setPushId

class GeTuiIntentService : GTIntentService() {
    override fun onReceiveServicePid(Context: Context?, pid: Int) {
    }

    // 接收 cid
    override fun onReceiveClientId(context: Context?, clientid: String?) {
        Log.e(TAG, "onReceiveClientId -> clientid = $clientid")
        clientid?.let {
            setPushId(clientid)
        }
    }

    // 处理透传消息
    override fun onReceiveMessageData(Context: Context?, msg: GTTransmitMessage?) {

    }

    // cid 离线上线通知
    override fun onReceiveOnlineState(Context: Context?, online: Boolean) {}

    // 各种事件处理回执
    override fun onReceiveCommandResult(Context: Context?, cmdMessage: GTCmdMessage?) {}

    // 通知到达，只有个推通道下发的通知会回调此方法
    override fun onNotificationMessageArrived(Context: Context?, msg: GTNotificationMessage?) {}

    // 通知点击，只有个推通道下发的通知会回调此方法
    override fun onNotificationMessageClicked(Context: Context?, msg: GTNotificationMessage?) {}
}