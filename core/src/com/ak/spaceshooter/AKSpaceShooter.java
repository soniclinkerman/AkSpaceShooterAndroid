package com.ak.spaceshooter;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.ScreenUtils;

public class AKSpaceShooter extends Game {

	GameScreen gameScreen;

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
		gameScreen = new GameScreen();
		setScreen(gameScreen);



	}
}
