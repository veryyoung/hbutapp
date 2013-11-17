package com.young.activity;

import android.app.Activity;
//import android.content.Intent;

import android.os.Bundle;
import android.view.GestureDetector;
import android.view.GestureDetector.OnGestureListener;
import android.view.MotionEvent;
import android.view.View;

import android.view.View.OnTouchListener;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.young.R;
import com.young.adapter.AdapterForSchedule;
import com.young.adapter.MyBaseAdapter;

public class ScheduleActivity extends Activity implements OnTouchListener, OnGestureListener{
	
	private TextView textView;
	private ListView listView;
	private String text = "星期一";
	private MyBaseAdapter adapter;
	private int n = 1;
	private GestureDetector mDetector;
	private static final int FLING_MIN_DISTANCE = 50;
	private static final int FLING_MIN_VELOCITY = 0;
	
	public static final String NO_NAME = "no_class_name";
	private String className;


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_schedule);
		textView = (TextView)this.findViewById(R.id.text_schedule_title);
		listView = (ListView)this.findViewById(R.id.list_schedule_course);
		listView.setDividerHeight(0);
		mDetector = new GestureDetector(this,this);
		mDetector.setIsLongpressEnabled(true);
//		listView.setEnabled(false);
		RelativeLayout layout = (RelativeLayout) findViewById(R.id.Schedule_relative_layout);
		layout.setOnTouchListener(this);
		layout.setLongClickable(true);
		layout.setFocusable(true);
		layout.setClickable(true);
		layout.setLongClickable(true);
//		listView.setOnTouchListener(this);
//		listView.setLongClickable(true);
		//这里接收Intent传来的消息，然后传到Adapter里面去
		className = this.getIntent().getStringExtra(ChoseItemActivity.CLASS_NAME);
		System.out.println("this is in ScheduleActivity 55  "+className);
		adapter = new AdapterForSchedule(this,n,className);
		upDate();
	}
	
	public void upDate(){
		
		textView.setText(text);
		listView.setAdapter(adapter);
	}
//
//	public String getText() {
//		return text;
//	}


	@Override
	public boolean dispatchTouchEvent(MotionEvent ev) {
		// TODO Auto-generated method stub
		mDetector.onTouchEvent(ev);
		return super.dispatchTouchEvent(ev);
	}

	@Override
	public boolean onTouch(View v, MotionEvent event) {
		// TODO Auto-generated method stub
		return  mDetector.onTouchEvent(event);
	}

	@Override
	public boolean onDown(MotionEvent e) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX,
			float velocityY) {
		// TODO Auto-generated method stub
		//fling to left
		if(e1.getX()-e2.getX()>FLING_MIN_DISTANCE&&Math.abs(velocityX)>FLING_MIN_VELOCITY){
			n = (n==7)?1:n+1;
		}//fling to right
		else if(e2.getX()-e1.getX()>FLING_MIN_DISTANCE&&Math.abs(velocityX)>FLING_MIN_VELOCITY){
			n = (n==1)?7:n-1;
		}
		setDate();
		adapter = new AdapterForSchedule(this,n,className);
		upDate();
		return false;
	}
	
	private void setDate(){
		switch(n){
		case 1:text = "星期一";break;
		case 2:text = "星期二";break;
		case 3:text = "星期三";break;
		case 4:text = "星期四";break;
		case 5:text = "星期五";break;
		case 6:text = "星期六";break;
		case 7:text = "星期天";break;
		}
	}

	@Override
	public void onLongPress(MotionEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX,
			float distanceY) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void onShowPress(MotionEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean onSingleTapUp(MotionEvent e) {
		// TODO Auto-generated method stub
		return false;
	}

}
