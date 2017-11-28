package com.combii.braintrainer.DAO;

import android.database.Cursor;
import android.database.sqlite.SQLiteConstraintException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.combii.braintrainer.Difficulty;
import com.combii.braintrainer.HighScore;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import static android.content.Context.MODE_PRIVATE;


public class HighScoreSQLDaoImpl implements HighScoreDao {

    SQLiteDatabase myDatabase;

    public HighScoreSQLDaoImpl(SQLiteDatabase myDatabase) {
        
        this.myDatabase = myDatabase;
    }

    @Override
    public void save(HighScore highScore) {
        try {

            //Insert
            myDatabase.execSQL("INSERT INTO HighScores (difficulty, highScore) VALUES ('" + highScore.getDifficulty() + "', " + highScore.getScore() + ")");

        } catch (SQLiteConstraintException ignored) {
        }
        myDatabase.close();
    }

    @Override
    public List<HighScore> findByDifficulty(Difficulty difficulty) {

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
}
