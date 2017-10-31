package com.combii.braintrainer;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
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
        score = intent.getIntExtra("score", 0);

        Log.i("INFO: ", score + "");
        Log.i("INFO: ", difficulty.toString());

        saveHighScore();
    }

    private void setUpHighScoreListview() {

    }

    private void saveHighScore(){

        try{
            SQLiteDatabase myDatabase = this.openOrCreateDatabase("HighScoreDatabase", MODE_PRIVATE, null);

            myDatabase.execSQL("CREATE TABLE IF NOT EXISTS HighScores (difficulty VARCHAR, highScore INT(4))");

            //Insert
            myDatabase.execSQL("INSERT INTO HighScores (difficulty, highScore) VALUES ('" + difficulty + "', " + score + ")");

            getHighScores();

        }
        catch (Exception e){
            e.printStackTrace();
        }
    }


    private void getHighScores(){
        SQLiteDatabase myDatabase = this.openOrCreateDatabase("HighScoreDatabase", MODE_PRIVATE, null);
        //Get
        Cursor c = myDatabase.rawQuery("SELECT * FROM HighScores", null);

        int difficultyIndex = c.getColumnIndex("difficulty");
        int highScoreIndex = c.getColumnIndex("highScore");

        c.moveToFirst();
        do{

            Log.i("Difficulty: ", c.getString(difficultyIndex));
            Log.i("HighScore: ", c.getString(highScoreIndex));

            c.moveToNext();
        }while(c != null);
    }
}

