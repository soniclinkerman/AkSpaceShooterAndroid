package com.ak.spaceshooter;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;

import com.badlogic.gdx.backends.android.AndroidApplication;
import com.badlogic.gdx.backends.android.AndroidApplicationConfiguration;
import com.ak.spaceshooter.AKSpaceShooter;

public class AndroidLauncher extends AndroidApplication {
	@Override
	protected void onCreate (Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Bundle extras = getIntent().getExtras();
		String level_number = extras.getString("level number");
		AndroidApplicationConfiguration config = new AndroidApplicationConfiguration();

		ResultScreen resultScreen = (score,userWon) -> {
			Intent intent = new Intent(getApplicationContext(), GameResultActivity.class);
			intent.putExtra("score", score);
			intent.putExtra("userWon", userWon);
			startActivity(intent);
		};
		SoundSettings soundSettings = new SoundSettings() {
			SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
			final boolean sfx=sharedPref.getBoolean("sfx",true);
			final boolean music=sharedPref.getBoolean("music",true);
			@Override
			public boolean getSFX() {
				return sfx;
			}
			@Override
			public boolean getMusic() {
				return music;
			}
		};

		initialize(new AKSpaceShooter(level_number,resultScreen,soundSettings), config);
	}
}
