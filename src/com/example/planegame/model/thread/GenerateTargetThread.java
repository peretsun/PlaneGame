package com.example.planegame.model.thread;

import java.util.Random;

import com.example.planegame.model.game.Enemy;
import com.example.planegame.view.GameSurfaceView;

public class GenerateTargetThread extends Thread{
	GameSurfaceView gameView;
	private int currentTime;
	private int currentMap;
	
	public GenerateTargetThread(GameSurfaceView gameView,int currentMap){
		this.gameView = gameView;
		currentTime = 0;
		this.currentMap = currentMap;
	}
	public void run(){
		while(!gameView.isGameOver){
			if(gameView.isPlay){
				try {
					map1();
					Thread.sleep(1000);
					currentTime++;
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	}
	
	private void createEnemy(int map){
		switch(map){
		case 1:
			map1();
			break;
		case 2:
			map2();
			break;
		case 3:
			map3();
			break;
		case 4:
			map4();
			break;
		}
	}
	
	private void map1(){
		if(gameView.getActivity().currentPattem == 0){
			if(currentTime < 10){
				Enemy e = new Enemy(gameView, 1,1);
				new Thread(e).start();
				gameView.allEnemy.add(e);
			}else if(currentTime < 20){
				int enemyType = new Random().nextInt(2)+1;
				Enemy e = new Enemy(gameView, enemyType,1);
				new Thread(e).start();
				gameView.allEnemy.add(e);
			}else if(currentTime == 20){
				Enemy e = new Enemy(gameView, 6,1);
				new Thread(e).start();
				gameView.allEnemy.add(e);
			}else if(currentTime < 40){
				int enemyType = new Random().nextInt(2)+1;
				Enemy e = new Enemy(gameView, enemyType,1);
				new Thread(e).start();
				gameView.allEnemy.add(e);
			}else if(currentTime == 40){
				Enemy e = new Enemy(gameView, 7,1);
				new Thread(e).start();
				gameView.allEnemy.add(e);
			}else if(currentTime < 60){
				int enemyType = new Random().nextInt(2)+1;
				Enemy e = new Enemy(gameView, enemyType,1);
				new Thread(e).start();
				gameView.allEnemy.add(e);
			}else if(currentTime == 60){
				gameView.allEnemy.add(gameView.currentBoss);//boss
				new Thread(gameView.currentBoss).start();
			}else{
				Enemy e = new Enemy(gameView, 1,1);
				new Thread(e).start();
				gameView.allEnemy.add(e);
			}
		}else{
			if(currentTime == 1){
				gameView.allEnemy.add(gameView.currentBoss);//boss
				new Thread(gameView.currentBoss).start();
			}
		}
	}
	private void map2(){
		if(gameView.getActivity().currentPattem == 0){
			if(currentTime < 10){
				int enemyType = new Random().nextInt(3)+1;
				Enemy e = new Enemy(gameView, enemyType,2);
				new Thread(e).start();
				gameView.allEnemy.add(e);
			}else if(currentTime < 20){
				int enemyType = new Random().nextInt(2)+6;
				Enemy e = new Enemy(gameView, enemyType,2);
				new Thread(e).start();
				gameView.allEnemy.add(e);
			}else if(currentTime == 20){
				Enemy e = new Enemy(gameView, 7,2);
				new Thread(e).start();
				gameView.allEnemy.add(e);
			}else if(currentTime < 40){
				int enemyType = new Random().nextInt(4)+1;
				Enemy e = new Enemy(gameView, enemyType,2);
				new Thread(e).start();
				gameView.allEnemy.add(e);
			}else if(currentTime == 40){
				Enemy e = new Enemy(gameView, 8,2);
				new Thread(e).start();
				gameView.allEnemy.add(e);
			}else if(currentTime < 60){
				int enemyType = new Random().nextInt(3)+1;
				Enemy e = new Enemy(gameView, enemyType,2);
				new Thread(e).start();
				gameView.allEnemy.add(e);
			}else if(currentTime == 60){
				new Thread(gameView.currentBoss).start();
				gameView.allEnemy.add(gameView.currentBoss);//boss
			}else{
				int enemyType = new Random().nextInt(3)+1;
				Enemy e = new Enemy(gameView, enemyType,2);
				new Thread(e).start();
				gameView.allEnemy.add(e);
			}
		}else{
			if(currentTime == 3){
				gameView.allEnemy.add(gameView.currentBoss);//boss
				new Thread(gameView.currentBoss).start();
			}
		}
	}
	private void map3(){
		if(gameView.getActivity().currentPattem == 0){
			if(currentTime < 10){
				int enemyType = new Random().nextInt(4)+1;
				Enemy e = new Enemy(gameView, enemyType,3);
				new Thread(e).start();
				gameView.allEnemy.add(e);
			}else if(currentTime < 20){
				int enemyType = new Random().nextInt(2)+6;
				Enemy e = new Enemy(gameView, enemyType,3);
				new Thread(e).start();
				gameView.allEnemy.add(e);
			}else if(currentTime == 20){
				Enemy e = new Enemy(gameView, 8,3);
				new Thread(e).start();
				gameView.allEnemy.add(e);
			}else if(currentTime < 40){
				int enemyType = new Random().nextInt(4)+1;
				Enemy e = new Enemy(gameView, enemyType,3);
				new Thread(e).start();
				gameView.allEnemy.add(e);
			}else if(currentTime == 40){
				Enemy e = new Enemy(gameView, 9,3);
				new Thread(e).start();
				gameView.allEnemy.add(e);
			}else if(currentTime < 60){
				int enemyType = new Random().nextInt(4)+1;
				Enemy e = new Enemy(gameView, enemyType,3);
				new Thread(e).start();
				gameView.allEnemy.add(e);
			}else if(currentTime == 60){
				new Thread(gameView.currentBoss).start();
				gameView.allEnemy.add(gameView.currentBoss);//boss
			}else{
				int enemyType = new Random().nextInt(4)+1;
				Enemy e = new Enemy(gameView, enemyType,3);
				new Thread(e).start();
				gameView.allEnemy.add(e);
			}
		}else{
			if(currentTime == 3){
				gameView.allEnemy.add(gameView.currentBoss);//boss
				new Thread(gameView.currentBoss).start();
			}
		}
	}
	private void map4(){
		if(gameView.getActivity().currentPattem == 0){
			if(currentTime < 10){
				int enemyType = new Random().nextInt(5)+1;
				Enemy e = new Enemy(gameView, enemyType,4);
				new Thread(e).start();
				gameView.allEnemy.add(e);
			}else if(currentTime < 20){
				int enemyType = new Random().nextInt(3)+6;
				Enemy e = new Enemy(gameView, enemyType,4);
				new Thread(e).start();
				gameView.allEnemy.add(e);
			}else if(currentTime == 20){
				Enemy e = new Enemy(gameView, 9,4);
				new Thread(e).start();
				gameView.allEnemy.add(e);
			}else if(currentTime < 40){
				int enemyType = new Random().nextInt(5)+1;
				Enemy e = new Enemy(gameView, enemyType,4);
				new Thread(e).start();
				gameView.allEnemy.add(e);
			}else if(currentTime == 40){
				Enemy e = new Enemy(gameView,10,4);
				new Thread(e).start();
				gameView.allEnemy.add(e);
			}else if(currentTime < 60){
				int enemyType = new Random().nextInt(4)+1;
				Enemy e = new Enemy(gameView, enemyType,4);
				new Thread(e).start();
				gameView.allEnemy.add(e);
			}else if(currentTime == 60){
				new Thread(gameView.currentBoss).start();
				gameView.allEnemy.add(gameView.currentBoss);//boss
			}else{
				int enemyType = new Random().nextInt(5)+1;
				Enemy e = new Enemy(gameView, enemyType,4);
				new Thread(e).start();
				gameView.allEnemy.add(e);
			}
		}else{
			if(currentTime == 3){
				gameView.allEnemy.add(gameView.currentBoss);//boss
				new Thread(gameView.currentBoss).start();
			}
		}
	}
	
	public int getCurrentTime() {
		return currentTime;
	}
	
}
