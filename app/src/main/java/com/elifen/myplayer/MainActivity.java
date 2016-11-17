package com.elifen.myplayer;

import android.content.res.AssetFileDescriptor;
import android.content.res.AssetManager;
import android.media.MediaPlayer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import java.io.IOException;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "myTag";
    private MediaPlayer mediaPlayer;
    private String[] music = new String[]{"Fade", "Till The World Ends", "I Really Like You", "Teenage Dream"};
    private int songIndex = 0;
    private ArrayList<String> songArrayList; //播放声音列表
    private Button buttonStart;
    private Button buttonStop;
    private Button buttonPause;
    private Button buttonLoop;
    private Button buttonPrevious;
    private Button buttonNext;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mediaPlayer = new MediaPlayer();
        final ListView musicList = (ListView) findViewById(R.id.listView);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(MainActivity.this, android.R.layout.simple_list_item_1, music);
        musicList.setAdapter(adapter);
        final TextView txtLoopState = (TextView) findViewById(R.id.txtLoopState);
         buttonStart = (Button) findViewById(R.id.buttonStart);
         buttonPause = (Button) findViewById(R.id.buttonPause);
         buttonStop = (Button) findViewById(R.id.buttonStop);
         buttonLoop = (Button) findViewById(R.id.buttonLoop);
         buttonPrevious = (Button) findViewById(R.id.buttonPrevious);
         buttonNext = (Button) findViewById(R.id.buttonNext);

        songArrayList = new ArrayList<String>();
        songArrayList.add("Alan Walker - Fade.mp3");
        songArrayList.add("Britney Spears - Till The World Ends.mp3");
        songArrayList.add("Carly Rae Jepsen - I Really Like You.mp3");
        songArrayList.add("Katy Perry - Teenage Dream.mp3");
        buttonPause.setEnabled(false);
        buttonStop.setEnabled(false);
        buttonLoop.setEnabled(false);
        //开始播放
        buttonStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                play(songIndex);
            }
        });
        //暂停播放
        buttonPause.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mediaPlayer.isPlaying()) {
                    buttonPause.setText("Play");
                    mediaPlayer.pause();
                } else {
                    buttonPause.setText("Pause");
                    mediaPlayer.start();
                }

            }
        });

        //停止播放
        buttonStop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mediaPlayer.isPlaying())
                    mediaPlayer.stop();

            }
        });

        //循环播放
        buttonLoop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.v(TAG, "Looping");
                boolean loop = mediaPlayer.isLooping();
                mediaPlayer.setLooping(!loop);
                if (!loop)
                    txtLoopState.setText("循环播放");
                else
                    txtLoopState.setText("一次播放");
            }
        });

        //上一首
        buttonPrevious.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (songIndex == 0) {
                    songIndex = 3;
                     play(songIndex);
                } else {
                    songIndex--;
                    play(songIndex);
                }
            }
        });

        //下一首
        buttonNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
             if(songIndex == 3){
                 songIndex=0;
                 play(songIndex);
             }
                else{
                 songIndex++;
                 play(songIndex);
             }
            }
        });

}

    private void play(int songIndex) {
        try {

            Log.v(TAG, "start");
            mediaPlayer.reset();

            AssetManager assetManager = getAssets();
            AssetFileDescriptor assetFileDescriptor = assetManager.openFd(songArrayList.get(songIndex).toString());
            mediaPlayer.setDataSource(assetFileDescriptor.getFileDescriptor(), assetFileDescriptor.getStartOffset(), assetFileDescriptor.getLength());
            mediaPlayer.prepare();
            mediaPlayer.start();

            buttonPause.setEnabled(true);
            buttonStop.setEnabled(true);
            buttonLoop.setEnabled(true);

        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        } catch (IllegalStateException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

}
}
