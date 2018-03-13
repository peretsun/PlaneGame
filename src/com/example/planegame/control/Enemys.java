/*package com.example.planegame.control;

import java.util.Random;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;

import com.example.planegame.R;
import com.example.planegame.model.constants.Constants;
import com.example.planegame.model.game.Bullet;
import com.example.planegame.model.utils.GameUtils;
import com.example.planegame.view.GameSurfaceView;

public class Enemys implements Runnable {
	private GameSurfaceView gameView;
	private Bitmap enemyBitmap;
	private Bitmap bulletBitmap;
	private float nowX;
	private float nowY;
	private float vx;
	private float vy;
	private int width;
	private int height;

	private int HP;
	private int power;
	private int shootSpeed;
	private int score;

	private int map;
	private int enemyType;

	public Enemys(GameSurfaceView gameView, int enemyType, int map) {
		this.gameView = gameView;
		this.map = map;
		this.enemyType = enemyType;
		initEnemy(enemyType);
	}

	public void initEnemy(int enemyType) {
		switch (enemyType) {
		case Constants.EnemyType.ENEMY1:
			initEnemy1();
			break;
		case Constants.EnemyType.ENEMY2:
			initEnemy2();
			break;
		case Constants.EnemyType.ENEMY3:
			initEnemy3();
			break;
		case Constants.EnemyType.ENEMY4:
			initEnemy4();
			break;
		case Constants.EnemyType.ENEMY5:
			initEnemy5();
			break;
		case Constants.EnemyType.ENEMY6:
			initEnemy6();
			break;
		case Constants.EnemyType.ENEMY7:
			initEnemy7();
			break;
		case Constants.EnemyType.ENEMY8:
			initEnemy8();
			break;
		case Constants.EnemyType.ENEMY9:
			initEnemy9();
			break;
		case Constants.EnemyType.ENEMY10:
			initEnemy10();
			break;
		case Constants.EnemyType.BOSS1:
			initBOSS1();
			break;
		case Constants.EnemyType.BOSS2:
			initBOSS2();
			break;
		case Constants.EnemyType.BOSS3:
			initBOSS3();
			break;
		case Constants.EnemyType.BOSS4:
			initBOSS4();
			break;

		}
		width = enemyBitmap.getWidth();
		height = enemyBitmap.getHeight();
	}

	private void initBOSS1() {
		enemyBitmap = BitmapFactory.decodeResource(gameView.getResources(),
				R.drawable.boss1);
		bulletBitmap = BitmapFactory.decodeResource(gameView.getResources(),
				R.drawable.boss_bullet);
		HP = 3000;
		power = 50;
		score = 3000;
		nowX = new Random().nextInt(320 - enemyBitmap.getWidth());
		nowY = -height;
		vx = 10;
		vy = height/10;
		shootSpeed = 200;
	}

	private void initBOSS2() {
		enemyBitmap = BitmapFactory.decodeResource(gameView.getResources(),
				R.drawable.boss2);
		bulletBitmap = BitmapFactory.decodeResource(gameView.getResources(),
				R.drawable.boss_bullet);
		HP = 6000;
		power = 50;
		score = 5000;
		nowX = new Random().nextInt(320 - enemyBitmap.getWidth());
		nowY = -height;
		vx = 10;
		vy = height/10;
		shootSpeed = 200;

	}

	private void initBOSS3() {
		enemyBitmap = BitmapFactory.decodeResource(gameView.getResources(),
				R.drawable.boss3);
		bulletBitmap = BitmapFactory.decodeResource(gameView.getResources(),
				R.drawable.boss_bullet);
		HP = 10000;
		power = 50;
		score = 8000;
		nowX = new Random().nextInt(320 - enemyBitmap.getWidth());
		nowY = -height;
		vx = 20;
		vy = height/10;
		shootSpeed = 200;

	}

	private void initBOSS4() {
		enemyBitmap = BitmapFactory.decodeResource(gameView.getResources(),
				R.drawable.boss4);
		bulletBitmap = BitmapFactory.decodeResource(gameView.getResources(),
				R.drawable.boss_bullet);
		HP = 15000;
		power = 50;
		score = 10000;
		nowX = new Random().nextInt(320 - enemyBitmap.getWidth());
		nowY = -height;
		vx = 20;
		vy = height/10;
		shootSpeed = 200;

	}

	private void initEnemy1() {
		enemyBitmap = BitmapFactory.decodeResource(gameView.getResources(),
				R.drawable.enemy1);
		bulletBitmap = BitmapFactory.decodeResource(gameView.getResources(),
				R.drawable.enemy_bullet1);
		HP = 20 * map;
		power = 10;
		score = 20 * map;
		nowX = new Random().nextInt(320 - enemyBitmap.getWidth());
		nowY = 0;
		vx = 0;
		vy = 10;
		shootSpeed = 0;
	}

	private void initEnemy2() {
		enemyBitmap = BitmapFactory.decodeResource(gameView.getResources(),
				R.drawable.enemy2);
		bulletBitmap = BitmapFactory.decodeResource(gameView.getResources(),
				R.drawable.enemy2_bullet);
		HP = 40 * map;
		power = 10;
		score = 50 * map;
		nowX = new Random().nextInt(320 - enemyBitmap.getWidth());
		nowY = 0;
		vx = 5;
		vy = 10;
		shootSpeed = 100;
	}

	private void initEnemy3() {
		enemyBitmap = BitmapFactory.decodeResource(gameView.getResources(),
				R.drawable.enemy3);
		bulletBitmap = BitmapFactory.decodeResource(gameView.getResources(),
				R.drawable.enemy3_bullet);
		HP = 40 * map;
		power = 30;
		score = 60 * map;
		nowX = new Random().nextInt(320 - enemyBitmap.getWidth());
		nowY = 0;
		vx = 0;
		vy = 10;
		shootSpeed = 200;
	}

	private void initEnemy4() {
		enemyBitmap = BitmapFactory.decodeResource(gameView.getResources(),
				R.drawable.enemy4);
		bulletBitmap = BitmapFactory.decodeResource(gameView.getResources(),
				R.drawable.enemy4_bullet);
		HP = 80 * map;
		power = 40;
		score = 80 * map;
		nowX = new Random().nextInt(320 - enemyBitmap.getWidth());
		nowY = 0;
		vx = 0;
		vy = 10;
		shootSpeed = 200;
	}

	private void initEnemy5() {
		enemyBitmap = BitmapFactory.decodeResource(gameView.getResources(),
				R.drawable.enemy5);
		bulletBitmap = BitmapFactory.decodeResource(gameView.getResources(),
				R.drawable.enemy5_bullet);
		HP = 80 * map;
		power = 50;
		score = 120 * map;
		nowX = new Random().nextInt(320 - enemyBitmap.getWidth());
		nowY = 0;
		vx = 0;
		vy = 10;
		shootSpeed = 200;
	}

	private void initEnemy6() {
		enemyBitmap = BitmapFactory.decodeResource(gameView.getResources(),
				R.drawable.enemy6);
		bulletBitmap = BitmapFactory.decodeResource(gameView.getResources(),
				R.drawable.enemy6_bullet);
		HP = 500 * map;
		power = 50;
		score = 120 * map;
		nowX = new Random().nextInt(320 - enemyBitmap.getWidth());
		nowY = 0;
		vx = 0;
		vy = 10;
		shootSpeed = 200;
	}

	private void initEnemy7() {
		enemyBitmap = BitmapFactory.decodeResource(gameView.getResources(),
				R.drawable.enemy7);
		bulletBitmap = BitmapFactory.decodeResource(gameView.getResources(),
				R.drawable.enemy7_bullet);
		HP = 500 * map;
		power = 50;
		score = 120 * map;
		nowX = new Random().nextInt(320 - enemyBitmap.getWidth());
		nowY = 0;
		vx = 0;
		vy = 10;
		shootSpeed = 200;
	}

	private void initEnemy8() {
		enemyBitmap = BitmapFactory.decodeResource(gameView.getResources(),
				R.drawable.enemy8);
		bulletBitmap = BitmapFactory.decodeResource(gameView.getResources(),
				R.drawable.enemy8_bullet);
		HP = 800 * map;
		power = 60;
		score = 200 * map;
		nowX = new Random().nextInt(320 - enemyBitmap.getWidth());
		nowY = 0;
		vx = 0;
		vy = 10;
		shootSpeed = 200;
	}

	private void initEnemy9() {
		enemyBitmap = BitmapFactory.decodeResource(gameView.getResources(),
				R.drawable.enemy9);
		bulletBitmap = BitmapFactory.decodeResource(gameView.getResources(),
				R.drawable.enemy9_bullet);
		HP = 800 * map;
		power = 60;
		score = 200 * map;
		nowX = new Random().nextInt(320 - enemyBitmap.getWidth());
		nowY = 0;
		vx = 0;
		vy = 10;
		shootSpeed = 200;
	}

	private void initEnemy10() {
		enemyBitmap = BitmapFactory.decodeResource(gameView.getResources(),
				R.drawable.enemy10);
		bulletBitmap = BitmapFactory.decodeResource(gameView.getResources(),
				R.drawable.enemy9_bullet);
		HP = 1000 * map;
		power = 80;
		score = 300 * map;
		nowX = new Random().nextInt(320 - enemyBitmap.getWidth());
		nowY = 0;
		vx = 0;
		vy = 10;
		shootSpeed = 200;
	}

	public boolean iscollsion() {
		return GameUtils.isTwoRectCollsion(nowX, nowY, width, height,
				gameView.myPlane.getNowX(), gameView.myPlane.getNowY(),
				gameView.myPlane.getWidth(), gameView.myPlane.getHeight());
	}

	public void move() {
		if(enemyType > 10){
			if(nowY < 0){
				nowY += vy;
				return;
			}else{
				vy = 0;
			}
		}
		if (nowX <= 0) {
			vx = -vx;
		}
		if (nowX > gameView.getScreenWidth() - width) {
			vx = -vx;
		}
		nowX += vx;
		nowY += vy;
		if (nowY >= gameView.getScreenHeight()) {
			dispear();
		}
	}

	public void shoot() {
		switch (enemyType) {
		case Constants.EnemyType.ENEMY1:
			gameView.allBullet.add(new Bullet(gameView, bulletBitmap, power,
					nowX + width / 2, nowY + height, 0, 30, false));
			break;
		case Constants.EnemyType.ENEMY2:
			gameView.allBullet.add(new Bullet(gameView, bulletBitmap, power,
					nowX + width / 2, nowY + height, 0, 30, false));
			break;
		case Constants.EnemyType.ENEMY3:
			gameView.allBullet.add(new Bullet(gameView, bulletBitmap, power,
					nowX + width / 2, nowY + height, 0, 30, false));
			break;
		case Constants.EnemyType.ENEMY4:
			gameView.allBullet.add(new Bullet(gameView, bulletBitmap, power,
					nowX + width / 2, nowY + height, 0, 30, false));
			break;
		case Constants.EnemyType.ENEMY5:
			gameView.allBullet.add(new Bullet(gameView, bulletBitmap, power,
					nowX + width / 2, nowY + height, 10, 10, false));
			gameView.allBullet.add(new Bullet(gameView, bulletBitmap, power,
					nowX + width / 2, nowY + height, -10, 10, false));
			gameView.allBullet.add(new Bullet(gameView, bulletBitmap, power,
					nowX + width / 2, nowY + height, 10, -10, false));
			gameView.allBullet.add(new Bullet(gameView, bulletBitmap, power,
					nowX + width / 2, nowY + height, -10, -10, false));
			break;
		case Constants.EnemyType.ENEMY6:
			gameView.allBullet.add(new Bullet(gameView, bulletBitmap, power,
					nowX + width / 2, nowY + height, 10, 10, false));
			gameView.allBullet.add(new Bullet(gameView, bulletBitmap, power,
					nowX + width / 2, nowY + height, -10, 10, false));
			gameView.allBullet.add(new Bullet(gameView, bulletBitmap, power,
					nowX + width / 2, nowY + height, 0, 10, false));
			break;
		case Constants.EnemyType.ENEMY7:
			gameView.allBullet.add(new Bullet(gameView, bulletBitmap, power,
					nowX + width / 2, nowY + height, 0, 20, false));
			gameView.allBullet.add(new Bullet(gameView, bulletBitmap, power,
					nowX + width / 2, nowY + height, 5, 20, false));
			gameView.allBullet.add(new Bullet(gameView, bulletBitmap, power,
					nowX + width / 2, nowY + height, -5, 20, false));
			break;
		case Constants.EnemyType.ENEMY8:
			gameView.allBullet.add(new Bullet(gameView, bulletBitmap, power,
					nowX + width / 2, nowY + height, 0, 20, false));
			gameView.allBullet.add(new Bullet(gameView, bulletBitmap, power,
					nowX + width / 2, nowY + height, 5, 20, false));
			gameView.allBullet.add(new Bullet(gameView, bulletBitmap, power,
					nowX + width / 2, nowY + height, -5, 20, false));
			break;
		case Constants.EnemyType.ENEMY9:
			gameView.allBullet.add(new Bullet(gameView, bulletBitmap, power,
					nowX + width / 2, nowY + height, 0, 20, false));
			gameView.allBullet.add(new Bullet(gameView, bulletBitmap, power,
					nowX + width / 2, nowY + height, 5, 20, false));
			gameView.allBullet.add(new Bullet(gameView, bulletBitmap, power,
					nowX + width / 2, nowY + height, -5, 20, false));
			break;
		case Constants.EnemyType.ENEMY10:
			gameView.allBullet.add(new Bullet(gameView, bulletBitmap, power,
					nowX + width / 2, nowY + height, 0, 20, false));
			gameView.allBullet.add(new Bullet(gameView, bulletBitmap, power,
					nowX + width / 2, nowY + height, 5, 20, false));
			gameView.allBullet.add(new Bullet(gameView, bulletBitmap, power,
					nowX + width / 2, nowY + height, -5, 20, false));
			break;
		case Constants.EnemyType.BOSS1:
			gameView.allBullet.add(new Bullet(gameView, bulletBitmap, power,
					nowX + width / 2, nowY + height, 0, 20, false));
			gameView.allBullet.add(new Bullet(gameView, bulletBitmap, power,
					nowX + width / 2, nowY + height, 10, 20, false));
			gameView.allBullet.add(new Bullet(gameView, bulletBitmap, power,
					nowX + width / 2, nowY + height, -10, 20, false));
			gameView.allBullet.add(new Bullet(gameView, bulletBitmap, power,
					nowX + width / 2, nowY + height, 20, 20, false));
			gameView.allBullet.add(new Bullet(gameView, bulletBitmap, power,
					nowX + width / 2, nowY + height, -20, 20, false));
			break;
		case Constants.EnemyType.BOSS2:
			gameView.allBullet.add(new Bullet(gameView, bulletBitmap, power,
					nowX + width / 2, nowY + height, 0, 20, false));
			gameView.allBullet.add(new Bullet(gameView, bulletBitmap, power,
					nowX + width / 2, nowY + height, 5, 20, false));
			gameView.allBullet.add(new Bullet(gameView, bulletBitmap, power,
					nowX + width / 2, nowY + height, -5, 20, false));
			gameView.allBullet.add(new Bullet(gameView, bulletBitmap, power,
					nowX + width / 2, nowY + height, 10, 20, false));
			gameView.allBullet.add(new Bullet(gameView, bulletBitmap, power,
					nowX + width / 2, nowY + height, -10, 20, false));
			break;
		case Constants.EnemyType.BOSS3:
			gameView.allBullet.add(new Bullet(gameView, bulletBitmap, power,
					nowX + width / 2, nowY + height, 0, 30, false));
			gameView.allBullet.add(new Bullet(gameView, bulletBitmap, power,
					nowX + width / 2, nowY + height, 5, 30, false));
			gameView.allBullet.add(new Bullet(gameView, bulletBitmap, power,
					nowX + width / 2, nowY + height, -5, 30, false));
			gameView.allBullet.add(new Bullet(gameView, bulletBitmap, power,
					nowX + width / 2, nowY + height, 10, 30, false));
			gameView.allBullet.add(new Bullet(gameView, bulletBitmap, power,
					nowX + width / 2, nowY + height, -10, 30, false));
			break;
		case Constants.EnemyType.BOSS4:
			gameView.allBullet.add(new Bullet(gameView, bulletBitmap, power,
					nowX + width / 2, nowY + height, 0, 30, false));
			gameView.allBullet.add(new Bullet(gameView, bulletBitmap, power,
					nowX + width / 2, nowY + height, 5, 30, false));
			gameView.allBullet.add(new Bullet(gameView, bulletBitmap, power,
					nowX + width / 2, nowY + height, -5, 30, false));
			gameView.allBullet.add(new Bullet(gameView, bulletBitmap, power,
					nowX + width / 2, nowY + height, 10, 30, false));
			gameView.allBullet.add(new Bullet(gameView, bulletBitmap, power,
					nowX + width / 2, nowY + height, -10, 30, false));
			break;

		}
	}

	public void drawSelf(Canvas canvas, Paint paint) {
		move();
		canvas.drawBitmap(enemyBitmap, nowX, nowY, paint);
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

	public int getScore() {
		return score;
	}

	public void setScore(int score) {
		this.score = score;
	}

	public int getEnemyType() {
		return enemyType;
	}

	public void setEnemyType(int enemyType) {
		this.enemyType = enemyType;
	}

	public void dispear() {
		gameView.allEnemy.remove(this);
	}

	@Override
	public void run() {
		while ((!gameView.isGameOver) && gameView.allEnemy.contains(this)) {
			if (gameView.isPlay) {
				shoot();
				try {
					Thread.sleep(2000 - shootSpeed);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			} else {

			}
		}
	}

}
*/