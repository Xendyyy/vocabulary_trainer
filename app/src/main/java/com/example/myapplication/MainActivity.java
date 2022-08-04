package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import java.io.IOException;


public class MainActivity extends AppCompatActivity {

    private static MainActivity app;
    public static MainActivity getApp() {
        return app;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        app = this;
        try {
            Data.load();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

    }

    public void buttonEnglish(android.view.View view) {
        Intent intent = new Intent(this, English.class);
        startActivity(intent);
    }

}