package com.example.factorfactor;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class HHackerModeInst extends AppCompatActivity {

    String str;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.hhacker_mode_inst);

        TextView t1 = findViewById(R.id.HHwelcomeH);
        TextView t2 = findViewById(R.id.HHinstruction);
        t1.setText("Welcome to Hacker Mode ++");
        str = "Intructions:";
        str = str + "\n\n1] Each correct answer adds 20 points to the score.";
        str = str + "\n\n2] For Streak greater than 7, 50 point would be provided.";
        str = str + "\n\n3] Each wrong answer will remove 20 points from the score.";
        str = str + "\n\n3] Each Question has a time limit of 10 sec.";
        str = str + "\n\n4] The Quiz will stop if no option is selected within the time limit.";
        str = str + "\n\n5] Happy Factorizing";
        t2.setText(str);
        t2.setTextColor(Color.rgb(241,241,241));

        Button start = findViewById(R.id.HHbuttonStart);
        start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(HHackerModeInst.this,HHackerMode.class);
                startActivity(i);
                finish();
            }
        });
    }
}
