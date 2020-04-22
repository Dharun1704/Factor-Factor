package com.example.factorfactor;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class HHackerResult extends AppCompatActivity {

    String str;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.hhacker_result);

        Bundle details = getIntent().getExtras();
        if(details == null)
            return;

        String Score = details.getString("Score");
        String Streak = details.getString("Streak");
        String Reason = details.getString("Reason");
        String LongStreak = details.getString("LongestStreak");

        str = "Score: "+Score;
        str = str + "\nStreak: "+Streak;
        str = str + "\nLongest Streak: "+LongStreak;
        str = str + "\nReason for Elimination: "+Reason;

        TextView T2 = findViewById(R.id.HHresultsummary);
        T2.setText(str);

        Button btn = findViewById(R.id.HHbuttonMenu);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(HHackerResult.this,MainActivity.class);
                startActivity(i);
                finish();
            }
        });

    }
}
