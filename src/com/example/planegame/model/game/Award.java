package com.example.planegame.model.game;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.Log;

import com.example.planegame.R;
import com.example.planegame.model.constants.Constants;
import com.example.planegame.model.utils.GameUtils;
import com.example.planegame.view.GameSurfaceView;

public class Award {
	private GameSurfaceView gameView;
	private Bitmap awardBitmap;
	private float nowX;
	private float nowY;
	
	private int awardType;
	private int width;
	private int height;
	private int speed;//移动速度
	
	public Award(GameSurfaceView gameView,float x,float y){
		this.gameView = gameView;
		this.nowX = x;
		this.nowY = y;
		initBitmap();
		width = awardBitmap.getWidth();
		height = awardBitmap.getHeight();
		speed = 5;
	}
	
	private void initBitmap(){
		Random random = new Random();
		int i = random.nextInt(4) + 1;
		awardType = i;
		try {
			Field field = R.drawable.class.getDeclaredField("award"+i);
			try {
				int resourceId = Integer.parseInt( field.get(null).toString() );
				this.awardBitmap = BitmapFactory.decodeResource(gameView.getResources(), resourceId);
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
		} catch (NoSuchFieldException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public boolean isCllosion(){
		return GameUtils.isTwoRectCollsion(
				nowX, nowY, 
				width, height, 
				gameView.myPlane.getNowX(), gameView.myPlane.getNowY(), 
				gameView.myPlane.getWidth(),gameView.myPlane.getHeight());
		
	}
	
	public void move(){
		//是否发生碰撞
		if(isCllosion()){
			if(gameView.getActivity().soundFlag){
				gameView.getActivity().gameSound.playSound(Constants.SoundType.GETAWRAD, 0);
			}
			disapear();
			gameView.currentScore += 100;
			if(awardType == 1){
				List<Enemy> allEnemy1 = new ArrayList<Enemy>(gameView.allEnemy);
				for(Enemy e:allEnemy1){
					if(e.enemyType < 10){
						Explosion explosion = new Explosion(gameView,gameView.enemyExplosion, e.getNowX(), e.getNowY());
						gameView.allExplosion.add(explosion);
						gameView.currentScore += e.score;
						e.dispear();
					}
				}
				List<Bullet> allBullet1 = new ArrayList<Bullet>(gameView.allBullet);
				for(Bullet b:allBullet1){
					if(b.isMyPlaneBullet){
						return;
					}else{
						gameView.allExplosion.add(new Explosion(gameView, Bullet.hited, b.nowX, b.nowY));
						b.disapear();
					}
				}
			}else if(awardType == 2){
				gameView.myPlane.HP += 50;
				System.out.println("myplane.HP="+gameView.myPlane.HP);
			}else if(awardType == 3){
				gameView.myPlane.power += 10;
				System.out.println("myplane.power="+gameView.myPlane.power);
			}else if(awardType == 4){
				if(gameView.myPlane.bulletType < 5){
					gameView.myPlane.bulletType += 1;
					System.out.println("myplane.bulletLevel="+gameView.myPlane.bulletType);
				}
			}
			//disapear();
		}else{
			Random random = new Random();
			int d = Math.abs(random.nextInt()) % 2;
			if(d == 0){
				nowX += speed;
			}else{
				nowX -= speed;
			}
			nowY += speed;
			
			if(nowX > gameView.getScreenWidth() - width){
				nowY = gameView.getScreenWidth() - width;
			}else if(nowX < 0){
				nowX = 0;
			}
			
			if(nowY > gameView.getScreenHeight()){
				disapear();
			}
		}
	}
	
	public void drawSelf(Canvas canvas,Paint paint){
		canvas.drawBitmap(awardBitmap, nowX, nowY, paint);
		move();
	}
	
	//消失的方法
	public void disapear()
	{
		gameView.allAward.remove(this);
	}
}
