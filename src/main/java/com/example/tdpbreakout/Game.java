package com.example.tdpbreakout;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;
import android.view.MotionEvent;

import java.util.LinkedList;
import java.util.Random;


/*
    @Class Game, lógica del juego
 */
public class Game {

    protected GameView myGui;
    protected int score;
    protected int lives;
    protected boolean gameOver;
    protected Paddle paddle;
    protected float oldPaddleX, oldX; //Usados por la paleta para calcular posiciones
    protected Ball ball;
    protected Velocity velocity = new Velocity(15, 15); //Usado para la velocidad de la bola
    protected Brick[] bricks = new Brick[66];
    protected LinkedList<PowerUp> pupList = new LinkedList<PowerUp>();
    protected ActivableEntity barrier;
    protected int numBricks = 0;
    protected int brokenBricks;
    protected Random rng;

    public Game(GameView gui){
        myGui = gui;
        lives = 3;
        score = 0;
        gameOver = false;
        barrier = new BarrierObj(null);
        rng = new Random();
    }

    //Suma la cantidad de puntos según la cantidad pasada por parametro
    public void addPoints(int pts){
       score+= pts;
    }

    //Incrementa en 1 la cantidad de vidas
    public void incrementLives() {
        if (lives<9)lives++;
    }

    //Reduce en 1 la cantidad de vidas
    public void decrementLives(){
        lives--;
    }

    //Retorna la puntuación del jugador
    public int getScore(){
        return score;
    }

    //Retorna la cantidad de vidas del jugador
    public int getLives(){
        return lives;
    }

    //Retorna si el juego acabó o no
    public boolean gameOver(){
        return gameOver;
    }


    //Realiza el movimiento de la paleta, recibe un evento capturado por la GUI como parametro
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

    //Crea la paleta a controlar, recibe un sprite como parametro
    public void createPaddle(Bitmap sprite) {
        this.paddle = new Paddle(sprite);
        paddle.setX(myGui.getScrWidth()/2-paddle.getWidth()/2);
        paddle.setY((myGui.getScrHeight()*4)/5);
    }

    //Retorna la paleta
    public Entity getPaddle() {
        return paddle;
    }

    //Creará la bola del juego, recibe un sprite como parametro
    public void createBall(Bitmap sprite){
        this.ball = new Ball(sprite);
        ball.setX(rng.nextInt(myGui.getScrWidth()-50));
        ball.setY(myGui.getScrHeight()/3);
    }

    //Retorna la bola
    public Entity getBall() {
        return ball;
    }

    //Creará un arreglo de ladrillos, recibe un sprite por parametro
    public void createBricks(LinkedList<Bitmap> sprites) {
        for (int col=0; col<11; col++){
            for (int row=0; row<6; row++){
                bricks[numBricks] = new Brick(sprites.get(row));
                bricks[numBricks].setX(col);
                bricks[numBricks].setY(row);
                numBricks++;
            }
        }
    }

    //Activa el efecto de acelerar la bola
    public void speedUp(){
        if(velocity.getY()<16 && velocity.getY()>-15){ //La velocidad de la bola no debe ser mayor a su tamaño
            if (velocity.getY()<0) velocity.setY(velocity.getY()-5);
            else velocity.setY(velocity.getY()+5);
        }

    }

    //Retorna un Brick en la posición pasada por parametro
    public ActivableEntity getBrick(int pos){
        return bricks[pos];
    }


    //Creará un PowerUp y recibirá una lista de sprites para poder utilizar
    //El tipo del PowerUp se definirá por un número generado aleatoriamente
    //está limitado a 10 generaciones de poderes, la lista contendrá todos los poderes creados
    //sean o no visibles
    //Devuelve un booleano verdadero si se ha creado el powerup falso en caso contrario
    public boolean createPowerup(LinkedList<Bitmap> sprBank){
        boolean created = false;
        if (pupList.size() < 10) {
            PowerUp pUp;
            int probability = rng.nextInt(100);
            Log.d("MESSI", ""+probability);
            if (probability <= 25) pUp = new Live(sprBank.get(0));
            else if (probability <= 70) pUp = new Speed(sprBank.get(1));
            else pUp = new Barrier(sprBank.get(2));
            pupList.add(pUp);
            created=true;
        }
        return created;
    }

    //Si no existe barrera, la creará, si existe pero está desactivada, la activará
    public void setBarrier(){
        barrier.setVisible();
    }

    //Chequea la visibilidad de la bola y devuelve un booleano
    public boolean isBarrierVisible(){
        return barrier.isVisible();
    }

    //Retorna la lista de PowerUps
    public LinkedList<PowerUp> getPupList(){return pupList;}

    //Devolverá la cantidad de ladrillos en pantalla
    public int cantBricks(){
        return numBricks;
    }

    //Ejecuta las funciones de movimiento de la bola cada vez que el metodo es llamado
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
            if (barrier.isVisible()){ //Si hay barrera, rebota la bola
                barrier.setInvisible();
                velocity.setY(velocity.getY()*-1);
                myGui.playSnd(2);
                score+=100;
            }
            else{ //Si no hay barrera, el jugador pierde una vida
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

                    //Según la probabilidad, generará un PowerUp al romper un ladrillo
                    int chance = rng.nextInt(100);
                    if (chance<26) if (createPowerup(myGui.getPUpList())) setPUpPosition();
                    if (brokenBricks==66){
                        gameOver=true;
                    }

                }
            }
        }
        if (brokenBricks==numBricks) gameOver=true;
    }

    //Ejecuta la caida del PowerUp
    public void powerUpMovement(){
        for (PowerUp p:pupList) {
            if (paddle.collides(p) && p.isVisible()){
                p.setInvisible();
                score+=500;
                setEffect(p.giveEffect());
            }
            if (p.getY()<=myGui.getScrHeight()) p.fallDown();
            else p.setInvisible();
        }
    }

    private void setEffect(String effect) {
        if(effect.equals("1up")) incrementLives();
        if(effect.equals("speed")) speedUp();
        if(effect.equals("barrier")) setBarrier();
    }

    //Devuelve un angulo (velocity) aleatorio
    private int xVelocity() {
        int[] values = {-15, -10, -5, 5, 10, 15};
        int i = rng.nextInt(6);
        return values[i];
    }

    //Genera el PowerUp con sus coordenadas
    private void setPUpPosition(){
        PowerUp p = pupList.getLast();
        p.setVisible();
        p.setX(ball.getX());
        p.setY(ball.getY());
    }

}
