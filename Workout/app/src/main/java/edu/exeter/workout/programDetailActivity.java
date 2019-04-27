package edu.exeter.workout;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

public class programDetailActivity extends Activity {

    TextView title;
    ListView lv;
    ArrayList<WorkoutMove> programItems;
    String name;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_program_detail);
        programItems = (ArrayList<WorkoutMove>) getIntent().getSerializableExtra("programItems");
        name = getIntent().getStringExtra("name");

        title = (TextView) findViewById(R.id.title);
        title.setText(name);
        lv =  (ListView) findViewById(R.id.listView);
        ArrayAdapter adapter = new ArrayAdapter<WorkoutMove>(this, R.layout.list_view, programItems);

        lv.setAdapter(adapter);

        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent j = new Intent(programDetailActivity.this, DetailActivity.class);
                WorkoutMove wm = programItems.get(i);
                j.putExtra("wm", wm);
                startActivityForResult(j,0);
            }
        });

    }

}
