package com.github.tvbox.osc.player;

import android.content.Context;
import com.github.tvbox.osc.bean.IJKCode;
import xyz.doikki.videoplayer.ijk.IjkPlayer;

public class IjkMediaPlayer extends IjkPlayer {

    public IjkMediaPlayer(Context context) {
        super(context);
    }

    public IjkMediaPlayer(Context context, IJKCode code) {
        super(context);
    }
}
