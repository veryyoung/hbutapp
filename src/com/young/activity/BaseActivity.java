package com.young.activity;

import android.app.Activity;
import android.view.KeyEvent;

public abstract class BaseActivity extends Activity{

	//监听所有的返回按钮，如果返回就杀这个activity用来释放资源
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		// TODO Auto-generated method stub
		if(keyCode==KeyEvent.KEYCODE_BACK){
			this.finish();
		}
		return super.onKeyDown(keyCode, event);
	}
	
	

}
