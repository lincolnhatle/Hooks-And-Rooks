package com.example.myexperiment1;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Switch;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.ButtonBarLayout;

public class ImgGeneratorActivity extends AppCompatActivity implements View.OnClickListener{

    protected Button imgbutton,homepgbutton;

    protected ImageView img1;


     //initialization
     @Override
     protected void onCreate(Bundle savedInstanceState) {
          super.onCreate(savedInstanceState);
          setContentView(R.layout.activity_img_generator);

          //initialize buttons
          homepgbutton = (Button) findViewById(R.id.homepgbutton);
          imgbutton = (Button) findViewById(R.id.imgbutton);
          img1 = (ImageView) findViewById(R.id.imageView);

          // button click listeners
          homepgbutton.setOnClickListener(this);
          imgbutton.setOnClickListener(this);
          img1.setOnClickListener(this);
     }

    @Override
    public void onClick(View v) {


         if(v.getId() == R.id.homepgbutton) {
             startActivity(new Intent(ImgGeneratorActivity.this,
                     MainActivity.class));
         }
    }
}
