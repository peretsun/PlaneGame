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
import com.example.planegame.model.constants.Constants;
import com.example.planegame.model.game.MyButton;

public class SoundControlSurfaceView extends SurfaceView implements
		SurfaceHolder.Callback {
	private MainActivity activity;
	private Paint paint;
	private Bitmap bgBitmap;
	private Bitmap text_bgBitmap;
	private Bitmap text_soundBitmap;
	private Bitmap btn_OnBitmap;
	private Bitmap btn_OffBitmap;

	private MyButton btnBgMusic;
	private MyButton btnSound;
	private int screenWidth;
	private int screenHeight;

	private Thread drawThread;

	public SoundControlSurfaceView(MainActivity activity) {
		super(activity);
		this.activity = activity;
		paint = new Paint();
		paint.setAntiAlias(true);
		this.requestFocus();
		this.setFocusableInTouchMode(true);
		getHolder().addCallback(this);
	}

	private void initBitmap() {
		bgBitmap = BitmapFactory.decodeResource(activity.getResources(),
				R.drawable.bg_soundcontrol);
		bgBitmap = Bitmap.createScaledBitmap(bgBitmap, screenWidth,
				screenHeight, false);

		btn_OnBitmap = BitmapFactory.decodeResource(activity.getResources(),
				R.drawable.btn_sound_on);
		btn_OffBitmap = BitmapFactory.decodeResource(activity.getResources(),
				R.drawable.btn_sound_off);
		text_bgBitmap = BitmapFactory.decodeResource(activity.getResources(),
				R.drawable.text_bg);
		text_soundBitmap = BitmapFactory.decodeResource(
				activity.getResources(), R.drawable.text_sound);
	}

	private void initMyBotton() {
		if (activity.backGroundMusicFlag) {
			btnBgMusic = new MyButton(btn_OnBitmap, screenWidth * 2 / 3
					- btn_OnBitmap.getWidth() / 2, screenHeight * 2 / 5
					- text_bgBitmap.getHeight() / 2);
		} else {
			btnBgMusic = new MyButton(btn_OffBitmap, screenWidth * 2 / 3
					- btn_OnBitmap.getWidth() / 2, screenHeight * 2 / 5
					- text_bgBitmap.getHeight() / 2);
		}
		if (activity.soundFlag) {
			btnSound = new MyButton(btn_OnBitmap, screenWidth * 2 / 3
					- btn_OnBitmap.getWidth() / 2, screenHeight * 3 / 5
					- text_soundBitmap.getHeight() / 2);
		} else {
			btnSound = new MyButton(btn_OffBitmap, screenWidth * 2 / 3
					- btn_OnBitmap.getWidth() / 2, screenHeight * 3 / 5
					- text_soundBitmap.getHeight() / 2);
		}
	}

	public void onDrawSelf(Canvas canvas) {
		canvas.drawBitmap(bgBitmap, 0, 0, paint);
		// 画“背景”两个字图片
		canvas.drawBitmap(text_bgBitmap,
				screenWidth / 3 - text_bgBitmap.getWidth() / 2, screenHeight
						* 2 / 5 - text_bgBitmap.getHeight() / 2, paint);
		btnBgMusic.drawSelf(canvas, paint);

		// 画“音效”两个字图片
		canvas.drawBitmap(text_soundBitmap,
				screenWidth / 3 - text_soundBitmap.getWidth() / 2, screenHeight
						* 3 / 5 - text_soundBitmap.getHeight() / 2, paint);

		btnSound.drawSelf(canvas, paint);
	}

	@Override
	public void surfaceCreated(SurfaceHolder holder) {
		screenWidth = this.getWidth();
		screenHeight = this.getHeight();
		initBitmap();
		initMyBotton();
		drawThread = new Thread() {
			public void run() {
				SurfaceHolder myholder = SoundControlSurfaceView.this
						.getHolder();
				Canvas canvas = myholder.lockCanvas();
				try {
					synchronized (myholder) {
						onDrawSelf(canvas);
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
		};
		drawThread.start();
	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		int x = (int) event.getX();
		int y = (int) event.getY();
		switch (event.getAction()) {
		case MotionEvent.ACTION_DOWN:
			// 点击在哪个按钮上开启哪个按钮
			if (btnBgMusic.isActionOnButton(x, y)) {
				if(activity.soundFlag){
					activity.gameSound.playSound(Constants.SoundType.BUTTON, 0);
				}
				
				if (btnBgMusic.getBtnBitmap() == btn_OnBitmap) {
					activity.mMediaPlayer.pause();
					activity.backGroundMusicFlag = false;
					btnBgMusic.setBtnBitmap(btn_OffBitmap);
				} else {
					activity.mMediaPlayer.start();
					activity.backGroundMusicFlag = true;
					btnBgMusic.setBtnBitmap(btn_OnBitmap);
				}

			} else if (btnSound.isActionOnButton(x, y)) {
				if(activity.soundFlag){
					activity.gameSound.playSound(Constants.SoundType.BUTTON, 0);
				}
				
				if (btnSound.getBtnBitmap() == btn_OnBitmap) {
					activity.soundFlag = false;
					btnSound.setBtnBitmap(btn_OffBitmap);
				} else {
					activity.soundFlag = true;
					btnSound.setBtnBitmap(btn_OnBitmap);
				}
			}
			break;
		case MotionEvent.ACTION_UP:
			// 抬起时关掉所有按钮
			// startGameBtn.switchOff();
			// soundBtn.switchOff();
			// highScoreBtn.switchOff();
			break;
		}
		SurfaceHolder myholder = SoundControlSurfaceView.this.getHolder();
		Canvas canvas = myholder.lockCanvas();
		try {
			synchronized (myholder) {
				onDrawSelf(canvas);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (canvas != null) {
				myholder.unlockCanvasAndPost(canvas);
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
