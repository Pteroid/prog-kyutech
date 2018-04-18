package com.example.tani.twittermusicapp

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.database.Cursor
import android.content.DialogInterface
import android.support.v7.app.AlertDialog
import android.view.View
import android.os.Build
import android.support.annotation.RequiresApi
import android.media.AudioManager
import android.media.MediaPlayer
import android.net.Uri
import android.widget.*
import java.io.IOException


class StartActivity : AppCompatActivity() {

    private var mediaPlayer: MediaPlayer? = null
    private val musicIDlist: ArrayList<String>
    private var selectedID = ""
    private var status = ETransition.NonSelected

    init {
        musicIDlist = ArrayList()

    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_start)
        updateTitleList()


    }

    fun updateTitleList() {
        val cr = applicationContext.contentResolver
        try {
            val cursor = cr.query(
                    MediaStore.Audio.Media.EXTERNAL_CONTENT_URI, //データの種類
                    arrayOf(MediaStore.Audio.Media.ALBUM, MediaStore.Audio.Media.ARTIST, MediaStore.Audio.Media.TITLE, MediaStore.Audio.Media._ID), //フィルター用のパラメータ
                    null   //並べ替え
                    , null, null
            )//取得する
            setupTitleList(cursor)
        } catch (e: SecurityException) {
            e.printStackTrace()
            AlertDialog.Builder(this@StartActivity)
                    .setTitle("注意")
                    .setMessage("このアプリにストレージの権限を与えてください")
                    .show()
            System.out.println("権限が与えられていない")
            finish()
        }
    }
    fun setupTitleList(cursor: Cursor) {
        musicIDlist.clear()

        val adapter = ArrayAdapter<String>(this, android.R.layout.simple_list_item_1)

        try {
            //フィルター条件 nullはフィルタリング無し
            cursor.moveToFirst()
            if (cursor.getCount() !== 0)
                do {
                    adapter.add(cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.TITLE)))
                    musicIDlist.add(cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media._ID)))

                } while (cursor.moveToNext())
            val listview = findViewById<ListView>(R.id.listview)
            listview.clearChoices()
            listview.setAdapter(adapter)
            val selectedTitle: TextView = findViewById(R.id.selected_title)
            listview.setOnItemClickListener(ClickEventForList(selectedTitle))
        } catch (e: Exception) {
            e.printStackTrace()
        }

    }

    fun setStartButton() {
        // 音楽開始ボタン
        val buttonStart = findViewById<Button>(R.id.start)

        // リスナーをボタンに登録
        buttonStart.setOnClickListener(View.OnClickListener {
            // 音楽再生
            when {
                status.equals(ETransition.Waiting) -> audioPlay()
                status.equals(ETransition.Playing) -> {
                    mediaPlayer?.pause()
                    status = ETransition.Pausing
                }
                status.equals(ETransition.Pausing) -> {
                    mediaPlayer?.start()
                    status = ETransition.Playing

                }
            }
        })

    }

    fun setStopButton() {
        // 音楽停止ボタン
        val buttonStop = findViewById<Button>(R.id.stop)

        // リスナーをボタンに登録
        buttonStop.setOnClickListener(View.OnClickListener {
            if (mediaPlayer != null) {
                // 音楽停止
                if (status.equals(ETransition.Playing) || status.equals(ETransition.Pausing))
                    audioStop()
            }
        })
    }


    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    private fun audioPlay() {

        if (mediaPlayer == null) {
            // audio ファイルを読出し
            if (audioSetup()) {
                Toast.makeText(application, "Rread audio file", Toast.LENGTH_SHORT).show()
                status = ETransition.Playing
            } else {
                Toast.makeText(application, "Error: read audio file", Toast.LENGTH_SHORT).show()
                return
            }
        } else {
            // 繰り返し再生する場合
            mediaPlayer?.stop()
            mediaPlayer?.reset()
            // リソースの解放
            mediaPlayer?.release()
        }

        // 再生する
        mediaPlayer?.start()

        // 終了を検知するリスナー
        mediaPlayer?.setOnCompletionListener(object : MediaPlayer.OnCompletionListener {
            override fun onCompletion(mp: MediaPlayer) {
                audioStop()
            }
        })
    }

    private fun audioStop() {
        // 再生終了
        mediaPlayer?.stop()
        // リセット
        mediaPlayer?.reset()
        // リソースの解放
        mediaPlayer?.release()

        mediaPlayer = null
        status = ETransition.Waiting
    }


    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    private fun audioSetup(): Boolean {
        var fileCheck = false

        // インタンスを生成
        mediaPlayer = MediaPlayer()
        if (status.equals(ETransition.Waiting))
            try {
                mediaPlayer?.setDataSource(applicationContext, Uri.withAppendedPath(MediaStore.Audio.Media.EXTERNAL_CONTENT_URI, selectedID))
                volumeControlStream = AudioManager.STREAM_MUSIC
                mediaPlayer?.prepare()
                fileCheck = true
                mediaPlayer?.start()
            } catch (e: IOException) {
                e.printStackTrace()
            }

        return fileCheck
    }


    internal inner class ClickEventForList : AdapterView.OnItemClickListener {
        private val titleText: TextView

        constructor(tv: TextView) {
            titleText = tv;
        }

        override fun onItemClick(adapter: AdapterView<*>, view: View, position: Int, id: Long) {


            val textView = view as TextView

            val musicPosition = position
            // ダイアログを表示する

            if (!(status.equals(ETransition.Playing) || status.equals(ETransition.Pausing)))
                AlertDialog.Builder(this@StartActivity)
                        .setTitle(textView.text)
                        .setMessage("この曲をセットしますか？")
                        .setNegativeButton("Cancel", null)
                        .setPositiveButton("OK", DialogInterface.OnClickListener { dialog, which ->
                            selectedID = musicIDlist[musicPosition]
                            status = ETransition.Waiting
                            titleText.setText(textView.text)
                            // OK button pressed
                        })
                        .show()
        }
    }

}
