package com.example.test;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.graphics.Color;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class Login extends AppCompatActivity {
    TextView send_otp;
    Button login_btn;
    int counter;
    EditText enter_otp, enter_mob;
    CountDownTimer countDownTimer;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        send_otp=findViewById(R.id.send_otp_text);
        login_btn=findViewById(R.id.login_btn);
        enter_otp=findViewById(R.id.enter_otp);
        enter_mob=findViewById(R.id.enter_mob);


        send_otp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(enter_mob.getText().length()==10)
                {
                    counter=30;
                    send_otp.setClickable(false);

                    new CountDownTimer(30000,1000){

                        @Override
                        public void onTick(long millisUntilFinished) {
                            send_otp.setText("Send OTP again in "+String.valueOf(counter)+" seconds");
                            send_otp.setTextColor(ContextCompat.getColor(Login.this,R.color.gray));

                            counter--;

                        }

                        @Override
                        public void onFinish() {
                            send_otp.setText("Send OTP");
                            send_otp.setTextColor(ContextCompat.getColor(Login.this,R.color.blue));
                            send_otp.setClickable(true);

                        }
                    }.start();
                    send_otp.setClickable(false);
                    enter_otp.setVisibility(View.VISIBLE);
                    login_btn.setVisibility(View.VISIBLE);
                }
                else {
                    Toast.makeText(Login.this, "Please Enter Valid 10 Digit Mobile Number", Toast.LENGTH_SHORT).show();
                }




            }
        });

        login_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(enter_otp.getText().length()==5)
                {
                    login_btn.setText("Please Wait");
                    login_btn.setClickable(false);
                }
                else{
                    Toast.makeText(Login.this, "Enter Valid OTP", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
