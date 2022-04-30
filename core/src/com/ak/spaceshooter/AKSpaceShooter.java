package com.ak.spaceshooter;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.Screen;

import java.util.Random;

public class AKSpaceShooter extends Game {
	private String level = "2";
	private ResultScreen resultScreen = (score,userWon) -> System.out.println("No result screen implemented. Score: "+score);
	private SoundSettings soundSettings = new SoundSettings() {
		@Override public boolean getSFX() { return true; }
		@Override public boolean getMusic() { return true; }
	};

	public void setLevel(String level) {
		this.level = level;
		System.out.println(level);
	}

	public AKSpaceShooter(){}
	public AKSpaceShooter(String level_number, ResultScreen resultScreen, SoundSettings soundSettings){
		level = level_number;
		this.resultScreen=resultScreen;
		this.soundSettings=soundSettings;
	}

	GameScreen gameScreen;

	public static Random random = new Random();

	@Override
	public void dispose() {
		super.dispose();
		gameScreen.dispose();
	}

	@Override
	public void render() {
		super.render();
	}

	@Override
	public void resize(int width, int height) {
		super.resize(width, height);
		gameScreen.resize(width,height);
	}

	@Override
	public void create() {
		gameScreen = new GameScreen(level,resultScreen, soundSettings);
		setScreen(gameScreen);


	}



}



