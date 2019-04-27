package edu.exeter.geoquiz;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class CheatActivity extends Activity {

    public static final String EXTRA_ANSWER_IS_TRUE = "edu.exeter.geoquiz.answer_is_true";
    public static final String EXTRA_ANSWER_SHOWN = "edu.exeter.geoquiz.answer_shown";
    private boolean answerIsTrue;

    private TextView answerTextView;
    private Button showAnswer;

    private void setAnswerShownResult(boolean isAnswerShown) {
        Intent data = new Intent();
        data.putExtra(EXTRA_ANSWER_SHOWN, isAnswerShown);
        setResult(RESULT_OK, data);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cheat);

        answerIsTrue = getIntent().getBooleanExtra(EXTRA_ANSWER_IS_TRUE, false);
        answerTextView = (TextView) findViewById(R.id.answerTextView);

        showAnswer = (Button) findViewById(R.id.showAnswerButton);
        showAnswer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (answerIsTrue) {
                    answerTextView.setText(R.string.trueButton);
                } else {
                    answerTextView.setText(R.string.falseButton);
                }
                setAnswerShownResult(true);
            }
        });
    }
}

