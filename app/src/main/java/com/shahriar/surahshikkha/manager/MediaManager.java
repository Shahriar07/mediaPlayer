package com.shahriar.surahshikkha.manager;

import android.content.Context;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.util.Log;

import com.shahriar.surahshikkha.Interfaces.AudioControllerListener;

/**
 * Created by H. M. Shahriar on 8/16/2018.
 */
public class MediaManager implements AudioManager.OnAudioFocusChangeListener, MediaPlayer.OnCompletionListener {
    // Media player
    MediaPlayer player;
    private AudioManager mAudioManager;
    int mediaDuration;
    private boolean isActivityForsedPaused;
    AudioControllerListener audioControllerListener;
    int surahNumber;

    public MediaManager(Context context, int resourceID, AudioControllerListener audioControllerListener, int surahNumber)
{
    player = MediaPlayer.create(context, resourceID);
    if (player != null) {
        player.setOnCompletionListener(this);
        mAudioManager = (AudioManager) context.getSystemService(Context.AUDIO_SERVICE);
        mAudioManager.requestAudioFocus(this, AudioManager.STREAM_MUSIC, AudioManager.AUDIOFOCUS_GAIN);
        mediaDuration = player.getDuration();
    }
    this.audioControllerListener = audioControllerListener;
    this.surahNumber = surahNumber;
}

    @Override
    public void onAudioFocusChange(int focusChange) {
        if(focusChange<=0) { // Focus losses
            Log.i(getClass().getSimpleName(),"OnStop");
            if (player.isPlaying()){
                player.pause();
                isActivityForsedPaused = true;
            }
            if (audioControllerListener != null)
            {
                audioControllerListener.audioPaused(surahNumber);
            }
        } else { // Focus gained
            if (isActivityForsedPaused){
                player.start();
                isActivityForsedPaused = false;
                if (audioControllerListener != null)
                {
                    audioControllerListener.audioStarted(surahNumber);
                }
            }
        }
    }

    @Override
    public void onCompletion(MediaPlayer mp) {
        if (audioControllerListener != null)
        {
            audioControllerListener.audioFinished(surahNumber);
        }
    }

    public boolean isMediaPlaying() {
        if (player != null)
            return player.isPlaying();
        else
            return false;
    }

    public void pauseMedia() {
        if (player != null){
            player.pause();
            if (audioControllerListener != null) {
                audioControllerListener.audioPaused(surahNumber);
            }
        }

    }

    public void stopMedia() {
        if (player != null){
            player.stop();
            if (audioControllerListener != null) {
                audioControllerListener.audioFinished(surahNumber);
            }
        }

    }

    public void startMedia() {
        if (player != null) {
            player.start();
            if (audioControllerListener != null) {
                audioControllerListener.audioStarted(surahNumber);
            }
        }
    }

    public void release() {
        player.release();
        mAudioManager.abandonAudioFocus(this);
    }

    public int getMediaDuration() {
        return mediaDuration;
    }

    public int getCurrentPosition()
    {
        if (player != null)
            return player.getCurrentPosition();
        return 0;
    }
}
