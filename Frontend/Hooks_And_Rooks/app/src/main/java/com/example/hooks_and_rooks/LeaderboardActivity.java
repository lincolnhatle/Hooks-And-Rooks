package com.example.hooks_and_rooks;


import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class LeaderboardActivity extends AppCompatActivity {

    private Button btnHome;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_leaderboard);

        btnHome=findViewById(R.id.buttonHome);

        btnHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LeaderboardActivity.this, HomeActivity.class); //go to sign up screen
                startActivity(intent);
            }
        });
    }



}
