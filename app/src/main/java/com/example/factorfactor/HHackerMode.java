package com.example.factorfactor;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.os.Vibrator;
import android.view.View;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.Random;

public class HHackerMode extends AppCompatActivity {

    Vibrator vibrator;
    int HHFactor, timeRemaining = 10, Hscore = 0, Hstreak = 0, HlongStreak = 0;;
    EditText number;
    TextView HHTopText, HHBottomText, HHScore, HHStreak, HHtime, HHLongStreak;
    Button HHButtonG, HHButton0, HHButton1, HHButton2, HHButtonMainMenu, HHButtonCont;
    ProgressBar time;

    View.OnClickListener HHAnswerClicked = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Button HbtnClicked = (Button) v;
            int isAnswer = Integer.parseInt(HbtnClicked.getText().toString());
            Timer.cancel();
            HHBottomText.setVisibility(View.VISIBLE);
            if(isAnswer == HHFactor) {
                HHBottomText.setText("\nCorrect Answer! Good Job!");
                IScoreSystem();
                HHScore.setText("Score: " + Hscore);
                HHStreak.setText("Streak: " + Hstreak);
                HHLongStreak.setText("Longest Streak: " + HlongStreak);
            }
            else {
                vibrator.vibrate(500);
                DScoreSystem();
                HHBottomText.setText("Oops! Correct Answer is " + HHFactor);
                HHScore.setText("Score: " + Hscore);
                HHStreak.setText("Streak: " + Hstreak);
                HHLongStreak.setText("Longest Streak: " + HlongStreak);
            }
            HHButtonCont.setVisibility(View.VISIBLE);
            HHButtonMainMenu.setVisibility(View.VISIBLE);
        }
    };


    CountDownTimer Timer = new CountDownTimer(10000,1000) {
        @Override
        public void onTick(long millisUntilFinished) {
            timeRemaining--;
            HHtime.setText(Integer.toString(timeRemaining) + " sec");
            time.setProgress(timeRemaining);
        }

        @Override
        public void onFinish() {
            HHButton0.setEnabled(false);
            HHButton1.setEnabled(false);
            HHButton2.setEnabled(false);
            HHBottomText.setVisibility(View.VISIBLE);
            HHBottomText.setText("Oops! Time up!!");
            final String reason;
            reason = "Time Up";
            if(HlongStreak == 0)
                HlongStreak = Hstreak;
            Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    Intent i = new Intent(HHackerMode.this,HHackerResult.class);
                    i.putExtra("Score",Integer.toString(Hscore));
                    i.putExtra("Streak",Integer.toString(Hstreak));
                    i.putExtra("LongestStreak",Integer.toString(HlongStreak));
                    i.putExtra("Reason",reason);
                    ResetScore();
                    startActivity(i);
                    finish();
                }
            }, 2000);
        }
    };


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.hhacker_mode);

        vibrator = (Vibrator) getSystemService(VIBRATOR_SERVICE);

        time = findViewById(R.id.progressBar);

        number = findViewById(R.id.HHEditText);

        HHTopText = findViewById(R.id.HHtextViewQues);
        HHBottomText = findViewById(R.id.HHTextViewBottom);
        HHScore = findViewById(R.id.HHtextViewScore);
        HHStreak = findViewById(R.id.HHtextViewStreak);
        HHLongStreak = findViewById(R.id.HHtextViewLongStreak);
        HHtime = findViewById(R.id.HHtextViewtime);

        HHButtonG = findViewById(R.id.HHButtonGo);
        HHButton0 = findViewById(R.id.HHButton0);
        HHButton1 = findViewById(R.id.HHButton1);
        HHButton2 = findViewById(R.id.HHButton2);
        HHButtonCont = findViewById(R.id.HHButtonCont);
        HHButtonMainMenu = findViewById(R.id.HHbuttonm);

        HHButton0.setVisibility(View.INVISIBLE);
        HHButton1.setVisibility(View.INVISIBLE);
        HHButton2.setVisibility(View.INVISIBLE);
        HHTopText.setVisibility(View.INVISIBLE);
        HHtime.setVisibility(View.INVISIBLE);
        HHButtonCont.setVisibility(View.INVISIBLE);
        HHButtonMainMenu.setVisibility(View.INVISIBLE);

        HHBottomText.setText("Enter the Number and Press GO");
        HHScore.setText("Score: 0");
        HHStreak.setText("0");
        HHLongStreak.setText("0");

        SharedPreferences Score = this.getSharedPreferences("MyHScore", Context.MODE_PRIVATE);
        Hscore = Score.getInt("Hscore",0);
        SharedPreferences Streak = this.getSharedPreferences("MyHStreak", Context.MODE_PRIVATE);
        Hstreak = Streak.getInt("Hstreak",0);
        SharedPreferences LongStreak = this.getSharedPreferences("HLongestStreak", Context.MODE_PRIVATE);
        HlongStreak = LongStreak.getInt("HlongStreak",0);

        HHScore.setText("Score: " + Hscore);
        HHStreak.setText("Streak: " + Hstreak);
        HHLongStreak.setText("Longest Streak: " + HlongStreak);

        HHButtonG.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                HHButtonG.setVisibility(View.INVISIBLE);
                number.setEnabled(false);
                HHBottomText.setVisibility(View.INVISIBLE);
                HHtime.setVisibility(View.VISIBLE);
                HHButton0.setVisibility(View.VISIBLE);
                HHButton1.setVisibility(View.VISIBLE);
                HHButton2.setVisibility(View.VISIBLE);
                HHTopText.setVisibility(View.VISIBLE);
                Timer.start();
                StartQuiz(Integer.parseInt(number.getText().toString()));
            }
        });


    }



    @Override
    public void onBackPressed() {
        super.onBackPressed();
        ResetScore();
    }




    public void StartQuiz(int num) {
        int HHposition;
        int[] HHansArray = new int[]{0, 1, 2};
        ArrayList<Integer> HHAns = new ArrayList<>();
        ArrayList<Integer> HHfalseAns = new ArrayList<>();
        for (int i = 1; i <= num; i++) {
            if (num % i == 0)
                HHAns.add(i);
            else
                HHfalseAns.add(i);
        }
        Random random = new Random();
        HHFactor = HHAns.get(random.nextInt(HHAns.size()));
        HHTopText.setText("The factor of " + num + " is");

        HHposition = random.nextInt(3);
        HHansArray = assignAnsArray(HHansArray, HHfalseAns);
        HHansArray[HHposition] = HHFactor;

        HHButton0.setText(Integer.toString(HHansArray[0]));
        HHButton1.setText(Integer.toString(HHansArray[1]));
        HHButton2.setText(Integer.toString(HHansArray[2]));

        HHButton0.setOnClickListener(HHAnswerClicked);
        HHButton1.setOnClickListener(HHAnswerClicked);
        HHButton2.setOnClickListener(HHAnswerClicked);

        HHButtonCont.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                timeRemaining = 10;
                startActivity(getIntent());
            }
        });
        HHButtonMainMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(HlongStreak == 0){
                    HlongStreak = Hstreak;
                }
                final String reason2;
                reason2 = "Quit on Purpose";
                Intent i = new Intent(HHackerMode.this,HHackerResult.class);
                i.putExtra("Score",Integer.toString(Hscore));
                i.putExtra("Streak",Integer.toString(Hstreak));
                i.putExtra("LongestStreak",Integer.toString(HlongStreak));
                i.putExtra("Reason",reason2);
                ResetScore();
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
        Hscore = Hscore + 20;
        if(Hstreak > 7)
            Hscore = Hscore + 50;
        Hstreak = Hstreak + 1;
        SharedPreferences Score = getSharedPreferences("MyHScore",MODE_PRIVATE);
        SharedPreferences.Editor editor = Score.edit();
        editor.putInt("Hscore",Hscore);
        editor.apply();

        SharedPreferences Streak = getSharedPreferences("MyHStreak",MODE_PRIVATE);
        SharedPreferences.Editor editor1 = Streak.edit();
        editor1.putInt("Hstreak",Hstreak);
        editor1.apply();
    }

    public void DScoreSystem(){
        Hscore = Hscore - 20;
        if(Hstreak > HlongStreak)
            HlongStreak = Hstreak;
        Hstreak = 0;

        SharedPreferences Score = getSharedPreferences("MyHScore",MODE_PRIVATE);
        SharedPreferences.Editor editor = Score.edit();
        editor.putInt("Hscore",Hscore);
        editor.apply();

        SharedPreferences HLongStreak = getSharedPreferences("HLongestStreak",MODE_PRIVATE);
        SharedPreferences.Editor editor2 = HLongStreak.edit();
        editor2.putInt("HlongStreak",HlongStreak);
        editor2.apply();

        SharedPreferences Streak = getSharedPreferences("MyHStreak",MODE_PRIVATE);
        SharedPreferences.Editor editor1 = Streak.edit();
        editor1.putInt("Hstreak",Hstreak);
        editor1.apply();
    }




    public void ResetScore(){
        Hscore = 0;
        Hstreak = 0;
        HlongStreak = 0;

        SharedPreferences Score = getSharedPreferences("MyHScore",MODE_PRIVATE);
        SharedPreferences.Editor editor = Score.edit();
        editor.putInt("Hscore",Hscore);
        editor.apply();

        SharedPreferences HLongStreak = getSharedPreferences("HLongestStreak",MODE_PRIVATE);
        SharedPreferences.Editor editor2 = HLongStreak.edit();
        editor2.putInt("HlongStreak",HlongStreak);
        editor2.apply();

        SharedPreferences Streak = getSharedPreferences("MyHStreak",MODE_PRIVATE);
        SharedPreferences.Editor editor1 = Streak.edit();
        editor1.putInt("Hstreak",Hstreak);
        editor1.apply();
    }
}

