package com.example.planegame.model;

import java.util.ArrayList;
import java.util.List;

public class UserInfo {
	private String name;//用户名
	private int military;//军衔级别
	private int money;//金币数
	private List<Integer> ownPlanes = new ArrayList<Integer>();//已拥有的飞机型号
	private int map;//已解锁的地图关卡
	
	public UserInfo(){
		
	}
	public UserInfo(String name,int military,int money,List<Integer> ownPlanes,int map){
		this.name = name;
		this.money = money;
		this.ownPlanes = ownPlanes;
		this.military = military;
		this.map = map;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getMilitary() {
		return military;
	}
	public void setMilitary(int military) {
		this.military = military;
	}
	public int getMoney() {
		return money;
	}
	public void setMoney(int money) {
		this.money = money;
	}
	public List<Integer> getOwnPlanes() {
		return ownPlanes;
	}
	public void setOwnPlane(List<Integer> ownPlanes) {
		this.ownPlanes = ownPlanes;
	}
	public int getMap() {
		return map;
	}
	public void setMap(int map) {
		this.map = map;
	}
	
	public void addPlanes(int planeNumber){
		ownPlanes.add(planeNumber);
	}
	@Override
	public String toString() {
		return "UserInfo [name=" + name + ", military=" + military + ", money="
				+ money + ", ownPlanes=" + ownPlanes + ", map=" + map + "]";
	}
	
}
