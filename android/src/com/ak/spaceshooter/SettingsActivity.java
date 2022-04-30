package com.ak.spaceshooter;

import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.widget.Button;

public class SettingsActivity extends AppCompatActivity {
    SharedPreferences sharedPref;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        sharedPref = PreferenceManager.getDefaultSharedPreferences(this);

        Button musicButton = findViewById(R.id.musicButton);
        musicButton.setOnClickListener((v)-> toggleButton((Button)v,"music"));
        updateButtonText(musicButton,"music");

        Button sfxButton = findViewById(R.id.sfxButton);
        sfxButton.setOnClickListener((v)-> toggleButton((Button)v,"sfx"));
        updateButtonText(sfxButton,"sfx");
    }
    public void toggleButton(Button b, String setting){
        boolean pref = sharedPref.getBoolean(setting,true);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putBoolean(setting,!pref);
        editor.commit();
        updateButtonText(b, setting);
    }

    public void updateButtonText(Button b, String setting){
        b.setText(
                sharedPref.getBoolean(setting,true)?"ON":"OFF"
        );
    }
}