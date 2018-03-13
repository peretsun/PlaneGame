package com.example.planegame.view;

import java.util.Map;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.widget.Toast;

import com.example.planegame.R;
import com.example.planegame.control.activitys.MainActivity;
import com.example.planegame.model.constants.Constants;
import com.example.planegame.model.db.DBManager;
import com.example.planegame.model.game.MyButton;

public class RecordSurfaceView extends SurfaceView implements
		SurfaceHolder.Callback {
	private MainActivity activity;
	private Bitmap bgBitmap;
	private Bitmap[] numberBitmap = new Bitmap[10];
	private Paint paint;

	private MyButton btn_lastPage;
	private MyButton btn_nextPage;

	private int pageMax;
	private int currentPage;
	private int rowWidth;// 每行记录之间的宽度
	private int generalStartX;
	private int challengeStartX;

	private int screenWidth;
	private int screenHeight;

	public RecordSurfaceView(MainActivity activity) {
		super(activity);
		this.activity = activity;
		currentPage = 1;
		paint = new Paint();
		paint.setAntiAlias(true);
		this.requestFocus();
		this.setFocusableInTouchMode(true);
		getHolder().addCallback(this);
	}

	public void initCoords() {
		rowWidth = 40;
		generalStartX = screenWidth / 2 - 30;
		challengeStartX = screenWidth * 3 / 4 - 30;
	}

	private void initBitmap() {
		bgBitmap = BitmapFactory.decodeResource(activity.getResources(),
				R.drawable.bg_record);
		bgBitmap = Bitmap.createScaledBitmap(bgBitmap, screenWidth,
				screenHeight, false);

		Bitmap numbersBitmap = BitmapFactory.decodeResource(
				activity.getResources(), R.drawable.numbers);

		for (int i = 0; i < 10; i++) {
			numberBitmap[i] = Bitmap.createBitmap(numbersBitmap,
					numbersBitmap.getWidth() * i / 10, 0,
					numbersBitmap.getWidth() / 10, numbersBitmap.getHeight());
		}
	}

	private void initButton() {
		Bitmap btn_lastBitmap = BitmapFactory.decodeResource(
				activity.getResources(), R.drawable.btn_last);
		btn_lastPage = new MyButton(btn_lastBitmap, screenWidth / 4 - 25,
				screenHeight * 3 / 4);

		Bitmap btn_nextBitmap = BitmapFactory.decodeResource(
				activity.getResources(), R.drawable.btn_next);
		btn_nextPage = new MyButton(btn_nextBitmap, screenWidth * 3 / 4 - 25,
				screenHeight * 3 / 4);
	}

	private void onDrawMyButton(Canvas canvas, Paint paint) {
		btn_lastPage.drawSelf(canvas, paint);
		btn_nextPage.drawSelf(canvas, paint);
	}

	private void onDrawNumber(Canvas canvas, int num, int startX, int startY) {
		String numStr = num + "";
		for (int i = 0; i < numStr.length(); i++) {
			canvas.drawBitmap(numberBitmap[numStr.charAt(i) - '0'], startX+numberBitmap[0].getWidth()*i,
					startY, paint);
		}
	}

	private void onDrawRecord(Canvas canvas) {
		Map<String, Integer> recordMap = null;
		// 每页最多显示4条记录
		for (int i = (currentPage - 1) * 4 + 1; i < (currentPage - 1) * 4 + 5; i++) {
			recordMap = activity.dbManager.queryRecords(i);
			if (recordMap != null) {
				// 画关卡
				canvas.drawBitmap(numberBitmap[i], screenWidth / 4 - 10,
						screenHeight / 4 + i * rowWidth, paint);
				// 画普通模式记录
				onDrawNumber(canvas, recordMap.get(DBManager.GENERAL),
						generalStartX, screenHeight / 4 + i * rowWidth);
				onDrawNumber(canvas, recordMap.get(DBManager.CHALLENGE),
						challengeStartX, screenHeight / 4 + i * rowWidth);
				//canvas.drawText("秒",challengeStartX+(recordMap.get(DBManager.CHALLENGE)+"").length()*numberBitmap[0].getWidth(), screenHeight / 4 + i * rowWidth, paint);
			} else {
				pageMax = (i - 1) / 4 + 1;
				return;
			}
		}
	}

	public void onDrawSelf(Canvas canvas) {
		canvas.drawBitmap(bgBitmap, 0, 0, paint);
		onDrawRecord(canvas);
		onDrawMyButton(canvas, paint);
	}

	@Override
	public void surfaceCreated(SurfaceHolder holder) {
		screenWidth = getWidth();
		screenHeight = getHeight();
		initBitmap();
		initButton();
		initCoords();
		new Thread() {
			public void run() {
				SurfaceHolder myholder = RecordSurfaceView.this.getHolder();
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
		}.start();

	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		int x = (int) event.getX();
		int y = (int) event.getY();
		switch (event.getAction()) {
		case MotionEvent.ACTION_DOWN:
			if (btn_lastPage.isActionOnButton(x, y)) {
				if (currentPage == 1) {
					Toast.makeText(activity, "这已经是第一页了",Toast.LENGTH_SHORT).show();
				} else {
					currentPage--;
				}
			} else if (btn_nextPage.isActionOnButton(x, y)) {
				if (currentPage == pageMax) {
					Toast.makeText(activity, "这已经是最后一页了",Toast.LENGTH_SHORT).show();
				} else {
					currentPage++;
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
