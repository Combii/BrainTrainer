package com.combii.braintrainer;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

public class MenuActivity extends AppCompatActivity {

    Button easyButton, mediumButton, hardButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        easyButton = (Button) findViewById(R.id.easy);
        mediumButton = (Button) findViewById(R.id.medium);
        hardButton = (Button) findViewById(R.id.hard);


    }


    public void buttonClicked(View view) {

        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
        if (view.getTag().equals("Hard")) {
            intent.putExtra("difficulty", Difficulty.HARD);
        } else if (view.getTag().equals("Medium")) {
            intent.putExtra("difficulty", Difficulty.MEDIUM);
        } else if (view.getTag().equals("Easy")) {
            intent.putExtra("difficulty", Difficulty.EASY);
        }
        startActivity(intent);
    }

    public void seeHighScore(View view) {

        //Intent intent = new Intent(getApplicationContext(), [ActivityName].class);
        //startActivity(intent);


    }
}