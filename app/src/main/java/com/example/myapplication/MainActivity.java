package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.myapplication.data.Lang;

import java.util.Vector;


public class MainActivity extends AppCompatActivity {

    public static class Data {

        public Vector<Lang> languages;
        public static void load() {
            //TODO
        }

    }



    private Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
       Data.load();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        button = (Button) findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openActivity2();
            }
        });
    }

    public void openActivity2() {
        Intent intent = new Intent(this, MainActivity2.class);
        startActivity(intent);
    }

}