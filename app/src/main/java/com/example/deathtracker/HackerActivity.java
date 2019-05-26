package com.example.deathtracker;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class HackerActivity extends AppCompatActivity {
private Button enterbtn,guessbtn,resetbtn;
private EditText age,guess;
private LinearLayout layout;
private TextView FailText,SucessText;
private int[] pallete;
private int CorrectAge, GuessedAge,fail,sucess;
public static final String SaveName = "DeathRecord";
public static final String Sucessref = "SucessRecord";
public static final String Failref = "FalureRecord";
public static final String AgeKey = "ageRecord";
private SharedPreferences save;
private SharedPreferences.Editor editor;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.hacker_activity);
        save = getSharedPreferences(SaveName,MODE_PRIVATE);
        editor = save.edit();
        enterbtn = findViewById(R.id.enter);
        guessbtn = findViewById(R.id.guessbtn);
        age = findViewById(R.id.age);
        guess = findViewById(R.id.guess);
        layout = findViewById(R.id.layout);
        FailText = findViewById(R.id.failtext);
        SucessText = findViewById(R.id.Sucesstext);
        resetbtn = findViewById(R.id.reset);
        fail = save.getInt(Failref,0);
        sucess = save.getInt(Sucessref,0);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu,menu);
        MenuItem menuItem = menu.findItem(R.id.hacker);
        menuItem.setVisible(false);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if(id == R.id.normal){
            startActivity(new Intent(getApplicationContext(),MainActivity.class));
        }
        return true;
    }

    @Override
    protected void onStart() {
        super.onStart();

        fail = save.getInt(Failref,0);
        sucess = save.getInt(Sucessref,0);
        FailText.setText("You Failed "+fail+" times");
        SucessText.setText("You succeeded "+sucess+" times");
        layout.setBackgroundColor(getResources().getColor(R.color.white));
        pallete = new int[]{R.color.hue0,R.color.hue1,R.color.hue2,R.color.hue3,R.color.hue4,R.color.hue5,R.color.hue6,R.color.hue7,R.color.hue8,R.color.hue9};
        CorrectAge = save.getInt(AgeKey,0);
        if(CorrectAge!=0 ){
            guessbtn.setVisibility(View.VISIBLE);
            guess.setVisibility(View.VISIBLE);
            age.setVisibility(View.GONE);
            enterbtn.setVisibility(View.GONE);
            age.onEditorAction(EditorInfo.IME_ACTION_DONE);
        }
        else {
            guessbtn.setVisibility(View.GONE);
            age.setText("");
            guess.setVisibility(View.GONE);
            age.setVisibility(View.VISIBLE);
            enterbtn.setVisibility(View.VISIBLE);
        }
        enterbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String temp = age.getText().toString();
                if(temp.equals("")){
                    Toast toast = Toast.makeText(getApplicationContext(),"enter the age",Toast.LENGTH_SHORT);
                    toast.show();
                }
                else{
                    CorrectAge = Integer.parseInt(temp);
                    if(CorrectAge<100 && CorrectAge>1) {
                        guessbtn.setVisibility(View.VISIBLE);
                        guess.setVisibility(View.VISIBLE);
                        age.setVisibility(View.GONE);
                        enterbtn.setVisibility(View.GONE);
                        age.onEditorAction(EditorInfo.IME_ACTION_DONE);
                        CorrectAge = Integer.parseInt(temp);
                        editor.putInt(AgeKey,CorrectAge);
                        editor.apply();
                    }
                    else{
                        Toast toast = Toast.makeText(getApplicationContext(),"enter a valid age (1 - 100)",Toast.LENGTH_SHORT);
                        toast.show();
                    }
                }

            }
        });

        guessbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String temp = guess.getText().toString();
                if (temp.equals("")) {
                    Toast toast = Toast.makeText(getApplicationContext(), "enter the guess", Toast.LENGTH_SHORT);
                    toast.show();
                } else {
                    guess.onEditorAction(EditorInfo.IME_ACTION_DONE);
                    GuessedAge = Integer.parseInt(temp);
                    guess.setText("");
                    int diff;
                    if (GuessedAge < CorrectAge) {
                        diff = ((CorrectAge-GuessedAge)/10);
                        fail++;
                        Toast.makeText(getApplicationContext(), "You guessed too low\nHe can live longer", Toast.LENGTH_LONG).show();
                    } else if (GuessedAge == CorrectAge) {
                        diff = 0;
                        editor.putInt(AgeKey,0);
                        editor.apply();
                        Intent winintent = new Intent(getApplicationContext(), winscreen.class);
                        startActivity(winintent);
                        onStart();
                        sucess++;

                    } else {
                        diff = ((GuessedAge-CorrectAge)/10);
                        Toast.makeText(getApplicationContext(), "Dont be so liberal\nHe won't live that long", Toast.LENGTH_LONG).show();
                        fail++;
                    }
                    layout.setBackgroundColor(getResources().getColor(pallete[diff]));
                    editor.putInt(Failref,fail);
                    editor.putInt(Sucessref,sucess);
                    editor.apply();
                    FailText.setText("You Failed "+fail+" times");
                    SucessText.setText("You succeeded "+sucess+" times");

                }
            }
        });

        resetbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editor.putInt(Failref,0);
                editor.putInt(Sucessref,0);
                editor.apply();
                onStart();
            }
        });

    }



}
