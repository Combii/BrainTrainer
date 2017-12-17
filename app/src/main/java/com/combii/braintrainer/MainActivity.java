package com.combii.braintrainer;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;


import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {


    List<TextView> textViewList = new ArrayList<>();
    TextView timerTextView;
    TextView scoreTextView;
    TextView calculationTextView;
    TextView titleTextView;
    Difficulty difficulty;

    CalculationGenerator gen;

    long currTimeLeft;
    CountDownTimer counter;


    Button playAgainButton, highScoreButton;


    int result, score, amountOfQuestions;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Log.i("Info", "In on create");

        textViewList.add((TextView) findViewById(R.id.textView));
        textViewList.add((TextView) findViewById(R.id.textView2));
        textViewList.add((TextView) findViewById(R.id.textView3));
        textViewList.add((TextView) findViewById(R.id.textView4));

        timerTextView = (TextView) findViewById(R.id.timerTextView);
        scoreTextView = (TextView) findViewById(R.id.scoreTextView);
        calculationTextView = (TextView) findViewById(R.id.calculationTextView);
        titleTextView = (TextView) findViewById(R.id.titleTextView);
        playAgainButton = (Button) findViewById(R.id.playAgainButton);
        highScoreButton = (Button) findViewById(R.id.saveHighscore);
        getDifficulty();
        gen = new CalculationGenerator(difficulty);

        resetGame();
    }

    private void getDifficulty() {
        Intent intent = getIntent();
        difficulty = (Difficulty) intent.getSerializableExtra("difficulty");
    }

    public void clickedPlayAgainButton(View view) {
        resetGame();
    }

    public void clickedSavedHighScoreButton(View view) {

        Intent intent = new Intent(getApplicationContext(), HighScoreActivity.class);

        intent.putExtra("difficulty", difficulty);
        intent.putExtra("score", score);
        intent.putExtra("newHighScore", true);

        startActivity(intent);
    }

    private void resetGame() {
        score = 0;
        amountOfQuestions = 0;

        setupNewQuestion();
        setUpCountDownTimer();
        playAgainButton.setVisibility(View.INVISIBLE);
        highScoreButton.setVisibility(View.INVISIBLE);
        updateScore();
        titleTextView.setText("");
    }

    private void stopGame() {
        for (final TextView textView : textViewList) {
            textView.setOnClickListener(null);
        }
        timerTextView.setText("0s");
        playAgainButton.setVisibility(View.VISIBLE);
        highScoreButton.setVisibility(View.VISIBLE);

        String rS = "Your score: " + score + "/" + amountOfQuestions;
        titleTextView.setText(rS);
    }

    private void checkAnswer(int value) {
        amountOfQuestions++;

        String rS;

        if (value == result) {
            score++;
            rS = "Correct!";
            titleTextView.setText(rS);
        } else {
            rS = "Wrong!";
            titleTextView.setText(rS);
        }


        setupNewQuestion();
        updateScore();
    }

    private void updateScore() {
        String scoreS = Integer.toString(score);
        String amountOfGamesS = Integer.toString(amountOfQuestions);

        scoreTextView.setText(scoreS + "/" + amountOfGamesS);
    }

    private void setupNewQuestion() {
        setupListeners();
        setUpListNumbers();
    }

    private void setupListeners() {
        for (final TextView textView : textViewList) {
            textView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    checkAnswer(Integer.parseInt(textView.getText().toString()));
                }
            });
        }
    }

    private void setUpListNumbers() {

        List<Integer> randomNumbers = gen.generateResult();

        for (int i = 0; i < randomNumbers.size(); i++) {
            textViewList.get(i).setText(randomNumbers.get(i) + "");
        }
        calculationTextView.setText(gen.getCalculationString());
        result = gen.getResult();
    }

    private void setUpCountDownTimer() {
        counter = new CountDownTimer(30000, 1000) {
            @Override
            public void onTick(long l) {
                updateCountDownTimer((int) l / 1000);
            }

            @Override
            public void onFinish() {
                stopGame();
            }
        }.start();
    }

    @Override
    protected void onPause() {
        super.onPause();        Log.i("Is in ON PAUSE: ", "");
        counter.pause();
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Log.i("Current Timer: ", currTimeLeft + "");
        counter.resume();
    }

    public void updateCountDownTimer(int amount) {
        int minutes = amount / 60;
        int seconds = amount - minutes * 60;

        String secondsString = Integer.toString(seconds);

        timerTextView.setText(secondsString + 's');
    }

}
