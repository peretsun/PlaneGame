package com.example.planegame.view;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import com.example.planegame.R;
import com.example.planegame.control.activitys.MainActivity;
import com.example.planegame.control.dialogs.MyDialog;
import com.example.planegame.model.constants.Constants;
import com.example.planegame.model.game.Award;
import com.example.planegame.model.game.Bullet;
import com.example.planegame.model.game.Enemy;
import com.example.planegame.model.game.Explosion;
import com.example.planegame.model.game.MyPlane;
import com.example.planegame.model.thread.GenerateTargetThread;

public class GameSurfaceView extends SurfaceView implements SurfaceHolder.Callback{
	private MainActivity activity;
	private Paint paint;
	private Bitmap[] bgBitmap = new Bitmap[2];
	
	private int myPlaneLife;
	public MyPlane myPlane;
	public Enemy currentBoss;
	public List<Enemy> allEnemy;
	public List<Explosion> allExplosion;
	public List<Bullet> allBullet;
	public List<Award> allAward;
	
	public Bitmap[] enemyExplosion;
	//public Bitmap[] myPlaneExplosion;
	public Bitmap[] bossExplosion;
	
	private Thread drawThread;
	private Thread shootThread;
	private Thread generateTargetThread;
	
	private int screenWidth;
	private int screenHeight;
	private int bg1CurrentFrame;
	private int bg2CurrentFrame;
	
	public int currentScore;
	public boolean isPlay;
	public boolean isGameOver;
	private boolean isTouchMyPlane = false;

	public GameSurfaceView(MainActivity activity) {
		super(activity);
		this.activity = activity;
		paint = new Paint();
		paint.setAntiAlias(true);
		
		this.requestFocus();
		this.setFocusableInTouchMode(true);
		getHolder().addCallback(this);
	}
	
	public void initDate(){
		allAward = new ArrayList<Award>();
		allBullet = new ArrayList<Bullet>();
		allEnemy = new ArrayList<Enemy>();
		allExplosion = new ArrayList<Explosion>();
		
		screenWidth = getWidth();
		screenHeight = getHeight();
		bg1CurrentFrame = 0;
		bg2CurrentFrame = -screenHeight;
		
		myPlaneLife = 3;
		isGameOver = false;
		isPlay = true;
	}
	
	public void initBitmap(int bgMap){
		try {
			Field field = R.drawable.class.getDeclaredField("bg_map"+bgMap);
			int resourceId = Integer.parseInt(field.get(null).toString());
			bgBitmap[0] = BitmapFactory.decodeResource(activity.getResources(), resourceId);
			bgBitmap[0] = Bitmap.createScaledBitmap(bgBitmap[0], screenWidth, screenHeight, false);
			bgBitmap[1] = bgBitmap[0];
		} catch (NoSuchFieldException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NumberFormatException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		enemyExplosion = new Bitmap[6];
		enemyExplosion[0] = BitmapFactory.decodeResource(activity.getResources(), R.drawable.enemy_explode0);
		enemyExplosion[1] = BitmapFactory.decodeResource(activity.getResources(), R.drawable.enemy_explode1);
		enemyExplosion[2] = BitmapFactory.decodeResource(activity.getResources(), R.drawable.enemy_explode2);
		enemyExplosion[3] = BitmapFactory.decodeResource(activity.getResources(), R.drawable.enemy_explode3);
		enemyExplosion[4] = BitmapFactory.decodeResource(activity.getResources(), R.drawable.enemy_explode4);
		enemyExplosion[5] = BitmapFactory.decodeResource(activity.getResources(), R.drawable.enemy_explode5);
		
		bossExplosion = new Bitmap[11];
		bossExplosion[0]  = BitmapFactory.decodeResource(activity.getResources(), R.drawable.boss_explode0);
		bossExplosion[1]  = BitmapFactory.decodeResource(activity.getResources(), R.drawable.boss_explode1);
		bossExplosion[2]  = BitmapFactory.decodeResource(activity.getResources(), R.drawable.boss_explode2);
		bossExplosion[3]  = BitmapFactory.decodeResource(activity.getResources(), R.drawable.boss_explode3);
		bossExplosion[4]  = BitmapFactory.decodeResource(activity.getResources(), R.drawable.boss_explode4);
		bossExplosion[5]  = BitmapFactory.decodeResource(activity.getResources(), R.drawable.boss_explode5);
		bossExplosion[6]  = BitmapFactory.decodeResource(activity.getResources(), R.drawable.boss_explode6);
		bossExplosion[7]  = BitmapFactory.decodeResource(activity.getResources(), R.drawable.boss_explode7);
		bossExplosion[8]  = BitmapFactory.decodeResource(activity.getResources(), R.drawable.boss_explode8);
		bossExplosion[9]  = BitmapFactory.decodeResource(activity.getResources(), R.drawable.boss_explode9);
		bossExplosion[10]  = BitmapFactory.decodeResource(activity.getResources(), R.drawable.boss_explode10);
		
	}
	
	public void initMyPlane(int planeNumber){
		Bitmap b = null;
		switch(planeNumber){
		case Constants.PlaneNumber.PLANE_NUMBER_0:
			b = BitmapFactory.decodeResource(activity.getResources(), R.drawable.player0);
			myPlane = new MyPlane(this, b, 100, 10, 10);
			break;
		case Constants.PlaneNumber.PLANE_NUMBER_1:
			b = BitmapFactory.decodeResource(activity.getResources(), R.drawable.player1);
			myPlane = new MyPlane(this, b, 120, 20, 20);
			break;
		case Constants.PlaneNumber.PLANE_NUMBER_2:
			b = BitmapFactory.decodeResource(activity.getResources(), R.drawable.player2);
			myPlane = new MyPlane(this, b, 140, 30, 30);
			break;
		case Constants.PlaneNumber.PLANE_NUMBER_3:
			b = BitmapFactory.decodeResource(activity.getResources(), R.drawable.player3);
			myPlane = new MyPlane(this, b, 160, 40, 40);
			break;
		case Constants.PlaneNumber.PLANE_NUMBER_4:
			b = BitmapFactory.decodeResource(activity.getResources(), R.drawable.player4);
			myPlane = new MyPlane(this, b, 180, 50, 50);
			break;
		case Constants.PlaneNumber.PLANE_NUMBER_5:
			b = BitmapFactory.decodeResource(activity.getResources(), R.drawable.player5);
			myPlane = new MyPlane(this, b, 200, 60, 60);
			break;
		case Constants.PlaneNumber.PLANE_NUMBER_6:
			b = BitmapFactory.decodeResource(activity.getResources(), R.drawable.player6);
			myPlane = new MyPlane(this, b, 220, 70, 70);
			break;
		case Constants.PlaneNumber.PLANE_NUMBER_7:
			b = BitmapFactory.decodeResource(activity.getResources(), R.drawable.player7);
			myPlane = new MyPlane(this, b, 240, 80, 80);
			break;
		case Constants.PlaneNumber.PLANE_NUMBER_8:
			b = BitmapFactory.decodeResource(activity.getResources(), R.drawable.player8);
			myPlane = new MyPlane(this, b, 260, 90, 90);
			break;
		case Constants.PlaneNumber.PLANE_NUMBER_9:
			b = BitmapFactory.decodeResource(activity.getResources(), R.drawable.player9);
			myPlane = new MyPlane(this, b, 280, 100, 100);
			break;
		case Constants.PlaneNumber.PLANE_NUMBER_10:
			b = BitmapFactory.decodeResource(activity.getResources(), R.drawable.player10);
			myPlane = new MyPlane(this, b, 300, 120, 120);
			break;
			
		}
		myPlaneLife--;
	}
	
	public void onDrawSelf(Canvas canvas) {
		try{
			canvas.save();
			canvas.drawBitmap(bgBitmap[0], 0, bg1CurrentFrame, paint);
			canvas.drawBitmap(bgBitmap[1], 0, bg2CurrentFrame, paint);
			paint.setTextSize(14);
			paint.setColor(Color.CYAN);
			paint.setTextAlign(Paint.Align.CENTER);
			canvas.drawText("关卡:"+ activity.currentMap+1, screenWidth/5, 20, paint);
			canvas.drawText("得分:"+currentScore, screenWidth*4/5, 20, paint);
			myPlane.drawSelf(canvas, paint);
			
			List<Enemy> allEnemy1 = new ArrayList<Enemy>(allEnemy);
			for(Enemy e:allEnemy1){
				if(isGameOver){
					return;
				}
				if(e.iscollsion()){
					Explosion explosion = new Explosion(this,enemyExplosion, myPlane.getNowX(), myPlane.getNowY());
					allExplosion.add(explosion);
					gameOver();
					return;
				}
				e.drawSelf(canvas, paint);
			}
			List<Bullet> allBullet1 = new ArrayList<Bullet>(allBullet);
			for(Bullet b:allBullet1){
				if(isGameOver){
					return;
				}
				b.drawSelf(canvas, paint);
			}
			List<Explosion> allExplosion1 = new ArrayList<Explosion>(allExplosion);
			for(Explosion e:allExplosion1){
				if(isGameOver){
					return;
				}
				e.drawSelf(canvas, paint);
			}
			List<Award> allAward1 = new ArrayList<Award>(allAward);
			for(Award a:allAward1){
				if(isGameOver){
					return;
				}
				a.drawSelf(canvas, paint);
			}
			canvas.restore();
			bg1CurrentFrame += 5;
			bg2CurrentFrame += 5;
			if(bg1CurrentFrame >= screenHeight){
				bg1CurrentFrame = -screenHeight;
			}
			if(bg2CurrentFrame >= screenHeight){
				bg2CurrentFrame = -screenHeight;
			}
		}catch(Exception e){
			e.printStackTrace();
		}
	}

	@Override
	public void surfaceCreated(SurfaceHolder holder) {
		initDate();
		initBitmap(activity.currentMap);
		initMyPlane(activity.currentPlaneNumber);
		currentBoss = new Enemy(this,activity.currentMap + 10,activity.currentMap);

		drawThread = new Thread() {
			@Override
			public void run() {
				while(!isGameOver){
					if(isPlay){
						SurfaceHolder myholder = GameSurfaceView.this.getHolder();
						Canvas canvas = myholder.lockCanvas();// 获取画布
						try {
							synchronized (myholder) {
								GameSurfaceView.this.onDrawSelf(canvas);// 绘制
							}
						} catch (Exception e) {
							e.printStackTrace();
						} finally {
							if (canvas != null) {
								myholder.unlockCanvasAndPost(canvas);
							}
						}
						try {
							Thread.sleep(100);// 睡眠指定毫秒数
						} catch (Exception e) {
							e.printStackTrace();// 打印堆栈信息
						}
		
					}
				}
			}

		};
		
		shootThread = new Thread() {
			private int time = 0;
			@Override
			public void run() {
				while(!isGameOver){
					if(isPlay){
						myPlane.shoot();
						try {
							Thread.sleep(500);// 睡眠指定毫秒数
						} catch (Exception e) {
							e.printStackTrace();// 打印堆栈信息
						}
					}
				}
			}

		};
		drawThread.start();
		shootThread.start();
		generateTargetThread = new GenerateTargetThread(this,activity.currentMap);
		generateTargetThread.start();
		
		
	}
	
	public void puase(){
		isPlay = false;//设置游戏标志
	}
	
	public void gameOver(){
		isGameOver = true;//设置游戏结束标志，停止所有线程
		activity.currentScore = currentScore;
		activity.time = ((GenerateTargetThread) generateTargetThread).getCurrentTime();
		if(currentBoss == null){
			activity.sendMessage(Constants.WhatMessage.GOTO_WIN_VIEW);
		}else{
			activity.sendMessage(Constants.WhatMessage.GOTO_FAIL_VIEW);
		}
	}
	

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		int x = (int) event.getX();
		int y = (int) event.getY();
		
		switch(event.getAction()){
		case MotionEvent.ACTION_DOWN:
			//判断飞机是否被按下
			if(myPlane != null&&myPlane.isTouch(x, y)){
				isTouchMyPlane = true;
			}
			break;
		case MotionEvent.ACTION_MOVE:
			if(isTouchMyPlane){
				myPlane.move(x, y);
			}
			break;
		case MotionEvent.ACTION_UP:
			isTouchMyPlane = false;
			break;
		}
		return true;
	}
	
	@Override
	public void surfaceChanged(SurfaceHolder holder, int format, int width,
			int height) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void surfaceDestroyed(SurfaceHolder holder) {
		isGameOver = true;
		
	}

	public int getScreenWidth() {
		return screenWidth;
	}

	public int getScreenHeight() {
		return screenHeight;
	}

	public MainActivity getActivity() {
		return activity;
	}

}
