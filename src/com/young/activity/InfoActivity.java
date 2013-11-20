package com.young.activity;

import java.io.IOException;

import com.young.R;
import com.young.business.HBUT;
import com.young.entry.Student;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.os.Handler;
import android.widget.TextView;

public class InfoActivity extends Activity {

	private Student student;
	private Handler handler;
	private TextView name;
	private TextView stuNum;
	private TextView className;
	private TextView sex;
	private TextView IDCard;
	private TextView ethnic;
	private TextView college;
	private TextView major;
	private TextView year;
	private TextView politicalStatus;
	private TextView birthday;
	private TextView enterScholl;
	private TextView leftScholl;
	private ProgressDialog mpDialog;  

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_info);
		
        mpDialog = new ProgressDialog(InfoActivity.this);  
        mpDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);//设置风格为圆形进度条  
        mpDialog.setTitle("");//设置标题  
        mpDialog.setMessage("正在玩命加载中，请稍候....");  
        mpDialog.setIndeterminate(false);//设置进度条是否为不明确  
        mpDialog.setCancelable(true);//设置进度条是否可以按退回键取消
        mpDialog.show();

		handler = new Handler();

		name = (TextView) findViewById(R.id.info_name);
		stuNum = (TextView) findViewById(R.id.info_stu_num);
		className = (TextView) findViewById(R.id.info_class_name);
		sex = (TextView) findViewById(R.id.info_sex);
		IDCard = (TextView) findViewById(R.id.info_IDCard);
		ethnic = (TextView) findViewById(R.id.info_ethnic);
		college = (TextView) findViewById(R.id.info_college);
		major = (TextView) findViewById(R.id.info_major);
		year = (TextView) findViewById(R.id.info_year);
		politicalStatus = (TextView) findViewById(R.id.info_potitical_status);
		birthday = (TextView) findViewById(R.id.info_birthday);
		enterScholl = (TextView) findViewById(R.id.info_enterscholl);
		leftScholl = (TextView) findViewById(R.id.info_left_scholl);
		new Thread(new Runnable() {

			@Override
			public void run() {
				HBUT hbut = HBUT.getInstance();
				try {
					student = hbut.getInfo();
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
			name.setText(student.getStuName());
			stuNum.setText(student.getStuNum());
			className.setText(student.getClassName());
			sex.setText(student.getSex());
			IDCard.setText(student.getIDCard());
			ethnic.setText(student.getEthnic());
			college.setText(student.getCollege());
			major.setText(student.getMajor());
			year.setText(student.getYear());
			politicalStatus.setText(student.getPoliticalStatus());
			birthday.setText(student.getBirthDay());
			enterScholl.setText(student.getEnterScholl());
			leftScholl.setText(student.getLeftScholl());
			mpDialog.dismiss();
		};
	};

}
