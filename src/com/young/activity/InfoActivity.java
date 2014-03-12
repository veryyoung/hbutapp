package com.young.activity;

import java.io.IOException;


import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import com.young.R;
import com.young.business.HBUT;
import com.young.entry.Student;
import com.young.sqlite.DatabaseHelper;
import com.young.util.NetworkUtil;

public class InfoActivity extends BaseActivity {

	private Student student;
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
	
	private String stuId;
	private String password;
	private DatabaseHelper helper;

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
//        mpDialog.show();

//		handler = new Handler();

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
		
		//获取已经登陆的用户名和密码
		getUserIdAndPassWord();
		helper = new DatabaseHelper(this);
		student = helper.getStudent(stuId);
		if(student == null){
			mpDialog.show();
			new GetStudentFromNetWork().execute();
		}else{
			setUI();
		}
//		new Thread(new Runnable() {
//
//			@Override
//			public void run() {
//				DatabaseHelper helper = new DatabaseHelper(InfoActivity.this);
//				student = helper.getStudent("1110321124");
//				if(student == null){
//					HBUT hbut = HBUT.getInstance();
//					try {
//						hbut.login(username, password)
//						student = hbut.getInfo();
//					} catch (IOException e) {
//						e.printStackTrace();
//					}
//					
//				}
//
//				handler.post(runnableUi);
//			}
//		}).start();
	}

	private void setUI(){
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
	}
	
	private class GetStudentFromNetWork extends
    AsyncTask<String, Integer, String> {


			@Override
			protected String doInBackground(String... arg0) {
			    HBUT hbut = HBUT.getInstance();
			    try {
			        if (!NetworkUtil.isOpenNetwork()) {
			            finish();
			            return "无网络连接";
			        } else {
			            hbut.login(stuId, password);
			            student = hbut.getInfo();
			            helper.insertStudent(student);
			        }
			    } catch (IOException e) {
			        e.printStackTrace();
			    } 
			    return "更新完毕";
			}


			//这个是在后台执行完毕之后执行
			@Override
			protected void onPostExecute(String result) {
			    mpDialog.dismiss();
			    if (("更新完毕").equals(result)) {
			        //需要在这里在查一遍，看看有没有数据
			        student = helper.getStudent(stuId);
			        if (student != null) {
			        	setUI();
			        } else {
			            Toast.makeText(InfoActivity.this, "没有数据", Toast.LENGTH_LONG).show();
			        }
			    }
			    else{
			        Toast.makeText(InfoActivity.this,result , Toast.LENGTH_LONG).show();
			    }
			
			}
			}

			private void getUserIdAndPassWord() {
			@SuppressWarnings("deprecation")
			SharedPreferences sp = InfoActivity.this.getSharedPreferences("userInfo", Context.MODE_WORLD_READABLE);
			stuId = sp.getString("USER_NAME", "");
			password = sp.getString("PASSWORD", "");
			}

}
