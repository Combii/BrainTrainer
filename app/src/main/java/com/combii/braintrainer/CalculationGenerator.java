package com.combii.braintrainer;

import android.content.Intent;
import android.util.Log;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;

import static com.combii.braintrainer.Difficulty.*;

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
        int number2 = r.nextInt(9) + 1;

        result = number1 * number2;

        calctionString = number1 + " * " + number2;


        return generateList();
    }

    private List<Integer> generateSubtractionResult() {
        int number1 = r.nextInt(rangeSubtraction) + 1;
        int number2 = r.nextInt(rangeSubtraction) + 1;

        result = number1 - number2;

        calctionString = number1 + " - " + number2;


        return generateList();
    }

    private List<Integer> generateAdditionResult() {

        int number1 = r.nextInt(rangeAddition) + 1;
        int number2 = r.nextInt(rangeAddition) + 1;

        result = number1 + number2;

        calctionString = number1 + " + " + number2;


        return generateList();
    }


    private List<Integer> generateList() {


        Set<Integer> listSet = new HashSet<>();

        listSet.add(result);

        boolean subtractOrAdd = false;
        while(listSet.size() < 4) {

            if(subtractOrAdd) {
                listSet.add(result + r.nextInt(10) + 1);
                subtractOrAdd = false;
            }
            else{
                listSet.add(result - r.nextInt(10) + 1);
                subtractOrAdd = true;
            }

        }

        return new ArrayList<>(listSet);
    }


    private void checkDifficulty() {

        if (difficulty == HARD) {
            rangeAddition = 100;
            rangeSubtraction = 80;
            rangeMultiplication = 30;
        } else if (difficulty == MEDIUM) {
            rangeAddition = 60;
            rangeSubtraction = 40;
            rangeMultiplication = 20;
        } else if (difficulty == EASY) {
            rangeAddition = 20;
            rangeSubtraction = 10;
            rangeMultiplication = 10;
        }
    }
}
