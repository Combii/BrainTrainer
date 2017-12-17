package com.combii.braintrainer.DAO;


import com.combii.braintrainer.Difficulty;
import com.combii.braintrainer.HighScore;

import java.util.List;

public class HighScoreCloudDaoImpl implements HighScoreDao {


    @Override
    public void save(HighScore highScore) {

    }

    @Override
    public List<HighScore> findByDifficulty(Difficulty difficulty) {
        return null;
    }

    @Override
    public void closeDb() {

    }
}
