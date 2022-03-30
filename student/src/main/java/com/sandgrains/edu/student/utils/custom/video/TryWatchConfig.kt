package com.sandgrains.edu.student.utils.custom.video

import android.view.View

class TryWatchConfig {
    var onSelectViewChange: (fromView: View, targetView: View, index: Int) -> Unit =
        { _, _, _ ->
        }

}