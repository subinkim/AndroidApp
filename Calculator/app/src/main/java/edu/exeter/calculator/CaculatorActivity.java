package edu.exeter.calculator;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.TextureView;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class CaculatorActivity extends AppCompatActivity {

    boolean reset = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_caculator);

        Button one = (Button) findViewById(R.id.one);
        one.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TextView txt = (TextView) findViewById(R.id.input);
                if (reset) txt.setText(""); reset = false;
                String input = txt.getText().toString();
                input += "1";
                txt.setText(input);
            }
        });

        Button two = (Button) findViewById(R.id.two);
        two.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TextView txt = (TextView) findViewById(R.id.input);
                if (reset) txt.setText(""); reset = false;
                String input = txt.getText().toString();
                input += "2";
                txt.setText(input);
            }
        });

        Button three = (Button) findViewById(R.id.three);
        three.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                TextView txt = (TextView) findViewById(R.id.input);
                if (reset) txt.setText(""); reset = false;
                String input = txt.getText().toString();
                input += "3";
                txt.setText(input);
            }
        });

        Button four = (Button) findViewById(R.id.four);
        four.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                TextView txt = (TextView) findViewById(R.id.input);
                if (reset) txt.setText(""); reset = false;
                String input = txt.getText().toString();
                input += "4";
                txt.setText(input);
            }
        });

        Button five = (Button) findViewById(R.id.five);
        five.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                TextView txt = (TextView) findViewById(R.id.input);
                if (reset) txt.setText(""); reset = false;
                String input = txt.getText().toString();
                input += "5";
                txt.setText(input);
            }
        });

        Button six = (Button) findViewById(R.id.six);
        six.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                TextView txt = (TextView) findViewById(R.id.input);
                if (reset) txt.setText(""); reset = false;
                String input = txt.getText().toString();
                input += "6";
                txt.setText(input);
            }
        });

        Button seven = (Button) findViewById(R.id.seven);
        seven.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                TextView txt = (TextView) findViewById(R.id.input);
                if (reset) txt.setText(""); reset = false;
                String input = txt.getText().toString();
                input += "7";
                txt.setText(input);
            }
        });

        Button eight = (Button) findViewById(R.id.eight);
        eight.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                TextView txt = (TextView) findViewById(R.id.input);
                if (reset) txt.setText(""); reset = false;
                String input = txt.getText().toString();
                input += "8";
                txt.setText(input);
            }
        });

        Button nine = (Button) findViewById(R.id.nine);
        nine.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                TextView txt = (TextView) findViewById(R.id.input);
                if (reset) txt.setText(""); reset = false;
                String input = txt.getText().toString();
                input += "9";
                txt.setText(input);
            }
        });

        Button zero = (Button) findViewById(R.id.zero);
        zero.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                TextView txt = (TextView) findViewById(R.id.input);
                if (reset) txt.setText(""); reset = false;
                String input = txt.getText().toString();
                input += "0";
                txt.setText(input);
            }
        });

        Button add = (Button) findViewById(R.id.add);
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TextView txt = (TextView) findViewById(R.id.input);
                if (reset) txt.setText(""); reset = false;
                String input = txt.getText().toString();
                input += "+";
                txt.setText(input);
            }
        });

        Button subtract = (Button) findViewById(R.id.subtract);
        subtract.setOnClickListener((new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TextView txt = (TextView) findViewById(R.id.input);
                if (reset) txt.setText(""); reset = false;
                String input = txt.getText().toString();
                input += "-";
                txt.setText(input);
            }
        }));

        Button multiply = (Button) findViewById(R.id.multiply);
        multiply.setOnClickListener((new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TextView txt = (TextView) findViewById(R.id.input);
                if (reset) txt.setText(""); reset = false;
                String input = txt.getText().toString();
                input += "*";
                txt.setText(input);
            }
        }));

        Button divide = (Button) findViewById(R.id.divide);
        divide.setOnClickListener((new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TextView txt = (TextView) findViewById(R.id.input);
                if (reset) txt.setText(""); reset = false;
                String input = txt.getText().toString();
                input += "/";
                txt.setText(input);
            }
        }));

        Button decimal = (Button) findViewById(R.id.decimal);
        decimal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TextView txt = (TextView) findViewById(R.id.input);
                if (reset) txt.setText(""); reset = false;
                String input = txt.getText().toString();
                input += ".";
                txt.setText(input);
            }
        });

        Button equal = (Button) findViewById(R.id.equal);
        equal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TextView txt = (TextView) findViewById(R.id.input);
                if (reset) txt.setText(""); reset = false;
                String input = txt.getText().toString();
                input += "=";
                if ((int) input.charAt(0) < 48 || (int) input.charAt(0) > 57) txt.setText("SYNTAX ERROR");
                else {
                    double result = calculate(input);
                    txt.setText(Double.toString(result));
                }
                reset = true;
            }
        });

        Button clear = (Button) findViewById(R.id.clear);
        clear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TextView txt = (TextView) findViewById(R.id.input);
                txt.setText("");
            }
        });
    }

    public double calculate(String a){
        double result = 0;
        int index = 0;
        int secondIndex = 0;
        for (int i = 0; i < a.length(); i++){
            if (((int) a.charAt(i) < 48 || (int) a.charAt(i) > 57 ) && (int) a.charAt(i) != 46) {
                if (index == 0) {
                    result += Double.parseDouble(a.substring(index, i));
                    index = i;
                }
                else {
                    for (int j = i - 1; j < a.length(); j++) {
                        if (((int) a.charAt(j) < 48 || (int) a.charAt(j) > 57) && (int) a.charAt(j) != 46) {
                            secondIndex = j;
                            j = a.length();
                        }
                    }
                    if ((int) a.charAt(index) == 43) {
                        result += Double.parseDouble(a.substring(index + 1, secondIndex));
                        index = secondIndex;
                    } else if ((int) a.charAt(index) == 45) {
                        result -= Double.parseDouble(a.substring(index + 1, secondIndex));
                        index = secondIndex;
                    } else if ((int) a.charAt(index) == 42) {
                        result *= Double.parseDouble(a.substring(index + 1, secondIndex));
                        index = secondIndex;
                    } else if ((int) a.charAt(index) == 47) {
                        result /= Double.parseDouble(a.substring(index + 1, secondIndex));
                        index = secondIndex;
                    }
                }
            }
        }
        return result;
    }

}
