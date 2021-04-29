package com.robpercival.flappybird;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.TimeUtils;

import java.util.Random;
import java.util.concurrent.TimeUnit;


public class FlappyBird extends ApplicationAdapter  {
	DefineVariables nosVariableAppele = new DefineVariables(this);

	//ShapeRenderer shapeRenderer;
	SpriteBatch batch;
	Texture background;
	Texture name;
	Texture gameover;
	Texture taptoPlay;
	Texture topTube;
	Texture bottomTube;
	Texture[] oiseauxs;
	Texture endBordScreen;
	Texture[] medals;



	int bestValue = 0;
	int flapState = 0;
	float birdY = 0;
	float velocity = 0;
	Circle birdCircle;
	int score = 0;
	int scoringTube = 0;
	BitmapFont font,bestScore;

	int jeuxStatus  = 0;
	float gravity = 2;


	float gap = 800;
	float maxTubeOffset;
	Random generateurRandomme;
	float tubeVelocity = 9;
	int numeroDesTubes = 4;
	float[] tubeX = new float[numeroDesTubes];
	float[] tubeOffset = new float[numeroDesTubes];
	float distanceBetweenTubes;
	Rectangle[] topTubeRectangles;
	Rectangle[] bottomTubeRectangles;

	@Override
	public void create(){
		nosVariableAppele.create();
	}

	@Override
	public void render () {

		batch.begin();
		batch.draw(background, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());



		if (jeuxStatus  == 1) {
			if (tubeX[scoringTube] < Gdx.graphics.getWidth() / 2) {
				score++;
				if(score > bestValue){
					bestValue++;
				}
				Gdx.app.log("Score", String.valueOf(score));
				if (scoringTube < numeroDesTubes - 1) {
					scoringTube++;
				} else {
					scoringTube = 0;
				}
			}
			if (Gdx.input.justTouched()) {
				velocity = -30;
			}
			for (int i = 0; i < numeroDesTubes; i++) {
				if (tubeX[i] < - topTube.getWidth()) {
					tubeX[i] += numeroDesTubes * distanceBetweenTubes;
					tubeOffset[i] = (generateurRandomme.nextFloat() - 0.5f) * (Gdx.graphics.getHeight() - gap - 200);
				} else tubeX[i] = tubeX[i] - tubeVelocity;


				batch.draw(topTube, tubeX[i], Gdx.graphics.getHeight() / 2 + gap / 2 + tubeOffset[i]);
				batch.draw(bottomTube, tubeX[i], Gdx.graphics.getHeight() / 2 - gap / 2 - bottomTube.getHeight() + tubeOffset[i]);

				batch.draw(oiseauxs [flapState], Gdx.graphics.getWidth() / 2 - oiseauxs [flapState].getWidth() / 2, birdY);

				topTubeRectangles[i] = new Rectangle(tubeX[i], Gdx.graphics.getHeight() / 2 + gap / 2 + tubeOffset[i], topTube.getWidth(), topTube.getHeight());
				bottomTubeRectangles[i] = new Rectangle(tubeX[i], Gdx.graphics.getHeight() / 2 - gap / 2 - bottomTube.getHeight() + tubeOffset[i], bottomTube.getWidth(), bottomTube.getHeight());
			}

			if (birdY > 0) {

				velocity = velocity + gravity;
				birdY -= velocity;

			} else {

				jeuxStatus  = 2;

			}

		} else if (jeuxStatus  == 0) {

			batch.draw(name,(Gdx.graphics.getWidth()/2)-name.getWidth()*2,Gdx.graphics.getHeight()/2+(name.getHeight()*4),name.getWidth()*4,name.getHeight()*4);
			batch.draw(taptoPlay,(Gdx.graphics.getWidth() / 2)-name.getWidth() * 2 /1 /2-20,Gdx.graphics.getHeight()/2-(name.getHeight()*4)-200,taptoPlay.getWidth()*4,taptoPlay.getHeight()*4);

			if (Gdx.input.justTouched()) {

				jeuxStatus  = 1;


			}

		} else if (jeuxStatus == 2) {


			batch.draw(gameover, Gdx.graphics.getWidth() / 2 - gameover.getWidth() / 2, Gdx.graphics.getHeight() / 2 - gameover.getHeight() / 2);
			batch.draw(endBordScreen,Gdx.graphics.getWidth()/2-gameover.getWidth()/2-Gdx.graphics.getWidth()/7,Gdx.graphics.getHeight()/2-endBordScreen.getHeight(),Gdx.graphics.getWidth()-Gdx.graphics.getWidth()/4,Gdx.graphics.getHeight()/4);
			if(score>30) batch.draw(medals[3],Gdx.graphics.getWidth()/2-gameover.getWidth()/2-Gdx.graphics.getWidth()/7,Gdx.graphics.getHeight()/2-endBordScreen.getHeight(),Gdx.graphics.getWidth()-Gdx.graphics.getWidth()/4,Gdx.graphics.getHeight()/4);
			else if(score>20) batch.draw(medals[2],Gdx.graphics.getWidth()/2-gameover.getWidth()/2-Gdx.graphics.getWidth()/7,Gdx.graphics.getHeight()/2-endBordScreen.getHeight(),Gdx.graphics.getWidth()-Gdx.graphics.getWidth()/4,Gdx.graphics.getHeight()/4);
			else if(score>10) batch.draw(medals[1],Gdx.graphics.getWidth()/2-gameover.getWidth()/2-Gdx.graphics.getWidth()/7,Gdx.graphics.getHeight()/2-endBordScreen.getHeight(),Gdx.graphics.getWidth()-Gdx.graphics.getWidth()/4,Gdx.graphics.getHeight()/4);
			else batch.draw(medals[0],Gdx.graphics.getWidth()/2-gameover.getWidth()/2-Gdx.graphics.getWidth()/7,Gdx.graphics.getHeight()/2-endBordScreen.getHeight(),Gdx.graphics.getWidth()-Gdx.graphics.getWidth()/4,Gdx.graphics.getHeight()/4);
			font.draw(batch, String.valueOf(score), Gdx.graphics.getWidth()/2+Gdx.graphics.getWidth()/3-110, Gdx.graphics.getHeight()/2+240);

			bestScore.draw(batch, String.valueOf(bestValue), Gdx.graphics.getWidth()/2+Gdx.graphics.getWidth()/3-110, Gdx.graphics.getHeight()/2+50);

			if (Gdx.input.justTouched()) {

				jeuxStatus = 1;
				nosVariableAppele.startGame();
				score = 0;
				scoringTube = 0;
				velocity = 0;


			}

		}

		if (flapState == 0) {//chandement de la Texture graphiaue pour aue les  ailes bouges ,

			flapState = 1;
		} else {
			flapState = 0;
		}



		//batch.draw(oiseauxs [flapState], Gdx.graphics.getWidth() / 2 - oiseauxs [flapState].getWidth() / 2, birdY);



		birdCircle.set(Gdx.graphics.getWidth() / 2, birdY + oiseauxs [flapState].getHeight() / 2, oiseauxs [flapState].getWidth() / 2);



		//shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
		//shapeRenderer.setColor(Color.RED);
		//shapeRenderer.circle(birdCircle.x, birdCircle.y, birdCircle.radius);

		for (int i = 0; i < numeroDesTubes; i++) {

			//shapeRenderer.rect(tubeX[i], Gdx.graphics.getHeight() / 2 + gap / 2 + tubeOffset[i], topTube.getWidth(), topTube.getHeight());
			//shapeRenderer.rect(tubeX[i], Gdx.graphics.getHeight() / 2 - gap / 2 - bottomTube.getHeight() + tubeOffset[i], bottomTube.getWidth(), bottomTube.getHeight());


			if (Intersector.overlaps(birdCircle, topTubeRectangles[i]) || Intersector.overlaps(birdCircle, bottomTubeRectangles[i])) {

				jeuxStatus  = 2;

			}

		}

		batch.end();

		//shapeRenderer.end();



	}


}
