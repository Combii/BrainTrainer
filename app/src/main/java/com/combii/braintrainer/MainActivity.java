package com.combii.braintrainer;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;


import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class MainActivity extends AppCompatActivity {


    List<TextView> textViewList = new ArrayList<>();
    TextView timerTextView;
    TextView scoreTextView;
    TextView calculationTextView;
    TextView titleTextView;
    Difficulty difficulty;
    int range = 0;

    Button playAgainButton, highScoreButton;


    int number1, number2, result, score, amountOfGames;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Log.i("Info","In on create");

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

        checkDifficulty();
        resetGame();
    }

    private void checkDifficulty() {
        Intent intent = getIntent();

        difficulty  = (Difficulty) intent.getSerializableExtra("difficulty");

        if(difficulty == Difficulty.HARD) {
            Log.i("Difficulty", "Difficulty set to Hard");
            range = 100;
        }
        else if(difficulty == Difficulty.MEDIUM) {
            Log.i("Difficulty", "Difficulty set to Medium");
            range = 60;
        }
        else if(difficulty == Difficulty.EASY) {
            Log.i("Difficulty", "Difficulty set to Easy");
            range = 20;
        }
    }

    public void clickedPlayAgainButton(View view) {
        resetGame();
    }

    public void clickedSavedHighScoreButton(View view) {

        Intent intent = new Intent(getApplicationContext(), HighScoreActivity.class);

        intent.putExtra("difficulty",difficulty);
        intent.putExtra("score", score);

        startActivity(intent);
    }

    private void resetGame(){
        score = 0;
        amountOfGames = 0;

        setupList();
        setUpCountDownTimer();
        playAgainButton.setVisibility(View.INVISIBLE);
        highScoreButton.setVisibility(View.INVISIBLE);
        updateScore();
        titleTextView.setText("");
    }

    private void stopGame(){
        for(final TextView textView : textViewList){
            textView.setOnClickListener(null);
        }
        playAgainButton.setVisibility(View.VISIBLE);
        highScoreButton.setVisibility(View.VISIBLE);

        String rS = "Your score: " + score + "/" + amountOfGames;
        titleTextView.setText(rS);

        SharedPreferences sharedPreferences = this.getSharedPreferences("com.combii.braintrainer", Context.MODE_PRIVATE);

        sharedPreferences.edit().putString("highscore", score + "").apply();
    }

    private void checkAnswer(int value){
        amountOfGames++;

        String rS;

        if(value == result){
            score++;
            rS = "Correct!";
            titleTextView.setText(rS);
        }
        else{
            rS = "Wrong!";
            titleTextView.setText(rS);
        }


        setUpNewGame();
        updateScore();
    }

    private void updateScore(){
        String scoreS = Integer.toString(score);
        String amountOfGamesS = Integer.toString(amountOfGames);

        scoreTextView.setText(scoreS + "/" + amountOfGamesS);
    }

    private void setUpNewGame(){
        generateCalculation();
        setUpListNumbers();
    }

    private void setupList(){
        for(final TextView textView : textViewList){
            textView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    checkAnswer(Integer.parseInt(textView.getText().toString()));
                }
            });
        }
        setUpNewGame();
    }

    private void setUpListNumbers(){
        Random r = new Random();

        String intS;

        for(TextView textView : textViewList){
            intS = Integer.toString(r.nextInt(range) + 1);
            textView.setText(intS);
        }

        intS = Integer.toString(result);

        textViewList.get(r.nextInt(textViewList.size())).setText(intS);
    }

    private void generateCalculation(){
        Random random = new Random();
        number1 = random.nextInt(range/2) + 1;
        number2 = random.nextInt(range/2) + 1;

        result = number1 + number2;

        Log.i("Result: ", Integer.toString(result));

        calculationTextView.setText(Integer.toString(number1) + " + " + Integer.toString(number2));
    }

    private void setUpCountDownTimer(){
        /*new CountDownTimer(30000, 1000) {
            @Override
            public void onTick(long l) {
                updateCountDownTimer((int) l / 1000);
            }

            @Override
            public void onFinish() {
                stopGame();
            }
        }.start();*/
        new CountDownTimer(3000, 100) {
            @Override
            public void onTick(long l) {
                updateCountDownTimer((int) l / 100);
            }

            @Override
            public void onFinish() {
                stopGame();
            }
        }.start();
    }

    public void updateCountDownTimer(int amount){
        int minutes = amount / 60;
        int seconds = amount - minutes * 60;

        String secondsString = Integer.toString(seconds);

        timerTextView.setText(secondsString + 's');
    }

}
