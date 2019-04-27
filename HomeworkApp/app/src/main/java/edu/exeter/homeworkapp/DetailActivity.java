package edu.exeter.homeworkapp;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class DetailActivity extends Activity{

    private TextView name;
    private TextView date;
    private TextView subject;
    private TextView description;
    private int id;
    private Button back;
    private Button delete;
    private boolean deleteItem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        Homework hw = (Homework) getIntent().getSerializableExtra("hw");
        id = getIntent().getIntExtra("id", 0);

        name = (TextView) findViewById(R.id.name);
        date = (TextView) findViewById(R.id.date);
        subject = (TextView) findViewById(R.id.subject);
        description = (TextView) findViewById(R.id.description);

        if (hw != null) {
            name.setText(hw.getName());
            date.setText("" + hw.getDueDate());
            subject.setText(hw.getSubject());
            description.setText(hw.getDescription());
        }

        back = (Button) findViewById(R.id.back);
        delete = (Button) findViewById(R.id.delete);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(DetailActivity.this, HomeworkActivity.class);
                startActivityForResult(i, 0);
            }
        });

        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(DetailActivity.this, HomeworkActivity.class);
                deleteItem = true;
                i.putExtra("id", id);
                i.putExtra("delete", deleteItem);
                startActivityForResult(i, 0);
            }
        });

    }

}
