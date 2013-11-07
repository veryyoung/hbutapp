
 

package com.young.activity;

import java.util.ArrayList;

import android.annotation.TargetApi;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.young.R;

public class ChoseActivity extends BaseActivity{
	
	private TextView choseText;
	private ListView choseList;
	private String secondTitle;
	private ArrayAdapter<String> myAdapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_chose);
		choseText = (TextView)this.findViewById(R.id.chose_title);
		
		choseList = (ListView)this.findViewById(R.id.chose_list);
		secondTitle = "课表";
		//这里需要添加一个switch用来添加不同的数据
		setMyAdatper();
		upDateSecondTitle();
		choseList.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				// TODO Auto-generated method stub
				switch(arg2){
				case 0:Intent intent = new Intent(ChoseActivity.this,ScheduleActivity.class);
					startActivity(intent);break;
				default:break;
				}
			}
		});
	}
	//更新数据
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

	@TargetApi(Build.VERSION_CODES.HONEYCOMB)
	private void setMyAdatper(){
		this.myAdapter = new ArrayAdapter<String>(ChoseActivity.this,R.layout.activity_chose_item,R.id.chose_item_text);
		ArrayList<String> list = getSecondList();
		myAdapter.addAll(list);
	}
	
	private ArrayList<String> getSecondList(){
		ArrayList<String> list = new ArrayList<String>();
		list.add("个人课表");
		list.add("其他班级课表");
		return list;
	}


	
}

