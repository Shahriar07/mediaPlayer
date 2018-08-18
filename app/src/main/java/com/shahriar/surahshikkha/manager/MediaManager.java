package com.shahriar.surahshikkha.manager;

import android.content.Context;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.util.Log;

/**
 * Created by H. M. Shahriar on 8/16/2018.
 */
public class MediaManager implements AudioManager.OnAudioFocusChangeListener, MediaPlayer.OnCompletionListener {
    // Media player
    MediaPlayer player;
    private AudioManager mAudioManager;
    int mediaDuration;
    private boolean isActivityForsedPaused;

    public MediaManager(Context context, int resourceID)
{
    player = MediaPlayer.create(context, resourceID);
    if (player != null) {
        player.setOnCompletionListener(this);
        mAudioManager = (AudioManager) context.getSystemService(Context.AUDIO_SERVICE);
        mAudioManager.requestAudioFocus(this, AudioManager.STREAM_MUSIC, AudioManager.AUDIOFOCUS_GAIN);
        mediaDuration = player.getDuration();
    }
}

    @Override
    public void onAudioFocusChange(int focusChange) {
        if(focusChange<=0) { // Focus losses
            Log.i(getClass().getSimpleName(),"OnStop");
            if (player.isPlaying()){
                player.pause();
                isActivityForsedPaused = true;
            }
        } else { // Focus gained
            if (isActivityForsedPaused){
                player.start();
                isActivityForsedPaused = false;
            }
        }
    }

    @Override
    public void onCompletion(MediaPlayer mp) {

    }

    public boolean isMediaPlaying() {
        if (player != null)
            return player.isPlaying();
        else
            return false;
    }

    public void pauseMedia() {
        if (player != null)
            player.pause();

    }

    public void startMedia() {
        if (player != null)
            player.start();
    }
}
