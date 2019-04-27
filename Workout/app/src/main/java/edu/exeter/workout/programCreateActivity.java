package edu.exeter.workout;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

public class programCreateActivity extends Activity {

    ArrayList<WorkoutMove> workoutMoveArrayList;
    boolean[] clicked = {false, false, false, false, false, false};
    TextView name;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_program_create);

        workoutMoveArrayList = (ArrayList<WorkoutMove>) getIntent().getSerializableExtra("wmAL");

        final ArrayAdapter adapter = new ArrayAdapter<WorkoutMove>(this, R.layout.list_view, workoutMoveArrayList);

        final ListView lv = (ListView) findViewById(R.id.listView);
        lv.setAdapter(adapter);
        final ArrayList<WorkoutMove> programItems = new ArrayList<>();
        Button done = (Button) findViewById(R.id.done);

        //add items to the list when clicked and take off when reclicked
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                if (clicked[i] == true) {
                    programItems.remove(workoutMoveArrayList.get(i));
                    lv.getChildAt(i).setBackgroundColor(Color.WHITE);
                    clicked[i] = false;
                } else {
                    programItems.add(workoutMoveArrayList.get(i));
                    lv.getChildAt(i).setBackgroundColor(Color.rgb(169,0,0));
                    clicked[i] = true;
                }
            }
        });

        //when done, intent to programActivity
        done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                name = (TextView) findViewById(R.id.name);
                String title = name.getText().toString();
                Intent i = new Intent(programCreateActivity.this, programActivity.class);
                i.putExtra("name", title);
                i.putExtra("programItems", programItems);
                startActivityForResult(i, 0);
            }
        });
    }
}
