package com.example.planegame.view;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.view.KeyEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.View.OnKeyListener;

import com.example.planegame.R;
import com.example.planegame.control.activitys.MainActivity;
import com.example.planegame.model.constants.Constants;

public class HelpSurfaceView extends SurfaceView implements
		SurfaceHolder.Callback {
	private MainActivity activity;
	private Bitmap bgBitmap;
	private Paint paint;
	private int screenWidth;
	private int screenHeight;

	public HelpSurfaceView(MainActivity activity) {
		super(activity);
		this.activity = activity;
		paint = new Paint();
		paint.setAntiAlias(true);
		this.requestFocus();
		this.setFocusableInTouchMode(true);
		getHolder().addCallback(this);
	}

	@Override
	public void surfaceCreated(SurfaceHolder holder) {
		screenWidth = this.getWidth();
		screenHeight = this.getHeight();
		bgBitmap = BitmapFactory.decodeResource(activity.getResources(),
				R.drawable.bg_help);
		bgBitmap = Bitmap.createScaledBitmap(bgBitmap, screenWidth,
				screenHeight, false);

		new Thread() {
			public void run() {
				SurfaceHolder myholder = HelpSurfaceView.this.getHolder();
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

	public void onDrawSelf(Canvas canvas) {
		canvas.drawBitmap(bgBitmap, 0, 0, paint);
	}

	@Override
	public void surfaceChanged(SurfaceHolder holder, int format, int width,
			int height) {
		// TODO Auto-generated method stub

	}

	@Override
	public void surfaceDestroyed(SurfaceHolder holder) {
		activity.sendMessage(Constants.WhatMessage.GOTO_MAIN_MENU_VIEW);
	}

}
