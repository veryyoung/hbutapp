package com.young.activity;

import java.io.IOException;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.os.Looper;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.young.R;
import com.young.business.HBUT;

public class LogoActivity extends Activity {
	private ProgressBar progressBar;
	private Button backButton;

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.logo);

		progressBar = (ProgressBar) findViewById(R.id.pgBar);
		backButton = (Button) findViewById(R.id.btn_back);

		final String username = this.getIntent().getStringExtra(
				loginActivity.USERNAME);
		final String password = this.getIntent().getStringExtra(
				loginActivity.PASSWORD);
		new Thread(new Runnable() {

			@Override
			public void run() {
				HBUT hbut = HBUT.getInstance();
				try {
					Boolean loginFlag = hbut.login(username, password);
					if (loginFlag) {
						Looper.prepare();
						Toast.makeText(getBaseContext(), "登录成功",
								Toast.LENGTH_LONG).show();
						Intent intent = new Intent(LogoActivity.this, MainActivity.class);
						LogoActivity.this.startActivity(intent);
						Looper.loop();
					} else {
						Looper.prepare();
						Toast.makeText(getBaseContext(),
								"登录失败，请核对您的学号和密码！", Toast.LENGTH_LONG)
								.show();
						Intent intent = new Intent(LogoActivity.this,
								loginActivity.class);
						LogoActivity.this.startActivity(intent);
						Looper.loop();
					}
				} catch (IOException e) {
					e.printStackTrace();
				}

			}
		}).start();



		backButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				finish();

			}
		});

	}
	
	@Override
	protected void onPause() {
		super.onPause();
		this.finish();
	}

}
