package com.young.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.Toast;

import com.young.R;
import com.young.adapter.AdapterForMain;

public class MainActivity extends BaseActivity {

    private ListView listView;
    private long exitTime;//两次返回键退出之间的间隔

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        listView = (ListView) findViewById(R.id.main_list_title);
        AdapterForMain adapter = new AdapterForMain(this);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
                                    long arg3) {
                switch (arg2) {
                    case 0://教学课表
                        Intent intent = new Intent(MainActivity.this, ChoseScheduleTypeActitity.class);
                        MainActivity.this.startActivity(intent);
                        break;

                    case 1://个人信息
                        intent = new Intent(MainActivity.this, InfoActivity.class);
                        MainActivity.this.startActivity(intent);
                        break;

                    case 2://成绩管理
                        Intent intent3 = new Intent(MainActivity.this, ChoseTermsActivity.class);
//						intent3.putExtra(CHOSE_MENU_MESSAGE, SCORE_MANAGEMENT);
                        MainActivity.this.startActivity(intent3);
                        break;
                    case 3://修改密码
                        intent = new Intent(MainActivity.this, ChangePwdActivity.class);
                        MainActivity.this.startActivity(intent);
                        break;
                    default:
                        break;

                }
            }
        });

    }

    //按两次退出程序
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK
                && event.getAction() == KeyEvent.ACTION_DOWN) {

            if ((System.currentTimeMillis() - exitTime) > 2000) // System.currentTimeMillis()无论何时调用，肯定大于2000
            {
                Toast.makeText(getApplicationContext(), "亲爱滴,再按一次退出程序",
                        Toast.LENGTH_SHORT).show();
                exitTime = System.currentTimeMillis();
            } else {
                Intent intent = new Intent(Intent.ACTION_MAIN);
                intent.addCategory(Intent.CATEGORY_HOME);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                android.os.Process.killProcess(android.os.Process.myPid());
                System.exit(0);
                finish();
            }

            return true;
        }
        return super.onKeyDown(keyCode, event);
    }


}
