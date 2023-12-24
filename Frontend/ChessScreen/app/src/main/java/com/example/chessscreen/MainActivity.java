package com.example.chessscreen;

import androidx.annotation.ColorInt;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.IntentSender;
import android.content.ServiceConnection;
import android.content.SharedPreferences;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.content.res.AssetManager;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.media.Image;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.UserHandle;
import android.util.Log;
import android.view.Display;
import android.view.DragEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.chessscreen.api.*;

import org.java_websocket.handshake.ServerHandshake;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;


public class MainActivity extends AppCompatActivity implements View.OnClickListener, WebSocketListener {

    private Button[][] Spaces = new Button[8][8];

    private Board gameBoard;

    private Space startSpace, endSpace;

    private ENUM_TYPES status;

    private String myColor;

    private Button connectBtn;

    private TextView textView, msgTextView;

    private ConstraintLayout dynamicLayout;

    private Context context;
    //WebSocket
    private String SERVER_URL = "ws://10.48.101.36:8080/moves/"; //needs to be dynamic
    //create a SERVER_URL = BASE_URL + something

    private List<ImageView> pieceImages; //so I can drag and drop my views/pieces

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //INITIALIZING BUTTONS
        Spaces[0][0] = findViewById(R.id.space_0_0);
        Spaces[0][1] = findViewById(R.id.space_0_1);
        Spaces[0][2] = findViewById(R.id.space_0_2);
        Spaces[0][3] = findViewById(R.id.space_0_3);
        Spaces[0][4] = findViewById(R.id.space_0_4);
        Spaces[0][5] = findViewById(R.id.space_0_5);
        Spaces[0][6] = findViewById(R.id.space_0_6);
        Spaces[0][7] = findViewById(R.id.space_0_7);

        Spaces[1][0] = findViewById(R.id.space_1_0);
        Spaces[1][1] = findViewById(R.id.space_1_1);
        Spaces[1][2] = findViewById(R.id.space_1_2);
        Spaces[1][3] = findViewById(R.id.space_1_3);
        Spaces[1][4] = findViewById(R.id.space_1_4);
        Spaces[1][5] = findViewById(R.id.space_1_5);
        Spaces[1][6] = findViewById(R.id.space_1_6);
        Spaces[1][7] = findViewById(R.id.space_1_7);

        Spaces[2][0] = findViewById(R.id.space_2_0);
        Spaces[2][1] = findViewById(R.id.space_2_1);
        Spaces[2][2] = findViewById(R.id.space_2_2);
        Spaces[2][3] = findViewById(R.id.space_2_3);
        Spaces[2][4] = findViewById(R.id.space_2_4);
        Spaces[2][5] = findViewById(R.id.space_2_5);
        Spaces[2][6] = findViewById(R.id.space_2_6);
        Spaces[2][7] = findViewById(R.id.space_2_7);

        Spaces[3][0] = findViewById(R.id.space_3_0);
        Spaces[3][1] = findViewById(R.id.space_3_1);
        Spaces[3][2] = findViewById(R.id.space_3_2);
        Spaces[3][3] = findViewById(R.id.space_3_3);
        Spaces[3][4] = findViewById(R.id.space_3_4);
        Spaces[3][5] = findViewById(R.id.space_3_5);
        Spaces[3][6] = findViewById(R.id.space_3_6);
        Spaces[3][7] = findViewById(R.id.space_3_7);

        Spaces[4][0] = findViewById(R.id.space_4_0);
        Spaces[4][1] = findViewById(R.id.space_4_1);
        Spaces[4][2] = findViewById(R.id.space_4_2);
        Spaces[4][3] = findViewById(R.id.space_4_3);
        Spaces[4][4] = findViewById(R.id.space_4_4);
        Spaces[4][5] = findViewById(R.id.space_4_5);
        Spaces[4][6] = findViewById(R.id.space_4_6);
        Spaces[4][7] = findViewById(R.id.space_4_7);

        Spaces[5][0] = findViewById(R.id.space_5_0);
        Spaces[5][1] = findViewById(R.id.space_5_1);
        Spaces[5][2] = findViewById(R.id.space_5_2);
        Spaces[5][3] = findViewById(R.id.space_5_3);
        Spaces[5][4] = findViewById(R.id.space_5_4);
        Spaces[5][5] = findViewById(R.id.space_5_5);
        Spaces[5][6] = findViewById(R.id.space_5_6);
        Spaces[5][7] = findViewById(R.id.space_5_7);

        Spaces[6][0] = findViewById(R.id.space_6_0);
        Spaces[6][1] = findViewById(R.id.space_6_1);
        Spaces[6][2] = findViewById(R.id.space_6_2);
        Spaces[6][3] = findViewById(R.id.space_6_3);
        Spaces[6][4] = findViewById(R.id.space_6_4);
        Spaces[6][5] = findViewById(R.id.space_6_5);
        Spaces[6][6] = findViewById(R.id.space_6_6);
        Spaces[6][7] = findViewById(R.id.space_6_7);

        Spaces[7][0] = findViewById(R.id.space_7_0);
        Spaces[7][1] = findViewById(R.id.space_7_1);
        Spaces[7][2] = findViewById(R.id.space_7_2);
        Spaces[7][3] = findViewById(R.id.space_7_3);
        Spaces[7][4] = findViewById(R.id.space_7_4);
        Spaces[7][5] = findViewById(R.id.space_7_5);
        Spaces[7][6] = findViewById(R.id.space_7_6);
        Spaces[7][7] = findViewById(R.id.space_7_7);

        connectBtn = findViewById(R.id.connect);
        textView = findViewById(R.id.textView);

        //Set color for testing
        //HTTP request to get which player (player 1 = WHITE and player 2 = BLACK)
        myColor = "BLACK";

        //INITIALIZE BOARD
        gameBoard = new Board();

        //DRAW PIECES ON BOARD
        dynamicLayout = findViewById(R.id.dynamic_layout);
        //draws pieces on dynamic layout every time a piece is moved(call from main activity)
        drawPieces(gameBoard, dynamicLayout);

        //INSTANTIATING LISTENERS
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                Spaces[i][j].setOnClickListener(this);
            }
        }

        connectBtn.setOnClickListener(this);
    }


    /**
     * The implementation of button behavior on a click.
     *
     * @param v
     */
    @Override
    public void onClick(View v) {

        if (v.getId() == R.id.connect) {
            //Establish WebSocket connection
            WebSocketManager.getInstance().connectWebSocket(SERVER_URL + "lincoln"); //CHANGE TO USER_SINGLETON VALUE
            WebSocketManager.getInstance().setWebSocketListener(MainActivity.this);

            textView.setText("Connected!");
        }

        //Sets first click as the starting space if there isn't already a starting space
        if (startSpace == null) {
            //sets first click to the corresponding space
            if (v.getId() == R.id.space_0_0) {
                startSpace = gameBoard.getSpace(0, 0);
            } else if (v.getId() == R.id.space_0_1) {
                startSpace = gameBoard.getSpace(0, 1);
            } else if (v.getId() == R.id.space_0_2) {
                startSpace = gameBoard.getSpace(0, 2);
            } else if (v.getId() == R.id.space_0_3) {
                startSpace = gameBoard.getSpace(0, 3);
            } else if (v.getId() == R.id.space_0_4) {
                startSpace = gameBoard.getSpace(0, 4);
            } else if (v.getId() == R.id.space_0_5) {
                startSpace = gameBoard.getSpace(0, 5);
            } else if (v.getId() == R.id.space_0_6) {
                startSpace = gameBoard.getSpace(0, 6);
            } else if (v.getId() == R.id.space_0_7) {
                startSpace = gameBoard.getSpace(0, 7);
            } else if (v.getId() == R.id.space_1_0) {
                startSpace = gameBoard.getSpace(1, 0);
            } else if (v.getId() == R.id.space_1_1) {
                startSpace = gameBoard.getSpace(1, 1);
            } else if (v.getId() == R.id.space_1_2) {
                startSpace = gameBoard.getSpace(1, 2);
            } else if (v.getId() == R.id.space_1_3) {
                startSpace = gameBoard.getSpace(1, 3);
            } else if (v.getId() == R.id.space_1_4) {
                startSpace = gameBoard.getSpace(1, 4);
            } else if (v.getId() == R.id.space_1_5) {
                startSpace = gameBoard.getSpace(1, 5);
            } else if (v.getId() == R.id.space_1_6) {
                startSpace = gameBoard.getSpace(1, 6);
            } else if (v.getId() == R.id.space_1_7) {
                startSpace = gameBoard.getSpace(1, 7);
            } else if (v.getId() == R.id.space_2_0) {
                startSpace = gameBoard.getSpace(2, 0);
            } else if (v.getId() == R.id.space_2_1) {
                startSpace = gameBoard.getSpace(2, 1);
            } else if (v.getId() == R.id.space_2_2) {
                startSpace = gameBoard.getSpace(2, 2);
            } else if (v.getId() == R.id.space_2_3) {
                startSpace = gameBoard.getSpace(2, 3);
            } else if (v.getId() == R.id.space_2_4) {
                startSpace = gameBoard.getSpace(2, 4);
            } else if (v.getId() == R.id.space_2_5) {
                startSpace = gameBoard.getSpace(2, 5);
            } else if (v.getId() == R.id.space_2_6) {
                startSpace = gameBoard.getSpace(2, 6);
            } else if (v.getId() == R.id.space_2_7) {
                startSpace = gameBoard.getSpace(2, 7);
            } else if (v.getId() == R.id.space_3_0) {
                startSpace = gameBoard.getSpace(3, 0);
            } else if (v.getId() == R.id.space_3_1) {
                startSpace = gameBoard.getSpace(3, 1);
            } else if (v.getId() == R.id.space_3_2) {
                startSpace = gameBoard.getSpace(3, 2);
            } else if (v.getId() == R.id.space_3_3) {
                startSpace = gameBoard.getSpace(3, 3);
            } else if (v.getId() == R.id.space_3_4) {
                startSpace = gameBoard.getSpace(3, 4);
            } else if (v.getId() == R.id.space_3_5) {
                startSpace = gameBoard.getSpace(3, 5);
            } else if (v.getId() == R.id.space_3_6) {
                startSpace = gameBoard.getSpace(3, 6);
            } else if (v.getId() == R.id.space_3_7) {
                startSpace = gameBoard.getSpace(3, 7);
            } else if (v.getId() == R.id.space_4_0) {
                startSpace = gameBoard.getSpace(4, 0);
            } else if (v.getId() == R.id.space_4_1) {
                startSpace = gameBoard.getSpace(4, 1);
            } else if (v.getId() == R.id.space_4_2) {
                startSpace = gameBoard.getSpace(4, 2);
            } else if (v.getId() == R.id.space_4_3) {
                startSpace = gameBoard.getSpace(4, 3);
            } else if (v.getId() == R.id.space_4_4) {
                startSpace = gameBoard.getSpace(4, 4);
            } else if (v.getId() == R.id.space_4_5) {
                startSpace = gameBoard.getSpace(4, 5);
            } else if (v.getId() == R.id.space_4_6) {
                startSpace = gameBoard.getSpace(4, 6);
            } else if (v.getId() == R.id.space_4_7) {
                startSpace = gameBoard.getSpace(4, 7);
            } else if (v.getId() == R.id.space_5_0) {
                startSpace = gameBoard.getSpace(5, 0);
            } else if (v.getId() == R.id.space_5_1) {
                startSpace = gameBoard.getSpace(5, 1);
            } else if (v.getId() == R.id.space_5_2) {
                startSpace = gameBoard.getSpace(5, 2);
            } else if (v.getId() == R.id.space_5_3) {
                startSpace = gameBoard.getSpace(5, 3);
            } else if (v.getId() == R.id.space_5_4) {
                startSpace = gameBoard.getSpace(5, 4);
            } else if (v.getId() == R.id.space_5_5) {
                startSpace = gameBoard.getSpace(5, 5);
            } else if (v.getId() == R.id.space_5_6) {
                startSpace = gameBoard.getSpace(5, 6);
            } else if (v.getId() == R.id.space_5_7) {
                startSpace = gameBoard.getSpace(5, 7);
            } else if (v.getId() == R.id.space_6_0) {
                startSpace = gameBoard.getSpace(6, 0);
            } else if (v.getId() == R.id.space_6_1) {
                startSpace = gameBoard.getSpace(6, 1);
            } else if (v.getId() == R.id.space_6_2) {
                startSpace = gameBoard.getSpace(6, 2);
            } else if (v.getId() == R.id.space_6_3) {
                startSpace = gameBoard.getSpace(6, 3);
            } else if (v.getId() == R.id.space_6_4) {
                startSpace = gameBoard.getSpace(6, 4);
            } else if (v.getId() == R.id.space_6_5) {
                startSpace = gameBoard.getSpace(6, 5);
            } else if (v.getId() == R.id.space_6_6) {
                startSpace = gameBoard.getSpace(6, 6);
            } else if (v.getId() == R.id.space_6_7) {
                startSpace = gameBoard.getSpace(6, 7);
            } else if (v.getId() == R.id.space_7_0) {
                startSpace = gameBoard.getSpace(7, 0);
            } else if (v.getId() == R.id.space_7_1) {
                startSpace = gameBoard.getSpace(7, 1);
            } else if (v.getId() == R.id.space_7_2) {
                startSpace = gameBoard.getSpace(7, 2);

            } else if (v.getId() == R.id.space_7_3) {
                startSpace = gameBoard.getSpace(7, 3);

            } else if (v.getId() == R.id.space_7_4) {
                startSpace = gameBoard.getSpace(7, 4);

            } else if (v.getId() == R.id.space_7_5) {
                startSpace = gameBoard.getSpace(7, 5);

            } else if (v.getId() == R.id.space_7_6) {
                startSpace = gameBoard.getSpace(7, 6);

            } else if (v.getId() == R.id.space_7_7) {
                startSpace = gameBoard.getSpace(7, 7);
            }
        }
        //Sets second click(endspace) if first click hasn't been initialized yet
        else {
            if (v.getId() == R.id.space_0_0) {
                try {
                    if (gameBoard.getSpace(0, 0).getPiece().getColor() != myColor) {
                        endSpace = gameBoard.getSpace(0, 0);
                    }
                } catch (Exception e) {
                    if(gameBoard.getSpace(0, 0).getPiece() == null) {
                        endSpace = gameBoard.getSpace(0, 0);
                    }
                }
            } else if (v.getId() == R.id.space_0_1) {
                try{
                    if (gameBoard.getSpace(0, 1).getPiece().getColor() != myColor) {
                        endSpace = gameBoard.getSpace(0, 1);
                    }
                } catch (Exception e) {
                    if(gameBoard.getSpace(0, 1).getPiece() == null) {
                        endSpace = gameBoard.getSpace(0, 1);
                    }
                }
            } else if (v.getId() == R.id.space_0_2) {
                try {
                if (gameBoard.getSpace(0, 2).getPiece().getColor() != myColor) {
                    endSpace = gameBoard.getSpace(0, 2);
                }
                } catch (Exception e) {
                    if(gameBoard.getSpace(0, 2).getPiece() == null) {
                        endSpace = gameBoard.getSpace(0, 2);
                    }
                }
            } else if (v.getId() == R.id.space_0_3) {
                try {
                if (gameBoard.getSpace(0, 3).getPiece().getColor() != myColor) {
                    endSpace = gameBoard.getSpace(0, 3);
                }
                } catch (Exception e) {
                    if(gameBoard.getSpace(0, 3).getPiece() == null) {
                        endSpace = gameBoard.getSpace(0, 3);
                    }
                }
            } else if (v.getId() == R.id.space_0_4) {
                try {
                if (gameBoard.getSpace(0, 4).getPiece().getColor() != myColor) {
                    endSpace = gameBoard.getSpace(0, 4);
                }
                } catch (Exception e) {
                    if(gameBoard.getSpace(0, 4).getPiece() == null) {
                        endSpace = gameBoard.getSpace(0, 4);
                    }
                }
            } else if (v.getId() == R.id.space_0_5) {
                try {
                if (gameBoard.getSpace(0, 5).getPiece().getColor() != myColor) {
                    endSpace = gameBoard.getSpace(0, 5);
                }
                } catch (Exception e) {
                    if(gameBoard.getSpace(0, 5).getPiece() == null) {
                        endSpace = gameBoard.getSpace(0, 5);
                    }
                }
            } else if (v.getId() == R.id.space_0_6) {
                try {
                if (gameBoard.getSpace(0, 6).getPiece().getColor() != myColor) {
                    endSpace = gameBoard.getSpace(0, 6);
                }
                } catch (Exception e) {
                    if(gameBoard.getSpace(0, 6).getPiece() == null) {
                        endSpace = gameBoard.getSpace(0, 6);
                    }
                }
            } else if (v.getId() == R.id.space_0_7) {
                try {
                if (gameBoard.getSpace(0, 7).getPiece().getColor() != myColor) {
                    endSpace = gameBoard.getSpace(0, 7);
                }
                } catch (Exception e) {
                    if(gameBoard.getSpace(0, 7).getPiece() == null) {
                        endSpace = gameBoard.getSpace(0, 7);
                    }
                }
            }
            if (v.getId() == R.id.space_1_0) {
                try {
                if (gameBoard.getSpace(1, 0).getPiece().getColor() != myColor) {
                    endSpace = gameBoard.getSpace(1, 0);
                }
                } catch (Exception e) {
                    if(gameBoard.getSpace(1, 0).getPiece() == null) {
                        endSpace = gameBoard.getSpace(1, 0);
                    }
                }
            } else if (v.getId() == R.id.space_1_1) {
                try {
                if (gameBoard.getSpace(1, 1).getPiece().getColor() != myColor) {
                    endSpace = gameBoard.getSpace(1, 1);
                }
                } catch (Exception e) {
                    if(gameBoard.getSpace(1, 1).getPiece() == null) {
                        endSpace = gameBoard.getSpace(1, 1);
                    }
                }
            } else if (v.getId() == R.id.space_1_2) {
                try {
                    if (gameBoard.getSpace(1, 2).getPiece().getColor() != myColor) {
                        endSpace = gameBoard.getSpace(1, 2);
                    }
                } catch (Exception e) {
                    if(gameBoard.getSpace(1, 2).getPiece() == null) {
                        endSpace = gameBoard.getSpace(1, 2);
                    }
                }
            } else if (v.getId() == R.id.space_1_3) {
                try {
                    if (gameBoard.getSpace(1, 3).getPiece().getColor() != myColor) {
                        endSpace = gameBoard.getSpace(1, 3);
                    }
                } catch (Exception e) {
                    if(gameBoard.getSpace(1, 3).getPiece() == null) {
                        endSpace = gameBoard.getSpace(1, 3);
                    }
                }
            } else if (v.getId() == R.id.space_1_4) {
                try {
                    if (gameBoard.getSpace(1, 4).getPiece().getColor() != myColor) {
                        endSpace = gameBoard.getSpace(1, 4);
                    }
                } catch (Exception e) {
                    if(gameBoard.getSpace(1, 4).getPiece() == null) {
                        endSpace = gameBoard.getSpace(1, 4);
                    }
                }
            } else if (v.getId() == R.id.space_1_5) {
                try {
                    if (gameBoard.getSpace(1, 5).getPiece().getColor() != myColor) {
                        endSpace = gameBoard.getSpace(1, 5);
                    }
                } catch (Exception e) {
                    if(gameBoard.getSpace(1, 5).getPiece() == null) {
                        endSpace = gameBoard.getSpace(1, 5);
                    }
                }
            } else if (v.getId() == R.id.space_1_6) {
                try {
                    if (gameBoard.getSpace(1, 6).getPiece().getColor() != myColor) {
                        endSpace = gameBoard.getSpace(1, 6);
                    }
                } catch (Exception e) {
                    if(gameBoard.getSpace(1, 6).getPiece() == null) {
                        endSpace = gameBoard.getSpace(1, 6);
                    }
                }
            } else if (v.getId() == R.id.space_1_7) {
                try {
                    if (gameBoard.getSpace(1, 7).getPiece().getColor() != myColor) {
                        endSpace = gameBoard.getSpace(1, 7);
                    }
                } catch (Exception e) {
                    if(gameBoard.getSpace(1, 7).getPiece() == null) {
                        endSpace = gameBoard.getSpace(1, 7);
                    }
                }
            }
            if (v.getId() == R.id.space_2_0) {
                try {
                    if (gameBoard.getSpace(2, 0).getPiece().getColor() != myColor) {
                        endSpace = gameBoard.getSpace(2, 0);
                    }
                } catch (Exception e) {
                    if(gameBoard.getSpace(2, 0).getPiece() == null) {
                        endSpace = gameBoard.getSpace(2, 0);
                    }
                }
            } else if (v.getId() == R.id.space_2_1) {
                try {
                    if (gameBoard.getSpace(2, 1).getPiece().getColor() != myColor) {
                        endSpace = gameBoard.getSpace(2, 1);
                    }
                } catch (Exception e) {
                    if(gameBoard.getSpace(2, 1).getPiece() == null) {
                        endSpace = gameBoard.getSpace(2, 1);
                    }
                }
            } else if (v.getId() == R.id.space_2_2) {
                try {
                    if (gameBoard.getSpace(2, 2).getPiece().getColor() != myColor) {
                        endSpace = gameBoard.getSpace(2, 2);
                    }
                } catch (Exception e) {
                    if(gameBoard.getSpace(2, 2).getPiece() == null) {
                        endSpace = gameBoard.getSpace(2, 2);
                    }
                }
            } else if (v.getId() == R.id.space_2_3) {
                try {
                    if (gameBoard.getSpace(2, 3).getPiece().getColor() != myColor) {
                        endSpace = gameBoard.getSpace(2, 3);
                    }
                } catch (Exception e) {
                    if(gameBoard.getSpace(2, 3).getPiece() == null) {
                        endSpace = gameBoard.getSpace(2, 3);
                    }
                }
            } else if (v.getId() == R.id.space_2_4) {
                try {
                    if (gameBoard.getSpace(2, 4).getPiece().getColor() != myColor) {
                        endSpace = gameBoard.getSpace(2, 4);
                    }
                } catch (Exception e) {
                    if(gameBoard.getSpace(2, 4).getPiece() == null) {
                        endSpace = gameBoard.getSpace(2, 4);
                    }
                }
            } else if (v.getId() == R.id.space_2_5) {
                try {
                    if (gameBoard.getSpace(2, 5).getPiece().getColor() != myColor) {
                        endSpace = gameBoard.getSpace(2, 5);
                    }
                } catch (Exception e) {
                    if(gameBoard.getSpace(2, 5).getPiece() == null) {
                        endSpace = gameBoard.getSpace(2, 5);
                    }
                }
            } else if (v.getId() == R.id.space_2_6) {
                try {
                    if (gameBoard.getSpace(2, 6).getPiece().getColor() != myColor) {
                        endSpace = gameBoard.getSpace(2, 6);
                    }
                } catch (Exception e) {
                    if(gameBoard.getSpace(2, 6).getPiece() == null) {
                        endSpace = gameBoard.getSpace(2, 6);
                    }
                }
            } else if (v.getId() == R.id.space_2_7) {
                try {
                    if (gameBoard.getSpace(2, 7).getPiece().getColor() != myColor) {
                        endSpace = gameBoard.getSpace(2, 7);
                    }
                } catch (Exception e) {
                    if(gameBoard.getSpace(2, 7).getPiece() == null) {
                        endSpace = gameBoard.getSpace(2, 7);
                    }
                }
            }
            if (v.getId() == R.id.space_3_0) {
                try {
                    if (gameBoard.getSpace(3, 0).getPiece().getColor() != myColor) {
                        endSpace = gameBoard.getSpace(3, 0);
                    }
                } catch (Exception e) {
                    if(gameBoard.getSpace(3, 0).getPiece() == null) {
                        endSpace = gameBoard.getSpace(3, 0);
                    }
                }
            } else if (v.getId() == R.id.space_3_1) {
                try {
                    if (gameBoard.getSpace(3, 1).getPiece().getColor() != myColor) {
                        endSpace = gameBoard.getSpace(3, 1);
                    }
                } catch (Exception e) {
                    if(gameBoard.getSpace(3, 1).getPiece() == null) {
                        endSpace = gameBoard.getSpace(3, 1);
                    }
                }
            } else if (v.getId() == R.id.space_3_2) {
                try {
                    if (gameBoard.getSpace(3, 2).getPiece().getColor() != myColor) {
                        endSpace = gameBoard.getSpace(3, 2);
                    }
                } catch (Exception e) {
                    if(gameBoard.getSpace(3, 2).getPiece() == null) {
                        endSpace = gameBoard.getSpace(3, 2);
                    }
                }
            } else if (v.getId() == R.id.space_3_3) {
                try {
                    if (gameBoard.getSpace(3, 3).getPiece().getColor() != myColor) {
                        endSpace = gameBoard.getSpace(3, 3);
                    }
                } catch (Exception e) {
                    if(gameBoard.getSpace(3, 3).getPiece() == null) {
                        endSpace = gameBoard.getSpace(3, 3);
                    }
                }
            } else if (v.getId() == R.id.space_3_4) {
                try {
                    if (gameBoard.getSpace(3, 4).getPiece().getColor() != myColor) {
                        endSpace = gameBoard.getSpace(3, 4);
                    }
                } catch (Exception e) {
                    if(gameBoard.getSpace(3, 4).getPiece() == null) {
                        endSpace = gameBoard.getSpace(3, 4);
                    }
                }
            } else if (v.getId() == R.id.space_3_5) {
                try {
                    if (gameBoard.getSpace(3, 5).getPiece().getColor() != myColor) {
                        endSpace = gameBoard.getSpace(3, 5);
                    }
                } catch (Exception e) {
                    if(gameBoard.getSpace(3, 5).getPiece() == null) {
                        endSpace = gameBoard.getSpace(3, 5);
                    }
                }
            } else if (v.getId() == R.id.space_3_6) {
                try {
                    if (gameBoard.getSpace(3, 6).getPiece().getColor() != myColor) {
                        endSpace = gameBoard.getSpace(3, 6);
                    }
                } catch (Exception e) {
                    if(gameBoard.getSpace(3, 6).getPiece() == null) {
                        endSpace = gameBoard.getSpace(3, 6);
                    }
                }
            } else if (v.getId() == R.id.space_3_7) {
                try {
                    if (gameBoard.getSpace(3, 7).getPiece().getColor() != myColor) {
                        endSpace = gameBoard.getSpace(3, 7);
                    }
                } catch (Exception e) {
                    if(gameBoard.getSpace(3, 7).getPiece() == null) {
                        endSpace = gameBoard.getSpace(3, 7);
                    }
                }
            }
            if (v.getId() == R.id.space_4_0) {
                try {
                    if (gameBoard.getSpace(4, 0).getPiece().getColor() != myColor) {
                        endSpace = gameBoard.getSpace(4, 0);
                    }
                } catch (Exception e) {
                    if(gameBoard.getSpace(4, 0).getPiece() == null) {
                        endSpace = gameBoard.getSpace(4, 0);
                    }
                }
            } else if (v.getId() == R.id.space_4_1) {
                try {
                    if (gameBoard.getSpace(4, 1).getPiece().getColor() != myColor) {
                        endSpace = gameBoard.getSpace(4, 1);
                    }
                } catch (Exception e) {
                    if(gameBoard.getSpace(4, 1).getPiece() == null) {
                        endSpace = gameBoard.getSpace(4, 1);
                    }
                }
            } else if (v.getId() == R.id.space_4_2) {
                try {
                    if (gameBoard.getSpace(4, 2).getPiece().getColor() != myColor) {
                        endSpace = gameBoard.getSpace(4, 2);
                    }
                } catch (Exception e) {
                    if(gameBoard.getSpace(4, 2).getPiece() == null) {
                        endSpace = gameBoard.getSpace(4, 2);
                    }
                }
            } else if (v.getId() == R.id.space_4_3) {
                try {
                    if (gameBoard.getSpace(4, 3).getPiece().getColor() != myColor) {
                        endSpace = gameBoard.getSpace(4, 3);
                    }
                } catch (Exception e) {
                    if(gameBoard.getSpace(4, 3).getPiece() == null) {
                        endSpace = gameBoard.getSpace(4, 3);
                    }
                }
            } else if (v.getId() == R.id.space_4_4) {
                try {
                    if (gameBoard.getSpace(4, 4).getPiece().getColor() != myColor) {
                        endSpace = gameBoard.getSpace(4, 4);
                    }
                } catch (Exception e) {
                    if(gameBoard.getSpace(4, 4).getPiece() == null) {
                        endSpace = gameBoard.getSpace(4, 4);
                    }
                }
            } else if (v.getId() == R.id.space_4_5) {
                try {
                    if (gameBoard.getSpace(4, 5).getPiece().getColor() != myColor) {
                        endSpace = gameBoard.getSpace(4, 5);
                    }
                } catch (Exception e) {
                    if(gameBoard.getSpace(4, 5).getPiece() == null) {
                        endSpace = gameBoard.getSpace(4, 5);
                    }
                }
            } else if (v.getId() == R.id.space_4_6) {
                try {
                    if (gameBoard.getSpace(4, 6).getPiece().getColor() != myColor) {
                        endSpace = gameBoard.getSpace(4, 6);
                    }
                } catch (Exception e) {
                    if(gameBoard.getSpace(4, 6).getPiece() == null) {
                        endSpace = gameBoard.getSpace(4, 6);
                    }
                }
            } else if (v.getId() == R.id.space_4_7) {
                try {
                    if (gameBoard.getSpace(4, 7).getPiece().getColor() != myColor) {
                        endSpace = gameBoard.getSpace(4, 7);
                    }
                } catch (Exception e) {
                    if(gameBoard.getSpace(4, 7).getPiece() == null) {
                        endSpace = gameBoard.getSpace(4, 7);
                    }
                }
            }
            if (v.getId() == R.id.space_5_0) {
                try {
                if (gameBoard.getSpace(5, 0).getPiece().getColor() != myColor) {
                    endSpace = gameBoard.getSpace(5, 0);
                }
                } catch (Exception e) {
                    if(gameBoard.getSpace(5, 0).getPiece() == null) {
                        endSpace = gameBoard.getSpace(5, 0);
                    }
                }
            } else if (v.getId() == R.id.space_5_1) {
                try {
                    if (gameBoard.getSpace(5, 1).getPiece().getColor() != myColor) {
                        endSpace = gameBoard.getSpace(5, 1);
                    }
                } catch (Exception e) {
                    if(gameBoard.getSpace(5, 1).getPiece() == null) {
                        endSpace = gameBoard.getSpace(5, 1);
                    }
                }
            } else if (v.getId() == R.id.space_5_2) {
                try {
                    if (gameBoard.getSpace(5, 2).getPiece().getColor() != myColor) {
                        endSpace = gameBoard.getSpace(5, 2);
                    }
                } catch (Exception e) {
                    if(gameBoard.getSpace(5, 2).getPiece() == null) {
                        endSpace = gameBoard.getSpace(5, 2);
                    }
                }
            } else if (v.getId() == R.id.space_5_3) {
                try {
                    if (gameBoard.getSpace(5, 3).getPiece().getColor() != myColor) {
                        endSpace = gameBoard.getSpace(5, 3);
                    }
                } catch (Exception e) {
                    if(gameBoard.getSpace(5, 3).getPiece() == null) {
                        endSpace = gameBoard.getSpace(5, 3);
                    }
                }
            } else if (v.getId() == R.id.space_5_4) {
                try {
                    if (gameBoard.getSpace(5, 4).getPiece().getColor() != myColor) {
                        endSpace = gameBoard.getSpace(5, 4);
                    }
                } catch (Exception e) {
                    if(gameBoard.getSpace(5, 4).getPiece() == null) {
                        endSpace = gameBoard.getSpace(5, 4);
                    }
                }
            } else if (v.getId() == R.id.space_5_5) {
                try {
                    if (gameBoard.getSpace(5, 5).getPiece().getColor() != myColor) {
                        endSpace = gameBoard.getSpace(5, 5);
                    }
                } catch (Exception e) {
                    if(gameBoard.getSpace(5, 5).getPiece() == null) {
                        endSpace = gameBoard.getSpace(5, 5);
                    }
                }
            } else if (v.getId() == R.id.space_5_6) {
                try {
                    if (gameBoard.getSpace(5, 6).getPiece().getColor() != myColor) {
                        endSpace = gameBoard.getSpace(5, 6);
                    }
                } catch (Exception e) {
                    if(gameBoard.getSpace(5, 6).getPiece() == null) {
                        endSpace = gameBoard.getSpace(5, 6);
                    }
                }
            } else if (v.getId() == R.id.space_5_7) {
                try {
                    if (gameBoard.getSpace(5, 7).getPiece().getColor() != myColor) {
                        endSpace = gameBoard.getSpace(5, 7);
                    }
                } catch (Exception e) {
                    if(gameBoard.getSpace(5, 7).getPiece() == null) {
                        endSpace = gameBoard.getSpace(5, 7);
                    }
                }
            }
            if (v.getId() == R.id.space_6_0) {
                try {
                    if (gameBoard.getSpace(6, 0).getPiece().getColor() != myColor) {
                        endSpace = gameBoard.getSpace(6, 0);
                    }
                } catch (Exception e) {
                    if(gameBoard.getSpace(6, 0).getPiece() == null) {
                        endSpace = gameBoard.getSpace(6, 0);
                    }
                }
            } else if (v.getId() == R.id.space_6_1) {
                try {
                    if (gameBoard.getSpace(6, 1).getPiece().getColor() != myColor) {
                        endSpace = gameBoard.getSpace(6, 1);
                    }
                } catch (Exception e) {
                    if(gameBoard.getSpace(6, 1).getPiece() == null) {
                        endSpace = gameBoard.getSpace(6, 1);
                    }
                }
            } else if (v.getId() == R.id.space_6_2) {
                try {
                    if (gameBoard.getSpace(6, 2).getPiece().getColor() != myColor) {
                        endSpace = gameBoard.getSpace(6, 2);
                    }
                } catch (Exception e) {
                    if(gameBoard.getSpace(6, 2).getPiece() == null) {
                        endSpace = gameBoard.getSpace(6, 2);
                    }
                }
            } else if (v.getId() == R.id.space_6_3) {
                try {
                    if (gameBoard.getSpace(6, 3).getPiece().getColor() != myColor) {
                        endSpace = gameBoard.getSpace(6, 3);
                    }
                } catch (Exception e) {
                    if(gameBoard.getSpace(6, 3).getPiece() == null) {
                        endSpace = gameBoard.getSpace(6, 3);
                    }
                }
            } else if (v.getId() == R.id.space_6_4) {
                try {
                    if (gameBoard.getSpace(6, 4).getPiece().getColor() != myColor) {
                        endSpace = gameBoard.getSpace(6, 4);
                    }
                } catch (Exception e) {
                    if(gameBoard.getSpace(6, 4).getPiece() == null) {
                        endSpace = gameBoard.getSpace(6, 4);
                    }
                }
            } else if (v.getId() == R.id.space_6_5) {
                try {
                    if (gameBoard.getSpace(6, 5).getPiece().getColor() != myColor) {
                        endSpace = gameBoard.getSpace(6, 5);
                    }
                } catch (Exception e) {
                    if(gameBoard.getSpace(6, 5).getPiece() == null) {
                        endSpace = gameBoard.getSpace(6, 5);
                    }
                }
            } else if (v.getId() == R.id.space_6_6) {
                try {
                    if (gameBoard.getSpace(6, 6).getPiece().getColor() != myColor) {
                        endSpace = gameBoard.getSpace(6, 6);
                    }
                } catch (Exception e) {
                    if(gameBoard.getSpace(6, 6).getPiece() == null) {
                        endSpace = gameBoard.getSpace(6, 6);
                    }
                }
            } else if (v.getId() == R.id.space_6_7) {
                try {
                    if (gameBoard.getSpace(6, 7).getPiece().getColor() != myColor) {
                        endSpace = gameBoard.getSpace(6, 7);
                    }
                } catch (Exception e) {
                    if(gameBoard.getSpace(6, 7).getPiece() == null) {
                        endSpace = gameBoard.getSpace(6, 7);
                    }
                }
            }
            if (v.getId() == R.id.space_7_0) {
                try {
                    if (gameBoard.getSpace(7, 0).getPiece().getColor() != myColor) {
                        endSpace = gameBoard.getSpace(7, 0);
                    }
                } catch (Exception e) {
                    if(gameBoard.getSpace(7, 0).getPiece() == null) {
                        endSpace = gameBoard.getSpace(7, 0);
                    }
                }
            } else if (v.getId() == R.id.space_7_1) {
                try {
                    if (gameBoard.getSpace(7, 1).getPiece().getColor() != myColor) {
                        endSpace = gameBoard.getSpace(7, 1);
                    }
                } catch (Exception e) {
                    if(gameBoard.getSpace(7, 1).getPiece() == null) {
                        endSpace = gameBoard.getSpace(7, 1);
                    }
                }
            } else if (v.getId() == R.id.space_7_2) {
                try {
                    if (gameBoard.getSpace(7, 2).getPiece().getColor() != myColor) {
                        endSpace = gameBoard.getSpace(7, 2);
                    }
                } catch (Exception e) {
                    if(gameBoard.getSpace(7, 2).getPiece() == null) {
                        endSpace = gameBoard.getSpace(7, 2);
                    }
                }
            } else if (v.getId() == R.id.space_7_3) {
                try {
                    if (gameBoard.getSpace(7, 3).getPiece().getColor() != myColor) {
                        endSpace = gameBoard.getSpace(7, 3);
                    }
                } catch (Exception e) {
                    if(gameBoard.getSpace(7, 3).getPiece() == null) {
                        endSpace = gameBoard.getSpace(7, 3);
                    }
                }
            } else if (v.getId() == R.id.space_7_4) {
                try {
                    if (gameBoard.getSpace(7, 4).getPiece().getColor() != myColor) {
                        endSpace = gameBoard.getSpace(7, 4);
                    }
                } catch (Exception e) {
                    if(gameBoard.getSpace(7, 4).getPiece() == null) {
                        endSpace = gameBoard.getSpace(7, 4);
                    }
                }
            } else if (v.getId() == R.id.space_7_5) {
                try {
                    if (gameBoard.getSpace(7, 5).getPiece().getColor() != myColor) {
                        endSpace = gameBoard.getSpace(7, 5);
                    }
                } catch (Exception e) {
                    if(gameBoard.getSpace(7, 5).getPiece() == null) {
                        endSpace = gameBoard.getSpace(7, 5);
                    }
                }
            } else if (v.getId() == R.id.space_7_6) {
                try {
                    if (gameBoard.getSpace(7, 6).getPiece().getColor() != myColor) {
                        endSpace = gameBoard.getSpace(7, 6);
                    }
                } catch (Exception e) {
                    if(gameBoard.getSpace(7, 6).getPiece() == null) {
                        endSpace = gameBoard.getSpace(7, 6);
                    }
                }
            } else if (v.getId() == R.id.space_7_7) {
                try {
                    if (gameBoard.getSpace(7, 7).getPiece().getColor() != myColor) {
                        endSpace = gameBoard.getSpace(7, 7);
                    }
                } catch (Exception e) {
                    if(gameBoard.getSpace(7, 7).getPiece() == null) {
                        endSpace = gameBoard.getSpace(7, 7);
                    }
                }
            }
        }

        if(endSpace != null) {
            Piece piece = startSpace.getPiece();

            try {
                piece.moveTo(gameBoard, startSpace, endSpace, myColor);
                //add user to this somehow, prolly getUser request
                //creates move to send to server through websocket
                Move move = new Move(myColor, startSpace, endSpace, false, false);
                //turns it into a string and sends it to server through websocket
                WebSocketManager.getInstance().sendMessage(move.toString());
            } catch (Exception e) {
                Log.d("ExceptionSendMessage:", e.getMessage().toString());
            }

            //UPDATING BOARD
            //check game logic (pawn promotion)
            GameLogic.pawnPromotion(gameBoard, myColor);
            //redraw board upon successful move
            drawPieces(gameBoard, dynamicLayout);

            //Reset start and end spaces
            startSpace = null;
            endSpace = null;
        }
    }



        //Make possible to send strings.
        @Override
        public void onWebSocketMessage (String message){
            /**
             * In Android, all UI-related operations must be performed on the main UI thread
             * to ensure smooth and responsive user interfaces. The 'runOnUiThread' method
             * is used to post a runnable to the UI thread's message queue, allowing UI updates
             * to occur safely from a background or non-UI thread.
             */
            runOnUiThread(() -> {
                String s = message;
            });
        }

        @Override
        public void onWebSocketClose ( int code, String reason,boolean remote){
            String closedBy = remote ? "server" : "local";
            runOnUiThread(() -> {
                String s = msgTextView.getText().toString();
                msgTextView.setText(s + "---\nconnection closed by " + closedBy + "\nreason: " + reason);
            });
        }

        @Override
        public void onWebSocketOpen (ServerHandshake handshakedata){
        }

        @Override
        public void onWebSocketError (Exception ex){
        }


        /**
         *
         * Draws a piece on a space depending on the status of that space.
         * May have to change so it returns an array or set of variables.
         * @param board
         * @param layout
         */
        public void drawPieces (Board board, ConstraintLayout layout){

            //invalidate the view(refresh screen)
            layout.removeAllViews();

            //DRAW PIECES
            //for each space, draw corresponding piece
            for (int r = 0; r < 8; r++) { //row
                for (int c = 0; c < 8; c++) { //col
                    ImageView i;

                    //CHECKING WHICH PIECE TO DRAW
                    if (board.getSpace(r, c).getPiece() == null) { //SKIPS SPACES WITHOUT PIECES
                        //add space(button)
                        continue;
                    } else if (board.getSpace(r, c).getPiece().getPieceType() == ENUM_TYPES.PAWN
                            && board.getSpace(r, c).getPiece().getColor() == "WHITE") {
                        // Instantiate an ImageView and define its properties
                        i = new ImageView(this);
                        i.setImageResource(R.drawable.white_pawn);
                    } else if (board.getSpace(r, c).getPiece().getPieceType() == ENUM_TYPES.PAWN
                            && board.getSpace(r, c).getPiece().getColor() == "BLACK") {
                        // Instantiate an ImageView and define its properties
                        i = new ImageView(this);
                        i.setImageResource(R.drawable.black_pawn);
                    } else if (board.getSpace(r, c).getPiece().getPieceType() == ENUM_TYPES.ROOK
                            && board.getSpace(r, c).getPiece().getColor() == "WHITE") {
                        // Instantiate an ImageView and define its properties
                        i = new ImageView(this);
                        i.setImageResource(R.drawable.white_rook);
                    } else if (board.getSpace(r, c).getPiece().getPieceType() == ENUM_TYPES.ROOK
                            && board.getSpace(r, c).getPiece().getColor() == "BLACK") {
                        // Instantiate an ImageView and define its properties
                        i = new ImageView(this);
                        i.setImageResource(R.drawable.black_rook);
                    } else if (board.getSpace(r, c).getPiece().getPieceType() == ENUM_TYPES.BISHOP
                            && board.getSpace(r, c).getPiece().getColor() == "WHITE") {
                        // Instantiate an ImageView and define its properties
                        i = new ImageView(this);
                        i.setImageResource(R.drawable.white_bishop);
                    } else if (board.getSpace(r, c).getPiece().getPieceType() == ENUM_TYPES.BISHOP
                            && board.getSpace(r, c).getPiece().getColor() == "BLACK") {
                        // Instantiate an ImageView and define its properties
                        i = new ImageView(this);
                        i.setImageResource(R.drawable.black_bishop);
                    } else if (board.getSpace(r, c).getPiece().getPieceType() == ENUM_TYPES.KNIGHT
                            && board.getSpace(r, c).getPiece().getColor() == "WHITE") {
                        // Instantiate an ImageView and define its properties
                        i = new ImageView(this);
                        i.setImageResource(R.drawable.white_knight);
                    } else if (board.getSpace(r, c).getPiece().getPieceType() == ENUM_TYPES.KNIGHT
                            && board.getSpace(r, c).getPiece().getColor() == "BLACK") {
                        // Instantiate an ImageView and define its properties
                        i = new ImageView(this);
                        i.setImageResource(R.drawable.black_knight);
                    } else if (board.getSpace(r, c).getPiece().getPieceType() == ENUM_TYPES.QUEEN
                            && board.getSpace(r, c).getPiece().getColor() == "WHITE") {
                        // Instantiate an ImageView and define its properties
                        i = new ImageView(this);
                        i.setImageResource(R.drawable.white_queen);
                    } else if (board.getSpace(r, c).getPiece().getPieceType() == ENUM_TYPES.QUEEN
                            && board.getSpace(r, c).getPiece().getColor() == "BLACK") {
                        // Instantiate an ImageView and define its properties
                        i = new ImageView(this);
                        i.setImageResource(R.drawable.black_queen);
                    } else if (board.getSpace(r, c).getPiece().getPieceType() == ENUM_TYPES.KING
                            && board.getSpace(r, c).getPiece().getColor() == "WHITE") {
                        // Instantiate an ImageView and define its properties
                        i = new ImageView(this);
                        i.setImageResource(R.drawable.white_king);
                    } else { //BLACK KING
                        // Instantiate an ImageView and define its properties
                        i = new ImageView(this);
                        i.setImageResource(R.drawable.black_king);
                    }
                    //i.setContentDescription(getResources().getString(R.string.my_image_desc));

                    // set the ImageView bounds to match the squares dimensions.
                    i.setAdjustViewBounds(true);
                    i.setLayoutParams(new ViewGroup.LayoutParams(
                            ViewGroup.LayoutParams.WRAP_CONTENT,
                            ViewGroup.LayoutParams.WRAP_CONTENT));

                    //set location according to space
                    //HOW DO I SET ON BUTTONS
                    i.setX((c * 133) + 40);
                    i.setY((r * 130) + 530);

                    // Add the ImageView to the layout.
                    layout.addView(i, 80, 80);
                }
            }
        }


    }