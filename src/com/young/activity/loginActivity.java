package com.young.activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.young.R;

public class LoginActivity extends Activity {

    public static final String USERNAME = "userName";
    public static final String PASSWORD = "passWord";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        @SuppressWarnings("deprecation")
        final SharedPreferences sp = this.getSharedPreferences("userInfo",
                Context.MODE_WORLD_READABLE);
        String stuId = sp.getString("USER_NAME", "");
        //判断你是否登陆过，如果是就直接进入MainActivity
        if (!("".equals(stuId))) {
            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
            LoginActivity.this.startActivity(intent);
            finish();
        } else {
            if (!isOpenNetwork()) {
                AlertDialog.Builder builder = new AlertDialog.Builder(
                        LoginActivity.this);
                builder.setTitle("没有可用的网络").setMessage("是否对网络进行设置?");

                builder.setPositiveButton("是",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Intent intent = null;

                                try {
                                    @SuppressWarnings("deprecation")
                                    String sdkVersion = android.os.Build.VERSION.SDK;
                                    if (Integer.valueOf(sdkVersion) > 10) {
                                        intent = new Intent(
                                                android.provider.Settings.ACTION_WIRELESS_SETTINGS);
                                    } else {
                                        intent = new Intent();
                                        ComponentName comp = new ComponentName(
                                                "com.android.settings",
                                                "com.android.settings.WirelessSettings");
                                        intent.setComponent(comp);
                                        intent.setAction("android.intent.action.VIEW");
                                    }
                                    LoginActivity.this.startActivity(intent);
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                            }
                        }
                )
                        .setNegativeButton("否",
                                new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog,
                                                        int which) {
                                        dialog.cancel();
                                        finish();
                                    }
                                }
                        ).show();
            }
        }

        final EditText usernameEditText = (EditText) findViewById(R.id.username);
        final EditText passwordEditText = (EditText) findViewById(R.id.password);
        Button loginButton = (Button) findViewById(R.id.login_ok);

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        String username = usernameEditText.getText().toString();
                        String password = passwordEditText.getText().toString();
                        Intent intent = new Intent(LoginActivity.this,
                                LogoActivity.class);
                        intent.putExtra(USERNAME, username);
                        intent.putExtra(PASSWORD, password);
                        LoginActivity.this.startActivity(intent);

                    }
                }).start();
            }
        });


    }

//	@Override
//	public boolean onCreateOptionsMenu(Menu menu) {
//		getMenuInflater().inflate(R.menu.main, menu);
//		return true;
//	}

    private boolean isOpenNetwork() {
        ConnectivityManager connManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connManager.getActiveNetworkInfo() != null) {
            return connManager.getActiveNetworkInfo().isAvailable();
        }

        return false;
    }


}
