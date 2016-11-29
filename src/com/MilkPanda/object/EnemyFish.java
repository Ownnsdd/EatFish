package com.MilkPanda.object;

import com.MilkPanda.object.GameObject;
import android.content.res.Resources;
import android.graphics.Canvas;

public class EnemyFish extends GameObject {

	protected int score; // ����ķ�ֵ
	protected int size; // ����ĵ�ǰѪ��
	protected int plus_size;
	protected boolean isVisible; // �����Ƿ�Ϊ�ɼ�״̬

	public EnemyFish(Resources resources) {
		super(resources);
		initBitmap(); // ��ʼ��ͼƬ��Դ
	}

	// ��ʼ������
	@Override
	public void initial(int SpeedTime, float arg1, float arg2) {
		
	}

	@Override
	protected void initBitmap() {
		// TODO Auto-generated method stub

	}

	@Override
	public void drawSelf(Canvas canvas) {
		// TODO Auto-generated method stub

	}

	@Override
	public void release() {
		// TODO Auto-generated method stub

	}

	@Override
	public void logic() {
		if (object_x < screen_width) {
			object_x += speed;
		} else {
			isAlive = false;
		}
		if (object_x + object_width > 0) {
			isVisible = true;
		} else {
			isVisible = false;
		}
	}

	@Override
	public boolean isCollide(GameObject obj) {
		// ����1λ�ھ���2�����
		if (object_x <= obj.getObject_x() && object_x + object_width <= obj.getObject_x()) {
			return false;
		}
		// ����1λ�ھ���2���Ҳ�
		else if (obj.getObject_x() <= object_x && obj.getObject_x() + obj.getObject_width() <= object_x) {
			return false;
		}
		// ����1λ�ھ���2���Ϸ�
		else if (object_y <= obj.getObject_y() && object_y + object_height <= obj.getObject_y()) {
			return false;
		}
		// ����1λ�ھ���2���·�
		else if (obj.getObject_y() <= object_y && obj.getObject_y() + obj.getObject_height() <= object_y) {
			return false;
		}
		return true;
	}

	// �ж��ܷ񱻼����ײ
	public boolean isCanCollide() {
		// TODO Auto-generated method stub
		return isAlive && isVisible;
	}

	// getter��setter����
	public int getScore() {
		// TODO Auto-generated method stub
		return score;
	}

	public void setScore(int score) {
		// TODO Auto-generated method stub
		this.score = score;
	}
	
	public int getSize() {
		return size;
	}
}
