package com.robpercival.flappybird;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Rectangle;

import java.util.Random;

public class DefineVariables{

    DefineVariables(FlappyBird obj) {
        this.obj = obj;
    }

    private FlappyBird obj;
    public void create () {


        obj.batch = new SpriteBatch();
        obj.endBordScreen=new Texture("MedalPicBoard.png");
        obj.background = new Texture("bg.png");


        obj.gameover = new Texture("gameover.png");
        obj.taptoPlay=new Texture("TapToPlay.png");
        obj.name= new Texture("StartGamePic.png");

        //shapeRenderer = new ShapeRenderer();//la forme de nos rectangles
        obj. birdCircle = new Circle();
        obj.bestScore = new BitmapFont();
        obj.bestScore.setColor(Color.PURPLE);
        obj.bestScore.getData().setScale(5);
        obj.font = new BitmapFont();
        obj.font.setColor(Color.PURPLE);
        obj.font.getData().setScale(5);

        obj.medals = new Texture[4];
        obj.medals[0] = new Texture("BronzelPicBoard.png");
        obj.medals[1] = new Texture("SilverlPicBoard.png");
        obj.medals[2] = new Texture("GoldMedalPicBoard.png");
        obj.medals[3] = new Texture("DiamondMedalPicBoard.png");
        obj.oiseauxs  = new Texture[2];
        obj.oiseauxs [0] = new Texture("bird.png");
        obj.oiseauxs [1] = new Texture("bird2.png");


        obj.topTube = new Texture("toptube.png");
        obj.bottomTube = new Texture("bottomtube.png");
        obj.maxTubeOffset = Gdx.graphics.getHeight() / 2 -    obj.gap / 2 - 100;
        obj.generateurRandomme = new Random();
        obj.distanceBetweenTubes = Gdx.graphics.getWidth() * 3 / 4;
        obj.topTubeRectangles = new Rectangle[obj.numeroDesTubes];
        obj.bottomTubeRectangles = new Rectangle[obj.numeroDesTubes];






        startGame();



    }

    public void startGame() {

        obj.birdY = Gdx.graphics.getHeight() / 2 - obj.oiseauxs [0].getHeight() / 2;

        for (int i = 0; i < obj.numeroDesTubes; i++) {

            obj.tubeOffset[i] = (obj.generateurRandomme.nextFloat() - 0.5f) * (Gdx.graphics.getHeight() - obj.gap - 200);

            obj.tubeX[i] = Gdx.graphics.getWidth() / 2 - obj.topTube.getWidth() / 2 + Gdx.graphics.getWidth() + i * obj.distanceBetweenTubes;

            obj.topTubeRectangles[i] = new Rectangle();
            obj.bottomTubeRectangles[i] = new Rectangle();


        }

    }


}
