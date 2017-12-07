package com.combii.braintrainer;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.combii.braintrainer.DAO.HighScoreDao;
import com.combii.braintrainer.DAO.HighScoreSQLDaoImpl;

import java.util.ArrayList;
import java.util.List;

public class HighScoreActivity extends AppCompatActivity {

    Difficulty difficulty;
    int score;
    HighScoreDao dao;

    ListView highScoreListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_high_score);

        dao = new HighScoreSQLDaoImpl(this.openOrCreateDatabase("HighScoreDatabase", MODE_PRIVATE, null));

        highScoreListView = (ListView) findViewById(R.id.highscoreListView);

        Intent intent = getIntent();

        if(intent.getBooleanExtra("newHighScore",false)) {
            saveHighScore();
        }

    }


    private void saveHighScore() {
        Intent intent = getIntent();

        difficulty = (Difficulty) intent.getSerializableExtra("difficulty");
        score = intent.getIntExtra("score", 0);

        dao.save(new HighScore(score,difficulty));

        setUpHighScoreListView(dao.findByDifficulty(difficulty));
    }
    

    private void setUpHighScoreListView(List<HighScore> highScoreList) {

        List<String> newHighScoreList = convertHighScoreList(highScoreList);

        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, newHighScoreList);

        highScoreListView.setAdapter(arrayAdapter);
        highScoreListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
            }
        });

    }


    public void clickedButtonHard(View view) {
        setUpHighScoreListView(dao.findByDifficulty(Difficulty.HARD));
        setTitle("Difficulty: " + Difficulty.HARD.toString());

    }

    public void clickedButtonMedium(View view) {
        setUpHighScoreListView(dao.findByDifficulty(Difficulty.MEDIUM));
        setTitle("Difficulty: " + Difficulty.MEDIUM.toString());
    }

    public void clickedButtonEasy(View view) {
        setUpHighScoreListView(dao.findByDifficulty(Difficulty.EASY));
        setTitle("Difficulty: " + Difficulty.EASY.toString());
    }
    

    private List<String> convertHighScoreList(List<HighScore> list){
        List<String> scores = new ArrayList<>();

        //Get list of scores as arraylist<String>
        for(HighScore s : list) {
            scores.add(String.valueOf(s.getScore()));
        }
        return scores;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        dao.closeDb();
        Log.i("Life Cycle: ", "IS IN OnDestroy");
    }
}

