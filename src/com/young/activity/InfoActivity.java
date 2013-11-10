package com.young.activity;

import java.io.IOException;

import com.young.R;
import com.young.business.HBUT;
import com.young.entry.Student;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

public class InfoActivity extends Activity{

	private Student student;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_info);
		new Thread(new Runnable() {
			
			@Override
			public void run() {
				HBUT hbut = HBUT.getInstance();
				try {
					student = hbut.getInfo();
				} catch (IOException e) {
					e.printStackTrace();
				}
				TextView name = (TextView) findViewById(R.id.info_name);
				TextView stuNum = (TextView) findViewById(R.id.info_stu_num);
				TextView className = (TextView) findViewById(R.id.info_class_name);
				TextView sex = (TextView) findViewById(R.id.info_sex);
				TextView IDCard = (TextView) findViewById(R.id.info_IDCard);
				TextView ethnic = (TextView) findViewById(R.id.info_ethnic);
				TextView college = (TextView) findViewById(R.id.info_college);
				TextView major = (TextView) findViewById(R.id.info_major);
				TextView year = (TextView) findViewById(R.id.info_year);
				TextView politicalStatus = (TextView) findViewById(R.id.info_potitical_status);
				TextView birthday = (TextView) findViewById(R.id.info_birthday);
				TextView enterScholl = (TextView) findViewById(R.id.info_enterscholl);
				TextView leftScholl = (TextView) findViewById(R.id.info_left_scholl);
				
				
				System.out.println(student.getStuName());
				
				name.setText(student.getStuName());
				stuNum.setText(student.getStuName());
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
			}
		}).start();
	}



}
