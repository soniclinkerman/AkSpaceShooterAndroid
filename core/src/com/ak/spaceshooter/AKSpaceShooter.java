package com.ak.spaceshooter;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.Screen;

import java.util.Random;

public class AKSpaceShooter extends Game {
	private String level;

	public void setLevel(String level) {
		this.level = level;
		System.out.println(level);
	}

	public AKSpaceShooter(){}
	public AKSpaceShooter(String level_number){
		level = level_number;
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
		setLevel("2");
		gameScreen = new GameScreen(level);
		setScreen(gameScreen);


	}



}



