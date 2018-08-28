package com.shahriar.surahshikkha.Interfaces;

/**
 * Created by H. M. Shahriar on 8/26/2018.
 */
public interface AudioControllerListener {
    void audioPaused( int surahNumber);

    void audioStarted( int surahNumber);

    void audioFinished( int surahNumber);
}
