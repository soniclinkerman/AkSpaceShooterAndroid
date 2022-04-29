package com.ak.spaceshooter;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LiveData;

import com.ak.spaceshooter.db.Level;
import com.ak.spaceshooter.db.LevelDatabase;

import java.util.List;

public class GameResultActivity extends AppCompatActivity {
    @Override
    protected void onCreate (Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_results);
        Bundle extras = getIntent().getExtras();
        int score = extras.getInt("score");
        boolean userWon = extras.getBoolean("userWon");

        TextView winText = findViewById(R.id.userWon);
        if(userWon){
            winText.setText("Y O U  W O N !");//TODO: add share to twitter button
        }
        else{
            winText.setText("Y O U  L O S T");
            winText.setTextColor(getResources().getColor(R.color.design_default_color_error));
        }

        TextView points = findViewById(R.id.points);
        points.setText(String.valueOf(score));

        ((Button)findViewById(R.id.back_to_main_menu)).setOnClickListener(this::onClickMainMenu);

    }
    public void onClickMainMenu(View v){
        //LevelDatabase levelDatabase = LevelDatabase.getDatabase(this);
        //LiveData<List<Level>> levels = levelDatabase.levelDAO().getAll();
        //TODO: write score to room database
        startActivity(new Intent(this, MainMenuActivity.class));

    }
}