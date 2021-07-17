package com.sandgrains.edu.student.ui.video

import android.content.res.Configuration
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.ViewModelProvider
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.angcyo.tablayout.delegate2.ViewPager2Delegate
import com.hi.dhl.binding.viewbind
import com.sandgrains.edu.student.R
import com.sandgrains.edu.student.databinding.ActivityPlayBinding
import com.sandgrains.edu.student.model.Chapter
import com.sandgrains.edu.student.model.Course
import com.sandgrains.edu.student.model.Section
import com.sandgrains.edu.student.utils.ViewPager2Util
import com.sandgrains.edu.student.utils.custom.video.VideoPlayer
import com.shuyu.gsyvideoplayer.GSYVideoManager
import com.shuyu.gsyvideoplayer.builder.GSYVideoOptionBuilder
import com.shuyu.gsyvideoplayer.listener.GSYSampleCallBack
import com.shuyu.gsyvideoplayer.utils.OrientationUtils

/**
 * 视频播放界面
 *
 * */
class PlayActivity : AppCompatActivity(R.layout.activity_play) {
    private val binding: ActivityPlayBinding by viewbind()
    private lateinit var courseId: String
    private val viewModel by lazy { ViewModelProvider(this).get(PlayViewModel::class.java) }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        courseId = intent.getStringExtra("course_id") ?: ""
        initData()
        initObserve()
        val viewpager = binding.viewpager
        viewpager.adapter = PagerAdapter(this)
        viewpager.offscreenPageLimit = 2
        ViewPager2Delegate.install(viewpager, binding.tabLayout)
        ViewPager2Util.changeToNeverMode(viewpager)

        binding.videoPlayer.setSectionSelectedCallback(sectionSelectedCallback)
        binding.videoPlayer.setFinishCallback(finishActivityCallback)
    }

    private val sectionSelectedCallback = object : SectionSelectedCallback {
        override fun callback(section: Section) {
            viewModel.updateSectionSelected(Pair(section, "PlayActivity"))
        }
    }

    private val finishActivityCallback = object : OnFinishActivity {
        override fun onFinish() {
            this@PlayActivity.finish()
        }

    }

    private fun initData() {
        viewModel.loadData(courseId)
    }

    private fun initObserve() {
        viewModel.loadData.observe(this, { result ->
//            val course = result.getOrNull()
//            if (course != null) {
//                initVideoPlayer(course)
//            }
            val videoUrlList = listOf(
                    "http://9890.vod.myqcloud.com/9890_4e292f9a3dd011e6b4078980237cc3d3.f20.mp4",
                    "http://7xjmzj.com1.z0.glb.clouddn.com/20171026175005_JObCxCE2.mp4",
                    "https://res.exexm.com/cw_145225549855002")

            //构建点假数据
            val chapterList = mutableListOf<Chapter>()
            for (i in 0..5) {
                val sections = mutableListOf<Section>()
                for (j in 0..5) {
                    val section = Section("我是第${i}章，第${j}小节", videoUrlList[j%3])
                    sections.add(section)
                }
                val chapter = Chapter("第${i}章", sections)
                chapterList.add(chapter)
            }
            initVideoPlayer(Course("自己构造的课程", "id", "imgUrl", 1, "免费播放链接", "desc", chapterList))
            binding.videoPlayer.setChapterList(chapterList)
        })

        viewModel.sectionSelected.observe(this, {
            //选中后进行播放视频
            val p =binding.videoPlayer.currentPlayer as VideoPlayer
            p.play(it.first.videoUri)
            //获取该小节下的问答列表
            viewModel.getQuestionsBySectionId(it.first.id)

            //如果是我这界面自己触发的选中就不用处理选中样式的设置了
            if (it.second == "PlayActivity") return@observe
            binding.videoPlayer.setSelectedSection(it.first.name)
        })
    }

    private var isPlay = false
    private var isPause = false
    private var orientationUtils: OrientationUtils? = null
    private fun initVideoPlayer(course: Course) {
        //第一章的第一个小节url
        val url = course.chapterList[0].sectionList[0].videoUri
        binding.videoPlayer.apply {
            //创建封面
            val imageView = ImageView(this@PlayActivity)
            imageView.scaleType = ImageView.ScaleType.CENTER_CROP
            imageView.setImageResource(R.mipmap.ic_launcher)

            //外部辅助的旋转，帮助全屏
            val orientationUtils = OrientationUtils(this@PlayActivity, this)
            this@PlayActivity.orientationUtils = orientationUtils
            //初始化不打开外部旋转
            orientationUtils.isEnable = false

            val optionBuilder = GSYVideoOptionBuilder()
            optionBuilder.setThumbImageView(imageView)
                    .setIsTouchWiget(true)
                    .setRotateViewAuto(false)
                    .setLockLand(false)
                    .setShowDragProgressTextOnSeekBar(true)
                    .setAutoFullWithSize(false)
                    .setShowFullAnimation(false)
                    .setNeedLockFull(true)
                    .setUrl(url)
                    .setDismissControlTime(3500)
                    .setCacheWithPlay(false)
                    .setVideoAllCallBack(object : GSYSampleCallBack() {
                        override fun onPrepared(url: String?, vararg objects: Any?) {
                            super.onPrepared(url, *objects)
                            //开始播放了才能旋转和全屏
                            orientationUtils.isEnable = true
                            isPlay = true
                        }

                        override fun onQuitFullscreen(url: String?, vararg objects: Any?) {
                            super.onQuitFullscreen(url, *objects)
                            setLayBottomScreenFillVisibility(View.GONE)
                            setLayBottomScreenNormalVisibility(View.VISIBLE)

                            orientationUtils.backToProtVideo()
                        }
                    })
                    .setLockClickListener { _, lock ->
                        orientationUtils.isEnable = !lock
                    }
                    .build(this)

            fullscreenButton.setOnClickListener {
                //横屏
                orientationUtils.resolveByClick()
                //第一个true是否需要隐藏actionbar，第二个true是否需要隐藏statusbar
                startWindowFullscreen(this@PlayActivity, true, true)
            }
        }
    }

   inner class PagerAdapter(fragmentActivity: FragmentActivity) :
            FragmentStateAdapter(fragmentActivity) {

        private val fragments: ArrayList<Fragment> = arrayListOf()

        init {
            val questionsFragment = QuestionsFragment()
            val bundle = Bundle()
            bundle.putString("course_id",courseId)
            questionsFragment.arguments = bundle

            fragments.add(ChapterFragment())
            fragments.add(questionsFragment)
            fragments.add(DetailsFragment())
        }

        override fun getItemCount(): Int {
            return fragments.size
        }

        override fun createFragment(position: Int): Fragment {
            return fragments[position]
        }

    }

    override fun onPause() {
        binding.videoPlayer.currentPlayer.onVideoPause()
        isPlay = true
        super.onPause()
    }

    override fun onResume() {
        binding.videoPlayer.currentPlayer.onVideoResume(false)
        isPause = false
        super.onResume()
    }

    override fun onDestroy() {
        super.onDestroy()
        GSYVideoManager.releaseAllVideos()
        orientationUtils?.releaseListener()
    }

    override fun onBackPressed() {
        orientationUtils?.backToProtVideo()
        if (GSYVideoManager.backFromWindowFull(this)) return
        super.onBackPressed()
    }

    override fun onConfigurationChanged(newConfig: Configuration) {
        super.onConfigurationChanged(newConfig)
        //如果旋转了就全屏
        if (isPlay && !isPause) {
            binding.videoPlayer.onConfigurationChanged(this, newConfig, orientationUtils, true, true)
        }
    }
}