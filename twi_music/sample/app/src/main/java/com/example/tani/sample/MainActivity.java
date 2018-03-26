package com.example.tani.sample;

import android.content.ContentResolver;
import android.content.DialogInterface;
import android.database.Cursor;
import android.graphics.Color;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Build;
import android.provider.MediaStore;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private MediaPlayer mediaPlayer;
    private Cursor cursor;
    private List<String> musicIDlist = new ArrayList<>();
    private String selectedID = "";
    private ETransition status = ETransition.NonSelected;
    private TextView musicName;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        musicName = findViewById(R.id.musicname);
        ArrayAdapter<String> adapter
                = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1);

        ContentResolver cr = getApplicationContext().getContentResolver();
        try {
            cursor = cr.query(
                    MediaStore.Audio.Media.EXTERNAL_CONTENT_URI,  //データの種類
                    new String[]{
                            MediaStore.Audio.Media.ALBUM,
                            MediaStore.Audio.Media.ARTIST,
                            MediaStore.Audio.Media.TITLE,
                            MediaStore.Audio.Media._ID
                    },//取得する
                    null, //フィルター条件 nullはフィルタリング無し
                    null, //フィルター用のパラメータ
                    null   //並べ替え
            );
            Log.d("TEST", Arrays.toString(cursor.getColumnNames()));  //項目名の一覧を出力
            Log.d("TEST", cursor.getCount() + ""); //取得できた件数を表示
            cursor.moveToFirst();
            if (cursor.getCount() != 0)
                do {
                    adapter.add(cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.TITLE)));
                    musicIDlist.add(cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media._ID)));

                } while (cursor.moveToNext());
            ListView listview = findViewById(R.id.listview);
            listview.setAdapter(adapter);
            listview.setOnItemClickListener(new ClickEvent()); // リスナを設定
        } catch (Exception e) {
            e.printStackTrace();
        }


        // 音楽開始ボタン
        Button buttonStart = findViewById(R.id.start);

        // リスナーをボタンに登録
        buttonStart.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            @Override
            public void onClick(View v) {
                // 音楽再生
                if (status.equals(ETransition.Waiting))
                    audioPlay();
                else if (status.equals(ETransition.Playing)) {
                    mediaPlayer.pause();
                    status = ETransition.Pausing;
                } else if (status.equals(ETransition.Pausing)) {
                    mediaPlayer.start();
                    status = ETransition.Playing;

                }
            }
        });

        // 音楽停止ボタン
        Button buttonStop = findViewById(R.id.stop);

        // リスナーをボタンに登録
        buttonStop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mediaPlayer != null) {
                    // 音楽停止
                    if (status.equals(ETransition.Playing) || status.equals(ETransition.Pausing))
                        audioStop();
                }
            }
        });
    }


    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    private boolean audioSetup() {
        boolean fileCheck = false;

        // インタンスを生成
        mediaPlayer = new MediaPlayer();
        if (status.equals(ETransition.Waiting))
            try {
                mediaPlayer.setDataSource(getApplicationContext(), Uri.withAppendedPath(MediaStore.Audio.Media.EXTERNAL_CONTENT_URI, selectedID));
                setVolumeControlStream(AudioManager.STREAM_MUSIC);
                mediaPlayer.prepare();
                fileCheck = true;
                mediaPlayer.start();
            } catch (IOException e) {
                e.printStackTrace();
            }
/*
        //音楽ファイル名, あるいはパス
        String filePath = "music.mp3";

        // assetsから mp3 ファイルを読み込み
        try (AssetFileDescriptor afdescripter = getAssets().openFd(filePath);) {
            // MediaPlayerに読み込んだ音楽ファイルを指定
            mediaPlayer.setDataSource(afdescripter.getFileDescriptor(),
                    afdescripter.getStartOffset(),
                    afdescripter.getLength());
            // 音量調整を端末のボタンに任せる
            setVolumeControlStream(AudioManager.STREAM_MUSIC);
            mediaPlayer.prepare();
            fileCheck = true;
        } catch (IOException e1) {
            e1.printStackTrace();
        }
*/
        return fileCheck;
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    private void audioPlay() {

        if (mediaPlayer == null) {
            // audio ファイルを読出し
            if (audioSetup()) {
                Toast.makeText(getApplication(), "Rread audio file", Toast.LENGTH_SHORT).show();
                status = ETransition.Playing;
            } else {
                Toast.makeText(getApplication(), "Error: read audio file", Toast.LENGTH_SHORT).show();
                return;
            }
        } else {
            // 繰り返し再生する場合
            mediaPlayer.stop();
            mediaPlayer.reset();
            // リソースの解放
            mediaPlayer.release();
        }

        // 再生する
        mediaPlayer.start();

        // 終了を検知するリスナー
        mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                Log.d("debug", "end of audio");
                audioStop();
            }
        });
    }

    private void audioStop() {
        // 再生終了
        mediaPlayer.stop();
        // リセット
        mediaPlayer.reset();
        // リソースの解放
        mediaPlayer.release();

        mediaPlayer = null;
        status = ETransition.Waiting;
    }

    class ClickEvent implements AdapterView.OnItemClickListener {

        public void onItemClick(final AdapterView<?> adapter, View view, int position, long id) {

            final TextView textView = (TextView) view;

            final int musicPosition = position;
            // ダイアログを表示する

            if (!(status.equals(ETransition.Playing) || status.equals(ETransition.Pausing)))
                new AlertDialog.Builder(MainActivity.this)
                        .setTitle(textView.getText())
                        .setMessage("この曲をセットしますか？")
                        .setNegativeButton("Cancel", null)
                        .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                selectedID = musicIDlist.get(musicPosition);
                                status = ETransition.Waiting;
                                musicName.setText(textView.getText());
                                // OK button pressed
                            }
                        })
                        .show();
        }
    }

}
