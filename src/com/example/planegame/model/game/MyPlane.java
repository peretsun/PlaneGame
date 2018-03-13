package com.example.planegame.model.game;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;

import com.example.planegame.R;
import com.example.planegame.model.constants.Constants;
import com.example.planegame.model.utils.GameUtils;
import com.example.planegame.view.GameSurfaceView;

public class MyPlane implements Runnable {
	GameSurfaceView gameView;
	Bitmap planeBitmap;
	float nowX;
	float nowY;
	int width;
	int height;
	boolean isInvincible;
	int times ;//…¡À∏¥Œ ˝

	int HP;	
	int power;
	int shootSpeed;

	Bitmap bulletBitmap;
	int bulletType;
	int bulletLevel;

	public MyPlane(GameSurfaceView gameView, Bitmap planeBitmap, int HP,
			int power, int speed) {
		this.gameView = gameView;
		this.planeBitmap = planeBitmap;
		this.HP = HP;
		this.power = power;
		this.shootSpeed = speed;
		times = 20;

		width = planeBitmap.getWidth();
		height = planeBitmap.getHeight();

		nowX = gameView.getScreenWidth() / 2 - width / 2;
		nowY = gameView.getScreenHeight() * 4 / 5 - height / 2;
		isInvincible = true;

		bulletType = 1;
		bulletBitmap = BitmapFactory.decodeResource(gameView.getResources(), R.drawable.bullet1);
	}
	

	
	

	public boolean isCollsion() {
		for (Enemy e : gameView.allEnemy) {
			if (GameUtils.isTwoRectCollsion(nowX, nowY, width, height,
					e.getNowX(), e.getNowY(), e.getWidth(), e.getHeight())) {
				return true;
			}
		}
		return false;
	}

	public void move(int x,int y) {
		nowX = x;
		nowY = y;
		if(nowX < 0){
			nowX = 0;
		}
		if(nowY < 0){
			nowY = 0;
		}
		if(nowX > gameView.getScreenWidth()-width){
			nowX = gameView.getScreenWidth()-width;
		}
		if(nowY > gameView.getScreenHeight()-height){
			nowY = gameView.getScreenHeight()-height;
		}
	}

	public void shoot() {
		switch (bulletType) {
		case 1:
			Bullet b = new Bullet(gameView, bulletBitmap, power, nowX + width
					/ 2 - bulletBitmap.getWidth() / 2, nowY, 0, 20, true);
			gameView.allBullet.add(b);
			break;
		case 2:
			Bullet b1 = new Bullet(gameView, bulletBitmap, power, nowX + width
					/ 2 - bulletBitmap.getWidth(), nowY, 0, 20, true);
			Bullet b2 = new Bullet(gameView, bulletBitmap, power, nowX + width
					/ 2, nowY, 0, 20, true);
			gameView.allBullet.add(b1);
			gameView.allBullet.add(b2);
			break;
		case 3:
			b = new Bullet(gameView, bulletBitmap, power, nowX + width / 2
					- bulletBitmap.getWidth() / 2, nowY, 0, 20, true);
			b1 = new Bullet(gameView, bulletBitmap, power, nowX + width / 2
					- bulletBitmap.getWidth(), nowY,
					10, 20, true);
			b2 = new Bullet(gameView, bulletBitmap, power, nowX + width / 2,
					nowY, -10, 20, true);
			gameView.allBullet.add(b);
			gameView.allBullet.add(b1);
			gameView.allBullet.add(b2);
			break;
		case 4:
			b1 = new Bullet(gameView, bulletBitmap, power, nowX + width / 2
					- bulletBitmap.getWidth()-2, nowY, 0, 20, true);
			b2 = new Bullet(gameView, bulletBitmap, power, nowX + width / 2+2,
					nowY, 0, 20, true);
			Bullet b3 = new Bullet(gameView, bulletBitmap, power, nowX + width
					/ 2 - bulletBitmap.getWidth()/2, nowY,
					-5, 20, true);
			Bullet b4 = new Bullet(gameView, bulletBitmap, power, nowX + width
					/ 2 - bulletBitmap.getWidth()/2, nowY,
					5, 20, true);
			gameView.allBullet.add(b1);
			gameView.allBullet.add(b2);
			gameView.allBullet.add(b3);
			gameView.allBullet.add(b4);
			break;
		case 5:
			b = new Bullet(gameView, bulletBitmap, power, nowX + width / 2- bulletBitmap.getWidth()/2,
					nowY, 0, 20, true);
			b1 = new Bullet(gameView, bulletBitmap, power, nowX + width / 2
					- bulletBitmap.getWidth()/2, nowY, 10, 20, true);
			b2 = new Bullet(gameView, bulletBitmap, power, nowX + width / 2
					- bulletBitmap.getWidth()/2, nowY, -10, 20, true);
			b3 = new Bullet(gameView, bulletBitmap, power, nowX + width / 2
					- bulletBitmap.getWidth()/2, nowY, 5, 20, true);
			b4 = new Bullet(gameView, bulletBitmap, power, nowX + width / 2
					- bulletBitmap.getWidth()/2, nowY, -5, 20, true);
			gameView.allBullet.add(b);
			gameView.allBullet.add(b1);
			gameView.allBullet.add(b2);
			gameView.allBullet.add(b3);
			gameView.allBullet.add(b4);
			break;
		}
	}
	
	public void drawSelf(Canvas canvas, Paint paint) {
		if(isInvincible){
			if(times > 0 && times % 2 == 1){
				paint.setAlpha(50);
				canvas.drawBitmap(planeBitmap, nowX, nowY, paint);
				paint.setAlpha(255);
			}else{
				canvas.drawBitmap(planeBitmap, nowX, nowY, paint);
			}
			if(times == 0){
				isInvincible = false;
			}
			times--;
		}else{
			canvas.drawBitmap(planeBitmap, nowX, nowY, paint);
		}
	}
	
	public boolean isTouch(int x,int y){
		return GameUtils.isPointInRect(x, y, nowX, nowY, width, height);
	}

	public Bitmap getBulletBitmap() {
		return bulletBitmap;
	}

	public void setBulletBitmap(Bitmap bulletBitmap) {
		this.bulletBitmap = bulletBitmap;
	}

	public float getNowX() {
		return nowX;
	}

	public void setNowX(float nowX) {
		this.nowX = nowX;
	}

	public float getNowY() {
		return nowY;
	}

	public void setNowY(float nowY) {
		this.nowY = nowY;
	}

	public int getWidth() {
		return width;
	}

	public void setWidth(int width) {
		this.width = width;
	}

	public int getHeight() {
		return height;
	}

	public void setHeight(int height) {
		this.height = height;
	}

	public int getHP() {
		return HP;
	}

	public void setHP(int hP) {
		HP = hP;
	}

	public int getShootSpeed() {
		return shootSpeed;
	}

	public void setShootSpeed(int shootSpeed) {
		this.shootSpeed = shootSpeed;
	}
	public boolean isInvincible() {
		return isInvincible;
	}


	public void setInvincible(boolean isInvincible) {
		this.isInvincible = isInvincible;
	}
	

	@Override
	public void run() {
		while (!gameView.isGameOver) {
			if (gameView.isPlay) {
				shoot();
				if(gameView.getActivity().soundFlag){
					gameView.getActivity().gameSound.playSound(Constants.SoundType.SHOOT, 0);
				}
				try {
					Thread.sleep(500 - shootSpeed);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			} else {

			}
		}

	}

}
