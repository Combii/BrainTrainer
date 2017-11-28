package com.combii.braintrainer.DAO;

import com.combii.braintrainer.Difficulty;
import com.combii.braintrainer.HighScore;

import java.util.List;

/**
 * Created by :0) on 28/11/2017.
 */

public interface HighScoreDao {

    void save(HighScore highScore);
    List<HighScore> findByDifficulty(Difficulty difficulty);


}
