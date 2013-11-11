package com.young.activity;

import java.io.IOException;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.os.Looper;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.EditText;
import android.widget.Toast;

import com.young.R;
import com.young.business.HBUT;

public class loginActivity extends Activity {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.login);
		final EditText usernameEditText = (EditText) findViewById(R.id.username);
		final EditText passwordEditText = (EditText) findViewById(R.id.password);
		Button loginButton = (Button) findViewById(R.id.login_ok);
		final CheckBox rememberPw = (CheckBox) findViewById(R.id.remember);
		@SuppressWarnings("deprecation")
		final SharedPreferences sp = this.getSharedPreferences("userInfo",
				Context.MODE_WORLD_READABLE);
		if (sp.getBoolean("ISCHECK", false)) {
			rememberPw.setChecked(true);
			usernameEditText.setText(sp.getString("USER_NAME", ""));
			passwordEditText.setText(sp.getString("PASSWORD", ""));
		}

		loginButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				new Thread(new Runnable() {

					@Override
					public void run() {
						String username = usernameEditText.getText().toString();
						String password = passwordEditText.getText().toString();
						HBUT hbut = HBUT.getInstance();
						try {

							Boolean loginFlag = hbut.login(username, password);
							if (loginFlag) {
								Looper.prepare();
								Toast.makeText(getBaseContext(), "登录成功",
										Toast.LENGTH_LONG).show();
								if (rememberPw.isChecked()) {
									Editor editor = sp.edit();
									editor.putString("USER_NAME", username);
									editor.putString("PASSWORD", password);
									editor.commit();
								}

								Intent intent = new Intent(loginActivity.this,
										LogoActivity.class);
								loginActivity.this.startActivity(intent);
								Looper.loop();
							} else {
								Looper.prepare();
								Toast.makeText(getBaseContext(),
										"登录失败，请核对您的学号和密码！", Toast.LENGTH_LONG)
										.show();
								Intent intent = new Intent(loginActivity.this,
										loginActivity.class);
								loginActivity.this.startActivity(intent);
								Looper.loop();
							}

						} catch (IOException e) {
							e.printStackTrace();
						}

					}
				}).start();
			}
		});

		rememberPw.setOnCheckedChangeListener(new OnCheckedChangeListener() {

			@Override
			public void onCheckedChanged(CompoundButton buttonView,
					boolean isChecked) {
				if (rememberPw.isChecked()) {
					sp.edit().putBoolean("ISCHECK", true).commit();
				} else {
					sp.edit().putBoolean("ISCHECK", false).commit();
				}
			}
		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

}
