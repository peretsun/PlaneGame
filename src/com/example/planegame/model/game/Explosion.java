package com.example.planegame.model.game;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;

import com.example.planegame.view.GameSurfaceView;

public class Explosion {
	private GameSurfaceView gameView;
	private Bitmap[] explosions;//爆炸组图
	private float nowX;//播放爆炸组图x坐标
	private float nowY;
	private int animIndex;//爆炸动画下标索引
	
	public Explosion(GameSurfaceView gameView,Bitmap[] explosions,float x,float y)
	{
		this.gameView=gameView;
		this.explosions = explosions;
		this.nowX = x;
		this.nowY = y;
	}
	//绘制背景的方法
	public void drawSelf(Canvas canvas,Paint paint)
	{
		canvas.drawBitmap(explosions[animIndex], nowX, nowY,paint);
		if(animIndex < explosions.length-1)//如果没有播放完
		{
			animIndex++;
		}
		else//如果爆炸动画完毕
		{
			gameView.allExplosion.remove(this);//从列表中删除此爆炸
		}
	}
}
