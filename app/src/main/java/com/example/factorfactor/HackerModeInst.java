package com.example.factorfactor;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class HackerModeInst extends AppCompatActivity {

    String str;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.hacker_mode_inst);

        Button start = (Button) findViewById(R.id.buttonStart);
        TextView inst = (TextView) findViewById(R.id.instruction);
        TextView wel = (TextView) findViewById(R.id.welcomeH);
        wel.setText("welcome to Hacker Mode");
        str = "Intructions:";
        str = str + "\n1] Each correct answer adds 20 points to the score.";
        str = str + "\n2] Each wrong answer will remove 10 points from the score";
        str = str + "\n3] For Streak greater than 7, 50 point would be provided.";
        str = str + "\n4] Continue the game until you get bored.";
        str = str + "\n5] Happy Factorizing";
        inst.setText(str);
        inst.setTextColor(Color.rgb(241,241,241));

        start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(HackerModeInst.this, HackerMode.class);
                startActivity(i);
                finish();
            }
        });
    }
}
