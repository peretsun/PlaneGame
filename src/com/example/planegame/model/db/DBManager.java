package com.example.planegame.model.db;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.widget.Toast;

import com.example.planegame.model.UserInfo;

public class DBManager {

	public static final String TABLE1_NAME = "userinfo";
	public static final String USER_NAME = "username";//第一个表的主键参数 
	public static final String USER_MILITARY = "military";
	public static final String USER_MONEY = "money";
	public static final String USER_MAP = "map";
	
	public static final String TABLE2_NAME = "ownplanes";
	public static final String PLANENUMBER = "planenumber";//第二个表的主键参数 表示已拥有飞机的编码
	
	
	public static final String TABLE3_NAME = "records";
	public static final String GUANQIA = "guanqia";//第三个表的主键参数 表示每一个关卡
	public static final String GENERAL = "general";//普通模式的最高纪录，如果为NULL说明当前关卡未解锁，为0表示为通关
	public static final String CHALLENGE = "challenge";
	
	
	
	private DBHelper helper;
	private SQLiteDatabase db;
	private Context context;
	
	public DBManager(Context context){
		this.context = context;
		helper = new DBHelper(context);
		db = helper.getWritableDatabase();
	}
	
	public DBManager(Context context,String dbName){
		this.context = context;
		helper = new DBHelper(context,dbName);
		db = helper.getWritableDatabase();
	}
	
	public DBManager(Context context,String dbName,int version){
		helper = new DBHelper(context,dbName,version);
		db = helper.getWritableDatabase();
	}
	
	public void closeDB(){
		if(db != null){
			db.close();
			helper.close();
		}
	}
	
	public void insertUserInfo(UserInfo userInfo){
		if(userInfo == null){
			Toast.makeText(context, "userInfo不能为空", Toast.LENGTH_SHORT).show();
			return ;
		}
		
		ContentValues userValue = new ContentValues();
		userValue.put(USER_MILITARY, userInfo.getMilitary());
		userValue.put(USER_MONEY, userInfo.getMoney());
		userValue.put(USER_NAME, userInfo.getName());
		userValue.put(USER_MAP, userInfo.getMap());
		db.insert(TABLE1_NAME, null, userValue);
		
		for(int planeNumber : userInfo.getOwnPlanes()){
			insertOwnPlanes(planeNumber);
		}
	}
	
	public UserInfo queryUserInfo(){
		Cursor cursor = db.query(TABLE1_NAME, new String[]{USER_NAME,USER_MILITARY,USER_MONEY,USER_MAP}, null, 
				null, null, null, null);
		if(cursor != null && cursor.getCount() > 0){
			cursor.moveToFirst();
			UserInfo userInfo = new UserInfo();
			userInfo.setName(cursor.getString(0));
			userInfo.setMilitary(cursor.getInt(1));
			userInfo.setMoney(cursor.getInt(2));
			userInfo.setMap(cursor.getInt(3));
			
			userInfo.setOwnPlane(queryOwnplanes());
			cursor.close();
			return userInfo;
		}
		else{
			cursor.close();
			return null;
		}
	}
	
	
	public void updateUserInfo(UserInfo userInfo){
		ContentValues userValue = new ContentValues();
		userValue.put(USER_NAME, userInfo.getName());
		userValue.put(USER_MILITARY, userInfo.getMilitary());
		userValue.put(USER_MONEY, userInfo.getMoney());
		userValue.put(USER_MAP, userInfo.getMap());
		//第一个参数表名
		//第二个参数需要修改的值
		//第三个参数相当于where，可以有占位符?    null更新所有行
		//第四个参数当有占位符时,new String[]{}其中第一个给第一个占位符
		//db.u
		db.update(TABLE1_NAME,userValue,null,null);
	}
	
	public void deleteUserInfo(){
		db.delete(TABLE1_NAME, null, null);
	}

	
	public void insertOwnPlanes(int planeNumber){
		if(planeNumber < 0){
			Toast.makeText(context, "planeNumber不能小于0", Toast.LENGTH_SHORT).show();
			return ;
		}
		
		ContentValues planeValues = new ContentValues();
		planeValues.put(PLANENUMBER, planeNumber);
		db.insert(TABLE2_NAME, null, planeValues);
	}
	
	/*
	public void updateOwnPlanes(String userName,int planeNumber){
		ContentValues planeValues = new ContentValues();
		planeValues.put(USER_NAME, userName);
		planeValues.put(PLANENUMBER, planeNumber);
		db.update(TABLE2_NAME,planeValues,"USER_NAME=" + userName, null);
	}
	*/
	
	public List<Integer> queryOwnplanes(){
		List<Integer> list = null;
		Cursor cursor = db.query(TABLE2_NAME, new String[]{PLANENUMBER}, null , 
				null, null, null, null);
		if(cursor != null && cursor.getCount() > 0){
			cursor.moveToFirst();
			list = new ArrayList<Integer>();
			for(int i = 0; i < cursor.getCount();i++){
				list.add(cursor.getInt(0));
				cursor.moveToNext();
			}
			cursor.close();
			return list;
		}
		cursor.close();
		return null;
	}
	
	public void deleteOwnPlanes(){
		db.delete(TABLE2_NAME, null, null);
	}
	
	public void insertRecords(int guanqia,int general,int challenge){
		if(guanqia < 1){
			Toast.makeText(context, "guanqia不能小于1",100).show();
			return ;
		}
		ContentValues values = new ContentValues();
		values.put(GUANQIA, guanqia);
		values.put(GENERAL, general);
		values.put(CHALLENGE, challenge);
		db.insert(TABLE3_NAME, null, values);
	}
	public void updateRecordsGaneral(int guanqia,int general){
		System.out.println("updateRecordsGaneral");
		ContentValues values = new ContentValues();
		//values.put(GUANQIA, guanqia);
		values.put(GENERAL, general);
		db.update(TABLE3_NAME, values, GUANQIA +"="+guanqia, null);
	}
	
	public void deleteRecords(){
		db.delete(TABLE3_NAME, null, null);
	}
	
	public void updateRecordsChallenge(int guanqia,int challenge){
		System.out.println("updateRecordsChallenge");
		ContentValues values = new ContentValues();
		//values.put(GUANQIA, guanqia);
		values.put(CHALLENGE, challenge);
		db.update(TABLE3_NAME, values, GUANQIA +"="+guanqia, null);
	}
	
	public Map<String ,Integer> queryRecords(int guanqia){
		System.out.println("queryRecords");
		Map<String ,Integer> hashMap = null;
		Cursor cursor = db.query(TABLE3_NAME, new String[]{GENERAL,CHALLENGE},GUANQIA+"="+guanqia, 
				null, null, null, null);
		if(cursor != null && cursor.getCount() > 0){
			hashMap = new HashMap<String ,Integer>();
			cursor.moveToFirst();
			hashMap.put(GENERAL, cursor.getInt(0));
			hashMap.put(CHALLENGE, cursor.getInt(1));
			cursor.close();
			return hashMap;
		}
		cursor.close();
		return hashMap;
	}
	
}
