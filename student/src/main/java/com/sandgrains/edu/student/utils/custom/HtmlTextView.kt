package com.sandgrains.edu.student.utils.custom


import android.content.Context
import android.text.Html
import android.text.Html.ImageGetter
import android.text.method.LinkMovementMethod
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatTextView
import com.sandgrains.edu.student.ui.video.UrlImageGetter

class HtmlTextView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : AppCompatTextView(context, attrs, defStyleAttr) {

    /**
     * Parses String containing HTML to Android's Spannable format and displays
     * it in this TextView.
     *
     * @param html String containing HTML, for example: "**Hello world!**"
     */
    fun setHtmlFromString(html: String?) {
        val imgGetter: ImageGetter
        imgGetter = UrlImageGetter(this, context)


        // this uses Android's Html class for basic parsing, and HtmlTagHandler
        text = Html.fromHtml(html, imgGetter, null)

        // make links work
        movementMethod = LinkMovementMethod.getInstance()
//        setTextColor(resources.getColor(R.color.secondary_text_dark_nodisable, null))
    }
}