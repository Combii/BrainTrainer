package com.combii.braintrainer;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;

public class HighScoreActivity extends AppCompatActivity {

    Difficulty difficulty;
    int score;

    ListView highScoreListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_high_score);

        highScoreListView = (ListView) findViewById(R.id.highscoreListView);

        getHighScore();
    }


    private void getHighScore(){
        Intent intent = getIntent();

        //getStringExtra if String
        difficulty  = (Difficulty) intent.getSerializableExtra("difficulty");
        int score = intent.getIntExtra("score", 0);

        Log.i("INFO: ", score + "");
        Log.i("INFO: ", difficulty.toString());

    }
}
