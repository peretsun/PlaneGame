package com.example.planegame.model.constants;


public interface Constants {
	public static final int GAME_MAP_MAX = 4;
	public static final int GAME_ALLPLANE_SUM = 11;
	public class WhatMessage{
		public static final int GOTO_WELLCOME_VIEW = 0;
		public static final int GOTO_MAIN_MENU_VIEW = 1;
		public static final int GOTO_GAME_VIEW = 2;
		public static final int GOTO_SELECT_PLANE_VIEW = 3;
		public static final int GOTO_SELECT_MAP_VIEW = 4;
		public static final int GOTO_SOUND_CONTORL_VIEW = 5;
		public static final int GOTO_RECORD_VIEW = 6;
		public static final int GOTO_HELP_VIEW = 7;
		public static final int GOTO_INFO_VIEW = 8;
		public static final int GOTO_SHOP_VIEW = 9;
		public static final int GOTO_WIN_VIEW = 10;
		public static final int GOTO_FAIL_VIEW = 11;
		//public static final int GOTO_CHALLENGEBOSS_VIEW = 10;
		public static final int OVER_GAME = 12;//ÓÎÏ·½áÊø
	}
	
	public class MilitaryRank{
		public static final int LIEBING = 0;
		public static final int SHANGDENGBING = 1;
		public static final int XIASHI = 2;
		public static final int ZHONGSHI = 3;
		public static final int SHANGSHI = 4;
		public static final int SHAOWEI = 5;
		public static final int ZHONGWEI = 6;
		public static final int SHANGWEI = 7;
		public static final int SHAOXIAO = 8;
		public static final int ZHONGXIAO = 9;
		public static final int SHANGXIAO = 10;
		public static final int DAXIAO = 11;
		public static final int SHAOJIANG = 12;
		public static final int ZHONGJIANG = 13;
		public static final int SHANGJIANG = 14;
	}
	
	public class MapName{
		public static final String MAP_0 = "¸ßË¹ÐÇ";
		public static final String MAP_1 = "»ô·òÂüÖ®ÐÇ";
		public static final String MAP_2 = "Í¼ÁéÐÇ";
		public static final String MAP_3 = "ÏÄÂå¿ËÐÇ";	
	}
	//·É»ú±àºÅ
	public class PlaneNumber{
		public static final int PLANE_NUMBER_0 = 0;
		public static final int PLANE_NUMBER_1 = 1;
		public static final int PLANE_NUMBER_2 = 2;
		public static final int PLANE_NUMBER_3 = 3;
		public static final int PLANE_NUMBER_4 = 4;
		public static final int PLANE_NUMBER_5 = 5;
		public static final int PLANE_NUMBER_6 = 6;
		public static final int PLANE_NUMBER_7 = 7;
		public static final int PLANE_NUMBER_8 = 8;
		public static final int PLANE_NUMBER_9 = 9;
		public static final int PLANE_NUMBER_10 = 10;
	}
	
	//·É»ú¼Û¸ñ
	public class PlaneCost{
		public static final int PLANE_NUMBER_0 = 10000;
		public static final int PLANE_NUMBER_1 = 12000;
		public static final int PLANE_NUMBER_2 = 14000;
		public static final int PLANE_NUMBER_3 = 16000;
		public static final int PLANE_NUMBER_4 = 18000;
		public static final int PLANE_NUMBER_5 = 20000;
		public static final int PLANE_NUMBER_6 = 25000;
		public static final int PLANE_NUMBER_7 = 30000;
		public static final int PLANE_NUMBER_8 = 35000;
		public static final int PLANE_NUMBER_9 = 40000;
		public static final int PLANE_NUMBER_10 = 50000;	
	}
	
	public class PlaneName{
		public static final String PLANE_NUMBER_0 = "¼ß¡ª11X";
		public static final String PLANE_NUMBER_1 = "¼ß¡ª12X";
		public static final String PLANE_NUMBER_2 = "¼ß¡ª13X";
		public static final String PLANE_NUMBER_3 = "¼ß¡ª14X";
		public static final String PLANE_NUMBER_4 = "¼ß¡ª15X";
		public static final String PLANE_NUMBER_5 = "¼ß¡ª16X";
		public static final String PLANE_NUMBER_6 = "ºä¡ª20X";
		public static final String PLANE_NUMBER_7 = "ºä¡ª21X";
		public static final String PLANE_NUMBER_8 = "ºä¡ª22X";
		public static final String PLANE_NUMBER_9 = "ºä¡ª23X";
		public static final String PLANE_NUMBER_10 = "ºä¡ª25X";
	}
	
	public class EnemyType{
		public static final int ENEMY1 = 1;
		public static final int ENEMY2 = 2;
		public static final int ENEMY3 = 3;
		public static final int ENEMY4 = 4;
		public static final int ENEMY5 = 5;
		public static final int ENEMY6 = 6;
		public static final int ENEMY7 = 7;
		public static final int ENEMY8 = 8;
		public static final int ENEMY9 = 9;
		public static final int ENEMY10 = 10;
		public static final int BOSS1 = 11;
		public static final int BOSS2 = 12;
		public static final int BOSS3 = 13;
		public static final int BOSS4 = 14;
	}
	
	public class SoundType{
		public static final int BACKGROUND_MUSIC = 0;
		public static final int BUTTON = 1;
		public static final int SHOOT = 2;
		public static final int EXPLOSION = 3;
		public static final int EXPLOSION2 = 4;
		public static final int EXPLOSION3 = 5;
		public static final int BIGEXPLOSION = 6;
		public static final int BOMB = 7;
		public static final int GETAWRAD = 8;
		public static final int HIT = 9;
	}
	
	public class DialogType{
		public static final int INPUT_USERNAME = 1;
		public static final int WIN = 2;
		public static final int FAIL = 3;
	}
}
