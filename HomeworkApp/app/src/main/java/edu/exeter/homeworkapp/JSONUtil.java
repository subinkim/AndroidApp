package edu.exeter.homeworkapp;

import android.content.Context;
import android.util.JsonWriter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.*;


public class JSONUtil {

    private Context mContext;
    private String mFilename;

    private static final String JSON_NAME = "name";
    private static final String JSON_SUBJECT = "subject";
    private static final String JSON_DUEDATE = "dueDate";
    private static final String JSON_DESCRIPTION = "description";

    public JSONUtil(Context c, String f){
        mContext = c;
        mFilename = f;
    }

    public JSONObject toJSON(Homework hw) throws JSONException {
        JSONObject json = new JSONObject();
        json.put(JSON_NAME, hw.getName());
        json.put(JSON_SUBJECT, hw.getSubject());
        json.put(JSON_DUEDATE, hw.getDueDate());
        json.put(JSON_DESCRIPTION, hw.getDescription());
        return json;
    }

    public void saveHW(ArrayList<Homework> assignments) throws JSONException, IOException {
        JSONArray array = new JSONArray();
        for (Homework h : assignments) {
            array.put(toJSON(h));
        }

        Writer writer = null;
        try {
            OutputStream out = mContext.openFileOutput(mFilename, Context.MODE_PRIVATE);
            writer = new OutputStreamWriter(out);
            writer.write(array.toString());
        } finally {
            if (writer != null)
                writer.close();
        }
    }

}
