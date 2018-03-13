package com.example.planegame.model.game;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;

import com.example.planegame.R;
import com.example.planegame.model.constants.Constants;
import com.example.planegame.model.constants.Constants.EnemyType;
import com.example.planegame.model.utils.GameUtils;
import com.example.planegame.view.GameSurfaceView;

public class Bullet {
	static Bitmap[] hited;//子弹击中敌机爆炸组图
	GameSurfaceView gameView;
	Bitmap bulletBitmap;
	float nowX;
	float nowY;
	float vx;//x方向速度
	float vy;//y方向速度
	int width;
	int height;
	
	//private int bulletStyle;//子弹运动方式
	//private int bulletType;//子弹的类型
	int harm;//子弹的伤害
	
	boolean isMyPlaneBullet;
	
	public Bullet(GameSurfaceView gameView,Bitmap bulletBitmap,int harm,float x,float y,float vx,float vy,boolean isMyPlaneBullet){
		this.gameView = gameView;
		this.bulletBitmap = bulletBitmap;
		this.nowX = x;
		this.nowY = y;
		this.vx = vx;
		this.vy = vy;
		this.harm = harm;
		this.isMyPlaneBullet = isMyPlaneBullet;
		this.width = bulletBitmap.getWidth();
		this.height = bulletBitmap.getHeight();
		if(hited == null){
			initHited();
		}
	}
	
	public void initHited(){
		hited = new Bitmap[3];
		//hited[0] = BitmapFactory.decodeResource(gameView.getResources(), R.drawable.hited0);
		hited[0] = BitmapFactory.decodeResource(gameView.getResources(), R.drawable.hited1);
		hited[1] = BitmapFactory.decodeResource(gameView.getResources(), R.drawable.hited2);
		hited[2] = BitmapFactory.decodeResource(gameView.getResources(), R.drawable.hited3);
	}
	
	public boolean isCllosion(){
		if(isMyPlaneBullet){
			for(Enemy e:gameView.allEnemy){
				if(GameUtils.isTwoRectCollsion(
						nowX, nowY, width, height, 
						e.getNowX(), e.getNowY(),e.getWidth(), e.getHeight())
				){
					e.setHP(e.getHP() - harm);
					disapear();//移除子弹
					gameView.allExplosion.add(new Explosion(gameView, Bullet.hited, nowX, nowY));
					if(e.getHP() <= 0){
						gameView.currentScore += e.getScore();
						if(e.getEnemyType() < 11){
							if(gameView.getActivity().soundFlag){
								gameView.getActivity().gameSound.playSound(Constants.SoundType.EXPLOSION2, 0);
							}
							Explosion explosion = new Explosion(gameView, gameView.enemyExplosion, e.getNowX(), e.getNowY());
							gameView.allExplosion.add(explosion);
							e.dispear();
						}else{
							if(gameView.getActivity().soundFlag){
								gameView.getActivity().gameSound.playSound(Constants.SoundType.BIGEXPLOSION, 0);
							}
							Explosion explosion = new Explosion(gameView, gameView.bossExplosion, e.getNowX(), e.getNowY());
							gameView.allExplosion.add(explosion);
							gameView.currentBoss = null;
							gameView.gameOver();
							return true;
						}
					}
					return true;
				}
			}
		}else{
			if(GameUtils.isTwoRectCollsion(
					nowX, nowY, width, height, 
					gameView.myPlane.getNowX(), gameView.myPlane.getNowY(),gameView.myPlane.getWidth(), gameView.myPlane.getHeight())
			){
				disapear();
				gameView.myPlane.setHP(gameView.myPlane.getHP() - harm);
				if(gameView.myPlane.getHP() <= 0){
					gameView.gameOver();
				}
				return true;
			}
		}
		
		return false;
	}
	
	public void move(){
		if(isCllosion()){
			if(gameView.getActivity().soundFlag){
				gameView.getActivity().gameSound.playSound(Constants.SoundType.HIT, 0);
			}
		}else{
			nowX += vx;
			if(isMyPlaneBullet){
				nowY -= vy;
			}else{
				nowY += vy;
			}
			if(nowX < 0 || nowX > gameView.getScreenWidth() || nowY < 0 || nowY > gameView.getScreenHeight()){
				disapear();
			}
		}
	}
	
	public void drawSelf(Canvas canvas,Paint paint){
		move();
		canvas.drawBitmap(bulletBitmap, nowX, nowY, paint);
	}
	
	public void disapear(){
		gameView.allBullet.remove(this);
	}
}
