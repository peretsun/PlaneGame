package com.example.planegame.model.utils;

public class GameUtils {
	public static boolean isPointInRect//一个点是否在矩形内（包括边界）
	(
			float pointX,float pointY,
			float x,float y,float width,float height
	)
	{
		if(
				pointX < x||pointY < y||pointX > x+width||pointY > y+height
		  )
		  {
			  return false;
		  }
		return true;
	}	
	
	public static boolean isTwoRectCollsion//判断两个矩形是否发生碰撞（包括边界）
	(
			float x1,float y1,int width1,int height1,
			float x2,float y2,int width2,int height2
	)
	{
		if( x1 + width1 < x2 || y1 + height1 < y2||
			x2 + width2 < x1 || y2 + height2 < y1	
		  )
		  {
			  return false;
		  }
		return true;
	}	
}
