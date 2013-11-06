
 

package com.young.activity;

import java.util.ArrayList;

import android.annotation.TargetApi;
import android.os.Build;
import android.os.Bundle;
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
		secondTitle = "please chose";
		//这里需要添加一个switch用来添加不同的数据
		setMyAdatper();
		upDateSecondTitle();
	}
	//���Ը���
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
		list.add("personal schedule");
		list.add("other schedule");
		return list;
	}


	
}

