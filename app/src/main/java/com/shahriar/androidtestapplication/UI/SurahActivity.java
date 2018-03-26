package com.shahriar.androidtestapplication.UI;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.shahriar.androidtestapplication.Adapter.SurahAdapter;
import com.shahriar.androidtestapplication.CustomComponents.CustomSpinner;
import com.shahriar.androidtestapplication.Data.Surah;
import com.shahriar.androidtestapplication.Data.Verse;
import com.shahriar.androidtestapplication.Factory.SurahFactory;
import com.shahriar.androidtestapplication.Interfaces.OnRecycleViewClicked;
import com.shahriar.androidtestapplication.LayoutManager.ScrollingLinearLayoutManager;
import com.shahriar.androidtestapplication.Listeners.VerseTouchListener;
import com.shahriar.androidtestapplication.R;
import com.shahriar.androidtestapplication.Utility.Constants;
import com.shahriar.androidtestapplication.Utility.Utility;

/**
 * Created by H. M. Shahriar on 2/21/2018.
 */


public class SurahActivity extends AppCompatActivity implements OnClickListener, MediaPlayer.OnCompletionListener {
    // Media Controllers
    ImageButton play_pause_button;
    Button loop_start_button;
    Button loop_end_button;
    ImageButton loop_reset_button;

    // Media player informations
    SeekBar seek_bar;
    TextView current_time;
    TextView end_time;

    // Media player
    MediaPlayer player;
    Surah surah;
    int surahNo = 114;
    Handler seekHandler;
    Utility utility;

    int loopStartTime = 0;
    int loopEndTime = 0;
    int mediaDuration = 0;
    int loopCount = 0;
    int maxLoopCount = 4;
    int currentLoopIndex = 0;
    int durationArray[];
    boolean isActivityInitialized = false; // As the spinners set the initial items, Surah should not start at that time.

    // list to show verses
    private RecyclerView.LayoutManager mLayoutManager;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView verseListView;

    // Spinner to select loop start and loop end verse
    CustomSpinner startSpinner;
    CustomSpinner endSpinner;


    // Need to register Surah in factory
//    static
//    {
//        try
//        {
//            Log.d("SurahActivity","Static block called");
//            Class.forName("com.shahriar.androidtestapplication.Data.SurahAlBalad"); //This call will execute register of surah in surahfactory
//            Class.forName("com.shahriar.androidtestapplication.Data.SurahAnNas");
//        }
//        catch (ClassNotFoundException any)
//        {
//            any.printStackTrace();
//        }
//    }

    /**
     * Called when the activity is first created.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.surah_activity_layout);
        surahNo = getIntent().getIntExtra(Constants.SURAH_ACTIVITY_SURAH_NO,114);
        Log.d(getLocalClassName(),"Surah number "+ surahNo);

        getInit();
    }

    public void getInit() {
        seekHandler = new Handler();
        utility = new Utility();

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        ActionBar actionbar = getSupportActionBar();
        actionbar.setDisplayHomeAsUpEnabled(true);
        actionbar.setHomeAsUpIndicator(R.drawable.arrow_back);

        seek_bar = (SeekBar) findViewById(R.id.seek_bar);
        play_pause_button = (ImageButton) findViewById(R.id.startButton);
        play_pause_button.setOnClickListener(this);
        play_pause_button.setTag(R.drawable.play);

        surah = SurahFactory.getInstance().prepareSurah(""+surahNo);
        setTitle(surah.getSurahName());

        startSpinner = (CustomSpinner) findViewById(R.id.start_loop);
        ArrayAdapter<Integer> adapter = new ArrayAdapter<Integer>(this,android.R.layout.simple_spinner_item,utility.getIntArray(0,surah.getVerseCount()));
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        startSpinner.setAdapter(adapter);
        startSpinner.setOnItemSelectedListener(startItemSelectedListener);

//        loop_start_button = (Button) findViewById(R.id.start_loop);
//        loop_start_button.setOnClickListener(this);

//        loop_end_button = (Button) findViewById(R.id.end_loop);
//        loop_end_button.setOnClickListener(this);

        endSpinner = (CustomSpinner) findViewById(R.id.end_loop);
        ArrayAdapter<Integer> endSpinnerAdapter = new ArrayAdapter<Integer>(this,android.R.layout.simple_spinner_item,utility.getIntArray(surah.getVerseCount(),0));
        endSpinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        endSpinner.setAdapter(endSpinnerAdapter);
        endSpinner.setOnItemSelectedListener(endItemSelectedListener);

        loop_reset_button = (ImageButton) findViewById(R.id.reset_loop);
        loop_reset_button.setOnClickListener(this);

        player = MediaPlayer.create(this, surah.getResourceId());
        player.setOnCompletionListener(this);
//        player.setLooping(true);
        current_time = (TextView) findViewById(R.id.audio_current_time_text);
        end_time = (TextView) findViewById(R.id.audio_max_time_text);
        mediaDuration = player.getDuration();
        seek_bar.setMax(mediaDuration);
        loopEndTime = mediaDuration;
        end_time.setText(utility.getFormatedTimeFromMilisecond(mediaDuration));
        seek_bar.setOnSeekBarChangeListener(seekBarChangeListener);

        verseListView = (RecyclerView) findViewById(R.id.listView);
        mLayoutManager = new ScrollingLinearLayoutManager(this,5);
        verseListView.setLayoutManager(mLayoutManager);

        durationArray = surah.getDuration();
        mAdapter = new SurahAdapter(surah,this);
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(this, LinearLayoutManager.VERTICAL);
        dividerItemDecoration.setDrawable(this.getResources().getDrawable(R.drawable.divider_item_decoration));
        verseListView.addItemDecoration(dividerItemDecoration);

        verseListView.setAdapter(mAdapter);

        verseListView.addOnItemTouchListener(new VerseTouchListener(getApplicationContext(), verseListView, new OnRecycleViewClicked(){
            @Override
            public void onClick(View view, int position) {
                isActivityInitialized = true;
                Verse verse = surah.getVerses().get(position);
                Toast.makeText(getApplicationContext(), verse.getVerseNo() + " is selected!", Toast.LENGTH_SHORT).show();
                setLoopWhenVerseClicked(position);
            }

            @Override
            public void onLongClick(View view, int position) {

            }
        }));

    }

    SeekBar.OnSeekBarChangeListener seekBarChangeListener = new SeekBar.OnSeekBarChangeListener() {
        @Override
        public void onProgressChanged(SeekBar seekBar, int i, boolean inputFromUser) {
            if(inputFromUser){
                isActivityInitialized = true;
                int index = utility.getIndexForLoop(i,durationArray);
                setLoopWhenVerseClicked(index);
            }
        }

        @Override
        public void onStartTrackingTouch(SeekBar seekBar) {

        }

        @Override
        public void onStopTrackingTouch(SeekBar seekBar) {

        }
    };

    void setLoopWhenVerseClicked(int index){
        current_time.setText(utility.getFormatedTimeFromMilisecond(durationArray[index]));
        currentLoopIndex = index;
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
        if (!player.isPlaying()){
            player.start();
            seekUpdation();
            changePlayPauseButton();
        }
        Log.d(getLocalClassName(),"LoopIndex " + currentLoopIndex + " loopStartTime " +loopStartTime + " LoopEndTime " + loopEndTime);
    }

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
            if (maxLoopCount > 0) {
                if (loopCount == maxLoopCount) {
                    Log.d(getClass().getSimpleName(), "Set Next Loop Count");
                    setNextLoop();
                }

                if (loopEndTime < mediaDuration && player.getCurrentPosition() >= loopEndTime && loopCount < maxLoopCount) {
                    player.seekTo(loopStartTime);
                    Log.d(getClass().getSimpleName(), "Loop Count ++ " + loopCount);
                    ++loopCount;
                }
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
        if ((int)play_pause_button.getTag() == R.drawable.play){
            play_pause_button.setImageResource(R.drawable.pause);
            play_pause_button.setTag(R.drawable.pause);
        }
        else {
            play_pause_button.setImageResource(R.drawable.play);
            play_pause_button.setTag(R.drawable.play);
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.startButton:{
                isActivityInitialized = true;
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

    AdapterView.OnItemSelectedListener startItemSelectedListener = new AdapterView.OnItemSelectedListener() {
        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
            setLoopWhenStartVerseIndexSelected(position);
        }

        @Override
        public void onNothingSelected(AdapterView<?> parent) {

        }
    };

    public void setLoopWhenStartVerseIndexSelected(int index){
        current_time.setText(utility.getFormatedTimeFromMilisecond(durationArray[index]));
        currentLoopIndex = index;
        loopStartTime = durationArray[currentLoopIndex];
        loopCount = 0;
        player.seekTo(loopStartTime);
        if (!player.isPlaying() && isActivityInitialized){
            player.start();
            seekUpdation();
            changePlayPauseButton();
        }
        Log.d(getLocalClassName(),"LoopIndex " + currentLoopIndex + " loopStartTime " +loopStartTime);
    }

    AdapterView.OnItemSelectedListener endItemSelectedListener = new AdapterView.OnItemSelectedListener() {
        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
            Log.d(getLocalClassName(),"Position " + position + " ID "+parent.getItemAtPosition(position));
            setLoopWhenEndVerseIndexSelected(Integer.parseInt(parent.getItemAtPosition(position).toString()) + 1);
        }

        @Override
        public void onNothingSelected(AdapterView<?> parent) {

        }
    };

    public void setLoopWhenEndVerseIndexSelected(int index){
        loopEndTime = durationArray[index];
        if (!player.isPlaying() && isActivityInitialized){
            player.start();
            seekUpdation();
            changePlayPauseButton();
        }
        Log.d(getLocalClassName(),"LoopIndex " + index + " LoopEndTime " +loopEndTime);
    }
}
