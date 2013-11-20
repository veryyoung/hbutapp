package com.young.activity;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
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
import com.young.business.HBUT;
//import android.content.Intent;

public class ScheduleActivity extends Activity implements OnTouchListener, OnGestureListener ,Runnable{
	
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
/////////////////////////////////////////////////////////////////////////////////////
	//用来存课表的变化
	private ArrayList<Integer> isOneLine  = new ArrayList<Integer>();
	private LinkedList<String> list;
	private ArrayList<HashMap<String,String>> data;
	private ProgressDialog pd;
	private Handler handler = new Handler(){

		@Override
		public void handleMessage(Message msg) {
			// TODO Auto-generated method stub
			data = getByDay(n-1);
			adapter = new AdapterForSchedule(ScheduleActivity.this,isOneLine,data);
			upDate();
			pd.dismiss();
			super.handleMessage(msg);
		}
		
	};

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
//		layout.setClickable(true);
		layout.setLongClickable(true);

		//这里接收Intent传来的消息，然后传到Adapter里面去
		className = this.getIntent().getStringExtra(ChoseItemActivity.CLASS_NAME);
		System.out.println("this is in ScheduleActivity 55  "+className);
///////////////////////////////////////////////////////////////////////////////
		isOneLine.add(0);
		Thread thread = new Thread(this);
		thread.start();
		pd = ProgressDialog.show(ScheduleActivity.this, "加载中", "加载中，请稍后...");
		
		
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
		adapter = new AdapterForSchedule(ScheduleActivity.this,isOneLine,getByDay(n-1));
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

	@Override
	public void run() {
		// TODO Auto-generated method stub
		try{
			HBUT hbut = HBUT.getInstance();

			if(ScheduleActivity.NO_NAME.equals(className)){
				list =(LinkedList<String>)  hbut.myselfSchedule();
//				System.out.println("the size  is "+list.size());
//				for(int i=0;i<list.size();i++){
//					System.out.println(list.get(i)+"    "+i);
//				}
			}else if(className!=null){
				list = (LinkedList<String>) hbut.classSchedule(className);
//				for(String name:list){
//					System.out.println(name);
//				}
			}
			handler.sendEmptyMessage(0);

		}catch(IOException e){
			
			e.printStackTrace();
		}

	}
	
	
	private ArrayList<HashMap<String,String>> getByDay(int day) {
		data = new ArrayList<HashMap<String,String>>();
		
		HashMap<String,String> map;
		Log.v("this","167 is ok");
		
//		pd = ProgressDialog.show(context, "please wait", "please wait ...");
		
			for(int i = day;i<35;i+=7){
				//map = new 
				String total = list.get(i);
//				System.out.println("total is "+total);
				if(!"".equals(total)){
					String[] devided = total.split("\\|");
					int coun = devided.length;
//					System.out.println("coun   "+coun);
//					for(int xx = 0;xx<devided.length;xx++){
//						System.out.println(xx+"   "+devided[xx]);
//					}
					////////////////
					for(int x = 0;x<=coun/4;x++){///////////////
						map = new HashMap<String,String>();
						int y = x*4;
//						System.out.println("y"+y);////////////////////////////////
						map.put("name", devided[y+0]);
						map.put("teacher",devided[y+1]);
						map.put("time", devided[y+2]);
						
						System.out.println(map);
						if(x==0){
							isOneLine.add(isOneLine.get(isOneLine.size()-1)==1?0:1);
						}else{
							isOneLine.add(isOneLine.get(isOneLine.size()-1));
						}
						data.add(map);
					}////////////////////////////////////////////////////////这个还没有测啊
					
				}else{
					map = new HashMap<String,String>();
					map.put("name", "");
					map.put("time", "");
					map.put("teacher","");
					data.add(map);
					isOneLine.add(isOneLine.get(isOneLine.size()-1)==1?0:1);
				}
//				System.out.println(total);
//				System.out.println(i);
//				System.out.println(map);
				
			}
			return data;
}

}
