package com.MilkPanda.view;

import com.MilkPanda.eatfish.MainActivity;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

public class BaseView extends SurfaceView implements SurfaceHolder.Callback, Runnable {
	protected int currentFrame; // 当前动画帧
	protected float scalex; // 背景图片的缩放比例
	protected float scaley;
	protected float screen_width; // 视图的宽度
	protected float screen_height; // 视图的高度
	protected boolean threadFlag; // 线程运行的标记
	protected Paint paint; // 画笔对象
	protected Canvas canvas; // 画布对象
	protected Thread thread; // 绘图线程
	protected SurfaceHolder sfh;
	protected MainActivity mainActivity;

	public BaseView(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
		this.mainActivity = (MainActivity) context;
		sfh = this.getHolder();
		sfh.addCallback(this);
		paint = new Paint();
	}
	
	// 线程运行的方法
	@Override
	public void run() {
		// TODO Auto-generated method stub

	}

	@Override
	public void surfaceCreated(SurfaceHolder holder) {
		// TODO Auto-generated method stub
		screen_width = this.getWidth(); // 获得视图的宽度
		screen_height = this.getHeight(); // 获得视图的高度
		threadFlag = true;
	}

	@Override
	public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
		// TODO Auto-generated method stub

	}

	@Override
	public void surfaceDestroyed(SurfaceHolder holder) {
		// TODO Auto-generated method stub
		threadFlag = false;
	}

	// 初始化图片资源方法
	public void initBitmap() {
	}

	// 释放图片资源的方法
	public void release() {
	}

	// 绘图方法
	public void drawSelf() {
	}

	public void setThreadFlag(boolean threadFlag) {
		this.threadFlag = threadFlag;
	}
}
