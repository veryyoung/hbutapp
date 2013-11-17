package com.young.activity;

import java.io.IOException;

import com.young.R;
import com.young.business.HBUT;
import com.young.entry.PubliClass;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.widget.TextView;

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

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_public);
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
			taskNo.setText(publiClass.getTaskNo());
			taskName.setText(publiClass.getTaskName());
			taskPlace.setText(publiClass.getTaskPlace());
			taskType.setText(publiClass.getTaskType());
			college.setText(publiClass.getTaskColledge());
			credit.setText(publiClass.getTaskCredit());
			times.setText(publiClass.getExamTimes());
			score.setText(publiClass.getScore());

		};
	};
}
