package com.ak.spaceshooter;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.ScreenUtils;

import java.util.Random;

public class AKSpaceShooter extends Game {
	private String level;
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
		gameScreen = new GameScreen(level);
		setScreen(gameScreen);



	}
}
