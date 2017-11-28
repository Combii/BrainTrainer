package com.combii.braintrainer;

import android.content.Intent;
import android.util.Log;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Random;

/**
 * Created by Combii on 28/11/2017.
 */

public class CalculationGenerator {

    private Difficulty difficulty;
    private int rangeAddition = 0;
    private int rangeSubtraction = 0;
    private int rangeMultiplication = 0;
    private Random r = new Random();


    private String calctionString;
    private int result;

    public CalculationGenerator(Difficulty difficulty) {
        this.difficulty = difficulty;
        checkDifficulty();

    }

    private CalculationGenerator() {
    }

    public String getCalctionString() {
        return calctionString;
    }

    public int getResult() {
        return result;
    }

    public List<Integer> generateResult() {


        int AddSubtractOrMultiply = r.nextInt(3);

        switch (AddSubtractOrMultiply) {
            case 0:
                return generateAdditionResult();
            case 1:
                return generateSubtractionResult();
            default:
                return generateMultiplicationResult();
        }

    }

    private List<Integer> generateMultiplicationResult() {
        int number1 = r.nextInt(rangeMultiplication) + 1;
        int number2 = r.nextInt(rangeMultiplication) + 1;

        result = number1 * number2;

        calctionString = number1 + " * " + number2;


        return generateList(rangeMultiplication);
    }

    private List<Integer> generateSubtractionResult() {
        int number1 = r.nextInt(rangeSubtraction) + 1;
        int number2 = r.nextInt(rangeSubtraction) + 1;

        result = number1 - number2;

        calctionString = number1 + " - " + number2;


        return generateList(rangeSubtraction);
    }

    private List<Integer> generateAdditionResult() {

        int number1 = r.nextInt(rangeAddition) + 1;
        int number2 = r.nextInt(rangeAddition) + 1;

        result = number1 + number2;

        calctionString = number1 + " + " + number2;


        return generateList(rangeAddition);
    }


    private List<Integer> generateList(int range) {

        List<Integer> randomNumbers = new ArrayList<>(Arrays.asList(
                r.nextInt(range) + 1,
                r.nextInt(range) + 1,
                r.nextInt(range) + 1,
                result
        ));

        Collections.shuffle(randomNumbers);

        return randomNumbers;

    }


    private void checkDifficulty() {

        if (difficulty == Difficulty.HARD) {
            rangeAddition = 100;
            rangeSubtraction = 80;
            rangeMultiplication = 30;
        } else if (difficulty == Difficulty.MEDIUM) {
            rangeAddition = 60;
            rangeSubtraction = 40;
            rangeMultiplication = 20;
        } else if (difficulty == Difficulty.EASY) {
            rangeAddition = 20;
            rangeSubtraction = 10;
            rangeMultiplication = 10;
        }
    }
}
