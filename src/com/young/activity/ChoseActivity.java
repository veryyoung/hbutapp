package com.young.activity;

import android.os.Bundle;
import android.widget.ListView;
import android.widget.TextView;

import com.young.R;
import com.young.adapter.MyBaseAdapter;

public class ChoseActivity extends BaseActivity{
	
	private TextView choseText;
	private ListView choseList;
	private String secondTitle;
	private MyBaseAdapter myAdapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_chose);
		choseText = (TextView)this.findViewById(R.id.chose_title);
		choseList = (ListView)this.findViewById(R.id.chose_list);
		secondTitle = "please chose";
		
		upDateSecondTitle();
	}
	//可以更新
	public void upDateSecondTitle(){
		choseText.setText(secondTitle);
		choseList.setAdapter(myAdapter);
	}


	public String getSecondTitle() {
		return secondTitle;
	}


	public void setSecondTitle(String secondTitle) {
		this.secondTitle = secondTitle;
	}


	public MyBaseAdapter getMyAdapter() {
		return myAdapter;
	}

	public void setMyAdapter(MyBaseAdapter myAdapter) {
		this.myAdapter = myAdapter;
	}

	
}
