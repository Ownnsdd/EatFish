package com.MilkPanda.view;

import com.MilkPanda.eatfish.MainActivity;
import com.MilkPanda.eatfish.R;
import com.MilkPanda.constant.ConstantUtil;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Rect;
import android.view.MotionEvent;
import android.view.SurfaceHolder;

public class EndView extends BaseView {
	private int score;
	private float button_x;
	private float button_y;
	private float button_y2;
	private boolean isBtChange; // 按钮图片改变的标记
	private boolean isBtChange2;
	private Bitmap button; // 按钮图片
	private Bitmap button_on; // 按钮1按下
	private Bitmap button2; // 按钮图片
	private Bitmap button2_on; // 按钮2按下
	private Bitmap background; // 背景图片
	private MainActivity mainActivity;

	public EndView(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
		this.mainActivity = (MainActivity) context;
		thread = new Thread(this);
	}

	@Override
	public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
		// TODO Auto-generated method stub
		super.surfaceChanged(holder, format, width, height);
	}

	@Override
	public void surfaceCreated(SurfaceHolder holder) {
		// TODO Auto-generated method stub
		super.surfaceCreated(holder);
		initBitmap();
		if (thread.isAlive()) {
			thread.start();
		} else {
			thread = new Thread(this);
			thread.start();
		}
	}

	@Override
	public void surfaceDestroyed(SurfaceHolder holder) {
		// TODO Auto-generated method stub
		super.surfaceDestroyed(holder);
		release();
	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		if(event.getAction() == MotionEvent.ACTION_DOWN && event.getPointerCount() == 1){
			float x = event.getX();
			float y = event.getY();
			//判断第一个按钮是否被按下
			if(x > button_x && x < button_x + button.getWidth() 
					&& y > button_y && y < button_y + button.getHeight())
			{
				isBtChange = true;
				drawSelf();
				mainActivity.getHandler().sendEmptyMessage(ConstantUtil.TO_MAIN_VIEW);
			}
			//判断第二个按钮是否被按下
			else if(x > button_x && x < button_x + button.getWidth() 
					&& y > button_y2 && y < button_y2 + button.getHeight())
			{
				isBtChange2 = true;
				drawSelf();
				threadFlag = false;
				mainActivity.getHandler().sendEmptyMessage(ConstantUtil.END_GAME);
			}
			return true;
		}
		else if(event.getAction() == MotionEvent.ACTION_MOVE){
			float x = event.getX();
			float y = event.getY();
			if(x > button_x && x < button_x + button.getWidth() 
					&& y > button_y && y < button_y + button.getHeight())
			{
				isBtChange = true;
			}
			else{
				isBtChange = false;
			}
			if(x > button_x && x < button_x + button.getWidth() 
					&& y > button_y2 && y < button_y2 + button.getHeight())
			{
				isBtChange2 = true;
			}
			else{
				isBtChange2 = false;
			}
			return true;
		}
		else if(event.getAction() == MotionEvent.ACTION_UP){
			isBtChange = false;
			isBtChange2 = false;
			return true;
		}
		return false;
	}
	
	@Override
	public void initBitmap() {
		background = BitmapFactory.decodeResource(getResources(), R.drawable.menu);
		button = BitmapFactory.decodeResource(getResources(), R.drawable.start_1);
		button_on = BitmapFactory.decodeResource(getResources(), R.drawable.start_2);
		button2 = BitmapFactory.decodeResource(getResources(), R.drawable.exit_1);
		button2_on = BitmapFactory.decodeResource(getResources(), R.drawable.exit_2);
		scalex = screen_width / background.getWidth();
		scaley = screen_height / background.getHeight();
		button_x = screen_width / 2 - button.getWidth() / 2;
		button_y = screen_height / 2 + button.getHeight();
		button_y2 = button_y + button.getHeight();
	}

	@Override
	public void drawSelf() {
		try {
			canvas = sfh.lockCanvas();
			canvas.drawColor(Color.BLACK);
			canvas.save();
			canvas.scale(scalex, scaley, 0, 0);
			canvas.drawBitmap(background, 0, 0, paint);
			paint.setTextSize(40); 
			float textlong = paint.measureText("总分:"+String.valueOf(score));
			canvas.drawText("总分:"+String.valueOf(score), screen_width/2 - textlong/2, screen_height/2 - 100, paint);
			canvas.restore();
			if (isBtChange) {
				canvas.drawBitmap(button_on, button_x, button_y, paint);
			} else {
				canvas.drawBitmap(button, button_x, button_y, paint);
			}
			if (isBtChange2) {
				canvas.drawBitmap(button2_on, button_x, button_y2, paint);
			} else {
				canvas.drawBitmap(button2, button_x, button_y2, paint);
			}
		} catch (Exception err) {
			// TODO: handle exception
			err.printStackTrace();
		} finally {
			if (canvas != null)
				sfh.unlockCanvasAndPost(canvas);
		}
	}
	
	@Override
	public void run() {
		while (threadFlag) {
			long startTime = System.currentTimeMillis();
			drawSelf();
			long endTime = System.currentTimeMillis();
			try {
				if (endTime - startTime < 400)
					Thread.sleep(400 - (endTime - startTime));
			} catch (InterruptedException err) {
				err.printStackTrace();
			}
		}
	}
	
	public void setScore(int score) {
		this.score = score;
	}

}
