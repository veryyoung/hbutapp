package com.young.activity;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.TextView;

import com.young.R;
import com.young.adapter.AdapterForSchedule;
import com.young.adapter.MyBaseAdapter;

public class ScheduleActivity extends Activity {
	
	private TextView textView;
	private ListView listView;
	private String text;
	private MyBaseAdapter adapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_schedule);
		textView = (TextView)this.findViewById(R.id.text_schedule_title);
		listView = (ListView)this.findViewById(R.id.list_schedule_course);
		text = "星期一";
		adapter = new AdapterForSchedule(this);
		upDate();
	}
	
	public void upDate(){
		textView.setText(text);
		listView.setAdapter(adapter);
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public MyBaseAdapter getAdapter() {
		return adapter;
	}

	public void setAdapter(MyBaseAdapter adapter) {
		this.adapter = adapter;
	}
	
	



}
