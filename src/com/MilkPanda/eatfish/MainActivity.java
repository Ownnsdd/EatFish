package com.MilkPanda.eatfish;

import com.MilkPanda.constant.ConstantUtil;
import com.MilkPanda.view.EndView;
import com.MilkPanda.view.MainView;
import com.MilkPanda.view.ReadyView;

import android.app.Activity;

import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.Window;
import android.view.WindowManager;

public class MainActivity extends Activity {
	private ReadyView readyView;
	private MainView mainView;
	private EndView endView;
	private Handler handler = new Handler(){
		@Override
		public void handleMessage(Message msg){
			if(msg.what == ConstantUtil.TO_MAIN_VIEW){
				toMainView();
			}
			else if(msg.what == ConstantUtil.TO_END_VIEW){
				toEndView(msg.arg1);
			}
			else if(msg.what == ConstantUtil.END_GAME){
				endGame();
			}
		}
	};
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
		readyView = new ReadyView(this);
		setContentView(readyView);
	}

	private void endGame() {
		// TODO Auto-generated method stub
		if(readyView != null){
			readyView.setThreadFlag(false);
		}
		else if(mainView != null){
			mainView.setThreadFlag(false);
		}
		else if(endView != null){
			endView.setThreadFlag(false);
		}
		this.finish();
	}

	private void toEndView(int score) {
		// TODO Auto-generated method stub
		if(endView == null){
			endView = new EndView(this);
			endView.setScore(score);
		}
		setContentView(endView);
		mainView = null;
	}

	private void toMainView() {
		// TODO Auto-generated method stub
		if(mainView == null){
			mainView = new MainView(this);
		}
		setContentView(mainView);
		readyView = null;
		endView = null;
	}
	
	public Handler getHandler() {
		return handler;
	}
	public void setHandler(Handler handler) {
		this.handler = handler;
	}
}
