package com.sandgrains.edu.student.utils.custom.video;

import android.app.Dialog;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.lxj.xpopup.XPopup;
import com.lxj.xpopup.core.BasePopupView;
import com.lxj.xpopup.enums.PopupPosition;
import com.lxj.xpopup.interfaces.SimpleCallback;
import com.sandgrains.edu.student.R;
import com.sandgrains.edu.student.model.Chapter;
import com.sandgrains.edu.student.model.Section;
import com.sandgrains.edu.student.ui.video.OnFinishActivity;
import com.sandgrains.edu.student.ui.video.SectionSelectedCallback;
import com.shuyu.gsyvideoplayer.listener.GSYStateUiListener;
import com.shuyu.gsyvideoplayer.model.GSYVideoModel;
import com.shuyu.gsyvideoplayer.utils.CommonUtil;
import com.shuyu.gsyvideoplayer.utils.Debuger;
import com.shuyu.gsyvideoplayer.video.StandardGSYVideoPlayer;
import com.shuyu.gsyvideoplayer.video.base.GSYBaseVideoPlayer;
import com.shuyu.gsyvideoplayer.video.base.GSYVideoView;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 4.播放结束拖动进度条再播放不能正常执行
 */


public class VideoPlayer extends StandardGSYVideoPlayer implements GSYStateUiListener {
    //左下角的播放按钮
    private ImageView mMiniPlay;

    private SeekBar mMyProgressBar;
    //正常状态下显示的底部工具栏
    private LinearLayout mLayBottomScreenNormal;
    //全屏状态下显示的底部工具栏
    private LinearLayout mLayBottomScreenFill;

    //全屏状态的工具栏
    //当前时间
    private TextView mCurrent2;
    //总时间
    private TextView mTotal2;
    //播放
    private ImageView mMiniPlay2;
    //清晰度
    private TextView mDefinition;
    //章节
    private TextView mChapter;
    //倍速
    private TextView mSpeed;

    public VideoPlayer(Context context, Boolean fullFlag) {
        super(context, fullFlag);
    }

    public VideoPlayer(Context context) {
        super(context);
    }

    public VideoPlayer(Context context, AttributeSet attrs) {
        super(context, attrs);
    }


    private OnFinishActivity finishCallback;
    public void setFinishCallback(OnFinishActivity callback){
        this.finishCallback = callback;
    }

    @Override
    protected void init(Context context) {
        super.init(context);
        mDefinition = findViewById(R.id.tv_definition);
        mChapter = findViewById(R.id.tv_chapter);
        mSpeed = findViewById(R.id.tv_speed);
        mMiniPlay = findViewById(R.id.iv_play);
        mMyProgressBar = findViewById(R.id.my_progress);
        mLayBottomScreenNormal = findViewById(R.id.lay_bottom_screen_normal);
        mLayBottomScreenFill = findViewById(R.id.lay_bottom_screen_fill);
        mCurrent2 = findViewById(R.id.current2);
        mTotal2 = findViewById(R.id.total2);
        mMiniPlay2 = findViewById(R.id.iv_play2);

        mMiniPlay.setOnClickListener(v -> clickStartIcon());
        mMiniPlay2.setOnClickListener(v -> {
            clickStartIcon();
        });
        mDefinition.setOnClickListener(v -> showSwitchDialog());
        mChapter.setOnClickListener(v -> {
            showSwitchChapterDialog();
        });

        setGSYStateUiListener(this);

        if (mBottomShowProgressDrawable != null) {
            mMyProgressBar.setProgressDrawable(mBottomProgressDrawable);
        }

        if (mBottomShowProgressThumbDrawable != null) {
            mMyProgressBar.setThumb(mBottomShowProgressThumbDrawable);
        }
        mMyProgressBar.setOnSeekBarChangeListener(this);

        mBackButton.setOnClickListener(v -> {
            if (finishCallback!=null)
                finishCallback.onFinish();
        });
    }



    private List<Section> sectionList = new ArrayList<>();

    public void setChapterList(List<Chapter> chapterList) {
        ArrayList<Section> sections = new ArrayList<>();
        for (int i = 0; i < chapterList.size(); i++) {
            sections.addAll(chapterList.get(i).getSectionList());
        }

        this.sectionList = sections;
    }

    private void showSwitchChapterDialog() {

        cancelDismissControlViewTimer();

        ListChapterDrawerPopupView drawerPopupView = new ListChapterDrawerPopupView(getContext(),
                sectionList,
                selectedSectionName,
                section -> {
                    popupView.dismiss();
                    String str = section.getName();
                    selectedSectionName = str;
                    callback.callback(section);
                    Toast.makeText(getContext(), "你选择了:" + str, Toast.LENGTH_SHORT).show();
                });

        popupView = new XPopup.Builder(getContext())
                .isDestroyOnDismiss(true) //对于只使用一次的弹窗，推荐设置这个
                .popupPosition(PopupPosition.Right)//右边
                .hasStatusBarShadow(false)//启用状态栏阴影
                .hasShadowBg(false)
                .setPopupCallback(new SimpleCallback() {
                    @Override
                    public void onDismiss(BasePopupView popupView) {
                        super.onDismiss(popupView);
                        startDismissControlViewTimer();
                    }
                })
                .asCustom(drawerPopupView)
                .show();
    }

    private SectionSelectedCallback callback;
    private String selectedDefinition = "流畅";
    private String selectedLine = "线路1";
    private String selectedSectionName = "";
    private BasePopupView popupView;

    public void setSelectedSection(String sectionName) {
        this.selectedSectionName = sectionName;
    }

    public void setSectionSelectedCallback(SectionSelectedCallback callback) {
        this.callback = callback;
    }

    /**
     * 弹出切换清晰度
     */
    private void showSwitchDialog() {
        cancelDismissControlViewTimer();
        ArrayList<VideoParameterModel> list = new ArrayList<>();

        ArrayList<String> options = new ArrayList<>();
        options.add("流畅");
        options.add("高清");
        options.add("超清");
        VideoParameterModel model1 = new VideoParameterModel("清晰度:", options);
        ArrayList<String> options2 = new ArrayList<>();
        options2.add("线路1");
        options2.add("线路2");
        options2.add("线路3");
        VideoParameterModel model2 = new VideoParameterModel("线路:", options2);
        list.add(model1);
        list.add(model2);

        ListDrawerPopupView drawerPopupView = new ListDrawerPopupView(getContext(),
                list,
                new String[]{selectedDefinition, selectedLine},
                view -> {
                    popupView.dismiss();
                    String str = view.getText().toString();
                    if (options.contains(view.getText().toString())) {
                        mDefinition.setText(str);
                        selectedDefinition = str;
                    } else {
                        selectedLine = str;
                    }

                    Toast.makeText(getContext(), "你选择了:" + str, Toast.LENGTH_SHORT).show();
                });

        popupView = new XPopup.Builder(getContext())
                .isDestroyOnDismiss(true) //对于只使用一次的弹窗，推荐设置这个
                .popupPosition(PopupPosition.Right)//右边
                .hasShadowBg(false)
                .hasStatusBarShadow(false)//启用状态栏阴影
                .setPopupCallback(new SimpleCallback() {
                    @Override
                    public void onDismiss(BasePopupView popupView) {
                        super.onDismiss(popupView);
                        startDismissControlViewTimer();
                    }
                })
                .asCustom(drawerPopupView)
                .show();

    }


//    /**
//     * 设置播放URL
//     *
//     * @param url           播放url
//     * @param cacheWithPlay 是否边播边缓存
//     * @param position      需要播放的位置
//     * @param cachePath     缓存路径，如果是M3U8或者HLS，请设置为false
//     * @param mapHeadData   http header
//     * @param changeState   切换的时候释放surface
//     * @return
//     */
//    protected boolean setUp(List<GSYVideoModel> url, boolean cacheWithPlay, int position, File cachePath, Map<String, String> mapHeadData, boolean changeState) {
//        mUriList = url;
//        mPlayPosition = position;
//        mMapHeadData = mapHeadData;
//        GSYVideoModel gsyVideoModel = url.get(position);
//        boolean set = setUp(gsyVideoModel.getUrl(), cacheWithPlay, cachePath, gsyVideoModel.getTitle(), changeState);
//        if (!TextUtils.isEmpty(gsyVideoModel.getTitle()) && mTitleTextView != null ) {
//            mTitleTextView.setText(gsyVideoModel.getTitle());
//        }
//        return set;
//    }

    @Override
    protected void clickStartIcon() {
        if (TextUtils.isEmpty(mUrl)) {
            Debuger.printfError("********" + getResources().getString(R.string.no_url));
            //Toast.makeText(getActivityContext(), getResources().getString(R.string.no_url), Toast.LENGTH_SHORT).show();
            return;
        }
        if (mCurrentState == CURRENT_STATE_NORMAL || mCurrentState == CURRENT_STATE_ERROR) {
            if (isShowNetConfirm()) {
                showWifiDialog();
                return;
            }
            startButtonLogic();
        } else if (mCurrentState == CURRENT_STATE_PLAYING) {
            try {
                onVideoPause();
            } catch (Exception e) {
                e.printStackTrace();
            }
            setStateAndUi(CURRENT_STATE_PAUSE);
            if (mVideoAllCallBack != null && isCurrentMediaListener()) {
                if (mIfCurrentIsFullscreen) {
                    Debuger.printfLog("onClickStopFullscreen");
                    mVideoAllCallBack.onClickStopFullscreen(mOriginUrl, mTitle, this);
                } else {
                    Debuger.printfLog("onClickStop");
                    mVideoAllCallBack.onClickStop(mOriginUrl, mTitle, this);
                }
            }
        } else if (mCurrentState == CURRENT_STATE_PAUSE) {
            if (mVideoAllCallBack != null && isCurrentMediaListener()) {
                if (mIfCurrentIsFullscreen) {
                    Debuger.printfLog("onClickResumeFullscreen");
                    mVideoAllCallBack.onClickResumeFullscreen(mOriginUrl, mTitle, this);
                } else {
                    Debuger.printfLog("onClickResume");
                    mVideoAllCallBack.onClickResume(mOriginUrl, mTitle, this);
                }
            }

            if (!mHadPlay && !mStartAfterPrepared) {
                startAfterPrepared();
            }

            try {
                getGSYVideoManager().start();
            } catch (Exception e) {
                e.printStackTrace();
            }
            setStateAndUi(CURRENT_STATE_PLAYING);
        } else if (mCurrentState == CURRENT_STATE_AUTO_COMPLETE) {
//            if (a) {
//                try {
//                    getGSYVideoManager().start();
//                } catch (Exception e) {
//                    e.printStackTrace();
//                }
//            }else {
            startButtonLogic();
//            }
        }
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        int id = v.getId();
        float x = event.getX();
        float y = event.getY();

        if (mIfCurrentIsFullscreen && mLockCurScreen && mNeedLockFull) {
            onClickUiToggle(event);
            startDismissControlViewTimer();
            return true;
        }

        if (id == R.id.fullscreen) {
            return false;
        }

        if (id == R.id.surface_container) {
            switch (event.getAction()) {
                case MotionEvent.ACTION_DOWN:

                    touchSurfaceDown(x, y);

                    break;
                case MotionEvent.ACTION_MOVE:
                    float deltaX = x - mDownX;
                    float deltaY = y - mDownY;
                    float absDeltaX = Math.abs(deltaX);
                    float absDeltaY = Math.abs(deltaY);

                    if ((mIfCurrentIsFullscreen && mIsTouchWigetFull)
                            || (mIsTouchWiget && !mIfCurrentIsFullscreen)) {
                        if (!mChangePosition && !mChangeVolume && !mBrightness) {
                            touchSurfaceMoveFullLogic(absDeltaX, absDeltaY);
                        }
                    }
                    touchSurfaceMove(deltaX, deltaY, y);

                    break;
                case MotionEvent.ACTION_UP:

                    startDismissControlViewTimer();

                    touchSurfaceUp();


                    Debuger.printfLog(VideoPlayer.this.hashCode() + "------------------------------ surface_container ACTION_UP");

                    startProgressTimer();

                    //不要和隐藏虚拟按键后，滑出虚拟按键冲突
                    if (mHideKey && mShowVKey) {
                        return true;
                    }
                    break;
            }
            gestureDetector.onTouchEvent(event);
        } else if (id == R.id.progress || id == R.id.my_progress) {
            switch (event.getAction()) {
                case MotionEvent.ACTION_DOWN:
                    cancelDismissControlViewTimer();
                case MotionEvent.ACTION_MOVE:
                    cancelProgressTimer();
                    ViewParent vpdown = getParent();
                    while (vpdown != null) {
                        vpdown.requestDisallowInterceptTouchEvent(true);
                        vpdown = vpdown.getParent();
                    }
                    break;
                case MotionEvent.ACTION_UP:
                    startDismissControlViewTimer();

                    Debuger.printfLog(VideoPlayer.this.hashCode() + "------------------------------ progress ACTION_UP");
                    startProgressTimer();
                    ViewParent vpup = getParent();
                    while (vpup != null) {
                        vpup.requestDisallowInterceptTouchEvent(false);
                        vpup = vpup.getParent();
                    }
                    mBrightnessData = -1f;
                    break;
            }
        }

        return false;
    }

    @Override
    protected void resolveFullVideoShow(Context context, GSYBaseVideoPlayer gsyVideoPlayer, FrameLayout frameLayout) {
        gsyVideoPlayer.findViewById(R.id.lay_bottom_screen_fill).setVisibility(VISIBLE);
        gsyVideoPlayer.findViewById(R.id.lay_bottom_screen_normal).setVisibility(GONE);
        super.resolveFullVideoShow(context, gsyVideoPlayer, frameLayout);
    }

    public void setLayBottomScreenNormalVisibility(int visibility) {
        if (visibility == View.GONE || visibility == View.VISIBLE || visibility == View.INVISIBLE)
            mLayBottomScreenNormal.setVisibility(visibility);
    }

    public void setLayBottomScreenFillVisibility(int visibility) {
        if (visibility == View.GONE || visibility == View.VISIBLE || visibility == View.INVISIBLE)
            mLayBottomScreenFill.setVisibility(visibility);
    }

    @Override
    protected void cloneParams(GSYBaseVideoPlayer from, GSYBaseVideoPlayer to) {
        super.cloneParams(from, to);
        VideoPlayer sf = (VideoPlayer) from;
        VideoPlayer st = (VideoPlayer) to;

        st.isShowDragProgressTextOnSeekBar = sf.isShowDragProgressTextOnSeekBar;

        if (st.mMyProgressBar != null && sf.mMyProgressBar != null) {
            st.mMyProgressBar.setProgress(sf.mMyProgressBar.getProgress());
            st.mMyProgressBar.setSecondaryProgress(sf.mMyProgressBar.getSecondaryProgress());
        }

        if (st.mTotal2 != null && sf.mTotal2 != null) {
            st.mTotal2.setText(sf.mTotal2.getText());
        }

        if (st.mCurrent2 != null && sf.mCurrent2 != null) {
            st.mCurrent2.setText(sf.mCurrent2.getText());
        }

        st.mDefinition.setText(sf.mDefinition.getText());
        st.selectedDefinition = sf.selectedDefinition;
        st.selectedLine = sf.selectedLine;
        st.selectedSectionName = sf.selectedSectionName;
        st.sectionList = sf.sectionList;
        st.callback = sf.callback;
    }

    @Override
    public void setBottomShowProgressBarDrawable(Drawable drawable, Drawable thumb) {
        mBottomShowProgressDrawable = drawable;
        mBottomShowProgressThumbDrawable = thumb;
        if (mProgressBar != null) {
            mProgressBar.setProgressDrawable(drawable);
            mMyProgressBar.setProgressDrawable(drawable);
            mProgressBar.setThumb(thumb);
            mMyProgressBar.setThumb(thumb);
        }
    }

    @Override
    protected void setSmallVideoTextureView() {
        if (mProgressBar != null) {
            mProgressBar.setOnTouchListener(null);
            mMyProgressBar.setOnTouchListener(null);
            mProgressBar.setVisibility(INVISIBLE);
            mMyProgressBar.setVisibility(INVISIBLE);
        }
        if (mFullscreenButton != null) {
            mFullscreenButton.setOnTouchListener(null);
            mFullscreenButton.setVisibility(INVISIBLE);
        }
        if (mCurrentTimeTextView != null) {
            mCurrentTimeTextView.setVisibility(INVISIBLE);
            mCurrent2.setVisibility(INVISIBLE);
        }
        if (mTextureViewContainer != null) {
            mTextureViewContainer.setOnClickListener(null);
        }
        if (mSmallClose != null) {
            mSmallClose.setVisibility(VISIBLE);
            mSmallClose.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    hideSmallVideo();
                    releaseVideos();
                }
            });
        }
    }

    @Override
    public void onBufferingUpdate(int percent) {

        post(new Runnable() {
            @Override
            public void run() {
                if (mCurrentState != CURRENT_STATE_NORMAL && mCurrentState != CURRENT_STATE_PREPAREING) {
                    if (percent != 0) {
                        setTextAndProgress(percent);
                        mBufferPoint = percent;
                        Debuger.printfLog("Net speed: " + getNetSpeedText() + " percent " + percent);
                    }
                    if (mProgressBar == null) {
                        return;
                    }
                    //循环清除进度
                    if (mLooping && mHadPlay && percent == 0 && mProgressBar.getProgress() >= (mProgressBar.getMax() - 1) && mMyProgressBar.getProgress() >= (mMyProgressBar.getMax() - 1)) {
                        loopSetProgressAndTime();
                    }
                }
            }
        });
    }


    @Override
    protected void touchSurfaceUp() {
        if (mChangePosition) {
            int duration = getDuration();
            int progress = mSeekTimePosition * 100 / (duration == 0 ? 1 : duration);
            if (mBottomProgressBar != null)
                mBottomProgressBar.setProgress(progress);
        }

        mTouchingProgressBar = false;
        dismissProgressDialog();
        dismissVolumeDialog();
        dismissBrightnessDialog();
        if (mChangePosition && getGSYVideoManager() != null && (mCurrentState == CURRENT_STATE_PLAYING || mCurrentState == CURRENT_STATE_PAUSE)) {
            try {
                getGSYVideoManager().seekTo(mSeekTimePosition);
            } catch (Exception e) {
                e.printStackTrace();
            }
            int duration = getDuration();
            int progress = mSeekTimePosition * 100 / (duration == 0 ? 1 : duration);
            if (mProgressBar != null) {
                mProgressBar.setProgress(progress);
                mMyProgressBar.setProgress(progress);
            }
            if (mVideoAllCallBack != null && isCurrentMediaListener()) {
                Debuger.printfLog("onTouchScreenSeekPosition");
                mVideoAllCallBack.onTouchScreenSeekPosition(mOriginUrl, mTitle, this);
            }
        } else if (mBrightness) {
            if (mVideoAllCallBack != null && isCurrentMediaListener()) {
                Debuger.printfLog("onTouchScreenSeekLight");
                mVideoAllCallBack.onTouchScreenSeekLight(mOriginUrl, mTitle, this);
            }
        } else if (mChangeVolume) {
            if (mVideoAllCallBack != null && isCurrentMediaListener()) {
                Debuger.printfLog("onTouchScreenSeekVolume");
                mVideoAllCallBack.onTouchScreenSeekVolume(mOriginUrl, mTitle, this);
            }
        }
    }

    @Override
    protected void setTextAndProgress(int secProgress, boolean forceChange) {
        int position = getCurrentPositionWhenPlaying();
        int duration = getDuration();
        int progress = (int) Math.ceil(position * 100 / (duration == 0 ? 1.0 : duration));
        setProgressAndTime(progress, secProgress, position, duration, forceChange);
    }

    @Override
    protected void setProgressAndTime(int progress, int secProgress, int currentTime, int totalTime, boolean forceChange) {
        if (mGSYVideoProgressListener != null && mCurrentState == CURRENT_STATE_PLAYING) {
            mGSYVideoProgressListener.onProgress(progress, secProgress, currentTime, totalTime);
        }

        if (mProgressBar == null || mTotalTimeTextView == null || mCurrentTimeTextView == null) {
            return;
        }
        if (mHadSeekTouch) {
            return;
        }
        if (!mTouchingProgressBar) {
            if (progress != 0 || forceChange) {
                mProgressBar.setProgress(progress);
                mMyProgressBar.setProgress(progress);
            }
        }
        if (getGSYVideoManager().getBufferedPercentage() > 0) {
            secProgress = getGSYVideoManager().getBufferedPercentage();
        }
        if (secProgress > 94) secProgress = 100;
        setSecondaryProgress(secProgress);
        mTotalTimeTextView.setText(CommonUtil.stringForTime(totalTime));
        mTotal2.setText(CommonUtil.stringForTime(totalTime));
        if (currentTime > 0) {
            mCurrentTimeTextView.setText(CommonUtil.stringForTime(currentTime));
            mCurrent2.setText(CommonUtil.stringForTime(currentTime));
        }
        if (mBottomProgressBar != null) {
            if (progress != 0 || forceChange) mBottomProgressBar.setProgress(progress);
            setSecondaryProgress(secProgress);
        }
    }

    @Override
    protected void setSecondaryProgress(int secProgress) {
        if (mProgressBar != null) {
            if (secProgress != 0 && !getGSYVideoManager().isCacheFile()) {
                mProgressBar.setSecondaryProgress(secProgress);
                mMyProgressBar.setSecondaryProgress(secProgress);
            }
        }
        if (mBottomProgressBar != null) {
            if (secProgress != 0 && !getGSYVideoManager().isCacheFile()) {
                mBottomProgressBar.setSecondaryProgress(secProgress);
            }
        }
    }

    @Override
    protected void resetProgressAndTime() {
        if (mProgressBar == null || mTotalTimeTextView == null || mCurrentTimeTextView == null) {
            return;
        }
        mProgressBar.setProgress(0);
        mMyProgressBar.setProgress(0);
        mProgressBar.setSecondaryProgress(0);
        mMyProgressBar.setSecondaryProgress(0);
        mCurrentTimeTextView.setText(CommonUtil.stringForTime(0));
        mCurrent2.setText(CommonUtil.stringForTime(0));
        mTotalTimeTextView.setText(CommonUtil.stringForTime(0));
        mTotal2.setText(CommonUtil.stringForTime(0));

        if (mBottomProgressBar != null) {
            mBottomProgressBar.setProgress(0);
            mBottomProgressBar.setSecondaryProgress(0);
        }
    }

    @Override
    protected void loopSetProgressAndTime() {
        if (mProgressBar == null || mTotalTimeTextView == null || mCurrentTimeTextView == null) {
            return;
        }
        mProgressBar.setProgress(0);
        mMyProgressBar.setProgress(0);
        mProgressBar.setSecondaryProgress(0);
        mMyProgressBar.setSecondaryProgress(0);
        mCurrentTimeTextView.setText(CommonUtil.stringForTime(0));
        mCurrent2.setText(CommonUtil.stringForTime(0));
        if (mBottomProgressBar != null)
            mBottomProgressBar.setProgress(0);
    }

    @Override
    public int getLayoutId() {

        return R.layout.lay_video_player;
    }

    @Override
    protected void showBrightnessDialog(float percent) {
        if (mBrightnessDialog == null) {
            View localView = LayoutInflater.from(getActivityContext()).inflate(getBrightnessLayoutId(), null);
            if (localView.findViewById(getBrightnessTextId()) instanceof TextView) {
                mBrightnessDialogTv = (TextView) localView.findViewById(getBrightnessTextId());
            }
            mBrightnessDialog = new Dialog(getActivityContext(), R.style.video_style_dialog_progress);
            mBrightnessDialog.setContentView(localView);
            mBrightnessDialog.getWindow().addFlags(WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE);
            mBrightnessDialog.getWindow().addFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL);
            mBrightnessDialog.getWindow().addFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
            mBrightnessDialog.getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_HIDE_NAVIGATION);
            mBrightnessDialog.getWindow().setLayout(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            WindowManager.LayoutParams localLayoutParams = mBrightnessDialog.getWindow().getAttributes();
            localLayoutParams.gravity = Gravity.TOP | Gravity.END;
            localLayoutParams.width = getWidth();
            localLayoutParams.height = getHeight();
            int location[] = new int[2];
            getLocationOnScreen(location);
            localLayoutParams.x = location[0];
            localLayoutParams.y = location[1];
            mBrightnessDialog.getWindow().setAttributes(localLayoutParams);
        }
        if (!mBrightnessDialog.isShowing()) {
            mBrightnessDialog.show();
        }
        if (mBrightnessDialogTv != null)
            mBrightnessDialogTv.setText((int) (percent * 100) + "%");
    }

    @Override
    protected void dismissBrightnessDialog() {
        if (mBrightnessDialog != null) {
            mBrightnessDialog.dismiss();
            mBrightnessDialog = null;
        }
    }


    @Override
    protected void showVolumeDialog(float deltaY, int volumePercent) {
        if (mVolumeDialog == null) {
            View localView = LayoutInflater.from(getActivityContext()).inflate(getVolumeLayoutId(), null);
            if (localView.findViewById(getVolumeProgressId()) instanceof ProgressBar) {
                mDialogVolumeProgressBar = ((ProgressBar) localView.findViewById(getVolumeProgressId()));
                if (mVolumeProgressDrawable != null && mDialogVolumeProgressBar != null) {
                    mDialogVolumeProgressBar.setProgressDrawable(mVolumeProgressDrawable);
                }
            }
            mVolumeDialog = new Dialog(getActivityContext(), R.style.Theme_AppCompat_Full);
            mVolumeDialog.setContentView(localView);
            mVolumeDialog.getWindow().addFlags(WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE);
            mVolumeDialog.getWindow().addFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL);
            mVolumeDialog.getWindow().addFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
            mVolumeDialog.getWindow().setLayout(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            WindowManager.LayoutParams localLayoutParams = mVolumeDialog.getWindow().getAttributes();
            localLayoutParams.gravity = Gravity.TOP | Gravity.START;
            localLayoutParams.width = getWidth();
            localLayoutParams.height = getHeight();
            int[] location = new int[2];
            getLocationOnScreen(location);
            localLayoutParams.x = location[0];
            localLayoutParams.y = location[1];
            mVolumeDialog.getWindow().setAttributes(localLayoutParams);
        }
        if (!mVolumeDialog.isShowing()) {
            mVolumeDialog.show();
        }
        if (mDialogVolumeProgressBar != null) {
            mDialogVolumeProgressBar.setProgress(volumePercent);
        }
    }


    @Override
    protected void dismissVolumeDialog() {
        if (mVolumeDialog != null) {
            mVolumeDialog.dismiss();
            mVolumeDialog = null;
        }
    }

    @Override
    protected void showDragProgressTextOnSeekBar(boolean fromUser, int progress) {
        if (fromUser && isShowDragProgressTextOnSeekBar) {
            int duration = getDuration();
            if (mCurrentTimeTextView != null) {
                String s = CommonUtil.stringForTime(progress * duration / 100);
                mCurrentTimeTextView.setText(s);
                mCurrent2.setText(CommonUtil.stringForTime(progress * duration / 100));
            }
        }
    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {
        cancelDismissControlViewTimer();
        super.onStartTrackingTouch(seekBar);
    }

    /**
     * @param seekBar
     */
    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {
        startDismissControlViewTimer();
        if (mVideoAllCallBack != null && isCurrentMediaListener()) {
            if (isIfCurrentIsFullscreen()) {
                Debuger.printfLog("onClickSeekbarFullscreen");
                mVideoAllCallBack.onClickSeekbarFullscreen(mOriginUrl, mTitle, this);
            } else {
                Debuger.printfLog("onClickSeekbar");
                mVideoAllCallBack.onClickSeekbar(mOriginUrl, mTitle, this);
            }
        }
        if (getGSYVideoManager() != null && mHadPlay) {
            try {
                int time = seekBar.getProgress() * getDuration() / 100;
                getGSYVideoManager().seekTo(time);
            } catch (Exception e) {
                Debuger.printfWarning(e.toString());
            }
        }
        mHadSeekTouch = false;

    }

    @Override
    protected int getVolumeLayoutId() {
        return R.layout.dialog_volume;
    }

    @Override
    public void onStateChanged(int state) {
        switch (state) {
            case GSYVideoView.CURRENT_STATE_NORMAL:
            case GSYVideoView.CURRENT_STATE_PLAYING_BUFFERING_START:
            case GSYVideoView.CURRENT_STATE_PAUSE:
            case GSYVideoView.CURRENT_STATE_ERROR: {
                mMiniPlay.setImageResource(R.drawable.play2);
                mMiniPlay2.setImageResource(R.drawable.play2);
            }
            break;
            case GSYVideoView.CURRENT_STATE_AUTO_COMPLETE: {
                mMiniPlay.setImageResource(R.drawable.play2);
                mMiniPlay2.setImageResource(R.drawable.play2);
                mMyProgressBar.setProgress(100);

                if (mCurrent2 != null && mTotal2 != null) {
                    mCurrent2.setText(mTotal2.getText());
                }
            }
            break;
            case GSYVideoView.CURRENT_STATE_PLAYING: {
                mMiniPlay.setImageResource(R.drawable.pause2);
                mMiniPlay2.setImageResource(R.drawable.pause2);
            }
            break;

        }
    }

    public void play(String videoUri) {
        setUp(videoUri, true, null, "", false);

//        List<String> videoUrlList = new ArrayList<>();
//        videoUrlList.add("http://9890.vod.myqcloud.com/9890_4e292f9a3dd011e6b4078980237cc3d3.f20.mp4");
//        videoUrlList.add("http://7xjmzj.com1.z0.glb.clouddn.com/20171026175005_JObCxCE2.mp4");
//        videoUrlList.add("https://res.exexm.com/cw_145225549855002");
//        setUp(videoUrlList,mCache);
        startPlayLogic();
    }
}
