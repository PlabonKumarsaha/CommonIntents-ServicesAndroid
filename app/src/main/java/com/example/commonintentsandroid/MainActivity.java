package com.example.commonintentsandroid;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DownloadManager;
import android.app.SearchManager;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

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
    }
}