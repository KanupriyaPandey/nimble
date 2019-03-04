package com.projects.android.MyNotes.activity;

import android.Manifest;
import android.app.Service;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.database.sqlite.SQLiteDatabase;
import android.media.MediaRecorder;
import android.os.Environment;
import android.os.IBinder;
import android.support.v4.app.ActivityCompat;
import android.util.Log;
import android.widget.Toast;

import com.projects.android.MyNotes.database.DbHelper;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Locale;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by LENOVO on 2/27/2019.
 */

public class RecordService extends Service {
    DbHelper helper;
    SQLiteDatabase db;
    private MediaRecorder mRecorder = null;
    private String mFileName = null;
    private String mFilePath = null;
    private long mStartingTimeMillis = 0;
    private long mElapsedMillis = 0;
    private int mElapsedSeconds = 0;
    private OnTimerChangedListener onTimerChangedListener = null;
    private static final SimpleDateFormat mTimerFormat = new SimpleDateFormat("mm:ss", Locale.getDefault());

    private Timer mTimer = null;
    private TimerTask mIncrementTimerTask = null;
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    public interface OnTimerChangedListener {
        void onTimerChanged(int seconds);
    }

    @Override
    public void onCreate() {
        //Toast.makeText(this, "Recording Service Called", Toast.LENGTH_SHORT).show();
        super.onCreate();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        startRecording();
        return START_STICKY;
    }

    private void startRecording() {
        setFileNameAndPath();
        Toast.makeText(this, "Recording Started", Toast.LENGTH_SHORT).show();
        mRecorder = new MediaRecorder();
        try
        {
        mRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
        mRecorder.setOutputFormat(MediaRecorder.OutputFormat.MPEG_4);
        mRecorder.setOutputFile(mFilePath);
        mRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.AAC);
        mRecorder.setAudioChannels(1);
        mRecorder.setAudioSamplingRate(44100);
        mRecorder.setAudioEncodingBitRate(192000);
        mRecorder.prepare();
        mRecorder.start();
        mStartingTimeMillis = System.currentTimeMillis();
        }
        catch (Exception e)
        {
            Toast.makeText(this, String.valueOf(e), Toast.LENGTH_SHORT).show();
        }
    }
    public void setFileNameAndPath(){
        File folder = new File(Environment.getExternalStorageDirectory() + "/AudioNotes");
        if (!folder.exists()) {
            //folder /AudioNotes doesn't exist, create the folder
            folder.mkdir();
        }
        int count = 0;
        File f;

        do{
            count++;

            mFileName = "Recording"
                    + "_" + count + ".mp4";
            mFilePath = Environment.getExternalStorageDirectory().getAbsolutePath();
            mFilePath += "/AudioNotes/" + mFileName;
            f = new File(mFilePath);
            /*if(f.exists() && !f.isDirectory())
            {
                count++;
                mFileName = "MyRecording"
                        + "_" + count + ".mp4";
                mFilePath = Environment.getExternalStorageDirectory().getAbsolutePath();
                mFilePath += "/AudioNotes/" + mFileName;
            }*/
        }while (f.exists() && !f.isDirectory());
    }

    @Override
    public void onDestroy() {
        if (mRecorder != null) {
            stopRecording();
        }

        super.onDestroy();
    }
    public void stopRecording() {
        try {
            mRecorder.stop();
            mElapsedMillis = (System.currentTimeMillis() - mStartingTimeMillis);
            mRecorder.release();
            if (mIncrementTimerTask != null) {
                mIncrementTimerTask.cancel();
                mIncrementTimerTask = null;
            }

            mRecorder = null;
            try {
                helper = new DbHelper(getApplicationContext());
                db = helper.getWritableDatabase();
                helper.addInfo("Audio_Recorded", null,  mFileName+"\n"+mElapsedMillis, null, db);
                Toast.makeText(this, "Recording Stopped", Toast.LENGTH_SHORT).show();
            }
            catch (Exception e)
            {
                Toast.makeText(this, String.valueOf(e), Toast.LENGTH_SHORT).show();
            }
            Intent intent = new Intent(getApplicationContext(), Main.class);
            startActivity(intent);
        }
        catch (Exception e)
        {
            Toast.makeText(this, String.valueOf(e), Toast.LENGTH_SHORT).show();
        }
    }
}
