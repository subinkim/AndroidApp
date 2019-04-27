package edu.exeter.workout;

import android.app.Activity;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.NumberPicker;
import android.widget.TextView;

public class timerActivity extends Activity {

    NumberPicker minute;
    NumberPicker second;
    int minutes = 0;
    int seconds = 0;
    Long millisInFuture = 0L;
    Long currentTimeLeft = 0L;
    Boolean paused = false;

    Button start;
    Button reset;
    Button pause;
    TextView timerTextView;
    CountDownTimer timer;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_timer);

        minute = findViewById(R.id.minute);
        second = findViewById(R.id.second);
        timerTextView = (TextView) findViewById(R.id.time);

        start = (Button) findViewById(R.id.start);
        reset = (Button) findViewById(R.id.reset);
        pause = (Button) findViewById(R.id.pause);

        minute.setMinValue(0);
        minute.setMaxValue(60);

        second.setMinValue(0);
        second.setMaxValue(60);

        minute.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker numberPicker, int i, int i1) {
                minutes = i1;
            }
        });

        second.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker numberPicker, int i, int i1) {
                seconds = i1;
            }
        });

        start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                millisInFuture = seconds * 1000L + minutes * 60 * 1000L;
                timer = new CountDownTimer(millisInFuture, 1) {
                    @Override
                    public void onTick(long l) {
                        int minuteLeft = (int) l/(60*1000);
                        int secondLeft = (int) (l - minuteLeft*60*1000)/1000;
                        float rest = (l - minuteLeft*60*1000 - secondLeft*1000)/10;

                        String minuteText = String.format("%02d", minuteLeft);
                        String secondText = String.format("%02d", secondLeft);
                        String restText = String.format("%02.0f", rest);

                        currentTimeLeft = l;

                        timerTextView.setText(minuteText+":"+secondText+":"+restText);
                    }

                    @Override
                    public void onFinish() {
                        timerTextView.setText("00:00:00");
                        MediaPlayer mp = new MediaPlayer().create(getBaseContext(), (R.raw.sound));
                        mp.start();
                    }
                }.start();
            }
        });

        pause.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (paused){
                    timer = new CountDownTimer(millisInFuture, 1) {
                        @Override
                        public void onTick(long l) {
                            int minuteLeft = (int) l/(60*1000);
                            int secondLeft = (int) (l - minuteLeft*60*1000)/1000;
                            float rest = (l - minuteLeft*60*1000 - secondLeft*1000)/10;

                            String minuteText = String.format("%02d", minuteLeft);
                            String secondText = String.format("%02d", secondLeft);
                            String restText = String.format("%02.0f", rest);

                            currentTimeLeft = l;

                            timerTextView.setText(minuteText+":"+secondText+":"+restText);
                        }

                        @Override
                        public void onFinish() {
                            timerTextView.setText("00:00:00");
                            MediaPlayer mp = new MediaPlayer().create(getBaseContext(), (R.raw.sound));
                            mp.start();
                        }
                    }.start();
                    paused = false;
                    pause.setText("Pause");
                } else {
                    timer.cancel();
                    paused = true;
                    pause.setText("Resume");
                    millisInFuture = currentTimeLeft;
                }
            }
        });

        reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                timer.cancel();
                timerTextView.setText("00:00:00");
                millisInFuture = 0L;
                paused = false;
                pause.setText("Pause");
            }
        });

    }
}
