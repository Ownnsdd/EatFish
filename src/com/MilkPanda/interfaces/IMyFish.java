package com.MilkPanda.interfaces;

import java.util.List;

import com.MilkPanda.object.EnemyFish;

import android.graphics.Canvas;

public interface IMyFish {
	public float getMiddle_x();
	public void setMiddle_x(float middle_x);
	public float getMiddle_y();
	public void setMiddle_y(float middle_y);
	public boolean Eat(Canvas canvas, List<EnemyFish> fishes);
}
