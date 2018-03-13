package com.example.planegame.model.game;

import java.util.HashMap;

import android.content.Context;
import android.media.AudioManager;
import android.media.SoundPool;

import com.example.planegame.R;
import com.example.planegame.control.activitys.MainActivity;
import com.example.planegame.model.constants.Constants;
/*音效播放类*/
public class GameSoundPool {
	private MainActivity mainActivity;
	private SoundPool soundPool;
	private HashMap<Integer,Integer>map;
	public GameSoundPool(MainActivity mainActivity){
		this.mainActivity = mainActivity;
		soundPool = new SoundPool(8,AudioManager.STREAM_MUSIC,0);
		map = new HashMap<Integer,Integer>();
	}
	//初始化系统音效
	public void initSound(){
		map.put(Constants.SoundType.BACKGROUND_MUSIC, soundPool.load(mainActivity, R.raw.back, 1));
		map.put(Constants.SoundType.BUTTON, soundPool.load(mainActivity, R.raw.button, 1));
		map.put(Constants.SoundType.SHOOT, soundPool.load(mainActivity, R.raw.shoot, 1));
		map.put(Constants.SoundType.HIT, soundPool.load(mainActivity, R.raw.hit, 1));
		map.put(Constants.SoundType.EXPLOSION, soundPool.load(mainActivity, R.raw.explosion, 1));
		map.put(Constants.SoundType.EXPLOSION2, soundPool.load(mainActivity, R.raw.explosion2, 1));
		map.put(Constants.SoundType.EXPLOSION3, soundPool.load(mainActivity, R.raw.explosion3, 1));
		map.put(Constants.SoundType.BIGEXPLOSION, soundPool.load(mainActivity, R.raw.bigexplosion, 1));
		map.put(Constants.SoundType.GETAWRAD, soundPool.load(mainActivity, R.raw.get_goods, 1));
		map.put(Constants.SoundType.BOMB, soundPool.load(mainActivity, R.raw.get_goods, 1));
	}
	//播放音效，loop=1循环播放
	public void playSound(int sound,int loop){
			AudioManager am = (AudioManager)mainActivity.getSystemService(Context.AUDIO_SERVICE);
			float stramVolumeCurrent = am.getStreamVolume(AudioManager.STREAM_MUSIC);
			float stramMaxVolumeCurrent = am.getStreamMaxVolume(AudioManager.STREAM_MUSIC);
			float volume = stramVolumeCurrent/stramMaxVolumeCurrent;
			soundPool.play(map.get(sound), volume, volume, 1, loop, 1.0f);
	}
	//释放资源
	public void release(){
		if(soundPool != null)
			soundPool.release();
	}
	//停止播放音效
	public void stopSound(int streamID){
		soundPool.pause(streamID);
	}	
	
}
