package com.example.hooks_and_rooks;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import org.json.JSONObject;

public class HomeActivity extends AppCompatActivity {

    private ImageView Logo;
    private TextView userName;
    private Drawable drawable;
    private Button btnPlay, btnLeaderBoard, btnSettings;
    private JSONObject user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        Logo = findViewById(R.id.imageViewLogo);
        drawable = getResources().getDrawable(R.drawable.chess_boxing_logo1);
        drawable.setBounds(300, 100, 300, 100 );
        Logo.setImageDrawable(drawable);

        userName=findViewById(R.id.userName);
        btnPlay=findViewById(R.id.buttonPlay);
        btnLeaderBoard=findViewById(R.id.buttonLeaderboard);
        btnSettings=findViewById(R.id.buttonSettings);
        try {
            userName.setText("User:   " + UserSingleton.getUsername());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        btnPlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(HomeActivity.this, ChessActivity.class);
                startActivity(intent);
            }
        });

        btnLeaderBoard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(HomeActivity.this, LeaderboardActivity.class);
                startActivity(intent);
            }
        });

        btnSettings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(HomeActivity.this, DeleteActivity.class);
                startActivity(intent);
            }
        });
    }
}