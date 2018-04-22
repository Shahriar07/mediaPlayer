package com.shahriar.surahshikkha.UI;

import android.content.Context;
import android.content.DialogInterface;
import android.media.AudioManager;
import android.media.Image;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.shahriar.surahshikkha.Adapter.SurahAdapter;
import com.shahriar.surahshikkha.CustomComponents.CustomSpinner;
import com.shahriar.surahshikkha.Data.Surah;
import com.shahriar.surahshikkha.Data.Verse;
import com.shahriar.surahshikkha.Factory.SurahFactory;
import com.shahriar.surahshikkha.Interfaces.OnRecycleViewClicked;
import com.shahriar.surahshikkha.LayoutManager.ScrollingLinearLayoutManager;
import com.shahriar.surahshikkha.Listeners.VerseTouchListener;
import com.shahriar.surahshikkha.R;
import com.shahriar.surahshikkha.Utility.Constants;
import com.shahriar.surahshikkha.Utility.LocaleManager;
import com.shahriar.surahshikkha.Utility.SharedPreferenceController;
import com.shahriar.surahshikkha.Utility.Utility;

import java.text.NumberFormat;
import java.util.Locale;

/**
 * Created by H. M. Shahriar on 2/21/2018.
 */


public class SurahActivity extends AppCompatActivity implements OnClickListener, MediaPlayer.OnCompletionListener, AudioManager.OnAudioFocusChangeListener {
    // Media Controllers
    ImageButton play_pause_button;
    ImageButton loop_reset_button;

    // Media player informations
    SeekBar seek_bar;
    TextView current_time;
    TextView end_time;

    // Media player
    MediaPlayer player;
    private AudioManager mAudioManager;
    Surah surah;
    int surahNo = 114;
    Handler seekHandler;
    Utility utility;

    int loopStartTime = 0;
    int loopEndTime = 0;
    int mediaDuration = 0;
    int loopCount = 1;

    int currentLoopIndex = 0;
    int durationArray[];

    int maxLoopCount = 2;
    boolean isActivityInitialized = false; // As the spinners set the initial items, Surah should not start at that time.
    boolean isScrollEnabled = true; // This value should set from shared preference
    boolean isActivityForsedPaused = false; // Need to set when some other application stops the player. we shall resume after activity resumes
    int currentScrollIndex = 0;
    int currentSelectedIndex = 0;

    // list to show verses
    private RecyclerView.LayoutManager mLayoutManager;
    private SurahAdapter mAdapter;
    private RecyclerView verseListView;

    // Spinner to select loop start and loop end verse
    CustomSpinner startSpinner;
    CustomSpinner endSpinner;


    final SharedPreferenceController controller = new SharedPreferenceController(this);
    Locale currentLocale=Locale.ENGLISH;

    TextView maxRepeatController;

    /**
     * Called when the activity is first created.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.surah_activity_layout);
        surahNo = getIntent().getIntExtra(Constants.SURAH_ACTIVITY_SURAH_NO,114);
        Log.d(getLocalClassName(),"Surah number "+ surahNo);
        initializeComponents();
        setMaxLoopCountFromSharedPreference();
        setAutoScrollFromSharedPreference();
    }

    private void setMaxLoopCountFromSharedPreference(){
        SharedPreferenceController controller = new SharedPreferenceController(this);
        maxLoopCount = controller.readIntWithKey(Constants.SURAH_VERSE_MAX_REPEAT_COUNT);
        if (maxLoopCount == -1){
            maxLoopCount = Constants.SURAH_VERSE_MAX_REPEAT_COUNT_DEFAULT;
            controller.writeIntWithKey(Constants.SURAH_VERSE_MAX_REPEAT_COUNT,maxLoopCount);
        }
    }

    private void setAutoScrollFromSharedPreference(){
        SharedPreferenceController controller = new SharedPreferenceController(this);
        isScrollEnabled = controller.readBooleanWithKey(Constants.SURAH_VERSE_AUTO_SCROLL);
    }

    private void updateAutoScrollToSharedPreference(boolean isAutoScrollEnabled){
        SharedPreferenceController controller = new SharedPreferenceController(this);
        controller.writeBooleanWithKey(Constants.SURAH_VERSE_AUTO_SCROLL, isAutoScrollEnabled);
        isScrollEnabled = isAutoScrollEnabled;
    }

    public void initializeComponents() {
        seekHandler = new Handler();
        utility = new Utility();

        Toolbar toolbar = findViewById(R.id.surahActivityToolbar);
        setSupportActionBar(toolbar);

        ActionBar actionbar = getSupportActionBar();
        actionbar.setDisplayOptions(ActionBar.DISPLAY_SHOW_HOME| ActionBar.DISPLAY_SHOW_TITLE | ActionBar.DISPLAY_SHOW_CUSTOM);
        actionbar.setDisplayHomeAsUpEnabled(true);
        actionbar.setHomeAsUpIndicator(R.drawable.arrow_back);

        seek_bar = (SeekBar) findViewById(R.id.seek_bar);
        play_pause_button = (ImageButton) findViewById(R.id.startButton);
        play_pause_button.setOnClickListener(this);
        play_pause_button.setTag(R.drawable.play);

        surah = SurahFactory.getInstance(this).prepareSurah(""+surahNo);
        setTitle(surah.getSurahName());

        startSpinner = (CustomSpinner) findViewById(R.id.start_loop);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,R.layout.spinner_item_layout,utility.getStringArray(0,surah.getVerseCount(),false));
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        startSpinner.setAdapter(adapter);
        startSpinner.setOnItemSelectedListener(startItemSelectedListener);

        endSpinner = (CustomSpinner) findViewById(R.id.end_loop);
        ArrayAdapter<String> endSpinnerAdapter = new ArrayAdapter<String>(this,R.layout.spinner_item_layout,utility.getStringArray(0,surah.getVerseCount(),false));
        endSpinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        endSpinner.setAdapter(endSpinnerAdapter);
        endSpinner.setSelection(endSpinnerAdapter.getCount()-1);
        endSpinner.setOnItemSelectedListener(endItemSelectedListener);

        loop_reset_button = (ImageButton) findViewById(R.id.reset_loop);
        loop_reset_button.setOnClickListener(this);

        currentLocale = Utility.getCurrentLocale(null);

        player = MediaPlayer.create(this, surah.getResourceId());
        player.setOnCompletionListener(this);
        mAudioManager = (AudioManager) getSystemService(Context.AUDIO_SERVICE);
        mAudioManager.requestAudioFocus(this, AudioManager.STREAM_MUSIC, AudioManager.AUDIOFOCUS_GAIN);

        current_time = (TextView) findViewById(R.id.audio_current_time_text);
        end_time = (TextView) findViewById(R.id.audio_max_time_text);
        mediaDuration = player.getDuration();
        Log.d(getClass().getSimpleName(), " Media duration is " + mediaDuration);
        seek_bar.setMax(mediaDuration);
        loopEndTime = mediaDuration;
        end_time.setText(utility.getFormatedTimeFromMilisecond(mediaDuration, currentLocale));
        seek_bar.setOnSeekBarChangeListener(seekBarChangeListener);

        verseListView = (RecyclerView) findViewById(R.id.listView);
        mLayoutManager = new ScrollingLinearLayoutManager(this,1);
        verseListView.setLayoutManager(mLayoutManager);

        durationArray = surah.getDurationList();
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
                startSpinner.setSelection(position);
                endSpinner.setSelection(position);
                scrollListToPosition(position);
                setCurrentSelectedIndex(position);
            }

            @Override
            public void onLongClick(View view, int position) {

            }
        }));

    }


    /*
     * Scroll List to position
     */
    private  void scrollListToPosition(int index){
        mLayoutManager.scrollToPosition(index);
        currentScrollIndex = index;
    }


    /*
     * Set Selected item and change background
     */
    private  void setCurrentSelectedIndex(int index){
        currentSelectedIndex = index;
        mAdapter.onItemChanged(index);
    }

    SeekBar.OnSeekBarChangeListener seekBarChangeListener = new SeekBar.OnSeekBarChangeListener() {
        @Override
        public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
            if(fromUser){
                isActivityInitialized = true;
                int index = utility.getIndexForLoop(progress,durationArray);
                startSpinner.setSelection(index);
                endSpinner.setSelection(index);
                scrollListToPosition(index);
                setCurrentSelectedIndex(index);
            }
        }

        @Override
        public void onStartTrackingTouch(SeekBar seekBar) {

        }

        @Override
        public void onStopTrackingTouch(SeekBar seekBar) {

        }
    };

    /*
     * Select only one verse
     * Set start position as index and end position as index + 1
     */
    void setLoopWhenVerseClicked(int index){
        current_time.setText(utility.getFormatedTimeFromMilisecond(durationArray[index],currentLocale));
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

    /*
    * 1. if verse 2 is running set verse 3
    * 2. if verse 2-4 is running set verse 5
    * 3. if verse 2-4 is the last set 0-4
    *
     */
    void setNextLoop(){
        Log.i(getClass().getSimpleName(),"Set next loop with currentLocale index " + currentLoopIndex);
        loopCount = 1;
        ++currentLoopIndex;
        // 3. if verse 2-4 is the last set 0-4
        if (currentLoopIndex == (durationArray.length -1)) {
            loopStartTime = durationArray[0];
            loopEndTime = durationArray[durationArray.length - 1];
            scrollListToPosition(0);
            setCurrentSelectedIndex(0);
            Log.d(getClass().getSimpleName(),"Set Next Loop in last index with start time " + loopStartTime + " End time "+ loopEndTime);
        }
        else{
            // 1. if verse 2 is running set verse 3
            loopStartTime = durationArray[currentLoopIndex];
            if (loopStartTime < loopEndTime){
                loopStartTime = loopEndTime;
                currentLoopIndex = utility.getIndexForLoop(loopStartTime,durationArray);
            }
            loopEndTime = durationArray[currentLoopIndex + 1];
            Log.d(getClass().getSimpleName(),"Set Next Loop with start time " + loopStartTime + " End time "+ loopEndTime);
            scrollListToPosition(currentLoopIndex);
            setCurrentSelectedIndex(currentLoopIndex);
        }

        Log.d(getClass().getSimpleName(),"Set Next Loop with currentLoopIndex" + currentLoopIndex);
    }
    Runnable run = new Runnable() {
        @Override
        public void run() {
            boolean isRepeatOn = controller.readBooleanWithKey(Constants.SURAH_VERSE_REPEAT_CONTROL);
            if (maxLoopCount > 0 && isRepeatOn) {
                // This is the last play of loop Need to set the next loop
                if (loopCount == maxLoopCount) {
                    Log.d(getClass().getSimpleName(), "Set Next Loop Count");
                    setNextLoop();
                }

                // If player continue playing and there is a loop set, go to the start position and play again
                if (loopEndTime < mediaDuration && player.getCurrentPosition() >= loopEndTime && loopCount < maxLoopCount) {
                    player.seekTo(loopStartTime);
                    Log.d(getClass().getSimpleName(), "Loop Count ++ " + loopCount);
                    ++loopCount;
                }
            }
            if (player.isPlaying()){
                seekUpdation();
            }

        }
    };

    public void seekUpdation() {
        int currentTime = player.getCurrentPosition();
        Log.i(getClass().getSimpleName(), "seekUpdation Current player time " + currentTime);
        seek_bar.setProgress(currentTime);
        current_time.setText(utility.getFormatedTimeFromMilisecond(currentTime,currentLocale));
        int index = utility.getIndexForLoop(currentTime,durationArray);
        if (index != currentScrollIndex) {
            scrollListToPosition(index);
            setCurrentSelectedIndex(index);
        }
        seekHandler.postDelayed(run, 250);
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
            case R.id.reset_loop:{
                loopStartTime = 0;
                loopEndTime = mediaDuration;
                loopCount = 1;
                break;
            }
        }
    }

    @Override
    public void onCompletion(MediaPlayer mediaPlayer) {
        Toast.makeText(getApplicationContext(), " On Completion called", Toast.LENGTH_SHORT).show();
        if (!mediaPlayer.isPlaying())
            changePlayPauseButton();
    }

    AdapterView.OnItemSelectedListener startItemSelectedListener = new AdapterView.OnItemSelectedListener() {
        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
            Log.d(SurahActivity.this.getClass().getSimpleName(),"Start ID is "+ id);
                setLoopWhenStartVerseIndexSelected(Integer.parseInt(parent.getSelectedItem().toString()));
        }

        @Override
        public void onNothingSelected(AdapterView<?> parent) {

        }
    };

    /*
     * Select only the starting verse
     * Set start position as index and end position will remain unchanged
     */
    public void setLoopWhenStartVerseIndexSelected(int index){
        current_time.setText(utility.getFormatedTimeFromMilisecond(durationArray[index],currentLocale));
        currentLoopIndex = index;
        loopStartTime = durationArray[currentLoopIndex];
        loopCount = 1;
        player.seekTo(loopStartTime);
        scrollListToPosition(index);
        setCurrentSelectedIndex(index);
        seek_bar.setProgress(loopStartTime);
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
            setLoopWhenEndVerseIndexSelected(Integer.parseInt(parent.getItemAtPosition(position).toString())+1);
        }

        @Override
        public void onNothingSelected(AdapterView<?> parent) {

        }
    };

    public void setLoopWhenEndVerseIndexSelected(int index){
        if (durationArray[index]<loopStartTime){
            // Show a dialog to choose large number than the start index
            Toast.makeText(getApplicationContext(), " Choose a large index than start", Toast.LENGTH_SHORT).show();
            loopEndTime = durationArray[currentLoopIndex];
            endSpinner.setSelection(currentLoopIndex);
        }
        else {
            loopEndTime = durationArray[index];
        }
        if (!player.isPlaying() && isActivityInitialized) {
            player.start();
            seekUpdation();
            changePlayPauseButton();
        }

        Log.d(getLocalClassName(),"LoopIndex " + index + " LoopEndTime " +loopEndTime);
    }

    /*
     * Create and set menu items for action bar
     */
    @Override
    public boolean onCreateOptionsMenu(android.view.Menu menu) {
        android.view.MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.surah_activity_action_bar_items, menu);
        MenuItem repeatItem = menu.findItem(R.id.action_repeat_control);
        boolean isRepeatOn = controller.readBooleanWithKey(Constants.SURAH_VERSE_REPEAT_CONTROL);
        setRepeatIcon(isRepeatOn,repeatItem);

        final MenuItem repeatCount = menu.findItem(R.id.action_max_repeat_count);
        RelativeLayout rootView = (RelativeLayout)repeatCount.getActionView();

        ImageButton button = (ImageButton)rootView.findViewById(R.id.actionBarRepeatCountIcon);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onOptionsItemSelected(repeatCount);
            }
        });
        maxRepeatController = (TextView) rootView.findViewById(R.id.actionBarRepeatCountText);
        SharedPreferenceController controller = new SharedPreferenceController(this);
        maxRepeatController.setText(""+controller.readIntWithKey(Constants.SURAH_VERSE_MAX_REPEAT_COUNT));
        maxRepeatController.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onOptionsItemSelected(repeatCount);
            }
        });
        rootView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onOptionsItemSelected(repeatCount);
            }
        });
//        repeatCount.setActionView(R.layout.surah_activity_menu_repeat_count);
//        repeatCount.expandActionView();


        return true;
    }

    @Override
    public boolean onOptionsItemSelected(android.view.MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_max_repeat_count:
                showMaxLoopCountPopup();
                return true;

            case R.id.action_repeat_control:
                boolean isRepeatOn = controller.readBooleanWithKey(Constants.SURAH_VERSE_REPEAT_CONTROL);
                updateRepeatStateandImage(isRepeatOn, item);
                return true;

            default:
                // If we got here, the user's action was not recognized.
                // Invoke the superclass to handle it.
                return super.onOptionsItemSelected(item);

        }
    }

    /*
     * Update action menu bar icon
     */
    private void setRepeatIcon(boolean isOn, android.view.MenuItem item){
        if (isOn){
            item.setIcon(R.drawable.ic_repeat_white_24dp);
        }
        else {
            item.setIcon(R.drawable.repeat);
        }
    }

    /*
     * Update Repeat state in preference and change action menu bar icon
     */
    private void updateRepeatStateandImage(boolean isOn, android.view.MenuItem item){
        controller.writeBooleanWithKey(Constants.SURAH_VERSE_REPEAT_CONTROL,!isOn);
        setRepeatIcon(!isOn,item);
    }

    private void showMaxLoopCountPopup() {
        AlertDialog.Builder builderSingle = new AlertDialog.Builder(this, R.style.MyDialogTheme);
        builderSingle.setTitle(getString(R.string.max_repeat_count));
        final ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_selectable_list_item);
        for (int i = Constants.SURAH_VERSE_MIN_REPEAT_COUNT_NUMBER; i<= Constants.SURAH_VERSE_MAX_REPEAT_COUNT_NUMBER; i++) {
            arrayAdapter.add(NumberFormat.getInstance().format(i));
        }
        builderSingle.setNegativeButton(getString(R.string.cancel), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        final SharedPreferenceController controller = new SharedPreferenceController(this);
        builderSingle.setAdapter(arrayAdapter, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                int newMaxRepeatCount = Integer.parseInt(arrayAdapter.getItem(which));
                controller.writeIntWithKey(Constants.SURAH_VERSE_MAX_REPEAT_COUNT,newMaxRepeatCount);
                maxLoopCount = newMaxRepeatCount;
                maxRepeatController.setText(Integer.toString(newMaxRepeatCount));
            }
        });
        builderSingle.create().show();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        player.release();
        seekHandler.removeCallbacks(run);
        mAudioManager.abandonAudioFocus(this);
    }

    /*
     * this function helps to change language
     */
    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(LocaleManager.onAttach(newBase));
    }

    /*
     * This method helps to run audio after screen off and stops during incoming call
     */
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
                seekUpdation();
                isActivityForsedPaused = false;
            }
        }
    }
}
