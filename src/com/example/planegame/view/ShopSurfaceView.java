package com.example.planegame.view;

import java.lang.reflect.Field;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.planegame.R;
import com.example.planegame.control.activitys.MainActivity;
import com.example.planegame.control.dialogs.MyDialog;
import com.example.planegame.model.constants.Constants;
import com.example.planegame.model.game.MyButton;

public class ShopSurfaceView extends SurfaceView implements
		SurfaceHolder.Callback {
	private MainActivity activity;
	private Bitmap bgBitmap;
	private Bitmap[] plane = new Bitmap[Constants.GAME_ALLPLANE_SUM];
	private Bitmap[] btn_shopBitmap = new Bitmap[2];//1已拥有图片，0购买

	private MyButton[] btnShop = new MyButton[Constants.GAME_ALLPLANE_SUM];
	private MyButton btnLastPage;
	private MyButton btnNextPage;

	private Paint paint;

	private int screenWidth;
	private int screenHeight;
	private int currentPage;
	private int pageMax;
	
	//private boolean flag = true;

	public ShopSurfaceView(MainActivity activity) {
		super(activity);
		this.activity = activity;
		paint = new Paint();
		paint.setAntiAlias(true);

		pageMax = Constants.GAME_ALLPLANE_SUM / 6+1;
		this.requestFocus();
		this.setFocusableInTouchMode(true);
		getHolder().addCallback(this);
	}

	private void initBitmap() {
		bgBitmap = BitmapFactory.decodeResource(activity.getResources(),
				R.drawable.bg_shop);
		bgBitmap = Bitmap.createScaledBitmap(bgBitmap, screenWidth,
				screenHeight, false);

		btn_shopBitmap[0] = BitmapFactory.decodeResource(
				activity.getResources(), R.drawable.btn_shop0);
		btn_shopBitmap[1] = BitmapFactory.decodeResource(
				activity.getResources(), R.drawable.btn_shop1);

		// 根据需要获取资源id
		for (int i = 0; i < Constants.GAME_ALLPLANE_SUM; i++) {
			try {
				Field field = R.drawable.class.getDeclaredField("shop_plane"
						+ i);
				int resourceId = Integer.parseInt(field.get(null).toString());
				plane[i] = BitmapFactory.decodeResource(
						activity.getResources(), resourceId);
			} catch (NoSuchFieldException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
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
		}
	}

	private void initMyButton() {
		Bitmap btn_lastBitmap = BitmapFactory.decodeResource(
				activity.getResources(), R.drawable.btn_last);
		btnLastPage = new MyButton(btn_lastBitmap, screenWidth / 4
				- btn_lastBitmap.getWidth() / 2, screenHeight * 9 / 10);
		Bitmap btn_nextBitmap = BitmapFactory.decodeResource(
				activity.getResources(), R.drawable.btn_next);
		btnNextPage = new MyButton(btn_nextBitmap, screenWidth * 3 / 4
				- btn_nextBitmap.getWidth() / 2, screenHeight * 9 / 10);
	}

	private void onDrawMyButton(Canvas canvas, Paint paint) {
		btnLastPage.drawSelf(canvas, paint);
		btnNextPage.drawSelf(canvas, paint);
	}

	private void onDrawPlane(Canvas canvas, Paint paint) {
		for (int row = 1, i = (currentPage - 1) * 6; (i < Constants.GAME_ALLPLANE_SUM)
				&& (row < 4); i = i + 2, row++) {

			canvas.drawBitmap(plane[i], screenWidth / 4 - plane[i].getWidth()
					/ 2, screenHeight * row / 4 - plane[i].getHeight() / 2,
					paint);
			// 如果当前飞机已拥有怎么画已拥有的按钮，否则画立即购买按钮
			if (activity.userInfo.getOwnPlanes().contains(i)) {
				btnShop[i] = new MyButton(btn_shopBitmap[1], screenWidth
						/ 4 - btn_shopBitmap[1].getWidth() / 2,
						screenHeight * row / 4 + plane[i].getHeight() / 2
						+ 5);
				
				btnShop[i].drawSelf(canvas, paint);
			} else {
				btnShop[i] = new MyButton(btn_shopBitmap[0],
						screenWidth / 4- btn_shopBitmap[0].getWidth() / 2, screenHeight * row / 4
						+ plane[i].getHeight() / 2 + 5);
				btnShop[i].drawSelf(canvas, paint);
			}
			if(i+1 >= Constants.GAME_ALLPLANE_SUM){
				return;
			}
			canvas.drawBitmap(plane[i + 1],
					screenWidth * 3 / 4 - plane[i].getWidth() / 2, screenHeight
							* row / 4 - plane[i].getHeight() / 2, paint);

			// 如果当前飞机已拥有怎么画已拥有的按钮，否则画立即购买按钮
			if (activity.userInfo.getOwnPlanes().contains(i + 1)) {
				btnShop[i + 1] = new MyButton(btn_shopBitmap[1],
						screenWidth * 3 / 4 - btn_shopBitmap[1].getWidth()
						/ 2, screenHeight * row / 4
						+ plane[i].getHeight() / 2 + 5);
				
				btnShop[i + 1].drawSelf(canvas, paint);

			} else {
				btnShop[i + 1] = new MyButton(btn_shopBitmap[0],
							screenWidth * 3 / 4 - btn_shopBitmap[0].getWidth()
							/ 2, screenHeight * row / 4
							+ plane[i].getHeight() / 2 + 5);
					
				btnShop[i + 1].drawSelf(canvas, paint);
			}
		}
	}

	public void onDrawSelf(Canvas canvas) {
		canvas.drawBitmap(bgBitmap, 0, 0, paint);
		// 画翻页按钮
		onDrawMyButton(canvas, paint);
		// 画飞机与购买按钮
		onDrawPlane(canvas, paint);
	}

	@Override
	public void surfaceCreated(SurfaceHolder holder) {
		screenWidth = getWidth();
		screenHeight = getHeight();
		currentPage = 1;
		initBitmap();
		initMyButton();

		new Thread() {
			@Override
			public void run() {
				SurfaceHolder myholder = ShopSurfaceView.this.getHolder();
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
			if (btnLastPage.isActionOnButton(x, y)) {
				if(activity.soundFlag){
					activity.gameSound.playSound(Constants.SoundType.BUTTON, 0);
				}
				
				if (currentPage == 1) {
					Toast.makeText(activity, "这已经是第一页了", Toast.LENGTH_SHORT)
							.show();
				} else {
					currentPage--;
					SurfaceHolder myholder = ShopSurfaceView.this.getHolder();
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
			} else if (btnNextPage.isActionOnButton(x, y)) {
				if(activity.soundFlag){
					activity.gameSound.playSound(Constants.SoundType.BUTTON, 0);
				}
				
				if (currentPage == pageMax) {
					Toast.makeText(activity, "这已经是最后一页了", Toast.LENGTH_SHORT)
							.show();
				} else {
					currentPage++;
					SurfaceHolder myholder = ShopSurfaceView.this.getHolder();
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
			} else {
				for ( int i = (currentPage - 1) * 6; i < Constants.GAME_ALLPLANE_SUM
						&& i < (currentPage - 1) * 6 + 6; i++) {
					if (btnShop[i] != null && btnShop[i].isActionOnButton(x, y)
							&& btnShop[i].getBtnBitmap() == btn_shopBitmap[0]) {
						System.out.println("点击购买飞机"+i);
						if(activity.soundFlag){
							activity.gameSound.playSound(Constants.SoundType.BUTTON, 0);
						}
						final MyDialog myDialog =  new MyDialog(activity, i, btnShop);
						myDialog.show();
						Button ok = (Button) myDialog.findViewById(R.id.btn_ok_buy_plane);
						Button clear = (Button) myDialog.findViewById(R.id.btn_clear_buy_plane);
						final int planeNumber = i;
						ok.setOnClickListener(
								 new View.OnClickListener() {
									@Override
									public void onClick(View v) {
										// TODO Auto-generated method stub
										if(buyPlane(planeNumber)){
											btnShop[planeNumber].setBtnBitmap(btn_shopBitmap[1]);
											SurfaceHolder myholder = ShopSurfaceView.this.getHolder();
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
											myDialog.dismiss();
										}
									}
								});
								
								clear.setOnClickListener(
										 new View.OnClickListener() {
											@Override
											public void onClick(View v) {
												myDialog.dismiss();
											}
								});
					}
				}
			}
		}
		return true;
	}

	private boolean buyPlane(int planeNumber){
		boolean b = false;
		switch(planeNumber){
		case Constants.PlaneNumber.PLANE_NUMBER_0:
			if(activity.userInfo.getMoney() >= Constants.PlaneCost.PLANE_NUMBER_0){
				activity.userInfo.setMoney(activity.userInfo.getMoney() - Constants.PlaneCost.PLANE_NUMBER_0);
				activity.dbManager.updateUserInfo(activity.userInfo);
				activity.dbManager.insertOwnPlanes(planeNumber);
				activity.userInfo.getOwnPlanes().add(planeNumber);
				Toast.makeText(activity, "购买成功", Toast.LENGTH_SHORT).show();
				b = true;
			}else{
				Toast.makeText(activity, "金币不够", Toast.LENGTH_SHORT).show();
			}
			break;
		case Constants.PlaneNumber.PLANE_NUMBER_1:
			if(activity.userInfo.getMoney() >= Constants.PlaneCost.PLANE_NUMBER_1){
				activity.userInfo.setMoney(activity.userInfo.getMoney() - Constants.PlaneCost.PLANE_NUMBER_1);
				activity.dbManager.updateUserInfo(activity.userInfo);
				activity.dbManager.insertOwnPlanes(planeNumber);
				activity.userInfo.getOwnPlanes().add(planeNumber);
				Toast.makeText(activity, "购买成功", Toast.LENGTH_SHORT).show();
				b = true;
			}else{
				Toast.makeText(activity, "金币不够", Toast.LENGTH_SHORT).show();
			}
			break;
		case Constants.PlaneNumber.PLANE_NUMBER_2:
			if(activity.userInfo.getMoney() >= Constants.PlaneCost.PLANE_NUMBER_2){
				activity.userInfo.setMoney(activity.userInfo.getMoney() - Constants.PlaneCost.PLANE_NUMBER_2);
				activity.dbManager.updateUserInfo(activity.userInfo);
				activity.dbManager.insertOwnPlanes(planeNumber);
				activity.userInfo.getOwnPlanes().add(planeNumber);
				Toast.makeText(activity, "购买成功", Toast.LENGTH_SHORT).show();
				b = true;
			}else{
				Toast.makeText(activity, "金币不够", Toast.LENGTH_SHORT).show();
			}
			break;
		case Constants.PlaneNumber.PLANE_NUMBER_3:
			if(activity.userInfo.getMoney() >= Constants.PlaneCost.PLANE_NUMBER_3){
				activity.userInfo.setMoney(activity.userInfo.getMoney() - Constants.PlaneCost.PLANE_NUMBER_3);
				activity.dbManager.updateUserInfo(activity.userInfo);
				activity.dbManager.insertOwnPlanes(planeNumber);
				activity.userInfo.getOwnPlanes().add(planeNumber);
				Toast.makeText(activity, "购买成功", Toast.LENGTH_SHORT).show();
				b = true;
			}else{
				Toast.makeText(activity, "金币不够", Toast.LENGTH_SHORT).show();
			}
			break;
		case Constants.PlaneNumber.PLANE_NUMBER_4:
			if(activity.userInfo.getMoney() >= Constants.PlaneCost.PLANE_NUMBER_4){
				activity.userInfo.setMoney(activity.userInfo.getMoney() - Constants.PlaneCost.PLANE_NUMBER_4);
				activity.dbManager.updateUserInfo(activity.userInfo);
				activity.dbManager.insertOwnPlanes(planeNumber);
				activity.userInfo.getOwnPlanes().add(planeNumber);
				Toast.makeText(activity, "购买成功", Toast.LENGTH_SHORT).show();
				b = true;
			}else{
				Toast.makeText(activity, "金币不够", Toast.LENGTH_SHORT).show();
			}
			break;
		case Constants.PlaneNumber.PLANE_NUMBER_5:
			if(activity.userInfo.getMoney() >= Constants.PlaneCost.PLANE_NUMBER_5){
				activity.userInfo.setMoney(activity.userInfo.getMoney() - Constants.PlaneCost.PLANE_NUMBER_5);
				activity.dbManager.updateUserInfo(activity.userInfo);
				activity.dbManager.insertOwnPlanes(planeNumber);
				activity.userInfo.getOwnPlanes().add(planeNumber);
				Toast.makeText(activity, "购买成功", Toast.LENGTH_SHORT).show();
				b = true;
			}else{
				Toast.makeText(activity, "金币不够", Toast.LENGTH_SHORT).show();
			}
			break;
		case Constants.PlaneNumber.PLANE_NUMBER_6:
			if(activity.userInfo.getMoney() >= Constants.PlaneCost.PLANE_NUMBER_6){
				activity.userInfo.setMoney(activity.userInfo.getMoney() - Constants.PlaneCost.PLANE_NUMBER_6);
				activity.dbManager.updateUserInfo(activity.userInfo);
				activity.dbManager.insertOwnPlanes(planeNumber);
				activity.userInfo.getOwnPlanes().add(planeNumber);
				Toast.makeText(activity, "购买成功", Toast.LENGTH_SHORT).show();
				b = true;
			}else{
				Toast.makeText(activity, "金币不够", Toast.LENGTH_SHORT).show();
			}
			break;
		case Constants.PlaneNumber.PLANE_NUMBER_7:
			if(activity.userInfo.getMoney() >= Constants.PlaneCost.PLANE_NUMBER_7){
				activity.userInfo.setMoney(activity.userInfo.getMoney() - Constants.PlaneCost.PLANE_NUMBER_7);
				activity.dbManager.updateUserInfo(activity.userInfo);
				activity.dbManager.insertOwnPlanes(planeNumber);
				activity.userInfo.getOwnPlanes().add(planeNumber);
				Toast.makeText(activity, "购买成功", Toast.LENGTH_SHORT).show();
				b = true;
			}else{
				Toast.makeText(activity, "金币不够", Toast.LENGTH_SHORT).show();
			}
			break;
		case Constants.PlaneNumber.PLANE_NUMBER_8:
			if(activity.userInfo.getMoney() >= Constants.PlaneCost.PLANE_NUMBER_8){
				activity.userInfo.setMoney(activity.userInfo.getMoney() - Constants.PlaneCost.PLANE_NUMBER_8);
				activity.dbManager.updateUserInfo(activity.userInfo);
				activity.dbManager.insertOwnPlanes(planeNumber);
				activity.userInfo.getOwnPlanes().add(planeNumber);
				Toast.makeText(activity, "购买成功", Toast.LENGTH_SHORT).show();
				b = true;
			}else{
				Toast.makeText(activity, "金币不够", Toast.LENGTH_SHORT).show();
			}
			break;
		case Constants.PlaneNumber.PLANE_NUMBER_9:
			if(activity.userInfo.getMoney() >= Constants.PlaneCost.PLANE_NUMBER_9){
				activity.userInfo.setMoney(activity.userInfo.getMoney() - Constants.PlaneCost.PLANE_NUMBER_9);
				activity.dbManager.updateUserInfo(activity.userInfo);
				activity.dbManager.insertOwnPlanes(planeNumber);
				activity.userInfo.getOwnPlanes().add(planeNumber);
				Toast.makeText(activity, "购买成功", Toast.LENGTH_SHORT).show();
				b = true;
			}else{
				Toast.makeText(activity, "金币不够", Toast.LENGTH_SHORT).show();
			}
			break;
		case Constants.PlaneNumber.PLANE_NUMBER_10:
			if(activity.userInfo.getMoney() >= Constants.PlaneCost.PLANE_NUMBER_10){
				activity.userInfo.setMoney(activity.userInfo.getMoney() - Constants.PlaneCost.PLANE_NUMBER_10);
				activity.dbManager.updateUserInfo(activity.userInfo);
				activity.dbManager.insertOwnPlanes(planeNumber);
				activity.userInfo.getOwnPlanes().add(planeNumber);
				Toast.makeText(activity, "购买成功", Toast.LENGTH_SHORT).show();
				b = true;
			}else{
				Toast.makeText(activity, "金币不够", Toast.LENGTH_SHORT).show();
			}
			break;
		}
		
		return b;
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
