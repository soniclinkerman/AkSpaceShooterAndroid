package com.ak.spaceshooter;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.badlogic.gdx.backends.android.AndroidApplicationConfiguration;

public class MainMenuActivity extends AppCompatActivity {
    @Override
    protected void onCreate (Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);
        final Button playButton = (Button) findViewById(R.id.play);
        playButton.setOnClickListener(v -> startActivity(new Intent(this, AndroidLauncher.class)));
    }
}
