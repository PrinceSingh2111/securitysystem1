package com.example.securitysystem;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
Button bt1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
   bt1=(Button)findViewById(R.id.bt1);
   bt1.setOnClickListener(new View.OnClickListener() {
       @Override
       public void onClick(View v) {

           Intent int1=new Intent(MainActivity.this,secondactivity.class);
           startActivity(int1);
       }
   });
    }
}
