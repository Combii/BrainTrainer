package com.combii.braintrainer;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteConstraintException;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

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

    private void saveHighScore(){
        SQLiteDatabase myDatabase = this.openOrCreateDatabase("HighScoreDatabase", MODE_PRIVATE, null);
        try{
            myDatabase.execSQL("CREATE TABLE IF NOT EXISTS HighScores (difficulty VARCHAR, highScore INT(4) UNIQUE)");

            //Insert
            myDatabase.execSQL("INSERT INTO HighScores (difficulty, highScore) VALUES ('" + difficulty + "', " + score + ")");

        } catch (SQLiteConstraintException ignored){
        }
        setUpHighScoreListView(getHighScores());
        myDatabase.close();
    }


        private List<Integer> getHighScores(){
            SQLiteDatabase myDatabase = this.openOrCreateDatabase("HighScoreDatabase", MODE_PRIVATE, null);

            //Get
            Cursor c = myDatabase.rawQuery("SELECT * FROM HighScores WHERE difficulty = '" + difficulty + "'", null);

            int difficultyIndex = c.getColumnIndex("difficulty");
            int highScoreIndex = c.getColumnIndex("highScore");

            c.moveToFirst();

            List<Integer> highScoreList = new ArrayList<>();

            try {
                while (c != null) {

                    Log.i("Difficulty: ", c.getString(difficultyIndex));
                    Log.i("HighScore: ", c.getString(highScoreIndex));

                    highScoreList.add(c.getInt(highScoreIndex));

                    c.moveToNext();
                }
                c.close();
            }
            catch (Exception ignored){
            }

            myDatabase.close();

            return highScoreList;
        }

        private void setUpHighScoreListView(List<Integer> highScoreList) {

        setTitle("Difficulty: " + difficulty);



        ArrayAdapter<Integer> arrayAdapter = new ArrayAdapter<>(this,android.R.layout.simple_list_item_1,highScoreList);

        highScoreListView.setAdapter(arrayAdapter);
        highScoreListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
            }
        });

    }
}

