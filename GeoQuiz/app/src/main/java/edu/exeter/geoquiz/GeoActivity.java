package edu.exeter.geoquiz;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class GeoActivity extends AppCompatActivity {

    public static final String TAG = "QuizActivity";
    public static final String KEY_INDEX = "index";

    private Button trueButton;
    private Button falseButton;
    private Button nextButton;
    private Button cheatButton;
    private TextView textView;
    private TextView points;

    Question[] questions = new Question[] {
        new Question(R.string.r0, true),
        new Question(R.string.r1, false),
        new Question(R.string.r2, false),
        new Question(R.string.r3, true),
        new Question(R.string.r4, false)
    };

    private int index = 0;
    private int score = 0;
    private boolean isCheater;

    private void updateQuestion(){
        int question = questions[index].getResId();
        textView.setText(question);
    }

    private boolean checkAnswer(boolean pTrue) {
        boolean answerT = questions[index].getAnswer();
        int msgId = 0;

        if (isCheater){
            msgId = R.string.judgment_toast;
            return false;
        } else {
            if (pTrue == answerT) {
                Toast.makeText(this, R.string.correctToast, Toast.LENGTH_SHORT)
                        .show();
                return true;
            } else {
                Toast.makeText(this, R.string.incorrectToast, Toast.LENGTH_SHORT)
                        .show();
                return false;
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (data == null) {
            return; }
        isCheater = data.getBooleanExtra(CheatActivity.EXTRA_ANSWER_SHOWN, false);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_geo);

        textView = (TextView) findViewById(R.id.textView);
        int question = questions[index].getResId();
        textView.setText(question);

        points = (TextView) findViewById(R.id.score);

        trueButton = (Button) findViewById(R.id.trueButton);
        trueButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkAnswer(true);
                if (checkAnswer(true) == true) score++;
                points.setText("Points: "+score);
            }
        });

        falseButton = (Button) findViewById(R.id.falseButton);
        falseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkAnswer(false);
                if (checkAnswer(false) == true) score++;
                points.setText("Points: "+score);
            }
        });

        nextButton = (Button) findViewById(R.id.nextButton);
        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                index = (index + 1) % questions.length;
                isCheater = false;
                int question = questions[index].getResId();
                textView.setText(question);
            }
        });

        cheatButton = (Button) findViewById(R.id.cheat_button);
        cheatButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(GeoActivity.this, CheatActivity.class);
                boolean answerIsTrue = questions[index].getAnswer();
                i.putExtra(CheatActivity.EXTRA_ANSWER_IS_TRUE, answerIsTrue);
                startActivityForResult(i,0);
            }
        });

        if (savedInstanceState != null){
            index = savedInstanceState.getInt(KEY_INDEX, 0);
        }

        updateQuestion();
    }

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        super.onSaveInstanceState(savedInstanceState);
        Log.i(TAG, "onSavedInstanceState");
        savedInstanceState.putInt(KEY_INDEX, index);
    }

}
