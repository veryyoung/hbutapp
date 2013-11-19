package com.young.activity;

import java.io.IOException;
import java.util.ArrayList;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.young.R;
import com.young.business.HBUT;

public class ChoseItemActivity extends Activity {

	private ArrayList<String> data;
	private TextView textTitle;
	private AutoCompleteTextView editClassName;
	private Button button;
	private String className;
	private ProgressDialog mpDialog;

	public static final String CLASS_NAME = "className";

	private Handler handler;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_chose_item_list);
		mpDialog = new ProgressDialog(ChoseItemActivity.this);
		mpDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);// 设置风格为圆形进度条
		mpDialog.setTitle("");// 设置标题
		mpDialog.setMessage("正在玩命加载中，请稍候....");
		mpDialog.setIndeterminate(false);// 设置进度条是否为不明确
		mpDialog.setCancelable(true);// 设置进度条是否可以按退回键取消
		mpDialog.show();

		textTitle = (TextView) findViewById(R.id.chose_list_title);
		editClassName = (AutoCompleteTextView) findViewById(R.id.chose_auto_input);
		button = (Button) findViewById(R.id.chose_button_ok);
		textTitle.setText("输入班级名称");
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(
				ChoseItemActivity.this,
				android.R.layout.simple_dropdown_item_1line, data);
		editClassName.setAdapter(adapter);
		handler = new Handler();
		new Thread(new Runnable() {

			@Override
			public void run() {
				try {
					data = HBUT.getInstance().getClassName();
					handler.sendEmptyMessage(0);
				} catch (IOException e) {
					e.printStackTrace();
				}
				mpDialog.dismiss();

			}
		}).start();

		button.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				className = editClassName.getText().toString();
				if ("".equals(className)) {
					Toast.makeText(ChoseItemActivity.this, "查询班级不能为空",
							Toast.LENGTH_SHORT).show();
				} else {
					Intent intent = new Intent();
					intent.setClass(ChoseItemActivity.this,
							ScheduleActivity.class);
					intent.putExtra(CLASS_NAME, className);
					startActivity(intent);
				}

			}
		});
	}

	// Runnable runnableUi = new Runnable() {
	// @Override
	// public void run() {
	// ArrayAdapter<String> adapter = new
	// ArrayAdapter<String>(ChoseItemActivity.this,
	// android.R.layout.simple_dropdown_item_1line,data);
	// editClassName.setAdapter(adapter);
	// mpDialog.dismiss();
	// };
	// };

}
