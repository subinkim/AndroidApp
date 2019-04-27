package edu.exeter.matchinggameapp;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MatchingGameActivity extends Activity {

    private Button play;
    private Button instruction;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_matching_game);

        play = (Button) findViewById(R.id.play);
        play.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(MatchingGameActivity.this, playGameActivity.class);
                startActivityForResult(i,0);
            }
        });

        instruction = (Button) findViewById(R.id.instruction);
        instruction.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(MatchingGameActivity.this, InstructionActivity.class);
                startActivityForResult(i,0);
            }
        });
    }
}
