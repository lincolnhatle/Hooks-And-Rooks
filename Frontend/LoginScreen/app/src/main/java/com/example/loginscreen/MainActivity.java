package com.example.loginscreen;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {


    TextView username,password;

    Button btnLogin, btnSignUp;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        username=findViewById(R.id.editTextUsername);
        password=findViewById(R.id.editTextPassword);
        btnLogin=findViewById(R.id.buttonLogin);
        btnSignUp=findViewById(R.id.buttonSignUp);

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String usr = username.getText().toString();
                String pwd = password.getText().toString();

//                Intent intent = new Intent(LoginActivity.this, HomeActivity.class);
//                intent.putExtra("USERNAME", username);  // key-value to pass to the MainActivity
//                intent.putExtra("PASSWORD", password);  // key-value to pass to the MainActivity
//                startActivity(intent);  // go to HomeActivity with the key-value data
            }
        });

        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Intent intent = new Intent(LoginActivity.this, SignUpActivity.class); //go to sign up screen
            }
        });

    }
}