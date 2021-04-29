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


public class FlappyBird extends ApplicationAdapter  { //La bibliothèque GDX est créée pour la programmation de jeux adaptatifs et basés sur des preuves pour Android et iOS.
	DefineVariables nosVariableAppele = new DefineVariables(this);

	//ShapeRenderer shapeRenderer;
	SpriteBatch batch;
	Texture background,name,gameover,taptoPlay,topTube,bottomTube,endBordScreen; //Appelle de Class Texture
	Texture[] oiseauxs;
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
	//methode main n est pas utilise car la methode begin() nous affecte un boucle qui se repete pendant le jeux
	@Override
	public void render () {

		batch.begin();
		batch.draw(background, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());//notre fond d'ecran



		if (jeuxStatus  == 1) {//Le status de jeux qui affecte et nous lance tout le jeux 1 veut dire lance qund 2 arret
			if (tubeX[scoringTube] < Gdx.graphics.getWidth() / 2) {
				score++;
				if(score > bestValue){ //le BEST score
					bestValue++;
				}
				Gdx.app.log("Score", String.valueOf(score));//de score que se montre dans le terminal pour povoir voir de changement
				if (scoringTube < numeroDesTubes - 1) { //Numero des tubes utilise car sur un ecran on dispose de 4 tube qui change leur position est se refreshisant
					scoringTube++;
				} else {
					scoringTube = 0;
				}
			}
			if (Gdx.input.justTouched()) {//changement de la saut de Oiseaux
				velocity = -30;
			}
			for (int i = 0; i < numeroDesTubes; i++) {//generateur des tubes
				if (tubeX[i] < - topTube.getWidth()) {
					tubeX[i] += numeroDesTubes * distanceBetweenTubes;
					tubeOffset[i] = (generateurRandomme.nextFloat() - 0.5f) * (Gdx.graphics.getHeight() - gap - 200);
				} else tubeX[i] = tubeX[i] - tubeVelocity;


				batch.draw(topTube, tubeX[i], Gdx.graphics.getHeight() / 2 + gap / 2 + tubeOffset[i]);//Affichage des tubes on se raperont sur le ordre X et Y
				batch.draw(bottomTube, tubeX[i], Gdx.graphics.getHeight() / 2 - gap / 2 - bottomTube.getHeight() + tubeOffset[i]);

				batch.draw(oiseauxs [flapState], Gdx.graphics.getWidth() / 2 - oiseauxs [flapState].getWidth() / 2, birdY);

				topTubeRectangles[i] = new Rectangle(tubeX[i], Gdx.graphics.getHeight() / 2 + gap / 2 + tubeOffset[i], topTube.getWidth(), topTube.getHeight());
				bottomTubeRectangles[i] = new Rectangle(tubeX[i], Gdx.graphics.getHeight() / 2 - gap / 2 - bottomTube.getHeight() + tubeOffset[i], bottomTube.getWidth(), bottomTube.getHeight());
			}

			if (birdY > 0) {//SE ropere a la position de oisiaux et dans un sens de gravite vers le bas

				velocity = velocity + gravity;
				birdY -= velocity;

			} else {

				jeuxStatus  = 2;

			}

		} else if (jeuxStatus  == 0) {//debut de jeux
			//les dessin suplementer comme nom touche
			batch.draw(name,(Gdx.graphics.getWidth()/2)-name.getWidth()*2,Gdx.graphics.getHeight()/2+(name.getHeight()*4),name.getWidth()*4,name.getHeight()*4);
			batch.draw(taptoPlay,(Gdx.graphics.getWidth() / 2)-name.getWidth() * 2 /1 /2-20,Gdx.graphics.getHeight()/2-(name.getHeight()*4)-200,taptoPlay.getWidth()*4,taptoPlay.getHeight()*4);

			if (Gdx.input.justTouched()) {

				jeuxStatus  = 1;


			}

		} else if (jeuxStatus == 2) {//arret de jeux


			batch.draw(gameover, Gdx.graphics.getWidth() / 2 - gameover.getWidth() / 2, Gdx.graphics.getHeight() / 2 - gameover.getHeight() / 2);
			batch.draw(endBordScreen,Gdx.graphics.getWidth()/2-gameover.getWidth()/2-Gdx.graphics.getWidth()/7,Gdx.graphics.getHeight()/2-endBordScreen.getHeight(),Gdx.graphics.getWidth()-Gdx.graphics.getWidth()/4,Gdx.graphics.getHeight()/4);
			//le affichage des medalle
			if(score>30) batch.draw(medals[3],Gdx.graphics.getWidth()/2-gameover.getWidth()/2-Gdx.graphics.getWidth()/7,Gdx.graphics.getHeight()/2-endBordScreen.getHeight(),Gdx.graphics.getWidth()-Gdx.graphics.getWidth()/4,Gdx.graphics.getHeight()/4);
			else if(score>20) batch.draw(medals[2],Gdx.graphics.getWidth()/2-gameover.getWidth()/2-Gdx.graphics.getWidth()/7,Gdx.graphics.getHeight()/2-endBordScreen.getHeight(),Gdx.graphics.getWidth()-Gdx.graphics.getWidth()/4,Gdx.graphics.getHeight()/4);
			else if(score>10) batch.draw(medals[1],Gdx.graphics.getWidth()/2-gameover.getWidth()/2-Gdx.graphics.getWidth()/7,Gdx.graphics.getHeight()/2-endBordScreen.getHeight(),Gdx.graphics.getWidth()-Gdx.graphics.getWidth()/4,Gdx.graphics.getHeight()/4);
			else batch.draw(medals[0],Gdx.graphics.getWidth()/2-gameover.getWidth()/2-Gdx.graphics.getWidth()/7,Gdx.graphics.getHeight()/2-endBordScreen.getHeight(),Gdx.graphics.getWidth()-Gdx.graphics.getWidth()/4,Gdx.graphics.getHeight()/4);
			font.draw(batch, String.valueOf(score), Gdx.graphics.getWidth()/2+Gdx.graphics.getWidth()/3-110, Gdx.graphics.getHeight()/2+240);

			bestScore.draw(batch, String.valueOf(bestValue), Gdx.graphics.getWidth()/2+Gdx.graphics.getWidth()/3-110, Gdx.graphics.getHeight()/2+50);

			if (Gdx.input.justTouched()) {//just touched est la sourie appuyes

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



		birdCircle.set(Gdx.graphics.getWidth() / 2, birdY + oiseauxs [flapState].getHeight() / 2, oiseauxs [flapState].getWidth() / 2);//le boxHit de oiseaux



		//shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);//des visualisation des hitBOX
		//shapeRenderer.setColor(Color.RED);
		//shapeRenderer.circle(birdCircle.x, birdCircle.y, birdCircle.radius);

		for (int i = 0; i < numeroDesTubes; i++) {//le senseur

			//shapeRenderer.rect(tubeX[i], Gdx.graphics.getHeight() / 2 + gap / 2 + tubeOffset[i], topTube.getWidth(), topTube.getHeight());
			//shapeRenderer.rect(tubeX[i], Gdx.graphics.getHeight() / 2 - gap / 2 - bottomTube.getHeight() + tubeOffset[i], bottomTube.getWidth(), bottomTube.getHeight());


			if (Intersector.overlaps(birdCircle, topTubeRectangles[i]) || Intersector.overlaps(birdCircle, bottomTubeRectangles[i])) {//attrape le fait quand l'oiseaux a touche la tube

				jeuxStatus  = 2;

			}

		}

		batch.end();

		//shapeRenderer.end();



	}


}
