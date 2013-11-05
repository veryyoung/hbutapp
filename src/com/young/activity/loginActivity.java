package com.young.activity;

import java.io.IOException;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

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
		                HBUT hbut = new HBUT();
		                try {
		                	
		                    Boolean loginFlag = hbut.login(username,password);
		                    if(loginFlag){
		                    	Intent intent = new Intent(loginActivity.this,MainActivity.class);
		                    	loginActivity.this.startActivity(intent);
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
