package com.example.bullettime;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

public class GameEngine extends SurfaceView implements Runnable {
    private final String TAG = "VECTOR-MATH";

    // game thread variables
    private Thread gameThread = null;
    private volatile boolean gameIsRunning;

    // drawing variables
    private Canvas canvas;
    private Paint paintbrush;
    private SurfaceHolder holder;

    // Screen resolution varaibles
    private int screenWidth;
    private int screenHeight;

    // SPRITES
    Square bullet;
    Square enemy;

    int SQUARE_WIDTH = 100;


    // GAME STATS
    int score = 0;


    public GameEngine(Context context, int screenW, int screenH) {
        super(context);

        // intialize the drawing variables
        this.holder = this.getHolder();
        this.paintbrush = new Paint();

        // set screen height and width
        this.screenWidth = screenW;
        this.screenHeight = screenH;

        // initalize sprites
        this.bullet = new Square(context, 100, 600, SQUARE_WIDTH);
        this.enemy = new Square(context, 1000, 100, SQUARE_WIDTH);

    }

    @Override
    public void run() {
        // @TODO: Put game loop in here
        while (gameIsRunning == true) {
            updateGame();    // updating positions of stuff
            redrawSprites(); // drawing the stuff
            controlFPS();
        }
    }


    boolean enemyIsMovingDown = true;

    // Game Loop methods
    public void updateGame() {

        Log.d(TAG,"Bullet position: " + this.bullet.getxPosition() + ", " + this.bullet.getyPosition());
        Log.d(TAG,"Enemy position: " + this.enemy.getxPosition() + ", " + this.enemy.getyPosition());

        // make enemy move up & down

        if (enemyIsMovingDown == true) {
            this.enemy.setyPosition(this.enemy.getyPosition() + 30);
        }
        else {
            this.enemy.setyPosition(this.enemy.getyPosition() - 30);
        }

        // update the enemy hitbox
        this.enemy.updateHitbox();


        // do collision detection
        // -----------------------
        // R1. colliding with bottom of screen
        if (this.enemy.getyPosition() >= this.screenHeight-400) {
            enemyIsMovingDown = false;
        }
        // R2. colliding with top of screen
        if (this.enemy.getyPosition() < 120 ) {
            enemyIsMovingDown = true;
        }



        // MAKE BULLET MOVE

        // 1. calculate distance between bullet and enemy
        double a = this.enemy.getxPosition() - this.bullet.getxPosition();
        double b = this.enemy.getyPosition() - this.bullet.getyPosition();

        // d = sqrt(a^2 + b^2)

        double d = Math.sqrt((a * a) + (b * b));

        Log.d(TAG, "Distance to enemy: " + d);

        // 2. calculate xn and yn constants
        // (amount of x to move, amount of y to move)
        double xn = (a / d);
        double yn = (b / d);

        // 3. calculate new (x,y) coordinates
        int newX = this.bullet.getxPosition() + (int) (xn * 15);
        int newY = this.bullet.getyPosition() + (int) (yn * 15);
        this.bullet.setxPosition(newX);
        this.bullet.setyPosition(newY);

        // 4. update the bullet hitbox position
        this.bullet.updateHitbox();


        // COLLISION DETECTION FOR BULLET
        // -----------------------------
        // R1: When bullet intersects the enemy, restart bullet position
        if (bullet.getHitbox().intersect(enemy.getHitbox())) {

            // UPDATE THE SCORE
            this.score = this.score + 1;

            // RESTART THE BULLET FROM INITIAL POSITION
            this.bullet.setxPosition(100);
            this.bullet.setyPosition(600);

            // RESTART THE HITBOX
            this.bullet.updateHitbox();
        }



        Log.d(TAG,"----------");
    }

    public void redrawSprites() {
        if (holder.getSurface().isValid()) {

            // initialize the canvas
            canvas = holder.lockCanvas();
            // --------------------------------
            // @TODO: put your drawing code in this section

            // set the game's background color
            canvas.drawColor(Color.argb(255,255,255,255));

            // setup stroke style and width
            paintbrush.setStyle(Paint.Style.FILL);
            paintbrush.setStrokeWidth(8);

            // draw bullet
            paintbrush.setColor(Color.BLACK);
            canvas.drawRect(
                    this.bullet.getxPosition(),
                    this.bullet.getyPosition(),
                    this.bullet.getxPosition() + this.bullet.getWidth(),
                    this.bullet.getyPosition() + this.bullet.getWidth(),
                    paintbrush
            );

            // draw the bullet hitbox
            paintbrush.setColor(Color.RED);
            paintbrush.setStyle(Paint.Style.STROKE);
            canvas.drawRect(
                    this.bullet.getHitbox(),
                    paintbrush
            );


            // draw enemy
            paintbrush.setColor(Color.YELLOW);
            paintbrush.setStyle(Paint.Style.FILL);
            canvas.drawRect(
                    this.enemy.getxPosition(),
                    this.enemy.getyPosition(),
                    this.enemy.getxPosition() + this.enemy.getWidth(),
                    this.enemy.getyPosition() + this.enemy.getWidth(),
                    paintbrush
            );

            // draw the enemy hitbox
            paintbrush.setColor(Color.BLUE);
            paintbrush.setStyle(Paint.Style.STROKE);
            canvas.drawRect(
                    this.enemy.getHitbox(),
                    paintbrush
            );



            // draw the score
            paintbrush.setTextSize(100);
            paintbrush.setStrokeWidth(5);
            canvas.drawText("Score: " + this.score, 10, 100, paintbrush);

            // --------------------------------
            holder.unlockCanvasAndPost(canvas);
        }

    }

    public void controlFPS() {
        try {
            gameThread.sleep(17);
        }
        catch (InterruptedException e) {

        }
    }


    // Deal with user input


    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction() & MotionEvent.ACTION_MASK) {
            case MotionEvent.ACTION_UP:

                break;
            case MotionEvent.ACTION_DOWN:

                break;
        }
        return true;
    }

    // Game status - pause & resume
    public void pauseGame() {
        gameIsRunning = false;
        try {
            gameThread.join();
        }
        catch (InterruptedException e) {

        }
    }
    public void  resumeGame() {
        gameIsRunning = true;
        gameThread = new Thread(this);
        gameThread.start();
    }

}

