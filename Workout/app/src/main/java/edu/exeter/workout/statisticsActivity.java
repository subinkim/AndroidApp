package edu.exeter.workout;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import static edu.exeter.workout.MainActivity.workoutMoveArrayList;

public class statisticsActivity extends Activity {

    private Button done;
    private Button main;
    private TextView title;
    boolean[] clicked = {false, false, false, false, false, false};
    CalendarView cv;
    ListView lv;
    String date;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_statistics);

        cv = (CalendarView) findViewById(R.id.calendarView);
        lv = (ListView) findViewById(R.id.listView);

        //get date from calendarView
        cv.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(CalendarView calendarView, int i, int i1, int i2) {
                date = (i1+1) + "/"+i2+"/"+i;
            }
        });

        //get items from the list
        final ArrayList<WorkoutMove> statsItems = new ArrayList<>();
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                if (clicked[i] == true) {
                    statsItems.remove(workoutMoveArrayList.get(i));
                    lv.getChildAt(i).setBackgroundColor(Color.WHITE);
                    clicked[i] = false;
                } else {
                    statsItems.add(workoutMoveArrayList.get(i));
                    lv.getChildAt(i).setBackgroundColor(Color.rgb(169,0,0));
                    clicked[i] = true;
                }
            }
        });

        ArrayAdapter adapter = new ArrayAdapter<WorkoutMove>(this, R.layout.list_view, workoutMoveArrayList);
        lv.setAdapter(adapter);

        //intent to MainActivity
        done = (Button) findViewById(R.id.done);
        final Toast toast = Toast.makeText(this, "Stat added", Toast.LENGTH_SHORT);
        done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                toast.show();
                Intent i = new Intent(statisticsActivity.this, MainActivity.class);
                i.putExtra("created", true);
                i.putExtra("date", date);
                i.putExtra("sItems", statsItems);
                startActivityForResult(i,0);
            }
        });

        main = (Button) findViewById(R.id.cancel);
        main.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(statisticsActivity.this, MainActivity.class);
                startActivityForResult(i, 0);
            }
        });
    }
}
