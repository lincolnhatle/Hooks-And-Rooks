package com.example.myexperiment1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Switch;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    protected Button pg2button, counterbtn;

    int counter;


    //initialization
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //initialize buttons
        pg2button = (Button) findViewById(R.id.pg2button);
        counterbtn = (Button) findViewById(R.id.counterbutton);

        // button click listeners
        pg2button.setOnClickListener(this);
        counterbtn.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
//        switch (v.getId()) {
//            //go to page 2
//            case R.id.pg2button:
//                startActivity(new Intent(MainActivity.this,
//                        ChangePageActivity.class));
//                break;
//            //change color theme
//            case R.id.darkmodeswitch:
//                startActivity(new Intent(MainActivity.this,
//                        DarkModeActivity.class));
//                break;
//            default:
//                break;
//        }
        if(v.getId() == R.id.pg2button) {
            startActivity(new Intent(MainActivity.this,
                    ImgGeneratorActivity.class));
        }

        if(v.getId() == R.id.counterbutton) {
            counter++;
            //R.id.countervalue.text == counter;
        }

    }
}