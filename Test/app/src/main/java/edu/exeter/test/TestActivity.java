package edu.exeter.test;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class TestActivity extends AppCompatActivity {

    private Button blue;
    private Button red;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);

        Log.d("","onCreate");

        blue = (Button) findViewById(R.id.blue);
        red = (Button) findViewById(R.id.red);

        blue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(TestActivity.this, "Welcome!", Toast.LENGTH_SHORT).show();
            }
        });
    }

}
