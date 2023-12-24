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
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.hooks_and_rooks.utils.Const;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class SignUpActivity extends AppCompatActivity {

    private TextView msgResponse;
    private EditText username, password;
    private String usr, pwd;
    private Button btnSignUp;
    private String TAG = SignUpActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        username=findViewById(R.id.editTextUsername);
        password=findViewById(R.id.editTextPassword);
        msgResponse=findViewById(R.id.textViewJSONObject);
        btnSignUp=findViewById(R.id.buttonSignUp);


        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                usr = username.getText().toString();
                pwd = password.getText().toString();
                registerUser();
            }
        });
    }

    private void registerUser() {
        //POPULATING JSON REQUEST BODY
        HashMap<String, String> params = new HashMap<String, String>();
        params.put("username", usr);
        params.put("password", pwd);

        JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.POST,
                Const.URL_JSON_USER, new JSONObject(params),
                new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        Log.d(TAG, response.toString());
                        //make a new JSON object user with the username and password provided
                        //JSONObject user = new JSONObject();

                        msgResponse.setText(response.toString());

                        Intent intent = new Intent(SignUpActivity.this, LoginActivity.class);
                        startActivity(intent);
                    }
                }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.d(TAG, "Error: " + error.getMessage());
                msgResponse.setText(error.toString());
            }
        });

        //Volley.newRequestQueue(this).add(jsonObjReq);
        VolleySingleton.getInstance(getApplicationContext()).addToRequestQueue(jsonObjReq);
    }

}