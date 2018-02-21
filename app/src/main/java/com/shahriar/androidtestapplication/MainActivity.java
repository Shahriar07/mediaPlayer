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
    Button play_pause_button;
    SeekBar seek_bar;
    MediaPlayer player;
    TextView current_time;
    TextView end_time;
    Handler seekHandler = new Handler();
    Utility utility = new Utility();
    /**
     * Called when the activity is first created.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getInit();
//        seekUpdation();
    }

    public void getInit() {
        seek_bar = (SeekBar) findViewById(R.id.seek_bar);
        play_pause_button = (Button) findViewById(R.id.startButton);
        play_pause_button.setOnClickListener(this);
        player = MediaPlayer.create(this, R.raw.small_town_boy);
        player.setOnCompletionListener(this);
        current_time = (TextView) findViewById(R.id.audio_current_time_text);
        end_time = (TextView) findViewById(R.id.audio_max_time_text);
        seek_bar.setMax(player.getDuration());
        end_time.setText(utility.getFormatedTimeFromMilisecond(player.getDuration()));
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
            case R.id.reset_loop:{

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