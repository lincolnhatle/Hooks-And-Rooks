package com.example.hooks_and_rooks;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.hooks_and_rooks.utils.Const;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class LoginActivity extends AppCompatActivity {

    private TextView msgResponse;
    private EditText username, password;
    private String usr, pwd;
    private Button btnLogin, btnSignUp;
    private String TAG = LoginActivity.class.getSimpleName();
    private String URL = Const.URL_JSON_USER; //will have to tag on id to pull the correct user
    private JSONArray userArray;
    protected int user_id;
    //user object
    //singleton class
    //singletons in tutorials

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        username = findViewById(R.id.editTextUsername);
        password = findViewById(R.id.editTextPassword);
        btnLogin = findViewById(R.id.buttonLogin);
        btnSignUp = findViewById(R.id.buttonSignUp);
        msgResponse = findViewById(R.id.textViewJSONObject);

        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LoginActivity.this, SignUpActivity.class); //go to sign up screen
                startActivity(intent);
            }
        });

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //initialize strings
                usr=username.getText().toString();
                pwd=password.getText().toString();
                //get user array and login in on response
                userArrayRequest();
                //check if user singleton got updated
//                if() {
//                    Intent intent = new Intent(LoginActivity.this, HomeActivity.class);
//                    startActivity(intent);
//                }
            }
        });
    }



    /**
    Requests a json object of a singular user
     */
    private void userObjRequest() {
        JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.GET,
                URL, null,
                new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        Log.d(TAG, response.toString());
                        msgResponse.setText(response.toString());
                    }
                }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.d(TAG, "Error: " + error.getMessage());
                msgResponse.setText("No such user found");
            }
        });

        // Adding request to request queue
        //Volley.newRequestQueue(this).add(jsonObjReq);
        VolleySingleton.getInstance(getApplicationContext()).addToRequestQueue(jsonObjReq);

    }



    /**
     * Making json array request of all users
     * */
    private void userArrayRequest() {
        JsonArrayRequest req = new JsonArrayRequest(Const.URL_JSON_USER, // /users is an array
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        Log.d(TAG, response.toString());
                        userArray=response;     //Sets the users JSONArray to the response we get from req
                        JSONObject loggedIn = getUser(usr,pwd,userArray);
                        msgResponse.setText(loggedIn.toString());
                        Intent intent = new Intent(LoginActivity.this, HomeActivity.class);
                        startActivity(intent);
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.d(TAG, "Error: " + error.getMessage());
                msgResponse.setText(error.toString());
            }
        });

        //Volley.newRequestQueue(this).add(req);
        VolleySingleton.getInstance(getApplicationContext()).addToRequestQueue(req);
    }



    /**
    Takes the username and password inputs and compares them to an array of JSON objects.
    Returns the user_id fo the first match.
     NEED TO CHANGE THIS TO RETURN A USER OBJECT
     */
    private JSONObject getUser(String USERNAME, String PASSWORD, JSONArray user_array) {

        //Increments through entire user_array and compares each object's
        //username and password key values with the given USERNAME and PASSWORD
        try {
        for (int i = 0; i < user_array.length(); i++) {
                if(USERNAME.equals(user_array.getJSONObject(i).getString("username"))) {
                    if(PASSWORD.equals(user_array.getJSONObject(i).getString("password"))) {
                        //set usersingleton
                        UserSingleton.getInstance(user_array.getJSONObject(i));
                        return user_array.getJSONObject(i);       //returns whole user
                    }
                }
        }
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }

        msgResponse.setText("Username or Password is Incorrect");
        return null;  //returns -1 if it can't find a match
    }

}