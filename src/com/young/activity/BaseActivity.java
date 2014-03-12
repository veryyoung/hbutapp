package com.young.activity;

import android.app.Activity;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

public abstract class BaseActivity extends Activity {

    //监听非MainActivity所有的返回按钮，如果返回就杀这个activity用来释放资源
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            this.finish();
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        menu.add(0, 1, 1, "退出").setIcon(android.R.drawable.ic_menu_close_clear_cancel);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Toast toast = Toast.makeText(this, "这是个Menu菜单的练习", Toast.LENGTH_SHORT);
        toast.show();
        return super.onOptionsItemSelected(item);
    }
}
