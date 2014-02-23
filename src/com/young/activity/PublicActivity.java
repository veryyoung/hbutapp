package com.young.activity;

import java.io.IOException;

import com.young.R;
import com.young.business.HBUT;
import com.young.entry.PubliClass;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.os.Handler;
import android.widget.TextView;
import android.widget.Toast;

public class PublicActivity extends Activity {
	private Handler handler;
	PubliClass publiClass;
	TextView taskNo;
	TextView taskName;
	TextView taskPlace;
	TextView taskType;
	TextView college;
	TextView credit;
	TextView times;
	TextView score;
	private ProgressDialog mpDialog;  


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_public);
        mpDialog = new ProgressDialog(PublicActivity.this);  
        mpDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);//设置风格为圆形进度条  
        mpDialog.setTitle("");//设置标题  
        mpDialog.setMessage("正在玩命加载中，请稍候....");  
        mpDialog.setIndeterminate(false);//设置进度条是否为不明确  
        mpDialog.setCancelable(true);//设置进度条是否可以按退回键取消
        mpDialog.show();
		
		
		taskNo = (TextView) findViewById(R.id.public_task_no);
		taskName = (TextView) findViewById(R.id.public_task_name);
		taskPlace = (TextView) findViewById(R.id.public_task_place);
		taskType = (TextView) findViewById(R.id.public_task_type);
		college = (TextView) findViewById(R.id.public_task_college);
		credit = (TextView) findViewById(R.id.public_task_credit);
		times = (TextView) findViewById(R.id.public_exam_times);
		score = (TextView) findViewById(R.id.public_task_score);
		handler = new Handler();
		new Thread(new Runnable() {

			@Override
			public void run() {
				HBUT hbut = HBUT.getInstance();
				try {
					publiClass = hbut.getPublic();
				} catch (IOException e) {
					e.printStackTrace();
				}

				handler.post(runnableUi);
			}
		}).start();
		
	}

	Runnable runnableUi = new Runnable() {
		@Override
		public void run() {
			if(publiClass==null){
				Toast.makeText(PublicActivity.this, "您还没有选课！", Toast.LENGTH_SHORT).show();
				taskNo.setText("");
				taskName.setText("");
				taskPlace.setText("");
				taskType.setText("");
				college.setText("");
				credit.setText("");
				times.setText("");
				score.setText("");
			}else{
				taskNo.setText(publiClass.getTaskNo());
				taskName.setText(publiClass.getTaskName());
				taskPlace.setText(publiClass.getTaskPlace());
				taskType.setText(publiClass.getTaskType());
				college.setText(publiClass.getTaskColledge());
				credit.setText(publiClass.getTaskCredit());
				times.setText(publiClass.getExamTimes());
				score.setText(publiClass.getScore());
			}
			
			mpDialog.dismiss();

		};
	};
}
