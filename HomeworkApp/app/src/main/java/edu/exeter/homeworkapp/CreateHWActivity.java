package edu.exeter.homeworkapp;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.Spinner;
import android.widget.TextView;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class CreateHWActivity extends Activity{

    private Button create;
    private Button cancel;
    private DatePicker dueDate;
    private String date;
    private String description;
    private String subject;
    private String name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create);

        create = (Button) findViewById(R.id.createAssignmentButton);
        create.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                dueDate = (DatePicker) findViewById(R.id.dueDatePicker);
                date = (dueDate.getMonth()+1)+"/"+(dueDate.getDayOfMonth())+"/"+(dueDate.getYear());

                name = ((TextView) findViewById(R.id.name)).getText().toString();
                description = ((TextView) findViewById(R.id.description)).getText().toString();
                subject = ((Spinner) findViewById(R.id.subject)).getSelectedItem().toString();

                Homework hw = new Homework(name, date, subject, description);

                Intent i = new Intent(CreateHWActivity.this, HomeworkActivity.class);
                i.putExtra("homework", hw);
                startActivityForResult(i, 0);
            }
        });

        cancel = (Button) findViewById(R.id.cancelButton);
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(CreateHWActivity.this, HomeworkActivity.class);
                startActivityForResult(i, 0);
            }
        });

    }

}
