package com.ak.spaceshooter;

import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Application;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3ApplicationConfiguration;
import com.ak.spaceshooter.AKSpaceShooter;

// Please note that on macOS your application needs to be started with the -XstartOnFirstThread JVM argument
public class DesktopLauncher {
	public static void main (String[] arg) {
		Lwjgl3ApplicationConfiguration config = new Lwjgl3ApplicationConfiguration();
		config.setWindowedMode(360,640);
//		config.height = 640;
//		config.width = 360;
		config.setForegroundFPS(60);

		new Lwjgl3Application(new AKSpaceShooter(), config);
	}
}
