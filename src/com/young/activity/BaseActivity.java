package com.young.activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.young.R;
import com.young.util.AppUtil;

import java.lang.reflect.Method;

public abstract class BaseActivity extends Activity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        AppUtil.getInstance().addActivity(this);
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        AppUtil.getInstance().remove(this);
    }

    // 监听非MainActivity所有的返回按钮，如果返回就杀这个activity用来释放资源
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            this.finish();
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        setIconEnable(menu, true);
        getMenuInflater().inflate(R.menu.main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.about_us:
                Intent intent1 = new Intent(this, AboutUsActivity.class);
                this.startActivity(intent1);
                break;
            case R.id.check_update:
                Toast.makeText(this, "检查更新", Toast.LENGTH_LONG).show();
                break;
            case R.id.exit:
                AlertDialog.Builder builder = new AlertDialog.Builder(
                        BaseActivity.this);
                builder.setTitle("确定注销吗？");
                builder.setNegativeButton("取消",
                        new DialogInterface.OnClickListener() {

                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.cancel();
                            }
                        }
                ).setPositiveButton("确定",
                        new DialogInterface.OnClickListener() {

                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                @SuppressWarnings("deprecation")
                                SharedPreferences sp = BaseActivity.this
                                        .getSharedPreferences("userInfo",
                                                Context.MODE_WORLD_READABLE);
                                SharedPreferences.Editor editor = sp.edit();
                                editor.remove("USER_NAME");
                                editor.remove("PASSWORD");
                                editor.commit();
                                Intent intent = new Intent(BaseActivity.this,
                                        LoginActivity.class);
                                BaseActivity.this.startActivity(intent);
                                AppUtil.getInstance().exit();

                                Toast.makeText(BaseActivity.this, "注销成功",
                                        Toast.LENGTH_LONG).show();

                            }
                        }
                ).show();

                break;
            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    // enable为true时，菜单添加图标有效，enable为false时无效。4.0系统默认无效
    private void setIconEnable(Menu menu, boolean enable) {
        try {
            Class<?> clazz = Class
                    .forName("com.android.internal.view.menu.MenuBuilder");
            Method m = clazz.getDeclaredMethod("setOptionalIconsVisible",
                    boolean.class);
            m.setAccessible(true);

            // MenuBuilder实现Menu接口，创建菜单时，传进来的menu其实就是MenuBuilder对象(java的多态特征)
            m.invoke(menu, enable);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
