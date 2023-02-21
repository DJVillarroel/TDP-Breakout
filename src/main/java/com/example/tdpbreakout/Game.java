package com.example.tdpbreakout;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.MotionEvent;
import java.util.Random;

public class Game {

    protected GameView myGui;
    protected int score;
    protected int lives;
    protected boolean gameOver;
    protected Paddle paddle;
    protected float oldPaddleX, oldX; //Usados por la paleta para calcular posiciones
    protected Ball ball;
    protected Velocity velocity = new Velocity(15, 20); //Usado para la velocidad de la bola
    protected Brick[] bricks = new Brick[66];
    protected int numBricks = 0;
    protected int brokenBricks;
    protected Random rng;

    public Game(GameView gui){
        myGui = gui;
        lives = 3;
        score = 0;
        gameOver = false;
        rng = new Random();
    }

    public void addPoints(int pts){
       score+= pts;
    }

    public void incrementLives() {
        lives++;
    }

    public void decrementLives(){
        lives--;
    }
    public int getScore(){
        return score;
    }

    public int getLives(){
        return lives;
    }
    public boolean gameOver(){
        return gameOver;
    }


    //Realiza el movimiento de la paleta, recibe un evento capturado por la GUI
    public void movePaddle(MotionEvent event){
        float touchX = event.getX();
        float touchY = event.getY();
        if (touchY >= paddle.getY()){ //Tocar debajo de la paleta nos permite moverla
            int action = event.getAction();
            if (action == MotionEvent.ACTION_DOWN){
                oldX = event.getX();
                oldPaddleX = paddle.getX();
            }
            if (action == MotionEvent.ACTION_MOVE){
                float shift = oldX - touchX;
                float newPaddleX = oldPaddleX - shift;
                if (newPaddleX <= 0) paddle.setX(0);
                else if (newPaddleX >= myGui.getScrWidth() - paddle.getWidth())
                    paddle.setX(myGui.getScrWidth() - paddle.getWidth());
                else paddle.setX(newPaddleX);
            }
        }
    }

    public void createPaddle(Bitmap sprite) {
        this.paddle = new Paddle(sprite);
        paddle.setX(myGui.getScrWidth()/2-paddle.getWidth()/2);
        paddle.setY((myGui.getScrHeight()*4)/5);
    }

    public Entity getPaddle() {
        return paddle;
    }

    public void createBall(Bitmap sprite){
        this.ball = new Ball(sprite);
        ball.setX(rng.nextInt(myGui.getScrWidth()-50));
        ball.setY(myGui.getScrHeight()/3);
    }

    public Entity getBall() {
        return ball;
    }

    public void createBricks(Bitmap sprite) {
        for (int col=0; col<11; col++){
            for (int row=0; row<6; row++){
                bricks[numBricks] = new Brick(sprite);
                bricks[numBricks].setX(col);
                bricks[numBricks].setY(row);
                numBricks++;
            }
        }
    }

    public ActivableEntity getBrick(int pos){
        return bricks[pos];
    }

    /*
    public PowerUp createPowerup(){
        PowerUp pUp;
        int probability = rng.nextInt(100);
        if (probability<=33) pUp = new Live(BitmapFactory.decodeResource(getResources(), R.drawable.lives));
        if (probability<=66) pUp = new Live(BitmapFactory.decodeResource(getResources(), R.drawable.slow));
        else pUp = new Live(BitmapFactory.decodeResource(getResources(), R.drawable.barrier));
        return pUp;
    }
    */
    public int cantBricks(){
        return numBricks;
    }
    public void ballMovement(){
        //Dar la posición inicial de la bola
        ball.setX(ball.getX()+velocity.getX());
        ball.setY(ball.getY()+velocity.getY());
        //Si la bola colisiona con los muros, corregirá su dirección
        if (ball.collidesWall(myGui.getScrWidth())){
            velocity.setX(velocity.getX()*-1);
            myGui.playSnd(3);
        }
        //Lo mismo que el chequeo anterior con el techo
        if (ball.collidesCeiling()){
            velocity.setY(velocity.getY()*-1);
            myGui.playSnd(3);
        }
        //Si la bola se escapa por debajo de la pantalla, reaparecerá con una dirección aleatoria
        if (ball.missesPaddle(paddle)) {
            ball.setX(1 + rng.nextInt(myGui.getScrWidth() - ball.getWidth() - 1));
            ball.setY(myGui.getScrHeight() / 3);
            velocity.setX(xVelocity());
            velocity.setY(20);
            lives--;
            myGui.playSnd(0);
            if (getLives() == 0) {
                gameOver=true;
            }
        }
        //Si la bola colisiona con la paleta, rebota
        if (ball.collides(paddle)){
            velocity.setY(velocity.getY()*-1);
            myGui.playSnd(2);
        }
        //Colisión de la bola con los bloques
        for (int i=0; i<numBricks; i++) {
            if (bricks[i].isVisible()) {
                if(ball.collidesBrick(bricks[i])){
                    velocity.setY((velocity.getY())*-1);
                    bricks[i].setInvisible();
                    myGui.playSnd(1);
                    addPoints(100);
                    brokenBricks++;
                    /*
                    if (power == null || !power.isVisible()){
                        power = createPowerup();
                        power.setX(ball.getX());
                        power.setY(ball.getY());
                        power.setVisibility();
                    }
                    */

                    //MODIFICAR PARA CUANDO HAYAN DISTINTOS NIVELES!!!
                    if (brokenBricks==66){
                        gameOver=true;
                    }

                }
            }
        }
        if (brokenBricks==numBricks) gameOver=true;
    }
    //Devuelve un angulo (velocity) aleatorio
    private int xVelocity() {
        int[] values = {-15, -10, -5, 5, 10, 15};
        int i = rng.nextInt(6);
        return values[i];
    }
}
