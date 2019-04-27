package edu.exeter.workout;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class programActivity extends Activity {

    private TextView title;
    private ListView programList;
    private FloatingActionButton add;
    private Button main;

    static ArrayList<ArrayList<WorkoutMove>> programs = new ArrayList<>();
    static ArrayList<WorkoutMove> workoutMoveArrayList = new ArrayList<>();
    static ArrayList<String> titles = new ArrayList<>();
    ArrayList<WorkoutMove> items;
    String name;

    String json;
    String names;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);

        title = (TextView) findViewById(R.id.title);
        title.setText("Your workout programs");

        workoutMoveArrayList = (ArrayList<WorkoutMove>) getIntent().getSerializableExtra("wmAL");

        /**************************/
        /*add new program to list */
        /**************************/
        if (getIntent().getSerializableExtra("programItems") != null){
            items = (ArrayList<WorkoutMove>) getIntent().getSerializableExtra("programItems");
            name = getIntent().getStringExtra("name");
            programs.add(items);
            titles.add(name);
        }

        /*************************/
        /*get JSON programs file */
        /*************************/
        if (programs.size() == 0){
            try{
                retrieveProgram();
                if (names != null) titles = getTitles(names);
                if (titles.size() != 0 && json != null) programs = getPrograms(json);
            } catch (IOException e){
                e.printStackTrace();
            } catch (JSONException exception){
                exception.printStackTrace();
            }
        }

        final ArrayAdapter adapter = new ArrayAdapter<String>(this, R.layout.list_view, titles);
        programList = (ListView) findViewById(R.id.programList);
        programList.setAdapter(adapter);

        /********************************/
        /*add program - to programCreate*/
        /********************************/
        add = (FloatingActionButton) findViewById(R.id.add);
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(programActivity.this, programCreateActivity.class);
                i.putExtra("wmAL", workoutMoveArrayList);
                startActivityForResult(i, 0);
            }
        });

        /**********************/
        /*intent to Detail    */
        /**********************/
        programList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent j = new Intent(programActivity.this, programDetailActivity.class);
                j.putExtra("programItems", programs.get(i));
                j.putExtra("name", titles.get(i));
                startActivityForResult(j, 0);
            }
        });

        /**********************/
        /*back to main page   */
        /**********************/
        main = (Button) findViewById(R.id.main);
        main.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(programActivity.this, MainActivity.class);
                startActivityForResult(i, 0);
            }
        });
    }

    /*****************************/
    /*get JSON files for program */
    /*****************************/
    public void retrieveProgram() throws JSONException, IOException {

        ArrayList<ArrayList<WorkoutMove>> retrievedPrograms = new ArrayList<>();

        BufferedReader reader = null;
        BufferedReader reader2 = null;
        try {
            InputStream in = this.openFileInput("program.json");
            InputStream in2 = this.openFileInput("titles.json");

            reader = new BufferedReader(new InputStreamReader(in));
            reader2 = new BufferedReader(new InputStreamReader(in2));

            StringBuilder jsonString = new StringBuilder();
            StringBuilder jsonString2 = new StringBuilder();

            String line = null;
            String line2 = null;

            while  ((line = reader.readLine()) != null) jsonString.append(line);
            while ((line2 = reader2.readLine()) != null) jsonString2.append(line2);

            json = jsonString.toString();
            names = jsonString2.toString();
        } catch (IOException e){
            e.printStackTrace();
        } finally{
            if (reader != null) reader.close();
        }

    }

    private static JSONObject getObject(String tagName, JSONObject jObj)  throws JSONException {
        JSONObject subObj = jObj.getJSONObject(tagName);
        return subObj;
    }

    /*************************/
    /*save on Pause to files */
    /*************************/
    @Override
    protected void onPause() {
        super.onPause();
        String filename = "program.json";
        String filename2 = "titles.json";
        String fileContents = null;
        String fileContentsTitles = null;
        try {
            fileContents = toJSON(programs, titles);
            fileContentsTitles = titlesToJSON(titles);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        FileOutputStream outputStream;
        FileOutputStream opStream;
        try {
            outputStream = openFileOutput(filename, Context.MODE_PRIVATE);
            outputStream.write(fileContents.getBytes());
            outputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        try{
            opStream = openFileOutput(filename2, Context.MODE_PRIVATE);
            opStream.write(fileContentsTitles.getBytes());
            opStream.close();
        } catch(Exception e){
            e.printStackTrace();
        }
    }

    /****************************/
    /*get data from String data */
    /****************************/

    //get titles of program
    public ArrayList<String> getTitles (String data) throws JSONException{
        ArrayList<String> retrievedTitles = new ArrayList<>();

        JSONArray jArray = new JSONArray(data);
        for (int i = 0; i < jArray.length(); i++){
            retrievedTitles.add(jArray.get(i).toString());
        }
        return retrievedTitles;
    }

    //get programs
    public ArrayList<ArrayList<WorkoutMove>> getPrograms (String data) throws JSONException {

        ArrayList<ArrayList<WorkoutMove>> retrievedWorkoutMoves = new ArrayList<>();

        JSONObject jObj = new JSONObject(data);

        for (int i = 0; i < jObj.length(); i++){

            JSONArray jArray = jObj.getJSONArray(titles.get(i));
            ArrayList<WorkoutMove> wmList = new ArrayList<>();

            for (int j = 0; j < jArray.length(); j++) {
                JSONArray jArrayInner = jArray.getJSONArray(j);

                WorkoutMove wm = new WorkoutMove("", "", "", "");

                String name = jArrayInner.get(0).toString();
                String description = jArrayInner.get(1).toString();
                String image = jArrayInner.get(2).toString();
                String diagram = jArrayInner.get(3).toString();

                wm.setName(name);
                wm.setDescription(description);
                wm.setImage(image);
                wm.setDiagram(diagram);

                wmList.add(wm);
            }

            retrievedWorkoutMoves.add(wmList);
        }

        return retrievedWorkoutMoves;
    }


    /**********************/
    /*data to String JSON */
    /**********************/
    //program arrayList to JSON string
    public String toJSON(ArrayList<ArrayList<WorkoutMove>> wmList, ArrayList<String> names) throws JSONException {
        JSONObject jObj = new JSONObject();
        for(int i = 0; i < wmList.size(); i++){
            JSONArray jArray = new JSONArray();
            ArrayList<WorkoutMove> programItem = wmList.get(i);
            for (int j = 0; j < programItem.size(); j++){
                JSONArray jArrayInner = new JSONArray();
                WorkoutMove workout = programItem.get(j);
                jArrayInner.put(workout.getName());
                jArrayInner.put(workout.getDescription());
                jArrayInner.put(workout.getImage());
                jArrayInner.put(workout.getDiagram());
                jArray.put(jArrayInner);
            }
            jObj.put(names.get(i),jArray);
        }
        return jObj.toString();
    }

    //program title arrayList to JSON string
    public String titlesToJSON(ArrayList<String> titles) throws JSONException {
        JSONArray jArray = new JSONArray();
        for (int i = 0; i < titles.size(); i++){
            jArray.put(titles.get(i));
        }
        return jArray.toString();
    }
}
