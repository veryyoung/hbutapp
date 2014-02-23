package com.young.activity;


import java.io.IOException;
import java.util.ArrayList;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.young.R;
import com.young.business.HBUT;

public class ChoseItemActivity extends Activity implements Runnable{

        
        private ArrayList<String> data;
        private TextView textTitle ;
        private AutoCompleteTextView editClassName;
        private Button button;
        private String className;
        private ProgressDialog pd;
        
        public static final String CLASS_NAME = "className";
        
        private Handler handler = new Handler(){

                @Override
             public void handleMessage(Message msg) {
                        // TODO Auto-generated method stub
               ArrayAdapter<String> adapter = new ArrayAdapter<String>(ChoseItemActivity.this,R.layout.simple_dropdown_item_1line,data);
			editClassName.setAdapter(adapter);
			pd.dismiss();
			super.handleMessage(msg);
		}

	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_chose_item_list);
		textTitle = (TextView) findViewById(R.id.chose_list_title);
		editClassName = (AutoCompleteTextView) findViewById(R.id.chose_auto_input);
		button = (Button) findViewById(R.id.chose_button_ok);
		textTitle.setText("输入班级名称");
		// 在这里添加一个线程
		// data = new ArrayList<String>();
		// data.add("11软件1");
		// data.add("11软件2");
		pd = ProgressDialog.show(ChoseItemActivity.this, "加载中", "加载中，请稍后...");
		Thread thread = new Thread(this);
		thread.start();

		// className = editClassName.getText().toString();
		editClassName.addTextChangedListener(new TextWatcher() {

			@Override
			public void onTextChanged(CharSequence s, int start, int before,
					int count) {
				// TODO Auto-generated method stub

			}

			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {
				// TODO Auto-generated method stub

			}

			@Override
			public void afterTextChanged(Editable s) {
				// TODO Auto-generated method stub
				className = editClassName.getText().toString();
			}
		});
		// className = "10工设1";
		button.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				System.out.println("in ChoseItemActivity 77" + className);
				if ("".equals(className)) {
					Toast.makeText(ChoseItemActivity.this, "查询班级不能为空",
							Toast.LENGTH_SHORT).show();
				} else {
					Intent intent = new Intent();
					intent.setClass(ChoseItemActivity.this,
							ScheduleActivity.class);
					intent.putExtra(CLASS_NAME, className);
					startActivity(intent);
					// System.out.println("this is in ChoseItemActivity 78   "+className);
				}

			}
		});
	}

	@Override
	protected void onStart() {
		// TODO Auto-generated method stub
		super.onStart();
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		try {
			data = HBUT.getInstance().getClassName();
			System.out.println(data);
			handler.sendEmptyMessage(0);
		} catch (IOException e) {
			// TODO Auto-generated catch block
                        e.printStackTrace();
                }
                
        }
        
        
	
	
}
