package com.young.activity;

import java.io.IOException;
import java.util.List;

import org.json.JSONException;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Looper;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
//import android.widget.ProgressBar;
import android.widget.Toast;

import com.young.R;
import com.young.business.HBUT;
import com.young.entry.Schedule;
import com.young.entry.Student;

public class LogoActivity extends Activity {
//	private ProgressBar progressBar;
	private Button backButton;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.logo);

//		progressBar = (ProgressBar) findViewById(R.id.pgBar);
		backButton = (Button) findViewById(R.id.btn_back);

		final String username = this.getIntent().getStringExtra(
				LoginActivity.USERNAME);
		final String password = this.getIntent().getStringExtra(
				LoginActivity.PASSWORD);
		new Thread(new Runnable() {

			@Override
			public void run() {
				HBUT hbut = HBUT.getInstance();
                try {
                    if(hbut.login("1110321229","005685")){
                        System.out.println("登录成功");
                        List<Schedule> schedules  = hbut.getSchedule("1110321229");
                        for (Schedule schedule:schedules){
                            System.out.print(schedule.getCurName() +  "       ");
                            System.out.print(schedule.getDayTime() +  "       ");
                            System.out.print(schedule.getDay() +  "       ");
                            System.out.print(schedule.getPlace() +  "       ");
                            System.out.print(schedule.getTeacher() + "       ");
                            System.out.print(schedule.getWeek() + "       ");
                          }
                          System.out.println();
                    }
                    else{
                        System.out.println("登录失败");
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (JSONException e) {
                										e.printStackTrace();
				}

//				try {
//					Boolean loginFlag = hbut.login(username, password);
//					if (loginFlag) {
//						Looper.prepare();
//						Toast.makeText(getBaseContext(), "登录成功",
//								Toast.LENGTH_LONG).show();
////						Intent intent = new Intent(LogoActivity.this, MainActivity.class);
//						LogoActivity.this.startActivity(intent);
//						Looper.loop();
//					} else {
//						Looper.prepare();
//						Toast.makeText(getBaseContext(),
//								"登录失败，请核对您的学号和密码！", Toast.LENGTH_LONG)
//								.show();
//						Intent intent = new Intent(LogoActivity.this,
//								LoginActivity.class);
//						LogoActivity.this.startActivity(intent);
//						Looper.loop();
//					}
//				} catch (IOException e) {
//					e.printStackTrace();
//				}

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
