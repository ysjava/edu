package com.sandgrains.edu.student.ui.video

import com.sandgrains.edu.student.model.Section

interface SectionSelectedCallback {
    fun callback(section: Section)
}