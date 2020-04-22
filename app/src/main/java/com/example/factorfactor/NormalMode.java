package com.example.factorfactor;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.Random;

public class NormalMode extends AppCompatActivity {

    int factor;
    TextView TopText, BottomText;
    Button buttonG, button0, button1, button2, buttonTryAgain, buttonMainMenu;

    View.OnClickListener answerClicked = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Button btnClicked = (Button) v;
            int isAnswer = Integer.parseInt(btnClicked.getText().toString());
            BottomText.setVisibility(View.VISIBLE);
            if(isAnswer == factor)
                BottomText.setText("\nCorrect Answer! Good Job!");
            else {
                BottomText.setText("\nThe correct answer is " + factor );
            }
            buttonTryAgain.setVisibility(View.VISIBLE);
            buttonMainMenu.setVisibility(View.VISIBLE);
            buttonTryAgain.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    finish();
                    startActivity(getIntent());
                }
            });
        }
    };

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.normal_mode);

        final EditText number = findViewById(R.id.editText);

        buttonTryAgain = findViewById(R.id.buttont);
        buttonTryAgain.setVisibility(View.INVISIBLE);
        buttonG = findViewById(R.id.buttong);
        button0 = findViewById(R.id.button0);
        button1 = findViewById(R.id.button1);
        button2 = findViewById(R.id.button2);
        buttonMainMenu = findViewById(R.id.buttonm);
        buttonMainMenu.setVisibility(View.INVISIBLE);
        button0.setVisibility(View.INVISIBLE);
        button1.setVisibility(View.INVISIBLE);
        button2.setVisibility(View.INVISIBLE);
        TopText = findViewById(R.id.textViewques);
        TopText.setVisibility(View.INVISIBLE);
        BottomText = findViewById(R.id.textViewbottom);

        BottomText.setText("Enter the Number and Press GO");

        buttonG.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                buttonG.setVisibility(View.INVISIBLE);
                number.setEnabled(false);
                BottomText.setVisibility(View.INVISIBLE);
                button0.setVisibility(View.VISIBLE);
                button1.setVisibility(View.VISIBLE);
                button2.setVisibility(View.VISIBLE);
                TopText.setVisibility(View.VISIBLE);
                StartQuiz(Integer.parseInt(number.getText().toString()));
            }
        });
    }
    public void StartQuiz(int num){
        int position;
        int[] ansArray = new int[] {0,1,2};
        ArrayList<Integer> Ans = new ArrayList<>();
        ArrayList<Integer> falseAns = new ArrayList<>();
        for(int i = 1; i <= num; i++){
            if (num % i == 0)
                Ans.add(i);
            else
                falseAns.add(i);
        }
        Random random = new Random();
        factor = Ans.get(random.nextInt(Ans.size()));
        TopText.setText("The factor of " + num + " is");

        position = random.nextInt(3);
        ansArray = assignAnsArray(ansArray,falseAns);
        ansArray[position] = factor;

        button0.setText(Integer.toString(ansArray[0]));
        button1.setText(Integer.toString(ansArray[1]));
        button2.setText(Integer.toString(ansArray[2]));

        button0.setOnClickListener(answerClicked);
        button1.setOnClickListener(answerClicked);
        button2.setOnClickListener(answerClicked);

        buttonMainMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(NormalMode.this,MainActivity.class);
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

}
