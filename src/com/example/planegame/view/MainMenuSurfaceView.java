package com.example.planegame.view;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import com.example.planegame.R;
import com.example.planegame.control.activitys.MainActivity;
import com.example.planegame.control.dialogs.MyDialog;
import com.example.planegame.model.UserInfo;
import com.example.planegame.model.constants.Constants;
import com.example.planegame.model.game.MyButton;

public class MainMenuSurfaceView extends SurfaceView implements
		SurfaceHolder.Callback {
	private MainActivity activity;
	private Paint paint;
	private Bitmap bgBitmap;

	private MyButton btnStart;
	private MyButton btnChallenge;
	private MyButton btnShop;
	private MyButton btnRecord;

	private MyButton btnSoundControl;
	private MyButton btnHelp;
	private MyButton btnInfo;
	private MyButton btnExit;

	private int screenWidth;
	private int screenHeight;

	public MainMenuSurfaceView(MainActivity activity) {
		super(activity);
		System.out.println("MainMenuSurfaceView");
		this.activity = activity;
		paint = new Paint();
		paint.setAntiAlias(true);
		// 获得焦点并设置为可触控
		this.requestFocus();
		this.setFocusableInTouchMode(true);
		getHolder().addCallback(this);// 注册回调接口
	}

	public void initBitmap() {
		// 获取图片并调整图片大小适应屏幕大小
		bgBitmap = BitmapFactory.decodeResource(activity.getResources(),
				R.drawable.bg_main_menu);
		bgBitmap = Bitmap.createScaledBitmap(bgBitmap, screenWidth,
				screenHeight, false);
	}

	public void initButton() {
		// 获取图片并调整图片大小为屏幕大小1/2
		Bitmap btnBitmap = BitmapFactory.decodeResource(
				activity.getResources(), R.drawable.btn_main_menu);
		btnBitmap = Bitmap.createScaledBitmap(btnBitmap, screenWidth / 2,
				screenHeight / 2, false);

		Bitmap bm = Bitmap.createBitmap(btnBitmap, 0, 0, btnBitmap.getWidth(),
				btnBitmap.getHeight() / 4);
		btnStart = new MyButton(bm, screenWidth / 4, screenHeight / 4);

		bm = Bitmap.createBitmap(btnBitmap, 0, btnBitmap.getHeight() / 4,
				btnBitmap.getWidth(), btnBitmap.getHeight() / 4);
		btnChallenge = new MyButton(bm, screenWidth / 4, screenHeight * 3 / 8);

		bm = Bitmap.createBitmap(btnBitmap, 0, btnBitmap.getHeight() / 2,
				btnBitmap.getWidth(), btnBitmap.getHeight() / 4);
		btnShop = new MyButton(bm, screenWidth / 4, screenHeight / 2);

		bm = Bitmap.createBitmap(btnBitmap, 0, btnBitmap.getHeight() * 3 / 4,
				btnBitmap.getWidth(), btnBitmap.getHeight() / 4);
		btnRecord = new MyButton(bm, screenWidth / 4, screenHeight * 5 / 8);

		bm = BitmapFactory.decodeResource(activity.getResources(),
				R.drawable.btn_set_main_menu);
		btnSoundControl = new MyButton(bm, screenWidth / 32,
				screenHeight * 3 / 4);

		bm = BitmapFactory.decodeResource(activity.getResources(),
				R.drawable.btn_help_main_menu);
		btnHelp = new MyButton(bm, screenWidth / 32, screenHeight * 3 / 4
				+ bm.getHeight());

		bm = BitmapFactory.decodeResource(activity.getResources(),
				R.drawable.btn_info_main_menu);
		btnInfo = new MyButton(bm, screenWidth * 31 / 32 - bm.getWidth(),
				screenHeight * 3 / 4);

		bm = BitmapFactory.decodeResource(activity.getResources(),
				R.drawable.btn_exit_main_menu);
		btnExit = new MyButton(bm, screenWidth * 31 / 32 - bm.getWidth(),
				screenHeight * 3 / 4 + bm.getHeight());
	}

	public void initUserInfo() {
		activity.userInfo = activity.dbManager.queryUserInfo();
		if (activity.userInfo != null) {
			if (activity.userInfo.getName() == null
					|| activity.userInfo.getName().equals("")) {
				activity.dbManager.deleteUserInfo();
				activity.userInfo = null;
			}
		}
		// 如果userinfo为null表示用户第一次进入游戏
		if (activity.userInfo == null) {
			activity.userInfo = new UserInfo();
			activity.userInfo.setMilitary(2);
			activity.userInfo.setMoney(100000);
			activity.userInfo.addPlanes(0);
			activity.userInfo.addPlanes(3);
			activity.userInfo.setMap(1);
			new MyDialog(activity, Constants.DialogType.INPUT_USERNAME).show();
		} else {
			activity.userInfo.setOwnPlane(activity.dbManager.queryOwnplanes());
		}
	}

	@Override
	public void surfaceCreated(SurfaceHolder holder) {
		screenWidth = this.getWidth();
		screenHeight = this.getHeight();
		initBitmap();
		initButton();
		if (activity.userInfo == null) {
			initUserInfo();
		}
		System.out.println(activity.userInfo);
		new Thread() {
			@Override
			public void run() {
				SurfaceHolder myholder = MainMenuSurfaceView.this.getHolder();
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

	private void onDrawMyButton(Canvas canvas, Paint paint) {
		paint.setAlpha(180);
		btnStart.drawSelf(canvas, paint);
		btnChallenge.drawSelf(canvas, paint);
		btnShop.drawSelf(canvas, paint);
		btnRecord.drawSelf(canvas, paint);
		btnSoundControl.drawSelf(canvas, paint);
		btnHelp.drawSelf(canvas, paint);
		btnInfo.drawSelf(canvas, paint);
		btnExit.drawSelf(canvas, paint);
	}

	public void onDrawSelf(Canvas canvas) {
		canvas.save();
		paint.setAlpha(255);
		canvas.drawBitmap(bgBitmap, 0, 0, paint);
		onDrawMyButton(canvas, paint);
		canvas.restore();
	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		int x = (int) event.getX();
		int y = (int) event.getY();
		switch (event.getAction()) {
		case MotionEvent.ACTION_DOWN:
			// 点击在哪个按钮上开启哪个按钮
			if(activity.userInfo.getName() == null||"".equals(activity.userInfo.getName())){
				new MyDialog(activity, Constants.DialogType.INPUT_USERNAME).show();
				return true;
			}
			if (btnStart.isActionOnButton(x, y)) {
				if (activity.soundFlag) {
					activity.gameSound.playSound(Constants.SoundType.BUTTON, 0);
				}
				activity.currentPattem = 0;
				activity.sendMessage(Constants.WhatMessage.GOTO_SELECT_PLANE_VIEW);

			} else if (btnChallenge.isActionOnButton(x, y)) {
				System.out.println("btnChallenge按下");
				if (activity.soundFlag) {
					activity.gameSound.playSound(Constants.SoundType.BUTTON, 0);
				}
				activity.currentPattem = 1;
				activity.sendMessage(Constants.WhatMessage.GOTO_SELECT_PLANE_VIEW);

			} else if (btnShop.isActionOnButton(x, y)) {
				System.out.println("btnShop按下");
				if (activity.soundFlag) {
					activity.gameSound.playSound(Constants.SoundType.BUTTON, 0);
				}
				activity.sendMessage(Constants.WhatMessage.GOTO_SHOP_VIEW);

			} else if (btnRecord.isActionOnButton(x, y)) {
				System.out.println("btnRecord按下");
				if (activity.soundFlag) {
					activity.gameSound.playSound(Constants.SoundType.BUTTON, 0);
				}
				activity.sendMessage(Constants.WhatMessage.GOTO_RECORD_VIEW);

			} else if (btnHelp.isActionOnButton(x, y)) {
				System.out.println("btnHelp按下");
				if (activity.soundFlag) {
					activity.gameSound.playSound(Constants.SoundType.BUTTON, 0);
				}
				activity.sendMessage(Constants.WhatMessage.GOTO_HELP_VIEW);

			} else if (btnInfo.isActionOnButton(x, y)) {
				System.out.println("btnInfo按下");
				if (activity.soundFlag) {
					activity.gameSound.playSound(Constants.SoundType.BUTTON, 0);
				}
				activity.sendMessage(Constants.WhatMessage.GOTO_INFO_VIEW);

			} else if (btnSoundControl.isActionOnButton(x, y)) {
				System.out.println("btnSoundControl按下");
				if (activity.soundFlag) {
					activity.gameSound.playSound(Constants.SoundType.BUTTON, 0);
				}
				activity.sendMessage(Constants.WhatMessage.GOTO_SOUND_CONTORL_VIEW);

			} else if (btnExit.isActionOnButton(x, y)) {
				System.out.println("btnExit按下");
				if (activity.soundFlag) {
					activity.gameSound.playSound(Constants.SoundType.BUTTON, 0);
				}
				System.exit(0);
			}

			break;
		case MotionEvent.ACTION_UP:
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
		// TODO Auto-generated method stub

	}

}
