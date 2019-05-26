package com.example.deathtracker;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.widget.TextView;

public class winscreen extends Activity {
    TextView small,big;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.win_layout);

        small = findViewById(R.id.smallTxt);
        big = findViewById(R.id.bigTxt);

        Intent intent = getIntent();
        if(intent.getBooleanExtra("isLost",false)){
            small.setText("You used 10 tries");
            big.setText(getResources().getText(R.string.you_lost));
        }
        else {
            small.setText("Congratulations");
            big.setText(getResources().getText(R.string.you_won));
        }
        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);

        int width = dm.widthPixels;
        int height = dm.heightPixels;

        getWindow().setLayout((int)(width*0.6),(int) (height*0.4));
    }

}
