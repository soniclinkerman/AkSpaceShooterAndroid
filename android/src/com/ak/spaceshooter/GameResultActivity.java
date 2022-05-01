package com.ak.spaceshooter;

import android.content.Intent;
import android.net.Uri;
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
        String level_number = extras.getString("level number");
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
        LevelDatabase.getDatabase(this);
        System.out.println("LEVEL NUMBER "+level_number);
        LevelDatabase.getLevel(level_number,level -> {
            if(userWon)
                level.completed=true;
            level.high_score=Math.max(score, level.high_score);
            LevelDatabase.updateLevel(level);
        });


        ((Button)findViewById(R.id.back_to_main_menu)).setOnClickListener(this::onClickMainMenu);
        ((Button)findViewById(R.id.twitterShare)).setOnClickListener((view)->{
            String url = "http://www.twitter.com/intent/tweet?text="+Uri.encode("I just got a score of "+score+" in Space Shooter! Can you beat my score? Download now!");
            Intent i = new Intent(Intent.ACTION_VIEW);
            i.setData(Uri.parse(url));
            startActivity(i);
        });

    }
    public void onClickMainMenu(View v){
        //LevelDatabase levelDatabase = LevelDatabase.getDatabase(this);
        //LiveData<List<Level>> levels = levelDatabase.levelDAO().getAll();
        //TODO: write score to room database
        startActivity(new Intent(this, MainMenuActivity.class));

    }
}