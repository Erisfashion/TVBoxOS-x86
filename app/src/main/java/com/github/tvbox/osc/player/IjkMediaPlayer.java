package com.github.tvbox.osc.player;

import android.content.Context;
import android.content.res.AssetFileDescriptor;
import android.media.AudioManager;
import android.net.Uri;
import android.os.Bundle;
import android.view.Surface;
import android.view.SurfaceHolder;
import java.io.FileDescriptor;
import java.io.IOException;
import java.util.Map;
import tv.danmaku.ijk.media.player.AbstractMediaPlayer;
import tv.danmaku.ijk.media.player.MediaInfo;
import tv.danmaku.ijk.media.player.misc.ITrackInfo;

public class IjkMediaPlayer extends AbstractMediaPlayer {

    private tv.danmaku.ijk.media.player.IjkMediaPlayer mMediaPlayer;

    public IjkMediaPlayer() {
        initPlayer();
    }

    private void initPlayer() {
        mMediaPlayer = new tv.danmaku.ijk.media.player.IjkMediaPlayer();
        mMediaPlayer.setOption(tv.danmaku.ijk.media.player.IjkMediaPlayer.OPT_CATEGORY_PLAYER, "mediacodec", 0);
        mMediaPlayer.setOption(tv.danmaku.ijk.media.player.IjkMediaPlayer.OPT_CATEGORY_PLAYER, "mediacodec-auto-rotate", 0);
        mMediaPlayer.setOption(tv.danmaku.ijk.media.player.IjkMediaPlayer.OPT_CATEGORY_PLAYER, "mediacodec-handle-resolution-change", 0);
        mMediaPlayer.setOption(tv.danmaku.ijk.media.player.IjkMediaPlayer.OPT_CATEGORY_PLAYER, "opensles", 0);
        mMediaPlayer.setOption(tv.danmaku.ijk.media.player.IjkMediaPlayer.OPT_CATEGORY_FORMAT, "dns_cache_clear", 1);
        mMediaPlayer.setOption(tv.danmaku.ijk.media.player.IjkMediaPlayer.OPT_CATEGORY_PLAYER, "framedrop", 1);
        mMediaPlayer.setOption(tv.danmaku.ijk.media.player.IjkMediaPlayer.OPT_CATEGORY_PLAYER, "start-on-prepared", 1);
    }

    @Override
    public void setDisplay(SurfaceHolder sh) {
        if (mMediaPlayer != null) mMediaPlayer.setDisplay(sh);
    }

    @Override
    public void setSurface(Surface surface) {
        if (mMediaPlayer != null) mMediaPlayer.setSurface(surface);
    }

    @Override
    public void setDataSource(Context context, Uri uri) throws IOException, IllegalArgumentException, SecurityException, IllegalStateException {
        if (mMediaPlayer != null) mMediaPlayer.setDataSource(context, uri);
    }

    @Override
    public void setDataSource(Context context, Uri uri, Map<String, String> headers) throws IOException, IllegalArgumentException, SecurityException, IllegalStateException {
        if (mMediaPlayer != null) mMediaPlayer.setDataSource(context, uri, headers);
    }

    @Override
    public void setDataSource(FileDescriptor fd) throws IOException, IllegalArgumentException, IllegalStateException {
        if (mMediaPlayer != null) mMediaPlayer.setDataSource(fd);
    }

    @Override
    public void setDataSource(String path) throws IOException, IllegalArgumentException, SecurityException, IllegalStateException {
        if (mMediaPlayer != null) mMediaPlayer.setDataSource(path);
    }

    @Override
    public String getDataSource() {
        return mMediaPlayer != null ? mMediaPlayer.getDataSource() : null;
    }

    @Override
    public void prepareAsync() throws IllegalStateException {
        if (mMediaPlayer != null) mMediaPlayer.prepareAsync();
    }

    @Override
    public void start() throws IllegalStateException {
        if (mMediaPlayer != null) mMediaPlayer.start();
    }

    @Override
    public void stop() throws IllegalStateException {
        if (mMediaPlayer != null) mMediaPlayer.stop();
    }

    @Override
    public void pause() throws IllegalStateException {
        if (mMediaPlayer != null) mMediaPlayer.pause();
    }

    @Override
    public void setScreenOnWhilePlaying(boolean screenOn) {
        if (mMediaPlayer != null) mMediaPlayer.setScreenOnWhilePlaying(screenOn);
    }

    @Override
    public int getVideoWidth() {
        return mMediaPlayer != null ? mMediaPlayer.getVideoWidth() : 0;
    }

    @Override
    public int getVideoHeight() {
        return mMediaPlayer != null ? mMediaPlayer.getVideoHeight() : 0;
    }

    @Override
    public boolean isPlaying() {
        return mMediaPlayer != null && mMediaPlayer.isPlaying();
    }

    @Override
    public void seekTo(long msec) throws IllegalStateException {
        if (mMediaPlayer != null) mMediaPlayer.seekTo(msec);
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
    public void release() {
        if (mMediaPlayer != null) {
            mMediaPlayer.resetListeners();
            mMediaPlayer.release();
            mMediaPlayer = null;
        }
    }

    @Override
    public void reset() {
        if (mMediaPlayer != null) mMediaPlayer.reset();
    }

    @Override
    public void setVolume(float leftVolume, float rightVolume) {
        if (mMediaPlayer != null) mMediaPlayer.setVolume(leftVolume, rightVolume);
    }

    @Override
    public int getAudioSessionId() {
        return mMediaPlayer != null ? mMediaPlayer.getAudioSessionId() : 0;
    }

    @Override
    public MediaInfo getMediaInfo() {
        return mMediaPlayer != null ? mMediaPlayer.getMediaInfo() : null;
    }

    @Override
    public void setLooping(boolean looping) {
        if (mMediaPlayer != null) mMediaPlayer.setLooping(looping);
    }

    @Override
    public boolean isLooping() {
        return mMediaPlayer != null && mMediaPlayer.isLooping();
    }

    @Override
    public ITrackInfo[] getTrackInfo() {
        return mMediaPlayer != null ? mMediaPlayer.getTrackInfo() : null;
    }

    @Override
    public void setAudioStreamType(int streamtype) {
        if (mMediaPlayer != null) mMediaPlayer.setAudioStreamType(streamtype);
    }

    @Override
    public void setWakeMode(Context context, int mode) {}

    @Override
    public int getVideoSarNum() {
        return mMediaPlayer != null ? mMediaPlayer.getVideoSarNum() : 1;
    }

    @Override
    public int getVideoSarDen() {
        return mMediaPlayer != null ? mMediaPlayer.getVideoSarDen() : 1;
    }

    @Override
    public void setKeepInBackground(boolean stayInBackground) {}

    @Override
    public boolean isPlayable() {
        return true;
    }

    public tv.danmaku.ijk.media.player.IjkMediaPlayer getInternalMediaPlayer() {
        return mMediaPlayer;
    }

    @Override
    public void setLogEnabled(boolean enable) {
        // 占位存根实现
    }
}
