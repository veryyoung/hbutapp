package com.young.activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.EditText;

import com.young.R;

public class loginActivity extends Activity {

	public static final String USERNAME = "userName";
	public static final String PASSWORD = "passWord";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.login);

		if (!isOpenNetwork()) {
			AlertDialog.Builder builder = new AlertDialog.Builder(
					loginActivity.this);
			builder.setTitle("没有可用的网络").setMessage("是否对网络进行设置?");

			builder.setPositiveButton("是",
					new DialogInterface.OnClickListener() {
						@Override
						public void onClick(DialogInterface dialog, int which) {
							Intent intent = null;

							try {
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
								loginActivity.this.startActivity(intent);
							} catch (Exception e) {
								e.printStackTrace();
							}
						}
					})
					.setNegativeButton("否",
							new DialogInterface.OnClickListener() {
								@Override
								public void onClick(DialogInterface dialog,
										int which) {
									dialog.cancel();
									finish();
								}
							}).show();
		}

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

						if (rememberPw.isChecked()) {
							Editor editor = sp.edit();
							editor.putString("USER_NAME", username);
							editor.putString("PASSWORD", password);
							editor.commit();
						}
						Intent intent = new Intent(loginActivity.this,
								LogoActivity.class);
						intent.putExtra(USERNAME, username);
						intent.putExtra(PASSWORD, password);
						loginActivity.this.startActivity(intent);

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
