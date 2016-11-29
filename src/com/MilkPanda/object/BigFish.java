package com.MilkPanda.object;

import java.util.Random;

import com.MilkPanda.eatfish.R;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;

public class BigFish extends EnemyFish{
	private static int currentCount = 0;
	public static int sumCount = 2;
	private Bitmap bigFish;

	public BigFish(Resources resources) {
		super(resources);
		// TODO Auto-generated constructor stub
		this.score = 4;
		this.plus_size = 3;
	}

	@Override
	public void initial(int SpeedTime, float arg1, float arg2) {
		isAlive = true;
		size = 80;
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
		bigFish = BitmapFactory.decodeResource(resources, R.drawable.big);
		object_height = bigFish.getHeight();
		object_width = bigFish.getWidth();
	}

	@Override
	public void drawSelf(Canvas canvas) {
		if (isAlive) {
			if (isVisible) {
				canvas.save();
				canvas.drawBitmap(bigFish, object_x, object_y, paint);
				canvas.restore();
			}
			logic();
		}
	}
	
	@Override
	public void release() {
		if(!bigFish.isRecycled())
			bigFish.recycle();
	}
}
