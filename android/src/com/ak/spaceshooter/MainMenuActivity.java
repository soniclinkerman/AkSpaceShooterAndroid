package com.ak.spaceshooter;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LiveData;

import com.ak.spaceshooter.db.Level;
import com.ak.spaceshooter.db.LevelDatabase;
import com.badlogic.gdx.backends.android.AndroidApplicationConfiguration;

import java.util.List;

public class MainMenuActivity extends AppCompatActivity {
    @Override
    protected void onCreate (Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);
        final Button playButton = (Button) findViewById(R.id.play);
        playButton.setOnClickListener(this::onClickPlay);


    }
    public void onClickPlay(View v){
        LevelDatabase levelDatabase = LevelDatabase.getDatabase(this);
        LiveData<List<Level>> levels = levelDatabase.levelDAO().getAll();
        startActivity(new Intent(this, LevelSelectActivity.class));

    }
    //sanket was here
}
