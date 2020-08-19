package com.example.commonintentsandroid;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.app.DownloadManager;
import android.app.SearchManager;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.Queue;

public class MainActivity extends AppCompatActivity {
    EditText et_data;
    Button btn_web,btn_email,btn_dail,btn_call,btn_sms,btn_maps;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        et_data = findViewById(R.id.et_data);
        btn_web = findViewById(R.id.btn_web);
        btn_email = findViewById(R.id.btn_email);
        btn_dail = findViewById(R.id.btn_dail);
        btn_call = findViewById(R.id.btn_call);
        btn_sms = findViewById(R.id.btn_sms);
        btn_maps = findViewById(R.id.btn_maps);

        btn_web.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String url = et_data.getText().toString();
                if(url!=null) {

                if(!url.startsWith("http://") || !url.startsWith("https://")){
                    url ="http://"+ url;
                }

                    Uri webpage = Uri.parse(url);
                    Intent intent = new Intent(Intent.ACTION_VIEW, webpage);

                    //the folllwoing thing will ask for browser prefernce
                    if (intent.resolveActivity(getPackageManager()) != null) {
                        startActivity(intent);
                    }
                }
            }
        });

        btn_email.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = et_data.getText().toString();
                String[] addresses = new String[5];
                addresses[0]=email;

                composeEmail(addresses,"No subject");
            }
        });



        btn_dail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String phoneNumber = et_data.getText().toString();
                Intent intent = new Intent(Intent.ACTION_DIAL);
                intent.setData(Uri.parse("tel:" + phoneNumber));
                if (intent.resolveActivity(getPackageManager()) != null) {
                    startActivity(intent);
                }
            }
        });

        btn_call.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                onCallBtnClick();
                }



        });

        btn_sms.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               // public void composeMmsMessage(String message, Uri attachment) {

                String message ="User enters a messgae";
                String phoneNo = et_data.getText().toString();
                Intent intent = new Intent(Intent.ACTION_SENDTO);
                intent.setData(Uri.parse("smsto:"+phoneNo));  // This ensures only SMS apps respond
                intent.putExtra("sms_body", message);
               // intent.putExtra(Intent.EXTRA_STREAM, attachment);
                if (intent.resolveActivity(getPackageManager()) != null) {
                    startActivity(intent);
                }
              //  }
            }
        });
    }


        public void composeEmail(String[] addresses, String subject) {
            Intent intent = new Intent(Intent.ACTION_SENDTO);
            intent.setData(Uri.parse("mailto:")); // only email apps should handle this
            intent.putExtra(Intent.EXTRA_EMAIL, addresses);
            intent.putExtra(Intent.EXTRA_SUBJECT, subject);
            if (intent.resolveActivity(getPackageManager()) != null) {
                startActivity(intent);
            }
        }

    public void callPhoneNumber(String phoneNumber) {
       /* Intent intent = new Intent(Intent.ACTION_CALL);
        intent.setData(Uri.parse("tel:" + phoneNumber));
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        }*/

        if (ActivityCompat.checkSelfPermission(MainActivity.this,
                Manifest.permission.CALL_PHONE) == PackageManager.PERMISSION_GRANTED) {
            Intent callIntent = new Intent(Intent.ACTION_CALL);
            callIntent.setData(Uri.parse("tel:"+phoneNumber));
            this.startActivity(callIntent);
        }else{
            Toast.makeText(MainActivity.this, "You don't assign permission.", Toast.LENGTH_SHORT).show();
        }
    }
    //for calling permission
    private void onCallBtnClick(){
        if(Build.VERSION.SDK_INT <23){
            callPhoneNumber(et_data.getText().toString());
        }else{
            if (ActivityCompat.checkSelfPermission(MainActivity.this,
                    Manifest.permission.CALL_PHONE) == PackageManager.PERMISSION_GRANTED) {

                callPhoneNumber(et_data.getText().toString());
            }else {
                final String[] PERMISSIONS_STORAGE = {Manifest.permission.CALL_PHONE};
                //Asking request Permissions
                ActivityCompat.requestPermissions(MainActivity.this, PERMISSIONS_STORAGE, 9);
            }
        }

    }

    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        boolean permissionGranted = false;
        switch(requestCode){
            case 9:
                permissionGranted = grantResults[0]== PackageManager.PERMISSION_GRANTED;
                break;
        }
        if(permissionGranted){
            callPhoneNumber(et_data.getText().toString());
        }else {
            Toast.makeText(MainActivity.this, "You don't assign permission.", Toast.LENGTH_SHORT).show();
        }
    }
}