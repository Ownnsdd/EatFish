package com.MilkPanda.object;

import java.util.List;

import com.MilkPanda.interfaces.IMyFish;
import com.MilkPanda.eatfish.R;
import com.MilkPanda.factory.GameObjectFactory;
import com.MilkPanda.view.MainView;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;

public class MyFish extends GameObject implements IMyFish {
	private float middle_x; // 飞机的中心坐标
	private float middle_y;
	private long startTime; // 开始的时间
	private long endTime; // 结束的时间
	private int size;
	private Bitmap myfish;
	private MainView mainView;
	private GameObjectFactory factory;

	public MyFish(Resources resources) {
		super(resources);
		this.size = 1;
		initBitmap();
		this.speed = 8;
		factory = new GameObjectFactory();
	}

	public void setMainView(MainView mainView) {
		this.mainView = mainView;
	}

	@Override
	public void setScreenWH(float screen_width, float screen_height) {
		super.setScreenWH(screen_width, screen_height);
		object_x = screen_width / 2 - object_width / 2;
		object_y = screen_height - object_height;
		middle_x = object_x + object_width / 2;
		middle_y = object_y + object_height / 2;
	}
	
	
	@Override
	public float getMiddle_x() {
		// TODO Auto-generated method stub
		return middle_x;
	}

	@Override
	public void setMiddle_x(float middle_x) {
		// TODO Auto-generated method stub
		this.middle_x = middle_x;
		this.object_x = middle_x - object_width/2;
	}

	@Override
	public float getMiddle_y() {
		// TODO Auto-generated method stub
		return middle_y;
	}

	@Override
	public void setMiddle_y(float middle_y) {
		// TODO Auto-generated method stub
		this.middle_y = middle_y;
		this.object_y = middle_y - object_height/2;
	}

	@Override
	public boolean Eat(Canvas canvas, List<EnemyFish> fishes) {
//		for(EnemyFish obj:fishes){
//			if(obj.isCanCollide()){
//				if(obj.isCollide())
//			}
//		}
		return false;
	}

	@Override
	protected void initBitmap() {
		// TODO Auto-generated method stub
		myfish = BitmapFactory.decodeResource(resources, R.drawable.myfish);
		object_width = myfish.getWidth();
		object_height = myfish.getHeight();
	}

	@Override
	public void drawSelf(Canvas canvas) {
		// TODO Auto-generated method stub
		if(isAlive){
			canvas.save();
			canvas.drawBitmap(myfish, object_x, object_y, paint);
			canvas.restore();
		}
	}

	@Override
	public void release() {
		// TODO Auto-generated method stub
		if(!myfish.isRecycled()){
			myfish.recycle();
		}
	}

	public void setStartTime(long startTime) {
		this.startTime = startTime;
	}
	
	public void plus_size(EnemyFish enemyFish){
		this.size += enemyFish.plus_size;
	}
	
	public int getSize() {
		return size;
	}
}
