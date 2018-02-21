package com.shahriar.androidtestapplication;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;

import com.shahriar.androidtestapplication.Utility.Utility;

public class MainActivity extends AppCompatActivity implements OnClickListener, MediaPlayer.OnCompletionListener {
    // Media Control Buttons
    Button play_pause_button;
    Button loop_start_button;
    Button loop_end_button;
    Button loop_reset_button;

    // Media player informations
    SeekBar seek_bar;
    TextView current_time;
    TextView end_time;

    // Media player
    MediaPlayer player;

    Handler seekHandler = new Handler();
    Utility utility = new Utility();
    int loopStartTime = 0;
    int loopEndTime = 0;
    int mediaDuration = 0;
    /**
     * Called when the activity is first created.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getInit();
    }

    public void getInit() {
        seek_bar = (SeekBar) findViewById(R.id.seek_bar);
        play_pause_button = (Button) findViewById(R.id.startButton);
        play_pause_button.setOnClickListener(this);

        loop_start_button = (Button) findViewById(R.id.start_loop);
        loop_start_button.setOnClickListener(this);

        loop_end_button = (Button) findViewById(R.id.end_loop);
        loop_end_button.setOnClickListener(this);

        loop_reset_button = (Button) findViewById(R.id.reset_loop);
        loop_reset_button.setOnClickListener(this);

        player = MediaPlayer.create(this, R.raw.surah_al_fatiha);
        player.setOnCompletionListener(this);
        current_time = (TextView) findViewById(R.id.audio_current_time_text);
        end_time = (TextView) findViewById(R.id.audio_max_time_text);
        mediaDuration = player.getDuration();
        seek_bar.setMax(mediaDuration);
        loopEndTime = mediaDuration;
        end_time.setText(utility.getFormatedTimeFromMilisecond(mediaDuration));
        seek_bar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean inputFromUser) {
                if(inputFromUser){
                    player.seekTo(i);
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
    }

    Runnable run = new Runnable() {
        @Override
        public void run() {
            if (loopEndTime < mediaDuration && player.getCurrentPosition() >= loopEndTime){
                player.seekTo(loopStartTime);
            }
            seekUpdation();
        }
    };

    public void seekUpdation() {
        seek_bar.setProgress(player.getCurrentPosition());
        current_time.setText(utility.getFormatedTimeFromMilisecond(player.getCurrentPosition()));
        seekHandler.postDelayed(run, 500);
    }

    void changePlayPauseButton(){
        if(getString(R.string.start_button).equals(play_pause_button.getText())){
            play_pause_button.setText(R.string.pause_button);
        }
        else {
            play_pause_button.setText(R.string.start_button);
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.startButton:{
                if (player.isPlaying()){
                    player.pause();
                }
                else {
                    player.start();
                    seekUpdation();
                }
                changePlayPauseButton();
                break;
            }
            case R.id.start_loop:{
                loopStartTime = player.getCurrentPosition();
                break;
            }
            case R.id.end_loop:{
                loopEndTime = player.getCurrentPosition();
                break;
            }
            case R.id.reset_loop:{
                loopStartTime = 0;
                loopEndTime = mediaDuration;
                break;
            }
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        player.release();
        seekHandler.removeCallbacks(run);
    }

    @Override
    public void onCompletion(MediaPlayer mediaPlayer) {
        changePlayPauseButton();
    }
}