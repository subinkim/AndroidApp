package edu.exeter.workout;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

public class DetailActivity extends Activity {

    TextView name;
    ImageView diagram;
    TextView description;
    ImageView image;
    Button stopwatch;
    Button timer;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        WorkoutMove wm = (WorkoutMove) getIntent().getSerializableExtra("wm");
        name = (TextView) findViewById(R.id.name);
        diagram = (ImageView) findViewById(R.id.diagram);
        description = (TextView) findViewById(R.id.description);
        image = (ImageView) findViewById(R.id.image);
        name.setText(wm.getName());
        description.setText(wm.getDescription());

        /**********************/
        /*Get Image           */
        /**********************/
        String imageImg = wm.getImage();
        String diagramImg = wm.getDiagram();
        int imgResId = getResources().getIdentifier(imageImg, "drawable", getPackageName());
        image.setImageResource(imgResId);
        int diagramResId = getResources().getIdentifier(diagramImg, "drawable", getPackageName());
        diagram.setImageResource(diagramResId);

        /**********************/
        /*Stopwatch & Timer   */
        /**********************/
        stopwatch = (Button) findViewById(R.id.stopwatch);
        timer = (Button) findViewById(R.id.timer);

        stopwatch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(DetailActivity.this, stopwatchActivity.class);
                startActivityForResult(i,0);
            }
        });

        timer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(DetailActivity.this, timerActivity.class);
                startActivityForResult(i,0);
            }
        });
    }
}
