package edu.exeter.workout;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.CalendarView;
import android.widget.ImageButton;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;


public class MainActivity extends AppCompatActivity {

    private ImageButton learn;
    private ImageButton programs;
    private ImageButton statistics;
    private TextView date;
    private CalendarView calendarView;
    private TextView dailyWorkoutList;

    static ArrayList<WorkoutMove> workoutMoveArrayList = new ArrayList<>();
    String[] workoutMoves = {"goblet_squat", "bench_press", "split_squat", "trx_row", "plank", "bicycle_crunches"};

    //statistics
    static ArrayList<ArrayList<WorkoutMove>> stats = new ArrayList<>();
    static ArrayList<WorkoutMove> dailyWorkout = new ArrayList<>();
    boolean created;
    static ArrayList<String> statsDate = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        date = (TextView) findViewById(R.id.dailyWorkout);
        calendarView = (CalendarView)findViewById(R.id.calendarView);

        /**********************/
        /*CalendarView        */
        /**********************/

        //calendarView
        SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
        String defaultDate = dateFormat.format(new Date(calendarView.getDate()));
        date.setText("Your workout on "+defaultDate);

        dailyWorkoutList = (TextView) findViewById(R.id.dailyWorkoutList);

        //display your workout stats on each day
        calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(CalendarView calendarView, int year, int month, int dayOfMonth) {
                date.setText("Your workout on "+(month+1)+"/"+dayOfMonth+"/"+year);
                dailyWorkoutList.setText("");
                String listText = "";
                String date = (month+1)+"/"+dayOfMonth+"/"+year;
                int index = statsDate.indexOf(date);
                if (index != -1) {
                    dailyWorkout = stats.get(index);
                    for (int i = 0; i < dailyWorkout.size(); i++) {
                        listText += dailyWorkout.get(i);
                        listText += "\n";
                    }
                    dailyWorkoutList.setText(listText);
                }
            }
        });

        /**********************/
        /*Get JSON data       */
        /**********************/

        //gets workout moves from JSON
        if (workoutMoveArrayList.size() == 0){
            String json = getJSON();
            try {
                workoutMoveArrayList = addWorkoutMoves(json);
            } catch (JSONException e){
                e.printStackTrace();
            }
        }

        //gets workout stats from JSON
        if (stats.size() == 0){
            String json = getJSONStats();
            String json2 = getJSONDates();
            try{
                if (json2 != null) statsDate = getDates(json2);
                if (statsDate != null && json !=null) stats = getStatsJSON(json);
            } catch (JSONException e){
                e.printStackTrace();
            }
        }

        //gets statistics from statisticsActivity.java
        created = getIntent().getBooleanExtra("created", false);
        if (getIntent().getSerializableExtra("sItems") != null && created){
            created = false;
            //checking if the user has already added stat for the date
            if (statsDate.indexOf(getIntent().getStringExtra("date")) != -1){
                int index = statsDate.indexOf(getIntent().getStringExtra("date"));
                ArrayList<WorkoutMove> getDaily = stats.get(index);
                ArrayList<WorkoutMove> newList = (ArrayList<WorkoutMove>) getIntent().getSerializableExtra("sItems");
                for (int i = 0; i < newList.size(); i++){
                    getDaily.add(newList.get(i));
                }
                stats.remove(index);
                stats.add(index, getDaily);
            } else {
                stats.add((ArrayList<WorkoutMove>) getIntent().getSerializableExtra("sItems"));
                statsDate.add(getIntent().getStringExtra("date"));
            }
            saveStats();
        }

        /**********************/
        /*Three Image Buttons */
        /**********************/

        //learn - intent to infoActivity
        learn = (ImageButton) findViewById(R.id.learn);
        learn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(MainActivity.this, infoActivity.class);
                i.putExtra("wmAL", workoutMoveArrayList);
                startActivityForResult(i,0);
            }
        });

        //programs - intent to programActivity
        programs = (ImageButton) findViewById(R.id.programs);
        programs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(MainActivity.this, programActivity.class);
                i.putExtra("wmAL", workoutMoveArrayList);
                startActivityForResult(i,0);
            }
        });

        //statistics - intent to statisticsActivity
        statistics = (ImageButton) findViewById(R.id.statistics);
        statistics.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(MainActivity.this, statisticsActivity.class);
                startActivityForResult(i,0);
            }
        });

    }

    /**********************/
    /*String data to JSON */
    /**********************/

    //gets the list of workout moves from string json data
    public ArrayList<WorkoutMove> addWorkoutMoves(String data) throws JSONException {

        ArrayList<WorkoutMove> retrievedWorkoutMoves = new ArrayList<>();

        JSONObject jObj = new JSONObject(data);

        for (int i = 0; i < jObj.length(); i++){
            WorkoutMove wm = new WorkoutMove("", "", "", "");
            JSONArray jArray = jObj.getJSONArray(workoutMoves[i]);

            String name = jArray.get(0).toString();
            String description = jArray.get(1).toString();
            String image = jArray.get(2).toString();
            String diagram = jArray.get(3).toString();

            wm.setName(name);
            wm.setDescription(description);
            wm.setImage(image);
            wm.setDiagram(diagram);

            retrievedWorkoutMoves.add(wm);

        }

        return retrievedWorkoutMoves;
    }

    //gets JSON data from moves.json
    public String getJSON(){
        String json = null;
        try {
            InputStream is = getAssets().open("moves.json");
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

    /**********************/
    /*JSON file to String */
    /**********************/

    //stats.json to String
    public String getJSONStats(){
        String json = null;
        BufferedReader reader = null;
        try {
            InputStream in = this.openFileInput("stats.json");
            reader = new BufferedReader(new InputStreamReader(in));
            StringBuilder jsonString = new StringBuilder();
            String line = null;
            while  ((line = reader.readLine()) != null) jsonString.append(line);
            json = jsonString.toString();
        } catch (IOException e){
            e.printStackTrace();
        } finally{
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        return json;
    }

    //dates.json to String
    public String getJSONDates(){
        String json = null;
        BufferedReader reader = null;
        try {
            InputStream in = this.openFileInput("dates.json");
            reader = new BufferedReader(new InputStreamReader(in));
            StringBuilder jsonString = new StringBuilder();
            String line = null;
            while  ((line = reader.readLine()) != null) jsonString.append(line);
            json = jsonString.toString();
        } catch (IOException e){
            e.printStackTrace();
        } finally{
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        return json;
    }

    /**********************/
    /*ArrayList from JSON */
    /**********************/
    //get dates from JSON
    public ArrayList<String> getDates (String data) throws JSONException{
        ArrayList<String> retrievedDates = new ArrayList<>();

        JSONArray jArray = new JSONArray(data);
        for (int i = 0; i < jArray.length(); i++){
            retrievedDates.add(jArray.get(i).toString());
        }
        return retrievedDates;
    }

    //get stats workoutMove from JSON
    public ArrayList<ArrayList<WorkoutMove>> getStatsJSON (String data) throws JSONException {

        ArrayList<ArrayList<WorkoutMove>> retreivedStats = new ArrayList<>();

        JSONObject jObj = new JSONObject(data);

        for (int i = 0; i < jObj.length(); i++){

            JSONArray jArray = jObj.getJSONArray(statsDate.get(i));
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

            retreivedStats.add(wmList);
        }

        return retreivedStats;
    }

    /**********************/
    /*Write JSON File     */
    /**********************/
    //write JSON files for stats
    public void saveStats() {
        super.onPause();
        String filename = "stats.json";
        String filename2 = "dates.json";
        String fileContents = null;
        String fileContents2 = null;
        try {
            fileContents = toJSON(stats, statsDate);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        FileOutputStream outputStream;
        try {
            outputStream = openFileOutput(filename, Context.MODE_PRIVATE);
            outputStream.write(fileContents.getBytes());
            outputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        try {
            fileContents2 = datesToJSON(statsDate);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        FileOutputStream outputStream2;
        try {
            outputStream2 = openFileOutput(filename2, Context.MODE_PRIVATE);
            outputStream2.write(fileContents2.getBytes());
            outputStream2.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**********************/
    /*Convert to JSON     */
    /**********************/
    //stats WorkoutMoves to JSON
    public String toJSON(ArrayList<ArrayList<WorkoutMove>> wmList, ArrayList<String> dates) throws JSONException {
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
            jObj.put(dates.get(i),jArray);
        }
        return jObj.toString();
    }

    //stats dates to JSON
    public String datesToJSON(ArrayList<String> dates) throws JSONException {
        JSONArray jArray = new JSONArray();
        for (int i = 0; i < dates.size(); i++){
            jArray.put(dates.get(i));
        }
        return jArray.toString();
    }
}
