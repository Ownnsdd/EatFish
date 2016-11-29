package com.MilkPanda.object;

import java.util.Random;
import com.MilkPanda.eatfish.R;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;

public class SmallFish extends EnemyFish {
	private static int currentCount = 0;
	public static int sumCount = 8;
	private Bitmap smallFish;

	public SmallFish(Resources resources) {
		super(resources);
		// TODO Auto-generated constructor stub
		this.score = 1;
		this.plus_size = 1;
	}

	@Override
	public void initial(int SpeedTime, float arg1, float arg2) {
		isAlive = true; 
		size = 0;
		Random random = new Random();
		speed = random.nextInt(8) + 8 * SpeedTime;
		object_x = -object_width * (currentCount * 2 + 1);
		object_y = random.nextInt((int) (screen_height - object_height));
		currentCount++;
		if (currentCount >= sumCount)
			currentCount = 0;
	}

	@Override
	protected void initBitmap() {
		smallFish = BitmapFactory.decodeResource(resources, R.drawable.small);
		object_height = smallFish.getHeight();
		object_width = smallFish.getWidth();
	}

	@Override
	public void drawSelf(Canvas canvas) {
		if (isAlive) {
			if (isVisible) {
				canvas.save();
				canvas.drawBitmap(smallFish, object_x, object_y, paint);
				canvas.restore();
			}
			logic();
		}
	}
	
	@Override
	public void release() {
		if(!smallFish.isRecycled())
			smallFish.recycle();
	}
	
}
