package com.young.activity;


import android.os.Bundle;
import android.widget.ListView;

import com.young.R;
import com.young.adapter.AdapterForSchedule;


public class PersonalMessageActivity extends BaseActivity{
	
	private ListView listView;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_personal_schedule);
		listView = (ListView)findViewById(R.id.list_personal_schedule);
		AdapterForSchedule mAdapter = new AdapterForSchedule(this,3);
		listView.setAdapter(mAdapter);
	}
	
	

}
