package com.pks.callingapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.firebase.FirebaseException;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;

import java.util.concurrent.TimeUnit;

public class SendOTPActivity extends AppCompatActivity {
    private EditText countryET, phoneET;
    private Button btn_getOTP;
    String countryCode,phoneNumber;
    private ProgressBar progressbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_send_o_t_p);
        init();

        btn_getOTP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                countryCode = countryET.getText().toString().trim();
                phoneNumber = phoneET.getText().toString().trim();
              boolean var= varify(countryCode,phoneNumber);
                String theNumber = "+"+countryCode+phoneNumber;
                //Toast.makeText(SendOTPActivity.this,theNumber+"", Toast.LENGTH_SHORT).show();

                progressbar.setVisibility(View.VISIBLE);
                btn_getOTP.setVisibility(View.INVISIBLE);
                PhoneAuthProvider.getInstance().verifyPhoneNumber(
                        theNumber,
                        60,
                        TimeUnit.SECONDS,
                        SendOTPActivity.this,
                        new PhoneAuthProvider.OnVerificationStateChangedCallbacks(){

                            @Override
                            public void onVerificationCompleted(@NonNull PhoneAuthCredential phoneAuthCredential) {

                                progressbar.setVisibility(View.GONE);
                                btn_getOTP.setVisibility(View.VISIBLE);
                            }

                            @Override
                            public void onVerificationFailed(@NonNull FirebaseException e) {
                                progressbar.setVisibility(View.GONE);
                                btn_getOTP.setVisibility(View.VISIBLE);
                                Toast.makeText(SendOTPActivity.this,e.getMessage(), Toast.LENGTH_SHORT).show();


                            }

                            @Override
                            public void onCodeSent(@NonNull String varificationId, @NonNull PhoneAuthProvider.ForceResendingToken forceResendingToken) {
                               // super.onCodeSent(varificationId, forceResendingToken);
                                progressbar.setVisibility(View.GONE);
                                btn_getOTP.setVisibility(View.VISIBLE);
                                if(var==true) {
                                    Intent intent = new Intent(SendOTPActivity.this, VarifyOTPActivity.class);
                                    intent.putExtra("mobileNo", theNumber);
                                    intent.putExtra("varificationId",varificationId);
                                    startActivity(intent);
                                }


                            }
                        }
                );


            }
        });

    }

    private boolean varify(String countryCode, String phoneNumber) {
        if(countryET.getText().toString().trim().isEmpty()){
            Toast.makeText(SendOTPActivity.this,"Country code is empty", Toast.LENGTH_SHORT).show();
            //  countryET.setFocusable(true);
            countryET.requestFocus();
            return false;
        }
        else if(countryCode.length()!=2){
            Toast.makeText(SendOTPActivity.this,"countrycode needs 11 digits", Toast.LENGTH_SHORT).show();
            // countryET.setFocusable(true);
            countryET.requestFocus();
            return false;
        }
        if(phoneNumber.isEmpty()){
            Toast.makeText(SendOTPActivity.this,"phoneNumber is empty", Toast.LENGTH_SHORT).show();
            // phoneET.setFocusable(true);
            phoneET.requestFocus();

            return false;
        }
        else if(phoneNumber.length()!=11){
            Toast.makeText(SendOTPActivity.this,"phone number must have 11 digits", Toast.LENGTH_SHORT).show();
            // phoneNumber.seFoc(true);
            phoneET.requestFocus();

            return false;
        }
return true;
    }


    void init(){
        countryET = findViewById(R.id.countryET);
        phoneET = findViewById(R.id.phoneET);
        btn_getOTP = findViewById(R.id.btn_getOTP);
        progressbar = findViewById(R.id.progressbar);
    }
}