package com.example.deathtracker;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
private Button enterbtn,guessbtn;
private EditText age,guess;
private int CorrectAge, GuessedAge,count;
public static final String SaveName = "DeathRecord";
public static final String AgeKey = "ageRecord";
    private SharedPreferences save;
    private SharedPreferences.Editor editor;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        enterbtn = findViewById(R.id.enter);
        guessbtn = findViewById(R.id.guessbtn);
        age = findViewById(R.id.age);
        guess = findViewById(R.id.guess);
        save = getSharedPreferences(SaveName,MODE_PRIVATE);
        editor = save.edit();

    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu,menu);
        MenuItem menuItem = menu.findItem(R.id.normal);
        menuItem.setVisible(false);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if(id == R.id.hacker);{
            startActivity(new Intent(getApplicationContext(),HackerActivity.class));
        }
        return true;
    }

    @Override
    protected void onStart() {
        super.onStart();

        count = 0;

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
                    count++;
                    guess.onEditorAction(EditorInfo.IME_ACTION_DONE);
                    GuessedAge = Integer.parseInt(temp);
                    guess.setText("");
                    if (GuessedAge < CorrectAge) {
                        Toast.makeText(getApplicationContext(), "You guessed too low\nHe can live longer", Toast.LENGTH_LONG).show();
                    } else if (GuessedAge == CorrectAge) {
                        editor.putInt(AgeKey,0);
                        editor.apply();
                        Intent intent = new Intent(getApplicationContext(), winscreen.class);
                        startActivity(intent);
                        onStart();

                    } else {
                        Toast.makeText(getApplicationContext(), "Dont be so liberal\nHe won't live that long", Toast.LENGTH_LONG).show();
                    }
                    if (count >= 10) {
                        editor.putInt(AgeKey,0);
                        Intent intent = new Intent(getApplicationContext(),winscreen.class);
                        intent.putExtra("isLost",true);
                        startActivity(intent);
                        onStart();
                    }

                }
            }
        });

    }


}
