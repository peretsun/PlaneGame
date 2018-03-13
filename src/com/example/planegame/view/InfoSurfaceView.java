package com.example.planegame.view;

import java.util.List;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import com.example.planegame.R;
import com.example.planegame.control.activitys.MainActivity;
import com.example.planegame.model.constants.Constants;
import com.example.planegame.model.db.DBHelper;

public class InfoSurfaceView extends SurfaceView implements SurfaceHolder.Callback{
	private MainActivity activity;
	private Paint paint;
	private Bitmap bgBitmap;
	private Bitmap bgTextBitmap;
	private Bitmap militaryBitmap;
	
	
	private Bitmap[] numberBitmap = new Bitmap[10];
	
	private List<Bitmap> planes;//已拥有的飞机集合
	private int guanqia;//已解锁关卡
	private int military;//军衔等级
	private int moneySum;//拥有的金币数
	private int screenWidth;
	private int screenHeight;
	

	public InfoSurfaceView(MainActivity activity) {
		super(activity);
		this.activity = activity;
		paint = new Paint();
		paint.setAntiAlias(true);
		getHolder().addCallback(this);
	}
	
	public void initBitmap(){
		bgBitmap = BitmapFactory.decodeResource(activity.getResources(), R.drawable.bg_info);
		bgBitmap = Bitmap.createScaledBitmap(bgBitmap, screenWidth, screenHeight, false);
		Bitmap numbers = BitmapFactory.decodeResource(activity.getResources(), R.drawable.numbers);
		//拆分数字图片，分别将0-9存入numbers图片数组
		for(int i = 0;i < 10; i++){
			numberBitmap[i] = Bitmap.createBitmap(numbers, numbers.getWidth()*i/10, 0, numbers.getWidth()/10, numbers.getHeight()/10);
		}	
		bgTextBitmap = BitmapFactory.decodeResource(activity.getResources(), R.drawable.bg_text_info);
		bgTextBitmap = Bitmap.createScaledBitmap(bgTextBitmap, screenWidth*2/3, screenHeight, false);
	}
	
	public void onDrawNumber(Canvas canvas ,int number,int startX,int startY,Paint paint){
		String numberStr = number+"";
		int numberBitmapWidth = numberBitmap[0].getWidth();
		for(int i = 0;i < numberStr.length();i++){
			canvas.drawBitmap(numberBitmap[numberStr.charAt(i)-'0'],
					startX+i*numberBitmapWidth, 
					startY, 
					paint
					);
		}
	}
	
	public void onDrawMilitary(Canvas canvas ,int military,int startX,int startY,Paint paint){
		switch(military){
		case Constants.MilitaryRank.LIEBING:
			canvas.drawText("列兵", startX, startY, paint);
			break;
		case Constants.MilitaryRank.SHANGDENGBING:
			canvas.drawText("上等兵", startX, startY, paint);
			break;
		case Constants.MilitaryRank.XIASHI:
			canvas.drawText("下士", startX, startY, paint);
			break;
		case Constants.MilitaryRank.ZHONGSHI:
			canvas.drawText("中士", startX, startY, paint);
			break;
		case Constants.MilitaryRank.SHANGSHI:
			canvas.drawText("上士", startX, startY, paint);
			break;
		case Constants.MilitaryRank.SHAOWEI:
			canvas.drawText("少尉", startX, startY, paint);
			break;
		case Constants.MilitaryRank.ZHONGWEI:
			canvas.drawText("中尉", startX, startY, paint);
			break;
		case Constants.MilitaryRank.SHANGWEI:
			canvas.drawText("上尉", startX, startY, paint);
			break;
		case Constants.MilitaryRank.SHAOXIAO:
			canvas.drawText("少校", startX, startY, paint);
			break;
		case Constants.MilitaryRank.ZHONGXIAO:
			canvas.drawText("中校", startX, startY, paint);
			break;
		case Constants.MilitaryRank.SHANGXIAO:
			canvas.drawText("上校", startX, startY, paint);
			break;
		case Constants.MilitaryRank.DAXIAO:
			canvas.drawText("大校", startX, startY, paint);
			break;
		case Constants.MilitaryRank.SHAOJIANG:
			canvas.drawText("少将", startX, startY, paint);
			break;
		case Constants.MilitaryRank.ZHONGJIANG:
			canvas.drawText("中将", startX, startY, paint);
			break;
		case Constants.MilitaryRank.SHANGJIANG:
			canvas.drawText("上将", startX, startY, paint);
			break;
		}
	}
	
	public void onDrawSelf(Canvas canvas){
		canvas.drawBitmap(bgBitmap, 0, 0, paint);
		paint.setColor(Color.BLACK);
		paint.setTextSize(20);
		canvas.drawText("名字:", screenWidth/3, screenHeight*3/8, paint);
		canvas.drawText("军衔:", screenWidth/3, screenHeight/2, paint);
		canvas.drawText("金币:", screenWidth/3, screenHeight*5/8, paint);
		
		canvas.drawText(activity.userInfo.getName(), screenWidth/2, screenHeight*3/8, paint);
		onDrawMilitary(canvas, activity.userInfo.getMilitary(), screenWidth/2, screenHeight/2,paint);
		canvas.drawText(activity.userInfo.getMoney()+"", screenWidth/2, screenHeight*5/8,paint);
	}

	@Override
	public void surfaceCreated(SurfaceHolder holder) {
		screenWidth = this.getWidth();
		screenHeight = this.getHeight();
		initBitmap();
		new Thread(){
			@Override
			public void run() {
				SurfaceHolder myholder=InfoSurfaceView.this.getHolder();
				Canvas canvas = myholder.lockCanvas();//获取画布
				try{
					synchronized(myholder){
						onDrawSelf(canvas);//绘制
					}
				}catch(Exception e){
					e.printStackTrace();
				}finally{
					if(canvas != null){
						myholder.unlockCanvasAndPost(canvas);
					}
				}
				try{
	            	Thread.sleep(100);//睡眠指定毫秒数
	            }
	            catch(Exception e){
	            	e.printStackTrace();//打印堆栈信息
	            }
				
			}
			
		}.start();
		
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
