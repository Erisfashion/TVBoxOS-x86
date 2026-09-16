package com.github.tvbox.osc.player;

import android.content.ContentResolver;
import android.content.Context;
import android.content.res.AssetFileDescriptor;
import android.net.Uri;
import android.text.TextUtils;
import android.view.Surface;
import android.view.SurfaceHolder;

import com.github.tvbox.osc.base.App;
import com.github.tvbox.osc.util.HawkConfig;
import com.orhanobut.hawk.Hawk;

import java.util.Map;

import tv.danmaku.ijk.media.player.IMediaPlayer;
import tv.danmaku.ijk.media.player.misc.ITrackInfo;
import xyz.doikki.videoplayer.player.AbstractPlayer;

public class IjkMediaPlayer extends AbstractPlayer {

    protected tv.danmaku.ijk.media.player.IjkMediaPlayer mMediaPlayer;
    private int mBufferedPercent;

    public IjkMediaPlayer() {
    }

    public IjkMediaPlayer(Context context) {
    }

    @Override
    public void initPlayer() {
        mMediaPlayer = new tv.danmaku.ijk.media.player.IjkMediaPlayer();
        setOptions();
        initListener();
    }

    protected void initListener() {
        mMediaPlayer.setOnErrorListener(new IMediaPlayer.OnErrorListener() {
            @Override
            public boolean onError(IMediaPlayer iMediaPlayer, int what, int extra) {
                if (mPlayerEventListener != null) {
                    mPlayerEventListener.onError();
                }
                return true;
            }
        });

        mMediaPlayer.setOnCompletionListener(new IMediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(IMediaPlayer iMediaPlayer) {
                if (mPlayerEventListener != null) {
                    mPlayerEventListener.onCompletion();
                }
            }
        });

        mMediaPlayer.setOnInfoListener(new IMediaPlayer.OnInfoListener() {
            @Override
            public boolean onInfo(IMediaPlayer iMediaPlayer, int what, int extra) {
                if (mPlayerEventListener != null) {
                    mPlayerEventListener.onInfo(what, extra);
                }
                return true;
            }
        });

        mMediaPlayer.setOnBufferingUpdateListener(new IMediaPlayer.OnBufferingUpdateListener() {
            @Override
            public void onBufferingUpdate(IMediaPlayer iMediaPlayer, int percent) {
                mBufferedPercent = percent;
                // mPlayerEventListener 无 onBufferingUpdate 方法，已注释避免编译报错
            }
        });

        mMediaPlayer.setOnPreparedListener(new IMediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(IMediaPlayer iMediaPlayer) {
                if (mPlayerEventListener != null) {
                    mPlayerEventListener.onPrepared();
                }
            }
        });

        mMediaPlayer.setOnSeekCompleteListener(new IMediaPlayer.OnSeekCompleteListener() {
            @Override
            public void onSeekComplete(IMediaPlayer iMediaPlayer) {
                // mPlayerEventListener 无 onSeekComplete 方法，已注释避免编译报错
            }
        });

        mMediaPlayer.setOnVideoSizeChangedListener(new IMediaPlayer.OnVideoSizeChangedListener() {
            @Override
            public void onVideoSizeChanged(IMediaPlayer iMediaPlayer, int width, int height, int sarNum, int sarDen) {
                if (mPlayerEventListener != null) {
                    mPlayerEventListener.onVideoSizeChanged(width, height);
                }
            }
        });
    }

    @Override
    public void setDataSource(String path, Map<String, String> headers) {
        if (TextUtils.isEmpty(path)) return;
        try {
            Uri uri = Uri.parse(path);
            if (headers != null && !headers.isEmpty()) {
                for (String key : headers.keySet()) {
                    mMediaPlayer.setOption(tv.danmaku.ijk.media.player.IjkMediaPlayer.OPT_CATEGORY_FORMAT, "headers", key + ": " + headers.get(key) + "\r\n");
                }
            }
            mMediaPlayer.setDataSource(App.getInstance(), uri, headers);
        } catch (Exception e) {
            if (mPlayerEventListener != null) {
                mPlayerEventListener.onError();
            }
        }
    }

    @Override
    public void setDataSource(AssetFileDescriptor fd) {
        try {
            try {
                mMediaPlayer.setDataSource(new xyz.doikki.videoplayer.ijk.RawDataSourceProvider(fd));
            } catch (Throwable t) {
                mMediaPlayer.setDataSource(fd.getFileDescriptor());
            }
        } catch (Exception e) {
            if (mPlayerEventListener != null) {
                mPlayerEventListener.onError();
            }
        }
    }

    @Override
    public void start() {
        try {
            mMediaPlayer.start();
        } catch (Exception e) {
            if (mPlayerEventListener != null) {
                mPlayerEventListener.onError();
            }
        }
    }

    @Override
    public void pause() {
        try {
            mMediaPlayer.pause();
        } catch (Exception e) {
            if (mPlayerEventListener != null) {
                mPlayerEventListener.onError();
            }
        }
    }

    @Override
    public void stop() {
        try {
            mMediaPlayer.stop();
        } catch (Exception e) {
            if (mPlayerEventListener != null) {
                mPlayerEventListener.onError();
            }
        }
    }

    @Override
    public void prepareAsync() {
        try {
            mMediaPlayer.prepareAsync();
        } catch (Exception e) {
            if (mPlayerEventListener != null) {
                mPlayerEventListener.onError();
            }
        }
    }

    @Override
    public void reset() {
        try {
            mMediaPlayer.reset();
            mMediaPlayer.setOnBufferingUpdateListener(null);
            mMediaPlayer.setOnCompletionListener(null);
            mMediaPlayer.setOnErrorListener(null);
            mMediaPlayer.setOnInfoListener(null);
            mMediaPlayer.setOnPreparedListener(null);
            mMediaPlayer.setOnSeekCompleteListener(null);
            mMediaPlayer.setOnVideoSizeChangedListener(null);
            setOptions();
        } catch (Exception e) {
            if (mPlayerEventListener != null) {
                mPlayerEventListener.onError();
            }
        }
    }

    @Override
    public boolean isPlaying() {
        return mMediaPlayer != null && mMediaPlayer.isPlaying();
    }

    @Override
    public void seekTo(long time) {
        try {
            mMediaPlayer.seekTo(time);
        } catch (Exception e) {
            if (mPlayerEventListener != null) {
                mPlayerEventListener.onError();
            }
        }
    }

    @Override
    public void release() {
        try {
            if (mMediaPlayer != null) {
                mMediaPlayer.release();
            }
        } catch (Exception e) {
            if (mPlayerEventListener != null) {
                mPlayerEventListener.onError();
            }
        }
    }

    @Override
    public long getCurrentPosition() {
        return mMediaPlayer != null ? mMediaPlayer.getCurrentPosition() : 0;
    }

    @Override
    public long getDuration() {
        return mMediaPlayer != null ? mMediaPlayer.getDuration() : 0;
    }

    @Override
    public int getBufferedPercentage() {
        return mBufferedPercent;
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
    public void setVolume(float left, float right) {
        if (mMediaPlayer != null) {
            mMediaPlayer.setVolume(left, right);
        }
    }

    @Override
    public void setLooping(boolean isLooping) {
        if (mMediaPlayer != null) {
            mMediaPlayer.setLooping(isLooping);
        }
    }

    public void setOptions() {
        try {
            int playerType = Hawk.get(HawkConfig.IJK_CODEC, 0);
            if (playerType == 1) {
                mMediaPlayer.setOption(tv.danmaku.ijk.media.player.IjkMediaPlayer.OPT_CATEGORY_PLAYER, "mediacodec", 1);
                mMediaPlayer.setOption(tv.danmaku.ijk.media.player.IjkMediaPlayer.OPT_CATEGORY_PLAYER, "mediacodec-auto-rotate", 1);
                mMediaPlayer.setOption(tv.danmaku.ijk.media.player.IjkMediaPlayer.OPT_CATEGORY_PLAYER, "mediacodec-handle-resolution-change", 1);
            } else {
                mMediaPlayer.setOption(tv.danmaku.ijk.media.player.IjkMediaPlayer.OPT_CATEGORY_PLAYER, "mediacodec", 0);
            }
            mMediaPlayer.setOption(tv.danmaku.ijk.media.player.IjkMediaPlayer.OPT_CATEGORY_PLAYER, "opensles", 0);
            mMediaPlayer.setOption(tv.danmaku.ijk.media.player.IjkMediaPlayer.OPT_CATEGORY_PLAYER, "overlay-format", tv.danmaku.ijk.media.player.IjkMediaPlayer.SDL_FCC_RV32);
            mMediaPlayer.setOption(tv.danmaku.ijk.media.player.IjkMediaPlayer.OPT_CATEGORY_PLAYER, "framedrop", 1);
            mMediaPlayer.setOption(tv.danmaku.ijk.media.player.IjkMediaPlayer.OPT_CATEGORY_PLAYER, "start-on-prepared", 1);
            mMediaPlayer.setOption(tv.danmaku.ijk.media.player.IjkMediaPlayer.OPT_CATEGORY_FORMAT, "http-detect-range-support", 0);
            mMediaPlayer.setOption(tv.danmaku.ijk.media.player.IjkMediaPlayer.OPT_CATEGORY_CODEC, "skip_loop_filter", 48);
            mMediaPlayer.setOption(tv.danmaku.ijk.media.player.IjkMediaPlayer.OPT_CATEGORY_PLAYER, "max-buffer-size", 1024 * 1024 * 10);
            mMediaPlayer.setOption(tv.danmaku.ijk.media.player.IjkMediaPlayer.OPT_CATEGORY_PLAYER, "enable-accurate-seek", 0);
        } catch (Throwable ignored) {}
    }

    public void setSpeed(float speed) {
        if (mMediaPlayer != null) {
            mMediaPlayer.setSpeed(speed);
        }
    }

    public float getSpeed() {
        return mMediaPlayer != null ? mMediaPlayer.getSpeed(0) : 1.0f;
    }

    public long getTcpSpeed() {
        return mMediaPlayer != null ? mMediaPlayer.getTcpSpeed() : 0;
    }

    public ITrackInfo[] getTrackInfo() {
        return mMediaPlayer != null ? mMediaPlayer.getTrackInfo() : null;
    }

    public void selectTrack(int track) {
        if (mMediaPlayer != null) {
            mMediaPlayer.selectTrack(track);
        }
    }

    public void deselectTrack(int track) {
        if (mMediaPlayer != null) {
            mMediaPlayer.deselectTrack(track);
        }
    }

    public int getSelectedTrack(int trackType) {
        return mMediaPlayer != null ? mMediaPlayer.getSelectedTrack(trackType) : -1;
    }
}
