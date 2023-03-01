package com.example.tdpbreakout;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.Canvas;
import android.media.MediaPlayer;
import android.view.View;
import android.view.Display;
import android.view.MotionEvent;
import android.os.Handler;
import java.util.LinkedList;

/*
    @Class GameView, interfaz gráfica del juego, aquí aparecerán en pantalla nuestros elementos de juego
 */
public class GameView extends View{
    protected Game game;
    protected Context context;
    protected Handler handler;
    protected final long UPDATE_MILLIS=16;
    protected Runnable runnable;
    protected Paint liveText  = new Paint();
    protected Paint scoreText = new Paint();
    protected int TEXT_SIZE = 50;
    protected int scrWidth, scrHeight;
    protected LinkedList<Bitmap> brickImgBank = new LinkedList<Bitmap>();
    protected LinkedList<MediaPlayer> sndBank = new LinkedList<MediaPlayer>();
    protected LinkedList<Bitmap> powerUpImgBank = new LinkedList<Bitmap>();

    protected Paint barrier = new Paint();


    public GameView(Context context){
        super(context);
        this.context = context;
        Display display = ((Activity) getContext()).getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        scrWidth = size.x;
        scrHeight = size.y;
        scoreText.setColor(Color.WHITE);
        scoreText.setTextSize(TEXT_SIZE);
        liveText.setColor(Color.WHITE);
        liveText.setTextSize(TEXT_SIZE);
        game = new Game(this);
        game.createPaddle(BitmapFactory.decodeResource(getResources(), R.drawable.paddle));
        game.createBall(BitmapFactory.decodeResource(getResources(), R.drawable.ball));
        setBrickBank(BitmapFactory.decodeResource(getResources(), R.drawable.bricks));
        setBrickBank(BitmapFactory.decodeResource(getResources(), R.drawable.brickg));
        setBrickBank(BitmapFactory.decodeResource(getResources(), R.drawable.bricko));
        setBrickBank(BitmapFactory.decodeResource(getResources(), R.drawable.brickp));
        setBrickBank(BitmapFactory.decodeResource(getResources(), R.drawable.brickr));
        setBrickBank(BitmapFactory.decodeResource(getResources(), R.drawable.brickb));
        game.createBricks(brickImgBank);
        setPUpBank(BitmapFactory.decodeResource(getResources(), R.drawable.lives));
        setPUpBank(BitmapFactory.decodeResource(getResources(), R.drawable.slow));
        setPUpBank(BitmapFactory.decodeResource(getResources(), R.drawable.barrier));
        setSndBank(MediaPlayer.create(context, R.raw.sndmiss));
        setSndBank(MediaPlayer.create(context, R.raw.sndhit));
        setSndBank(MediaPlayer.create(context, R.raw.sndpaddle));
        setSndBank(MediaPlayer.create(context, R.raw.sndwall));
        barrier.setColor(Color.YELLOW);
        handler = new Handler();
        runnable = new Runnable() {
            @Override
            public void run() { //Hilo que actualiza el juego en cada frame
                updateGame();
                invalidate();
            }
        };

    }


    @Override
    //Movimiento de la paleta
    public boolean onTouchEvent(MotionEvent event){
        game.movePaddle(event);
        return true;
    }

    //Devuelve el ancho de la pantalla
    public int getScrWidth(){return scrWidth;}

    //Devuelve el alto de la pantalla
    public int getScrHeight(){return scrHeight;}

    //Reproduce un sonido del banco de sonidos
    public void playSnd(int pos){
        if (sndBank.get(pos) !=null) sndBank.get(pos).start();
    }

    //Muestra en pantalla la cantidad de vidas
    public void drawLives(Canvas canvas, String lives){
        canvas.drawText(lives, scrWidth-25,50, liveText);
    }

    //Devuelve una lista de sprites para los powerups
    public LinkedList<Bitmap> getPUpList(){return powerUpImgBank;}

    //Muestra en pantalla la puntuación
    public void drawScore(Canvas canvas, String score){
        canvas.drawText(score, 25,50, scoreText);
    }

    //Actualiza los movimientos y la lógica del juego
    protected void updateGame(){
        game.ballMovement();
        game.powerUpMovement();
    }

    @Override
    //OnDraw actualiza lo dibujado en pantalla en cada frame
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (!game.gameOver()) handler.postDelayed(runnable, UPDATE_MILLIS);
        else gameOver();
        drawScore(canvas, "" + game.getScore());
        drawLives(canvas, "" + game.getLives());
        if (game.isBarrierVisible()){
            canvas.drawRect(0, game.getPaddle().getY(), scrWidth, game.getPaddle().getY() + game.getPaddle().getHeight(), barrier);
        }
        canvas.drawBitmap(game.getBall().getSprite(), game.getBall().getX(), game.getBall().getY(), null);
        canvas.drawBitmap(game.getPaddle().getSprite(), game.getPaddle().getX(), game.getPaddle().getY(), null);
        for (int i=0; i<game.cantBricks(); i++) {
            if (game.getBrick(i).isVisible()) {
                canvas.drawBitmap(game.getBrick(i).getSprite(), game.getBrick(i).getX()*game.getBrick(i).getWidth()+1,
                        game.getBrick(i).getY()*game.getBrick(i).getHeight()+1, null);
            }
            drawScore(canvas, ""+game.getScore());
            drawLives(canvas, ""+game.getLives());
        }
        if (!game.getPupList().isEmpty()){
            for(PowerUp pup:game.getPupList()){
                if (pup.isVisible()) canvas.drawBitmap(pup.getSprite(), pup.getX(), pup.getY(), null);
            }
        }
    }


    //Incluye un sonido recibido por parametro en el banco de sonidos
    private void setSndBank(MediaPlayer snd){
        sndBank.add(snd);
    }

    //Incluye un sprite recibido por parametro en el banco de sprites de powerups
    private void setPUpBank(Bitmap sprite){powerUpImgBank.add(sprite);}

    //Incluye un sprite recibido por parametro en el banco de sprites de ladrillos
    private void setBrickBank(Bitmap sprite){brickImgBank.add(sprite);}

    //Hará una llamada a la escena de gameOver
    private void gameOver() {
        handler.removeCallbacksAndMessages(null);
        Intent intent = new Intent(context, GameOver.class);
        intent.putExtra("points", game.getScore());
        context.startActivity(intent);
        ((Activity) context).finish();
    }
}