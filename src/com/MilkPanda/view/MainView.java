package com.MilkPanda.view;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import com.MilkPanda.factory.GameObjectFactory;
import com.MilkPanda.object.BigFish;
import com.MilkPanda.object.EnemyFish;
import com.MilkPanda.object.GameObject;
import com.MilkPanda.object.MiddleFish;
import com.MilkPanda.object.MyFish;
import com.MilkPanda.object.SmallFish;
import com.MilkPanda.constant.ConstantUtil;
import com.MilkPanda.eatfish.R;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.os.Message;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;

public class MainView extends BaseView {
	private GameObjectFactory factory;
	private List<EnemyFish> enemyFish;
	private int speedTime;
	private boolean isPlay;
	private boolean isTouchFish; // 判断玩家是否按下屏幕
	private Bitmap background; // 背景图片
	private Bitmap playButton; // 开始/暂停游戏的按钮图片
	private MyFish myFish;
	private int sumScore;

	public MainView(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
		isPlay = true;
		speedTime = 1;
		factory = new GameObjectFactory();
		enemyFish = new ArrayList<EnemyFish>();
		myFish = (MyFish) factory.createMyFish(getResources());
		myFish.setMainView(this);
		for (int i = 0; i < SmallFish.sumCount; i++) {
			SmallFish smallFish = (SmallFish) factory.createSmallFish(getResources());
			enemyFish.add(smallFish);
		}
		for (int i = 0; i < MiddleFish.sumCount; i++) {
			MiddleFish middleFish = (MiddleFish) factory.createMiddleFish(getResources());
			enemyFish.add(middleFish);
		}
		for (int i = 0; i < BigFish.sumCount; i++) {
			BigFish bigFish = (BigFish) factory.createBigFish(getResources());
			enemyFish.add(bigFish);
		}
		thread = new Thread(this);
	}

	public void initObject() {
		for (EnemyFish obj : enemyFish) {
			if (obj instanceof SmallFish) {
				if (!obj.isAlive()) {
					obj.initial(speedTime, 0, 0);
					break;
				}
			} else if (obj instanceof MiddleFish) {
				if (!obj.isAlive()) {
					obj.initial(speedTime, 0, 0);
					break;
				}
			} else if (obj instanceof BigFish) {
				if (!obj.isAlive()) {
					obj.initial(speedTime, 0, 0);
					break;
				}
			}
		}
	}

	@Override
	public void initBitmap() {
		background = BitmapFactory.decodeResource(getResources(), R.drawable.menu);
		scalex = screen_width / background.getWidth();
		scaley = screen_height / background.getHeight();
	}

	@Override
	public void release() {
		for (GameObject obj : enemyFish) {
			obj.release();
		}
		myFish.release();
		if (!background.isRecycled())
			background.recycle();
	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		if (event.getAction() == MotionEvent.ACTION_UP) {
			isTouchFish = false;
			return true;
		} else if (event.getAction() == MotionEvent.ACTION_DOWN) {
			float x = event.getX();
			float y = event.getY();
			// if (x > 10 && x < 10 + play_bt_w && y > 10 && y < 10 + play_bt_h)
			// {
			// if (isPlay) {
			// isPlay = false;
			// } else {
			// isPlay = true;
			// synchronized (thread) {
			// thread.notify();
			// }
			// }
			// return true;
			// }
			if (x > myFish.getObject_x() && x < myFish.getObject_x() + myFish.getObject_width()
					&& y > myFish.getObject_y() && y < myFish.getObject_y() + myFish.getObject_height()) {
				if (isPlay) {
					isTouchFish = true;
				}
				return true;
			}
		}
		// 响应手指在屏幕移动的事件
		else if (event.getAction() == MotionEvent.ACTION_MOVE && event.getPointerCount() == 1) {
			// 判断触摸点是否为玩家的飞机
			if (isTouchFish) {
				float x = event.getX();
				float y = event.getY();
				if (x > myFish.getMiddle_x() + 20) {
					if (myFish.getMiddle_x() + myFish.getSpeed() <= screen_width) {
						myFish.setMiddle_x(myFish.getMiddle_x() + myFish.getSpeed());
					}
				} else if (x < myFish.getMiddle_x() - 20) {
					if (myFish.getMiddle_x() - myFish.getSpeed() >= 0) {
						myFish.setMiddle_x(myFish.getMiddle_x() - myFish.getSpeed());
					}
				}
				if (y > myFish.getMiddle_y() + 20) {
					if (myFish.getMiddle_y() + myFish.getSpeed() <= screen_height) {
						myFish.setMiddle_y(myFish.getMiddle_y() + myFish.getSpeed());
					}
				} else if (y < myFish.getMiddle_y() - 20) {
					if (myFish.getMiddle_y() - myFish.getSpeed() >= 0) {
						myFish.setMiddle_y(myFish.getMiddle_y() - myFish.getSpeed());
					}
				}
				return true;
			}
		}
		return false;
	}

	@Override
	public void drawSelf() {
		// TODO Auto-generated method stub
		try {
			canvas = sfh.lockCanvas();
			canvas.drawColor(Color.BLACK);
			canvas.save();
			canvas.scale(scalex, scaley, 0, 0);
			canvas.drawBitmap(background, 0, 0, paint);
			canvas.restore();
			for (EnemyFish obj : enemyFish) {
				if (obj.isAlive()) {
					obj.drawSelf(canvas);
					if (obj.isCanCollide() && myFish.isAlive()) {
						if (obj.isCollide(myFish)) {
							if (obj.getSize() > myFish.getSize())
								myFish.setAlive(false);
							else {
								obj.setAlive(false);
								myFish.plus_size(obj);
								addScore(obj);
							}
						}
					}
				}
			}

			if (!myFish.isAlive()) {
				threadFlag = false;
			}
			myFish.drawSelf(canvas);

		} catch (Exception err) {
			err.printStackTrace();
		} finally {
			if (canvas != null)
				sfh.unlockCanvasAndPost(canvas);
		}
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		while (threadFlag) {
			long startTime = System.currentTimeMillis();
			initObject();
			drawSelf();
			long endTime = System.currentTimeMillis();
			if (!isPlay) {
				synchronized (thread) {
					try {
						thread.wait();
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			}
			try {
				if (endTime - startTime < 10)
					Thread.sleep(10 - (endTime - startTime));
			} catch (InterruptedException err) {
				err.printStackTrace();
			}
		}
		try {
			Thread.sleep(100);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Message message = new Message();
		message.what = ConstantUtil.TO_END_VIEW;
		message.arg1 = Integer.valueOf(sumScore);
		mainActivity.getHandler().sendMessage(message);
	}

	@Override
	public void surfaceCreated(SurfaceHolder holder) {
		// TODO Auto-generated method stub
		super.surfaceCreated(holder);
		initBitmap();
		for (GameObject obj : enemyFish) {
			obj.setScreenWH(screen_width, screen_height);
		}
		myFish.setScreenWH(screen_width, screen_height);
		myFish.setAlive(true);
		if (thread.isAlive()) {
			thread.start();
		} else {
			thread = new Thread(this);
			thread.start();
		}
	}

	@Override
	public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
		// TODO Auto-generated method stub
		super.surfaceChanged(holder, format, width, height);
	}

	@Override
	public void surfaceDestroyed(SurfaceHolder holder) {
		// TODO Auto-generated method stub
		super.surfaceDestroyed(holder);
		release();
	}

	private void addScore(EnemyFish enemyFish) {
		// TODO Auto-generated method stub
		sumScore += enemyFish.getScore();
	}
}
