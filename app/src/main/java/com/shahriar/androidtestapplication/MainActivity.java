package com.shahriar.androidtestapplication;

import android.content.Intent;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;

import com.shahriar.androidtestapplication.Adapter.SurahAdapter;
import com.shahriar.androidtestapplication.Data.Surah;
import com.shahriar.androidtestapplication.Data.Verse;
import com.shahriar.androidtestapplication.Utility.Utility;

import java.util.ArrayList;

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
    int loopCount = 0;
    int maxLoopCount = 2;
    int currentLoopIndex = 0;
    int durationArray[];

    private RecyclerView.LayoutManager mLayoutManager;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView verseList;
    /**
     * Called when the activity is first created.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getInit();
        durationArray = DurationConstants.surah_al_balad;
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

        player = MediaPlayer.create(this, R.raw.surah_al_balad_90);
        player.setOnCompletionListener(this);
//        player.setLooping(true);
        current_time = (TextView) findViewById(R.id.audio_current_time_text);
        end_time = (TextView) findViewById(R.id.audio_max_time_text);
        mediaDuration = player.getDuration();
        seek_bar.setMax(mediaDuration);
        loopEndTime = mediaDuration;
        end_time.setText(utility.getFormatedTimeFromMilisecond(mediaDuration));
        seek_bar.setOnSeekBarChangeListener(seekBarChangeListener);

        verseList = (RecyclerView) findViewById(R.id.listView);
        mLayoutManager = new LinearLayoutManager(this);
        verseList.setLayoutManager(mLayoutManager);
        mAdapter = new SurahAdapter(prepareSurah());
        verseList.setAdapter(mAdapter);
    }


    private Surah prepareSurah(){
        Verse v = new Verse(1,"Verse in arabic","Verse in bangla");
        Verse v1 = new Verse(2,"Verse in arabic","Verse in bangla");
        Verse v2 = new Verse(3,"Verse in arabic","Verse in bangla");
        Verse v3 = new Verse(4,"Verse in arabic","Verse in bangla");
        Verse v4 = new Verse(5,"Verse in arabic","Verse in bangla");
        Verse v5 = new Verse(6,"Verse in arabic","Verse in bangla");
        Verse v6 = new Verse(7,"Verse in arabic","Verse in bangla");
        Verse v7 = new Verse(8,"Verse in arabic","Verse in bangla");
        Verse v8 = new Verse(9,"Verse in arabic","Verse in bangla");


        ArrayList<Verse> verseArrayList = new ArrayList<>();
        verseArrayList.add(v);
        verseArrayList.add(v1);
        verseArrayList.add(v2);
        verseArrayList.add(v3);
        verseArrayList.add(v4);
        verseArrayList.add(v5);
        verseArrayList.add(v6);
        verseArrayList.add(v7);
        verseArrayList.add(v8);
        Surah surah = new Surah("Surah-Al-Balad",verseArrayList,90,true);
        return surah;
    }

    SeekBar.OnSeekBarChangeListener seekBarChangeListener = new SeekBar.OnSeekBarChangeListener() {
        @Override
        public void onProgressChanged(SeekBar seekBar, int i, boolean inputFromUser) {
            current_time.setText(utility.getFormatedTimeFromMilisecond(i));
            if(inputFromUser){
                currentLoopIndex = utility.getIndexForLoop(i,durationArray);
                if (currentLoopIndex == 0) {
                    loopStartTime = durationArray[0];
                    loopEndTime = durationArray[1];
                }
                else {
                    loopStartTime = durationArray[currentLoopIndex];
                    loopEndTime = durationArray[currentLoopIndex+1];
                }
                loopCount = 0;
                player.seekTo(loopStartTime);

                Log.d(getLocalClassName(),"LoopIndex " + currentLoopIndex + " loopStartTime " +loopStartTime + " LoopEndTime " + loopEndTime);
            }
        }

        @Override
        public void onStartTrackingTouch(SeekBar seekBar) {

        }

        @Override
        public void onStopTrackingTouch(SeekBar seekBar) {

        }
    };
    void setNextLoop(){
        loopCount = 0;
        ++currentLoopIndex;
        if (currentLoopIndex == (durationArray.length -1)) {
            loopStartTime = durationArray[0];
            loopEndTime = durationArray[durationArray.length - 1];
            Log.d(getClass().getSimpleName(),"Set Next Loop in last index with start time " + loopStartTime + " End time "+ loopEndTime);
        }
        else{
            loopStartTime = durationArray[currentLoopIndex];
            loopEndTime = durationArray[currentLoopIndex + 1];
            Log.d(getClass().getSimpleName(),"Set Next Loop with start time " + loopStartTime + " End time "+ loopEndTime);
        }

        Log.d(getClass().getSimpleName(),"Set Next Loop with currentLoopIndex" + currentLoopIndex);
    }
    Runnable run = new Runnable() {
        @Override
        public void run() {
            if (loopCount == maxLoopCount){
                Log.d(getClass().getSimpleName(),"Set Next Loop Count");
                setNextLoop();
            }

            if (loopEndTime < mediaDuration && player.getCurrentPosition() >= loopEndTime && loopCount < maxLoopCount ){
                player.seekTo(loopStartTime);
                Log.d(getClass().getSimpleName(),"Loop Count ++ " + loopCount);
                ++loopCount;
            }

            seekUpdation();
        }
    };

    public void seekUpdation() {
        seek_bar.setProgress(player.getCurrentPosition());
        current_time.setText(utility.getFormatedTimeFromMilisecond(player.getCurrentPosition()));
        seekHandler.postDelayed(run, 300);
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
                loopCount = 0;
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
//        if (loopCount == maxLoopCount){
            changePlayPauseButton();
//        }
//        else {
//            seekUpdation();
//            player.seekTo(loopStartTime);
//            mediaPlayer.start();
//        }

    }
}