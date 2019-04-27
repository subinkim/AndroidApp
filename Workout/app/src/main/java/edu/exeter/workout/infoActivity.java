package edu.exeter.workout;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

public class infoActivity extends Activity {

    public static ArrayList<WorkoutMove> workoutMoveArrayList = new ArrayList<>();
    ListView lv;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info);

        workoutMoveArrayList = (ArrayList<WorkoutMove>) getIntent().getSerializableExtra("wmAL");


        ArrayAdapter adapter = new ArrayAdapter<WorkoutMove>(this, R.layout.list_view, workoutMoveArrayList);
        lv = (ListView) findViewById(R.id.listView);
        lv.setAdapter(adapter);

        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent j = new Intent(infoActivity.this,  DetailActivity.class);
                j.putExtra("id", i);
                j.putExtra("wm", workoutMoveArrayList.get(i));
                WorkoutMove hw = workoutMoveArrayList.get(i);
                startActivityForResult(j, 0);
            }
        });
    }

}
