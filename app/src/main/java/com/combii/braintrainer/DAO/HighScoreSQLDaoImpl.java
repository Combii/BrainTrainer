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


public class HighScoreSQLDaoImpl implements HighScoreDao {

    private SQLiteDatabase myDatabase;

    public HighScoreSQLDaoImpl(SQLiteDatabase myDatabase) {
        this.myDatabase = myDatabase;

    }

    private HighScoreSQLDaoImpl() {
    }

    @Override
    public void save(HighScore highScore) {

        myDatabase.execSQL("CREATE TABLE IF NOT EXISTS HighScores (difficulty VARCHAR, highScore INT(4), CONSTRAINT unq UNIQUE (difficulty, highScore))");

        try {
            //Insert
            myDatabase.execSQL("INSERT INTO HighScores (difficulty, highScore) VALUES ('" + highScore.getDifficulty() + "', " + highScore.getScore() + ")");

        } catch (SQLiteConstraintException ignored) {
        }
    }

    @Override
    public List<HighScore> findByDifficulty(Difficulty difficulty) {
        //myDatabase.execSQL("CREATE TABLE IF NOT EXISTS HighScores (difficulty VARCHAR, highScore INT(4), CONSTRAINT unq UNIQUE (difficulty, highScore))");

        Cursor c;
        //Get
        c = myDatabase.rawQuery("SELECT * FROM HighScores WHERE difficulty = '" + difficulty + "'", null);


        int difficultyIndex = c.getColumnIndex("difficulty");
        int highScoreIndex = c.getColumnIndex("highScore");

        c.moveToFirst();

        List<HighScore> highScoreList = new ArrayList<>();

        try {
            while (c != null) {

                Log.i("Difficulty: ", c.getString(difficultyIndex));
                Log.i("HighScore: ", c.getString(highScoreIndex));

                highScoreList.add(new HighScore(Integer.parseInt(c.getString(highScoreIndex)), difficulty));

                c.moveToNext();
            }
            c.close();
        } catch (Exception ignored) {
        }

        highScoreList.sort(Comparator.reverseOrder());


        return highScoreList;
    }

    public void closeDb(){
        if(myDatabase.isOpen())
        myDatabase.close();
    }

}
