package com.projects.android.MyNotes.fragment;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.ColorFilter;
import android.graphics.LightingColorFilter;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatDialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.github.clans.fab.FloatingActionButton;
import com.projects.android.MyNotes.R;
import com.projects.android.MyNotes.database.DbHelper;
import com.projects.android.MyNotes.database.Dbhelper2;
import com.projects.android.MyNotes.helper.Data;

import java.sql.Time;
import java.util.concurrent.TimeUnit;

/**
 * Created by LENOVO on 2/28/2019.
 */

public class PlayFragment extends AppCompatDialogFragment {
    TextView currentProgress, timeLimit, fileName;
    AlertDialog dialog;
    FloatingActionButton btnPlay;
    SeekBar seekBar;
    DbHelper dbhelper;
    SQLiteDatabase db;
    Data item;
    long minutes, seconds;
    Boolean isPlaying = false;
    String content, fName, fPath, fileLength;
    private MediaPlayer mediaPlayer;
    private Handler handler  = new Handler();
    private Runnable runnable = new Runnable() {
        @Override
        public void run() {
            if(mediaPlayer != null)
            {
                int currentPos = mediaPlayer.getCurrentPosition();
                seekBar.setProgress(currentPos);
                long minutes = TimeUnit.MILLISECONDS.toMinutes(currentPos);
                long seconds = TimeUnit.MILLISECONDS.toSeconds(currentPos) - TimeUnit.MINUTES.toSeconds(minutes);
                currentProgress.setText(String.format("%02d:%02d", minutes, seconds));
                updateSeekbar();
            }
        }
    };
    private void updateSeekbar()
    {
       handler.postDelayed(runnable, 1000);
    }
    public PlayFragment newInstance()
    {
        PlayFragment f = new PlayFragment();
        return f;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        View view = getActivity().getLayoutInflater().inflate(R.layout.fragment_media_playback, null);
        currentProgress = view.findViewById(R.id.current_progress);
        timeLimit = view.findViewById(R.id.file_limit);
        fileName = view.findViewById(R.id.file_name);
        btnPlay = view.findViewById(R.id.btn_play);
        seekBar = view.findViewById(R.id.seekbar);
        dbhelper = new DbHelper(getActivity());
        db = dbhelper.getWritableDatabase();
        this.item = Home.item;
        content = item.getText();
        //Toast.makeText(getActivity(), content, Toast.LENGTH_SHORT).show();
        String[] split = content.split("\n", 2);
        fName = split[0];
        fileLength = split[1];
        //Toast.makeText(getActivity(), fileLength, Toast.LENGTH_SHORT).show();
        long itemDuration = Integer.parseInt(fileLength);
        minutes = TimeUnit.MILLISECONDS.toMinutes(itemDuration);
        seconds = TimeUnit.MILLISECONDS.toSeconds(itemDuration) - TimeUnit.MINUTES.toSeconds(minutes);
        ColorFilter filter = new LightingColorFilter(getResources().getColor(R.color.colorAccent),getResources().getColor(R.color.colorPrimaryDark));
        seekBar.getProgressDrawable().setColorFilter(filter);
        seekBar.getThumb().setColorFilter(filter);
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                if(mediaPlayer != null && b)
                {
                    mediaPlayer.seekTo(i);
                    handler.removeCallbacks(runnable);
                    long minutes = TimeUnit.MILLISECONDS.toMinutes(mediaPlayer.getCurrentPosition());
                    long secondes = TimeUnit.MILLISECONDS.toSeconds(mediaPlayer.getCurrentPosition()) - TimeUnit.MINUTES.toSeconds(minutes);
                    currentProgress.setText(String.format("%02d:%02d", minutes, secondes));
                    updateSeekbar();
                }
                else if(mediaPlayer == null && b)
                {
                    prepareMediaPlayer(i);
                    updateSeekbar();
                }

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                if(mediaPlayer != null)
                    handler.removeCallbacks(runnable);
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                if(mediaPlayer != null)
                {
                    handler.removeCallbacks(runnable);
                    mediaPlayer.seekTo(seekBar.getProgress());
                    long minutes = TimeUnit.MILLISECONDS.toMinutes(mediaPlayer.getCurrentPosition());
                    long secondes = TimeUnit.MILLISECONDS.toSeconds(mediaPlayer.getCurrentPosition()) - TimeUnit.MINUTES.toSeconds(minutes);
                    currentProgress.setText(String.format("%02d:%02d", minutes, secondes));
                    updateSeekbar();
                }
            }
        });
        fileName.setText(fName);
        timeLimit.setText(String.format("%02d:%02d", minutes, seconds));
        btnPlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onPlay(isPlaying);
                isPlaying = !isPlaying;
            }
        });
            dialog = new AlertDialog.Builder(getActivity()).setView(view).create();
            dialog.getWindow().requestFeature(Window.FEATURE_NO_TITLE);
            return dialog;
    }
    private void prepareMediaPlayer(int progress)
    {
        mediaPlayer = new MediaPlayer();
        try
        {
            fPath = Environment.getExternalStorageDirectory().getAbsolutePath() + "/AudioNotes/"+fName;
            mediaPlayer.setDataSource(fPath);
            mediaPlayer.prepare();
            seekBar.setMax(mediaPlayer.getDuration());
            mediaPlayer.seekTo(progress);
            mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                @Override
                public void onCompletion(MediaPlayer mediaPlayer) {
                    stopPlaying();
                }
            });
        }
        catch (Exception e)
        {
            Toast.makeText(getContext(), String.valueOf(e), Toast.LENGTH_SHORT).show();
        }
        getActivity().getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
    }

    private void onPlay(Boolean isPlaying) {
        if(!isPlaying)
        {
            if(mediaPlayer == null)
                startPlaying();
            else
                resumePlaying();
        }
        else
        {
            pausePlaying();
        }
    }
    public void startPlaying()
    {
        //Toast.makeText(getActivity(), "Start PLaying", Toast.LENGTH_SHORT).show();
     btnPlay.setImageResource(R.drawable.ic_media_pause);
     try
     {
         mediaPlayer = new MediaPlayer();
         fPath = Environment.getExternalStorageDirectory().getAbsolutePath() + "/AudioNotes/"+fName;
         mediaPlayer.setDataSource(fPath);
         mediaPlayer.prepare();
         seekBar.setMax(mediaPlayer.getDuration());
         mediaPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
             @Override
             public void onPrepared(MediaPlayer mediaPlayer) {
                 mediaPlayer.start();
             }
         });
         mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
             @Override
             public void onCompletion(MediaPlayer mediaPlayer) {
                 stopPlaying();
             }
         });
         updateSeekbar();
         getActivity().getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
     }
     catch (Exception e)
     {
         Toast.makeText(getContext(), String.valueOf(e), Toast.LENGTH_SHORT).show();
     }

    }
    public void resumePlaying()
    {
        btnPlay.setImageResource(R.drawable.ic_media_pause);
        handler.removeCallbacks(runnable);
        mediaPlayer.start();
        updateSeekbar();

    }
    public void pausePlaying()
    {
        btnPlay.setImageResource(R.drawable.ic_media_play);
        handler.removeCallbacks(runnable);
        updateSeekbar();

    }
    public void stopPlaying()
    {
        btnPlay.setImageResource(R.drawable.ic_media_play);
        handler.removeCallbacks(runnable);
        mediaPlayer.stop();
        mediaPlayer.reset();
        mediaPlayer.release();
        mediaPlayer = null;
        seekBar.setProgress(seekBar.getMax());
        isPlaying = !isPlaying;
        currentProgress.setText(timeLimit.getText());
        getActivity().getWindow().clearFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);

    }
}
