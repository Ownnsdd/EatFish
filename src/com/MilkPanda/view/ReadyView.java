package com.MilkPanda.view;

import com.MilkPanda.eatfish.R;
import com.MilkPanda.constant.ConstantUtil;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Rect;
import android.view.MotionEvent;
import android.view.SurfaceHolder;

public class ReadyView extends BaseView {
	private float fish_x; // 图片的坐标
	private float fish_y;
	private float fish_height;
	private float text_x;
	private float text_y;
	private float button_x;
	private float button_y;
	private float button_y2;
	private boolean isBtChange; // 按钮图片改变的标记
	private boolean isBtChange2;
	private Bitmap text; // 文字图片
	private Bitmap button; // 按钮图片
	private Bitmap button_on; // 按钮1按下
	private Bitmap button2; // 按钮图片
	private Bitmap button2_on; // 按钮2按下
	private Bitmap background; // 背景图片

	public ReadyView(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
		paint.setTextSize(40);
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
		if (event.getAction() == MotionEvent.ACTION_DOWN
				&& event.getPointerCount() == 1) {
			float x = event.getX();
			float y = event.getY();
			//判断第一个按钮是否被按下
			if (x > button_x && x < button_x + button.getWidth()
					&& y > button_y && y < button_y + button.getHeight()) {
				isBtChange = true;
				drawSelf();
				mainActivity.getHandler().sendEmptyMessage(ConstantUtil.TO_MAIN_VIEW);
			}
			//判断第二个按钮是否被按下
			else if (x > button_x && x < button_x + button.getWidth()
					&& y > button_y2 && y < button_y2 + button.getHeight()) {
				isBtChange2 = true;
				drawSelf();
				mainActivity.getHandler().sendEmptyMessage(ConstantUtil.END_GAME);
			}
			return true;
		} 
		//响应屏幕单点移动的消息
		else if (event.getAction() == MotionEvent.ACTION_MOVE) {
			float x = event.getX();
			float y = event.getY();
			if (x > button_x && x < button_x + button.getWidth()
					&& y > button_y && y < button_y + button.getHeight()) {
				isBtChange = true;
			} else {
				isBtChange = false;
			}
			if (x > button_x && x < button_x + button.getWidth()
					&& y > button_y2 && y < button_y2 + button.getHeight()) {
				isBtChange2 = true;
			} else {
				isBtChange2 = false;
			}
			return true;
		} 
		//响应手指离开屏幕的消息
		else if (event.getAction() == MotionEvent.ACTION_UP) {
			isBtChange = false;
			isBtChange2 = false;
			return true;
		}
		return false;
	}

	@Override
	public void initBitmap() {
		background = BitmapFactory.decodeResource(getResources(), R.drawable.menu);
		text = BitmapFactory.decodeResource(getResources(), R.drawable.text);
		button = BitmapFactory.decodeResource(getResources(), R.drawable.start_1);
		button_on = BitmapFactory.decodeResource(getResources(), R.drawable.start_2);
		button2 = BitmapFactory.decodeResource(getResources(), R.drawable.exit_1);
		button2_on = BitmapFactory.decodeResource(getResources(), R.drawable.exit_2);
		scalex = screen_width / background.getWidth();
		scaley = screen_height / background.getHeight();
		text_x = screen_width / 2 - text.getWidth() / 2;
		text_y = screen_height / 2 - text.getHeight()/2;
		button_x = screen_width / 2 - button.getWidth() / 2;
		button_y = screen_height / 2 + button.getHeight();
		button_y2 = button_y + button.getHeight();
	}

	@Override
	public void release() {
		if (!text.isRecycled()) {
			text.recycle();
		}
		if (!button.isRecycled()) {
			button.recycle();
		}
		if (!button2.isRecycled()) {
			button2.recycle();
		}
		if (!background.isRecycled()) {
			background.recycle();
		}
	}

	@Override
	public void drawSelf() {
		try {
			canvas = sfh.lockCanvas();
			canvas.drawColor(Color.BLACK);
			canvas.save();
			canvas.scale(scalex, scaley, 0, 0);
			canvas.drawBitmap(background, 0, 0, paint);
			canvas.restore();
			canvas.drawBitmap(text, text_x, text_y, paint);
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
			long starTime = System.currentTimeMillis();
			drawSelf();
			long endTime = System.currentTimeMillis();
			try {
				if (endTime - starTime < 400)
					Thread.sleep(400 - (endTime - starTime));
			} catch (InterruptedException err) {
				// TODO: handle exception
				err.printStackTrace();
			}
		}
	}
}
