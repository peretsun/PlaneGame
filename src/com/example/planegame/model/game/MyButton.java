package com.example.planegame.model.game;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;

import com.example.planegame.model.utils.GameUtils;

public class MyButton {
	//该button的x,y坐标
	private int x;
	private int y;
	private int width;
	private int height;
	private Bitmap btnBitmap;
	private boolean isOn;//是否被按下
	
	public MyButton(Bitmap bitmap,int x,int y){
		this.btnBitmap = bitmap;
		this.x = x;
		this.y = y;
		width = btnBitmap.getWidth();
		height = btnBitmap.getHeight();
	}
	
	public void drawSelf(Canvas canvas,Paint paint){
		canvas.drawBitmap(btnBitmap, x, y, paint);
	}

	//判断按钮是否有触控事件的方法
	public boolean isActionOnButton(int pressX,int pressY)
	{
			return GameUtils.isPointInRect(pressX, pressY, x, y, width, height);
	}
	
	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}

	public boolean isOn() {
		return isOn;
	}

	public void setOn(boolean isOn) {
		this.isOn = isOn;
	}

	public Bitmap getBtnBitmap() {
		return btnBitmap;
	}

	public void setBtnBitmap(Bitmap btnBitmap) {
		this.btnBitmap = btnBitmap;
	}
	
	

}
