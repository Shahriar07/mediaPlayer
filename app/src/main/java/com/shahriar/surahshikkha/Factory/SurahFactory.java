package com.shahriar.surahshikkha.Factory;

import android.content.Context;

import com.shahriar.surahshikkha.Data.Surah;
import com.shahriar.surahshikkha.Data.SurahAdDuha;
import com.shahriar.surahshikkha.Data.SurahAlAdiyat;
import com.shahriar.surahshikkha.Data.SurahAlAla;
import com.shahriar.surahshikkha.Data.SurahAlAsr;
import com.shahriar.surahshikkha.Data.SurahAlBalad;
import com.shahriar.surahshikkha.Data.SurahAlBayyinah;
import com.shahriar.surahshikkha.Data.SurahAlFajr;
import com.shahriar.surahshikkha.Data.SurahAlFalaq;
import com.shahriar.surahshikkha.Data.SurahAlFatihah;
import com.shahriar.surahshikkha.Data.SurahAlFil;
import com.shahriar.surahshikkha.Data.SurahAlGhashiyah;
import com.shahriar.surahshikkha.Data.SurahAlHumazah;
import com.shahriar.surahshikkha.Data.SurahAlIkhlash;
import com.shahriar.surahshikkha.Data.SurahAlKafirun;
import com.shahriar.surahshikkha.Data.SurahAlKawthar;
import com.shahriar.surahshikkha.Data.SurahAlLayl;
import com.shahriar.surahshikkha.Data.SurahAlMasad;
import com.shahriar.surahshikkha.Data.SurahAlMaun;
import com.shahriar.surahshikkha.Data.SurahAlQadr;
import com.shahriar.surahshikkha.Data.SurahAlQariah;
import com.shahriar.surahshikkha.Data.SurahAlQuraysh;
import com.shahriar.surahshikkha.Data.SurahAnNas;
import com.shahriar.surahshikkha.Data.SurahAnNasr;
import com.shahriar.surahshikkha.Data.SurahAsSharh;
import com.shahriar.surahshikkha.Data.SurahAshShams;
import com.shahriar.surahshikkha.Data.SurahAtTakathur;
import com.shahriar.surahshikkha.Data.SurahAtTariq;
import com.shahriar.surahshikkha.Data.SurahAtTin;
import com.shahriar.surahshikkha.Data.SurahAzZilzalah;

/**
 * Created by H. M. Shahriar on 2/25/2018.
 */

public class SurahFactory {

    private static SurahFactory instance;
    Context applicationContext;

    public static SurahFactory getInstance(Context context){
        if (instance == null)
        {
            synchronized (SurahFactory.class){
                if (instance == null)
                instance = new SurahFactory();
            }
        }
        if (context != null)
            instance.applicationContext = context;
        return instance;
    }

    public Surah prepareSurah(String surahNumber){
        switch (surahNumber){
            case "1":
            {
                return new SurahAlFatihah(applicationContext);
            }case "86":
            {
                return new SurahAtTariq(applicationContext);
            }
            case "87":
            {
                return new SurahAlAla(applicationContext);
            }
            case "88":
            {
                return new SurahAlGhashiyah(applicationContext);
            }
            case "89":
            {
                return new SurahAlFajr(applicationContext);
            }
            case "90":
            {
                return new SurahAlBalad(applicationContext);
            }
            case "91":
            {
                return new SurahAshShams(applicationContext);
            }case "92":
            {
                return new SurahAlLayl(applicationContext);
            }
            case "93":
            {
                return new SurahAdDuha(applicationContext);
            }
            case "94":
            {
                return new SurahAsSharh(applicationContext);
            }
            case "95":
            {
                return new SurahAtTin(applicationContext);
            }
//            case "96":
//            {
//                return new SurahAlAlaq(applicationContext);
//            }
            case "97":
            {
                return new SurahAlQadr(applicationContext);
            }
            case "98":
            {
                return new SurahAlBayyinah(applicationContext);
            }
            case "99":
            {
                return new SurahAzZilzalah(applicationContext);
            }
            case "100":
            {
                return new SurahAlAdiyat(applicationContext);
            }
            case "101":
            {
                return new SurahAlQariah(applicationContext);
            }
            case "102":
            {
                return new SurahAtTakathur(applicationContext);
            }
            case "103":
            {
                return new SurahAlAsr(applicationContext);
            }
            case "104":
            {
                return new SurahAlHumazah(applicationContext);
            }
            case "105":
            {
                return new SurahAlFil(applicationContext);
            }
            case "106":
            {
                return new SurahAlQuraysh(applicationContext);
            }
            case "107":
            {
                return new SurahAlMaun(applicationContext);
            }
            case "108":
            {
                return new SurahAlKawthar(applicationContext);
            }
            case "109":
            {
                return new SurahAlKafirun(applicationContext);
            }
            case "110":
            {
                return new SurahAnNasr(applicationContext);
            }
            case "111":
            {
                return new SurahAlMasad(applicationContext);
            }
            case "112":
            {
                return new SurahAlIkhlash(applicationContext);
            }
            case "113":
            {
                return new SurahAlFalaq(applicationContext);
            }
            case "114":
            {
                return new SurahAnNas(applicationContext);
            }
            default:
                return new SurahAnNas(applicationContext);
        }
//        Surah surah = (Surah)m_RegisteredSurah.get(surahNumber);
//        if (surah != null)
//         return surah.getSuraContent();

//        return null;
    }
}
