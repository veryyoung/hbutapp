package com.young.activity;

import java.io.IOException;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Looper;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.young.R;
import com.young.business.HBUT;

public class loginActivity extends Activity {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.login);
		Button loginButton = (Button) findViewById(R.id.login_ok);
		loginButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				new Thread(new Runnable() {

					@Override
					public void run() {
						EditText usernameEditText = (EditText) findViewById(R.id.username);
						EditText passwordEditText = (EditText) findViewById(R.id.password);
						String username = usernameEditText.getText().toString();
						String password = passwordEditText.getText().toString();
						Log.d("username", username);
						Log.d("password", password);
						HBUT hbut = HBUT.getInstance();
						try {

							Boolean loginFlag = hbut.login(username, password);
							if (loginFlag) {
								Looper.prepare();
								Toast.makeText(getBaseContext(), "登录成功",
										Toast.LENGTH_LONG).show();
								Intent intent = new Intent(loginActivity.this,
										MainActivity.class);
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
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

}
