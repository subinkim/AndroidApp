package edu.exeter.workout;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.SystemClock;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class stopwatchActivity extends Activity {

    TextView timer ;
    Button start, pause, reset;
    long millisecondTime, startTime, gap, updateTime = 0L ;
    Handler handler;
    int seconds, minutes, milliSeconds;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stopwatch);

        timer = (TextView)findViewById(R.id.tvTimer);
        start = (Button)findViewById(R.id.start);
        pause = (Button)findViewById(R.id.pause);
        reset = (Button)findViewById(R.id.reset);

        handler = new Handler() ;

        start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startTime = SystemClock.uptimeMillis();
                handler.postDelayed(runnable, 0);
                reset.setEnabled(false);
            }
        });

        pause.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                gap += millisecondTime;
                handler.removeCallbacks(runnable);
                reset.setEnabled(true);
            }
        });

        reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                millisecondTime = 0L ;
                startTime = 0L ;
                gap = 0L ;
                updateTime = 0L ;
                seconds = 0 ;
                minutes = 0 ;
                milliSeconds = 0 ;

                timer.setText("00:00:00");

            }
        });

    }

    public Runnable runnable = new Runnable() {

        public void run() {
            millisecondTime = SystemClock.uptimeMillis() - startTime;
            updateTime = gap + millisecondTime;
            seconds = (int) (updateTime / 1000);
            minutes = seconds / 60;
            seconds = seconds % 60;
            milliSeconds = (int) (updateTime % 1000);
            timer.setText("" + minutes + ":" + String.format("%02d", seconds) + ":" + String.format("%03d", milliSeconds));

            handler.postDelayed(this, 0);
        }

    };
}
