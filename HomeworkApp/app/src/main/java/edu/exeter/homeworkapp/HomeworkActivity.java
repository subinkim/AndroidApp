package edu.exeter.homeworkapp;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.*;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.*;

public class HomeworkActivity extends AppCompatActivity {

    static ArrayList<Homework> assignments = new ArrayList<>();

    private FloatingActionButton add;
    private ListView lv;
    private Homework hw;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_homework);

        if (assignments.size() == 0){
            String json = getJSON();
            try {
                assignments = getHomework(json);
            } catch (JSONException e){
                e.printStackTrace();
            }
        }

        if (getIntent().getSerializableExtra("homework") != null) {
            hw = (Homework) getIntent().getSerializableExtra("homework");
            assignments.add(hw);
        }

        if (getIntent().getBooleanExtra("delete", false)) {
            int id = getIntent().getIntExtra("id",-1);
            if (id > -1) assignments.remove(id);
        }

        ArrayAdapter adapter = new ArrayAdapter<Homework>(this, R.layout.list_view, assignments);
        lv = (ListView) findViewById(R.id.listView);
        lv.setAdapter(adapter);

        add = (FloatingActionButton) findViewById(R.id.addButton);
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(HomeworkActivity.this, CreateHWActivity.class);
                startActivityForResult(i, 0);
            }
        });

        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent j = new Intent(HomeworkActivity.this,  DetailActivity.class);
                j.putExtra("id", i);
                j.putExtra("hw", assignments.get(i));
                Homework hw = assignments.get(i);
                startActivityForResult(j, 0);
            }
        });
    }

    @Override
    protected void onPause() {
        super.onPause();
        JSONUtil json = new JSONUtil(this, "hwActivity.json");
        try {
            json.saveHW(assignments);
        } catch (JSONException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public static ArrayList<Homework> getHomework(String data) throws JSONException {

        int index = 0;
        ArrayList<Homework> retrievedAssignments = new ArrayList<>();

        JSONObject jObj = new JSONObject(data);
        for (int i = 0; i < jObj.length(); i++){
            Homework homework = new Homework("a", "00/00/0000","Math", "aa");
            String name = getObject("name", jObj).toString();
            String subject = getObject("subject", jObj).toString();

            String date = getObject("dueDate", jObj).toString();

            String description = getObject("description", jObj).toString();

            homework.setName(name);
            homework.setDescription(description);
            homework.setDueDate(date);
            homework.setSubject(subject);
            retrievedAssignments.add(homework);
        }

        return retrievedAssignments;
    }

    public String getJSON(){
        String json = null;
        try {
            //how to open file???????
            InputStream is = getAssets().open("hwActivity.json");
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            json = new String(buffer, "UTF-8");

        } catch (IOException ex) {
            ex.printStackTrace();
            return null;
        }
        return json;
    }

    private static JSONObject getObject(String tagName, JSONObject jObj)  throws JSONException {
        JSONObject subObj = jObj.getJSONObject(tagName);
        return subObj;
    }
}