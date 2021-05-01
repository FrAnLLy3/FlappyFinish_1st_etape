package com.robpercival.flappybird;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;

import java.awt.Menu;
import java.util.Random;

public class DefineVariables{//une Classe pour apppelle de variable pour que le code d execution soit plus compreencible

    DefineVariables(FlappyBird obj) {
        this.obj = obj;
    }

    private FlappyBird obj;
    public void create () {



        obj.batch = new SpriteBatch();

        obj.mousePosition = new Vector2();
        OrthographicCamera cam;

        obj.positionInversefonsee = new Texture("InverseePicMenuFonsee.png");
        obj.positionIversee = new Texture("InverseePicMenu.png");
        obj.positionNormale = new Texture("NormalePicMenu.png");
        obj.endBordScreen=new Texture("MedalPicBoard.png");
        obj.background = new Texture("bg.png");

        obj.beeObstacle = new Texture("BeeImage.png");

        obj.gameover = new Texture("gameover.png");
        obj.taptoPlay=new Texture("TapToPlay.png");
        obj.name= new Texture("StartGamePic.png");

      //  obj.shapeRenderer = new ShapeRenderer();//la forme de nos rectangles


        obj. birdCircle = new Circle();
        obj.beeCircleMoving = new Circle();
        obj.beeCircle = new Circle();
        obj.bestScore = new BitmapFont();
        obj.bestScore.setColor(Color.PURPLE);
        obj.bestScore.getData().setScale(5);
        obj.font = new BitmapFont();
        obj.font.setColor(Color.PURPLE);
        obj.font.getData().setScale(5);

        obj.lesMenuesInversee = new Rectangle[2] ;
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

    public void startGame() {//la methode quelle comence le jeux

        obj.birdY = Gdx.graphics.getHeight() / 2 - obj.oiseauxs [0].getHeight() / 2;

        for (int i = 0; i < obj.numeroDesTubes; i++) {

            obj.tubeOffset[i] = (obj.generateurRandomme.nextFloat() - 0.5f) * (Gdx.graphics.getHeight() - obj.gap - 200);

            obj.tubeX[i] = Gdx.graphics.getWidth() / 2 - obj.topTube.getWidth() / 2 + Gdx.graphics.getWidth() + i * obj.distanceBetweenTubes;

            obj.topTubeRectangles[i] = new Rectangle();
            obj.bottomTubeRectangles[i] = new Rectangle();


        }

    }

    public void versionInverseCode(){


        obj.lesMenuesInversee[1]= new Rectangle((Gdx.graphics.getWidth() / 2)-obj.name.getWidth() * 2 /1 /2-20,Gdx.graphics.getHeight()/4,obj.positionNormale.getWidth()*5/4,obj.positionNormale.getHeight());
        obj.lesMenuesInversee[0]= new Rectangle((Gdx.graphics.getWidth() / 2)-obj.name.getWidth() * 2 /1 /2-20+obj.positionNormale.getWidth()*5/4,Gdx.graphics.getHeight()/4,obj.positionNormale.getWidth()*5/4,obj.positionNormale.getHeight());
        obj.batch.draw(obj.background, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());//notre fond d'ecran
        int track = Gdx.input.getX();



        if (obj.jeuxStatus  == 1) {//Le status de jeux qui affecte et nous lance tout le jeux 1 veut dire lance qund 2 arret
            if (obj.tubeX[obj.scoringTube] < Gdx.graphics.getWidth() / 2) {
                obj.score++;
                if(obj.score > obj.bestValue){ //le BEST score
                    obj.bestValue++;
                }
                Gdx.app.log("Score", String.valueOf(obj.score));//de score que se montre dans le terminal pour povoir voir de changement
                if (obj.scoringTube < obj.numeroDesTubes - 1) { //Numero des tubes utilise car sur un ecran on dispose de 4 tube qui change leur position est se refreshisant
                    obj.scoringTube++;
                } else {
                    obj.scoringTube = 0;
                }
            }
            if (Gdx.input.justTouched()) {//changement de la saut de Oiseaux
                obj.velocity = -30;
            }
            for (int i = 0; i < obj.numeroDesTubes; i++) {//generateur des tubes
                if (obj.tubeX[i] < - obj.topTube.getWidth()) {
                    obj.tubeX[i] += obj.numeroDesTubes * obj.distanceBetweenTubes;
                    obj.tubeOffset[i] = (obj.generateurRandomme.nextFloat() - 0.5f) * (Gdx.graphics.getHeight() - obj.gap - 200);
                } else obj.tubeX[i] = obj.tubeX[i] - obj.tubeVelocity;


                obj.batch.draw(obj.topTube, obj.tubeX[i], Gdx.graphics.getHeight() / 2 + obj.gap / 2 + obj.tubeOffset[i]);//Affichage des tubes on se raperont sur le ordre X et Y
                obj.batch.draw(obj.bottomTube, obj.tubeX[i], Gdx.graphics.getHeight() / 2 - obj.gap / 2 - obj.bottomTube.getHeight() + obj.tubeOffset[i]);

                obj.batch.draw(obj.oiseauxs [obj.flapState], Gdx.graphics.getWidth() / 2 - obj.oiseauxs [obj.flapState].getWidth() / 2, obj.birdY);

                obj.topTubeRectangles[i] = new Rectangle(obj.tubeX[i], Gdx.graphics.getHeight() / 2 + obj.gap / 2 + obj.tubeOffset[i], obj.topTube.getWidth(), obj.topTube.getHeight());
                obj.bottomTubeRectangles[i] = new Rectangle(obj.tubeX[i], Gdx.graphics.getHeight() / 2 - obj.gap / 2 - obj.bottomTube.getHeight() + obj.tubeOffset[i], obj.bottomTube.getWidth(), obj.bottomTube.getHeight());
            }

            if (obj.birdY > 0) {//SE ropere a la position de oisiaux et dans un sens de gravite vers le bas

                obj.velocity = obj.velocity + obj.gravity;
                obj.birdY -= obj.velocity;

            } else {

                obj.jeuxStatus  = 2;

            }

        } else if (obj.jeuxStatus  == 0) {//debut de jeux
            //les dessin suplementer comme nom touche


            obj.batch.draw(obj.name,(Gdx.graphics.getWidth()/2)-obj.name.getWidth()*2,Gdx.graphics.getHeight()/2+(obj.name.getHeight()*4),obj.name.getWidth()*4,obj.name.getHeight()*4);
            obj.batch.draw(obj.taptoPlay,(Gdx.graphics.getWidth() / 2)-obj.name.getWidth() * 2 /1 /2-20,Gdx.graphics.getHeight()/2-(obj.name.getHeight()*4)-200,obj.taptoPlay.getWidth()*4,obj.taptoPlay.getHeight()*4);
            obj.batch.draw(obj.positionNormale,(Gdx.graphics.getWidth() / 2)-obj.name.getWidth() * 2 /1 /2-20,Gdx.graphics.getHeight()/4,obj.positionNormale.getWidth()*5/4,obj.positionIversee.getHeight()*5/4);
            obj.batch.draw(obj.positionIversee,(Gdx.graphics.getWidth() / 2)-obj.name.getWidth() * 2 /1 /2-20+obj.positionNormale.getWidth()*5/4,Gdx.graphics.getHeight()/4,obj.positionNormale.getWidth()*5/4,obj.positionIversee.getHeight()*5/4);
            //Gdx.app.log("НУ заработало",String.valueOf(track));



            if (Gdx.input.justTouched()) obj.jeuxStatus  = 1;




        } else if (obj.jeuxStatus == 2) {//arret de jeux


            obj.batch.draw(obj.gameover, Gdx.graphics.getWidth() / 2 - obj.gameover.getWidth() / 2, Gdx.graphics.getHeight() / 2 - obj.gameover.getHeight() / 2);
            obj.batch.draw(obj.endBordScreen,Gdx.graphics.getWidth()/2-obj.gameover.getWidth()/2-Gdx.graphics.getWidth()/7,Gdx.graphics.getHeight()/2-obj.endBordScreen.getHeight(),Gdx.graphics.getWidth()-Gdx.graphics.getWidth()/4,Gdx.graphics.getHeight()/4);
            //le affichage des medalle
            if(obj.score>30) obj.batch.draw(obj.medals[3],Gdx.graphics.getWidth()/2-obj.gameover.getWidth()/2-Gdx.graphics.getWidth()/7,Gdx.graphics.getHeight()/2-obj.endBordScreen.getHeight(),Gdx.graphics.getWidth()-Gdx.graphics.getWidth()/4,Gdx.graphics.getHeight()/4);
            else if(obj.score>20) obj.batch.draw(obj.medals[2],Gdx.graphics.getWidth()/2-obj.gameover.getWidth()/2-Gdx.graphics.getWidth()/7,Gdx.graphics.getHeight()/2-obj.endBordScreen.getHeight(),Gdx.graphics.getWidth()-Gdx.graphics.getWidth()/4,Gdx.graphics.getHeight()/4);
            else if(obj.score>10) obj.batch.draw(obj.medals[1],Gdx.graphics.getWidth()/2-obj.gameover.getWidth()/2-Gdx.graphics.getWidth()/7,Gdx.graphics.getHeight()/2-obj.endBordScreen.getHeight(),Gdx.graphics.getWidth()-Gdx.graphics.getWidth()/4,Gdx.graphics.getHeight()/4);
            else obj.batch.draw(obj.medals[0],Gdx.graphics.getWidth()/2-obj.gameover.getWidth()/2-Gdx.graphics.getWidth()/7,Gdx.graphics.getHeight()/2-obj.endBordScreen.getHeight(),Gdx.graphics.getWidth()-Gdx.graphics.getWidth()/4,Gdx.graphics.getHeight()/4);
            obj.font.draw(obj.batch, String.valueOf(obj.score), Gdx.graphics.getWidth()/2+Gdx.graphics.getWidth()/3-110, Gdx.graphics.getHeight()/2+240);

            obj.bestScore.draw(obj.batch, String.valueOf(obj.bestValue), Gdx.graphics.getWidth()/2+Gdx.graphics.getWidth()/3-110, Gdx.graphics.getHeight()/2+50);

            if (Gdx.input.justTouched()) {//just touched est la sourie appuyes

                obj.jeuxStatus = 1;
                obj.nosVariableAppele.startGame();
                obj.score = 0;
                obj.scoringTube = 0;
                obj.velocity = 0;


            }

        }

        if (obj.flapState == 0) {//chandement de la Texture graphiaue pour aue les  ailes bouges ,

            obj.flapState = 1;
        } else {
            obj.flapState = 0;
        }



        //batch.draw(oiseauxs [flapState], Gdx.graphics.getWidth() / 2 - oiseauxs [flapState].getWidth() / 2, birdY);



        obj.birdCircle.set(Gdx.graphics.getWidth() / 2, obj.birdY + obj.oiseauxs [obj.flapState].getHeight() / 2, obj.oiseauxs [obj.flapState].getWidth() / 2);//le boxHit de oiseaux





        //shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);//des visualisation des hitBOX
        //shapeRenderer.setColor(Color.RED);
        //shapeRenderer.rect((Gdx.graphics.getWidth() / 2)-name.getWidth() * 2 /1 /2-20+positionNormale.getWidth()*5/4,Gdx.graphics.getHeight()/4,positionNormale.getWidth()*5/4,positionNormale.getHeight());;
        //shapeRenderer.circle(birdCircle.x, birdCircle.y, birdCircle.radius);

        for (int i = 0; i < obj.numeroDesTubes; i++) {//le senseur

            //shapeRenderer.rect(tubeX[i], Gdx.graphics.getHeight() / 2 + gap / 2 + tubeOffset[i], topTube.getWidth(), topTube.getHeight());
            //shapeRenderer.rect(tubeX[i], Gdx.graphics.getHeight() / 2 - gap / 2 - bottomTube.getHeight() + tubeOffset[i], bottomTube.getWidth(), bottomTube.getHeight());


            if (Intersector.overlaps(obj.birdCircle, obj.topTubeRectangles[i]) || Intersector.overlaps(obj.birdCircle, obj.bottomTubeRectangles[i])) {//attrape le fait quand l'oiseaux a touche la tube

                obj.jeuxStatus  = 2;

            }

        }



        //shapeRenderer.end();



    }
    public void versionInversee(){

        int  x= Gdx.input.getX();
        int y = Gdx.input.getY();


        if((x>571 &&x<768)&&(y>1475)&&(y<1582) && Gdx.input.justTouched()){
            Gdx.app.log("Score", "CONDITION IF POPALO VNUTR");


            obj.batch.draw(obj.positionInversefonsee,(Gdx.graphics.getWidth() / 2)-obj.name.getWidth() * 2 /1 /2-20+obj.positionNormale.getWidth()*5/4,Gdx.graphics.getHeight()/4,obj.positionNormale.getWidth()*5/4,obj.positionIversee.getHeight()*5/4);

            obj.batch.draw(obj.positionInversefonsee,(Gdx.graphics.getWidth() / 2)-obj.name.getWidth() * 2 /1 /2-20+obj.positionNormale.getWidth()*5/4,Gdx.graphics.getHeight()/4,obj.positionNormale.getWidth()*5/4,obj.positionIversee.getHeight()*5/4);
            Gdx.app.log("x=",String.valueOf(x));
            Gdx.app.log("y=",String.valueOf(y));


            versionInverseCode();


        }


    }


}
//posibilite a faire publie le jeux sur Play Market pour povoir y accedex sur telephone et le tellecharge
//Problemes,Les telehone avec un petit ecran sont pas adapte