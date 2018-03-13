package com.example.planegame.view;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff.Mode;
import android.graphics.PorterDuffXfermode;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import com.example.planegame.R;
import com.example.planegame.control.activitys.MainActivity;
import com.example.planegame.model.constants.Constants;

public class WellcomeSurfaceView extends SurfaceView implements
		SurfaceHolder.Callback {
	private MainActivity activity;
	private Paint paint;
	private Bitmap[] logoBitmaps = new Bitmap[2];
	private Bitmap currentLogo;
	private int currentAlpha = 0;

	private int screenWidth;// 屏幕宽度
	private int screenHeight;// 屏幕高度

	public WellcomeSurfaceView(MainActivity activity) {
		super(activity);
		this.activity = activity;
		paint = new Paint();// 创建画笔
		paint.setAntiAlias(true);// 打开抗锯齿
		
		// this.requestFocus();//获得焦点
		// this.setFocusableInTouchMode(true);//设置可触摸
		getHolder().addCallback(this);// 注册回调接口

	}

	public void initBitmap() {
		logoBitmaps[0] = BitmapFactory.decodeResource(activity.getResources(),
				R.drawable.wellcome1);
		logoBitmaps[0] = Bitmap.createScaledBitmap(logoBitmaps[0], screenWidth,
				screenHeight, false);
		logoBitmaps[1] = BitmapFactory.decodeResource(activity.getResources(),
				R.drawable.wellcome2);
		logoBitmaps[1] = Bitmap.createScaledBitmap(logoBitmaps[1], screenWidth,
				screenHeight, false);
	}

	public void onDrawSelf(Canvas canvas) {
		/*
		 * //绘制黑填充矩形清背景 canvas.save();//保存画布状态 paint.setXfermode(new
		 * PorterDuffXfermode(Mode.SRC));//恢复画笔
		 * paint.setColor(Color.BLACK);//设置画笔颜色 paint.setAlpha(255);
		 * canvas.drawRect(0, 0, screenWidth, screenHeight, paint);
		 * paint.setXfermode(new PorterDuffXfermode(Mode.CLEAR));//清除对画笔的设置
		 * canvas.restore(); //恢复画布状态
		 */
		// 进行平面贴图
		if (currentLogo == null)
			return;
		canvas.save();
		paint.setXfermode(new PorterDuffXfermode(Mode.SRC));
		paint.setAlpha(currentAlpha);
		canvas.drawBitmap(currentLogo, 0, 0, paint);
		paint.setXfermode(new PorterDuffXfermode(Mode.CLEAR));
		canvas.restore();
	}

	public void surfaceCreated(SurfaceHolder holder) {
		// 获得屏幕宽高
		screenWidth = this.getWidth();
		screenHeight = this.getHeight();
		initBitmap();
		// System.out.println("H:"+screenHeight +" W:"+screenWidth);
		new Thread() {
			public void run() {
				for (Bitmap bm : logoBitmaps) {
					currentLogo = bm;
					// 计算图片位置
					// currentX=screenWidth/2-bm.getWidth()/2;
					// currentY=screenHeight/2-bm.getHeight()/2;

					for (int i = 255; i > -10; i = i - 10) {// 动态更改图片的透明度值并不断重绘
						currentAlpha = i;
						if (currentAlpha < 0) {
							currentAlpha = 0;
						}
						SurfaceHolder myholder = WellcomeSurfaceView.this
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
						try {
							if (i == 255) {// 若是新图片，多等待一会
								Thread.sleep(1000);
							}
							Thread.sleep(50);
						} catch (Exception e) {
							e.printStackTrace();
						}
					}
				}
				// 动画播放完毕后，去主菜单界面
				activity.sendMessage(Constants.WhatMessage.GOTO_MAIN_MENU_VIEW);
			}
		}.start();
	}

	public void surfaceChanged(SurfaceHolder holder, int format, int width,
			int height) {
		// TODO Auto-generated method stub

	}

	public void surfaceDestroyed(SurfaceHolder holder) {
		// TODO Auto-generated method stub

	}

}
