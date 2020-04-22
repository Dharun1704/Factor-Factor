package com.example.factorfactor;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import java.util.ArrayList;
import java.util.Random;

public class HackerMode extends AppCompatActivity {

    int score = 0;
    int streak = 0;
    int longStreak = 0;
    int HFactor;
    EditText number;
    TextView HTopText, HBottomText, HScore, HStreak, HLongStreak;
    Button HButtonG, HButton0, HButton1, HButton2, HButtonMainMenu, HButtonCont;
    ConstraintLayout layout;



    View.OnClickListener HAnswerClicked = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Button HbtnClicked = (Button) v;
            int isAnswer = Integer.parseInt(HbtnClicked.getText().toString());
            HBottomText.setVisibility(View.VISIBLE);
            HButton0.setEnabled(false);
            HButton1.setEnabled(false);
            HButton2.setEnabled(false);
            if(isAnswer == HFactor) {
                HBottomText.setText("\nCorrect Answer! Good Job!");
                layout.setBackgroundColor(Color.rgb(14,116,0));
                IScoreSystem();
                HScore.setText("Score: " + score);
                HStreak.setText("Streak: " + streak);
                HLongStreak.setText("Longest Streak: " + longStreak);
            }
            else {
                layout.setBackgroundColor(Color.rgb(173,0,0));
                DScoreSystem();
                HScore.setText("Score: " + score);
                HStreak.setText("Streak: "+ streak);
                HLongStreak.setText("Longest Streak: " + longStreak);
                HBottomText.setText("Oops! Correct Answer is " + HFactor);
            }
            HButtonCont.setVisibility(View.VISIBLE);
            HButtonMainMenu.setVisibility(View.VISIBLE);
        }
    };




    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.hacker_mode);

        layout =  findViewById(R.id.HackerLayout);

        number = findViewById(R.id.HEditText);

        HTopText = findViewById(R.id.HtextViewQues);
        HBottomText = findViewById(R.id.HTextViewBottom);
        HScore = findViewById(R.id.textViewScore);
        HStreak = findViewById(R.id.textViewStreak);
        HLongStreak = findViewById(R.id.textViewLongStreak);

        HButtonG = findViewById(R.id.HButtonGo);
        HButton0 = findViewById(R.id.HButton0);
        HButton1 = findViewById(R.id.HButton1);
        HButton2 = findViewById(R.id.HButton2);
        HButtonCont = findViewById(R.id.HButtonCont);
        HButtonMainMenu = findViewById(R.id.Hbuttonm);

        HButton0.setVisibility(View.INVISIBLE);
        HButton1.setVisibility(View.INVISIBLE);
        HButton2.setVisibility(View.INVISIBLE);
        HTopText.setVisibility(View.INVISIBLE);
        HButtonCont.setVisibility(View.INVISIBLE);
        HButtonMainMenu.setVisibility(View.INVISIBLE);

        HBottomText.setText("Enter the Number and Press GO");
        HScore.setText("Score: 0");
        HStreak.setText("0");

        SharedPreferences Score = this.getSharedPreferences("MyScore", Context.MODE_PRIVATE);
        score = Score.getInt("score",0);
        SharedPreferences Streak = this.getSharedPreferences("MyStreak", Context.MODE_PRIVATE);
        streak = Streak.getInt("streak",0);
        SharedPreferences LongStreak = this.getSharedPreferences("LongestStreak", Context.MODE_PRIVATE);
        longStreak = LongStreak.getInt("longStreak",0);

        HScore.setText("Score: " + score);
        HStreak.setText("Streak: " + streak);
        HLongStreak.setText("Longest Streak: "+longStreak);

        HButtonG.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                HButtonG.setVisibility(View.INVISIBLE);
                number.setEnabled(false);
                HBottomText.setVisibility(View.INVISIBLE);
                HButton0.setVisibility(View.VISIBLE);
                HButton1.setVisibility(View.VISIBLE);
                HButton2.setVisibility(View.VISIBLE);
                HTopText.setVisibility(View.VISIBLE);
                StartQuiz(Integer.parseInt(number.getText().toString()));
            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        ResetScore();
    }


    public void StartQuiz(int num){
        int Hposition;
        int[] HansArray = new int[] {0,1,2};
        ArrayList<Integer> HAns = new ArrayList<>();
        ArrayList<Integer> HfalseAns = new ArrayList<>();
        for(int i = 1; i <= num; i++){
            if (num % i == 0)
                HAns.add(i);
            else
                HfalseAns.add(i);
        }
        Random random = new Random();
        HFactor = HAns.get(random.nextInt(HAns.size()));
        HTopText.setText("The factor of " + num + " is");

        Hposition = random.nextInt(3);
        HansArray = assignAnsArray(HansArray,HfalseAns);
        HansArray[Hposition] = HFactor;

        HButton0.setText(Integer.toString(HansArray[0]));
        HButton1.setText(Integer.toString(HansArray[1]));
        HButton2.setText(Integer.toString(HansArray[2]));

        HButton0.setOnClickListener(HAnswerClicked);
        HButton1.setOnClickListener(HAnswerClicked);
        HButton2.setOnClickListener(HAnswerClicked);


        HButtonCont.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                startActivity(getIntent());
            }
        });
        HButtonMainMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ResetScore();
                Intent i = new Intent(HackerMode.this, MainActivity.class);
                startActivity(i);
                finish();
            }
        });

    }



    public int[] assignAnsArray(int[] arr, ArrayList<Integer> array){
        Random r = new Random();
        arr[0] = array.get(r.nextInt(array.size()));
        arr[1] = array.get(r.nextInt(array.size()));
        while(arr[0] == arr[1])
            arr[1] = array.get(r.nextInt(array.size()));
        arr[2] = array.get(r.nextInt(array.size()));
        while(arr[0] == arr[2] || arr[1] == arr[2])
            arr[2] = array.get(r.nextInt(array.size()));
        return arr;
    }



    public void IScoreSystem(){
        score = score + 20;
        if(streak > 7)
            score = score + 50;
        streak = streak + 1;
        SharedPreferences Score = getSharedPreferences("MyScore",MODE_PRIVATE);
        SharedPreferences.Editor editor = Score.edit();
        editor.putInt("score",score);
        editor.apply();

        SharedPreferences Streak = getSharedPreferences("MyStreak",MODE_PRIVATE);
        SharedPreferences.Editor editor1 = Streak.edit();
        editor1.putInt("streak",streak);
        editor1.apply();
    }


    public void DScoreSystem(){
        score = score - 10;
        if(streak > longStreak)
            longStreak = streak;
        streak = 0;

        SharedPreferences Score = getSharedPreferences("MyScore",MODE_PRIVATE);
        SharedPreferences.Editor editor = Score.edit();
        editor.putInt("score",score);
        editor.apply();

        SharedPreferences LongStreak = getSharedPreferences("LongestStreak",MODE_PRIVATE);
        SharedPreferences.Editor editor2 = LongStreak.edit();
        editor2.putInt("longStreak",longStreak);
        editor2.apply();

        SharedPreferences Streak = getSharedPreferences("MyStreak",MODE_PRIVATE);
        SharedPreferences.Editor editor1 = Streak.edit();
        editor1.putInt("streak",streak);
        editor1.apply();
    }


    public void ResetScore(){
        score = 0;
        streak = 0;
        longStreak = 0;

        SharedPreferences Score = getSharedPreferences("MyScore",MODE_PRIVATE);
        SharedPreferences.Editor editor = Score.edit();
        editor.putInt("score",score);
        editor.apply();

        SharedPreferences LongStreak = getSharedPreferences("LongestStreak",MODE_PRIVATE);
        SharedPreferences.Editor editor2 = LongStreak.edit();
        editor2.putInt("longStreak",longStreak);
        editor2.apply();


        SharedPreferences Streak = getSharedPreferences("MyStreak",MODE_PRIVATE);
        SharedPreferences.Editor editor1 = Streak.edit();
        editor1.putInt("streak",streak);
        editor1.apply();
    }

}
