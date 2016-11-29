package com.MilkPanda.factory;

import com.MilkPanda.object.BigFish;
import com.MilkPanda.object.GameObject;
import com.MilkPanda.object.MiddleFish;
import com.MilkPanda.object.MyFish;
import com.MilkPanda.object.SmallFish;

import android.content.res.Resources;

public class GameObjectFactory {
	public GameObject createSmallFish(Resources resources){
		return new SmallFish(resources);
	}
	
	public GameObject createMiddleFish(Resources resources){
		return new MiddleFish(resources);
	}
	
	public GameObject createBigFish(Resources resources){
		return new BigFish(resources);
	}
	
	public GameObject createMyFish(Resources resources){
		return new MyFish(resources);
	}
}
