package com.young.activity;

import java.io.IOException;

import org.json.JSONException;

import com.young.R;
import com.young.business.HBUT;

import android.app.Activity;
import android.os.Bundle;
import android.os.Looper;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class ChangeActivity extends Activity {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_changepwd);
		Button button = (Button) findViewById(R.id.changeButton);
		button.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				new Thread(new Runnable() {

					@Override
					public void run() {
						EditText oldPass = (EditText) findViewById(R.id.change_oldpw);
						EditText newPass = (EditText) findViewById(R.id.change_newpw);
						EditText conPass = (EditText) findViewById(R.id.change_conpw);

						String oldPassword = oldPass.getText().toString();
						String newPassword = newPass.getText().toString();
						String conPassword = conPass.getText().toString();

						HBUT hbut = HBUT.getInstance();
						try {
							String message = hbut.changePass(oldPassword,
									newPassword, conPassword);
							Looper.prepare();

							Toast.makeText(getBaseContext(), message,
									Toast.LENGTH_LONG).show();
							if("保存成功".equals(message)){
								ChangeActivity.this.finish();
							}
							Looper.loop();
						} catch (IOException e) {
							e.printStackTrace();
						} catch (JSONException e) {
							e.printStackTrace();
						}
					}
				}).start();
			}
		});

	}

}
