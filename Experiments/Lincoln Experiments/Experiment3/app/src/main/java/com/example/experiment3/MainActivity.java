package com.example.experiment3;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

    Button NextNumberBtn;

    TextView FibValTxt;

    Button ResetBtn;

    int FibonaccisNumber = 1;

    int prevNumber = -1;

    int temp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        NextNumberBtn = findViewById(R.id.button);
        FibValTxt  = findViewById(R.id.fib_val);
        ResetBtn    = findViewById(R.id.button2);

        NextNumberBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                temp = FibonaccisNumber;
                FibonaccisNumber = FibonaccisNumber + prevNumber;
                prevNumber = temp;

                FibValTxt.setText(String.valueOf(FibonaccisNumber));
            }
        });


        ResetBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });
    }
}