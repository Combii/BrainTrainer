package com.combii.braintrainer;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteConstraintException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class HighScoreActivity extends AppCompatActivity {

    Difficulty difficulty;
    int score;
    SQLiteDatabase myDatabase;

    ListView highScoreListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_high_score);

        myDatabase = this.openOrCreateDatabase("HighScoreDatabase", MODE_PRIVATE, null);

        highScoreListView = (ListView) findViewById(R.id.highscoreListView);

        getHighScore();
    }


    private void getHighScore() {
        Intent intent = getIntent();

        boolean viewAllHighScores = intent.getBooleanExtra("viewAll", false);

        myDatabase.execSQL("CREATE TABLE IF NOT EXISTS HighScores (difficulty VARCHAR, highScore INT(4), CONSTRAINT unq UNIQUE (difficulty, highScore))");

        if (!viewAllHighScores) {
            //getStringExtra if String
            difficulty = (Difficulty) intent.getSerializableExtra("difficulty");
            score = intent.getIntExtra("score", 0);

            Log.i("INFO: ", score + "");
            Log.i("INFO: ", difficulty.toString());

            saveHighScore();
        }

        setUpHighScoreListView(getHighScores(difficulty));
    }

    private void saveHighScore() {
        try {
            //Insert
            myDatabase.execSQL("INSERT INTO HighScores (difficulty, highScore) VALUES ('" + difficulty + "', " + score + ")");

        } catch (SQLiteConstraintException ignored) {
        }
        setUpHighScoreListView(getHighScores(difficulty));
        myDatabase.close();
    }

    private List<String> getHighScores(Difficulty difficulty) {
        SQLiteDatabase myDatabase = this.openOrCreateDatabase("HighScoreDatabase", MODE_PRIVATE, null);

        Cursor c;
        //Get
        c = myDatabase.rawQuery("SELECT * FROM HighScores WHERE difficulty = '" + difficulty + "'", null);


        int difficultyIndex = c.getColumnIndex("difficulty");
        int highScoreIndex = c.getColumnIndex("highScore");

        c.moveToFirst();

        List<String> highScoreList = new ArrayList<>();

        try {
            while (c != null) {

                Log.i("Difficulty: ", c.getString(difficultyIndex));
                Log.i("HighScore: ", c.getString(highScoreIndex));

                highScoreList.add(c.getString(highScoreIndex));

                c.moveToNext();
            }
            c.close();
        } catch (Exception ignored) {
        }

        myDatabase.close();

        highScoreList.sort(Comparator.reverseOrder());

        return highScoreList;
    }

    private void setUpHighScoreListView(List<String> highScoreList) {

        setTitle("Difficulty: " + difficulty);


        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, highScoreList);

        highScoreListView.setAdapter(arrayAdapter);
        highScoreListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
            }
        });

    }


    public void clickedButtonHard(View view) {
        setUpHighScoreListView(getHighScores(Difficulty.HARD));
    }

    public void clickedButtonMedium(View view) {
        setUpHighScoreListView(getHighScores(Difficulty.MEDIUM));
    }

    public void clickedButtonEasy(View view) {
        setUpHighScoreListView(getHighScores(Difficulty.EASY));
    }
}

