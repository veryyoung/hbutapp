package com.young.activity;

import android.app.Activity;
import android.content.Intent;
import android.view.KeyEvent;

public abstract class BaseActivity extends Activity {

    //监听非MainActivity所有的返回按钮，如果返回就杀这个activity用来释放资源
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
//            Intent intent = new Intent(BaseActivity.this,MainActivity.class);
//            BaseActivity.this.startActivity(intent);
            this.finish();
        }
        return super.onKeyDown(keyCode, event);
    }


}
