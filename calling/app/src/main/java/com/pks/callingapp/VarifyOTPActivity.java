package com.pks.callingapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;

import java.util.concurrent.TimeUnit;

public class VarifyOTPActivity extends AppCompatActivity {
    private EditText otpET;
    private Button btn_varify, btn_resend;
    private TextView thePhoneNo;
    String varificationId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_varify_o_t_p);
        init();
        String test =getIntent().getStringExtra("mobileNo");
        thePhoneNo.setText(getIntent().getStringExtra("mobileNo"));
        varificationId = getIntent().getStringExtra("varificationId");
       // Toast.makeText(VarifyOTPActivity.this,""+test, Toast.LENGTH_SHORT).show();

        btn_varify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               // Toast.makeText(VarifyOTPActivity.this,"countrycode needs 11 digits", Toast.LENGTH_SHORT).show();
            String code = otpET.getText().toString();
            if(code.length()<6){
                Toast.makeText(VarifyOTPActivity.this,"Enter all 6 digits", Toast.LENGTH_SHORT).show();
            }else {
                PhoneAuthCredential phoneAuthCredential = PhoneAuthProvider.getCredential(varificationId,code);
                FirebaseAuth.getInstance().signInWithCredential(phoneAuthCredential)
                        .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {

                                if(task.isSuccessful()){
                                    Intent intent = new Intent(VarifyOTPActivity.this,MainActivity.class);
                                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                    startActivity(intent);
                                }else {
                                    Toast.makeText(VarifyOTPActivity.this,"Code is invalid", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
            }
            }
        });

        btn_resend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                PhoneAuthProvider.getInstance().verifyPhoneNumber(
                        getIntent().getStringExtra("mobileNo"),
                        60,
                        TimeUnit.SECONDS,
                        VarifyOTPActivity.this,
                        new PhoneAuthProvider.OnVerificationStateChangedCallbacks(){

                            @Override
                            public void onVerificationCompleted(@NonNull PhoneAuthCredential phoneAuthCredential) {


                            }

                            @Override
                            public void onVerificationFailed(@NonNull FirebaseException e) {
                               /* progressbar.setVisibility(View.GONE);
                                btn_getOTP.setVisibility(View.VISIBLE);*/
                                Toast.makeText(VarifyOTPActivity.this,e.getMessage(), Toast.LENGTH_SHORT).show();


                            }

                            @Override
                            public void onCodeSent(@NonNull String newvarificationId, @NonNull PhoneAuthProvider.ForceResendingToken forceResendingToken) {
                                // super.onCodeSent(varificationId, forceResendingToken);

                                varificationId = newvarificationId;
                                Toast.makeText(VarifyOTPActivity.this,"Varification code resend", Toast.LENGTH_SHORT).show();



                            }
                        }
                );
            }
        });

    }

    void init(){
        otpET = findViewById(R.id.otpET);
        btn_varify = findViewById(R.id.btn_varify);
        thePhoneNo = findViewById(R.id.thePhoneNo);
        btn_resend = findViewById(R.id.btn_resend);
    }
}