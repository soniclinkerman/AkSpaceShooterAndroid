package com.ak.spaceshooter;

import android.os.Bundle;

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
		initialize(new AKSpaceShooter(level_number), config);
	}
}
