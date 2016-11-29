package com.MilkPanda.view;

import com.MilkPanda.eatfish.MainActivity;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

public class BaseView extends SurfaceView implements SurfaceHolder.Callback, Runnable {
	protected int currentFrame; // ��ǰ����֡
	protected float scalex; // ����ͼƬ�����ű���
	protected float scaley;
	protected float screen_width; // ��ͼ�Ŀ��
	protected float screen_height; // ��ͼ�ĸ߶�
	protected boolean threadFlag; // �߳����еı��
	protected Paint paint; // ���ʶ���
	protected Canvas canvas; // ��������
	protected Thread thread; // ��ͼ�߳�
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
	
	// �߳����еķ���
	@Override
	public void run() {
		// TODO Auto-generated method stub

	}

	@Override
	public void surfaceCreated(SurfaceHolder holder) {
		// TODO Auto-generated method stub
		screen_width = this.getWidth(); // �����ͼ�Ŀ��
		screen_height = this.getHeight(); // �����ͼ�ĸ߶�
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

	// ��ʼ��ͼƬ��Դ����
	public void initBitmap() {
	}

	// �ͷ�ͼƬ��Դ�ķ���
	public void release() {
	}

	// ��ͼ����
	public void drawSelf() {
	}

	public void setThreadFlag(boolean threadFlag) {
		this.threadFlag = threadFlag;
	}
}
