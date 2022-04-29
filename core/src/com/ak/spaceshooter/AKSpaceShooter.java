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

	public void setLevel(String level) {
		this.level = level;
		System.out.println(level);
	}

	public AKSpaceShooter(){}
	public AKSpaceShooter(String level_number, ResultScreen resultScreen){
		level = level_number;
		this.resultScreen=resultScreen;
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
		gameScreen = new GameScreen(level,resultScreen);
		setScreen(gameScreen);


	}



}



