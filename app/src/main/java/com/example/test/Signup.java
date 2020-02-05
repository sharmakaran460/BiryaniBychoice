package com.example.test;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class Signup extends AppCompatActivity {

    TextView username ,password,otp;
    Button signupBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);

        username = findViewById(R.id.signupUsername);
        password= findViewById(R.id.signUppassword);
        otp= findViewById(R.id.signupOtp);
        signupBtn = findViewById(R.id.btn_singup);

        signupBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                username.setText("username");
                password.setText("password");
                otp.setText(String.valueOf(1234));
                Toast.makeText(Signup.this, "han ye chal raha hai ", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
