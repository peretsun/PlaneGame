package com.example.planegame.view;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.widget.Toast;

import com.example.planegame.R;
import com.example.planegame.control.activitys.MainActivity;
import com.example.planegame.model.constants.Constants;
import com.example.planegame.model.game.MyButton;

public class SelectMapSurfaceView extends SurfaceView implements
		SurfaceHolder.Callback {
	private MainActivity activity;
	private Bitmap bgBitmap;
	private Bitmap[] mapBitmaps;

	private Paint paint;

	private MyButton btnLast;
	private MyButton btnNext;
	private MyButton btnAttack;

	private int screenWidth;
	private int screenHeight;
	private int currentPage;

	public SelectMapSurfaceView(MainActivity activity) {
		super(activity);
		this.activity = activity;
		currentPage = 0;
		paint = new Paint();
		paint.setAntiAlias(true);
		requestFocus();
		setFocusableInTouchMode(true);
		getHolder().addCallback(this);
	}

	public void initBitmap() {
		bgBitmap = BitmapFactory.decodeResource(activity.getResources(),
				R.drawable.bg_select);
		bgBitmap = Bitmap.createScaledBitmap(bgBitmap, screenWidth,
				screenHeight, false);
		mapBitmaps = new Bitmap[Constants.GAME_MAP_MAX];
		mapBitmaps[0] = BitmapFactory.decodeResource(activity.getResources(),
				R.drawable.map1);
		mapBitmaps[1] = BitmapFactory.decodeResource(activity.getResources(),
				R.drawable.map2);
		mapBitmaps[2] = BitmapFactory.decodeResource(activity.getResources(),
				R.drawable.map3);
		mapBitmaps[3] = BitmapFactory.decodeResource(activity.getResources(),
				R.drawable.map4);
	}

	public void initButton() {
		Bitmap btn_lastBitmap = BitmapFactory.decodeResource(
				activity.getResources(), R.drawable.btn_last);
		btnLast = new MyButton(btn_lastBitmap, screenWidth / 4
				- btn_lastBitmap.getWidth() / 2, screenHeight * 9 / 10
				- btn_lastBitmap.getHeight() / 2);

		Bitmap btn_attackBitmap = BitmapFactory.decodeResource(
				activity.getResources(), R.drawable.btn_attack);
		btnAttack = new MyButton(btn_attackBitmap, screenWidth / 2
				- btn_attackBitmap.getWidth() / 2, screenHeight * 9 / 10
				- btn_attackBitmap.getHeight() / 2);

		Bitmap btn_nextBitmap = BitmapFactory.decodeResource(
				activity.getResources(), R.drawable.btn_next);
		btnNext = new MyButton(btn_nextBitmap, screenWidth * 3 / 4
				- btn_lastBitmap.getWidth() / 2, screenHeight * 9 / 10
				- btn_nextBitmap.getHeight() / 2);
	}

	private void onDrawMyButton(Canvas canvas, Paint paint) {
		btnLast.drawSelf(canvas, paint);
		btnAttack.drawSelf(canvas, paint);
		btnNext.drawSelf(canvas, paint);
	}

	public void onDrawMapName(Canvas canvas, int currentMap) {
		String mapName = "";
		switch (currentMap) {
		case 0:
			mapName = Constants.MapName.MAP_0;
			break;
		case 1:
			mapName = Constants.MapName.MAP_1;
			break;
		case 2:
			mapName = Constants.MapName.MAP_2;
			break;
		case 3:
			mapName = Constants.MapName.MAP_3;
			break;
		}
		if(currentMap + 1 > activity.userInfo.getMap()){
			mapName = "未解锁";
		}
		paint.setColor(Color.YELLOW);
		paint.setTextAlign(Paint.Align.CENTER);
		paint.setTextSize(24);
		canvas.drawText(mapName, screenWidth / 2, screenHeight / 10 + 5, paint);
	}

	public void onDrawSelf(Canvas canvas) {
		canvas.drawBitmap(bgBitmap, 0, 0, paint);
		onDrawMapName(canvas, currentPage);
		canvas.drawBitmap(mapBitmaps[currentPage], screenWidth / 2
				- mapBitmaps[currentPage].getWidth() / 2, screenHeight / 2
				- mapBitmaps[currentPage].getHeight() / 2, paint);
		onDrawMyButton(canvas, paint);
	}

	@Override
	public void surfaceCreated(SurfaceHolder holder) {
		screenWidth = getWidth();
		screenHeight = getHeight();

		initBitmap();
		initButton();

		new Thread() {
			@Override
			public void run() {
				SurfaceHolder myholder = SelectMapSurfaceView.this.getHolder();
				Canvas canvas = myholder.lockCanvas();// 获取画布
				try {
					synchronized (myholder) {
						onDrawSelf(canvas);// 绘制
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

		}.start();

	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		int x = (int) event.getX();
		int y = (int) event.getY();
		switch (event.getAction()) {
		case MotionEvent.ACTION_DOWN:
			if (btnLast.isActionOnButton(x, y)) {
				if(activity.soundFlag){
					activity.gameSound.playSound(Constants.SoundType.BUTTON, 0);
				}
				
				if (currentPage == 0) {
					Toast.makeText(activity, "这已经是第一页了", Toast.LENGTH_SHORT)
							.show();
				} else {
					currentPage--;
					SurfaceHolder myholder = SelectMapSurfaceView.this
							.getHolder();
					Canvas canvas = myholder.lockCanvas();// 获取画布
					try {
						synchronized (myholder) {
							onDrawSelf(canvas);// 绘制
						}
					} catch (Exception e) {
						e.printStackTrace();
					} finally {
						if (canvas != null) {
							myholder.unlockCanvasAndPost(canvas);
						}
					}
				}
			} else if (btnNext.isActionOnButton(x, y)) {
				if(activity.soundFlag){
					activity.gameSound.playSound(Constants.SoundType.BUTTON, 0);
				}
				
				if (currentPage == Constants.GAME_MAP_MAX - 1) {
					Toast.makeText(activity, "这已经是最后一页了", Toast.LENGTH_SHORT)
							.show();
				} else {
					currentPage++;
					SurfaceHolder myholder = SelectMapSurfaceView.this
							.getHolder();
					Canvas canvas = myholder.lockCanvas();// 获取画布
					try {
						synchronized (myholder) {
							onDrawSelf(canvas);// 绘制
						}
					} catch (Exception e) {
						e.printStackTrace();
					} finally {
						if (canvas != null) {
							myholder.unlockCanvasAndPost(canvas);
						}
					}
				}
			} else if (btnAttack.isActionOnButton(x, y)) {
				if(activity.soundFlag){
					activity.gameSound.playSound(Constants.SoundType.BUTTON, 0);
				}
				
				if(currentPage+1 > activity.userInfo.getMap()){
					Toast.makeText(activity,"当前关卡未解锁", Toast.LENGTH_SHORT).show();
				}else{
					System.out.println("MAP----" + currentPage + 1);
					activity.currentMap = currentPage + 1;
					activity.sendMessage(Constants.WhatMessage.GOTO_GAME_VIEW);
				}
			}
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
		// TODO Auto-generated method stub

	}

}
