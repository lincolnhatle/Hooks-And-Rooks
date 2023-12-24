package com.example.homescreen;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

public class MainActivity extends AppCompatActivity {

    ImageView Logo;
    Drawable drawable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Logo = findViewById(R.id.imageViewLogo);
        drawable = getResources().getDrawable(R.drawable.ic_launcher_background);
        Logo.setImageDrawable(drawable);
    }
}