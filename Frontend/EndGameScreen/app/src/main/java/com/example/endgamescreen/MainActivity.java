package com.example.endgamescreen;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;



public class MainActivity extends AppCompatActivity {

    private String URL = "http://coms-309-015.class.las.iastate.edu:8080/users";
    private String TAG = MainActivity.class.getSimpleName();
    private JSONObject match;

    //should be 13 objects
private Button home;

 //Button move_list;

 private TextView match_Result;

 //TextView opponent; name at the top

  private TextView match_time;

   private TextView punches_landed;

    private TextView opponent_punches_landed;

    private TextView pieces_taken;

    private TextView opponent_pieces_taken;

    private TextView rank;

    private TextView opponent_rank;

    private TextView opponent_new_rank;

    private TextView win_loss;

    private TextView opponent_win_loss;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        home = findViewById(R.id.buttonHome);
        match_Result = findViewById(R.id.textViewResult);
        match_time = findViewById(R.id.textViewMatchTime);
        punches_landed = findViewById(R.id.textViewPunchesLanded);
        win_loss = findViewById(R.id.textViewWinLoss);
        pieces_taken = findViewById(R.id.textViewPiecesTaken);
        rank = findViewById(R.id.textViewNewRank);
        opponent_rank = findViewById(R.id.textViewOpponentNewRank);
        opponent_pieces_taken = findViewById(R.id.textViewOpponentPiecesTaken);
        opponent_punches_landed = findViewById(R.id.textViewOpponentsPunchesLanded);
        opponent_new_rank = findViewById(R.id.textViewOpponentStats);
        opponent_win_loss = findViewById(R.id.textViewOpponentWinLoss);

        userObjRequest();

        //get punches
        match.getJSONObject(player1).getInt(punches);

        //get winner
        if (match.getJSONObject(player1).getBoolean(win) == true) {
            match_Result.setText("Winner");
        } if else(match.getJSONObject(player2).getBoolean(win) == true) {
            match_Result.setText("Winner");
        }
        else{
            match_Result.setText("Loser");
        }

        //get pieces taken
        match.getJSONObject(player).getInt(pieces);
    }



    /**
     Requests a json object of a singular user
     */
    private void userObjRequest() {
        // Create the request object
        JsonObjectRequest userDataRequest = new JsonObjectRequest(Request.Method.GET,
                URL, null,
                response -> {
                    Log.d(TAG, response.toString());
                    //msgResponse.setText(response.toString());
                    match = response;
                }, error -> {
            VolleyLog.d(TAG, "Error: " + error.getMessage());
            //msgResponse.setText("No such user found");
        });

        // Adding request to request queue
        Volley.newRequestQueue(this).add(userDataRequest);
        //VolleySingleton.getInstance(getApplicationContext()).addToRequestQueue(userDataRequest);
    }

    private JSONObject getInfo(JSONObject match) {
        match.getInt().getInt();
    };



    matchId.Player2.punches;


    public void onHomeButton(View view){
        Log.d(TAG, "Home Button Pressed");
    }
//    home.setOnClickListener(new View.OnClickerListener(){
//        @Override
//        public void onClick(View view){
//            Intent intent = new Intent(MainActivity.this, HomeScreen.class);
//            startActivity(intent);
//        }
//    });

}