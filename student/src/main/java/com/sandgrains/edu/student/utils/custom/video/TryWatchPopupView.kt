package com.sandgrains.edu.student.utils.custom.video

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.LinearLayout
import android.widget.TextView
import com.lxj.xpopup.core.CenterPopupView
import com.lxj.xpopup.impl.FullScreenPopupView
import com.sandgrains.edu.student.R
import com.sandgrains.edu.student.model.TryWatchVideoInfo
import com.sandgrains.edu.student.utils.custom.RoundAngleImageView

class TryWatchPopupView(context: Context, private val dataList: ArrayList<TryWatchVideoInfo>,val dismiss:()->Unit): FullScreenPopupView(context){
    override fun getImplLayoutId(): Int {
        return R.layout.popup_try_watch
    }

    override fun onCreate() {
        super.onCreate()
        val group = findViewById<TryWatchViewGroup>(R.id.try_watch_view_group)
        val player = findViewById<VideoPlayer>(R.id.video_player)
        val lay = findViewById<LinearLayout>(R.id.lay_popup)
        lay.setOnClickListener {
            player.onVideoPause()
            dismiss()
        }

        for (i in 0 until dataList.size){
            val child = LayoutInflater.from(context).inflate(R.layout.cell_try_watch,null,false)
            val title = child.findViewById<TextView>(R.id.tv_title)
            title.text = dataList[i].title
            group.addView(child)
        }

        player.play(dataList[0].url)
        group.configTryWatchConfig {
            onSelectViewChange = { _,_,index ->
                player.play(dataList[index].url)
            }
        }
    }

}