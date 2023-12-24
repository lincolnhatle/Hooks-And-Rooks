package com.example.androidexample;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.java_websocket.handshake.ServerHandshake;
import org.w3c.dom.Text;

public class MainActivity extends AppCompatActivity implements WebSocketListener{

    private String BASE_URL = "ws://10.48.101.1:8080/chat/";
   // private String BASE_URL = "ws://10.0.2.2:8080/chat/";

    private Button connectBtn, sendBtn, disconnectBtn;
    private EditText usernameEtx, msgEtx;
    private TextView msgTv,typingTv;
    protected TextView rdEtx;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        /* initialize UI elements */
        connectBtn = (Button) findViewById(R.id.bt1);
        sendBtn = (Button) findViewById(R.id.bt2);
        disconnectBtn = (Button) findViewById(R.id.btn3);
        usernameEtx = (EditText) findViewById(R.id.et1);
        msgEtx = (EditText) findViewById(R.id.et2);
        typingTv = (TextView) findViewById(R.id.connect);
        msgTv = (TextView) findViewById(R.id.tx1);
        rdEtx = (TextView) findViewById(R.id.Read);


        /* connect button listener */
        connectBtn.setOnClickListener(view -> {
            String serverUrl = BASE_URL + usernameEtx.getText().toString();

            // Establish WebSocket connection and set listener
            WebSocketManager.getInstance().connectWebSocket(serverUrl);
            WebSocketManager.getInstance().setWebSocketListener(MainActivity.this);
            rdEtx.setText("Read");
            msgTv.setText("Chat: \n");
        });

        disconnectBtn.setOnClickListener(view -> {
            WebSocketManager.getInstance().disconnectWebSocket();
            rdEtx.setText("");
            msgTv.setText("Chat: \n");
        });

        msgEtx.addTextChangedListener(new TextWatcher() {
            @Override
            // when there is no text added
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                if (s.toString().trim().length() == 0) {
                    // set text to Not typing
                    typingTv.setText("Not Typing");
                } else {
                    // set text to typing
                    typingTv.setText(" Typing");
                }
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                typingTv.setText(" Typing");
            }

            // after we input some text
            @Override
            public void afterTextChanged(Editable s) {
                if (s.toString().trim().length() == 0) {
                    // set text to Stopped typing
                    typingTv.setText("Stopped Typing");
                }


                /* send button listener */
                sendBtn.setOnClickListener(v -> {
                    try {

                        // send message
                        WebSocketManager.getInstance().sendMessage(msgEtx.getText().toString());
                    } catch (Exception e) {
                        Log.d("ExceptionSendMessage:", e.getMessage().toString());
                    }
                });
            }
        });
    }



    @Override
    public void onWebSocketMessage(String message) {
        /**
         * In Android, all UI-related operations must be performed on the main UI thread
         * to ensure smooth and responsive user interfaces. The 'runOnUiThread' method
         * is used to post a runnable to the UI thread's message queue, allowing UI updates
         * to occur safely from a background or non-UI thread.
         */
        runOnUiThread(() -> {
            String s = msgTv.getText().toString();
            msgTv.setText(s + "\n"+message);
        });
    }

    @Override
    public void onWebSocketClose(int code, String reason, boolean remote) {
        String closedBy = remote ? "server" : "local";
        runOnUiThread(() -> {
            String s = msgTv.getText().toString();
            msgTv.setText(s + "---\nconnection closed by " + closedBy + "\nreason: " + reason);
        });
    }

    @Override
    public void onWebSocketOpen(ServerHandshake handshakedata) {
      //  rdEtx.setText("Read");

    }

    @Override
    public void onWebSocketError(Exception ex) {}
}
