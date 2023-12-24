package com.example.hooks_and_rooks;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;
import com.example.hooks_and_rooks.utils.Const;

import org.json.JSONException;
import org.json.JSONObject;

public class DeleteActivity extends AppCompatActivity {
    private TextView msgResponse;

    private Button btnHome, btnDelete;

    private String URL = Const.URL_JSON_USER; //will have to tag on id to delete the correct user
    private String TAG = DeleteActivity.class.getSimpleName();

    private UserSingleton user;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delete);

        msgResponse=findViewById(R.id.textViewResponse);
        btnHome=findViewById(R.id.buttonHome);
        btnDelete=findViewById(R.id.buttonDelete);
        try {
            user = UserSingleton.getInstance();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        btnHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(DeleteActivity.this, HomeActivity.class);
                startActivity(intent);
            }
        });

        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //should be something like URL +user_id
                try {
                    URL = URL + "/" + UserSingleton.getId();
                } catch (JSONException e) {
                    throw new RuntimeException(e);
                }
                deleteUser(); //is a new user 9 being created in backend
                //MAKE ONE QUEUE WITH SINGLETON
                Intent intent = new Intent(DeleteActivity.this, LoginActivity.class);
                startActivity(intent);
            }
        });
    }

    /**
     * Deletes a user from the DB.
     */
    private void deleteUser() {
        JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.DELETE,
                URL, null,
                new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        Log.d(TAG, response.toString());
                        //prints out deleted user or a response that Ty and Josh set up?
                        msgResponse.setText(response.toString());
                    }
                }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.d(TAG, "Error: " + error.getMessage());
            }
        });


        //Volley.newRequestQueue(this).add(jsonObjReq);
        VolleySingleton.getInstance(getApplicationContext()).addToRequestQueue(jsonObjReq);
    }
}
