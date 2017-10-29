package net.kokohadoko.instil.kotlinstopwatch

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.widget.Button
import android.widget.TextView

class MainActivity : AppCompatActivity() {

    // 一度だけ代入するものは valを使用
    val handler = Handler()
    // 繰り返し代入するものは varを使用
    var timeValue = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // View要素を変数に代入
        val timeText = findViewById<TextView>(R.id.timeText)
        val startButton = findViewById<Button>(R.id.start)
        val stopButton = findViewById<Button>(R.id.stop)
        val resetButton = findViewById<Button>(R.id.reset)

        // 一秒ごとに時間を追加
        val runnable = object : Runnable {
            override fun run() {
                timeValue++

                // ?letを用いて、nullでない場合のみ更新
                timeToText(timeValue)?.let {
                    // timeToText(timeValue)の値がlet内ではitとして使える
                    timeText.text = it
                }
                handler.postDelayed(this, 1000)
            }
        }

        // startボタン
        startButton.setOnClickListener {
            handler.post(runnable)
        }

        // stopボタン
        stopButton.setOnClickListener {
            handler.removeCallbacks(runnable)
        }

        // resetボタン
        resetButton.setOnClickListener {
            handler.removeCallbacks(runnable)
            timeValue = 0

            // timeToTextの引数はデフォルト値が設定されているため引数の省略可能
            timeToText()?.let {
                timeText.text = it
            }
        }
    }

    /**
     * 数値を hh:mm:ss 形式の文字列に変換する
     */
    private fun timeToText(time: Int = 0): String? {
        // if式は値を返すため、そのまま returnできる
        return if (time < 0) {
            null
        } else if (time == 0) {
            "00:00:00"
        } else {
            val h = time / 3600       // 時
            val m = time % 3600 / 60  // 分
            val s = time % 60         // 秒
            "%1$02d:%2$02d:%3$02d".format(h, m, s)
        }
    }
}
