package com.example.hooks_and_rooks;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import org.java_websocket.client.WebSocketClient;
import org.java_websocket.handshake.ServerHandshake;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import org.java_websocket.WebSocket;
import org.java_websocket.client.WebSocketClient;
import org.java_websocket.handshake.ServerHandshake;

import org.json.JSONObject;

import java.util.Random;

public class BoxingActivity extends AppCompatActivity implements View.OnClickListener, WebSocketListener {

    public static TextView healthBar;
    public static TextView healthOpp;

    private ImageButton Jab;
    private ImageButton Block;
    private ImageButton UpperCut;

    public String move;

    public String oppmove;

    ImageButton oppUpper;
    ConstraintLayout dynamicLayout;

    WebSocketClient webSocketClient;

    boolean lose;

    boolean win;

    int computer_move;

    int damage;

    int health;
    int oppHealth;
    public static int staticHealth = 1067;
    public static int staticOppHealth = 1067;
    private String SERVER_URL = "ws://10.48.101.36:8080/moves/";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_boxing);

        health = staticHealth;
        oppHealth = staticOppHealth;

        healthOpp = findViewById(R.id.healthOpponent);
        healthBar = findViewById(R.id.healthYou);
        Jab = findViewById(R.id.Jab);
        Block = findViewById(R.id.Block);
        UpperCut = findViewById(R.id.Upper);
//        oppJab = findViewById(R.id.oppJab);
//        oppBlock = findViewById(R.id.oppBlock);
        oppUpper = findViewById(R.id.oppUpper);
        healthBar.setWidth(health);
        healthOpp.setWidth(oppHealth);
        dynamicLayout = findViewById(R.id.dynamic_layout);

        Jab.setOnClickListener(this);
        Block.setOnClickListener(this);
        UpperCut.setOnClickListener(this);



        WebSocketManager.getInstance().connectWebSocket(SERVER_URL);
        WebSocketManager.getInstance().setWebSocketListener(BoxingActivity.this);


        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {

            @Override
            public void run() {
                staticHealth = health;
                staticOppHealth = oppHealth;
                Intent intent = new Intent(BoxingActivity.this, ChessActivity.class);
                startActivity(intent);
            }

        }, 15000);
    }


    public void showSelectedMove(View v, ConstraintLayout layout) {

        layout.removeAllViews();

//        if (v.getId() == R.id.oppBlock) {
//            ImageView block = new ImageView(this);
//            block.setImageResource(R.drawable.block);
//        } else if (v.getId() == R.id.oppJab) {
//            ImageView jab = new ImageView(this);
//            jab.setImageResource(R.drawable.fist);
//        } else {
//            ImageView upper = new ImageView(this);
//            upper.setImageResource(R.drawable.uppercut);
//        }
    }




    public void onClick(View v) {
//        Random rn = new Random();
//        computer_move = rn.nextInt(3 - 1 + 1) + 1;

        dynamicLayout.removeAllViews();


        if (v.getId() == R.id.Jab) {
            move = "Jab";
            WebSocketManager.getInstance().sendMessage(move);
            if (oppmove == "Block") {
                lose = true;
                win = false;
            } else if (oppmove == "Upper") {
                lose = false;
                win = true;
            } else {
                lose = false;
                win = false;
            }

        } else if (v.getId() == R.id.Block) {
            move = "Block";
            WebSocketManager.getInstance().sendMessage(move);
            if (oppmove == "Block") {
                lose = false;
                win = false;
            } else if (oppmove == "Upper") {
                lose = true;
                win = false;
            } else {
                lose = false;
                win = true;
            }

        } else {
            move = "Upper";
            WebSocketManager.getInstance().sendMessage(move);
            if (oppmove == "Block") {
                lose = false;
                win = true;
            } else if (oppmove == "Upper") {
                lose = false;
                win = false;
            } else {
                lose = true;
                win = false;
            }

        }

        damageDone();
        gameLogic();
    }






    /**
     * HELPER METHODS
     */
    private void damageDone () {
        damage = 107;
        if (lose == true) {
            health = health - damage;
            health = Math.max(health, 0);
            healthBar.setWidth(health);
            gameLogic();
        } else if (win == true) {
            oppHealth = oppHealth - damage;
            oppHealth = Math.max(oppHealth, 0);
            healthOpp.setWidth(oppHealth);
            gameLogic();
        } else {

        }

    }

    private void gameLogic () {
        if (health == 0 || oppHealth == 0) {
            //Go to end game screen
                Intent intent = new Intent(BoxingActivity.this, EndActivity.class);
                startActivity(intent);
        }
    }






    /**
     * WEBSOCKET METHODS
     * @param ex The exception that describes the error.
     */
    @Override
    public void onWebSocketError(Exception ex) {
        //MAKE A TEXTVIEW TO USE FOR TESTING
    }

    @Override
    public void onWebSocketClose(int code, String reason, boolean remote) {
        //MAKE A TEXTVIEW TO USE FOR TESTING
    }

    @Override
    public void onWebSocketOpen(ServerHandshake handshakedata) {
        //MAKE A TEXTVIEW TO USE FOR TESTING
    }

    @Override
    public void onWebSocketMessage(String message) {
        oppmove = message;

        //depending on the message recieved, updates the Image to show and
        if (oppmove == "Block") {
            ImageView block = new ImageView(this);
            block.setImageResource(R.drawable.block);
            block.setMaxHeight(350);
            block.setMaxWidth(350);
            block.setX(375);
            block.setY(750);
            block.setAdjustViewBounds(true);
            block.setLayoutParams(new ViewGroup.LayoutParams(
                    ViewGroup.LayoutParams.WRAP_CONTENT,  ViewGroup.LayoutParams.WRAP_CONTENT
            ));
            dynamicLayout.addView(block);
        } else if (oppmove == "Jab") {
            ImageView jab = new ImageView(this);
            jab.setImageResource(R.drawable.fist);
            jab.setMaxHeight(350);
            jab.setMaxWidth(350);
            jab.setX(375);
            jab.setY(750);
            jab.setAdjustViewBounds(true);
            jab.setLayoutParams(new ViewGroup.LayoutParams(
                    ViewGroup.LayoutParams.WRAP_CONTENT,  ViewGroup.LayoutParams.WRAP_CONTENT
            ));
            dynamicLayout.addView(jab);
        } else {
            ImageView upper = new ImageView(this);
            upper.setImageResource(R.drawable.uppercut);
            upper.setMaxHeight(350);
            upper.setMaxWidth(350);
            upper.setX(375);
            upper.setY(750);
            upper.setAdjustViewBounds(true);
            upper.setLayoutParams(new ViewGroup.LayoutParams(
                    ViewGroup.LayoutParams.WRAP_CONTENT,  ViewGroup.LayoutParams.WRAP_CONTENT
            ));
            dynamicLayout.addView(upper);
        }
    }
}






