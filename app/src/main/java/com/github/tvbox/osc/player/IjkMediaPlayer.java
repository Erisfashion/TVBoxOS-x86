package com.github.tvbox.osc.player;

import android.content.Context;
import android.content.res.AssetFileDescriptor;
import android.media.AudioManager;
import android.net.Uri;
import android.view.Surface;
import android.view.SurfaceHolder;

import java.io.FileDescriptor;
import java.util.Map;

import tv.danmaku.ijk.media.player.MediaInfo;
import tv.danmaku.ijk.media.player.misc.ITrackInfo;
import xyz.doikki.videoplayer.player.AbstractPlayer;

public class IjkMediaPlayer extends AbstractPlayer {

    private tv.danmaku.ijk.media.player.IjkMediaPlayer mMediaPlayer;
    private Context mContext;
    private Object mCodec;
    private float mSpeed = 1.0f;
    private int mBufferPercent = 0;

    public IjkMediaPlayer(Context context, Object codec) {
        mContext = context;
        mCodec = codec;
    }

    @Override
    public void initPlayer() {
        mMediaPlayer = new tv.danmaku.ijk.media.player.IjkMediaPlayer();
        mMediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);

        // 针对 Android x86 / 4.2.2 核心优化配置
        mMediaPlayer.setOption(tv.danmaku.ijk.media.player.IjkMediaPlayer.OPT_CATEGORY_PLAYER, "mediacodec", 0);
        mMediaPlayer.setOption(tv.danmaku.ijk.media.player.IjkMediaPlayer.OPT_CATEGORY_PLAYER, "mediacodec-auto-rotate", 0);
        mMediaPlayer.setOption(tv.danmaku.ijk.media.player.IjkMediaPlayer.OPT_CATEGORY_PLAYER, "mediacodec-handle-resolution-change", 0);
        mMediaPlayer.setOption(tv.danmaku.ijk.media.player.IjkMediaPlayer.OPT_CATEGORY_PLAYER, "opensles", 0);
        mMediaPlayer.setOption(tv.danmaku.ijk.media.player.IjkMediaPlayer.OPT_CATEGORY_FORMAT, "dns_cache_clear", 1);
        mMediaPlayer.setOption(tv.danmaku.ijk.media.player.IjkMediaPlayer.OPT_CATEGORY_PLAYER, "framedrop", 1);
        mMediaPlayer.setOption(tv.danmaku.ijk.media.player.IjkMediaPlayer.OPT_CATEGORY_PLAYER, "start-on-prepared", 1);

        initListener();
    }

    private void initListener() {
        mMediaPlayer.setOnPreparedListener(mp -> {
            if (mPlayerEventListener != null) mPlayerEventListener.onPrepared();
        });
        mMediaPlayer.setOnCompletionListener(mp -> {
            if (mPlayerEventListener != null) mPlayerEventListener.onCompletion();
        });
        mMediaPlayer.setOnErrorListener((mp, what, extra) -> {
            if (mPlayerEventListener != null) {
                mPlayerEventListener.onError();
                return true;
            }
            return false;
        });
        mMediaPlayer.setOnInfoListener((mp, what, extra) -> {
            if (mPlayerEventListener != null) {
                mPlayerEventListener.onInfo(what, extra);
                return true;
            }
            return false;
        });
        mMediaPlayer.setOnBufferingUpdateListener((mp, percent) -> {
            mBufferPercent = percent;
            if (mPlayerEventListener != null) mPlayerEventListener.onBufferingUpdate(percent);
        });
        mMediaPlayer.setOnSeekCompleteListener(mp -> {
            if (mPlayerEventListener != null) mPlayerEventListener.onSeekComplete();
        });
        mMediaPlayer.setOnVideoSizeChangedListener((mp, width, height, sarNum, sarDen) -> {
            if (mPlayerEventListener != null) mPlayerEventListener.onVideoSizeChanged(width, height);
        });
    }

    @Override
    public void setDataSource(String path, Map<String, String> headers) {
        try {
            if (headers != null && !headers.isEmpty()) {
                mMediaPlayer.setDataSource(mContext, Uri.parse(path), headers);
            } else {
                mMediaPlayer.setDataSource(path);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void setDataSource(AssetFileDescriptor fd) {
        try {
            mMediaPlayer.setDataSource(fd.getFileDescriptor(), fd.getStartOffset(), fd.getLength());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void prepareAsync() {
        try {
            mMediaPlayer.prepareAsync();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void start() {
        try {
            mMediaPlayer.start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void pause() {
        try {
            mMediaPlayer.pause();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void stop() {
        try {
            mMediaPlayer.stop();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void reset() {
        try {
            mMediaPlayer.reset();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean isPlaying() {
        try {
            return mMediaPlayer.isPlaying();
        } catch (Exception e) {
            return false;
        }
    }

    @Override
    public void seekTo(long time) {
        try {
            mMediaPlayer.seekTo(time);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public long getCurrentPosition() {
        try {
            return mMediaPlayer.getCurrentPosition();
        } catch (Exception e) {
            return 0;
        }
    }

    @Override
    public long getDuration() {
        try {
            return mMediaPlayer.getDuration();
        } catch (Exception e) {
            return 0;
        }
    }

    @Override
    public void release() {
        if (mMediaPlayer != null) {
            mMediaPlayer.release();
            mMediaPlayer = null;
        }
    }

    @Override
    public void setVolume(float leftVolume, float rightVolume) {
        if (mMediaPlayer != null) {
            mMediaPlayer.setVolume(leftVolume, rightVolume);
        }
    }

    @Override
    public void setLooping(boolean isLooping) {
        if (mMediaPlayer != null) {
            mMediaPlayer.setLooping(isLooping);
        }
    }

    @Override
    public void setSurface(Surface surface) {
        if (mMediaPlayer != null) {
            mMediaPlayer.setSurface(surface);
        }
    }

    @Override
    public void setDisplay(SurfaceHolder holder) {
        if (mMediaPlayer != null) {
            mMediaPlayer.setDisplay(holder);
        }
    }

    @Override
    public void setScreenOnWhilePlaying(boolean screenOn) {
        if (mMediaPlayer != null) {
            mMediaPlayer.setScreenOnWhilePlaying(screenOn);
        }
    }

    @Override
    public long getTcpSpeed() {
        return 0;
    }

    @Override
    public void setSpeed(float speed) {
        mSpeed = speed;
        try {
            if (mMediaPlayer != null) {
                mMediaPlayer.setSpeed(speed);
            }
        } catch (Throwable ignored) {}
    }

    @Override
    public void setPitch(float pitch) {
        // 存根
    }

    @Override
    public float getSpeed() {
        return mSpeed;
    }

    @Override
    public int getBufferedPercentage() {
        return mBufferPercent;
    }

    @Override
    public boolean isLooping() {
        return mMediaPlayer != null && mMediaPlayer.isLooping();
    }

    // 补齐 AbstractPlayer 在当前版本中额外要求的抽象方法
    @Override
    public void setOptions() {
        // 存根实现
    }

    @Override
    public void pause(&boolean pause) {
        // 兼容存根
    }

    // 扩展方法
    public ITrackInfo[] getTrackInfo() {
        return mMediaPlayer != null ? mMediaPlayer.getTrackInfo() : null;
    }

    public void setTrack(int trackId, String progressKey) {
        if (mMediaPlayer != null) {
            try {
                mMediaPlayer.selectTrack(trackId);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void loadDefaultTrack(String progressKey) {
        // 兼容存根
    }

    public tv.danmaku.ijk.media.player.IjkMediaPlayer getInternalMediaPlayer() {
        return mMediaPlayer;
    }
}
